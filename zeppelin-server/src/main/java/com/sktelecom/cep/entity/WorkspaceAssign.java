package com.sktelecom.cep.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

/**
 * ValueObject.
 *
 * @author 박상민
 */

@Entity
public class WorkspaceAssign implements Serializable {
  @ManyToOne
  @JoinColumn(name = "wrkspc_id")
  private WorkspaceObject workspaceObject;

  @ManyToOne
  @JoinColumn(name = "wrkspc_obj_id")
  private Workspace workspace;

  @Column(name = "update_date")
  private Date updateDate;

  @Column(name = "update_user_id")
  private String updateUserId;

  public WorkspaceObject getWorkspaceObject() {
    return workspaceObject;
  }

  public void setWorkspaceObject(WorkspaceObject workspaceObject) {
    this.workspaceObject = workspaceObject;
  }

  public Workspace getWorkspace() {
    return workspace;
  }

  public void setWorkspace(Workspace workspace) {
    this.workspace = workspace;
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
