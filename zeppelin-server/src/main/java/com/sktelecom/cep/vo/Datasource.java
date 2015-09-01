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
public class Datasource extends Paging {

  private String datasourceId;
  private String datstoreId;
  private String datsrcName;
  private String containerName;
  private String srcObjName;
  private String description;
  private Date updateDate;
  private String updateUserId;
  
  
  public String getDatasourceId() {
    return datasourceId;
  }
  public void setDatasourceId(String datasourceId) {
    this.datasourceId = datasourceId;
  }
  public String getDatstoreId() {
    return datstoreId;
  }
  public void setDatstoreId(String datstoreId) {
    this.datstoreId = datstoreId;
  }
  public String getDatsrcName() {
    return datsrcName;
  }
  public void setDatsrcName(String datsrcName) {
    this.datsrcName = datsrcName;
  }
  public String getContainerName() {
    return containerName;
  }
  public void setContainerName(String containerName) {
    this.containerName = containerName;
  }
  public String getSrcObjName() {
    return srcObjName;
  }
  public void setSrcObjName(String srcObjName) {
    this.srcObjName = srcObjName;
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
