package com.sktelecom.cep.entity;

import com.sktelecom.cep.vo.WorkspaceAssign;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * ValueObject.
 *
 * @author 박상민
 */

@Entity
@Table(name = "workspace_object")
public class WorkspaceObject implements Serializable{
  @Id
  @Column(name = "wrkspc_obj_id")
  private String wrkspcObjId;

  @Column(name = "wrkspc_obj_type")
  private String wrkspcObjType;

  @Column(name = "share_type")
  private String shareType;

  @Column(name = "obj_status")
  private String objStatus;

  @Column(name = "create_user_id")
  private String createUserId;

  @Column(name = "own_user_id")
  private String ownUserId;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "workspaceObject")
  private Set<WorkspaceAssign> workspaceAssigns = new HashSet<>();

  @OneToOne(fetch = FetchType.EAGER)
  private DataSource dataSource;

  public Set<WorkspaceAssign> getWorkspaceAssigns() {
    return workspaceAssigns;
  }

  public void setWorkspaceAssigns(Set<WorkspaceAssign> workspaceAssigns) {
    this.workspaceAssigns = workspaceAssigns;
  }

  public String getWrkspcObjId() {
    return wrkspcObjId;
  }

  public void setWrkspcObjId(String wrkspcObjId) {
    this.wrkspcObjId = wrkspcObjId;
  }

  public String getWrkspcObjType() {
    return wrkspcObjType;
  }

  public void setWrkspcObjType(String wrkspcObjType) {
    this.wrkspcObjType = wrkspcObjType;
  }

  public String getShareType() {
    return shareType;
  }

  public void setShareType(String shareType) {
    this.shareType = shareType;
  }

  public String getObjStatus() {
    return objStatus;
  }

  public void setObjStatus(String objStatus) {
    this.objStatus = objStatus;
  }

  public String getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(String createUserId) {
    this.createUserId = createUserId;
  }

  public String getOwnUserId() {
    return ownUserId;
  }

  public void setOwnUserId(String ownUserId) {
    this.ownUserId = ownUserId;
  }
}
