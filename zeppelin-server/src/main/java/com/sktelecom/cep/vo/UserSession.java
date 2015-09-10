package com.sktelecom.cep.vo;

/**
 * 사용자세션 ValueObject.
 *
 * @author 박상민
 */
public class UserSession {
  // 아이디 
  private String id;
  // 이름 
  private String name;
  // 이메일 
  private String email;
  // 전화번호 
  private String tel;
  // 사용자그룹코드 
  private String userGrpCd;
  // 사용자그룹명 
  private String userGrpNm;

  public String getUserGrpNm() {
    return userGrpNm;
  }

  public void setUserGrpNm(String userGrpNm) {
    this.userGrpNm = userGrpNm;
  }

  public String getUserGrpCd() {
    return userGrpCd;
  }

  public void setUserGrpCd(String userGrpCd) {
    this.userGrpCd = userGrpCd;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

}
