package com.sktelecom.cep.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class WorkspaceObject {

  private String wrkspcObjId;
  private String wrkspcObjType;
  private String shareType;
  private String objStatus;
  private String createUserId;
  private String ownUserId;
  
  public String getWrkspcObjId() {
    return wrkspcObjId;
  }
  public void setWrkspcObjId(String wrkspcObjId) {
    this.wrkspcObjId = wrkspcObjId;
  }
  public String getWrkspcObjType() {
    return wrkspcObjType;
  }
  public void setWrkspcObjType(String wrkspcObjType) {
    this.wrkspcObjType = wrkspcObjType;
  }
  public String getShareType() {
    return shareType;
  }
  public void setShareType(String shareType) {
    this.shareType = shareType;
  }
  public String getObjStatus() {
    return objStatus;
  }
  public void setObjStatus(String objStatus) {
    this.objStatus = objStatus;
  }
  public String getCreateUserId() {
    return createUserId;
  }
  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }
  public String getOwnUserId() {
    return ownUserId;
  }
  public void setOwnUserId(String ownUserId) {
    this.ownUserId = ownUserId;
  }
    
  
}
