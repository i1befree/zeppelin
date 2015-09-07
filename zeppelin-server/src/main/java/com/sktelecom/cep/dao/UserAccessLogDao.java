package com.sktelecom.cep.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.UserAccessLog;

/**
 * 사용자접속로그 - 사용자 접속 로그를 조회하는 Dao.
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/UserAccessLogMapper.xml 과
 * 1:1 매핑한다.
 *
 * @author 박상민
 */
public interface UserAccessLogDao {

  static final Logger LOG = LoggerFactory.getLogger(UserAccessLogDao.class);

  /**
   * 사용자 접속 로그 생성.
   * 
   * @param userAccessLog
   * @return
   */
  int create(UserAccessLog userAccessLog);

  /**
   * 사용자 접속 로그 목록 조회.
   * 
   * @param userAccessLog
   * @return
   */
  List<UserAccessLog> getList(UserAccessLog userAccessLog);

  /**
   * 사용자 접속 로그 목록 카운트.
   * 
   * @param userAccessLog
   * @return
   */
  long getListCount(UserAccessLog userAccessLog);

  List<UserAccessLog> getLoginCount(UserAccessLog userAccessLog);

  List<UserAccessLog> getLoginList(UserAccessLog userAccessLog);

  List<UserAccessLog> getUserList(UserAccessLog userAccessLog);

}
