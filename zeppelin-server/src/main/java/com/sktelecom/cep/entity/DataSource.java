package com.sktelecom.cep.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * ValueObject.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "datasource")
@DiscriminatorValue("DATSRC")
@PrimaryKeyJoinColumn(name = "datasource_id")
public class DataSource extends WorkspaceObject {
  
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "datstore_id")
  @JsonManagedReference
  private DataStore dataStore;

  @Column(name = "datsrc_name")
  private String datsrcName;

  @Column(name = "container_name")
  private String containerName;

  @Column(name = "src_obj_name")
  private String srcObjName;

  @Column(name = "description")
  private String description;

  @Column(name = "update_date")
  private Date updateDate;

  @OneToOne
  @JoinColumn(name = "update_user_id", referencedColumnName = "id")
  private User lastModifiedUser;


  public DataStore getDataStore() {
    return dataStore;
  }

  public void setDataStore(DataStore dataStore) {
    this.dataStore = dataStore;
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

  public User getLastModifiedUser() {
    return lastModifiedUser;
  }

  public void setLastModifiedUser(User lastModifiedUser) {
    this.lastModifiedUser = lastModifiedUser;
  }
}
