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

-- workspace
INSERT INTO workspace (wrkspc_id, wrkspc_name, description, wrkspc_type, admin_user_id, update_date, update_user_id)
VALUES ('53af58da-d182-424f-bd3a-6c1cfb594535', 'admin', 'Admin Workspace', 'PERSONAL', 'admin', NOW(), 'admin');

-- user
INSERT INTO user (id, name, passwd, wrkspc_id, email, tel, user_grp_cd, update_user_id, update_date)
VALUES ('admin', '관리자', 'dmin', '53af58da-d182-424f-bd3a-6c1cfb594535', NULL, NULL, '1', 'admin', NOW());
INSERT INTO workspace_share (wrkspc_id, user_id, update_date, update_user_id)
VALUES ('53af58da-d182-424f-bd3a-6c1cfb594535', 'admin', NOW(), 'admin');

-- datastore
INSERT INTO datastore (datstore_id, datstore_name, datstore_type, datstore_subtype, host_name, port_num, cred_user_info, cred_pass_info, description, update_date, update_user_id)
VALUES ('5c9439ee-ca70-4878-9e38-0ca6d3bd6eea', 'mymeta', 'DATABASE', 'MYSQL', 'cep1', 3306, 'trip', '!Trip@2015', 'metadata store',
        NOW(), 'admin');
INSERT INTO datastore (datstore_id, datstore_name, datstore_type, datstore_subtype, host_name, port_num, cred_user_info, cred_pass_info, description, update_date, update_user_id)
VALUES ('23caae00-506b-11e5-bb39-063b17d52e29', 'trip 테이블', 'DATABASE', 'MYSQL', '52.68.186.228', 3306,
        'trip', '!Trip@2015', 'RDB 테스트', NOW(), 'admin');
INSERT INTO datastore (datstore_id, datstore_name, datstore_type, datstore_subtype, host_name, port_num, cred_user_info, cred_pass_info, description, update_date, update_user_id)
VALUES ('86f3cf18-4fab-11e5-bb39-063b17d52e29', 'elasticsearch', 'INTERNAL', NULL, '52.68.186.228', 9300,
        '', '', '인터널 기본설정 스토어', NOW(), 'admin');

-- notebook
INSERT INTO workspace_object (create_user_id, obj_status, own_user_id, share_type, wrkspc_obj_type, wrkspc_obj_id)
VALUES ('admin', 'CREATED', 'admin', 'NONE', 'NOTEBOOK', '5c9439ee-ca70-4878-9e38-0ca6d3bd6eez');
INSERT INTO Notebook (note_name, update_date, note_id) VALUES ('note test', NOW(), '5c9439ee-ca70-4878-9e38-0ca6d3bd6eez');

-- datasource
INSERT INTO workspace_object (create_user_id, obj_status, own_user_id, share_type, wrkspc_obj_type, wrkspc_obj_id)
VALUES ('admin', 'CREATED', 'admin', 'NONE', 'DATSRC', '2efb0571-7f1c-4f30-bc3c-71355367b635');
INSERT INTO workspace_object (create_user_id, obj_status, own_user_id, share_type, wrkspc_obj_type, wrkspc_obj_id)
VALUES ('admin', 'CREATED', 'admin', 'ALL', 'DATSRC', '47e51d22-907b-4999-b714-2e211ec4b8b8');
INSERT INTO datasource (datasource_id, datstore_id, datsrc_name, container_name, src_obj_name, description, update_date, update_user_id)
VALUES ('2efb0571-7f1c-4f30-bc3c-71355367b635', '23caae00-506b-11e5-bb39-063b17d52e29',
        '노트북', 'trip', 'notebook', '노트북 설명', NOW(), 'admin');
INSERT INTO datasource (datasource_id, datstore_id, datsrc_name, container_name, src_obj_name, description, update_date, update_user_id)
VALUES ('47e51d22-907b-4999-b714-2e211ec4b8b8', '86f3cf18-4fab-11e5-bb39-063b17d52e29',
        'es 테스트', 'nmc', 'sgsn_transition_1d', NULL, NOW(), 'admin');

--workspace assign
INSERT INTO workspace_assign (wrkspc_id, wrkspc_obj_id, update_date, update_user_id)
VALUES ('53af58da-d182-424f-bd3a-6c1cfb594535', '2efb0571-7f1c-4f30-bc3c-71355367b635', NOW(), 'admin');
