package com.sktelecom.cep.repository;

import com.sktelecom.cep.entity.DataStore;
import com.sktelecom.cep.entity.DataStore.SubType;
import com.sktelecom.cep.entity.User;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;

import static com.sktelecom.cep.entity.DataStore.Type;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataStoreRepositoryTest {
  static GenericXmlApplicationContext ctx;

  @BeforeClass
  public static void setUp(){
    ctx = new GenericXmlApplicationContext();
    ctx.load("classpath:repository-test-context.xml");
    ctx.refresh();
  }

  @Test
  public void testSave(){
    DataStoreRepository repository = ctx.getBean("DataStoreRepository", DataStoreRepository.class);
    UserRepository userRepository = ctx.getBean("UserRepository", UserRepository.class);

    User admin = userRepository.findOne("admin");
    assertNotNull(admin);

    DataStore dataStore = new DataStore();
    dataStore.setName("database test");
    dataStore.setDescription("DataStore test with mysql setting");
    dataStore.setHostName("cep1");
    dataStore.setPassword("mytest");
    dataStore.setPortNum(3306);
    dataStore.setSubType(SubType.MSSQL);
    dataStore.setType(Type.DATABASE);
    dataStore.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    dataStore.setUpdator(admin);
    dataStore.setUsername(admin.getName());

    DataStore returnVal = repository.save(dataStore);

    assertNotNull(returnVal.getId());
    assertEquals("database test", returnVal.getName());
  }

  @Test
  public void testFindOne(){
    DataStoreRepository repository = ctx.getBean("DataStoreRepository", DataStoreRepository.class);
    DataStore dataStore = repository.findOne("5c9439ee-ca70-4878-9e38-0ca6d3bd6eea");

    assertNotNull(dataStore);
    assertEquals("mymeta", dataStore.getName());
  }

  @Test
  public void testFindByNameOrderByUpdateTimeDesc(){
    DataStoreRepository repository = ctx.getBean("DataStoreRepository", DataStoreRepository.class);
    Pageable pageable = new PageRequest(0, 10);
    Page<DataStore> dataStores = repository.findByNameLikeOrderByUpdateTimeDesc("%mymeta%", pageable);

    assertEquals(1, dataStores.getTotalPages());
  }
}
