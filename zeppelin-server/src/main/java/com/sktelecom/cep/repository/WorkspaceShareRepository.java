package com.sktelecom.cep.repository;

import com.sktelecom.cep.entity.WorkspaceShare;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * WorkspaceShareRepository.
 * @author Sangmin Park
 *
 */
@Repository("WorkspaceShareRepository")
public interface WorkspaceShareRepository extends JpaRepository<WorkspaceShare, String> {
  
  void deleteByUserId(String userId);

  /**
   * wrkspcId 컬럼이 User 와 Workspace 에도 같이 있어서 찾지 못하는것 같음. 이런경우 엔터티명과 필드명을 붙여서 메소드명을 만든다. 
   * @param wrkspcId
   * @param userId
   */
  void deleteByWorkspaceWrkspcIdAndUserId(String wrkspcId, String userId);

}
