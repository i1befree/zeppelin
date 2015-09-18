package com.sktelecom.cep.vo;

import java.io.Serializable;

import com.sktelecom.cep.entity.DataStore;

/**
 * ValueObject.
 *
 * @author 박상민
 */
@SuppressWarnings("serial")
public class DataStorePropertyVo implements Serializable{
  private String id;
  private String name;
  private String value;
  private DataStore dataStore;
  
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
  public DataStore getDataStore() {
    return dataStore;
  }
  public void setDataStore(DataStore dataStore) {
    this.dataStore = dataStore;
  }


}
