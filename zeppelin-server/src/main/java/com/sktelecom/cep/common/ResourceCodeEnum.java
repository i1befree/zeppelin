package com.sktelecom.cep.common;

/**
 * 리소스관리용으로서 db 코드명과 화면에서 사용하는 코드명칭 정의한 enum 클래스.
 * 
 * 자원종류를 구분하는데 있어서, 플로우디자인과 관리용 db 에서 사용하는 값이 다르다. 그러므로, 각 자원종류별로 타입을 매핑해서 사용함
 *
 * @author 박상민
 */
public enum ResourceCodeEnum {
  // db코드, 화면코드
  SOURCE("cmp_01", "datasource"), COMPONENT("cmp_03", "component"), SINK(
      "cmp_02", "repository");

  // db 사용코드 
  private String code;
  // 화면 사용 명 
  private String name;

  private ResourceCodeEnum(String code, String name) {
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
    for (ResourceCodeEnum e : ResourceCodeEnum.values()) {
      if (e.getCode().equals(code)) {
        return e.getName();
      }
    }
    return null;
  }

}
