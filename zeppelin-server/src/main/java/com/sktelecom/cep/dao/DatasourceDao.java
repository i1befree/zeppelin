package com.sktelecom.cep.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.Datasource;

/**
 * datasource관리 - datasource CRUD 담당 Dao.
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/DatasourceMapper.xml 과 1:1
 * 매핑한다.
 *
 * @author 박상민
 */
public interface DatasourceDao {

  static final Logger LOG = LoggerFactory.getLogger(DatasourceDao.class);

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
