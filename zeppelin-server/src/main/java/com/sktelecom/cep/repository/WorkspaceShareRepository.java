package com.sktelecom.cep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sktelecom.cep.entity.WorkspaceShare;

/**
 * WorkspaceShareRepository.
 * @author Sangmin Park
 *
 */
@Repository("WorkspaceShareRepository")
public interface WorkspaceShareRepository extends JpaRepository<WorkspaceShare, String> {
  
  void deleteByUserId(String userId);

  void deleteByWorkspaceWrkspcIdAndUserId(String wrkspcId, String userId);

  List<WorkspaceShare> findByUserId(String userId);
}
