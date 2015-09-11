package com.sktelecom.cep.repository;

import com.sktelecom.cep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository.
 * @author Sangmin Park
 *
 */
@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, String> {

}
