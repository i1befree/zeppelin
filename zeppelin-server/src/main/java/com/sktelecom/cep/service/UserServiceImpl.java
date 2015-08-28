package com.sktelecom.cep.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.cep.dao.UserDao;
import com.sktelecom.cep.dao.WorkspaceDao;
import com.sktelecom.cep.vo.Role;
import com.sktelecom.cep.vo.User;
import com.sktelecom.cep.vo.Workspace;

/**
 * 사용자관리 - 사용자 CRUD 담당 Service 구현체.
 *
 * @author 박상민
 */
@Service
public class UserServiceImpl implements UserService {

  static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

  @Inject
  private UserDao userDao;

  @Inject
  private WorkspaceDao workspaceDao;

  @Override
  public int create(User user) {
    
    String wrkspcId = UUID.randomUUID().toString();
    Workspace workspace = new Workspace();
    workspace.setWrkspcId(wrkspcId);
    workspace.setWrkspcName("PERSONAL");
    workspace.setDescription("Personal Workspace");
    workspace.setWrkspcType("PERSONAL");
    workspace.setAdminUserId(user.getId());
    workspace.setUpdateUserId(user.getId());
    workspaceDao.create(workspace);
    
    user.setWrkspcId(wrkspcId);
    int resultInt = userDao.create(user);
    //todo 사용자 계정 생성시, workspace personal default 하나 생성
    return resultInt;
  }

  @Override
  public int update(User user) {
    int resultInt = userDao.update(user);
    return resultInt;
  }

  @Override
  public int updateByManager(User user) {
    int resultInt = userDao.updateByManager(user);
    return resultInt;
  }

  @Override
  public int delete(User user) {
    int resultInt = userDao.delete(user);
    return resultInt;
  }

  @Override
  public User getInfo(User user) {
    User userInfo = userDao.getInfo(user);
    return userInfo;
  }

  @Override
  public User getCheckLoginUserInfo(User user) {
    User userInfo = userDao.getCheckLoginUserInfo(user);
    return userInfo;
  }

  @Override
  public List<User> getList(User user) {
    if (user.getQuery() != null && !"".equals(user.getQuery())) {
      user.setQuery(user.getQuery() + "%");
    }

    List<User> userList = new ArrayList<User>();
    long totalCount = userDao.getListCount(user);
    if (totalCount > 0) {
      userList = userDao.getList(user);
      if (userList != null && userList.size() > 0) {
        userList.get(0).setTotalCount(totalCount);
      }
    }
    return userList;
  }

  @Override
  public List<Role> getRole() {
    return this.userDao.getRole();
  }

}
