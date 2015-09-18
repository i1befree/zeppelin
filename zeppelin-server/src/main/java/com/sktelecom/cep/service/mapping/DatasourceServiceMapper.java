package com.sktelecom.cep.service.mapping;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
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

  /**
   * ModelMapper : bean to bean mapping library.
   */
  private ModelMapper modelMapper;

  /**
   * Constructor.
   */
  public DatasourceServiceMapper() {
    modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  @Override
  protected ModelMapper getModelMapper() {
    return modelMapper;
  }

  protected void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  /**
   * DataSource Entity 로 부터 DatasourceVo (DatastoreVo 포함) 로 매핑한다.
   * @param datasource
   * @return
   */
  public DatasourceVo getDatasourceVoWithAssignedWorkspaceFromEntity(DataSource datasource) {
    DatasourceVo datasourceVo = mapEntityToVo(datasource, DatasourceVo.class);
    
    List<WorkspaceAssign> assignList = datasource.getWorkspaceAssigns();
    for (WorkspaceAssign assign : assignList) {
      datasourceVo.getWorkspaces().add(mapEntityToVo(assign.getWorkspace(), WorkspaceVo.class));
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
      DatasourceVo datasourceVo = mapEntityToVo(datasource, DatasourceVo.class);
      datasourceVo.setDatastore(mapEntityToVo(datasource.getDataStore(), DatastoreVo.class));
      list.add(datasourceVo);
    }
    return list;
  }

}

