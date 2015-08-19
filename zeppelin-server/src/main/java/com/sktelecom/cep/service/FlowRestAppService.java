package com.sktelecom.cep.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.FlowInfo;

/**
 * 스팍 어플리케이션과의 통신 처리 - Service.
 *
 * @author 박상민
 */
public interface FlowRestAppService {

  static final Logger LOG = LoggerFactory
      .getLogger(FlowRestAppService.class);

  /**
   * 원격 어플리케이션 실행처리 : 실행 플로우의 정보를 json 으로 전송한다.
   * 
   * { "flowId": "ce23e723-a60c-40e6-821d-516201604501", "appId": null,
   * "appExecServerPath": null, "resources": [{ "id": 1, "name":
   * "Source_Kafka_01", "type_1": "com_01", "type_2": "io_01", "resource_desc":
   * "Cdrtopic 연동", "properties": { "Zookeeper_list": "zk-01", "Topic_name":
   * "Cdrtopic" } }, { "id": 2, "name": "Component_File_01", "type_1": "com_03",
   * "type_2": "io_02", "resource_desc": "Cdr 처리결과 저장", "properties": {
   * "File_dir": "/test/data", "File_name": "Test.txt" } }, { "id": 3, "name":
   * "Sink_01", "type_1": "com_02", "type_2": "io_03", "resource_desc":
   * "intercall/user", "properties": { "index": "intercall", "type": "user" } }]
   * }
   * 
   * @param flowInfo
   * @return
   */
  FlowInfo updateStartApplication(FlowInfo flowInfo);

  /**
   * 원격 어플리케이션 정지처리 : 정지 플로우의 정보를 json 으로 전송한다.
   * 
   * @param flowInfo
   */
  FlowInfo updateStopApplication(FlowInfo flowInfo);

  /**
   * 플로우 App 상태 업데이트.
   * 
   * @param flowInfo
   */
  void updateAppStatus(FlowInfo flowInfo);

}
