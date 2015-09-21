package com.sktelecom.cep.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sktelecom.cep.common.CepConstant;
import com.sktelecom.cep.common.SimpleResultMessage;
import com.sktelecom.cep.service.DatastoreService;
import com.sktelecom.cep.vo.DatastoreVo;
import com.sktelecom.cep.vo.UserSessionVo;
import com.sktelecom.cep.vo.UserVo;

/**
 * Datastore -  Controller.
 *
 * @author 박상민
 */
@Controller
public class DatastoreController {

  @Inject
  private DatastoreService datastoreService;

  /**
   * datastore 생성.
   * 
   * @param datastore
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datastore/create", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage create(@RequestBody DatastoreVo datastore, HttpSession session) {
    UserSessionVo userSession = (UserSessionVo) session.getAttribute(CepConstant.USER_SESSION);
    UserVo user = new UserVo();
    user.setId(userSession.getId());
    
    datastoreService.create(datastore);
    return new SimpleResultMessage("SUCCESS", "사용자를 생성하였습니다.");
  }
  
  /**
   * datastore 목록 조회.
   * 
   * @param datastore
   * @return List<DatasourceVo>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datastore/getList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<DatastoreVo> getList(@RequestBody DatastoreVo datastore) {
    return datastoreService.getList(datastore);
  }
  
  
  
}
