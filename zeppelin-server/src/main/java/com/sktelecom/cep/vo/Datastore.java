package com.sktelecom.cep.vo;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sktelecom.cep.entity.DataStoreProperty;

/**
 * ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
@Alias("Datastore")
public class Datastore {

  /**
   * Type of interpreter.
   */
  public static enum Type {
    INTERNAL,
    DATABASE,
    HDFS
  }

  /**
   * SubType of interpreter.
   */
  public static enum SubType {
    MYSQL,
    MSSQL,
    ORACLE,
    GENERIC
  }

  private String id;
  private String name;
  private Type type;
  private SubType subType;
  private String hostName;
  private int portNum;
  private String username;
  private String password;
  private String description;
  private Timestamp updateTime;
  private String updateUserId;
  private List<DataStoreProperty> properties;

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

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public SubType getSubType() {
    return subType;
  }

  public void setSubType(SubType subType) {
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

  public Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

  public String getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(String updateUserId) {
    this.updateUserId = updateUserId;
  }

  public List<DataStoreProperty> getProperties() {
    return properties;
  }

  public void setProperties(List<DataStoreProperty> properties) {
    this.properties = properties;
  }
}
