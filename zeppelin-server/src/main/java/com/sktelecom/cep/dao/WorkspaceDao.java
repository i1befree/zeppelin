package com.sktelecom.cep.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.Workspace;
import com.sktelecom.cep.vo.WorkspaceSummary;

/**
 * 작업공간관리 - 작업공간 CRUD 담당 Dao.
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/WorkspaceMapper.xml 과 1:1
 * 매핑한다.
 *
 * @author 박상민
 */
public interface WorkspaceDao {

  static final Logger LOG = LoggerFactory.getLogger(WorkspaceDao.class);

  /**
   * 작업공간 생성.
   * 
   * @param Workspace
   * @return
   */
  int create(Workspace workspace);

  /**
   * 작업공간 수정.
   * 
   * @param Workspace
   * @return
   */
  int update(Workspace workspace);

  /**
   * 작업공간 삭제.
   * 
   * @param Workspace
   * @return
   */
  int delete(Workspace workspace);

  /**
   * 작업공간 정보 조회.
   * 
   * @param Workspace
   * @return
   */
  Workspace getInfo(Workspace workspace);

  /**
   * 작업공간 목록 조회.
   * 
   * @param Workspace
   * @return
   */
  List<Workspace> getList(Workspace workspace);

  /**
   * 작업공간 목록 카운트.
   * 
   * @param Workspace
   * @return
   */
  long getListCount(Workspace workspace);

  /**
   * 타입에 의한 작업공간 목록 조회.
   * 
   * @param Workspace
   * @return
   */
  List<Workspace> getListByType(Workspace workspace);

  /**
   * 작업공간의 요약정보 조회
   * @param workspace
   * @return
   */
  WorkspaceSummary getWorkspaceSummaryInfo(Workspace workspace);
  

}
