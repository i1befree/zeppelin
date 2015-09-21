package com.sktelecom.cep.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sktelecom.cep.common.CepConstant;
import com.sktelecom.cep.vo.UserSessionVo;

/**
 * 메인페이지로 포워딩 작업을 처리하는 Controller.
 *
 * @author 박상민
 */
@Controller
public class HomeController {

  private static final Logger logger = LoggerFactory
      .getLogger(HomeController.class);

  /**
   * 루트경로에 대한 이동페이지를 결정한다.
   * 
   * @return main.html 로 포워딩한다.
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/", method = RequestMethod.GET)
  // / @endcond
  public String home() {
    logger.debug("home");

    return "forward:/main.html";
  }

  /**
   * 사용자 세션정보를 리턴한다.
   * 
   * @param session
   * @return 사용자세션정보
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/getUserSession", method = RequestMethod.GET)
  @ResponseBody
  // / @endcond
  public UserSessionVo login(HttpSession session) {
    UserSessionVo userSession = (UserSessionVo) session
        .getAttribute(CepConstant.USER_SESSION);

    return userSession;
  }
}
