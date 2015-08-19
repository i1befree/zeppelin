package com.sktelecom.cep.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sktelecom.cep.common.CepConstant;
import com.sktelecom.cep.common.FlowStatusCodeEnum;
import com.sktelecom.cep.common.ResourceCodeEnum;
import com.sktelecom.cep.common.SimpleResultMessage;
import com.sktelecom.cep.exception.BizException;
import com.sktelecom.cep.service.FlowMgrService;
import com.sktelecom.cep.service.FlowRestAppService;
import com.sktelecom.cep.vo.FlowCluster;
import com.sktelecom.cep.vo.FlowInfo;
import com.sktelecom.cep.vo.FlowResource;
import com.sktelecom.cep.vo.JobDeleteResult;
import com.sktelecom.cep.vo.JobPostResult;
import com.sktelecom.cep.vo.Resource;
import com.sktelecom.cep.vo.ResourceList;
import com.sktelecom.cep.vo.UserSession;

/**
 * flow 관리 - Controller.
 *
 * @author 박상민
 */
@Controller
public class FlowMgrController {

  Logger logger = LoggerFactory.getLogger(FlowMgrController.class);

  private final String startSparkJobServerUrl = "http://52.68.186.228:8090/jobs?appName" + 
      "=test&classPath=spark.jobserver.WordCountExample&context=shared&sync=false";
  private final String stopSparkJobServerUrl = "http://52.68.186.228:8090/jobs";

  @Inject
  private FlowMgrService flowMgrService;

  @Inject
  private FlowRestAppService flowRestAppService;

  // / @cond doxygen don't parsing in here
  /**
   * 각각의 리소드 전체 목록들을 가져온다.
   * 
   * @return
   */
  @RequestMapping(value = "/flowMgr/getResourceList", method = {
      RequestMethod.GET, RequestMethod.POST })
  @ResponseBody
  // / @endcond
  public ResourceList getResourceList() {

    ResourceList resultList = new ResourceList();
    FlowResource pFlowResource = new FlowResource();
    pFlowResource.setType_1(ResourceCodeEnum.SOURCE.getCode());
    List<Resource> datasourceList = flowMgrService
        .getResourceList(pFlowResource);
    if (datasourceList != null) {
      resultList.setDatasourceList(datasourceList);
    }
    pFlowResource.setType_1(ResourceCodeEnum.COMPONENT.getCode());
    List<Resource> componentList = flowMgrService
        .getResourceList(pFlowResource);
    if (componentList != null) {
      resultList.setComponentList(componentList);
    }
    pFlowResource.setType_1(ResourceCodeEnum.SINK.getCode());
    List<Resource> repositoryList = flowMgrService
        .getResourceList(pFlowResource);
    if (repositoryList != null) {
      resultList.setRepositoryList(repositoryList);
    }
    return resultList;
  }

  // / @cond doxygen don't parsing in here
  /**
   * 플로우 목록을 조회한다. - 페이징
   * 
   * @param flowInfo
   * @return
   */
  @RequestMapping(value = "/flowMgr/getFlowList", method = { RequestMethod.GET,
      RequestMethod.POST })
  @ResponseBody
  // / @endcond
  public List<FlowInfo> getFlowList(@RequestBody FlowInfo flowInfo) {
    List<FlowInfo> resultList = flowMgrService.getFlowList(flowInfo);
    return resultList;
  }

  // / @cond doxygen don't parsing in here
  /**
   * 플로우 정보를 조회한다.
   * 
   * @param flowInfo
   * @return
   */
  @RequestMapping(value = "/flowMgr/getFlowInfo", method = { RequestMethod.GET,
      RequestMethod.POST })
  @ResponseBody
  // / @endcond
  public FlowInfo getFlowInfo(@RequestBody FlowInfo flowInfo) {
    return flowMgrService.getFlowInfo(flowInfo);
  }

