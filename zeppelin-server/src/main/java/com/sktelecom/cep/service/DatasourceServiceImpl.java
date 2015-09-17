package com.sktelecom.cep.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sktelecom.cep.common.CommCode;
import com.sktelecom.cep.entity.DataSource;
import com.sktelecom.cep.entity.DataStore;
import com.sktelecom.cep.entity.User;
import com.sktelecom.cep.exception.BizException;
import com.sktelecom.cep.repository.DataSourceRepository;
import com.sktelecom.cep.service.mapping.DatasourceServiceMapper;
import com.sktelecom.cep.vo.DatasourceVo;
import com.sktelecom.cep.vo.DatastoreVo;
import com.sktelecom.cep.vo.LayoutColumn;
import com.sktelecom.cep.vo.LayoutSchema;
import com.sktelecom.cep.vo.LayoutTable;
import com.sktelecom.cep.vo.WorkspaceObjectVo;

/**
 * 데이타소스 - 데이타소스 CRUD 담당 Service 구현체.
 *
 * @author 박상민
 */
@Service
public class DatasourceServiceImpl implements DatasourceService {

  static final Logger LOG = LoggerFactory.getLogger(DatasourceServiceImpl.class);

  @Inject
  private DataSourceRepository dataSourceRepository;
  
  @Inject
  private DatasourceServiceMapper datasourceServiceMapper;

  private Map<String, List<LayoutSchema>> layoutMap = new ConcurrentHashMap<String, List<LayoutSchema>>();
  
  
  @Override
  public void create(DatasourceVo datasource) {
    User user = new User();
    user.setId(datasource.getCreator().getId());
    
    DataStore dataStore = new DataStore();
    dataStore.setId(datasource.getDatastore().getId());
    
    com.sktelecom.cep.entity.DataSource dataSourceObject = new com.sktelecom.cep.entity.DataSource();    
    datasourceServiceMapper.mapVoToEntity(datasource, dataSourceObject);
    
    dataSourceObject.setCreator(user);
    dataSourceObject.setObjStatus(CommCode.WorkspaceObjectStatus.CREATED);
    dataSourceObject.setOwner(user);
    dataSourceObject.setShareType(CommCode.WorkspaceObjectShareType.NONE);
    dataSourceObject.setWrkspcObjType(CommCode.WorkspaceObjectType.DATSRC);
    
    dataSourceObject.setLastModifiedUser(user);
    dataSourceObject.setDataStore(dataStore);
    
    dataSourceRepository.save(dataSourceObject);
    
  }

  @Override
  public List<DatasourceVo> getList(DatasourceVo datasource) {
//    List<DatasourceVo> datasourceList = datasourceDao.getList(datasource);
//    return datasourceList;
    return null;
  }

  @Override
  public int saveAssignWorkspace(WorkspaceObjectVo workspaceObject) {
//    if ("ALL".equals(workspaceObject.getShareType())) {
//      //remove all
//      //update sharetype
//      WorkspaceAssign workspaceAssign = new WorkspaceAssign();
//      workspaceAssign.setWrkspcObjId(workspaceObject.getWrkspcObjId());
//      workspaceAssignDao.deleteByWrkspcObjId(workspaceAssign);
//      
//      //데이타소스를 모든 갖업공간에 할당한다.
//      workspaceObjectDao.updateForShareType(workspaceObject);
//    } else {
//      //remove all
//      //add
//      WorkspaceAssign workspaceAssign = new WorkspaceAssign();
//      workspaceAssign.setWrkspcObjId(workspaceObject.getWrkspcObjId());
//      workspaceAssignDao.deleteByWrkspcObjId(workspaceAssign);
//      
//      for (WorkspaceAssign info : workspaceObject.getWorkspaceAssigns()) {
//        info.setAssignId(UUID.randomUUID().toString());
//        info.setWrkspcObjId(workspaceObject.getWrkspcObjId());
//        workspaceAssignDao.create(info);
//      }
//      //데이타소스를 모든 갖업공간에 할당한다.
//      workspaceObject.setShareType("NONE");
//      workspaceObjectDao.updateForShareType(workspaceObject);
//    }
    return 1;
  }

  @Override
  public DatasourceVo getDatasourceObjectInfo(DatasourceVo datasourceVo) {
    DataSource datasource = dataSourceRepository.findOne(datasourceVo.getWrkspcObjId());
  
    return datasourceServiceMapper.getDatasourceVoWithAssignedWorkspaceFromEntity(datasource);
  }

