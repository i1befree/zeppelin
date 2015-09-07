package com.sktelecom.cep.dao;

import java.util.List;

import com.sktelecom.cep.vo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sktelecom.cep.vo.User;

/**
 * 사용자관리 - 사용자 CRUD 담당 Dao.
 *
 * Mybatis mapper 로써 resources/com/sktelecom/voc/doc/UserMapper.xml 과 1:1 매핑한다.
 *
 * @author 박상민
 */
public interface UserDao {

  static final Logger LOG = LoggerFactory.getLogger(UserDao.class);

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
   * 아이디와 패스워드로 사용자 정보 조회.
   * 
   * @param user
   * @return
   */
  User getCheckLoginUserInfo(User user);

  /**
   * 사용자 목록 조회.
   * 
   * @param user
   * @return
   */
  List<User> getList(User user);

  /**
   * 사용자 목록 카운트.
   * 
   * @param user
   * @return
   */
  long getListCount(User user);

  /**
   * 사용자 그룹 가져오기
   *
   * @return
   */
  List<Role> getRole();

}