  /**
   * 플로우 생성/수정한다.
   * 
   * @param flowInfo
   * @return
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/flowMgr/saveFlowInfo", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage saveFlowInfo(@RequestBody FlowInfo flowInfo,
      HttpSession session) {
    SimpleResultMessage message = new SimpleResultMessage("FAIL", "플로우 저장 실패");

    UserSession userSession = (UserSession) session
        .getAttribute(CepConstant.USER_SESSION);

    int resultInt = 0;
    if (flowInfo.getFlowId() == null || "".equals(flowInfo.getFlowId().trim())) {
      flowInfo.setCreateId(userSession.getId());
      flowInfo.setCreateIdName(userSession.getName());
      flowInfo.setUpdateId(userSession.getId());
      flowInfo.setUpdateIdName(userSession.getName());
      resultInt = flowMgrService.createFlowInfo(flowInfo);
    } else {
      flowInfo.setUpdateId(userSession.getId());
      flowInfo.setUpdateIdName(userSession.getName());
      resultInt = flowMgrService.updateFlowInfo(flowInfo);
    }
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("플로우를 저장 하였습니다.");
    }
    return message;
  }

  // / @cond doxygen don't parsing in here
  /**
   * 자원(데이타소스, 컴포넌트, 싱크) 정보를 조회한다.
   * 
   * @param id
   * @return
   */
  @RequestMapping(value = "/flowMgr/getResourceInfo", method = {
      RequestMethod.GET, RequestMethod.POST })
  @ResponseBody
  // / @endcond
  public FlowResource getDatasourceInfo(@RequestParam("id") int id) {
    FlowResource pFlowResource = new FlowResource();
    pFlowResource.setId(id);
    FlowResource info = flowMgrService.getResourceDetailInfo(pFlowResource);
    if (info == null) {
      info = new FlowResource();
    }
    return info;
  }

  // 플로우 실행
  /*
   * 상태 STARTED COMPLETED KILLED OK 나머지는 FAIL
   */
  @RequestMapping(value = "/flowMgr/startApplication", method = {
      RequestMethod.GET, RequestMethod.POST })
  @ResponseBody
  // / @endcond
  public SimpleResultMessage startApplication(@RequestBody FlowInfo pFlowInfo,
      HttpSession session) {
    SimpleResultMessage message = new SimpleResultMessage("FAIL",
        "어플리케이션 실행 실패");
    try {
      final FlowInfo flowInfo = flowRestAppService
          .updateStartApplication(pFlowInfo);
      flowInfo.setAppStatusName(FlowStatusCodeEnum.getNameByCode(flowInfo
          .getAppStatus()));

      // app 실행에 필요한 JSON 을 전달하기 위해서, 동기 rest api 호출
      // TODO
      String url = startSparkJobServerUrl;
      String body = null;
      try {
        body = String.format("input.string=\"%s\"",
            URLEncoder.encode(flowInfo.getFlowJsonData(), "UTF-8"));
      } catch (UnsupportedEncodingException e1) {

        e1.printStackTrace();
      }
      asyncRest(new ListenableFutureCallback<ResponseEntity<String>>() {
        @Override
        public void onSuccess(ResponseEntity<String> entity) {
          // prints body source code for the given URL
          ObjectMapper jsonMapper = new ObjectMapper();
          JobPostResult result;
          try {
            result = (JobPostResult) jsonMapper.readValue(entity.getBody(),
                JobPostResult.class);
            if (logger.isInfoEnabled()) {
              logger.info("============> startApplication 상태:"
                  + result.getStatus());
            }
            // db 상태 업데이트
            if ("STARTED".equals(result.getStatus())) {
              if (logger.isInfoEnabled()) {
                logger.info("============> startApplication onSuccess");
              }
              FlowInfo jobResult = new FlowInfo();
              jobResult.setFlowId(flowInfo.getFlowId());
              jobResult.setAppId(result.getResult().getJobId());
              jobResult.setAppExecServerPath(flowInfo.getAppExecServerPath());
              jobResult.setAppStatus(FlowStatusCodeEnum.EXEC_SUCCESS.getCode());
              flowRestAppService.updateAppStatus(jobResult);
            } else { // OK 는 정상으로 본다.즉 실행요청
              throw new BizException("FAIL");
            } // else 나머지는 실패
          } catch (Exception e) {
            if (logger.isInfoEnabled()) {
              logger.info("============> startApplication onFailure");
            }
            FlowInfo jobResult = new FlowInfo();
            jobResult.setFlowId(flowInfo.getFlowId());
            jobResult.setAppId(null);
            jobResult.setAppExecServerPath(null);
            jobResult.setAppStatus(FlowStatusCodeEnum.EXEC_FAIL.getCode());
            flowRestAppService.updateAppStatus(jobResult);
          }
        }

        @Override
        public void onFailure(Throwable t) {
          if (logger.isInfoEnabled()) {
            logger.info("============> startApplication onFailure");
          }
          // db 상태 롤백
          FlowInfo jobResult = new FlowInfo();
          jobResult.setFlowId(flowInfo.getFlowId());
          jobResult.setAppId(null);
          jobResult.setAppExecServerPath(null);
          jobResult.setAppStatus(FlowStatusCodeEnum.EXEC_FAIL.getCode());
          flowRestAppService.updateAppStatus(jobResult);
        }
      }, url, HttpMethod.POST, body);

      message.setRsCode("SUCCESS");
      message.setRsMessage("어플리케이션 실행 요청.");
    } catch (BizException e) {
      // flowInfo.setAppStatus(e.getMessage());
      // flowInfo.setAppStatusName(FlowStatusCodeEnum.getNameByCode(e.getMessage()));

      message.setRsMessage(String.format("현재 어플리케이션은 %s [%s] 상태입니다.",
          FlowStatusCodeEnum.getNameByCode(e.getMessage()), e.getMessage()));
    }
    // message.setObject(flowInfo);
    if (logger.isInfoEnabled()) {
      logger.info("============> startApplication End");
    }
    return message;
  }

