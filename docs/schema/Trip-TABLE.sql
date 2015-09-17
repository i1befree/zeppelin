/* #﻿root 권한으로 생성 */
CREATE DATABASE trip CHARACTER SET utf8 COLLATE utf8_general_ci; /*기본문자셋을 지정한 데이타베이스 생성*/
GRANT ALL privileges ON trip.* TO trip@'%' IDENTIFIED BY '!Trip@2015'; /*유저생성과 권한부여*/
GRANT ALL privileges ON trip.* TO trip@'locahost' IDENTIFIED BY '!Trip@2015';
FLUSH PRIVILEGES; /*위 작업들 적용완료*/



SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS trip.datasource;
DROP TABLE IF EXISTS trip.datastore_props;
DROP TABLE IF EXISTS trip.datastore;
DROP TABLE IF EXISTS trip.notebook;
DROP TABLE IF EXISTS trip.role_privs;
DROP TABLE IF EXISTS trip.privilege;
DROP TABLE IF EXISTS trip.role_grantee;
DROP TABLE IF EXISTS trip.role;
DROP TABLE IF EXISTS trip.workspace_share;
DROP TABLE IF EXISTS trip.user;
DROP TABLE IF EXISTS trip.user_access_log;
DROP TABLE IF EXISTS trip.workspace_assign;
DROP TABLE IF EXISTS trip.workspace;
DROP TABLE IF EXISTS trip.workspace_object;




/* Create Tables */

CREATE TABLE trip.datasource
(
	datasource_id varchar(36) NOT NULL COMMENT 'datasource_id',
	datstore_id varchar(36) NOT NULL COMMENT 'Datastore ID',
	datsrc_name varchar(100) NOT NULL COMMENT '데이터소스명',
	container_name varchar(200) COMMENT '컨테이너명',
	src_obj_name varchar(100) COMMENT '소스오브젝트명',
	description varchar(1000) COMMENT '설명',
	update_date datetime NOT NULL COMMENT '변경시각',
	update_user_id varchar(45) NOT NULL COMMENT '변경자ID',
	PRIMARY KEY (datasource_id),
	UNIQUE (datsrc_name)
) ENGINE = InnoDB COMMENT = '데이터소스' DEFAULT CHARACTER SET utf8;


CREATE TABLE trip.datastore
(
	datstore_id varchar(36) NOT NULL COMMENT 'Datastore ID',
	datstore_name varchar(100) NOT NULL COMMENT 'Datastore 이름',
	datstore_type varchar(10) COMMENT 'Datastore 유형',
	datstore_subtype varchar(10) COMMENT 'Datastore 서브유형',
	host_name varchar(50) COMMENT '호스트명',
	port_num int COMMENT '포트번호',
	cred_user_info varchar(50) COMMENT '접속 유저 정보',
	cred_pass_info varchar(50) COMMENT '접속 암호 정보',
	description varchar(1000) COMMENT '설명',
	update_date datetime NOT NULL COMMENT '변경시각',
	update_user_id varchar(45) NOT NULL COMMENT '변경자ID',
	PRIMARY KEY (datstore_id),
	UNIQUE (datstore_name)
) ENGINE = InnoDB COMMENT = 'Datastore' DEFAULT CHARACTER SET utf8;


CREATE TABLE trip.datastore_props
(
	datstore_id varchar(36) NOT NULL COMMENT 'Datastore ID',
	prop_name varchar(30) NOT NULL COMMENT '속성명',
	prop_value varchar(100) NOT NULL COMMENT '속성값',
	PRIMARY KEY (datstore_id, prop_name)
) ENGINE = InnoDB COMMENT = 'Datastore Properties' DEFAULT CHARACTER SET utf8;


