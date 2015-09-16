package com.sktelecom.cep.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
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
@DiscriminatorValue("NOTEBOOK")
@PrimaryKeyJoinColumn(name = "note_id")
public class Notebook extends WorkspaceObject {
  @Column(name = "note_name")
  private String noteName;

  @Column(name = "update_date")
  private Date updateDate;

  @PrePersist
  @PreUpdate
  public void prePersist() {
    updateDate = new Date();
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
