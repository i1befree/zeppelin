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
import com.sktelecom.cep.service.WorkspaceService;
import com.sktelecom.cep.vo.LayoutColumn;
import com.sktelecom.cep.vo.LayoutSchema;
import com.sktelecom.cep.vo.LayoutTable;
import com.sktelecom.cep.vo.UserSession;
import com.sktelecom.cep.vo.Workspace;

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


  /**
   * datasource 목록 조회.
   * 
   * @param Workspace
   * @param session
   * @return List<Workspace>
   */
  // / @cond doxygen don't parsing in here
  @RequestMapping(value = "/datasoruce/getList", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<Workspace> getList(@RequestBody Workspace workspace, HttpSession session) {
    UserSession userSession = (UserSession) session.getAttribute(CepConstant.USER_SESSION);
    workspace.setUpdateUserId(userSession.getId());
    List<Workspace> resultList = workspaceService.getList(workspace);
    return resultList;
  }
 
  @RequestMapping(value = "/datasoruce/loadDatasourceMetadata", method = RequestMethod.POST)
  @ResponseBody
  // / @endcond
  public List<LayoutSchema> loadDatasourceMetadata() throws Exception {
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
      
      ResultSet schemaRs = metadata.getSchemas();
      while (schemaRs.next()) {
        LayoutSchema info = new LayoutSchema();
        info.setName(schemaRs.getString("TABLE_SCHEM"));
        schemas.add(info);
      }
      schemaRs.close();
      if (schemas.size() == 0) {
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
        ResultSet tableRs = metadata.getTables(schema.getName(), schema.getName(), "", null);
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