CREATE TABLE trip.notebook
(
	wrkspc_obj_id varchar(36) NOT NULL COMMENT 'wrkspc_obj_id',
	note_id varchar(36) NOT NULL COMMENT '노트 아이디',
	note_name varchar(300) COMMENT '노드 이름',
	note_content longtext COMMENT '노트 컨텐츠',
	update_date datetime COMMENT '변경시각',
	update_user_id varchar(45) COMMENT '변경자ID',
	PRIMARY KEY (wrkspc_obj_id)
) ENGINE = InnoDB COMMENT = '노트북' DEFAULT CHARACTER SET utf8;


CREATE TABLE trip.privilege
(
	priv_name varchar(30) NOT NULL COMMENT '권한명',
	PRIMARY KEY (priv_name)
) ENGINE = InnoDB COMMENT = '권한' DEFAULT CHARACTER SET utf8;


CREATE TABLE trip.role
(
	role_id varchar(36) NOT NULL COMMENT 'Role ID',
	role_name varchar(100) NOT NULL COMMENT 'Role 이름',
	role_cd varchar(2) NOT NULL COMMENT 'Role 코드',
	PRIMARY KEY (role_id)
) ENGINE = InnoDB COMMENT = 'Role' DEFAULT CHARACTER SET utf8;


CREATE TABLE trip.role_grantee
(
	user_id varchar(45) NOT NULL COMMENT '사용ID',
	role_id varchar(36) NOT NULL COMMENT 'Role ID',
	update_user_id varchar(45) NOT NULL COMMENT '변경자ID',
	update_date datetime NOT NULL COMMENT '변경시각',
	PRIMARY KEY (user_id, role_id)
) ENGINE = InnoDB COMMENT = '새 테이블' DEFAULT CHARACTER SET utf8;


CREATE TABLE trip.role_privs
(
	priv_name varchar(30) NOT NULL COMMENT '권한명',
	role_id varchar(36) NOT NULL COMMENT 'Role ID',
	PRIMARY KEY (priv_name, role_id)
) ENGINE = InnoDB COMMENT = 'Role 권한' DEFAULT CHARACTER SET utf8;


CREATE TABLE trip.user
(
	id varchar(45) NOT NULL COMMENT '사용ID',
	name varchar(100) COMMENT '사용자명',
	passwd varchar(500) COMMENT '사용자 암호',
	wrkspc_id varchar(36) NOT NULL COMMENT '워크스페이스 ID',
	email varchar(50) COMMENT '이메일',
	tel varchar(14) COMMENT '전화번호',
	user_grp_cd varchar(2) COMMENT '사용자 Role code',
	update_user_id varchar(45) NOT NULL COMMENT '변경자ID',
	update_date datetime NOT NULL COMMENT '변경시각',
	PRIMARY KEY (id)
) ENGINE = InnoDB COMMENT = '사용자' DEFAULT CHARACTER SET utf8;


CREATE TABLE trip.user_access_log (
  	id varchar(36) NOT NULL COMMENT 'log ID',
	  login_date datetime NOT NULL COMMENT '로그인일시',
  	user_id varchar(45) NOT NULL COMMENT '아이디',
  	name varchar(45) DEFAULT NULL COMMENT '이름',
  	email varchar(100) DEFAULT NULL COMMENT '이메일',
  	tel varchar(14) DEFAULT NULL COMMENT '전화번호',
  	user_grp_cd varchar(10) DEFAULT NULL COMMENT '사용자그룹코드',
  	PRIMARY KEY (id)
) ENGINE=InnoDB COMMENT = '사용자 접속 로그' DEFAULT CHARACTER SET utf8;


CREATE TABLE trip.workspace
(
	wrkspc_id varchar(36) NOT NULL COMMENT '워크스페이스 ID',
	wrkspc_name varchar(100) NOT NULL COMMENT '워크스페이스',
	description varchar(1000) COMMENT '설명',
	wrkspc_type varchar(10) NOT NULL COMMENT '워크스페이스 유형',
	admin_user_id varchar(36) COMMENT '관리자ID',
	update_date datetime NOT NULL COMMENT '변경시각',
	update_user_id varchar(45) NOT NULL COMMENT '변경자ID',
	PRIMARY KEY (wrkspc_id),
	UNIQUE (wrkspc_name)
) ENGINE = InnoDB COMMENT = '워크스페이스' DEFAULT CHARACTER SET utf8;


