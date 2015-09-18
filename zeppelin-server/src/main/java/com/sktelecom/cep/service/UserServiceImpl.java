package com.sktelecom.cep.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sktelecom.cep.common.CipherUtils;
import com.sktelecom.cep.common.CommCode;
import com.sktelecom.cep.entity.Workspace;
import com.sktelecom.cep.entity.WorkspaceShare;
import com.sktelecom.cep.repository.RoleRepository;
import com.sktelecom.cep.repository.UserRepository;
import com.sktelecom.cep.repository.WorkspaceRepository;
import com.sktelecom.cep.repository.WorkspaceShareRepository;
import com.sktelecom.cep.service.mapping.RoleServiceMapper;
import com.sktelecom.cep.service.mapping.UserServiceMapper;
import com.sktelecom.cep.vo.PageVo;
import com.sktelecom.cep.vo.RoleVo;
import com.sktelecom.cep.vo.UserVo;

/**
 * 사용자관리 - 사용자 CRUD 담당 Service 구현체.
 *
 * @author 박상민
 */
@Service
public class UserServiceImpl implements UserService {

  static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

  @Inject
  private UserRepository userRepository;
  
  @Inject
  private RoleRepository roleRepository;
    
  @Inject
  private WorkspaceRepository workspaceRepository;

  @Inject
  private WorkspaceShareRepository workspaceShareRepository;

  @Inject
  private UserServiceMapper userServiceMapper;
  
  @Inject
  private RoleServiceMapper roleServiceMapper;
  
  

  @Override
  public int create(UserVo userVo) {
    
    com.sktelecom.cep.entity.User user = userRepository.findOne(userVo.getId());
    if (user != null) {
      throw new IllegalStateException("이미 존재하는 사용자가 아이디입니다.");
    }
    
    Workspace workspace = new Workspace();
    workspace.setWrkspcName(userVo.getId());
    workspace.setDescription("Personal Workspace");
    workspace.setWrkspcType(CommCode.WorkspaceType.PERSONAL);
    workspace.setAdminUserId(userVo.getUpdateUserId());
    workspace.setUpdateUserId(userVo.getUpdateUserId());
    com.sktelecom.cep.entity.Workspace savedWorkspace = workspaceRepository.save(workspace);
         
    //com.sktelecom.cep.entity.Role role = roleRepository.findByCode(userVo.getRole().getCode());
    
    com.sktelecom.cep.entity.User newUser = new com.sktelecom.cep.entity.User();
    userServiceMapper.mapVoToEntity(userVo, newUser);
    newUser.setWorkspace(workspace);
    //newUser.setRole(role);
    com.sktelecom.cep.entity.User savedUser = userRepository.save(newUser);
    
    WorkspaceShare workspaceShare = new WorkspaceShare();
    workspaceShare.setWorkspace(savedWorkspace);
    workspaceShare.setUser(savedUser);
    workspaceShare.setUpdateUserId(userVo.getUpdateUserId());
    workspaceShareRepository.save(workspaceShare);
    
    return 1;
  }

  @Override
  public UserVo update(UserVo userVo) {    
//    com.sktelecom.cep.entity.Role role = roleRepository.findByCode(userVo.getRole().getCode());
//    RoleVo updatableUserRole = new RoleVo();
//    updatableUserRole.setId(role.getId());
//    updatableUserRole.setName(role.getName());
//    updatableUserRole.setCode(role.getCode());
//    userVo.setRole(updatableUserRole);
    
    com.sktelecom.cep.entity.User user = userRepository.findOne(userVo.getId());
    userServiceMapper.mapVoToEntity(userVo, user);
    com.sktelecom.cep.entity.User updatedUser = userRepository.save(user);
    return userServiceMapper.mapEntityToVo(updatedUser);
  }

  @Override
  public int updateByManager(UserVo user) {
    com.sktelecom.cep.entity.User userEntity = userRepository.findOne(user.getId());
    userServiceMapper.mapVoToEntity(user, userEntity);
    userRepository.save(userEntity);
    return 1;
  }

  @Override
  public void delete(UserVo userVo) {
    workspaceShareRepository.deleteByUserId(userVo.getId());
    
    com.sktelecom.cep.entity.User user = userRepository.findOne(userVo.getId());
    String wrkspcId = user.getWorkspace().getWrkspcId();    
    userRepository.delete(userVo.getId());

    workspaceRepository.delete(wrkspcId);    
  }

  @Override
  public UserVo getInfo(UserVo user) {
    com.sktelecom.cep.entity.User userEntity = userRepository.findOne(user.getId());
    
    //convert from entity to vo 
    UserVo userInfo = userServiceMapper.mapEntityToVo(userEntity);
    return userInfo;
  }

  @Override
  public UserVo getCheckLoginUserInfo(UserVo user) {
    com.sktelecom.cep.entity.User userEntity = userRepository.findByIdAndPasswd(user.getId(), CipherUtils.getSHA256(user.getPasswd()));
    
    //convert from entity to vo 
    UserVo userInfo = userServiceMapper.mapEntityToVo(userEntity);
    return userInfo;
  }

  @Override
  public PageVo<UserVo> getListByPage(Pageable pageable) {
    Page<com.sktelecom.cep.entity.User> result = userRepository.findAll(pageable);
    
    List<com.sktelecom.cep.entity.Role> roles = roleRepository.findAll();
    //convert from entity to vo 
    PageVo<UserVo> page = userServiceMapper.mapListEntityToVo(result, roles);   
    return page;
  }

  @Override
  public List<RoleVo> getRole() {
    List<com.sktelecom.cep.entity.Role> roleEntity = roleRepository.findAll();
    
    //convert from entity to vo 
    return roleServiceMapper.mapListEntityToVo(roleEntity, RoleVo.class);
  }

}
