package com.sktelecom.cep.vo;

import java.io.Serializable;

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
public class WorkspaceSummaryVo implements Serializable {

  private String wrkspcId;
  private String wrkspcName;
  private String wrkspcType;
  private int totalOfNotebooks;
  private int totalOfQueries;
  private int totalOfDatasource;
  private int totalOfMembers;
  private int totalOfJobs;
  
  public String getWrkspcType() {
    return wrkspcType;
  }
  public void setWrkspcType(String wrkspcType) {
    this.wrkspcType = wrkspcType;
  }
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
  public int getTotalOfNotebooks() {
    return totalOfNotebooks;
  }
  public void setTotalOfNotebooks(int totalOfNotebooks) {
    this.totalOfNotebooks = totalOfNotebooks;
  }
  public int getTotalOfQueries() {
    return totalOfQueries;
  }
  public void setTotalOfQueries(int totalOfQueries) {
    this.totalOfQueries = totalOfQueries;
  }
  public int getTotalOfDatasource() {
    return totalOfDatasource;
  }
  public void setTotalOfDatasource(int totalOfDatasource) {
    this.totalOfDatasource = totalOfDatasource;
  }
  public int getTotalOfMembers() {
    return totalOfMembers;
  }
  public void setTotalOfMembers(int totalOfMembers) {
    this.totalOfMembers = totalOfMembers;
  }
  public int getTotalOfJobs() {
    return totalOfJobs;
  }
  public void setTotalOfJobs(int totalOfJobs) {
    this.totalOfJobs = totalOfJobs;
  }
  
}
