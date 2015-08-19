package com.sktelecom.cep.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sktelecom.cep.common.FlowStatusCodeEnum;
import com.sktelecom.cep.common.ResourceCodeEnum;
import com.sktelecom.cep.dao.FlowInfoDao;
import com.sktelecom.cep.dao.FlowObjectDao;
import com.sktelecom.cep.dao.FlowResourceDao;
import com.sktelecom.cep.exception.BizException;
import com.sktelecom.cep.vo.FlowCluster;
import com.sktelecom.cep.vo.FlowInfo;
import com.sktelecom.cep.vo.FlowJsonObject;
import com.sktelecom.cep.vo.FlowObject;
import com.sktelecom.cep.vo.FlowResource;
import com.sktelecom.cep.vo.FlowResourceProperty;
import com.sktelecom.cep.vo.Resource;

/**
 * 사용자관리 - 사용자 CRUD 담당 Service 구현체.
 *
 * @author 박상민
 */
@Service
public class FlowMgrServiceImpl implements FlowMgrService {

  static final Logger LOG = LoggerFactory
      .getLogger(FlowMgrServiceImpl.class);

  @Inject
  private FlowInfoDao flowInfoDao;

  @Inject
  private FlowObjectDao flowObjectDao;

  @Inject
  private FlowResourceDao flowResourceDao;

  @Override
  public FlowResource getResourceDetailInfo(FlowResource pFlowResource) {
    FlowResource flowResource = flowResourceDao
        .getResourceDetailInfo(pFlowResource);
    if (flowResource != null) {
      // properties 리스트 목록을 , properties 맵 으로 변경한다.
      Map<String, String> propertyMap = new HashMap<String, String>();
      for (FlowResourceProperty property : flowResource.getProperties()) {
        propertyMap.put(property.getResource_key(),
            property.getResource_value());
      }
      flowResource.setPropertyMap(propertyMap);
    }
    return flowResource;
  }

  @Override
  public List<Resource> getResourceList(FlowResource pFlowResource) {
    String resourceType = ResourceCodeEnum.getNameByCode(pFlowResource
        .getType_1());
    List<Resource> list = new ArrayList<Resource>();
    Resource info = new Resource();

    List<FlowResource> flowResourceList = flowResourceDao
        .getResourceList(pFlowResource);
    for (FlowResource flowResource : flowResourceList) {
      info = new Resource();
      info.setResourceId("" + flowResource.getId());
      info.setResourceType(resourceType);
      info.setTitle(flowResource.getName());
      list.add(info);
    }
    return list;
  }

  @Override
  public FlowInfo getFlowInfo(FlowInfo flowInfo) {
    FlowInfo info = flowInfoDao.getInfo(flowInfo);
    info.setAppStatusName(FlowStatusCodeEnum.getNameByCode(info.getAppStatus()));
    return info;
  }

  @Override
  public int createFlowInfo(FlowInfo flowInfo) {
    String uuid = UUID.randomUUID().toString();
    flowInfo.setFlowId(uuid);
    flowInfo.setAppStatus(FlowStatusCodeEnum.STOP_SUCCESS.getCode()); // 초기는
                                                                      // 정지상태임
    int resultInt = flowInfoDao.create(flowInfo);

    // update 에서의 동작과 동일한 메소드 호출
    deleteCreate(flowInfo);

    return resultInt;
  }

  @Override
  public int updateFlowInfo(FlowInfo flowInfo) {
    int resultInt = flowInfoDao.update(flowInfo);

    // 삭제후 추가
    deleteCreate(flowInfo);

    return resultInt;
  }

  @Override
  public List<FlowInfo> getFlowList(FlowInfo flowInfo) {
    List<FlowInfo> list = new ArrayList<FlowInfo>();
    long totalCount = flowInfoDao.getListCount(flowInfo);
    if (totalCount > 0) {
      list = flowInfoDao.getList(flowInfo);
      if (list != null && list.size() > 0) {
        list.get(0).setTotalCount(totalCount);
      }
      for (FlowInfo info : list) {
        info.setAppStatusName(FlowStatusCodeEnum.getNameByCode(info
            .getAppStatus()));
      }
    }
    return list;
  }

  /**
   * 플로우관련 객체,링크를 삭제후 추가한다.
   * 
   * @param flowInfo
   */
  private void deleteCreate(FlowInfo flowInfo) {
    String flowJsonData = flowInfo.getFlowJsonData();

    ObjectMapper jsonMapper = new ObjectMapper();
    FlowJsonObject flowJsonObject = null;
    try {
      flowJsonObject = jsonMapper.readValue(flowJsonData, FlowJsonObject.class);
    } catch (Exception e) {
      throw new BizException(e.toString());
    }
    FlowObject flowObject = new FlowObject();
    flowObject.setFlowId(flowInfo.getFlowId());
    flowObjectDao.deleteByFlowId(flowObject);

    if (flowJsonObject != null) {
      for (FlowObject node : flowJsonObject.getNodes()) {
        node.setFlowId(flowInfo.getFlowId());
        flowObjectDao.create(node);
      }
    }
  }

  public List<FlowCluster> getClusterList() {
    List<FlowCluster> list = flowResourceDao.getClusterList();
    return list;
  }

}
