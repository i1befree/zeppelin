package com.sktelecom.cep.service.mapping;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.sktelecom.cep.entity.DataSource;
import com.sktelecom.cep.vo.DatasourceVo;

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

  /**
   * Mapping from entity to vo
   * 
   * @param DataSourceEntity
   */
  public DatasourceVo mapEntityToVo(DataSource entity) {
    if (entity == null) {
      return null;
    }

    // --- Generic mapping
    DatasourceVo vo = map(entity, DatasourceVo.class);
    return vo;
  }

  /**
   * List Mapping from entity to vo
   * 
   * @param list
   * @return
   */
  public List<DatasourceVo> mapListEntityToVo(List<DataSource> list) {
    List<DatasourceVo> voList = new ArrayList<DatasourceVo>();
    for (com.sktelecom.cep.entity.DataSource entity : list) {
      voList.add(mapEntityToVo(entity));
    }
    return voList;
  }

  /**
   * Mapping from vo to entity
   * 
   * @param DataSource
   * @param DataSourceEntity
   */
  public void mapVoToEntity(DatasourceVo vo, DataSource entity) {
    if (vo == null) {
      return;
    }
    // --- Generic mapping
    map(vo, entity);
  }

  @Override
  protected ModelMapper getModelMapper() {
    return modelMapper;
  }

  protected void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

}

