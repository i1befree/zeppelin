package com.sktelecom.cep.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.WorkspaceObject;

/**
 * WorkspaceObject - WorkspaceObject CRUD 담당 Dao.
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/WorkspaceObjectMapper.xml 과 1:1
 * 매핑한다.
 *
 * @author 박상민
 */
public interface WorkspaceObjectDao {

  static final Logger LOG = LoggerFactory.getLogger(WorkspaceObjectDao.class);

  /**
   * WorkspaceObject 생성.
   * 
   * @param WorkspaceObject
   * @return
   */
  int create(WorkspaceObject workspaceObject);

}
