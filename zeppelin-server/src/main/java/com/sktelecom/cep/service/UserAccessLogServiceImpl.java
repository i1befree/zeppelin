package com.sktelecom.cep.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.cep.entity.User;
import com.sktelecom.cep.entity.UserAccessLog;
import com.sktelecom.cep.repository.UserAccessLogRepository;
import com.sktelecom.cep.repository.UserRepository;
import com.sktelecom.cep.vo.UserAccessLogVo;

/**
 * 사용자접속로그 - 사용자 접속 로그를 조회하는 Service 구현체.
 *
 * @author 박상민
 */
@Service
public class UserAccessLogServiceImpl implements UserAccessLogService {

  static final Logger LOG = LoggerFactory
      .getLogger(UserAccessLogServiceImpl.class);

  @Inject
  private UserAccessLogRepository userAccessLogRepository;

  @Inject
  private UserRepository userRepository;
  
  @Override
  public int create(UserAccessLogVo userAccessLog) {
    User userEntity = userRepository.findOne(userAccessLog.getUserId());
    
    UserAccessLog logEntity = new UserAccessLog();
    logEntity.setEmail(userEntity.getEmail());
    logEntity.setName(userEntity.getName());
    logEntity.setTel(userEntity.getTel());
    logEntity.setUserGrpCd(userEntity.getUserGrpCd());
    logEntity.setUserId(userEntity.getId());
    
    userAccessLogRepository.save(logEntity);
    return 1;
  }

}
