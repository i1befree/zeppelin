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
  public enum Status {
    CREATED,
    DROPPED
  }

  /**
   * Type of workspace's share
   */
  public enum ShareType {
    NONE,
    ALL
  }

  /**
   * Type of workspace's object
   */
  public enum ObjectType {
    DATSRC,
    NOTEBOOK
  }

  /**
   * Type of DataStore.
   */
  public static enum DataStoreType {
    INTERNAL,
    DATABASE,
    HDFS
  }

  /**
   * if type is DATABASE, choose this one.
   */
  public static enum SubType {
    MYSQL,
    MSSQL,
    ORACLE,
    GENERIC
  }
}
