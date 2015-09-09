package com.sktelecom.cep.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * ValueObject.
 *
 * @author 박상민
 */

@Entity
@Table(name = "WORKSPACE_SHARE")
public class WorkspaceShare implements Serializable{
  /**
   * 
   * @author Administrator
   *
   */
  @Embeddable
  public static class WorkspaceSharePk implements Serializable {
    @ManyToOne
    @JoinColumn(name = "wrkspc_id")
    private Workspace workspace;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    protected WorkspaceSharePk(){

    }

    public WorkspaceSharePk(Workspace workspace, User user){
      this.workspace = workspace;
      this.user = user;
    }

    public Workspace getWorkspace() {
      return workspace;
    }

    public User getUser() {
      return user;
    }
  }

  @EmbeddedId
  private WorkspaceSharePk pk;

  @Column(name = "update_date")
  private Date updateDate;

  @Column(name = "update_user_id")
  private String updateUserId;

  public WorkspaceSharePk getPk() {
    return pk;
  }

  public void setPk(WorkspaceSharePk pk) {
    this.pk = pk;
  }

  public Workspace getWorkspace(){
    return this.pk.workspace;
  }

  public User getUser(){
    return this.pk.user;
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
