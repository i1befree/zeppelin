package com.sktelecom.cep.repository;

import com.sktelecom.cep.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository.
 * @author Sangmin Park
 *
 */
@Repository("WorkspaceRepository")
public interface WorkspaceRepository extends JpaRepository<Workspace, String> {

}
