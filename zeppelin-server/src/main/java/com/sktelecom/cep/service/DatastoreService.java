package com.sktelecom.cep.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.DatastoreVo;

/**
 * datastore관리 - datastore CRUD 담당 Service.
 *
 * @author 박상민
 */
public interface DatastoreService {

  static final Logger LOG = LoggerFactory.getLogger(DatastoreService.class);

  /**
   * datastore 생성.
   * 
   * @param datastore
   * @return
   */
  void create(DatastoreVo datastore);

  /**
   * datastore 수정
   * @param datastore
   * @return
   */
  void update(DatastoreVo datastore);

  /**
   * datastore 삭제
   * @param datastore
   * @return
   */
  void delete(DatastoreVo datastore);

  /**
   * datastore 목록 조회.
   * 
   * @param datastore
   * @return
   */
  List<DatastoreVo> getList(DatastoreVo datastore);



}
