package com.sktelecom.cep.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.Datasource;
import com.sktelecom.cep.vo.LayoutSchema;
import com.sktelecom.cep.vo.Workspace;
import com.sktelecom.cep.vo.WorkspaceAssign;
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
  int create(Datasource datasource);

  /**
   * datasource 목록 조회.
   * 
   * @param datasource
   * @return
   */
  List<Datasource> getList(Datasource datasource);

  /**
   * workspace 목록 조회
   * @param workspace
   * @return
   */
  List<Workspace> getWorkspaceList(Workspace workspace);

  /**
   * datasource 를 작업공간에 할당하기
   * @param workspaceObject
   * @return
   */
  int saveAssignWorkspace(WorkspaceObject workspaceObject);

  /**
   * 데이타소스에 할당된 workspace 조회
   * @param workspaceAssign
   * @return
   */
  List<Workspace> getAssignedWorkspaceList(WorkspaceAssign workspaceAssign);

  /**
   * workspaceObject 정보 조회
   * @param workspaceObject
   * @return
   */
  WorkspaceObject getWorkspaceObjectInfo(WorkspaceObject workspaceObject);

  /**
   * store 별로 스키마, 테이블, 컬럼 정보들을 가져온다.
   * @param datasource
   * @return
   */
  List<LayoutSchema> loadDatasourceMetadata(Datasource datasource);

}
