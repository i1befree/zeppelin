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
public class WorkspaceShare extends Paging {

  private String shareId;
  private String wrkspcId;
  private String userId;
  private Date updateDate;
  private String updateUserId;
  
  //extra
  private String wrkspcType;
    
  public String getShareId() {
    return shareId;
  }
  public void setShareId(String shareId) {
    this.shareId = shareId;
  }
  public String getWrkspcType() {
    return wrkspcType;
  }
  public void setWrkspcType(String wrkspcType) {
    this.wrkspcType = wrkspcType;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getWrkspcId() {
    return wrkspcId;
  }
  public void setWrkspcId(String wrkspcId) {
    this.wrkspcId = wrkspcId;
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
