package com.sktelecom.cep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sktelecom.cep.entity.WorkspaceShare;

/**
 * WorkspaceShareRepository.
 * @author Sangmin Park
 *
 */
@Repository
public interface WorkspaceShareRepository extends JpaRepository<WorkspaceShare, String> {
  void deleteByUserId(String userId);
}
