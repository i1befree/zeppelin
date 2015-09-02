package com.sktelecom.cep.controller;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sktelecom.cep.common.CepConstant;
import com.sktelecom.cep.common.SimpleResultMessage;
import com.sktelecom.cep.service.DatasourceService;
import com.sktelecom.cep.service.WorkspaceService;
import com.sktelecom.cep.vo.Datasource;
import com.sktelecom.cep.vo.LayoutColumn;
import com.sktelecom.cep.vo.LayoutSchema;
import com.sktelecom.cep.vo.LayoutTable;
import com.sktelecom.cep.vo.UserSession;
import com.sktelecom.cep.vo.Workspace;
import com.sktelecom.cep.vo.WorkspaceAssign;
import com.sktelecom.cep.vo.WorkspaceObject;

/**
 * Datasource -  Controller.
 *
 * @author 박상민
 */
@Controller
public class DatasourceController {

  private static final Logger logger = LoggerFactory
      .getLogger(DatasourceController.class);

  @Inject
  private WorkspaceService workspaceService;

  @Inject
  private DatasourceService datasourceService;

  /**
   * datasource 생성.
   * 
   * @param user
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datasource/create", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage create(@RequestBody Datasource datasource, HttpSession session) {
    SimpleResultMessage message = new SimpleResultMessage("FAIL", "데이타소스 생성을 실패하였습니다.");

    UserSession userSession = (UserSession) session.getAttribute(CepConstant.USER_SESSION);
    datasource.setUpdateUserId(userSession.getId());
    int resultInt = datasourceService.create(datasource);
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("데이타소스를 생성하였습니다.");
    }
    return message;
  }
  
  /**
   * workspaceObject 정보 조회.
   * 
   * @param Workspace
   * @param session
   * @return List<Workspace>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datasource/getWorkspaceObjectInfo", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public WorkspaceObject getWorkspaceObjectInfo(@RequestBody WorkspaceObject workspaceObject) {
    WorkspaceObject info = datasourceService.getWorkspaceObjectInfo(workspaceObject);
    return info;
  }
  
  /**
   * datasource 목록 조회.
   * 
   * @param Workspace
   * @param session
   * @return List<Workspace>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datasource/getList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<Datasource> getList(@RequestBody Datasource datasource) {
    List<Datasource> resultList = datasourceService.getList(datasource);
    return resultList;
  }
  
  /**
   * Workspace 목록 조회.
   * 
   * @param Workspace
   * @param session
   * @return List<Workspace>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datasource/getWorkspaceList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<Workspace> getWorkspaceList(@RequestBody Workspace workspace) {
    List<Workspace> resultList = datasourceService.getWorkspaceList(workspace);
    return resultList;
  }
  
  /**
   * 할당된 Workspace 목록 조회.
   * 
   * @param Workspace
   * @param session
   * @return List<Workspace>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datasource/getAssignedWorkspaceList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<Workspace> getAssignedWorkspaceList(@RequestBody WorkspaceAssign workspaceAssign) {
    List<Workspace> resultList = datasourceService.getAssignedWorkspaceList(workspaceAssign);
    return resultList;
  }
  
  /**
   * datasource 에 workspace 를 할당한다.
   * 
   * @param workspaceObject
   * @return SimpleResultMessage : rsCode[FAIL|SUCCESS]
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datasource/saveAssignWorkspace", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public SimpleResultMessage saveAssignWorkspace(@RequestBody WorkspaceObject workspaceObject, HttpSession session) {
    SimpleResultMessage message = new SimpleResultMessage("FAIL", "데이타소스를 작업공간에 할당하기를 실패하였습니다.");

    UserSession userSession = (UserSession) session.getAttribute(CepConstant.USER_SESSION);
    for (WorkspaceAssign info : workspaceObject.getWorkspaceAssigns()) {
      info.setUpdateUserId(userSession.getId());
    }
    int resultInt = datasourceService.saveAssignWorkspace(workspaceObject);
    if (resultInt > 0) {
      message.setRsCode("SUCCESS");
      message.setRsMessage("데이타소스를 작업공간에 할당하기를 성공하였습니다");
    }
    return message;
  }
   
  @RequestMapping(value = "/datasource/loadDatasourceMetadata", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<LayoutSchema> loadDatasourceMetadata(@RequestBody Datasource datasource) throws Exception {
    List<LayoutSchema> schemas = new ArrayList<LayoutSchema>();
    
    String DRIVER = "com.mysql.jdbc.Driver";
    String URL = "jdbc:mysql://52.68.186.228:3306/?useInformationSchema=true&useUnicode=true&characterEncoding=utf8";
    String USERNAME = "trip";
    String PASSWORD = "!Trip@2015";
    Connection connection = null;
    try {
      Class.forName(DRIVER);
      connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      
      DatabaseMetaData metadata = connection.getMetaData();
      
      boolean isSchema = true;
      ResultSet schemaRs = metadata.getSchemas();
      while (schemaRs.next()) {
        LayoutSchema info = new LayoutSchema();
        info.setName(schemaRs.getString("TABLE_SCHEM"));
        schemas.add(info);
      }
      schemaRs.close();
      if (schemas.size() == 0) {
        isSchema = false;
        ResultSet catelogRs = metadata.getCatalogs();
        while (catelogRs.next()) {
          LayoutSchema info = new LayoutSchema();
          info.setName(catelogRs.getString("TABLE_CAT"));
          schemas.add(info);
        }
        catelogRs.close();
      }

      for (LayoutSchema schema : schemas) {
        List<LayoutTable> tables = new ArrayList<LayoutTable>();
        ResultSet tableRs = null;
        if (isSchema) {
          tableRs = metadata.getTables(null, schema.getName(), null, new String[] {"TABLE"});
        } else {
          tableRs = metadata.getTables(schema.getName(), null, null, new String[] {"TABLE"});
        }
        while (tableRs.next()) {
          LayoutTable table = new LayoutTable();
          table.setName(tableRs.getString("TABLE_NAME"));
          table.setComment(tableRs.getString("REMARKS"));
          tables.add(table);
          
          List<LayoutColumn> columns = new ArrayList<LayoutColumn>();
          ResultSet columnRs = metadata.getColumns(schema.getName(), schema.getName(), table.getName(), null);
          while (columnRs.next()) {
            LayoutColumn column = new LayoutColumn();
            column.setName(columnRs.getString("COLUMN_NAME"));
            column.setType(columnRs.getString("TYPE_NAME"));
            column.setSize(columnRs.getInt("COLUMN_SIZE"));
            column.setComment(columnRs.getString("REMARKS"));
            columns.add(column);
          }
          table.setColumns(columns);
          columnRs.close();
        } 
        schema.setTables(tables);
        tableRs.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        connection.close();
      }
    }
    return schemas;
  }

}
