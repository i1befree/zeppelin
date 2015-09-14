package com.sktelecom.cep.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ValueObject.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "user")
public class User implements Serializable {
  @Id
  @Column(name = "id", nullable = false)
  private String id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "passwd", nullable = false)
  private String passwd;

  @Column(name = "email")
  private String email;

  @Column(name = "tel")
  private String tel;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "update_date")
  private Date updateDate;

  @Column(name = "update_user_id")
  private String updateUserId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "wrkspc_id")
  private Workspace workspace;

  //차후 Relationship의 갱신이 필요하다(이유 : Role 기반 권한 관리가 될 경우 Role 쪽이 복잡해 질 수 있음).
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_grp_cd", referencedColumnName = "role_cd")
  private Role role;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  private List<WorkspaceShare> workspaceShares = new ArrayList<WorkspaceShare>();

  @PrePersist
  public void prePersist() {
    if (this.updateDate == null)
      this.updateDate = new Date();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPasswd() {
    return passwd;
  }

  public void setPasswd(String passwd) {
    this.passwd = passwd;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
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

  public Workspace getWorkspace() {
    return workspace;
  }

  public void setWorkspace(Workspace workspace) {
    this.workspace = workspace;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public List<WorkspaceShare> getWorkspaceShares() {
    return workspaceShares;
  }

  public void setWorkspaceShares(List<WorkspaceShare> workspaceShares) {
    this.workspaceShares = workspaceShares;
  }

  
}
