package com.sktelecom.cep.service.mapping;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.sktelecom.cep.common.CommCode;
import com.sktelecom.cep.entity.DataSource;
import com.sktelecom.cep.entity.Role;
import com.sktelecom.cep.entity.Workspace;
import com.sktelecom.cep.entity.WorkspaceAssign;
import com.sktelecom.cep.entity.WorkspaceObject;
import com.sktelecom.cep.entity.WorkspaceShare;
import com.sktelecom.cep.vo.DatasourceVo;
import com.sktelecom.cep.vo.DatastoreVo;
import com.sktelecom.cep.vo.NotebookVo;
import com.sktelecom.cep.vo.RoleVo;
import com.sktelecom.cep.vo.UserVo;
import com.sktelecom.cep.vo.WorkspaceSummary;
import com.sktelecom.cep.vo.WorkspaceVo;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class WorkspaceServiceMapper extends AbstractServiceMapper {

  /**
   * ModelMapper : bean to bean mapping library.
   */
  private ModelMapper modelMapper;

  /**
   * Constructor.
   */
  public WorkspaceServiceMapper() {
    modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  /**
   * List Mapping from entity to vo.
   * workspace 에 notebook list, datasource list 를 포함한다.
   * 
   * @param list
   * @return
   */
  public WorkspaceVo mapWorkspaceVoFromEntity(Workspace workspace) {
    WorkspaceVo workspaceVo = mapEntityToVo(workspace, WorkspaceVo.class);
    
    List<WorkspaceAssign> assignList = workspace.getWorkspaceAssigns();
    for (WorkspaceAssign assign : assignList) {
      WorkspaceObject workspaceObjectEntity = assign.getWorkspaceObject();
      if (workspaceObjectEntity.getWrkspcObjType() == CommCode.WorkspaceObjectType.DATSRC) {
        DataSource datasource = (DataSource) workspaceObjectEntity.getTarget();
        
        DatasourceVo dsvo = mapEntityToVo(datasource, DatasourceVo.class);
        dsvo.setDatastore(mapEntityToVo(datasource.getDataStore(), DatastoreVo.class));
        workspaceVo.getDatasources().add(dsvo);
        
      } else if (workspaceObjectEntity.getWrkspcObjType() == CommCode.WorkspaceObjectType.NOTEBOOK) {
        workspaceVo.getNotebooks().add(mapEntityToVo(workspaceObjectEntity.getTarget(), NotebookVo.class));
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
        
        DatasourceVo dsvo = mapEntityToVo(datasource, DatasourceVo.class);
        dsvo.setDatastore(mapEntityToVo(datasource.getDataStore(), DatastoreVo.class));
        
        list.add(dsvo);
      }
    }
    return list;
  }

  public WorkspaceSummary getWorkspaceSummaryVoFromEntity(Workspace workspace) {
    WorkspaceSummary workspaceSummary = mapEntityToVo(workspace, WorkspaceSummary.class);
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


  @Override
  protected ModelMapper getModelMapper() {
    return modelMapper;
  }

  protected void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }
}

