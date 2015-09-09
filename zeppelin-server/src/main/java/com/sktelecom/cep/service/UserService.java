package com.sktelecom.cep.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sktelecom.cep.vo.Role;
import com.sktelecom.cep.vo.User;

/**
 * 사용자관리 - 사용자 CRUD 담당 Service.
 *
 * @author 박상민
 */
public interface UserService {

  static final Logger LOG = LoggerFactory.getLogger(UserService.class);

  /**
   * 사용자 생성.
   * 
   * @param user
   * @return
   */
  int create(User user);

  /**
   * 사용자 수정.
   * 
   * @param user
   * @return
   */
  int update(User user);

  /**
   * 관리자에 의한 사용자 생성 - 사용자그룹 및 패스워드 변경할수 있다.
   * 
   * @param user
   * @return
   */
  int updateByManager(User user);

  /**
   * 사용자 삭제.
   * 
   * @param user
   * @return
   */
  int delete(User user);

  /**
   * 사용자 정보 조회.
   * 
   * @param user
   * @return
   */
  User getInfo(User user);

  /**
   * 로그인 - 아이디와 패스워드로 사용자 정보 조회.
   * 
   * @param loginInfo
   * @return
   */
  User getCheckLoginUserInfo(User loginInfo);

  /**
   * 사용자 목록 조회.
   * 
   * @param user
   * @return
   */
  List<User> getList(User user);

  /**
   * 페이징 처리되는 사용자 목록 조회
   * @param pageRequest
   * @return
   */
  Page<com.sktelecom.cep.entity.User> getListByPage(Pageable pageable);
  
  /**
   * 사용자 그룹 목록 가져오기.
   * @return
   */
  List<Role> getRole();
}
