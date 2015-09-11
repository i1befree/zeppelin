package com.sktelecom.cep.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * ValueObject.
 *
 * @author 박상민
 */

@SuppressWarnings("serial")
@Entity
@Table(name = "workspace_object")
@Inheritance(strategy=InheritanceType.JOINED)
public class WorkspaceObject implements Serializable{
  public enum Status{
    ACTIVE,
    DELTED
  }

  public enum ShareType{
    PRIVATE,
    SHARED
  }

  public enum ObjectType{
    DATASOURCE,
    NOTEBOOK
  }

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "wrkspc_obj_id")
  private String wrkspcObjId;

  @Column(name = "wrkspc_obj_type")
  @Enumerated(EnumType.STRING)
  private ObjectType wrkspcObjType;

  @Column(name = "share_type")
  @Enumerated(EnumType.STRING)
  private ShareType shareType;

  @Column(name = "obj_status")
  @Enumerated(EnumType.STRING)
  private Status objStatus;

  @OneToOne
  @JoinColumn(name="create_user_id", referencedColumnName = "id")
  private User creator;

  @OneToOne
  @JoinColumn(name="own_user_id", referencedColumnName = "id")
  private User owner;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.workspaceObject")
  private Set<WorkspaceAssign> workspaceAssigns = new HashSet<>();

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

  public ObjectType getWrkspcObjType() {
    return wrkspcObjType;
  }

  public void setWrkspcObjType(ObjectType wrkspcObjType) {
    this.wrkspcObjType = wrkspcObjType;
  }

  public ShareType getShareType() {
    return shareType;
  }

  public void setShareType(ShareType shareType) {
    this.shareType = shareType;
  }

  public Status getObjStatus() {
    return objStatus;
  }

  public void setObjStatus(Status objStatus) {
    this.objStatus = objStatus;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }
}
