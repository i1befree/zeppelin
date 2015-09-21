package com.sktelecom.cep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sktelecom.cep.entity.UserAccessLog;

/**
 * UserRepository.
 * @author Sangmin Park
 *
 */
@Repository("UserAccessLogRepository")
public interface UserAccessLogRepository extends JpaRepository<UserAccessLog, String> {
  
}
