package com.sktelecom.cep.repository;

import com.sktelecom.cep.entity.DataStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DataStoreRepository
 */
@Repository("dataStoreRepository")
public interface DataStoreRepository extends JpaRepository<DataStore, String> {
  Page<DataStore> findByNameOrderByUpdateTimeDesc(String name, Pageable pageable);
}
