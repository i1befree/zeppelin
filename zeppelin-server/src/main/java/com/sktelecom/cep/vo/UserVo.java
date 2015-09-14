package com.sktelecom.cep.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ValueObject.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVo implements Serializable {

  private String id;
  private String name;
  private String passwd;
  private String email;
  private String tel;
  private Date updateDate;
  private String updateUserId;
  //private WorkspaceVo workspace;
  //private RoleVo role;
  private String userGrpCd;

  public String getUserGrpCd() {
    return userGrpCd;
  }
  public void setUserGrpCd(String userGrpCd) {
    this.userGrpCd = userGrpCd;
  }
  public String getId() {
    return id;
  }
//  public RoleVo getRole() {
//    return role;
//  }
//  public void setRole(RoleVo role) {
//    this.role = role;
//  }
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
//  public WorkspaceVo getWorkspace() {
//    return workspace;
//  }
//  public void setWorkspace(WorkspaceVo workspace) {
//    this.workspace = workspace;
//  }
  
    
}
