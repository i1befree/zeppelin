package com.sktelecom.cep.repository;

import com.sktelecom.cep.entity.DataStore;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface DataStoreRepository extends JpaRepository<DataStore, String> {
  Page<DataStore> findByName(String name);
}
