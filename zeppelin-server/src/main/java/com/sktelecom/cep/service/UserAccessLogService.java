package com.sktelecom.cep.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.UserAccessLog;

/**
 * 사용자접속로그 - 사용자 접속 로그를 조회하는 Service.
 *
 * @author 박상민
 */
public interface UserAccessLogService {

  static final Logger LOG = LoggerFactory
      .getLogger(UserAccessLogService.class);

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

}
