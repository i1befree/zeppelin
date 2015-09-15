package com.sktelecom.cep.service.mapping;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.sktelecom.cep.entity.User;
import com.sktelecom.cep.vo.PageVo;
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

  /**
   * Mapping from entity to vo
   * 
   * @param UserEntity
   */
  public UserVo mapUserEntityToUserVo(User entity) {
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
  public PageVo<UserVo> mapListUserEntityToUserVo(Page<User> page) {
    PageVo<UserVo> pageVo = new PageVo<UserVo>();
    pageVo.setTotalCount(page.getTotalElements());
    pageVo.setPageSize(page.getSize());
    List<UserVo> listVo = new ArrayList<UserVo>();
    for (com.sktelecom.cep.entity.User entity : page.getContent()) {
      listVo.add(mapUserEntityToUserVo(entity));
    }
    pageVo.setContent(listVo);

    return pageVo;
  }

  /**
   * Mapping from vo to Entity
   * 
   * @param User
   * @param UserEntity
   */
  public void mapUserVoToUserEntity(UserVo vo, User entity) {
    if (vo == null) {
      return;
    }
    String passwd = entity.getPasswd();
    // --- Generic mapping
    map(vo, entity);
    if (vo.getPasswd() == null) {
      entity.setPasswd(passwd);
    }
  }

  @Override
  protected ModelMapper getModelMapper() {
    return modelMapper;
  }

  protected void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

}