  // 플로우 정지
  @RequestMapping(value = "/flowMgr/stopApplication", method = {
      RequestMethod.GET, RequestMethod.POST })
  @ResponseBody
  // / @endcond
  public SimpleResultMessage stopApplication(
      @RequestBody final FlowInfo pFlowInfo, HttpSession session) {
    SimpleResultMessage message = new SimpleResultMessage("FAIL",
        "어플리케이션 정지 실패");
    try {
      final FlowInfo flowInfo = flowRestAppService
          .updateStopApplication(pFlowInfo);
      flowInfo.setAppStatusName(FlowStatusCodeEnum.getNameByCode(flowInfo
          .getAppStatus()));

      // app 정지에 필요한 JSON 을 전달하기 위해서, 동기 rest api 호출
      // TODO
      // job 의 상태 체크 RUNNING 인경우 중지처리, 아닌경우는 실패처리
      String url = stopSparkJobServerUrl + "/" + flowInfo.getAppId();
      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<JobDeleteResult> getResult = restTemplate.getForEntity(
          url, JobDeleteResult.class);
      if (logger.isInfoEnabled()) {
        logger.info("============> stopApplication job 상태:"
            + getResult.getBody().getStatus());
      }
      if ("OK".equals(getResult.getBody().getStatus())) {
        FlowInfo jobResult = new FlowInfo();
        jobResult.setFlowId(flowInfo.getFlowId());
        jobResult.setAppId(null);
        jobResult.setAppExecServerPath(null);
        jobResult.setAppStatus(FlowStatusCodeEnum.STOP_SUCCESS.getCode());
        flowRestAppService.updateAppStatus(jobResult);
        message.setRsCode("SUCCESS");
        message.setRsMessage("어플리케이션이 이미 정지되어 있습니다.");
        return message;
      }

      asyncRest(new ListenableFutureCallback<ResponseEntity<String>>() {
        @Override
        public void onSuccess(ResponseEntity<String> entity) {
          // prints body source code for the given URL
          ObjectMapper jsonMapper = new ObjectMapper();
          JobDeleteResult result;
          try {
            result = (JobDeleteResult) jsonMapper.readValue(entity.getBody(),
                JobDeleteResult.class);
            if (logger.isInfoEnabled()) {
              logger.info("============> stopApplication 상태:"
                  + result.getStatus());
            }
            // db 상태 업데이트
            if ("OK".equals(result.getStatus())
                || "KILLED".equals(result.getStatus())) {
              if (logger.isInfoEnabled()) {
                logger.info("============> stopApplication onSuccess");
              }
              FlowInfo jobResult = new FlowInfo();
              jobResult.setFlowId(flowInfo.getFlowId());
              jobResult.setAppId(null);
              jobResult.setAppExecServerPath(null);
              jobResult.setAppStatus(FlowStatusCodeEnum.STOP_SUCCESS.getCode());
              flowRestAppService.updateAppStatus(jobResult);
            } else {
              throw new BizException("FAIL");
            }
          } catch (Exception e) {
            if (logger.isInfoEnabled()) {
              logger.info("============> stopApplication onFailure");
            }
            FlowInfo jobResult = new FlowInfo();
            jobResult.setFlowId(pFlowInfo.getFlowId());
            jobResult.setAppId(pFlowInfo.getAppId());
            jobResult.setAppExecServerPath(pFlowInfo.getAppExecServerPath());
            jobResult.setAppStatus(FlowStatusCodeEnum.STOP_FAIL.getCode());
            flowRestAppService.updateAppStatus(jobResult);
          }
        }

        @Override
        public void onFailure(Throwable t) {
          if (logger.isInfoEnabled()) {
            logger.info("============> stopApplication onFailure");
          }
          // db 상태 롤백
          FlowInfo jobResult = new FlowInfo();
          jobResult.setFlowId(pFlowInfo.getFlowId());
          jobResult.setAppId(pFlowInfo.getAppId());
          jobResult.setAppExecServerPath(pFlowInfo.getAppExecServerPath());
          jobResult.setAppStatus(FlowStatusCodeEnum.STOP_FAIL.getCode());
          flowRestAppService.updateAppStatus(jobResult);
        }
      }, url, HttpMethod.DELETE);

      message.setRsCode("SUCCESS");
      message.setRsMessage("어플리케이션 정지 요청.");
    } catch (BizException e) {
      // flowInfo.setAppStatus(e.getMessage());
      // flowInfo.setAppStatusName(FlowStatusCodeEnum.getNameByCode(e.getMessage()));
      String strMsg = FlowStatusCodeEnum.getNameByCode(e.getMessage());
      if (strMsg == null) {
        strMsg = e.getMessage();
      }
      message.setRsMessage(String.format("현재 어플리케이션은 %s [%s] 상태입니다.",
          FlowStatusCodeEnum.getNameByCode(e.getMessage()), e.getMessage()));
    }
    // message.setObject(flowInfo);
    if (logger.isInfoEnabled()) {
      logger.info("============> stopApplication end");
    }
    return message;
  }

  // / @cond doxygen don't parsing in here
  /**
   * 클러스터 목록을 조회한다. - 노페이징
   * 
   * @param flowInfo
   * @return
   */
  @RequestMapping(value = "/flowMgr/getClusterList", method = {
      RequestMethod.GET, RequestMethod.POST })
  @ResponseBody
  // / @endcond
  public List<FlowCluster> getClusterList() {
    List<FlowCluster> resultList = flowMgrService.getClusterList();
    return resultList;
  }

  private void asyncRest(
      ListenableFutureCallback<ResponseEntity<String>> futureCallback,
      String url, HttpMethod method) {
    asyncRest(futureCallback, url, method, null);
  }

  private void asyncRest(
      ListenableFutureCallback<ResponseEntity<String>> futureCallback,
      String url, HttpMethod method, String body) {
    AsyncRestTemplate asycTemp = new AsyncRestTemplate();
    Class<String> responseType = String.class;
    try {
      // create request entity using HttpHeaders
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
      ListenableFuture<ResponseEntity<String>> future = asycTemp.exchange(url,
          method, requestEntity, responseType);

      // Add completion callbacks
      future.addCallback(futureCallback);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException(e);
    }
  }

}
