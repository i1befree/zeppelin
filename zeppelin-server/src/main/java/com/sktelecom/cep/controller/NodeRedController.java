package com.sktelecom.cep.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Administrator
 *
 */
@Controller
public class NodeRedController {

  @RequestMapping(value = "/public/settings", method = { RequestMethod.GET,
      RequestMethod.POST })
  @ResponseBody
  public String settings() {
    return ""; 
  }

  @RequestMapping(value = "/public/library/flows", method = {
      RequestMethod.GET, RequestMethod.POST })
  @ResponseBody
  public String flows() {
    return "{}";
  }

  @RequestMapping(value = "/public/nodes", method = { RequestMethod.GET,
      RequestMethod.POST })
  @ResponseBody
  public String nodes() {
    return "";
  }
}
