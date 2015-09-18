package com.sktelecom.cep.service.mapping;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.sktelecom.cep.entity.DataSource;
import com.sktelecom.cep.entity.WorkspaceAssign;
import com.sktelecom.cep.vo.DatasourceVo;
import com.sktelecom.cep.vo.DatastoreVo;
import com.sktelecom.cep.vo.WorkspaceVo;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class DatasourceServiceMapper extends AbstractServiceMapper {

  @Inject 
  private DatastoreServiceMapper datastoreMapper;
  
  @Inject 
  private WorkspaceServiceMapper workspaceMapper;
  
  /**
   * Constructor.
   */
  public DatasourceServiceMapper() {
    modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    PropertyMap<DataSource, DatasourceVo> datasourceMap = new PropertyMap<DataSource, DatasourceVo>() {
      @Override
      protected void configure() {
        skip().setCreator(null);
        skip().setOwner(null);
        skip().setWorkspaceAssigns(null);
        skip().setDatastore(null);
        skip().setLastModifiedUser(null);
        skip().setWorkspaces(null);
      }
    };
    modelMapper.addMappings(datasourceMap);
  }

  /**
   * DataSource Entity 로 부터 DatasourceVo (DatastoreVo 포함) 로 매핑한다.
   * @param datasource
   * @return
   */
  public DatasourceVo getDatasourceVoWithAssignedWorkspaceFromEntity(DataSource datasource) {
    DatasourceVo datasourceVo = this.mapEntityToVo(datasource, DatasourceVo.class);
    datasourceVo.setDatastore(datastoreMapper.mapEntityToVo(datasource.getDataStore(), DatastoreVo.class));
    
    List<WorkspaceAssign> assignList = datasource.getWorkspaceAssigns();
    for (WorkspaceAssign assign : assignList) {
      datasourceVo.getWorkspaces().add(workspaceMapper.mapEntityToVo(assign.getWorkspace(), WorkspaceVo.class));
    }
    return datasourceVo;
  }

  /**
   * DataSource Entity 로 부터 DatasourceVo (Assigned WorkspaceVo 포함) 로 매핑한다.
   * @param datasourceList
   * @return
   */
  public List<DatasourceVo> getDatasourceVoWithDatastoreFromEntity(List<DataSource> datasourceList) {
    List<DatasourceVo> list = new ArrayList<DatasourceVo>(); 
    
    for (DataSource datasource : datasourceList) {
      DatasourceVo datasourceVo = this.mapEntityToVo(datasource, DatasourceVo.class);
      datasourceVo.setDatastore(datastoreMapper.mapEntityToVo(datasource.getDataStore(), DatastoreVo.class));
      list.add(datasourceVo);
    }
    return list;
  }

}

