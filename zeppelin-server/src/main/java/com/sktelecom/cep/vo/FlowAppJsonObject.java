package com.sktelecom.cep.vo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ValueObject.
 *
 * @author 박상민
 */
// / @cond doxygen don't parsing in here
@JsonIgnoreProperties(ignoreUnknown = true)
// / @endcond
public class FlowAppJsonObject {

  private String flowId;
  private String appId;
  private String appExecServerPath;

  private List<FlowResource> sources = new ArrayList<FlowResource>();
  private List<FlowResource> components = new ArrayList<FlowResource>();
  private List<FlowResource> sinks = new ArrayList<FlowResource>();

  public String getFlowId() {
    return flowId;
  }

  public void setFlowId(String flowId) {
    this.flowId = flowId;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getAppExecServerPath() {
    return appExecServerPath;
  }

  public void setAppExecServerPath(String appExecServerPath) {
    this.appExecServerPath = appExecServerPath;
  }

  public List<FlowResource> getSources() {
    return sources;
  }

  public void setSources(List<FlowResource> sources) {
    this.sources = sources;
  }

  public List<FlowResource> getComponents() {
    return components;
  }

  public void setComponents(List<FlowResource> components) {
    this.components = components;
  }

  public List<FlowResource> getSinks() {
    return sinks;
  }

  public void setSinks(List<FlowResource> sinks) {
    this.sinks = sinks;
  }

}
