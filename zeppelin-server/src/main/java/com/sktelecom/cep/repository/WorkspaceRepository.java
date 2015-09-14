package com.sktelecom.cep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sktelecom.cep.entity.Workspace;

/**
 * UserRepository.
 * @author Sangmin Park
 *
 */
@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, String> {

}
