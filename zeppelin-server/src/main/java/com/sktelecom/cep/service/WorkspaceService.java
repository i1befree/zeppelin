package com.sktelecom.cep.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.Notebook;
import com.sktelecom.cep.vo.Workspace;

/**
 * 작업공간관리 - 작업공간 CRUD 담당 Service.
 *
 * @author 박상민
 */
public interface WorkspaceService {

  static final Logger LOG = LoggerFactory.getLogger(WorkspaceService.class);

  /**
   * 작업공간 생성.
   * 
   * @param workspace
   * @return
   */
  int create(Workspace workspace);

  /**
   * 작업공간 수정.
   * 
   * @param workspace
   * @return
   */
  int update(Workspace workspace);

  /**
   * 작업공간 삭제.
   * 
   * @param workspace
   * @return
   */
  int delete(Workspace workspace);

  /**
   * 작업공간 정보 조회.
   * 
   * @param workspace
   * @return
   */
  Workspace getInfo(Workspace workspace);

  /**
   * 작업공간 목록 조회.
   * 
   * @param workspace
   * @return
   */
  List<Workspace> getList(Workspace workspace);

  /**
   * 노트북 목록 조회.
   * 
   * @param workspace
   * @return
   */
  List<Notebook> getNotebookList(Workspace workspace);

}
