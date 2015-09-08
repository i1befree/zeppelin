package com.sktelecom.cep.dao;

import com.sktelecom.cep.vo.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Datastore관리 - Datastore CRUD 담당 Dao.
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
   * Datastore 생성.
   * 
   * @param Datastore
   * @return
   */
  int create(Datastore datastore);

  /**
   * Datastore 수정.
   * 
   * @param Datastore
   * @return
   */
  int update(Datastore datastore);

  /**
   * Datastore 삭제.
   * 
   * @param Datastore
   * @return
   */
  int delete(Datastore datastore);

  /**
   * Datastore 정보 조회.
   * 
   * @param Datastore
   * @return
   */
  Datastore getInfo(Datastore datastore);

  /**
   * Datastore 목록 조회.
   * 
   * @param Datastore
   * @return
   */
  List<Datastore> getList(Datastore datastore);

}
