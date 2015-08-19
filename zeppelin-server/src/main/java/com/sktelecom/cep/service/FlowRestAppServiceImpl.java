package com.sktelecom.cep.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sktelecom.cep.common.FlowStatusCodeEnum;
import com.sktelecom.cep.common.ResourceCodeEnum;
import com.sktelecom.cep.dao.FlowInfoDao;
import com.sktelecom.cep.dao.FlowResourceDao;
import com.sktelecom.cep.exception.BizException;
import com.sktelecom.cep.vo.FlowAppJsonObject;
import com.sktelecom.cep.vo.FlowInfo;
import com.sktelecom.cep.vo.FlowJsonObject;
import com.sktelecom.cep.vo.FlowObject;
import com.sktelecom.cep.vo.FlowResource;
import com.sktelecom.cep.vo.FlowResourceProperty;

/**
 * 사용자관리 - 사용자 CRUD 담당 Service 구현체.
 *
 * @author 박상민
 */
@Service
public class FlowRestAppServiceImpl implements FlowRestAppService {

  static final Logger LOG = LoggerFactory
      .getLogger(FlowRestAppServiceImpl.class);

  @Inject
  private FlowInfoDao flowInfoDao;

  @Inject
  private FlowResourceDao flowResourceDao;

  @Override
  public FlowInfo updateStartApplication(FlowInfo pFlowInfo) {
    FlowInfo flowInfo = flowInfoDao.getInfo(pFlowInfo);

    // 상태변화 (201:초기상태), (100:실행요청, 101:실행성공, 102:실행실패), (200:정지요청, 201:정지성공,
    // 202:정지실패)
    // 실행요청 가능상태는 현재 flow app 이 201,102 가 아니면 실행요청이 되지 않도록 한다.
    if (!FlowStatusCodeEnum.EXEC_FAIL.getCode().equals(flowInfo.getAppStatus())
        && !FlowStatusCodeEnum.STOP_SUCCESS.getCode().equals(
            flowInfo.getAppStatus())) {
      throw new BizException(flowInfo.getAppStatus());
    }
    // 앱 실행 요청상태로 변경
    flowInfo.setAppExecServerPath(pFlowInfo.getAppExecServerPath());
    flowInfo.setAppStatus(FlowStatusCodeEnum.EXEC_REQUEST.getCode());
    flowInfoDao.updateAppStatus(flowInfo);

    // spark 어플리케이션을 실행시키기 위한 json 생성
    String flowJsonData = flowInfo.getFlowJsonData();

    ObjectMapper jsonMapper = new ObjectMapper();
    FlowJsonObject flowJsonObject = null;

    try {
      flowJsonObject = jsonMapper.readValue(flowJsonData, FlowJsonObject.class);

      // spark app 호출을 위한 json 생성
      FlowAppJsonObject flowAppJsonObject = new FlowAppJsonObject();
      flowAppJsonObject.setFlowId(flowInfo.getFlowId());
      flowAppJsonObject.setAppId(flowInfo.getAppId());
      flowAppJsonObject.setAppExecServerPath(flowInfo.getAppExecServerPath());
      List<FlowResource> sources = flowAppJsonObject.getSources();
      List<FlowResource> components = flowAppJsonObject.getComponents();
      List<FlowResource> sinks = flowAppJsonObject.getSinks();

      FlowResource pFlowResource = new FlowResource();
      for (FlowObject flowObject : flowJsonObject.getNodes()) {
        pFlowResource.setId(Integer.parseInt(flowObject.getResourceId()));
        FlowResource flowResource = flowResourceDao
            .getResourceDetailInfo(pFlowResource);
        Map<String, String> propertyMap = new HashMap<String, String>();
        for (FlowResourceProperty property : flowResource.getProperties()) {
          propertyMap.put(property.getResource_key(),
              property.getResource_value());
        }
        flowResource.setPropertyMap(propertyMap);
        if (ResourceCodeEnum.SOURCE.getCode().equals(flowResource.getType_1())) {
          sources.add(flowResource);
        } else if (ResourceCodeEnum.COMPONENT.getCode().equals(
            flowResource.getType_1())) {
          components.add(flowResource);
        } else if (ResourceCodeEnum.SINK.getCode().equals(
            flowResource.getType_1())) {
          sinks.add(flowResource);
        }
      }

      // app 실행 json 으로 변경
      String jsonStr = jsonMapper.writeValueAsString(flowAppJsonObject);
      flowInfo.setFlowJsonData(jsonStr);

    } catch (Exception e) {
      throw new BizException(e);
    }
    return flowInfo;
  }

  @Override
  public FlowInfo updateStopApplication(FlowInfo pFlowInfo) {
    FlowInfo flowInfo = flowInfoDao.getInfo(pFlowInfo);

    // 상태변화 (201:초기상태), (100:실행요청, 101:실행성공, 102:실행실패), (200:정지요청, 201:정지성공,
    // 202:정지실패)
    // 정지요청 가능상태는 현재 flow app 이 101,202 가 아니면 실행요청이 되지 않도록 한다.
    if (!FlowStatusCodeEnum.EXEC_SUCCESS.getCode().equals(
        flowInfo.getAppStatus())
        && !FlowStatusCodeEnum.STOP_FAIL.getCode().equals(
            flowInfo.getAppStatus())) {
      throw new BizException(flowInfo.getAppStatus());
    }
    // 앱 정지 요청상태로 변경
    flowInfo.setAppStatus(FlowStatusCodeEnum.STOP_REQUEST.getCode());
    flowInfoDao.updateAppStatus(flowInfo);

    // spark 어플리케이션을 정지시키기 위한 json 생성
    // app 정지 json 으로 변경
    String jsonStr = "{'flowId': '', 'appId': '', 'appExecServerPath': ''}";
    flowInfo.setFlowJsonData(jsonStr);

    return flowInfo;
  }

  @Override
  public void updateAppStatus(FlowInfo flowInfo) {
    // 상태변화 (201:초기상태), (100:실행요청, 101:실행성공, 102:실행실패), (200:정지요청, 201:정지성공,
    // 202:정지실패)
    // App상태 업데이트
    // 실행 101, 정지 201

    flowInfoDao.updateAppStatus(flowInfo);
  }

}
