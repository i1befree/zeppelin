package com.sktelecom.cep.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sktelecom.cep.common.CepConstant;
import com.sktelecom.cep.common.SimpleResultMessage;
import com.sktelecom.cep.common.UserGroupCodeEnum;
import com.sktelecom.cep.exception.AutyorityException;
import com.sktelecom.cep.exception.SessionTimeoutException;
import com.sktelecom.cep.service.UserAccessLogService;
import com.sktelecom.cep.service.UserService;
import com.sktelecom.cep.vo.UserAccessLog;
import com.sktelecom.cep.vo.UserSession;
import com.sktelecom.cep.vo.UserVo;

/**
 * 로그인처리를 담당, 로그아웃처리, 유효한세션인지 체크, 세션타임아웃예외를 발생시키는 Controller.
 *
 * @author 박상민
 */
@Controller
public class LoginoutController {

  private static final Logger logger = LoggerFactory.getLogger(LoginoutController.class);

  @Inject
  private UserService userService;

  @Inject
  private UserAccessLogService userAccessLogService;

  /**
   * 로그인 페이지로 이동한다.
   * 
   * @return 로그인페이지로 리다이렉트 한다.
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/loginPage")
  // / @endcond
  public String viewLoginPage() {
    return "redirect:/views/login.html";
  }

  /**
   * 로그인 처리를 한다. 사용자 세션 생성을 하고 접속로그를 기록에 남긴다.
   * 
   * @param loginInfo
   * @param session
   * @return SimpleResultMessage
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage login(@RequestBody UserVo loginInfo, HttpSession session) {
    logger.debug("id {}", loginInfo.getId());
    SimpleResultMessage message = new SimpleResultMessage("FAIL", "사용자와 패스워드가 올바르지 않습니다.");

    UserVo user = userService.getCheckLoginUserInfo(loginInfo);
    if (user != null) {
      UserSession userSession = new UserSession();
      userSession.setId(user.getId());
      userSession.setName(user.getName());
      userSession.setEmail(user.getEmail());
      userSession.setTel(user.getTel());
      userSession.setUserGrpCd(user.getUserGrpCd());
      userSession.setUserGrpNm(UserGroupCodeEnum.getDescByValue(user.getUserGrpCd()));

      session.setAttribute(CepConstant.USER_SESSION, userSession);

      message.setRsCode("SUCCESS");
      message.setRsMessage("로그인 하였습니다.");
      message.setObject(userSession);
      
      // 접속로그 기록 시작
      logger.debug("접속로그 기록 시작");
      try {
        UserAccessLog userAccessLog = new UserAccessLog();
        userAccessLog.setId(user.getId());
        userAccessLogService.create(userAccessLog);
      } catch (Exception e) {
        // 로그 기록만 남기기 때문에, Exception 을 던지지 않는다.
        logger.error(e.getMessage());
      }
      logger.debug("접속로그 기록 종료");
      // 접속로그 기록 종료

      return message;
    }
    return message;
  }

  /**
   * 로그아웃시 로그인페이지 url 로 리다이렉트 한다.
   * 
   * @param session
   * @return String
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage logout(HttpSession session) {
    session.invalidate();
    SimpleResultMessage message = new SimpleResultMessage("FAIL", "로그아웃에 실패하였습니다.");
    message.setRsCode("SUCCESS");
    message.setRsMessage("로그아웃 하였습니다.");
    return message;
  }

  /**
   * 세션타임아웃 예외를 발생시킨다.
   * 
   * application/json 형식의 호출에 대해서 LoginCheckInterceptor 에서 체크를 하는데, 이때 사용자 세션이
   * 종료되었으면 /exception/sessionTimeout 으로 포워딩 시키고, SessionTimeoutException 을 던져서
   * json 형태로 결과를 클라이언트에 던져준다.
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/exception/sessionTimeout")
  @ResponseBody
  // / @endcond
  public void sessionTimeout() {
    throw new SessionTimeoutException("사용자 세션이 종료 되었습니다.");
  }

  /**
   * 세션이 유효한지 그렇지 않은지에 대한 더미 경로이다.
   * 
   * /isValidSession 경로로 접근하는 요청에 대해서 interceptor 에서 세션유효여부를 판단하여 오류값을 받을지 오류가
   * 아닌 값을 받을지 호출한 client 쪽에서 처리할수 있도록 한다.
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/isValidSession")
  @ResponseBody
  // / @endcond
  public String isValidSessionForBeforeEsClient() {
    String validSessionKey = "VALID_SESSION_FOR_ESCLIENT";
    if (logger.isDebugEnabled()) {
      logger.debug(String.format("Call before using the esService: validSession[%s]", validSessionKey));
    }
    return validSessionKey;
  }

  /**
   * 권한이 없을때 처리
   * 
   * application/json 형식의 호출에 대해서 LoginCheckInterceptor 에서 체크를 하는데, 이때 사용자가 권한이
   * 없는 url 접근시 /exception/authority 으로 포워딩 시키고, AutyorityException 을 던져서 json
   * 형태로 결과를 클라이언트에 던져준다.
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/exception/authority")
  @ResponseBody
  // / @endcond
  public void authority() {
    throw new AutyorityException("접근권한이 부여되지 않은 경로입니다.");
  }

}
