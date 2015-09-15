package com.sktelecom.cep.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sktelecom.cep.entity.DataSource;

/**
 * DataSourceRepository
 */
@Repository("DataSourceRepository")
public interface DataSourceRepository extends JpaRepository<DataSource, String> {

}
