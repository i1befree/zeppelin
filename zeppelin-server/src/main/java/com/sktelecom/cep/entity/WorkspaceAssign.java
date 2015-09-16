package com.sktelecom.cep.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ValueObject.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "workspace_assign")
public class WorkspaceAssign implements Serializable {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "assign_id")
  private String id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "wrkspc_obj_id")
  private WorkspaceObject workspaceObject;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "wrkspc_id")
  private Workspace workspace;

  @Column(name = "update_date")
  private Date updateDate;

  @Column(name = "update_user_id")
  private String updateUserId;

  @PrePersist
  @PreUpdate
  public void prePersist() {
    updateDate = new Date();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public WorkspaceObject getWorkspaceObject() {
    return workspaceObject;
  }

  public void setWorkspaceObject(WorkspaceObject workspaceObject) {
    this.workspaceObject = workspaceObject;
    if (!workspaceObject.getWorkspaceAssigns().contains(this)) {
      workspaceObject.getWorkspaceAssigns().add(this);
    }
  }

  public Workspace getWorkspace() {
    return workspace;
  }

  public void setWorkspace(Workspace workspace) {
    this.workspace = workspace;
//    if (!workspace.getWorkspaceAssigns().contains(this)) {
//      workspace.getWorkspaceAssigns().add(this);
//    }
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
