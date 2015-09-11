package com.sktelecom.cep.repository;

import com.sktelecom.cep.entity.DataSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DataSourceRepository
 */
@Repository("DataSourceRepository")
public interface DataSourceRepository extends JpaRepository<DataSource, String> {
}
