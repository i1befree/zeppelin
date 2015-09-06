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
public class Datastore {

  private String datstoreId;
  private String datstoreName;
  private String datstoreType;
  private String datstoreSubtype;
  private String hostName;
  private int portNum;
  private String credUserInfo;
  private String credPassInfo;
  private String description;
  private Date updateDate;
  private String updateUserId;
  
  public String getDatstoreId() {
    return datstoreId;
  }
  public void setDatstoreId(String datstoreId) {
    this.datstoreId = datstoreId;
  }
  public String getDatstoreName() {
    return datstoreName;
  }
  public void setDatstoreName(String datstoreName) {
    this.datstoreName = datstoreName;
  }
  public String getDatstoreType() {
    return datstoreType;
  }
  public void setDatstoreType(String datstoreType) {
    this.datstoreType = datstoreType;
  }
  public String getDatstoreSubtype() {
    return datstoreSubtype;
  }
  public void setDatstoreSubtype(String datstoreSubtype) {
    this.datstoreSubtype = datstoreSubtype;
  }
  public String getHostName() {
    return hostName;
  }
  public void setHostName(String hostName) {
    this.hostName = hostName;
  }
  public int getPortNum() {
    return portNum;
  }
  public void setPortNum(int portNum) {
    this.portNum = portNum;
  }
  public String getCredUserInfo() {
    return credUserInfo;
  }
  public void setCredUserInfo(String credUserInfo) {
    this.credUserInfo = credUserInfo;
  }
  public String getCredPassInfo() {
    return credPassInfo;
  }
  public void setCredPassInfo(String credPassInfo) {
    this.credPassInfo = credPassInfo;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
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
