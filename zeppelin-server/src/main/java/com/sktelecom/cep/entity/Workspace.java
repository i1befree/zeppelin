package com.sktelecom.cep.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Workspace implements Serializable{
  @Id
  @Column(name="wrkspc_id")
  private String wrkspcId;

  @Column(name="wrkspc_name")
  private String wrkspcName;

  @Column(name="description")
  private String description;

  @Column(name="wrkspc_type")
  private String wrkspcType;

  @Column(name="admin_user_id")
  private String adminUserId;

  @Column(name="update_date")
  private Date updateDate;

  @Column(name="update_user_id")
  private String updateUserId;

  @OneToMany(mappedBy = "workspace")
  private List<WorkspaceAssign> workspaceAssigns = new ArrayList<>();

  public String getWrkspcId() {
    return wrkspcId;
  }

  public void setWrkspcId(String wrkspcId) {
    this.wrkspcId = wrkspcId;
  }

  public String getWrkspcName() {
    return wrkspcName;
  }

  public void setWrkspcName(String wrkspcName) {
    this.wrkspcName = wrkspcName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getWrkspcType() {
    return wrkspcType;
  }

  public void setWrkspcType(String wrkspcType) {
    this.wrkspcType = wrkspcType;
  }

  public String getAdminUserId() {
    return adminUserId;
  }

  public void setAdminUserId(String adminUserId) {
    this.adminUserId = adminUserId;
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

  public List<WorkspaceAssign> getWorkspaceAssigns() {
    return workspaceAssigns;
  }

  public void setWorkspaceAssigns(List<WorkspaceAssign> workspaceAssigns) {
    this.workspaceAssigns = workspaceAssigns;
  }
}
