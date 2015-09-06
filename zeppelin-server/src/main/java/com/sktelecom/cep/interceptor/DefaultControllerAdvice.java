package com.sktelecom.cep.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sktelecom.cep.common.SimpleResultMessage;
import com.sktelecom.cep.exception.AutyorityException;
import com.sktelecom.cep.exception.SessionTimeoutException;

/**
 * MVC Controller 수행시 발생되는 Exception 을 캐치해서 동일형태의 메세지로 리턴하는 Advice 클래스.
 *
 * @author 박상민
 */
@ControllerAdvice
@EnableWebMvc
public class DefaultControllerAdvice {
  private static final Logger logger = LoggerFactory.getLogger(DefaultControllerAdvice.class);

  /**
   * 세션 타임아웃 Exception 발생에 대해서 처리한다.
   * 
   * @param e
   * @return SimpleResultMessage
   */
  @ExceptionHandler(SessionTimeoutException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public SimpleResultMessage handleSessionTimeoutException(SessionTimeoutException e) {
    logger.error(e.toString());
    SimpleResultMessage message = new SimpleResultMessage("FAIL_SESSION_TIMEOUT", e.getMessage());
    return message;
  }

  /**
   * 기타 Exception 발생에 대해서 처리한다.
   * 
   * @param e
   * @return SimpleResultMessage
   */
  @ExceptionHandler(Throwable.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public SimpleResultMessage handleException(final Exception e) {
    logger.error(e.toString());
    SimpleResultMessage message = new SimpleResultMessage("FAIL", e.getMessage());
    return message;
  }

  /**
   * 메뉴접근 권한이 없을때 처리.
   * 
   * @param e
   * @return SimpleResultMessage
   */
  @ExceptionHandler(AutyorityException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public SimpleResultMessage handleAutyorityException(AutyorityException e) {
    logger.error(e.toString());
    SimpleResultMessage message = new SimpleResultMessage("FAIL_AUTHORITY", e.getMessage());
    return message;
  }

}
