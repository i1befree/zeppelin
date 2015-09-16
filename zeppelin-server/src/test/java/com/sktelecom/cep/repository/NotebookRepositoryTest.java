package com.sktelecom.cep.repository;

import com.sktelecom.cep.entity.Notebook;
import com.sktelecom.cep.entity.User;
import com.sktelecom.cep.entity.WorkspaceAssign;
import com.sktelecom.cep.entity.WorkspaceObject.ObjectType;
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

import static com.sktelecom.cep.entity.WorkspaceObject.ShareType;
import static com.sktelecom.cep.entity.WorkspaceObject.Status;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * NotebookRepository 테스트용 클래스
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:repository-test-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotebookRepositoryTest {
  @Autowired
  NotebookRepository notebookRepository;

  @Autowired
  UserRepository userRepository;

  @BeforeClass
  public static void setUp() {
  }

  @Test
  public void testSave() {
    User admin = userRepository.findOne("admin");

    Notebook notebook = new Notebook();
    notebook.setCreator(admin);
    notebook.setNoteName("Notebook test");
    notebook.setUpdateDate(new Date(System.currentTimeMillis()));
    notebook.setOwner(admin);
    notebook.setShareType(ShareType.NONE);
    notebook.setObjStatus(Status.CREATED);
    notebook.setWrkspcObjType(ObjectType.NOTEBOOK);

    WorkspaceAssign assign = new WorkspaceAssign();
    assign.setWorkspaceObject(notebook);
    assign.setWorkspace(admin.getWorkspace());
    assign.setUpdateDate(new Date(System.currentTimeMillis()));
    assign.setUpdateUserId(admin.getId());

    notebookRepository.save(notebook);
  }

  @Test
  public void testFindOne() {
    Notebook notebook = notebookRepository.findOne("5c9439ee-ca70-4878-9e38-0ca6d3bd6eez");
    assertNotNull(notebook);

    assertEquals("note test", notebook.getNoteName());
  }
}
