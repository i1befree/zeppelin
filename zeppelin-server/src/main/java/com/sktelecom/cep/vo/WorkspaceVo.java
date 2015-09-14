package com.sktelecom.cep.vo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sktelecom.cep.entity.Workspace.Type;

/**
 * ValueObject.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkspaceVo implements Serializable {

  private String wrkspcId;
  private String wrkspcName;
  private String description;
  private Type wrkspcType;
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
  public Type getWrkspcType() {
    return wrkspcType;
  }
  public void setWrkspcType(Type wrkspcType) {
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
