package com.sktelecom.cep.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sktelecom.cep.common.CepConstant;
import com.sktelecom.cep.common.SimpleResultMessage;
import com.sktelecom.cep.common.UserGroupCodeEnum;
import com.sktelecom.cep.service.UserService;
import com.sktelecom.cep.vo.PageVo;
import com.sktelecom.cep.vo.Role;
import com.sktelecom.cep.vo.UserSession;
import com.sktelecom.cep.vo.UserVo;

/**
 * 사용자관리 - 사용자 CRUD 담당 Controller.
 *
 * @author 박상민
 */
@Controller
public class UserController {

  private static final Logger logger = LoggerFactory
      .getLogger(UserController.class);

  @Inject
  private UserService userService;

  /**
   * 사용자 생성.
   * 
   * @param user
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/user/create", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage create(@RequestBody UserVo user, HttpSession session) {
    logger.debug("id {}", user.getId());
    
    UserSession userSession = (UserSession) session.getAttribute(CepConstant.USER_SESSION);
    user.setUpdateUserId(userSession.getId());
    userService.create(user);
    return new SimpleResultMessage("SUCCESS", "사용자를 생성하였습니다.");
  }

  /**
   * 사용자 수정.
   * 
   * @param user
   * @param session
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/user/update", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage update(@RequestBody UserVo user, HttpSession session) {
    logger.debug("id {}", user.getId());
    
    // 유저가 관리자 여부에 따라서 수정처리를 다르게 한다.
    user.setPasswd(user.getPasswd() != null && !"".equals(user.getPasswd().trim()) ? user.getPasswd().trim() : null);
    UserSession userSession = (UserSession) session.getAttribute(CepConstant.USER_SESSION);
    user.setUpdateUserId(userSession.getId());
    if (UserGroupCodeEnum.MANAGER.getValue().equals(userSession.getUserGrpCd())) {
      int resultInt = userService.updateByManager(user); // 관리자 경우만
    } else {
      UserVo updatedUser = userService.update(user);
    }
    return new SimpleResultMessage("SUCCESS", "사용자 정보를 수정하였습니다.");
  }

  /**
   * 사용자 삭제.
   * 
   * @param user
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage delete(@RequestBody UserVo user) {
    logger.debug("id {}", user.getId());
    
    userService.delete(user);
    return new SimpleResultMessage("SUCCESS", "사용자를 삭제하였습니다.");
  }

  /**
   * 사용자 정보 조회.
   * 
   * @param user
   * @return User
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/user/getInfo", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public UserVo getInfo(@RequestBody UserVo user) {
    logger.debug("id {}", user.getId());
    
    UserVo resultInfo = userService.getInfo(user);
    return resultInfo;
  }

  /**
   * 사용자 목록 조회.
   * 
   * @param user
   * @param session
   * @return List<User>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/user/getList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public PageVo<UserVo> getList(@RequestBody PageVo<UserVo> pageVo) {
    PageRequest pageRequest = new PageRequest(pageVo.getPageNumber(), pageVo.getPageSize());
    PageVo<UserVo> result = userService.getListByPage(pageRequest);
    List<UserVo> list = result.getContent();
    for (UserVo user : list) {
      user.setPasswd("");
    }
    return result;
  }

  /**
   * 사용자 그룹 목록 가져오기
   * @return
   */
  @RequestMapping(value = "/user/getRole", method = RequestMethod.POST)
  @ResponseBody
  public List<Role> getRole(){
    return userService.getRole();
  }

}
