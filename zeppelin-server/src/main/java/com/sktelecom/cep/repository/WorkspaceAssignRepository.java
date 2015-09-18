package com.sktelecom.cep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sktelecom.cep.entity.WorkspaceAssign;

/**
 * WorkspaceShareRepository.
 * @author Sangmin Park
 *
 */
@Repository("WorkspaceAssignRepository")
public interface WorkspaceAssignRepository extends JpaRepository<WorkspaceAssign, String> {
  
  void deleteByWorkspaceObjectWrkspcObjId(String wrkspcObjId);

}
