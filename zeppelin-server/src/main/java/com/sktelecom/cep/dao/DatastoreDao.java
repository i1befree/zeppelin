package com.sktelecom.cep.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sktelecom.cep.vo.Datastore;

/**
 * dataStore관리 - dataStore CRUD 담당 Dao.
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/DatastoreMapper.xml 과 1:1
 * 매핑한다.
 *
 * @author 박상민
 */
@Repository
public interface DatastoreDao {

  static final Logger LOG = LoggerFactory.getLogger(DatastoreDao.class);

  /**
   * dataStore 생성.
   * 
   * @param dataStore
   * @return
   */
  int create(Datastore dataStore);

  /**
   * dataStore 수정.
   * 
   * @param dataStore
   * @return
   */
  int update(Datastore dataStore);

  /**
   * dataStore 삭제.
   * 
   * @param dataStore
   * @return
   */
  int delete(Datastore dataStore);

  /**
   * dataStore 정보 조회.
   * 
   * @param dataStore
   * @return
   */
  Datastore getInfo(Datastore dataStore);

  /**
   * dataStore 목록 조회.
   * 
   * @param dataStore
   * @return
   */
  List<Datastore> getList(Datastore dataStore);

}
