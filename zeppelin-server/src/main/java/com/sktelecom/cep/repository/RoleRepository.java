package com.sktelecom.cep.repository;

import com.sktelecom.cep.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RoleRepository.
 * @author Sangmin Park
 *
 */
@Repository("RoleRepository")
public interface RoleRepository extends JpaRepository<Role, String> {
  Role findByCode(String roleCd);
}
