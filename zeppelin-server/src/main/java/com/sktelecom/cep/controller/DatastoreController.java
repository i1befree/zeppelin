package com.sktelecom.cep.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sktelecom.cep.common.CepConstant;
import com.sktelecom.cep.common.CommCode;
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
    datastore.setUpdator(user);
    datastoreService.create(datastore);
    return new SimpleResultMessage("SUCCESS", "DataStore 를 생성하였습니다.");
  }
  
  /**
   * datastore 수정.
   * 
   * @param datastore
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datastore/update", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage update(@RequestBody DatastoreVo datastore, HttpSession session) {
    UserSessionVo userSession = (UserSessionVo) session.getAttribute(CepConstant.USER_SESSION);
    UserVo user = new UserVo();
    user.setId(userSession.getId());
    datastore.setUpdator(user);
    datastoreService.update(datastore);
    return new SimpleResultMessage("SUCCESS", "DataStore 를 수정하였습니다.");
  }
  
  /**
   * datastore 삭제.
   * 
   * @param datastore
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datastore/delete", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage delete(@RequestBody DatastoreVo datastore, HttpSession session) {
    datastoreService.update(datastore);
    return new SimpleResultMessage("SUCCESS", "DataStore 를 삭제하였습니다.");
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
  
  @RequestMapping(value = "/datastore/testConnection", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage testConnection(@RequestBody DatastoreVo datastore) {
    if (datastore.getType() == CommCode.DataStoreType.INTERNAL) {
      Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", datastore.getProperties().get("CLUSTER_NAME").getValue()).build();
      @SuppressWarnings({ "unused", "resource" })
      Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(datastore.getHostName(), datastore.getPortNum()));
      
    } else if (datastore.getType() == CommCode.DataStoreType.DATABASE) {
      Connection connection = null;
      try {
        Class.forName(datastore.getProperties().get("DRIVER_CLASS").getValue());
        connection = DriverManager.getConnection(datastore.getProperties().get("URL").getValue(), datastore.getUsername(), datastore.getPassword());
      } catch (Exception e) {
        throw new IllegalArgumentException(e.toString());
      } finally {
        if (connection != null) {
          try {
            connection.close();
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      }
    } else {
      throw new IllegalArgumentException("접속정보가 없습니다.");
    }
    return new SimpleResultMessage("SUCCESS", "접속 테스트를 성공하였습니다.");
  }
  
  
  
}
