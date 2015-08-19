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
public class FlowJsonObject {

  private List<FlowObject> nodes = new ArrayList<FlowObject>();

  public List<FlowObject> getNodes() {
    return nodes;
  }

  public void setNodes(List<FlowObject> nodes) {
    this.nodes = nodes;
  }

}
