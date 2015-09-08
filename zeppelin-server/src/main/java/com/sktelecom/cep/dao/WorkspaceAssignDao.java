package com.sktelecom.cep.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.Workspace;
import com.sktelecom.cep.vo.WorkspaceAssign;
import org.springframework.stereotype.Repository;

/**
 * WorkspaceAssign - WorkspaceAssign CRUD 담당 Dao.
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/WorkspaceAssignMapper.xml 과 1:1
 * 매핑한다.
 *
 * @author 박상민
 */
@Repository
public interface WorkspaceAssignDao {

  static final Logger LOG = LoggerFactory.getLogger(WorkspaceAssignDao.class);

  /**
   * WorkspaceAssign 생성.
   * 
   * @param workspaceAssign
   * @return
   */
  int create(WorkspaceAssign workspaceAssign);

  /**
   * WrkspcObjId 과 연관된 작업공간 할당을 해제한다.
   * @param workspaceAssign
   * @return
   */
  int deleteByWrkspcObjId(WorkspaceAssign workspaceAssign);

}
