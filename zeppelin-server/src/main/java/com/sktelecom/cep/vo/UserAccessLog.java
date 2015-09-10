package com.sktelecom.cep.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 사용자접속로그 ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class UserAccessLog extends Paging {
  // 로그인 일시 
  private Date loginDate;
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

  private String cnt;

  public Date getLoginDate() {
    return loginDate;
  }

  public void setLoginDate(Date loginDate) {
    this.loginDate = loginDate;
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

  public String getUserGrpCd() {
    return userGrpCd;
  }

  public void setUserGrpCd(String userGrpCd) {
    this.userGrpCd = userGrpCd;
  }

  public String getUserGrpNm() {
    return userGrpNm;
  }

  public void setUserGrpNm(String userGrpNm) {
    this.userGrpNm = userGrpNm;
  }

  public String getCnt() {
    return cnt;
  }

  public void setCnt(String cnt) {
    this.cnt = cnt;
  }

}
