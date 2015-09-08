package com.sktelecom.cep.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sktelecom.cep.common.CepConstant;
import com.sktelecom.cep.common.UserGroupCodeEnum;
import com.sktelecom.cep.service.UserAccessLogService;
import com.sktelecom.cep.vo.UserAccessLog;
import com.sktelecom.cep.vo.UserSession;

/**
 * 사용자접속로그 - 사용자 접속 로그를 조회하는 Controller.
 *
 * @author 박상민
 */
@Controller
public class UserAccessLogController {

  @Inject
  private UserAccessLogService userAccessLogService;

  /**
   * 사용자 접속 로그 목록 조회.
   * 
   * @param userAccessLog
   * @param session
   * @return 사용자 접속로그 목록
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/userAccessLog/getList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<UserAccessLog> getList(@RequestBody UserAccessLog userAccessLog,
      HttpSession session) {
    // 관리자가 아닌 경우 세션 아이디 세팅후 조회
    UserSession userSession = (UserSession) session.getAttribute(CepConstant.USER_SESSION);
    if (userSession != null && !UserGroupCodeEnum.MANAGER.getValue().equals(userSession.getUserGrpCd())) {
      userAccessLog.setQuery(userSession.getId());
    }

    List<UserAccessLog> resultList = userAccessLogService
        .getList(userAccessLog);
    for (UserAccessLog log : resultList) {
      log.setUserGrpNm(UserGroupCodeEnum.getDescByValue(log.getUserGrpCd()));
    }
    return resultList;
  }

}
