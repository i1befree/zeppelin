package com.sktelecom.cep.service.mapping;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.sktelecom.cep.common.CommCode;
import com.sktelecom.cep.entity.DataSource;
import com.sktelecom.cep.entity.Notebook;
import com.sktelecom.cep.entity.Workspace;
import com.sktelecom.cep.entity.WorkspaceAssign;
import com.sktelecom.cep.entity.WorkspaceObject;
import com.sktelecom.cep.entity.WorkspaceShare;
import com.sktelecom.cep.vo.DatasourceVo;
import com.sktelecom.cep.vo.DatastoreVo;
import com.sktelecom.cep.vo.NotebookVo;
import com.sktelecom.cep.vo.UserVo;
import com.sktelecom.cep.vo.WorkspaceSummaryVo;
import com.sktelecom.cep.vo.WorkspaceVo;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class WorkspaceServiceMapper extends AbstractServiceMapper {

  @Inject
  private DatasourceServiceMapper datasourceMapper;
  
  @Inject
  private DatastoreServiceMapper datastoreMapper;
  
  @Inject
  private NotebookServiceMapper notebookMapper;
  
  @Inject
  private UserServiceMapper userMapper;
  
  /**
   * Constructor.
   */
  public WorkspaceServiceMapper() {
    modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    
    PropertyMap<Workspace, WorkspaceVo> workspaceMap = new PropertyMap<Workspace, WorkspaceVo>() {
      @Override
      protected void configure() {
        skip().setDatasources(null);
        skip().setNotebooks(null);
        skip().setUsers(null);
      }
    };
    modelMapper.addMappings(workspaceMap);
  }

  /**
   * List Mapping from entity to vo.
   * workspace 에 notebook list, datasource list 를 포함한다.
   * 
   * @param list
   * @return workspace 
   *             - datasource
   *                 - creator         
   */
  public WorkspaceVo mapWorkspaceVoFromEntity(Workspace workspace) {
    WorkspaceVo workspaceVo = this.mapEntityToVo(workspace, WorkspaceVo.class);
    
    List<WorkspaceAssign> assignList = workspace.getWorkspaceAssigns();
    for (WorkspaceAssign assign : assignList) {
      WorkspaceObject workspaceObjectEntity = assign.getWorkspaceObject();
      if (workspaceObjectEntity.getWrkspcObjType() == CommCode.WorkspaceObjectType.DATSRC) {
        DataSource datasource = (DataSource) workspaceObjectEntity.getTarget();
        
        DatasourceVo dsvo = datasourceMapper.mapEntityToVo(datasource, DatasourceVo.class);
        dsvo.setDatastore(datastoreMapper.mapEntityToVo(datasource.getDataStore(), DatastoreVo.class));
        workspaceVo.getDatasources().add(dsvo);
        
      } else if (workspaceObjectEntity.getWrkspcObjType() == CommCode.WorkspaceObjectType.NOTEBOOK) {
        NotebookVo notebookVo = notebookMapper.mapEntityToVo(workspaceObjectEntity.getTarget(), NotebookVo.class);
        notebookVo.setCreator(userMapper.mapEntityToVo(workspaceObjectEntity.getCreator(), UserVo.class));
        workspaceVo.getNotebooks().add(notebookVo);
      }
    }
    return workspaceVo;
  }

  /**
   * workspace 에서 datasource 목록만 추출한다.
   * @param workspace
   * @return
   */
  public List<DatasourceVo> mapListDatasourceVoFromEntity(Workspace workspace) {
    List<DatasourceVo> list = new ArrayList<DatasourceVo>();
    
    List<WorkspaceAssign> assignList = workspace.getWorkspaceAssigns();
    for (WorkspaceAssign assign : assignList) {
      WorkspaceObject workspaceObjectEntity = assign.getWorkspaceObject();
      if (workspaceObjectEntity.getWrkspcObjType() == CommCode.WorkspaceObjectType.DATSRC) {
        DataSource datasource = (DataSource) workspaceObjectEntity.getTarget();
        
        DatasourceVo dsvo = datasourceMapper.mapEntityToVo(datasource, DatasourceVo.class);
        dsvo.setDatastore(datastoreMapper.mapEntityToVo(datasource.getDataStore(), DatastoreVo.class));
        
        list.add(dsvo);
      }
    }
    return list;
  }

  public WorkspaceSummaryVo getWorkspaceSummaryVoFromEntity(Workspace workspace) {
    WorkspaceSummaryVo workspaceSummary = this.mapEntityToVo(workspace, WorkspaceSummaryVo.class);
    workspaceSummary.setTotalOfMembers(workspace.getWorkspaceShares().size());
    
    List<WorkspaceAssign> assignList = workspace.getWorkspaceAssigns();
    for (WorkspaceAssign assign : assignList) {
      WorkspaceObject workspaceObjectEntity = assign.getWorkspaceObject();
      if (workspaceObjectEntity.getWrkspcObjType() == CommCode.WorkspaceObjectType.DATSRC) {
        workspaceSummary.setTotalOfDatasource(workspaceSummary.getTotalOfDatasource() + 1);
        
      } else if (workspaceObjectEntity.getWrkspcObjType() == CommCode.WorkspaceObjectType.NOTEBOOK) {
        workspaceSummary.setTotalOfNotebooks(workspaceSummary.getTotalOfNotebooks() + 1);
        
      }
    }
    return workspaceSummary;
  }

  public List<WorkspaceVo> getListWorkspaceVoFromEntity(List<WorkspaceShare> shareList) {
    List<WorkspaceVo> list = new ArrayList<WorkspaceVo>();
    List<WorkspaceVo> personalList = new ArrayList<WorkspaceVo>();
    List<WorkspaceVo> sharedList = new ArrayList<WorkspaceVo>();
    
    for (WorkspaceShare share : shareList) {
      Workspace workspace = share.getWorkspace();
      WorkspaceVo workspaceVo = this.mapEntityToVo(workspace, WorkspaceVo.class);
      if (workspace.getWrkspcType() == CommCode.WorkspaceType.PERSONAL) {
        personalList.add(workspaceVo);
      } else if (workspace.getWrkspcType() == CommCode.WorkspaceType.SHARE) {
        sharedList.add(workspaceVo);
      }
    }
    if (personalList != null && personalList.size() > 0) {
      WorkspaceVo personalRoot = new WorkspaceVo();
      personalRoot.setpId("ROOT");
      personalRoot.setWrkspcId("PERSONAL");
      personalRoot.setWrkspcName("Personal");
      personalRoot.setWrkspcType(CommCode.WorkspaceType.PERSONAL);
      personalList.add(personalRoot);
    }
    if (sharedList != null && sharedList.size() > 0) {
      WorkspaceVo sharedRoot = new WorkspaceVo();
      sharedRoot.setpId("ROOT");
      sharedRoot.setWrkspcId("SHARE");
      sharedRoot.setWrkspcName("Share");
      sharedRoot.setWrkspcType(CommCode.WorkspaceType.SHARE);
      sharedList.add(sharedRoot);
    }
    list.addAll(personalList);
    list.addAll(sharedList);
    return list;
  }

  public List<NotebookVo> mapListNotebookVoFromEntity(com.sktelecom.cep.entity.Workspace workspace) {
    List<NotebookVo> list = new ArrayList<NotebookVo>();
    
    List<WorkspaceAssign> assignList = workspace.getWorkspaceAssigns();
    for (WorkspaceAssign assign : assignList) {
      WorkspaceObject workspaceObjectEntity = assign.getWorkspaceObject();
      if (workspaceObjectEntity.getWrkspcObjType() == CommCode.WorkspaceObjectType.NOTEBOOK) {
        Notebook notebook = (Notebook) workspaceObjectEntity.getTarget();
        
        list.add(notebookMapper.mapEntityToVo(notebook, NotebookVo.class));
      }
    }
    return list;
  }
}

