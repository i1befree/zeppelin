package com.sktelecom.cep.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Date;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.sktelecom.cep.common.CommCode;
import com.sktelecom.cep.entity.Notebook;
import com.sktelecom.cep.entity.User;
import com.sktelecom.cep.entity.WorkspaceAssign;

/**
 * NotebookRepository 테스트용 클래스
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotebookRepositoryTest {
  static GenericXmlApplicationContext ctx;

  @BeforeClass
  public static void setUp() {
    ctx = new GenericXmlApplicationContext();
    ctx.load("classpath:repository-test-context.xml");
    ctx.refresh();
  }

  @Test
  public void testSave() {
    NotebookRepository notebookRepository = ctx.getBean("NotebookRepository", NotebookRepository.class);
    UserRepository userRepository = ctx.getBean("UserRepository", UserRepository.class);

    User admin = userRepository.findOne("admin");

    Notebook notebook = new Notebook();
    notebook.setCreator(admin);
    notebook.setNoteName("Notebook test");
    notebook.setUpdateDate(new Date(System.currentTimeMillis()));
    notebook.setOwner(admin);
    notebook.setShareType(CommCode.WorkspaceObjectShareType.NONE);
    notebook.setObjStatus(CommCode.WorkspaceObjectStatus.CREATED);
    notebook.setWrkspcObjType(CommCode.WorkspaceObjectType.NOTEBOOK);

    WorkspaceAssign assign = new WorkspaceAssign();
    assign.setWorkspaceObject(notebook);
    assign.setWorkspace(admin.getWorkspace());
    assign.setUpdateDate(new Date(System.currentTimeMillis()));
    assign.setUpdateUserId(admin.getId());

    notebookRepository.save(notebook);
  }

  @Test
  public void testFindOne() {
    NotebookRepository repository = ctx.getBean("NotebookRepository", NotebookRepository.class);

    Notebook notebook = repository.findOne("5c9439ee-ca70-4878-9e38-0ca6d3bd6eez");
    assertNotNull(notebook);

    assertEquals("note test", notebook.getNoteName());
  }
}
