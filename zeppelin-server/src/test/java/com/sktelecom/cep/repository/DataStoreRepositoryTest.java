package com.sktelecom.cep.repository;

import com.sktelecom.cep.entity.DataStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataStoreRepositoryTest {
  GenericXmlApplicationContext ctx;

  @Before
  public void setUp(){
    ctx = new GenericXmlApplicationContext();
    ctx.load("classpath:repository-test-context.xml");
    ctx.refresh();
  }

  public void testSave(){
    DataStoreRepository repository = ctx.getBean("dataStoreRepository", DataStoreRepository.class);
  }

  @Test
  public void testFindByNameOrderByUpdateTimeDesc(){
    DataStoreRepository repository = ctx.getBean("dataStoreRepository", DataStoreRepository.class);
    Pageable pageable = new PageRequest(0, 10);
    Page<DataStore> dataStores = repository.findByNameLikeOrderByUpdateTimeDesc("%mymeta%", pageable);

    Assert.assertEquals(1, dataStores.getTotalPages());
  }
}
