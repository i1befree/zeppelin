package com.sktelecom.cep.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.sktelecom.cep.entity.DataStore;
import com.sktelecom.cep.entity.DataStoreProperty;
import com.sktelecom.cep.vo.DataStorePropertyVo;
import com.sktelecom.cep.vo.DatastoreVo;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class DatastoreServiceMapper extends AbstractServiceMapper {

  /**
   * Constructor.
   */
  public DatastoreServiceMapper() {
    modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    
    PropertyMap<DataStore, DatastoreVo> datastoreMap = new PropertyMap<DataStore, DatastoreVo>() {
      @Override
      protected void configure() {
        skip().setUpdator(null);
        skip().setProperties(null);
      }
    };
    modelMapper.addMappings(datastoreMap);
    
    PropertyMap<DataStoreProperty, DataStorePropertyVo> datastorePropertyMap = new PropertyMap<DataStoreProperty, DataStorePropertyVo>() {
      @Override
      protected void configure() {
        skip().setDataStore(null);
      }
    };
    modelMapper.addMappings(datastorePropertyMap);
  }

}

