package com.sktelecom.cep.controller;

import java.util.ArrayList;
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
import com.sktelecom.cep.service.DatasourceService;
import com.sktelecom.cep.vo.DatasourceVo;
import com.sktelecom.cep.vo.DatastoreVo;
import com.sktelecom.cep.vo.LayoutSchema;
import com.sktelecom.cep.vo.UserSessionVo;
import com.sktelecom.cep.vo.UserVo;
import com.sktelecom.cep.vo.WorkspaceAssignVo;

/**
 * Datasource -  Controller.
 *
 * @author 박상민
 */
@Controller
public class DatasourceController {

  @Inject
  private DatasourceService datasourceService;

  /**
   * datasource 생성.
   * 
   * @param datasource
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datasource/create", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage create(@RequestBody DatasourceVo datasource, HttpSession session) {
    UserSessionVo userSession = (UserSessionVo) session.getAttribute(CepConstant.USER_SESSION);
    UserVo user = new UserVo();
    user.setId(userSession.getId());
    datasource.setCreator(user);
    datasourceService.create(datasource);
    return new SimpleResultMessage("SUCCESS", "사용자를 생성하였습니다.");
  }
  
  /**
   * DatasourceVo 정보 조회.
   * 
   * @param DatasourceVo
   * @return List<Workspace>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datasource/getDatasourceObjectInfo", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public DatasourceVo getDatasourceObjectInfo(@RequestBody DatasourceVo datasourceVo) {
    return datasourceService.getDatasourceObjectInfo(datasourceVo);
  }
  
  /**
   * datasource 목록 조회.
   * 
   * @param datasource
   * @return List<Workspace>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datasource/getList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<DatasourceVo> getList(@RequestBody DatasourceVo datasource) {
    return datasourceService.getList(datasource);
  }
  
  /**
   * datasource 에 workspace 를 할당한다.
   * 
   * @param DatasourceVo
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datasource/saveAssignWorkspace", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage saveAssignWorkspace(@RequestBody DatasourceVo datasourceVo, HttpSession session) {
    UserSessionVo userSession = (UserSessionVo) session.getAttribute(CepConstant.USER_SESSION);
    
    List<WorkspaceAssignVo> workspaceAssigns = new ArrayList<WorkspaceAssignVo>();
    for (String wrkspcId : datasourceVo.getWrkspcIds()) {
      WorkspaceAssignVo assign = new WorkspaceAssignVo();
      assign.setWrkspcId(wrkspcId);
      assign.setUpdateUserId(userSession.getId());
      workspaceAssigns.add(assign);
    }
    datasourceVo.setWorkspaceAssigns(workspaceAssigns);
    
    datasourceService.saveAssignWorkspace(datasourceVo);
    return new SimpleResultMessage("SUCCESS", "데이타소스를 작업공간에 할당하였습니다");
  }
   
  @RequestMapping(value = "/datasource/loadDatasourceMetadata", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<LayoutSchema> loadDatasourceMetadata(@RequestBody DatastoreVo datastoreVo) throws Exception {
    List<LayoutSchema> resultList = datasourceService.loadDatasourceMetadata(datastoreVo);
    return resultList;
  }

  /**
   * datastore 전체 목록 조회
   * @param datasource
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/datasource/getDatastoreAllList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<DatastoreVo> getDatastoreAllList(@RequestBody DatastoreVo dataStore) throws Exception {
    List<DatastoreVo> resultList = datasourceService.getDatastoreAllList(dataStore);
    return resultList;
  }
  
}
