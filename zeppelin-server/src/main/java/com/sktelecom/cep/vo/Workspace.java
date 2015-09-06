package com.sktelecom.cep.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class Workspace extends Paging {

  private String wrkspcId;
  private String wrkspcName;
  private String description;
  private String wrkspcType;
  private String adminUserId;
  private Date updateDate;
  private String updateUserId;
    
  public String getWrkspcId() {
    return wrkspcId;
  }
  public void setWrkspcId(String wrkspcId) {
    this.wrkspcId = wrkspcId;
  }
  public String getWrkspcName() {
    return wrkspcName;
  }
  public void setWrkspcName(String wrkspcName) {
    this.wrkspcName = wrkspcName;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getWrkspcType() {
    return wrkspcType;
  }
  public void setWrkspcType(String wrkspcType) {
    this.wrkspcType = wrkspcType;
  }
  public String getAdminUserId() {
    return adminUserId;
  }
  public void setAdminUserId(String adminUserId) {
    this.adminUserId = adminUserId;
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
}
