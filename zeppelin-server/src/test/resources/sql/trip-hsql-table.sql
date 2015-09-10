/* Drop Tables */

DROP TABLE IF EXISTS datasource;
DROP TABLE IF EXISTS datastore_props;
DROP TABLE IF EXISTS datastore;
DROP TABLE IF EXISTS notebook;
DROP TABLE IF EXISTS role_privs;
DROP TABLE IF EXISTS privilege;
DROP TABLE IF EXISTS role_grantee;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS workspace_share;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS user_access_log;
DROP TABLE IF EXISTS workspace_assign;
DROP TABLE IF EXISTS workspace;
DROP TABLE IF EXISTS workspace_object;

/* Create Tables */

CREATE TABLE datasource
(
  datasource_id  VARCHAR(36)  NOT NULL,
  datstore_id    VARCHAR(36)  NOT NULL,
  datsrc_name    VARCHAR(100) NOT NULL,
  container_name VARCHAR(200),
  src_obj_name   VARCHAR(100),
  description    VARCHAR(1000),
  update_date    DATETIME     NOT NULL,
  update_user_id VARCHAR(45)  NOT NULL,
  PRIMARY KEY (datasource_id),
  UNIQUE (datsrc_name)
);

CREATE TABLE datastore
(
  datstore_id      VARCHAR(36)  NOT NULL,
  datstore_name    VARCHAR(100) NOT NULL,
  datstore_type    VARCHAR(10),
  datstore_subtype VARCHAR(10),
  host_name        VARCHAR(50),
  port_num         INT,
  cred_user_info   VARCHAR(50),
  cred_pass_info   VARCHAR(50),
  description      VARCHAR(1000),
  update_date      DATETIME     NOT NULL,
  update_user_id   VARCHAR(45)  NOT NULL,
  PRIMARY KEY (datstore_id),
  UNIQUE (datstore_name)
);


CREATE TABLE datastore_props
(
  datstore_id VARCHAR(36)  NOT NULL,
  prop_name   VARCHAR(30)  NOT NULL,
  prop_value  VARCHAR(100) NOT NULL,
  PRIMARY KEY (datstore_id, prop_name)
);


CREATE TABLE notebook
(
  note_id        VARCHAR(36) NOT NULL,
  note_name      VARCHAR(300),
  note_content   CLOB,
  update_date    DATETIME,
  update_user_id VARCHAR(45),
  PRIMARY KEY (note_id)
);


CREATE TABLE privilege
(
  priv_name VARCHAR(30) NOT NULL,
  PRIMARY KEY (priv_name)
);


CREATE TABLE role
(
  role_id   VARCHAR(36)  NOT NULL,
  role_name VARCHAR(100) NOT NULL,
  role_cd   VARCHAR(2)   NOT NULL,
  PRIMARY KEY (role_id)
);


CREATE TABLE role_grantee
(
  user_id        VARCHAR(45) NOT NULL,
  role_id        VARCHAR(36) NOT NULL,
  update_user_id VARCHAR(45) NOT NULL,
  update_date    DATETIME    NOT NULL,
  PRIMARY KEY (user_id, role_id)
);


CREATE TABLE role_privs
(
  priv_name VARCHAR(30) NOT NULL,
  role_id   VARCHAR(36) NOT NULL,
  PRIMARY KEY (priv_name, role_id)
);


CREATE TABLE user
(
  id             VARCHAR(45) NOT NULL,
  name           VARCHAR(100),
  passwd         VARCHAR(500),
  wrkspc_id      VARCHAR(36) NOT NULL,
  email          VARCHAR(50),
  tel            VARCHAR(14),
  user_grp_cd    VARCHAR(2),
  update_user_id VARCHAR(45) NOT NULL,
  update_date    DATETIME    NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE user_access_log (
  login_date  DATETIME    NOT NULL,
  id          VARCHAR(45) NOT NULL,
  name        VARCHAR(45)  DEFAULT NULL,
  email       VARCHAR(100) DEFAULT NULL,
  tel         VARCHAR(14)  DEFAULT NULL,
  user_grp_cd VARCHAR(10)  DEFAULT NULL,
  PRIMARY KEY (login_date, id)
);


CREATE TABLE workspace
(
  wrkspc_id      VARCHAR(36)  NOT NULL,
  wrkspc_name    VARCHAR(100) NOT NULL,
  description    VARCHAR(1000),
  wrkspc_type    VARCHAR(10)  NOT NULL,
  admin_user_id  VARCHAR(36),
  update_date    DATETIME     NOT NULL,
  update_user_id VARCHAR(45)  NOT NULL,
  PRIMARY KEY (wrkspc_id),
  UNIQUE (wrkspc_name)
);


CREATE TABLE workspace_assign
(
  wrkspc_id      VARCHAR(36) NOT NULL,
  wrkspc_obj_id  VARCHAR(36) NOT NULL,
  update_date    DATETIME    NOT NULL,
  update_user_id VARCHAR(45) NOT NULL,
  PRIMARY KEY (wrkspc_id, wrkspc_obj_id)
);


CREATE TABLE workspace_object
(
  wrkspc_obj_id   VARCHAR(36) NOT NULL,
  wrkspc_obj_type VARCHAR(10) NOT NULL,
  share_type      VARCHAR(10) NOT NULL,
  obj_status      VARCHAR(10) NOT NULL,
  create_user_id  VARCHAR(45),
  own_user_id     VARCHAR(45),
  PRIMARY KEY (wrkspc_obj_id)
);


CREATE TABLE workspace_share
(
  wrkspc_id      VARCHAR(36) NOT NULL,
  user_id        VARCHAR(45) NOT NULL,
  update_date    DATETIME    NOT NULL,
  update_user_id VARCHAR(45) NOT NULL,
  PRIMARY KEY (wrkspc_id, user_id)
);

/* 초기데이타 세팅 */
INSERT INTO role (role_id, role_name, role_cd) VALUES ('5c9439ee-ca70-4878-9e38-0ca6d3bd6eee', 'System Admin', '1');
INSERT INTO role (role_id, role_name, role_cd) VALUES ('1eabc394-c29c-4a97-823c-770605d7aeaa', 'Workspace Admin', '2');
INSERT INTO role (role_id, role_name, role_cd) VALUES ('d12b0fa9-3fad-4475-bac4-b3dcdfa623e6', 'User', '3');
INSERT INTO workspace (wrkspc_id, wrkspc_name, description, wrkspc_type, admin_user_id, update_date, update_user_id)
VALUES ('53af58da-d182-424f-bd3a-6c1cfb594535', 'admin', 'Admin Workspace', 'PERSONAL', 'admin', NOW(), 'admin');
INSERT INTO user (id, name, passwd, wrkspc_id, email, tel, user_grp_cd, update_user_id, update_date)
VALUES ('admin', '관리자', 'dmin', '53af58da-d182-424f-bd3a-6c1cfb594535', NULL, NULL, '1', 'admin', NOW());
INSERT INTO datastore(datstore_id, datstore_name, datstore_type, datstore_subtype, host_name, port_num, cred_user_info, cred_pass_info, description, update_date, update_user_id)
VALUES ('5c9439ee-ca70-4878-9e38-0ca6d3bd6eea', 'mymeta', 'DATABASE', 'MYSQL', 'cep1', 3306, 'trip', '!Trip@2015', 'metadata store', NOW(), 'admin');