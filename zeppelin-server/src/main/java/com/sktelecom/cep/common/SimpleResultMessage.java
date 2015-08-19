package com.sktelecom.cep.common;

/**
 * ajax 호출에 대하여 CUD 작업 결과를 코드와 메세지로 보내며, 예외발생시에도 결과상태를 보내는 클래스.
 *
 * @author 박상민
 */
public class SimpleResultMessage {

  // 결과 코드 
  private String rsCode;
  // 결과 메세지 
  private String rsMessage;
  // 추가정보   
  private Object object;

  public SimpleResultMessage() {

  }

  public SimpleResultMessage(String rsCode, String rsMessage) {
    this(rsCode, rsMessage, null);
  }

  public SimpleResultMessage(String rsCode, String rsMessage, Object object) {
    this.rsCode = rsCode;
    this.rsMessage = rsMessage;
    this.object = object;
  }

  public String getRsCode() {
    return rsCode;
  }

  public void setRsCode(String rsCode) {
    this.rsCode = rsCode;
  }

  public String getRsMessage() {
    return rsMessage;
  }

  public void setRsMessage(String rsMessage) {
    this.rsMessage = rsMessage;
  }

  public Object getObject() {
    return object;
  }

  public void setObject(Object object) {
    this.object = object;
  }

}
