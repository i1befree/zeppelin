package com.sktelecom.cep.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.FlowInfo;

/**
 * 플로우관리 - 플로우 CRUD 담당 Dao.
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/FlowInfoMapper.xml 과 1:1
 * 매핑한다.
 *
 * @author 박상민
 */
public interface FlowInfoDao {

  static final Logger LOG = LoggerFactory.getLogger(FlowInfoDao.class);

  /**
   * 플로우 생성.
   * 
   * @param FlowInfo
   * @return
   */
  int create(FlowInfo flowInfo);

  /**
   * 플로우 수정.
   * 
   * @param FlowInfo
   * @return
   */
  int update(FlowInfo flowInfo);

  /**
   * 플로우 삭제.
   * 
   * @param FlowInfo
   * @return
   */
  int delete(FlowInfo flowInfo);

  /**
   * 플로우 정보 조회.
   * 
   * @param FlowInfo
   * @return
   */
  FlowInfo getInfo(FlowInfo flowInfo);

  /**
   * 플로우 목록 조회.
   * 
   * @param FlowInfo
   * @return
   */
  List<FlowInfo> getList(FlowInfo flowInfo);

  /**
   * 플로우 목록 카운트.
   * 
   * @param FlowInfo
   * @return
   */
  long getListCount(FlowInfo flowInfo);

  /**
   * 플로우 APP 상태 업데이트.
   * 
   * @param flowInfo
   * @return
   */
  int updateAppStatus(FlowInfo flowInfo);

}
