package com.sktelecom.cep.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sktelecom.cep.common.CommCode;

/**
 * ValueObject.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatastoreVo implements Serializable {

  private String id;
  private String name;
  private CommCode.DataStoreType type;
  private CommCode.DataStoreSubType subType;
  private String hostName;
  private int portNum;
  private String username;
  private String password;
  private String description;
  private Date updateDate;
  private UserVo updator;
  //private List<DataStorePropertyVo> properties = new ArrayList<DataStorePropertyVo>();
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public CommCode.DataStoreType getType() {
    return type;
  }
  public void setType(CommCode.DataStoreType type) {
    this.type = type;
  }
  public CommCode.DataStoreSubType getSubType() {
    return subType;
  }
  public void setSubType(CommCode.DataStoreSubType subType) {
    this.subType = subType;
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
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
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
  public UserVo getUpdator() {
    return updator;
  }
  public void setUpdator(UserVo updator) {
    this.updator = updator;
  }
//  public List<DataStorePropertyVo> getProperties() {
//    return properties;
//  }
//  public void setProperties(List<DataStorePropertyVo> properties) {
//    this.properties = properties;
//  }
    
}
