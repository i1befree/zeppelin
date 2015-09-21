package com.sktelecom.cep.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.cep.entity.DataStore;
import com.sktelecom.cep.repository.DataStoreRepository;
import com.sktelecom.cep.service.mapping.DatastoreServiceMapper;
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
  private DatastoreServiceMapper datastoreServiceMapper;
  
  @Override
  public void create(DatastoreVo datastore) {
    //dataStoreRepository.save(entity)
  }

  @Override
  public void update(DatastoreVo datastore) {
    // dataStoreRepository.save(entity)
    
  }

  @Override
  public void delete(DatastoreVo datastore) {
    //dataStoreRepository.delete(id);
    
  }

  @Override
  public List<DatastoreVo> getList(DatastoreVo datastore) {
    List<DataStore> datastoreList = dataStoreRepository.findAll();

    //convert from entity to vo 
    return datastoreServiceMapper.getDatastoreVoWithDatastorePropsFromEntity(datastoreList);
  }

}
