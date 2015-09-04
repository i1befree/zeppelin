package com.sktelecom.cep.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.Notebook;
import com.sktelecom.cep.vo.User;
import com.sktelecom.cep.vo.Workspace;

/**
 * 
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/NotebookMapper.xml 과 1:1
 * 매핑한다.
 *
 * @author 박상민
 */
public interface NotebookDao {

  static final Logger LOG = LoggerFactory.getLogger(NotebookDao.class);

  /**
   * 노트북 목록 조회 
   * 
   * @param Workspace
   * @return
   */
  List<Notebook> getListByWorkspaceId(Workspace workspace);

  /**
   * 사용자 관련 최신 노트북 목록 조회 
   * 
   * @param Workspace
   * @return
   */
  List<Notebook> getLastestNotebookListByUserId(User pUser);
  

}
