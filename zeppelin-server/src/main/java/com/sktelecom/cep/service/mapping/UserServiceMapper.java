package com.sktelecom.cep.service.mapping;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sktelecom.cep.common.CipherUtils;
import com.sktelecom.cep.common.CommCode;
import com.sktelecom.cep.entity.DataSource;
import com.sktelecom.cep.entity.Role;
import com.sktelecom.cep.entity.User;
import com.sktelecom.cep.entity.Workspace;
import com.sktelecom.cep.entity.WorkspaceAssign;
import com.sktelecom.cep.entity.WorkspaceObject;
import com.sktelecom.cep.entity.WorkspaceShare;
import com.sktelecom.cep.vo.DatasourceVo;
import com.sktelecom.cep.vo.DatastoreVo;
import com.sktelecom.cep.vo.NotebookVo;
import com.sktelecom.cep.vo.PageVo;
import com.sktelecom.cep.vo.RoleVo;
import com.sktelecom.cep.vo.UserVo;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class UserServiceMapper extends AbstractServiceMapper {

  /**
   * ModelMapper : bean to bean mapping library.
   */
  private ModelMapper modelMapper;

  /**
   * Constructor.
   */
  public UserServiceMapper() {
    modelMapper = new ModelMapper();
    modelMapper.getConfiguration().setMatchingStrategy(
        MatchingStrategies.STRICT);
    
    
//    PropertyMap<User, UserVo> orderMap = new PropertyMap<User, UserVo>() {
//      protected void configure() {
//        skip().setWorkspace(null);
//      }
//    };
//      
//    modelMapper.addMappings(orderMap);
  }

  @Override
  protected ModelMapper getModelMapper() {
    return modelMapper;
  }

  protected void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  /**
   * Mapping from entity to vo
   * 
   * @param UserEntity
   */
  public UserVo mapEntityToVo(User entity) {
    if (entity == null) {
      return null;
    }

    // --- Generic mapping
    UserVo vo = map(entity, UserVo.class);
    vo.setPasswd(null);
    return vo;
  }

  /**
   * 페이징 엔터티에서 vo field 와 매핑
   * 
   * @param list
   * @return
   */
  public PageVo<UserVo> mapListEntityToVo(Page<User> page) {
    PageVo<UserVo> pageVo = new PageVo<UserVo>();
    pageVo.setTotalCount(page.getTotalElements());
    pageVo.setPageSize(page.getSize());
    List<UserVo> listVo = new ArrayList<UserVo>();
    for (com.sktelecom.cep.entity.User entity : page.getContent()) {
      listVo.add(mapEntityToVo(entity));
    }
    pageVo.setContent(listVo);

    return pageVo;
  }

  /**
   * workspace 공유 멤버 목록을 가져온다.
   * @param workspace
   * @param roles
   * @return
   */
  public List<UserVo> mapListUserVoFromWorkspaceEntity(Workspace workspace, List<Role> roles) {
    List<UserVo> list = new ArrayList<UserVo>();
    
    List<WorkspaceShare> shareList = workspace.getWorkspaceShares();
    for (WorkspaceShare share : shareList) {
      UserVo userVo = mapEntityToVo(share.getUser());
      userVo.setRole(getMatchRole(roles, userVo.getUserGrpCd()));
      list.add(userVo);
    }
    return list;
  }

  /**
   * 유저목록entity 를 vo 목록으로 리턴
   * @param userList
   * @param roles
   * @return
   */
  public List<UserVo> mapListUserVoFromEntity(List<User> userList, List<Role> roles) {
    List<UserVo> list = new ArrayList<UserVo>();
    
    for (User userEntity : userList) {
      UserVo userVo = mapEntityToVo(userEntity);
      userVo.setRole(getMatchRole(roles, userVo.getUserGrpCd()));
      list.add(userVo);
    }
    return list;
  }

  /**
   * 사용자 그룹코드와 같은 role을 리턴한다.
   * @param roles
   * @param roleCode
   * @return
   */
  public RoleVo getMatchRole(List<Role> roles, String roleCode) {
    for (Role roleEntity : roles) {
      if (roleCode.equals(roleEntity.getCode())) {
        return mapEntityToVo(roleEntity, RoleVo.class);
      }
    }
    return null;
  }
  
  /**
   * Mapping from vo to Entity
   * 
   * @param UserVo
   * @param UserEntity
   */
  public void mapVoToEntity(UserVo vo, User entity) {
    if (vo == null) {
      return;
    }
    String passwd = entity.getPasswd();
    // --- Generic mapping
    map(vo, entity);
    if (vo.getPasswd() == null) {
      entity.setPasswd(passwd);
    } else {
      entity.setPasswd(CipherUtils.getSHA256(vo.getPasswd()));
    }
  }

  /**
   * 최근 노트북 목록을 조회한다.
   * @param userEntity
   * @return
   */
  public List<NotebookVo> mapListLastestNotebookFromUserEntity(User userEntity) {
    List<NotebookVo> list = new ArrayList<NotebookVo>();
    
    List<WorkspaceShare> shareList = userEntity.getWorkspaceShares();
    for (WorkspaceShare share : shareList) {
      Workspace workspace = share.getWorkspace();
      List<WorkspaceAssign> assignList = workspace.getWorkspaceAssigns();
      for (WorkspaceAssign assign : assignList) {
        WorkspaceObject workspaceObjectEntity = assign.getWorkspaceObject();
        if (workspaceObjectEntity.getWrkspcObjType() == CommCode.WorkspaceObjectType.NOTEBOOK) {
          NotebookVo notebookVo = mapEntityToVo(workspaceObjectEntity.getTarget(), NotebookVo.class);
          notebookVo.setWrkspcId(workspace.getWrkspcId());
          list.add(notebookVo);
        }
      }
    }
    return list;
  }

}

