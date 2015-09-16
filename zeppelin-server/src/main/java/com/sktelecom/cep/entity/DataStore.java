package com.sktelecom.cep.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sktelecom.cep.common.CommCode;

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

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "datstore_id")
  private String id;

  @Column(name = "datstore_name")
  private String name;

  @Column(name = "datstore_type")
  @Enumerated(EnumType.STRING)
  private CommCode.DataStoreType type;

  @Column(name = "datstore_subtype")
  @Enumerated(EnumType.STRING)
  private CommCode.DataStoreSubType subType;

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
  private Date updateDate;

  @ManyToOne
  @JoinColumn(name = "update_user_id", referencedColumnName = "id")
  private User updator;

  @OneToMany
  @JoinColumn(name = "datstore_id", referencedColumnName = "datstore_id")
  private List<DataStoreProperty> properties;

  @OneToMany(mappedBy = "dataStore", fetch = FetchType.LAZY)
  private Set<DataSource> dataSources = new HashSet<>();

  @PrePersist
  @PreUpdate
  public void prePersist() {
    updateDate = new Date();
  }

  public String getDatabaseUrl() {
    if (this.getType() == CommCode.DataStoreType.DATABASE) {
      //checkstyle 버그로 인해서 indentation 이 이상하게 작동함. 여기 indentation 을 항상 유지해야함.
      switch (this.getSubType()) {
          case MSSQL:
            return "jdbc:mysql://" + this.getHostName() + ":" + this.getPortNum() + "/?useInformationSchema=true&useUnicode=true&characterEncoding=utf8";
          case ORACLE:
            return "jdbc:oracle:thin:@" + this.getHostName() + ":" + this.getPortNum();
          case MYSQL:
            return "jdbc:microsoft:sqlserver:" + this.getHostName() + ":" + this.getPortNum();
          default:
            break;
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
