package com.sktelecom.cep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sktelecom.cep.entity.Role;

/**
 * RoleRepository.
 * @author Sangmin Park
 *
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
  Role findByCode(String roleCd);
}
