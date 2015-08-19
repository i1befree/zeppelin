package com.sktelecom.cep.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class FlowResource {

  private int id;
  private String name;
  private String type_1;
  private String type_2;
  private String resource_desc;

  @JsonIgnore
  private List<FlowResourceProperty> properties = new ArrayList<FlowResourceProperty>();

  @JsonProperty("properties")
  private Map<String, String> propertyMap = new HashMap<String, String>();

  public Map<String, String> getPropertyMap() {
    return propertyMap;
  }

  public void setPropertyMap(Map<String, String> propertyMap) {
    this.propertyMap = propertyMap;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType_1() {
    return type_1;
  }

  public void setType_1(String type_1) {
    this.type_1 = type_1;
  }

  public String getType_2() {
    return type_2;
  }

  public void setType_2(String type_2) {
    this.type_2 = type_2;
  }

  public String getResource_desc() {
    return resource_desc;
  }

  public void setResource_desc(String resource_desc) {
    this.resource_desc = resource_desc;
  }

  public List<FlowResourceProperty> getProperties() {
    return properties;
  }

  public void setProperties(List<FlowResourceProperty> properties) {
    this.properties = properties;
  }

}
