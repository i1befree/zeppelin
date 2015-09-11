package com.sktelecom.cep.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * ValueObject.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "datasource")
@PrimaryKeyJoinColumn(name="datasource_id", referencedColumnName = "wrkspc_obj_id")
public class DataSource extends WorkspaceObject implements Serializable {
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "datstore_id")
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

  @Column(name = "update_user_id")
  private String updateUserId;

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

  public String getUpdateUserId() {
    return updateUserId;
  }

  public void setUpdateUserId(String updateUserId) {
    this.updateUserId = updateUserId;
  }
}
