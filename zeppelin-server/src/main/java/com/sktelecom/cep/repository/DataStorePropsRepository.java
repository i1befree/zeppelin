package com.sktelecom.cep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sktelecom.cep.entity.DataStoreProperty;

/**
 * DataStoreRepository
 */
@Repository("DataStorePropsRepository")
public interface DataStorePropsRepository extends JpaRepository<DataStoreProperty, String> {

}
