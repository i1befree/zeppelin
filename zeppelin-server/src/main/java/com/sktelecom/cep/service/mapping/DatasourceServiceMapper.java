package com.sktelecom.cep.service.mapping;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.sktelecom.cep.entity.DataSource;
import com.sktelecom.cep.entity.WorkspaceAssign;
import com.sktelecom.cep.vo.DatasourceVo;
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

  public DatasourceVo getDatasourceVoWithAssignedWorkspaceFromEntity(DataSource datasource) {
    DatasourceVo datasourceVo = mapEntityToVo(datasource, DatasourceVo.class);
    
    List<WorkspaceAssign> assignList = datasource.getWorkspaceAssigns();
    for (WorkspaceAssign assign : assignList) {
      datasourceVo.getWorkspaces().add(mapEntityToVo(assign.getWorkspace(), WorkspaceVo.class));
    }
    return datasourceVo;
  }

}

