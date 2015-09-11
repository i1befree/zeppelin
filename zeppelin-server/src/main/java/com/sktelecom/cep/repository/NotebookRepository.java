package com.sktelecom.cep.repository;

import com.sktelecom.cep.entity.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Notebook repository
 */
@Repository("NotebookRepository")
public interface NotebookRepository extends JpaRepository<Notebook, String> {
}
