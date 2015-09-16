package com.sktelecom.cep.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sktelecom.cep.common.CommCode;
import com.sktelecom.cep.entity.DataStore;
import com.sktelecom.cep.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:repository-test-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataStoreRepositoryTest {
  @Autowired
  DataStoreRepository dataStoreRepository;

  @Autowired
  UserRepository userRepository;

  @BeforeClass
  public static void setUp(){
  }

  @Test
  public void testSave(){
    User admin = userRepository.findOne("admin");
    assertNotNull(admin);

    DataStore dataStore = new DataStore();
    dataStore.setName("database test");
    dataStore.setDescription("DataStore test with mysql setting");
    dataStore.setHostName("cep1");
    dataStore.setPassword("mytest");
    dataStore.setPortNum(3306);
    dataStore.setSubType(CommCode.DataStoreSubType.MSSQL);
    dataStore.setType(CommCode.DataStoreType.DATABASE);
    dataStore.setUpdateDate(new Timestamp(System.currentTimeMillis()));
    dataStore.setUpdator(admin);
    dataStore.setUsername(admin.getName());

    DataStore returnVal = dataStoreRepository.save(dataStore);

    assertNotNull(returnVal.getId());
    assertEquals("database test", returnVal.getName());
  }

  @Test
  public void testFindOne(){
    DataStore dataStore = dataStoreRepository.findOne("5c9439ee-ca70-4878-9e38-0ca6d3bd6eea");

    assertNotNull(dataStore);
    assertEquals("mymeta", dataStore.getName());
  }

  @Test
  public void testFindByNameOrderByUpdateTimeDesc(){
    Pageable pageable = new PageRequest(0, 10);
    Page<DataStore> dataStores = dataStoreRepository.findByNameLikeOrderByUpdateDateDesc("%mymeta%", pageable);

    assertEquals(1, dataStores.getTotalPages());
  }
}
