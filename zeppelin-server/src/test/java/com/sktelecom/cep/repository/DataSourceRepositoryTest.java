package com.sktelecom.cep.repository;

import com.sktelecom.cep.entity.DataSource;
import com.sktelecom.cep.entity.DataStore;
import com.sktelecom.cep.entity.User;
import com.sktelecom.cep.entity.WorkspaceAssign;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.sql.Date;

import static org.junit.Assert.assertNotNull;

/**
 *
 */
public class DataSourceRepositoryTest {
  static GenericXmlApplicationContext ctx;

  @BeforeClass
  public static void setUp() {
    ctx = new GenericXmlApplicationContext();
    ctx.load("classpath:repository-test-context.xml");
    ctx.refresh();
  }

  @Test
  public void testSave() {
    DataSourceRepository dataSourceRepository = ctx.getBean("DataSourceRepository", DataSourceRepository.class);
    UserRepository userRepository = ctx.getBean("UserRepository", UserRepository.class);
    DataStoreRepository dataStoreRepository = ctx.getBean("DataStoreRepository", DataStoreRepository.class);

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
    assign.setPk(new WorkspaceAssign.WorkspaceAssignPk(dataSource, admin.getWorkspace()));
    assign.setUpdateDate(new Date(System.currentTimeMillis()));
    assign.setUpdateUserId(admin.getId());

    DataSource retValue = dataSourceRepository.save(dataSource);
    assertNotNull(retValue.getWrkspcObjId());
  }
}
