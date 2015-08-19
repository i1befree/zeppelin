package com.sktelecom.cep.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.FlowObject;

/**
 * 플로우관리 - 플로우 객체 CRUD 담당 Dao.
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/FlowObjectMapper.xml 과 1:1
 * 매핑한다.
 *
 * @author 박상민
 */
public interface FlowObjectDao {

  static final Logger LOG = LoggerFactory.getLogger(FlowObjectDao.class);

  /**
   * 플로우 객체 생성.
   * 
   * @param FlowObject
   * @return
   */
  int create(FlowObject flowObject);

  /**
   * 플로우 객체 삭제.
   * 
   * @param FlowObject
   * @return
   */
  int deleteByFlowId(FlowObject flowObject);

  /**
   * 플로우 목록 조회.
   * 
   * @param FlowObject
   * @return
   */
  List<FlowObject> getListByFlowId(FlowObject flowObject);

}
