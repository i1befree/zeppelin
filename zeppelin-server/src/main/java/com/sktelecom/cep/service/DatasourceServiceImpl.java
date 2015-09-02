package com.sktelecom.cep.service;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.cep.dao.DatasourceDao;
import com.sktelecom.cep.dao.WorkspaceAssignDao;
import com.sktelecom.cep.dao.WorkspaceDao;
import com.sktelecom.cep.dao.WorkspaceObjectDao;
import com.sktelecom.cep.vo.Datasource;
import com.sktelecom.cep.vo.Workspace;
import com.sktelecom.cep.vo.WorkspaceAssign;
import com.sktelecom.cep.vo.WorkspaceObject;

/**
 * 데이타소스 - 데이타소스 CRUD 담당 Service 구현체.
 *
 * @author 박상민
 */
@Service
public class DatasourceServiceImpl implements DatasourceService {

  static final Logger LOG = LoggerFactory.getLogger(DatasourceServiceImpl.class);

  @Inject
  private DatasourceDao datasourceDao;
  
  @Inject
  private WorkspaceDao workspaceDao;
  
  @Inject
  private WorkspaceObjectDao workspaceObjectDao;
  
  @Inject
  private WorkspaceAssignDao workspaceAssignDao;
  
  @Override
  public int create(Datasource datasource) {
    String wrkspcObjId = UUID.randomUUID().toString();
    
    WorkspaceObject workspaceObject = new WorkspaceObject();
    workspaceObject.setWrkspcObjId(wrkspcObjId);
    workspaceObject.setWrkspcObjType("DATSRC");
    workspaceObject.setShareType("NONE");
    workspaceObject.setObjStatus("CREATED");
    workspaceObject.setCreateUserId(datasource.getUpdateUserId());
    workspaceObject.setOwnUserId(workspaceObject.getCreateUserId());
    workspaceObjectDao.create(workspaceObject);
    
    datasource.setDatasourceId(wrkspcObjId);
    int resultInt = datasourceDao.create(datasource);
    return resultInt;
  }

  @Override
  public List<Datasource> getList(Datasource datasource) {
    List<Datasource> datasourceList = datasourceDao.getList(datasource);
    return datasourceList;
  }

  @Override
  public List<Workspace> getWorkspaceList(Workspace workspace) {
    List<Workspace> list = workspaceDao.getList(workspace);
    return list;
  }

  @Override
  public int saveAssignWorkspace(WorkspaceObject workspaceObject) {
    if ("ALL".equals(workspaceObject.getShareType())) {
      //remove all
      //update sharetype
      WorkspaceAssign workspaceAssign = new WorkspaceAssign();
      workspaceAssign.setWrkspcObjId(workspaceObject.getWrkspcObjId());
      workspaceAssignDao.deleteByWrkspcObjId(workspaceAssign);
      
      //데이타소스를 모든 갖업공간에 할당한다.
      workspaceObjectDao.updateForShareType(workspaceObject);
    } else {
      //remove all
      //add
      WorkspaceAssign workspaceAssign = new WorkspaceAssign();
      workspaceAssign.setWrkspcObjId(workspaceObject.getWrkspcObjId());
      workspaceAssignDao.deleteByWrkspcObjId(workspaceAssign);
      
      for (WorkspaceAssign info : workspaceObject.getWorkspaceAssigns()) {
        info.setWrkspcObjId(workspaceObject.getWrkspcObjId());
        workspaceAssignDao.create(info);
      }
      //데이타소스를 모든 갖업공간에 할당한다.
      workspaceObject.setShareType("NONE");
      workspaceObjectDao.updateForShareType(workspaceObject);
    }
    return 1;
  }

  @Override
  public List<Workspace> getAssignedWorkspaceList(WorkspaceAssign workspaceAssign) {
    List<Workspace> list = workspaceDao.getAssignedWorkspaceList(workspaceAssign);
    return list;
  }

  @Override
  public WorkspaceObject getWorkspaceObjectInfo(WorkspaceObject workspaceObject) {
    WorkspaceObject info = workspaceObjectDao.getInfo(workspaceObject);
    return info;
  }
  
}
