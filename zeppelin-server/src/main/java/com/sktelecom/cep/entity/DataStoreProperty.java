package com.sktelecom.cep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * ValueObject.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "datastore_props")
public class DataStoreProperty implements Serializable {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "props_id")
  private String id;

  @Column(name = "prop_name")
  private String name;

  @Column(name = "prop_value")
  private String value;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "datstore_id")
  private DataStore dataStore;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public DataStore getDataStore() {
    return dataStore;
  }

  public void setDataStore(DataStore dataStore) {
    this.dataStore = dataStore;
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
