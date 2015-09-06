/* #﻿root 권한으로 생성 - 데이타베이스 초기화 */
drop database cep;
use mysql;
delete from user where User = 'cep';  
commit;

/* #﻿root 권한으로 생성 */
CREATE DATABASE `cep` CHARACTER SET utf8 COLLATE utf8_general_ci; /*기본문자셋을 지정한 데이타베이스 생성*/
GRANT ALL privileges ON cep.* TO `cep`@'%' IDENTIFIED BY '!Cep@2015'; /*유저생성과 권한부여*/
GRANT ALL privileges ON cep.* TO `cep`@'locahost' IDENTIFIED BY '!Cep@2015';
FLUSH PRIVILEGES; /*위 작업들 적용완료*/


DROP TABLE IF EXISTS `cep`.`user`;
CREATE TABLE `cep`.`user` (
  `id` varchar(45) NOT NULL COMMENT '아이디',
  `name` varchar(45) DEFAULT NULL COMMENT '이름',
  `passwd` varchar(500) DEFAULT NULL COMMENT '비밀번호',
  `email` varchar(100) DEFAULT NULL COMMENT '이메일',
  `tel` varchar(14) DEFAULT NULL COMMENT '전화번호',
  `user_grp_cd` varchar(10) DEFAULT NULL COMMENT '사용자그룹코드',
  `create_date` datetime DEFAULT NULL COMMENT '생성일시',
  `update_date` datetime DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자 정보';
/* 초기데이타 세팅 */
INSERT INTO `cep`.`user` (`id`,`name`,`passwd`,`email`,`tel`,`user_grp_cd`,`create_date`,`update_date`) VALUES ('admin','관리자',sha1('cepadmin'),NULL,NULL,'1',NULL,NULL);

DROP TABLE IF EXISTS `cep`.`user_access_log`;
CREATE TABLE `cep`.`user_access_log` (
  `login_date` datetime NOT NULL COMMENT '로그인일시',
  `id` varchar(45) NOT NULL COMMENT '아이디',
  `name` varchar(45) DEFAULT NULL COMMENT '이름',
  `email` varchar(100) DEFAULT NULL COMMENT '이메일',
  `tel` varchar(14) DEFAULT NULL COMMENT '전화번호',
  `user_grp_cd` varchar(10) DEFAULT NULL COMMENT '사용자그룹코드',
  PRIMARY KEY (`login_date`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='사용자 접속 로그';

DROP TABLE IF EXISTS `cep`.`notebook`;
CREATE TABLE `cep`.`notebook` (
  `note_id` varchar(100) NOT NULL COMMENT '노트아이디',
  `note_name` varchar(300) NOT NULL COMMENT '노트이름',
  `note_content` text COMMENT '노트내용',
  `user_id` varchar(45) NOT NULL COMMENT '사용자아이디',
  `create_date` datetime DEFAULT NULL COMMENT '생성일시',
  `update_date` datetime DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`note_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='노트북';

DROP TABLE IF EXISTS `cep`.`workspace`;
CREATE TABLE `cep`.`workspace` (
  `workspace_id` varchar(36) NOT NULL COMMENT '작업공간아이디:(UUID)',
  `name` varchar(100) NOT NULL COMMENT '이름',
  `type` varchar(20) NOT NULL COMMENT '타입',
  `p_id` varchar(36) NOT NULL COMMENT '상위아이디',
  `user_id` varchar(45) DEFAULT NULL COMMENT '사용자아이디',
  `create_date` datetime DEFAULT NULL COMMENT '생성일시',
  `update_date` datetime DEFAULT NULL COMMENT '수정일시',
  PRIMARY KEY (`workspace_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='작업공간';
INSERT INTO `cep`.`workspace` (`workspace_id`,`name`,`type`,`p_id`) VALUES ('15634a90-0e8f-44e4-98e1-550be090a210','Personal','P','ROOT');
INSERT INTO `cep`.`workspace` (`workspace_id`,`name`,`type`,`p_id`) VALUES ('f5697b8a-6fe0-4d0e-be26-3472b3c8e2eb','Shared','S','ROOT');
INSERT INTO `cep`.`workspace` (`workspace_id`,`name`,`type`,`p_id`) VALUES ('79efbe48-8205-4a1c-91e3-051bb246b468','Global','G','ROOT');

DROP TABLE IF EXISTS `cep`.`notebook_workspace`;
CREATE TABLE `cep`.`notebook_workspace` (
  `note_id` varchar(100) NOT NULL COMMENT '노트아이디',
  `workspace_id` varchar(36) NOT NULL COMMENT '작업공간아이디:(UUID)',
  PRIMARY KEY (`note_id`, `workspace_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='노트북_작업공간';
