package com.sktelecom.cep.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="WORKSPACE_SHARE")
public class WorkspaceShare implements Serializable{
  @ManyToOne
  @JoinColumn(name="wrkspc_id")
  private Workspace workspace;

  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;

  @Column(name="update_date")
  private Date updateDate;

  @Column(name="update_user_id")
  private String updateUserId;

  public Workspace getWorkspace() {
    return workspace;
  }

  public void setWorkspace(Workspace workspace) {
    this.workspace = workspace;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
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
