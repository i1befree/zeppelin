package com.sktelecom.cep.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * ValueObject.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
public class WorkspaceAssignVo implements Serializable {
  private String id;
  private WorkspaceObject workspaceObject;
  private Workspace workspace;
  private Date updateDate;
  private String updateUserId;
  
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
