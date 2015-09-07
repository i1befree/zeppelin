package com.sktelecom.cep.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.cep.dao.DatasourceDao;
import com.sktelecom.cep.dao.NotebookDao;
import com.sktelecom.cep.dao.WorkspaceDao;
import com.sktelecom.cep.vo.Datasource;
import com.sktelecom.cep.vo.Notebook;
import com.sktelecom.cep.vo.User;
import com.sktelecom.cep.vo.Workspace;
import com.sktelecom.cep.vo.WorkspaceMember;
import com.sktelecom.cep.vo.WorkspaceShare;
import com.sktelecom.cep.vo.WorkspaceSummary;

/**
 * 사용자관리 - 사용자 CRUD 담당 Service 구현체.
 *
 * @author 박상민
 */
@Service
public class WorkspaceServiceImpl implements WorkspaceService {

  static final Logger LOG = LoggerFactory.getLogger(WorkspaceServiceImpl.class);

  @Inject
  private WorkspaceDao workspaceDao;
  
  @Inject
  private NotebookDao notebookDao;
  
  @Inject
  private DatasourceDao datasourceDao;

  @Override
  public int create(Workspace workspace) {
    int resultInt = workspaceDao.create(workspace);
    return resultInt;
  }

  @Override
  public int update(Workspace workspace) {
    int resultInt = workspaceDao.update(workspace);
    return resultInt;
  }

  @Override
  public int delete(Workspace workspace) {
    int resultInt = workspaceDao.delete(workspace);
    return resultInt;
  }

  @Override
  public Workspace getInfo(Workspace workspace) {
    Workspace WorkspaceInfo = workspaceDao.getInfo(workspace);
    return WorkspaceInfo;
  }

  @Override
  public List<Workspace> getList(Workspace workspace) {
    List<Workspace> WorkspaceList = new ArrayList<Workspace>();
    workspace.setWrkspcType("PERSONAL");
    List<Workspace> personalList = workspaceDao.getListByType(workspace);
    WorkspaceList.addAll(personalList);
    
    workspace.setWrkspcType("SHARED");
    List<Workspace> sharedList = workspaceDao.getListByType(workspace);
    WorkspaceList.addAll(sharedList);
    
    return WorkspaceList;
  }
  
  @Override
  public List<Workspace> getListByUserId(String userId) {
    WorkspaceShare workspaceShare = new WorkspaceShare();
    workspaceShare.setUserId(userId);
    
    List<Workspace> workspaceList = new ArrayList<Workspace>();
    workspaceShare.setWrkspcType("PERSONAL");
    List<Workspace> personalList = workspaceDao.getListByTypeUserId(workspaceShare);
    if(personalList != null && personalList.size() > 0) {
      Workspace personalRoot = new Workspace();
      personalRoot.setpId("ROOT");
      personalRoot.setWrkspcId("PERSONAL");
      personalRoot.setWrkspcName("Personal");
      personalRoot.setWrkspcType("PERSONAL");
      workspaceList.add(personalRoot);
    }
    workspaceList.addAll(personalList);
    
    workspaceShare.setWrkspcType("SHARED");
    List<Workspace> sharedList = workspaceDao.getListByTypeUserId(workspaceShare);
    if(sharedList != null && sharedList.size() > 0) {
      Workspace sharedRoot = new Workspace();
      sharedRoot.setpId("ROOT");
      sharedRoot.setWrkspcId("SHARED");
      sharedRoot.setWrkspcName("Shared");
      sharedRoot.setWrkspcType("SHARED");
      workspaceList.add(sharedRoot);
    }
    workspaceList.addAll(sharedList);
    
    return workspaceList;
  }

  @Override
  public List<Notebook> getNotebookList(Workspace workspace) {
    List<Notebook> notebookList = notebookDao.getListByWorkspaceId(workspace);
    return notebookList;
  }

  @Override
  public List<Notebook> getLastestNotebookListByUserId(String userId) {
    User pUser = new User();
    pUser.setId(userId);
    List<Notebook> notebookList = notebookDao.getLastestNotebookListByUserId(pUser);
    return notebookList;
  }

  @Override
  public WorkspaceSummary getWorkspaceSummaryInfo(Workspace workspace) {
    WorkspaceSummary info = workspaceDao.getWorkspaceSummaryInfo(workspace);
    return info;
  }

  @Override
  public List<WorkspaceMember> getWorkspaceMemberList(Workspace workspace) {
    List<WorkspaceMember> list = workspaceDao.getWorkspaceMemberList(workspace);
    return list;
  }

  @Override
  public int insertMembers(List<WorkspaceShare> wsList) {
    int resultInt = 0;
    for (WorkspaceShare item : wsList) {
      resultInt += workspaceDao.insertMembers(item);
    }
    return resultInt;
  }

  @Override
  public int deleteMembers(List<WorkspaceShare> wsList) {
    int resultInt = 0;
    for (WorkspaceShare item : wsList) {
      resultInt += workspaceDao.deleteMembers(item);
    }
    return resultInt;
  }

  @Override
  public List<Datasource> getDatasourceList(Workspace workspace) {
    List<Datasource> list = datasourceDao.getListByWrkspcIid(workspace);
    return list;
  }
}
