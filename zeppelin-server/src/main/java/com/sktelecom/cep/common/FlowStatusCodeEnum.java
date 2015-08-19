package com.sktelecom.cep.common;

/**
 * Flow 의 실행상태 코드명칭 정의한 enum 클래스.
 * 
 * App 을 실행과 정지를 처리하기 위한 상태를 나타내는 코드명칭
 *
 * @author 박상민
 */
public enum FlowStatusCodeEnum {
  // db코드, 화면코드
  EXEC_REQUEST("100", "실행요청"), EXEC_SUCCESS("101", "실행"), EXEC_FAIL("102",
      "실행실패"), STOP_REQUEST("200", "정지요청"), STOP_SUCCESS("201", "정지"), STOP_FAIL(
      "202", "정지실패");

  // db 사용코드 
  private String code;
  // 화면 사용 명 
  private String name;

  private FlowStatusCodeEnum(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public static String getNameByCode(String code) {
    for (FlowStatusCodeEnum e : FlowStatusCodeEnum.values()) {
      if (e.getCode().equals(code)) {
        return e.getName();
      }
    }
    return null;
  }

}
