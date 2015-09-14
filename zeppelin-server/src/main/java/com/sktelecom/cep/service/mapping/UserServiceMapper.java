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
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'UserEntity' to 'User'
	 * @param UserEntity
	 */
	public UserVo mapUserEntityToUserVo(User userEntity) {
		if(userEntity == null) {
			return null;
		}

		//--- Generic mapping 
		UserVo userVo = map(userEntity, UserVo.class);
		userVo.setPasswd(null);
		return userVo;
	}
	
	/**
	 * 페이징 엔터티에서 vo field 와 매핑
	 * @param list
	 * @return
	 */
  public PageVo<UserVo> mapListUserEntityToUserVo(Page<User> list) {
    PageVo<UserVo> page = new PageVo<UserVo>();
    page.setTotalCount(list.getTotalElements());
    page.setPageSize(list.getSize());
    List<UserVo> beans = new ArrayList<UserVo>();
    for(com.sktelecom.cep.entity.User userEntity : list.getContent()) {
      beans.add(mapUserEntityToUserVo(userEntity));
    }
    page.setContent(beans); 

    return page;
  }

  /**
	 * Mapping from 'User' to 'UserEntity'
	 * @param User
	 * @param UserEntity
	 */
	public void mapUserVoToUserEntity(UserVo userVo, User userEntity) {
		if(userVo == null) {
			return;
		}
		String passwd = userEntity.getPasswd();
		//--- Generic mapping 
		map(userVo, userEntity);
		if(userVo.getPasswd() == null) {
		  userEntity.setPasswd(passwd);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}