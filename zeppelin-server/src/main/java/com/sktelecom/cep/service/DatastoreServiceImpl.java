package com.sktelecom.cep.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.cep.entity.DataStore;
import com.sktelecom.cep.entity.DataStoreProperty;
import com.sktelecom.cep.entity.User;
import com.sktelecom.cep.repository.DataStorePropsRepository;
import com.sktelecom.cep.repository.DataStoreRepository;
import com.sktelecom.cep.service.mapping.DatastoreServiceMapper;
import com.sktelecom.cep.service.mapping.UserServiceMapper;
import com.sktelecom.cep.vo.DatastorePropertyVo;
import com.sktelecom.cep.vo.DatastoreVo;

/**
 * 데이타소스 - 데이타소스 CRUD 담당 Service 구현체.
 *
 * @author 박상민
 */
@Service
public class DatastoreServiceImpl implements DatastoreService {

  static final Logger LOG = LoggerFactory.getLogger(DatastoreServiceImpl.class);
    
  @Inject
  private DataStoreRepository dataStoreRepository;
  
  @Inject
  private DataStorePropsRepository dataStorePropsRepository;
  
  @Inject
  private DatastoreServiceMapper datastoreServiceMapper;
  
  @Inject
  private UserServiceMapper userServiceMapper;
  
  @Override
  public void create(DatastoreVo datastore) {
    DataStore dataStoreEntity = new DataStore();
    datastoreServiceMapper.mapVoToEntity(datastore, dataStoreEntity);
    
    User userEntity = new User();
    userServiceMapper.mapVoToEntity(datastore.getUpdator(), userEntity);
    dataStoreEntity.setUpdator(userEntity);
    
    dataStoreRepository.save(dataStoreEntity);
    
    Map<String, DatastorePropertyVo> map = datastore.getProperties();
    for (String key : map.keySet()) {
      DataStoreProperty dataStoreProperty = new DataStoreProperty();
      dataStoreProperty.setDataStore(dataStoreEntity);
      dataStoreProperty.setName(key);
      dataStoreProperty.setValue(map.get(key).getValue());
      dataStorePropsRepository.save(dataStoreProperty);
    }    
  }

  @Override
  public void update(DatastoreVo datastore) {
    DataStore dataStoreEntity = dataStoreRepository.findOne(datastore.getId());
    datastoreServiceMapper.mapVoToEntity(datastore, dataStoreEntity);

    User userEntity = new User();
    userServiceMapper.mapVoToEntity(datastore.getUpdator(), userEntity);
    dataStoreEntity.setUpdator(userEntity);
    
    dataStoreRepository.save(dataStoreEntity);
    dataStorePropsRepository.delete(dataStoreEntity.getProperties());
    
    Map<String, DatastorePropertyVo> map = datastore.getProperties();
    for (String key : map.keySet()) {
      DataStoreProperty dataStoreProperty = new DataStoreProperty();
      dataStoreProperty.setDataStore(dataStoreEntity);
      dataStoreProperty.setName(key);
      dataStoreProperty.setValue(map.get(key).getValue());
      dataStorePropsRepository.save(dataStoreProperty);
    }  
  }

  @Override
  public void delete(DatastoreVo datastore) {
    DataStore dataStoreEntity = dataStoreRepository.findOne(datastore.getId());
    dataStorePropsRepository.delete(dataStoreEntity.getProperties());
    dataStoreRepository.delete(dataStoreEntity);
  }

  @Override
  public List<DatastoreVo> getList(DatastoreVo datastore) {
    List<DataStore> datastoreList = dataStoreRepository.findAll();

    //convert from entity to vo 
    return datastoreServiceMapper.getDatastoreVoWithDatastorePropsFromEntity(datastoreList);
  }

}
