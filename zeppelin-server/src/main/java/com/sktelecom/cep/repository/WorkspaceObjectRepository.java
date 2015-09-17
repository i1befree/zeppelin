package com.sktelecom.cep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sktelecom.cep.entity.WorkspaceObject;

/**
 * UserRepository.
 * @author Sangmin Park
 *
 */
@Repository("WorkspaceObjectRepository")
public interface WorkspaceObjectRepository extends JpaRepository<WorkspaceObject, String> {

}
