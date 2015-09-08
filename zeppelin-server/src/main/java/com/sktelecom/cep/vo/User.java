package com.sktelecom.cep.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 사용자 ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class User extends Paging {
  // 아이디 
  private String id;
  // 이름 
  private String name;
  // 패스워드 
  private String passwd;
  // 이메일 
  private String email;
  // 전화번호 
  private String tel;
  // 수정일시 
  private Date updateDate;
  // 수정 사용자 아이디 
  private String updateUserId;
  // 사용자그룹코드 
  private String userGrpCd;
  // 사용자그룹명 
  private String userGrpNm;
  // personal workspace
  private String wrkspcId;
  
  
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
  public String getPasswd() {
    return passwd;
  }
  public void setPasswd(String passwd) {
    this.passwd = passwd;
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
  public Date getUpdateDate() {
    return updateDate;
  }
  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
  public String getUpdateUserId() {
    return updateUserId;
  }
  public void setUpdateUserId(String updateUserId) {
    this.updateUserId = updateUserId;
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
  public String getWrkspcId() {
    return wrkspcId;
  }
  public void setWrkspcId(String wrkspcId) {
    this.wrkspcId = wrkspcId;
  }

  
}
