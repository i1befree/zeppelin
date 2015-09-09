package com.sktelecom.cep.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * ValueObject.
 *
 * @author 박상민
 */

@Entity
@Table(name = "datastore_props")
public class DataStoreProperty implements Serializable{
  @Id
  @Column(name = "datstore_id")
  private String id;

  @Column(name = "prop_name")
  private String name;

  @Column(name = "prop_value")
  private String value;

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

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
