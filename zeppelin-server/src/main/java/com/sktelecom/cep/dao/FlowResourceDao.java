package com.sktelecom.cep.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.FlowCluster;
import com.sktelecom.cep.vo.FlowResource;

/**
 * 플로우관리 - 플로우 CRUD 담당 Dao
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/FlowInfoMapper.xml 과 1:1
 * 매핑한다.
 *
 * @author 박상민
 */
public interface FlowResourceDao {

  static final Logger LOG = LoggerFactory.getLogger(FlowResourceDao.class);

  /**
   * 플로우 리소스 정보 조회 - property 정보들도 포함한다.
   * 
   * @param FlowResource
   * @return
   */
  FlowResource getResourceDetailInfo(FlowResource flowResource);

  /**
   * 리소스 정보 목록을 조회한다. - property 정보들은 제외시킨다.
   * 
   * @param flowResource
   * @return
   */
  List<FlowResource> getResourceList(FlowResource flowResource);

  /**
   * 클러스터 목록을 가져온다.
   * 
   * @return
   */
  List<FlowCluster> getClusterList();
}
