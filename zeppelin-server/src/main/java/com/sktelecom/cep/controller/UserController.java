package com.sktelecom.cep.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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
import com.sktelecom.cep.vo.Role;
import com.sktelecom.cep.vo.SearchParam;
import com.sktelecom.cep.vo.User;
import com.sktelecom.cep.vo.UserSession;

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
   * 사용자 생성 요청.
   * 
   * @param user
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/user/createRequest", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage createRequest(@RequestBody User user, HttpSession session) {
    logger.debug("id {}", user.getId());
    SimpleResultMessage message = new SimpleResultMessage("FAIL",
        "사용자를 생성하지 못하였습니다.");

    User resultInfo = userService.getInfo(user);
    if (resultInfo != null) {
      message.setRsCode("FAIL");
      message.setRsMessage("사용자가 존재 합니다.");
      return message;
    }
    UserSession userSession = (UserSession) session.getAttribute(CepConstant.USER_SESSION);
    user.setUpdateUserId(userSession.getId());
    int resultInt = userService.create(user);
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("사용자를 생성하였습니다.");
    }
    return message;
  }

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
  public SimpleResultMessage create(@RequestBody User user, HttpSession session) {
    logger.debug("id {}", user.getId());
    SimpleResultMessage message = new SimpleResultMessage("FAIL",
        "사용자가 존재 합니다.");

    User resultInfo = userService.getInfo(user);
    if (resultInfo != null) {
      message.setRsCode("FAIL");
      message.setRsMessage("사용자가 존재 합니다.");
      return message;
    }
    UserSession userSession = (UserSession) session.getAttribute(CepConstant.USER_SESSION);
    user.setUpdateUserId(userSession.getId());
    int resultInt = userService.create(user);
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("사용자를 생성하였습니다.");
    }
    return message;
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
  public SimpleResultMessage update(@RequestBody User user, HttpSession session) {
    logger.debug("id {}", user.getId());
    SimpleResultMessage message = new SimpleResultMessage("FAIL",
        "사용자 정보 수정을 실패하였습니다.");
    int resultInt = 0;
    // 유저가 관리자 여부에 따라서 수정처리를 다르게 한다.
    user.setPasswd(user.getPasswd() != null ? user.getPasswd().trim() : null);
    UserSession userSession = (UserSession) session.getAttribute(CepConstant.USER_SESSION);
    user.setUpdateUserId(userSession.getId());
    if (UserGroupCodeEnum.MANAGER.getValue().equals(userSession.getUserGrpCd())) {
      resultInt = userService.updateByManager(user); // 관리자 경우만
    } else {
      resultInt = userService.update(user);
    }
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("사용자 정보를 수정하였습니다.");
    }
    return message;
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
  public SimpleResultMessage delete(@RequestBody User user) {
    logger.debug("id {}", user.getId());
    SimpleResultMessage message = new SimpleResultMessage("FAIL",
        "사용자를 삭제 하였습니다.");

    int resultInt = userService.delete(user);
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("사용자를 생성하였습니다.");
    }
    return message;
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
  public User getInfo(@RequestBody User user) {
    User resultInfo = userService.getInfo(user);
    resultInfo.setUserGrpNm(UserGroupCodeEnum.getDescByValue(user
        .getUserGrpCd()));

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
  public Page<com.sktelecom.cep.entity.User> getList(@RequestBody SearchParam search) {
    PageRequest pageRequest = new PageRequest(search.getPageNumber(), search.getPageSize());
    Page<com.sktelecom.cep.entity.User> result = userService.getListByPage(pageRequest);
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
