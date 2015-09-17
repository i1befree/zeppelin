package com.sktelecom.cep.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ValueObject.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class WorkspaceShareVo implements Serializable {

  private String shareId;
  private String wrkspcId;
  private String userId;
  private Date updateDate;
  private String updateUserId;
  
  private List<String> userIds;
  
  
  public List<String> getUserIds() {
    return userIds;
  }
  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
  }
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
