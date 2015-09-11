package com.sktelecom.cep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sktelecom.cep.entity.User;
import org.springframework.stereotype.Repository;

/**
 * UserRepository.
 * @author Sangmin Park
 *
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, String> {

}
