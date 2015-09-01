package com.sktelecom.cep.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.WorkspaceAssign;

/**
 * WorkspaceAssign - WorkspaceAssign CRUD 담당 Dao.
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/WorkspaceAssignMapper.xml 과 1:1
 * 매핑한다.
 *
 * @author 박상민
 */
public interface WorkspaceAssignDao {

  static final Logger LOG = LoggerFactory.getLogger(WorkspaceAssignDao.class);

  /**
   * WorkspaceAssign 생성.
   * 
   * @param WorkspaceAssign
   * @return
   */
  int create(WorkspaceAssign workspaceAssign);

}
