package com.sktelecom.cep.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * DataStore.
 *
 * @author Administrator
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "datastore")
@NamedQuery(name = "DataStore.count", query = "select count(o) from DataStore o where upper(o.name) like ?1")
public class DataStore implements Serializable {

  /**
   * Type of DataStore.
   */
  public static enum Type {
    INTERNAL,
    DATABASE,
    HDFS
  }

  /**
   * if type is DATABASE, choose this one.
   */
  public static enum SubType {
    MYSQL,
    MSSQL,
    ORACLE,
    GENERIC
  }

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "datstore_id")
  private String id;

  @Column(name = "datstore_name")
  private String name;

  @Column(name = "datstore_type")
  @Enumerated(EnumType.STRING)
  private Type type;

  @Column(name = "datstore_subtype")
  @Enumerated(EnumType.STRING)
  private SubType subType;

  @Column(name = "host_name")
  private String hostName;

  @Column(name = "port_num")
  private int portNum;

  @Column(name = "cred_user_info")
  private String username;

  @Column(name = "cred_pass_info")
  private String password;

  @Column(name = "description")
  private String description;

  @Column(name = "update_date")
  private Timestamp updateTime;

  @ManyToOne
  @JoinColumn(name = "update_user_id", referencedColumnName = "id")
  private User updator;

  @OneToMany
  @JoinColumn(name = "datstore_id", referencedColumnName = "datstore_id")
  private List<DataStoreProperty> properties;

  @OneToMany(mappedBy = "dataStore", fetch = FetchType.LAZY)
  private Set<DataSource> dataSources = new HashSet<>();

  @PrePersist
  public void prePersist() {
    if (this.updateTime == null)
      this.updateTime = new Timestamp(System.currentTimeMillis());
  }

  public String getDatabaseUrl() {
    if (this.getType() == Type.DATABASE) {
      //checkstyle 버그로 인해서 indentation 이 이상하게 작동함. 여기 indentation 을 항상 유지해야함.
      switch (this.getSubType()) {
          case MSSQL:
            return "jdbc:mysql://" + this.getHostName() + ":" + this.getPortNum() + "/?useInformationSchema=true&useUnicode=true&characterEncoding=utf8";
          case ORACLE:
            return "jdbc:oracle:thin:@" + this.getHostName() + ":" + this.getPortNum();
          case MYSQL:
            return "jdbc:microsoft:sqlserver:" + this.getHostName() + ":" + this.getPortNum();
      }
    }

    return null;
  }

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

  public User getUpdator() {
    return updator;
  }

  public void setUpdator(User updator) {
    this.updator = updator;
  }

  public List<DataStoreProperty> getProperties() {
    return properties;
  }

  public void setProperties(List<DataStoreProperty> properties) {
    this.properties = properties;
  }
}
