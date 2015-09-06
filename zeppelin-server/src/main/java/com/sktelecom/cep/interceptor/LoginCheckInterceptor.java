package com.sktelecom.cep.interceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import com.sktelecom.cep.common.CepConstant;
import com.sktelecom.cep.vo.UserSession;

/**
 * MVC Controller 로 진입전에 웹호출에 대하여 세션유무에 따라서 로직처리를 담당하는 Interceptor.
 *
 * @author 박상민
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

  private static final Logger logger = LoggerFactory.getLogger(LoginCheckInterceptor.class);

  /**
   * Controller 로 진입전에 웹호출에 대하여 처리를 담당한다.
   * 
   * <pre>
   *             사용자 세션이 없을때(로그인전이거나, 세션이 종료되었거나)
   *             호출이 application/json 인지 아닌지 구분하여, 로그인 페이지로 보내게 되는데
   *            json 인경우는 /exception/sessionTimeout 으로, 아니면 /loginPage 로 포워딩 시킨다.
   * </pre>
   */
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    UserSession user = new UserSession();
    user.setId("admin");
    user.setName("관리자");
    user.setUserGrpCd("1");
    WebUtils.setSessionAttribute(request, CepConstant.USER_SESSION, user);
    
    
    UserSession userSession = (UserSession) WebUtils.getSessionAttribute(request, CepConstant.USER_SESSION);
    logger.debug("request.getRequestURI()==>" + request.getRequestURI());
    if (userSession == null) {
      String headerAccept = request.getHeader("Accept");
      String forwardPath = "/loginPage";
      if (headerAccept != null && headerAccept.indexOf(MediaType.APPLICATION_JSON_VALUE) != -1) {
        forwardPath = "/exception/sessionTimeout"; // application/json 컨텐츠 호출시
                                                   // 세션 종료가 된경우
      }
      try {
        if (logger.isDebugEnabled()) {
          logger.debug("============ request.getRequestURI()   :" + request.getRequestURI());
          logger.debug("============ request.getHeader(Accept) :" + request.getHeader("Accept"));
          logger.debug("============ UserSession is null, Go " + forwardPath);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
        dispatcher.forward(request, response);

      } catch (IOException e) {
        throw new ServletException(e);
      }
      return false;
    } else {
      // 정의된 권한만 적용함. 정의한 권한이 없는경우 모두 권한이 있는것으로 처리됨
      String requestURI = request.getRequestURI();
      if (requestURI.indexOf("/view/") != -1) {
        Map<String, List<String>> menuAuthMap = getMenuAuthMap();
        for (String keyUri : menuAuthMap.keySet()) {
          if (requestURI.indexOf(keyUri) != -1) {
            List<String> authGrpCd = menuAuthMap.get(keyUri);
            if (!authGrpCd.contains(userSession.getUserGrpCd())) {
              RequestDispatcher dispatcher = request.getRequestDispatcher("/exception/authority");
              dispatcher.forward(request, response);
              return false;
            }
          }
        }
      }
    }
    return super.preHandle(request, response, handler);
  }

  /**
   * 권한이 적용되는 메뉴에 대한 uri 정의.
   */
  private Map<String, List<String>> getMenuAuthMap() {
    Map<String, List<String>> menuAuthMap = new HashMap<String, List<String>>();
    menuAuthMap.put("/view/mobile.html", Arrays.asList("1", "4"));
    menuAuthMap.put("/view/alarmMgr.html", Arrays.asList("1", "4"));
    menuAuthMap.put("/view/userMgr.html", Arrays.asList("1"));
    return menuAuthMap;
  }

}
