package com.sktelecom.cep.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.cep.dao.WorkspaceDao;
import com.sktelecom.cep.vo.Workspace;

/**
 * 사용자관리 - 사용자 CRUD 담당 Service 구현체.
 *
 * @author 박상민
 */
@Service
public class WorkspaceServiceImpl implements WorkspaceService {

  static final Logger LOG = LoggerFactory.getLogger(WorkspaceServiceImpl.class);

  @Inject
  private WorkspaceDao WorkspaceDao;

  @Override
  public int create(Workspace workspace) {
    int resultInt = WorkspaceDao.create(workspace);
    return resultInt;
  }

  @Override
  public int update(Workspace workspace) {
    int resultInt = WorkspaceDao.update(workspace);
    return resultInt;
  }

  @Override
  public int delete(Workspace workspace) {
    int resultInt = WorkspaceDao.delete(workspace);
    return resultInt;
  }

  @Override
  public Workspace getInfo(Workspace workspace) {
    Workspace WorkspaceInfo = WorkspaceDao.getInfo(workspace);
    return WorkspaceInfo;
  }

  @Override
  public List<Workspace> getList(Workspace workspace) {
    List<Workspace> WorkspaceList = new ArrayList<Workspace>();
    workspace.setType("P");
    List<Workspace> personalList = WorkspaceDao.getListByType(workspace);
    WorkspaceList.addAll(personalList);
    
    workspace.setType("S");
    List<Workspace> sharedList = WorkspaceDao.getListByType(workspace);
    WorkspaceList.addAll(sharedList);
    
    workspace.setType("G");
    List<Workspace> globalList = WorkspaceDao.getListByType(workspace);
    WorkspaceList.addAll(globalList);
    
    return WorkspaceList;
  }

}
