package com.sktelecom.cep.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.FlowCluster;
import com.sktelecom.cep.vo.FlowInfo;
import com.sktelecom.cep.vo.FlowResource;
import com.sktelecom.cep.vo.Resource;

/**
 * 플로우관리 - Service.
 *
 * @author 박상민
 */
public interface FlowMgrService {

  static final Logger LOG = LoggerFactory.getLogger(FlowMgrService.class);

  /**
   * 리소스 정보를 조회한다.
   * 
   * @param pFlowResource
   * @return
   */
  FlowResource getResourceDetailInfo(FlowResource pFlowResource);

  /**
   * 리소스 타입별로 리소스들을 조회한다.
   * 
   * @param pFlowResource
   * @return
   */
  List<Resource> getResourceList(FlowResource pFlowResource);

  /**
   * 플로우 목록을 조회한다.
   * 
   * @param flowInfo
   * @return
   */
  List<FlowInfo> getFlowList(FlowInfo flowInfo);

  /**
   * 플로우 정보를 조회한다.
   * 
   * @param FlowInfo
   * @return
   */
  FlowInfo getFlowInfo(FlowInfo FlowInfo);

  /**
   * 플로우를 생성한다.
   * 
   * @param flowInfo
   * @return
   */
  int createFlowInfo(FlowInfo flowInfo);

  /**
   * 플로우를 수정한다.
   * 
   * @param flowInfo
   * @return
   */
  int updateFlowInfo(FlowInfo flowInfo);

  /**
   * 클러스터 목록을 가져온다.
   * 
   * @return
   */
  List<FlowCluster> getClusterList();
}