CREATE TABLE trip.workspace_assign
(
	assign_id varchar(36) NOT NULL COMMENT '할당 ID',
	wrkspc_id varchar(36) NOT NULL COMMENT '워크스페이스 ID',
	wrkspc_obj_id varchar(36) NOT NULL COMMENT 'wrkspc_obj_id',
	update_date datetime NOT NULL COMMENT '변경시각',
	update_user_id varchar(45) NOT NULL COMMENT '변경자ID',
	PRIMARY KEY (assign_id)
) ENGINE = InnoDB COMMENT = '워크스페이스 할당' DEFAULT CHARACTER SET utf8;


CREATE TABLE trip.workspace_object
(
	wrkspc_obj_id varchar(36) NOT NULL COMMENT 'wrkspc_obj_id',
	wrkspc_obj_type varchar(10) NOT NULL COMMENT '워크스페이스 객체 유형',
	share_type varchar(10) NOT NULL COMMENT '공유유형',
	obj_status varchar(10) NOT NULL COMMENT '객체상태',
	create_user_id varchar(45) COMMENT '생성한 사용자ID',
	own_user_id varchar(45) COMMENT '소유한 사용자ID',
	PRIMARY KEY (wrkspc_obj_id)
) ENGINE = InnoDB COMMENT = '워크스페이스 관리객체' DEFAULT CHARACTER SET utf8;


CREATE TABLE trip.workspace_share
(
	share_id varchar(36) NOT NULL COMMENT '공유 ID',
	wrkspc_id varchar(36) NOT NULL COMMENT '워크스페이스 ID',
	user_id varchar(45) NOT NULL COMMENT '사용ID',
	update_date datetime NOT NULL COMMENT '변경시각',
	update_user_id varchar(45) NOT NULL COMMENT '변경자ID',
	PRIMARY KEY (share_id)
) ENGINE = InnoDB COMMENT = '새 테이블' DEFAULT CHARACTER SET utf8;




/* 초기데이타 세팅 */
INSERT INTO trip.role (role_id, role_name, role_cd) VALUES ('5c9439ee-ca70-4878-9e38-0ca6d3bd6eee','System Admin','1');
INSERT INTO trip.role (role_id, role_name, role_cd) VALUES ('1eabc394-c29c-4a97-823c-770605d7aeaa','Workspace Admin','2');
INSERT INTO trip.role (role_id, role_name, role_cd) VALUES ('d12b0fa9-3fad-4475-bac4-b3dcdfa623e6','User','3');
INSERT INTO trip.workspace (wrkspc_id, wrkspc_name, description, wrkspc_type, admin_user_id, update_date, update_user_id) VALUES ('53af58da-d182-424f-bd3a-6c1cfb594535', 'admin', 'Admin Workspace', 'PERSONAL', 'admin', NOW(), 'admin');
/* id: admin, pw: tripadmin */
INSERT INTO trip.user (id,name,passwd,wrkspc_id,email,tel,user_grp_cd,update_user_id,update_date) VALUES ('admin','관리자','0c8c019a9ee2759170e8814b7342ec0e2a5639bc82c347bb281db3d1383beb4d','53af58da-d182-424f-bd3a-6c1cfb594535',NULL,NULL,'1','admin',NOW());
INSERT INTO trip.workspace_share (share_id,wrkspc_id,user_id,update_date,update_user_id) VALUES ('c2491637-59d3-11e5-bb39-063b17d52e29', '53af58da-d182-424f-bd3a-6c1cfb594535', 'admin', NOW(), 'admin');
