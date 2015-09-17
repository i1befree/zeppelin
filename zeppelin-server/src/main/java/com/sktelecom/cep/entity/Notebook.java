package com.sktelecom.cep.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Notebook
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "notebook")
@PrimaryKeyJoinColumn(name = "wrkspc_obj_id")
public class Notebook extends WorkspaceObject {
  @Column(name = "note_id")
  private String noteId;

  @Column(name = "note_name")
  private String noteName;

  @Column(name = "update_date")
  private Date updateDate;

  @PrePersist
  @PreUpdate
  public void prePersist() {
    updateDate = new Date();
  }

  public String getNoteId() {
    return noteId;
  }

  public void setNoteId(String noteId) {
    this.noteId = noteId;
  }

  public String getNoteName() {
    return noteName;
  }

  public void setNoteName(String noteName) {
    this.noteName = noteName;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }
}
