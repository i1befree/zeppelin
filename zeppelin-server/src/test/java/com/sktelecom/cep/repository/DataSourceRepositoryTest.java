package com.sktelecom.cep.repository;

import com.sktelecom.cep.entity.DataSource;
import com.sktelecom.cep.entity.DataStore;
import com.sktelecom.cep.entity.User;
import com.sktelecom.cep.entity.WorkspaceAssign;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

import static org.junit.Assert.assertNotNull;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:repository-test-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DataSourceRepositoryTest {
  @Autowired
  DataSourceRepository dataSourceRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  DataStoreRepository dataStoreRepository;

  @BeforeClass
  public static void setUp() {
  }

  @Test
  public void testSave() {
    DataStore dataStore = dataStoreRepository.findOne("23caae00-506b-11e5-bb39-063b17d52e29");
    assertNotNull(dataStore);

    User admin = userRepository.findOne("admin");
    assertNotNull(admin);

    DataSource dataSource = new DataSource();
    dataSource.setContainerName("trip");
    dataSource.setDataStore(dataStore);
    dataSource.setDatsrcName("datasource test");
    dataSource.setDescription("datasource test");
    dataSource.setSrcObjName("workspace");
    dataSource.setUpdateDate(new Date(System.currentTimeMillis()));
    dataSource.setLastModifiedUser(admin);
    dataSource.setCreator(admin);
    dataSource.setOwner(admin);
    dataSource.setObjStatus(DataSource.Status.CREATED);
    dataSource.setShareType(DataSource.ShareType.NONE);
    dataSource.setWrkspcObjType(DataSource.ObjectType.DATSRC);

    // Assign to workspace
    WorkspaceAssign assign = new WorkspaceAssign();
    assign.setWorkspaceObject(dataSource);
    assign.setWorkspace(admin.getWorkspace());
    assign.setUpdateDate(new Date(System.currentTimeMillis()));
    assign.setUpdateUserId(admin.getId());

    DataSource retValue = dataSourceRepository.save(dataSource);
    assertNotNull(retValue.getWrkspcObjId());
  }
}
