package com.sktelecom.cep.exception;

/**
 * 세션타임아웃이 발생했을때 적용하는 Exception 클래스.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
public class AutyorityException extends RuntimeException {

  public AutyorityException(String msg) {
    super(msg);
  }

  public AutyorityException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public AutyorityException(Throwable cause) {
    super(cause);
  }

}
