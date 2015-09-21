package com.sktelecom.cep.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.DatasourceVo;
import com.sktelecom.cep.vo.NotebookVo;
import com.sktelecom.cep.vo.UserVo;
import com.sktelecom.cep.vo.WorkspaceShareVo;
import com.sktelecom.cep.vo.WorkspaceSummaryVo;
import com.sktelecom.cep.vo.WorkspaceVo;

/**
 * 작업공간관리 - 작업공간 CRUD 담당 Service.
 *
 * @author 박상민
 */
public interface WorkspaceService {

  static final Logger LOG = LoggerFactory.getLogger(WorkspaceService.class);


  /**
   * workspace 목록 조회
   * @param workspace
   * @return
   */
  List<WorkspaceVo> getWorkspaceList();

  /**
   * 사용자와 관련된 작업공간 조회
   * @param userId
   * @return
   */
  List<WorkspaceVo> getWorkspaceListByUserId(String userId);

  /**
   * 노트북 목록 조회.
   * 
   * @param workspace
   * @return
   */
  List<NotebookVo> getNotebookList(WorkspaceVo workspace);

  /**
   * 사용자의 최근 노트북 목록 조회
   * @param userId
   * @return
   */
  List<NotebookVo> getLastestNotebookListByUserId(String userId);

  /**
   * 작업공간의 요약정보 조회
   * @param workspace
   * @return
   */
  WorkspaceSummaryVo getWorkspaceSummaryInfo(WorkspaceVo workspace);

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
  int insertMembers(WorkspaceVo workspace, List<WorkspaceShareVo> wsList);

  /**
   * 작업공간 공유 사용자를 삭제한다.
   * @param item
   */
  int deleteMembers(WorkspaceVo workspaceVo, List<WorkspaceShareVo> wsList);

  /**
   * 데이타 소스 목록 조회
   * @param workspace
   * @return
   */
  List<DatasourceVo> getDatasourceList(WorkspaceVo workspace);
  
  /**
   * workspace 정보 조회 ( notebook목록, datasoruce 목록 포함)
   * @param workspace
   * @return
   */
  WorkspaceVo getWorkspaceObject(WorkspaceVo workspace);
}
