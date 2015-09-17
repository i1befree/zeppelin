package com.sktelecom.cep.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.cep.repository.RoleRepository;
import com.sktelecom.cep.repository.UserRepository;
import com.sktelecom.cep.repository.WorkspaceRepository;
import com.sktelecom.cep.repository.WorkspaceShareRepository;
import com.sktelecom.cep.service.mapping.UserServiceMapper;
import com.sktelecom.cep.service.mapping.WorkspaceServiceMapper;
import com.sktelecom.cep.vo.DatasourceVo;
import com.sktelecom.cep.vo.NotebookVo;
import com.sktelecom.cep.vo.UserVo;
import com.sktelecom.cep.vo.WorkspaceShareVo;
import com.sktelecom.cep.vo.WorkspaceSummaryVo;
import com.sktelecom.cep.vo.WorkspaceVo;

/**
 * 사용자관리 - 사용자 CRUD 담당 Service 구현체.
 *
 * @author 박상민
 */
@Service
public class WorkspaceServiceImpl implements WorkspaceService {

  static final Logger LOG = LoggerFactory.getLogger(WorkspaceServiceImpl.class);

  @Inject
  private WorkspaceRepository workspaceRepository;

  @Inject
  private WorkspaceShareRepository workspaceShareRepository;

  @Inject
  private UserRepository userRepository;

  @Inject
  private WorkspaceServiceMapper workspaceServiceMapper;

  @Inject
  private UserServiceMapper userServiceMapper;
  
  @Inject
  private RoleRepository roleRepository;
  

  @Override
  public List<WorkspaceVo> getWorkspaceList() {
    List<com.sktelecom.cep.entity.Workspace> workspaceList = workspaceRepository.findAll();
    
    //convert from entity to vo 
    return workspaceServiceMapper.mapListEntityToVo(workspaceList, WorkspaceVo.class);
  }

  @Override
  public List<WorkspaceVo> getWorkspaceListByUserId(String userId) {    
    List<com.sktelecom.cep.entity.WorkspaceShare> shareList = workspaceShareRepository.findByUserId(userId);
    
    //convert from entity to vo 
    return workspaceServiceMapper.getListWorkspaceVoFromEntity(shareList);
  }

  @Override
  public List<NotebookVo> getNotebookList(WorkspaceVo workspace) {
    com.sktelecom.cep.entity.Workspace entity = workspaceRepository.findOne(workspace.getWrkspcId());
    
    //convert from entity to vo 
    return workspaceServiceMapper.mapListNotebookVoFromEntity(entity);
  }

  @Override
  public List<NotebookVo> getLastestNotebookListByUserId(String userId) {
    com.sktelecom.cep.entity.User userEntity = userRepository.findOne(userId);
    
    //convert from entity to vo 
    return userServiceMapper.mapListLastestNotebookFromUserEntity(userEntity);
  }

  @Override
  public WorkspaceSummaryVo getWorkspaceSummaryInfo(WorkspaceVo workspace) {
    com.sktelecom.cep.entity.Workspace entity = workspaceRepository.findOne(workspace.getWrkspcId());
    
    //convert from entity to vo 
    return workspaceServiceMapper.getWorkspaceSummaryVoFromEntity(entity);
  }

  @Override
  public List<UserVo> getWorkspaceMemberList(WorkspaceVo workspace) {
    com.sktelecom.cep.entity.Workspace entity = workspaceRepository.findOne(workspace.getWrkspcId());
    
    List<com.sktelecom.cep.entity.Role> roles = roleRepository.findAll();
    //convert from entity to vo 
    return userServiceMapper.mapListUserVoFromWorkspaceEntity(entity, roles);
  }

  @Override
  public int insertMembers(WorkspaceVo workspaceVo, List<WorkspaceShareVo> wsList) {
    com.sktelecom.cep.entity.Workspace workspace = new com.sktelecom.cep.entity.Workspace();
    workspace.setWrkspcId(workspaceVo.getWrkspcId());
    
    for (WorkspaceShareVo shareVo : wsList) {
      com.sktelecom.cep.entity.User user = new com.sktelecom.cep.entity.User();
      user.setId(shareVo.getUserId());
      
      com.sktelecom.cep.entity.WorkspaceShare share = new com.sktelecom.cep.entity.WorkspaceShare();
      share.setWorkspace(workspace);
      share.setUpdateUserId(shareVo.getUpdateUserId());
      share.setUser(user);
      
      workspaceShareRepository.save(share);
    }
    return 1;
  }

  @Override
  public int deleteMembers(WorkspaceVo workspaceVo, List<WorkspaceShareVo> wsList) {
    for (WorkspaceShareVo shareVo : wsList) {
      workspaceShareRepository.deleteByWorkspaceWrkspcIdAndUserId(workspaceVo.getWrkspcId(), shareVo.getUserId());
    }
    return 1;
  }

  @Override
  public List<DatasourceVo> getDatasourceList(WorkspaceVo workspace) {
    com.sktelecom.cep.entity.Workspace entity = workspaceRepository.findOne(workspace.getWrkspcId());
    
    //convert from entity to vo 
    return workspaceServiceMapper.mapListDatasourceVoFromEntity(entity);
  }
  
  @Override
  public WorkspaceVo getWorkspaceObject(WorkspaceVo workspace) {
    com.sktelecom.cep.entity.Workspace entity = workspaceRepository.findOne(workspace.getWrkspcId());
    
    //convert from entity to vo 
    return workspaceServiceMapper.mapWorkspaceVoFromEntity(entity);
  }
}
