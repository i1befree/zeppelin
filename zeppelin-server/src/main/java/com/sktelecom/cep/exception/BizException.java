package com.sktelecom.cep.exception;

/**
 * 로직상 오류정보를 갖는 Exception 클래스.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
public class BizException extends RuntimeException {

  public BizException(String msg) {
    super(msg);
  }

  public BizException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public BizException(Throwable cause) {
    super(cause);
  }

}
