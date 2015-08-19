package com.sktelecom.cep.vo;

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
  // 생성일시 
  private String createDate;
  // 수정일시 
  private String updateDate;
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

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public String getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(String updateDate) {
    this.updateDate = updateDate;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPasswd() {
    return passwd;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }

}