  @Override
  public List<LayoutSchema> loadDatasourceMetadata(DatasourceVo datasource) {
    List<LayoutSchema> schemas = new ArrayList<LayoutSchema>();
    
//    DatastoreVo pDatastoreInfo = new DatastoreVo();
//    pDatastoreInfo.setId(datasource.getDatstoreId());
//    DatastoreVo datastoreInfo = datastoreDao.getInfo(pDatastoreInfo);
//    
//    schemas = layoutMap.get(datasource.getDatstoreId());
//    if (schemas != null) {
//      return schemas;
//    }
//    
//    if (CommCode.DataStoreType.INTERNAL == datastoreInfo.getType()) {
//      schemas = getElasticSearch(datastoreInfo);
//    } else if (CommCode.DataStoreType.DATABASE == datastoreInfo.getType()) {
//      schemas = getDatabase(datastoreInfo);
//    } else if (CommCode.DataStoreType.HDFS == datastoreInfo.getType()) {
//      
//    }
    return schemas;
  }
  
  private List<LayoutSchema> getElasticSearch(DatastoreVo datastoreInfo) {
    List<LayoutSchema> schemas = new ArrayList<LayoutSchema>();
    
    Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "cep").build();
    Client client = new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress(datastoreInfo.getHostName(), datastoreInfo.getPortNum()));
    try {
      GetIndexResponse indexResponse = client.admin().indices().prepareGetIndex().get();
      ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = indexResponse.getMappings();
      String[] indexes = indexResponse.getIndices();
      for (String indexName : indexes) {
        LayoutSchema schema = new LayoutSchema();
        schema.setName(indexName);
        schemas.add(schema);
        
        List<LayoutTable> tables = new ArrayList<LayoutTable>();
        ImmutableOpenMap<String, MappingMetaData> indexMappings = mappings.get(indexName);
        for (Iterator<String> iterator = indexMappings.keysIt(); iterator.hasNext();) {
          String typeName = iterator.next();
          MappingMetaData mappingMetadata = indexMappings.get(typeName);
          LayoutTable table = new LayoutTable();
          table.setName(typeName);
          table.setComment("");
          tables.add(table);
          
          Map<String, Object> source = mappingMetadata.sourceAsMap();
          Map<String, Object> properties = (Map<String, Object>) source.get("properties");
          if (properties != null) {
            List<LayoutColumn> columns = new ArrayList<LayoutColumn>();
            for (String propertyName : properties.keySet()) {
              Map<String, String> property = (Map<String, String>) properties.get(propertyName);
              LayoutColumn column = new LayoutColumn();
              column.setName(propertyName);
              column.setType(property.get("type"));
              column.setSize(null);
              column.setComment("");
              columns.add(column);
            }
            table.setColumns(columns);
          }
        }
        schema.setTables(tables);
      }
    } catch (Exception e) {
      
      e.printStackTrace();
    } 
    
    return schemas;
  }
  
  private List<LayoutSchema> getDatabase(DatastoreVo datastoreInfo) {
    List<LayoutSchema> schemas = new ArrayList<LayoutSchema>();
    
    //default MYSQL
    String DRIVER = "com.mysql.jdbc.Driver";
    String URL = "jdbc:mysql://" + datastoreInfo.getHostName() + ":" + datastoreInfo.getPortNum() + "/?useInformationSchema=true&useUnicode=true&characterEncoding=utf8";
    
    if (CommCode.DataStoreSubType.MYSQL == datastoreInfo.getSubType()) {
      // DRIVER = "com.mysql.jdbc.Driver";
      // URL = "jdbc:mysql://" + datastoreInfo.getHostName() + ":" + datastoreInfo.getPortNum() + "/?useInformationSchema=true&useUnicode=true&characterEncoding=utf8";
      
    } else if (CommCode.DataStoreSubType.ORACLE == datastoreInfo.getSubType()) {
      DRIVER = "oracle.jdbc.driver.OracleDriver";
      URL = "jdbc:oracle:thin:@" + datastoreInfo.getHostName() + ":" + datastoreInfo.getPortNum();
      
    } else if (CommCode.DataStoreSubType.MSSQL == datastoreInfo.getSubType()) {
      DRIVER = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
      URL = "jdbc:microsoft:sqlserver:" + datastoreInfo.getHostName() + ":" + datastoreInfo.getPortNum();
      
    } else if (CommCode.DataStoreSubType.GENERIC == datastoreInfo.getSubType()) {
      return schemas;
    }
    
    Connection connection = null;
    try {
      Class.forName(DRIVER);
      connection = DriverManager.getConnection(URL, datastoreInfo.getUsername(), datastoreInfo.getPassword());
      
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
    } catch (Exception e) {
      e.printStackTrace();
      new BizException(e.getMessage());
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return schemas;
  }

  @Override
  public List<DatastoreVo> getDatastoreAllList(DatastoreVo dataStore) {
    //return datastoreDao.getList(dataStore);
    return null;
  }
  
}
