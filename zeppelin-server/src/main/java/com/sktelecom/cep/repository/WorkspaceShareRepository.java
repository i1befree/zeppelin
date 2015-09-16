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
}
