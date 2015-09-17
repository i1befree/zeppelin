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
  private WorkspaceObjectVo workspaceObject;
  private WorkspaceVo workspace;
  private Date updateDate;
  private String updateUserId;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public WorkspaceObjectVo getWorkspaceObject() {
    return workspaceObject;
  }
  public void setWorkspaceObject(WorkspaceObjectVo workspaceObject) {
    this.workspaceObject = workspaceObject;
  }
  public WorkspaceVo getWorkspace() {
    return workspace;
  }
  public void setWorkspace(WorkspaceVo workspace) {
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
