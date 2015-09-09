package com.sktelecom.cep.entity;

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
@Table(name = "workspace_assign")
public class WorkspaceAssign implements Serializable {
  /**
   * 
   * @author Administrator
   *
   */
  @Embeddable
  public static class WorkspaceAssignPk implements Serializable {
    @ManyToOne
    @JoinColumn(name = "wrkspc_id")
    private WorkspaceObject workspaceObject;

    @ManyToOne
    @JoinColumn(name = "wrkspc_obj_id")
    private Workspace workspace;

    protected WorkspaceAssignPk(){

    }

    public WorkspaceAssignPk(WorkspaceObject workspaceObject, Workspace workspace){
      this.workspaceObject = workspaceObject;
      this.workspace = workspace;
    }

    public WorkspaceObject getWorkspaceObject() {
      return workspaceObject;
    }

    public Workspace getWorkspace() {
      return workspace;
    }
  }

  @EmbeddedId
  private WorkspaceAssignPk pk;

  @Column(name = "update_date")
  private Date updateDate;

  @Column(name = "update_user_id")
  private String updateUserId;

  public WorkspaceAssignPk getPk() {
    return pk;
  }

  public void setPk(WorkspaceAssignPk pk) {
    this.pk = pk;
  }

  public WorkspaceObject getWorkspaceObject() {
    return pk.workspaceObject;
  }

  public Workspace getWorkspace() {
    return pk.workspace;
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
