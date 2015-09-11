package com.sktelecom.cep.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ValueObject.
 *
 * @author 박상민
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "workspace")
public class Workspace implements Serializable {
  public static enum Type {
    PERSONAL,
    SHARE
  }

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "wrkspc_id")
  private String wrkspcId;

  @Column(name = "wrkspc_name")
  private String wrkspcName;

  @Column(name = "description")
  private String description;

  @Column(name = "wrkspc_type")
  @Enumerated(EnumType.STRING)
  private Type wrkspcType;

  @Column(name = "admin_user_id")
  private String adminUserId;

  @Column(name = "update_date")
  private Date updateDate;

  @Column(name = "update_user_id")
  private String updateUserId;

  @OneToMany(mappedBy = "pk.workspace")
  private List<WorkspaceAssign> workspaceAssigns = new ArrayList<>();

  @OneToMany(mappedBy = "pk.workspace")
  private List<WorkspaceShare> workspaceShares = new ArrayList<>();

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

  public Type getWrkspcType() {
    return wrkspcType;
  }

  public void setWrkspcType(Type wrkspcType) {
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

  public List<WorkspaceShare> getWorkspaceShares() {
    return workspaceShares;
  }

  public void setWorkspaceShares(List<WorkspaceShare> workspaceShares) {
    this.workspaceShares = workspaceShares;
  }
}
