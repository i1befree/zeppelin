package com.sktelecom.cep.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.sktelecom.cep.common.CommCode;
import com.sktelecom.cep.entity.User;
import com.sktelecom.cep.entity.Workspace;

/**
 * UserRepositoryTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:repository-test-context.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class UserRepositoryTest {
  @Autowired
  UserRepository userRepository;

  @Autowired
  WorkspaceRepository workspaceRepository;

  @BeforeClass
  public static void setUp() {
  }

  @Test
  public void testFindByNameLikeOrderByName(){
    Pageable pageable = new PageRequest(0, 10);
    Page<User> users = userRepository.findByNameLikeOrderByNameAsc("관리%", pageable);

    assertEquals(1, users.getTotalPages());
    assertEquals(1, users.getNumberOfElements());
  }

  @Test
  public void testFindByIdLikeOrderByIdDesc(){
    Pageable pageable = new PageRequest(0, 10);
    Page<User> users = userRepository.findByIdLikeOrderByIdDesc("ad%", pageable);

    assertEquals(1, users.getTotalPages());
    assertEquals(1, users.getNumberOfElements());
  }

  @Test
  @Rollback(false)
  public void testSave(){
    User admin = userRepository.findOne("admin");

    User user = new User();
    user.setId("i1befree");
    user.setName("이정룡");
    user.setEmail("i1befree@sk.com");
    user.setPasswd("eptest");
    user.setTel("010-1111-1111");
    user.setUpdateUserId("admin");
    user.setUserGrpCd("3");
    user.setUpdateUserId(admin.getId());

    Workspace workspace = new Workspace();
    workspace.setWrkspcName(user.getName());
    workspace.setAdminUserId(user.getId());
    workspace.setDescription("Personal workspace");
    workspace.setUpdateDate(new Date());
    workspace.setUpdateUserId(user.getId());
    workspace.setWrkspcType(CommCode.WorkspaceType.PERSONAL);

    Workspace retWorkspace = workspaceRepository.save(workspace);

    user.setWorkspace(retWorkspace);
    User retUser = userRepository.saveAndFlush(user);

    assertNotNull(retUser.getId());
    assertNotNull(retUser.getWorkspace());
    assertEquals(user.getId(), retUser.getWorkspace().getAdminUserId());
  }
}
