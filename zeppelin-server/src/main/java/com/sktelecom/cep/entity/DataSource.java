package com.sktelecom.cep.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * ValueObject.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "datasource")
@PrimaryKeyJoinColumn(name = "datasource_id", referencedColumnName = "wrkspc_obj_id")
public class DataSource extends WorkspaceObject {
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

  @OneToOne
  @JoinColumn(name = "update_user_id", referencedColumnName = "id")
  private User lastModifiedUser;

  @Override
  @PrePersist
  public void prePersist() {
    super.prePersist();

    if (updateDate == null)
      this.updateDate = new Date();

    //저장시에 최종 수정자가 정해지지 않으면 생성자로 자동으로 채워준다.
    if (lastModifiedUser == null && this.getCreator() != null)
      this.lastModifiedUser = this.getCreator();
  }

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
