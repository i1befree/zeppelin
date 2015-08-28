package com.sktelecom.cep.common;

/**
 * 사용자 그룹을 코드와 명칭으로 정의한 enum 클래스.
 *
 * @author 박상민
 */
public enum UserGroupCodeEnum {
  MANAGER("1", "System admin"), SPECIAL("2", "Workspace admin"), NORMAL("3", "User");

  // 사용자그룹 값 
  private String value;
  // 사용자그룹 명칭 
  private String desc;

  private UserGroupCodeEnum(String value, String desc) {
    this.value = value;
    this.desc = desc;
  }

  public String getValue() {
    return value;
  }

  public String getDesc() {
    return desc;
  }

  public static String getDescByValue(String value) {
    for (UserGroupCodeEnum e : UserGroupCodeEnum.values()) {
      if (e.getValue().equals(value)) {
        return e.getDesc();
      }
    }
    return null;
  }

}
