package com.sktelecom.cep.service.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.sktelecom.cep.entity.DataStore;
import com.sktelecom.cep.entity.DataStoreProperty;
import com.sktelecom.cep.vo.DatastorePropertyVo;
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
        skip().setDataSources(null);
      }
    };
    modelMapper.addMappings(datastoreMap);
    
    PropertyMap<DataStoreProperty, DatastorePropertyVo> datastorePropsMap = new PropertyMap<DataStoreProperty, DatastorePropertyVo>() {
      @Override
      protected void configure() {
        skip().setDataStore(null);
      }
    };
    modelMapper.addMappings(datastorePropsMap);
  }

  public List<DatastoreVo> getDatastoreVoWithDatastorePropsFromEntity(List<DataStore> datastoreList) {
    List<DatastoreVo> list = new ArrayList<DatastoreVo>(); 
    
    for (DataStore dataStore : datastoreList) {
      DatastoreVo datastoreVo = this.mapEntityToVo(dataStore, DatastoreVo.class);
      datastoreVo.setProperties(getDataStorePropertyVoFromEntity(dataStore.getProperties()));
      list.add(datastoreVo);
    }
    return list;
  }

  public Map<String, DatastorePropertyVo> getDataStorePropertyVoFromEntity(List<DataStoreProperty> dataStorePropertyList) {
    Map<String, DatastorePropertyVo> map = new HashMap<String, DatastorePropertyVo>(); 
    
    for (DataStoreProperty dataStoreProperty : dataStorePropertyList) {
      DatastorePropertyVo dataStorePropertyVo = this.mapEntityToVo(dataStoreProperty, DatastorePropertyVo.class);
      map.put(dataStorePropertyVo.getName(), dataStorePropertyVo);
    }
    return map;
  }
}

