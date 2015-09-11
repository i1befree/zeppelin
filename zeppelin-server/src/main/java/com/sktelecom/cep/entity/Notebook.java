package com.sktelecom.cep.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Date;

/**
 * Notebook
 */
@Entity
@PrimaryKeyJoinColumn(name = "note_id", referencedColumnName = "wrkspc_obj_id")
public class Notebook extends WorkspaceObject{
  @Column(name = "note_name")
  private String noteName;

  @Column(name = "update_date")
  private Date updateDate;

  @PrePersist
  protected void updateDates() {
    if (updateDate == null)
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
