package com.sktelecom.cep.repository;

import com.sktelecom.cep.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository.
 * @author Sangmin Park
 *
 */
@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, String> {
  Page<User> findByNameLikeOrderByNameAsc(String name, Pageable pageable);
  Page<User> findByIdLikeOrderByIdDesc(String id, Pageable pageable);
  User findByIdAndPasswd(String id, String passwd);
}
