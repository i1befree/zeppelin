package com.sktelecom.cep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sktelecom.cep.entity.User;

/**
 * UserRepository.
 * @author Sangmin Park
 *
 */
public interface UserRepository extends JpaRepository<User, String> {

}
