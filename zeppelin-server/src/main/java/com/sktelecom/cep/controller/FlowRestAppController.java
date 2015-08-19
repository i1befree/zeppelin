package com.sktelecom.cep.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sktelecom.cep.service.FlowRestAppService;
import com.sktelecom.cep.vo.FlowInfo;

/**
 * app 연관 REST Api - Controller no session.
 *
 * @author 박상민
 */
@Controller
public class FlowRestAppController {

  @Inject
  private FlowRestAppService flowRestAppService;

  /**
   * APP 상태 업데이트.
   * 
   * 요청자는 비동기 호출을 하고, 리턴값은 없음
   * 
   * @param list
   *          : [ {"flowId" : "1", "appId": "1", "appStatus": "STOP"
   *          ,"message":""}, {"flowId" : "2", "appId": "2", "appStatus":
   *          "START","message":""} ]
   * 
   * @return
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/api/updateApp", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public void updateAppStatusList(@RequestBody List<FlowInfo> list) {
    for (FlowInfo info : list) {
      flowRestAppService.updateAppStatus(info);
    }
  }

}
