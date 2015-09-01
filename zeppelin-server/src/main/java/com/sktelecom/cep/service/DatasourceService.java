package com.sktelecom.cep.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.Datasource;

/**
 * datasource관리 - datasource CRUD 담당 Service.
 *
 * @author 박상민
 */
public interface DatasourceService {

  static final Logger LOG = LoggerFactory.getLogger(DatasourceService.class);

  /**
   * datasource 생성.
   * 
   * @param datasource
   * @return
   */
  int create(Datasource datasource);

  /**
   * datasource 목록 조회.
   * 
   * @param datasource
   * @return
   */
  List<Datasource> getList(Datasource datasource);


}
