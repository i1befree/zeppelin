package com.sktelecom.cep.entity;

import com.sktelecom.cep.vo.DatastoreProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="datastore")
public class DataStore  implements Serializable {
  public static enum Type {
    INTERNAL,
    DATABASE,
    HDFS
  }

  public static enum SubType {
    MYSQL,
    MSSQL,
    ORACLE,
    GENERIC
  }

  @Id
  @GeneratedValue
  @Column(name="datstore_id")
  private String id;

  @Column(name="datstore_name")
  private String name;

  @Column(name="datstore_type")
  @Enumerated(EnumType.STRING)
  private Type type;

  @Column(name="datstore_subtype")
  @Enumerated(EnumType.STRING)
  private SubType subType;

  @Column(name="host_name")
  private String hostName;

  @Column(name="port_num")
  private int portNum;

  @Column(name="cred_user_info")
  private String username;

  @Column(name="cred_pass_info")
  private String password;

  @Column(name="description")
  private String description;

  @Column(name="update_date")
  private Timestamp updateTime;

  @Column(name="update_user_id")
  private String updateUserId;

  @OneToMany
  @JoinColumn(name = "datstore_id", referencedColumnName="datstore_id")
  private List<DatastoreProperty> properties;

  @OneToMany(mappedBy = "dataStore", fetch = FetchType.LAZY)
  private Set<DataSource> dataSources = new HashSet<>();

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

  public List<DatastoreProperty> getProperties() {
    return properties;
  }

  public void setProperties(List<DatastoreProperty> properties) {
    this.properties = properties;
  }
}
