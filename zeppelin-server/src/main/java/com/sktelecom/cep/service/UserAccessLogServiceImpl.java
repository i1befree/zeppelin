package com.sktelecom.cep.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.cep.dao.UserAccessLogDao;
import com.sktelecom.cep.vo.UserAccessLog;

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
  private UserAccessLogDao userAccessLogDao;

  @Override
  public int create(UserAccessLog userAccessLog) {
    int resultInt = userAccessLogDao.create(userAccessLog);
    return resultInt;
  }

  @Override
  public List<UserAccessLog> getList(UserAccessLog userAccessLog) {
    if (userAccessLog.getQuery() != null
        && !"".equals(userAccessLog.getQuery())) {
      userAccessLog.setQuery(userAccessLog.getQuery() + "%");
    }

    List<UserAccessLog> userList = new ArrayList<UserAccessLog>();
    long totalCount = userAccessLogDao.getListCount(userAccessLog);
    if (totalCount > 0) {
      userList = userAccessLogDao.getList(userAccessLog);
      if (userList != null && userList.size() > 0) {
        userList.get(0).setTotalCount(totalCount);
      }
    }
    return userList;
  }

}
