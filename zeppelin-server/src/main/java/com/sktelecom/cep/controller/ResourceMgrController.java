package com.sktelecom.cep.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
//
//import kafka.consumer.Consumer;
//import kafka.consumer.ConsumerConfig;
//import kafka.javaapi.consumer.ConsumerConnector;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sktelecom.cep.common.CepConstant;
import com.sktelecom.cep.common.SimpleResultMessage;
import com.sktelecom.cep.exception.BizException;
import com.sktelecom.cep.service.ResourceMgrService;
import com.sktelecom.cep.util.FieldUtil;
import com.sktelecom.cep.vo.FieldInfo;
import com.sktelecom.cep.vo.ResourceInfo;
import com.sktelecom.cep.vo.UserSession;

/**
 * ResourceMgr관리 - ResourceMgr CRUD 담당 Controller.
 *
 * @author 박현지
 */
@Controller
public class ResourceMgrController {

  @Inject
  private ResourceMgrService resourceMgrService;

  /**
   * ResourceMgr 목록 조회.
   * 
   * @param resourceInfo
   * @param session
   * @return List<SourceFile>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/resourceMgr/getList", method = { RequestMethod.GET,
      RequestMethod.POST })
  @ResponseBody
  // / @endcond
  public List<ResourceInfo> getList(@RequestBody ResourceInfo resourceInfo) {
    List<ResourceInfo> resultList = resourceMgrService
        .getResourceList(resourceInfo);
    return resultList;
  }

  /**
   * 리소스 정보를 조회한다.
   * 
   * @param resourceInfo
   * @return
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/resourceMgr/getInfo", method = { RequestMethod.GET,
      RequestMethod.POST })
  @ResponseBody
  // / @endcond
  public ResourceInfo getInfo(@RequestBody ResourceInfo resourceInfo) {
    return resourceMgrService.getResourceInfo(resourceInfo);
  }

  /**
   * 리소스 생성/수정한다.
   * 
   * @param resourceInfo
   * @return
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/resourceMgr/saveResourceInfo", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage saveResourceInfo(
      @RequestBody ResourceInfo resourceInfo, HttpSession session) {
    SimpleResultMessage message = new SimpleResultMessage("FAIL", "리소스 저장 실패");

    UserSession userSession = (UserSession) session
        .getAttribute(CepConstant.USER_SESSION);

    // 같은이름의 리소스명이 있는지 체크한다.
    ResourceInfo resultInfo = resourceMgrService
        .getResourceInfoByResourceName(resourceInfo);

    int resultInt = 0;
    if (resourceInfo.getResourceId() == null
        || "".equals(resourceInfo.getResourceId().trim())) {
      if (resultInfo != null) {
        message.setRsCode("FAIL_EXIST");
        message.setRsMessage("이미 존재 합니다.");
        return message;
      }
      resourceInfo.setCreateId(userSession.getId());
      resourceInfo.setCreateIdName(userSession.getName());
      resourceInfo.setUpdateId(userSession.getId());
      resourceInfo.setUpdateIdName(userSession.getName());
      resultInt = resourceMgrService.createResource(resourceInfo);
    } else {
      // 같은명칭의 리소스가 존재하고, 수정하는 리소스아이디와 같지 않을때만 이미존재하는 리소스이다.
      if (resultInfo != null
          && !resultInfo.getResourceId().equals(resourceInfo.getResourceId())) {
        message.setRsCode("FAIL_EXIST");
        message.setRsMessage("이미 존재 합니다.");
        return message;
      }
      resourceInfo.setUpdateId(userSession.getId());
      resourceInfo.setUpdateIdName(userSession.getName());
      resultInt = resourceMgrService.updateResource(resourceInfo);
    }
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("리소스를 저장 하였습니다.");
    }
    return message;
  }

  /**
   * 리소스 삭제한다.
   * 
   * @param resourceInfo
   * @return
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/resourceMgr/removeResourceInfo", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage removeResourceInfo(
      @RequestBody ResourceInfo resourceInfo, HttpSession session) {
    SimpleResultMessage message = new SimpleResultMessage("FAIL", "리소스 삭제 실패");

    int resultInt = resourceMgrService.deleteResource(resourceInfo);
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("리소스를 삭제 하였습니다.");
    }
    return message;
  }

  /**
   * 컴포넌트 jar 에서 필드정보를 추출한다.
   * 
   * @param file
   * @return
   * @throws IOException
   */
  @RequestMapping(value = "/resourceMgr/componentJar")
  @ResponseBody
  // / @endcond
  public Map<String, Object> componentJar(
      @RequestParam("file") MultipartFile file) throws IOException {
    Map<String, Object> map = new HashMap<String, Object>();

    String fileName = FieldUtil.getSaveFileName(file);
    map.put("fileName", file.getOriginalFilename());
    map.put("fileSize", file.getSize());
    map.put("aliasFileName", fileName);

    List<FieldInfo> fieldInfoList = FieldUtil.getFieldInfo(fileName);
    map.put("fieldInfoList", fieldInfoList);
    return map;
  }

  /**
   * 커스텀 파서로 부터 필드의 정보를 추출한다.
   * 
   * @param file
   * @return
   * @throws IOException
   */
  @RequestMapping(value = "/resourceMgr/customParser")
  @ResponseBody
  // / @endcond
  public Map<String, Object> customParser(
      @RequestParam("file") MultipartFile file) throws IOException {
    Map<String, Object> map = new HashMap<String, Object>();

    String fileName = FieldUtil.getSaveFileName(file);
    map.put("fileName", file.getOriginalFilename());
    map.put("fileSize", file.getSize());
    map.put("aliasFileName", fileName);

    List<FieldInfo> fieldInfoList = FieldUtil.getFieldInfo(fileName);
    map.put("fieldInfoList", fieldInfoList);
    return map;
  }

  /**
   * 커넥션 테스트.
   * 
   * @param file
   * @return
   * @throws IOException
   */
  @RequestMapping(value = "/resourceMgr/testConnection")
  @ResponseBody
  // / @endcond
  public SimpleResultMessage testConnection(
      @RequestBody ResourceInfo resourceInfo) {
    SimpleResultMessage message = new SimpleResultMessage("FAIL",
        "Fail to Connection");

    Map<String, String> map = resourceInfo.getProperty();
    try {
      String sourceType = map.get("sourceType");
      if ("kafka".equals(sourceType)) {
        String zkQuorum = map.get("zkQuorum");
        String group = map.get("group");
        String topics = map.get("topics");
        String numThread = map.get("numThread");

        Properties props = new Properties();
        props.put("zookeeper.connect", zkQuorum);
        props.put("zookeeper.session.timeout.ms", "500");
        props.put("group.id", group);
        props.put("auto.commit.enable", "false");

        // ConsumerConnector consumer = null;
        // try {
        // consumer = Consumer.createJavaConsumerConnector(new
        // ConsumerConfig(props));
        //
        // } catch(Exception e) {
        // throw new BizException("Fail to kafka connection");
        // } finally {
        // if(consumer != null) {
        // consumer.shutdown();
        // }
        // }
      }
      message.setRsCode("SUCCESS");
      message.setRsMessage("Success to Connection");
    } catch (Exception e) {
      message.setRsCode("FAIL");
      message.setRsMessage(e.getMessage());
    }
    return message;
  }

}
