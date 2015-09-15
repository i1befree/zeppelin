package com.sktelecom.cep.vo;

import java.io.Serializable;

import com.sktelecom.cep.common.CommCode;

@SuppressWarnings("serial")
public abstract class WorkspaceObjectVo implements Serializable {
  
  private String wrkspcObjId;
  private CommCode.ObjectType wrkspcObjType;
  private CommCode.ShareType shareType;
  private CommCode.Status objStatus;
  private UserVo creator;
  private UserVo owner;

  public String getWrkspcObjId() {
    return wrkspcObjId;
  }

  public void setWrkspcObjId(String wrkspcObjId) {
    this.wrkspcObjId = wrkspcObjId;
  }

  public CommCode.ObjectType getWrkspcObjType() {
    return wrkspcObjType;
  }

  public void setWrkspcObjType(CommCode.ObjectType wrkspcObjType) {
    this.wrkspcObjType = wrkspcObjType;
  }

  public CommCode.ShareType getShareType() {
    return shareType;
  }

  public void setShareType(CommCode.ShareType shareType) {
    this.shareType = shareType;
  }

  public CommCode.Status getObjStatus() {
    return objStatus;
  }

  public void setObjStatus(CommCode.Status objStatus) {
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

