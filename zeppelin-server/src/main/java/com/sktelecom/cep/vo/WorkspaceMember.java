package com.sktelecom.cep.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 사용자 ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class WorkspaceMember {
  private String wrkspcId;
  // 아이디 
  private String userId;
  // 이름 
  private String userName;
  //role cd 
  private String roleCd;
  // role name 
  private String roleName;
  
  private List<String> members;
  
  
  public List<String> getMembers() {
    return members;
  }
  public void setMembers(List<String> members) {
    this.members = members;
  }
  public String getRoleCd() {
    return roleCd;
  }
  public void setRoleCd(String roleCd) {
    this.roleCd = roleCd;
  }
  public String getWrkspcId() {
    return wrkspcId;
  }
  public void setWrkspcId(String wrkspcId) {
    this.wrkspcId = wrkspcId;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName) {
    this.userName = userName;
  }
  public String getRoleName() {
    return roleName;
  }
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }
    
}
