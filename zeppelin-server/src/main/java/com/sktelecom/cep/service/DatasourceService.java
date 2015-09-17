package com.sktelecom.cep.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.Datasource;
import com.sktelecom.cep.vo.DatasourceVo;
import com.sktelecom.cep.vo.Datastore;
import com.sktelecom.cep.vo.LayoutSchema;
import com.sktelecom.cep.vo.WorkspaceObject;

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
  void create(DatasourceVo datasource);

  /**
   * datasource 목록 조회.
   * 
   * @param datasource
   * @return
   */
  List<Datasource> getList(Datasource datasource);

  /**
   * datasource 를 작업공간에 할당하기
   * @param workspaceObject
   * @return
   */
  int saveAssignWorkspace(WorkspaceObject workspaceObject);

  /**
   * dataSourceVo 정보 조회 (할당 작업공간 목록 포함)
   * @param dataSourceVo
   * @return
   */
  DatasourceVo getDatasourceObjectInfo(DatasourceVo datasourceVo);

  /**
   * store 별로 스키마, 테이블, 컬럼 정보들을 가져온다.
   * @param datasource
   * @return
   */
  List<LayoutSchema> loadDatasourceMetadata(Datasource datasource);

  /**
   * 데이타 스토어 목록을 가져온다.
   * @param dataStore
   * @return
   */
  List<Datastore> getDatastoreAllList(Datastore datastore);

}
