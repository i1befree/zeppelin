package com.sktelecom.cep.common;

/**
 * enum type 정의.
 * @author Administrator
 *
 */
public class CommCode {
 
  /**
   * Type of workspace
   */
  public enum WorkspaceType {
    PERSONAL,
    SHARE
  }

  /**
   * Status of workspace's object
   */
  public enum WorkspaceObjectStatus {
    CREATED,
    DROPPED
  }

  /**
   * Type of workspace's share
   */
  public enum WorkspaceObjectShareType {
    NONE,
    ALL
  }

  /**
   * Type of workspace's object
   */
  public enum WorkspaceObjectType {
    DATSRC,
    NOTEBOOK
  }

  /**
   * Type of DataStore.
   */
  public enum DataStoreType {
    INTERNAL,
    DATABASE,
    HDFS
  }

  /**
   * if type is DATABASE, choose this one.
   */
  public enum DataStoreSubType {
    MYSQL,
    MSSQL,
    ORACLE,
    GENERIC
  }
}
