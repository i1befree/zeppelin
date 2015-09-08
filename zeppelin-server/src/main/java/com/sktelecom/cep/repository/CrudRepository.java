package com.sktelecom.cep.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

/**
 * CrudRepository.
 * @author Administrator
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface CrudRepository<T, ID extends Serializable> extends Repository {
  T save(T entity);
  Iterable<T> save(Iterable<? extends T> entities);
  T findOne(ID id);
  boolean exists(ID id);
  Iterable<T> findAll();
  long count();
  void delete(ID id);
  void delete(T entity);
  void delete(Iterable<? extends T> entities);
  void deleteAll();
}
