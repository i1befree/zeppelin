package com.sktelecom.cep.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.DatasourceVo;
import com.sktelecom.cep.vo.Notebook;
import com.sktelecom.cep.vo.UserVo;
import com.sktelecom.cep.vo.Workspace;
import com.sktelecom.cep.vo.WorkspaceMember;
import com.sktelecom.cep.vo.WorkspaceShare;
import com.sktelecom.cep.vo.WorkspaceSummary;
import com.sktelecom.cep.vo.WorkspaceVo;

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
   * 사용자와 관련된 작업공간 조회
   * @param userId
   * @return
   */
  List<Workspace> getListByUserId(String userId);

  /**
   * 노트북 목록 조회.
   * 
   * @param workspace
   * @return
   */
  List<Notebook> getNotebookList(Workspace workspace);

  /**
   * 사용자의 최근 노트북 목록 조회
   * @param userId
   * @return
   */
  List<Notebook> getLastestNotebookListByUserId(String userId);

  /**
   * 작업공간의 요약정보 조회
   * @param workspace
   * @return
   */
  WorkspaceSummary getWorkspaceSummaryInfo(Workspace workspace);

  /**
   * 작업공간을 공유하는 멤버 목록 조회
   * @param workspace
   * @return
   */
  List<UserVo> getWorkspaceMemberList(WorkspaceVo workspace);

  /**
   * 작업공간 공유 사용자를 추가한다.
   * @param wsList
   * @return
   */
  int insertMembers(List<WorkspaceShare> wsList);

  /**
   * 작업공간 공유 사용자를 삭제한다.
   * @param item
   */
  int deleteMembers(List<WorkspaceShare> wsList);

  /**
   * 데이타 소스 목록 조회
   * @param workspace
   * @return
   */
  List<DatasourceVo> getDatasourceList(WorkspaceVo workspace);
  
  WorkspaceVo getWorkspaceObject(WorkspaceVo workspace);
}
