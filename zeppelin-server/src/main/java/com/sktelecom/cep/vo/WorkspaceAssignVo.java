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
/// @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
/// @endcond
public class WorkspaceAssignVo implements Serializable {
  private String assignId;
  private String wrkspcObjId;
  private String wrkspcId;
  private Date updateDate;
  private String updateUserId;
  
  public String getAssignId() {
    return assignId;
  }

  public void setAssignId(String assignId) {
    this.assignId = assignId;
  }

  public String getWrkspcObjId() {
    return wrkspcObjId;
  }

  public void setWrkspcObjId(String wrkspcObjId) {
    this.wrkspcObjId = wrkspcObjId;
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
