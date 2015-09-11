package com.sktelecom.cep.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
