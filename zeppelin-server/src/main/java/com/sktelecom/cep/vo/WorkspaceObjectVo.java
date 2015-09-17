package com.sktelecom.cep.vo;

import java.io.Serializable;

import com.sktelecom.cep.common.CommCode;

/**
 * WorkspaceObjectVo.
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public abstract class WorkspaceObjectVo implements Serializable {
  
  private String wrkspcObjId;
  private CommCode.WorkspaceObjectType wrkspcObjType;
  private CommCode.WorkspaceObjectShareType shareType;
  private CommCode.WorkspaceObjectStatus objStatus;
  private UserVo creator;
  private UserVo owner;
  
  
  public String getWrkspcObjId() {
    return wrkspcObjId;
  }

  public void setWrkspcObjId(String wrkspcObjId) {
    this.wrkspcObjId = wrkspcObjId;
  }

  public CommCode.WorkspaceObjectType getWrkspcObjType() {
    return wrkspcObjType;
  }

  public void setWrkspcObjType(CommCode.WorkspaceObjectType wrkspcObjType) {
    this.wrkspcObjType = wrkspcObjType;
  }

  public CommCode.WorkspaceObjectShareType getShareType() {
    return shareType;
  }

  public void setShareType(CommCode.WorkspaceObjectShareType shareType) {
    this.shareType = shareType;
  }

  public CommCode.WorkspaceObjectStatus getObjStatus() {
    return objStatus;
  }

  public void setObjStatus(CommCode.WorkspaceObjectStatus objStatus) {
    this.objStatus = objStatus;
  }

  public UserVo getCreator() {
    return creator;
  }

  public void setCreator(UserVo creator) {
    this.creator = creator;
  }

  public UserVo getOwner() {
    return owner;
  }

  public void setOwner(UserVo owner) {
    this.owner = owner;
  }

    
}

