package com.sktelecom.cep.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import com.sktelecom.cep.vo.PageVo;
import com.sktelecom.cep.vo.RoleVo;
import com.sktelecom.cep.vo.UserVo;

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
  int create(UserVo user);

  /**
   * 사용자 수정.
   * 
   * @param user
   * @return
   */
  UserVo update(UserVo user);

  /**
   * 관리자에 의한 사용자 생성 - 사용자그룹 및 패스워드 변경할수 있다.
   * 
   * @param user
   * @return
   */
  int updateByManager(UserVo user);

  /**
   * 사용자 삭제.
   * 
   * @param user
   * @return
   */
  void delete(UserVo userVo);

  /**
   * 사용자 정보 조회.
   * 
   * @param user
   * @return
   */
  UserVo getInfo(UserVo user);

  /**
   * 로그인 - 아이디와 패스워드로 사용자 정보 조회.
   * 
   * @param loginInfo
   * @return
   */
  UserVo getCheckLoginUserInfo(UserVo loginInfo);

  /**
   * 페이징 처리되는 사용자 목록 조회
   * @param pageRequest
   * @return
   */
  PageVo<UserVo> getListByPage(Pageable pageable);
  
  /**
   * 사용자 그룹 목록 가져오기.
   * @return
   */
  List<RoleVo> getRole();
}
