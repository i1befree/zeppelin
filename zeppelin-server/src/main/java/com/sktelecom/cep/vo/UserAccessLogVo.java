package com.sktelecom.cep.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 사용자접속로그 ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("serial")
// / @endcond
public class UserAccessLogVo implements Serializable {
  
  private String id;
  private Date loginDate;
  private String userId;
  private String name;
  private String email;
  private String tel;
  private String userGrpCd;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getLoginDate() {
    return loginDate;
  }

  public void setLoginDate(Date loginDate) {
    this.loginDate = loginDate;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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
  

}
