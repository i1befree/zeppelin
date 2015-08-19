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
public class FlowInfo extends Paging {

  private String flowId;
  private String flowTitle;
  private String appExecServerPath;
  private String appId;
  private String appStatus;
  private String flowJsonData;
  private String createId;
  private String updateId;
  private Date createDate;
  private Date updateDate;
  private String createIdName;
  private String updateIdName;

  // db 컬럼 이외
  private String appStatusName;

  public String getAppStatusName() {
    return appStatusName;
  }

  public void setAppStatusName(String appStatusName) {
    this.appStatusName = appStatusName;
  }

  public String getAppExecServerPath() {
    return appExecServerPath;
  }

  public void setAppExecServerPath(String appExecServerPath) {
    this.appExecServerPath = appExecServerPath;
  }

  public String getCreateIdName() {
    return createIdName;
  }

  public void setCreateIdName(String createIdName) {
    this.createIdName = createIdName;
  }

  public String getUpdateIdName() {
    return updateIdName;
  }

  public void setUpdateIdName(String updateIdName) {
    this.updateIdName = updateIdName;
  }

  public String getFlowId() {
    return flowId;
  }

  public void setFlowId(String flowId) {
    this.flowId = flowId;
  }

  public String getFlowTitle() {
    return flowTitle;
  }

  public void setFlowTitle(String flowTitle) {
    this.flowTitle = flowTitle;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getAppStatus() {
    return appStatus;
  }

  public void setAppStatus(String appStatus) {
    this.appStatus = appStatus;
  }

  public String getFlowJsonData() {
    return flowJsonData;
  }

  public void setFlowJsonData(String flowJsonData) {
    this.flowJsonData = flowJsonData;
  }

  public String getCreateId() {
    return createId;
  }

  public void setCreateId(String createId) {
    this.createId = createId;
  }

  public String getUpdateId() {
    return updateId;
  }

  public void setUpdateId(String updateId) {
    this.updateId = updateId;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

}
