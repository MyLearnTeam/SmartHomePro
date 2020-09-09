/*
MySQL Data Transfer
Source Host: localhost
Source Database: yhwl_project
Target Host: localhost
Target Database: yhwl_project
Date: 2020-02-28 22:17:29
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for sys_basedata
-- ----------------------------
DROP TABLE IF EXISTS `sys_basedata`;
CREATE TABLE `sys_basedata` (
  `param_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'key',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级',
  `param_token` varchar(30) DEFAULT '' COMMENT '数据类型标识',
  `pvalue` int(11) DEFAULT NULL COMMENT '数据值',
  `ptext` varchar(150) DEFAULT '' COMMENT '数据中文',
  `remark` varchar(200) DEFAULT '' COMMENT '说明',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_user_id` int(11) DEFAULT '0' COMMENT '创建人',
  `update_user_id` int(11) DEFAULT '0' COMMENT '更新人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更改时间',
  PRIMARY KEY (`param_id`),
  UNIQUE KEY `param_token` (`param_token`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_catalog
-- ----------------------------
DROP TABLE IF EXISTS `sys_catalog`;
CREATE TABLE `sys_catalog` (
  `catalog_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `catalogcode` varchar(50) DEFAULT '' COMMENT '唯一标识',
  `catalog` varchar(50) DEFAULT NULL COMMENT '名称',
  `parent_id` int(11) unsigned DEFAULT '0' COMMENT '上级ID',
  `img` varchar(150) DEFAULT '' COMMENT '图标',
  `url` varchar(250) DEFAULT '' COMMENT '访问地址',
  `is_menu` int(11) DEFAULT '1' COMMENT '是否菜单(1-菜单,2-权限)',
  `winWidth` int(11) DEFAULT '650' COMMENT '宽度',
  `winHeight` int(11) DEFAULT '550' COMMENT '高度',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_user_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`catalog_id`) USING BTREE,
  UNIQUE KEY `catalogcode` (`catalogcode`),
  KEY `parent_id` (`parent_id`),
  KEY `url` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=1290 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='权限模块表';

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `depart_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '机构ID',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父级ID(0为根层级)',
  `depart_code` varchar(30) DEFAULT NULL COMMENT '部门编号',
  `depart_name` varchar(150) NOT NULL COMMENT '部门名称',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `sort` int(11) DEFAULT '0' COMMENT '排序号',
  `author_id` int(11) DEFAULT '0' COMMENT '创建人ID',
  `thetime` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_user_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`depart_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7712 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='部门表';

-- ----------------------------
-- Table structure for sys_errorlogs
-- ----------------------------
DROP TABLE IF EXISTS `sys_errorlogs`;
CREATE TABLE `sys_errorlogs` (
  `error_id` int(11) NOT NULL AUTO_INCREMENT,
  `errMsg` varchar(1000) DEFAULT '',
  `errSql` varchar(500) DEFAULT '',
  `thetime` datetime DEFAULT NULL,
  PRIMARY KEY (`error_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_files
-- ----------------------------
DROP TABLE IF EXISTS `sys_files`;
CREATE TABLE `sys_files` (
  `file_id` int(10) NOT NULL AUTO_INCREMENT,
  `filename` varchar(256) NOT NULL COMMENT '文件名(用于显示，一般为上传时文件使用的名字)',
  `savename` varchar(256) NOT NULL DEFAULT '' COMMENT '文件物理文件名',
  `extname` varchar(100) NOT NULL COMMENT '后序',
  `file_type_id` int(11) NOT NULL DEFAULT '0' COMMENT '文件类型ID：可扩展作为指定类型文件的区分字段',
  `file_sort` varchar(30) DEFAULT '' COMMENT '文件类型：可做扩展，如 营业执照 等',
  `filePath` varchar(500) DEFAULT NULL COMMENT '保存路径(相对路径)',
  `fileSize` varchar(20) DEFAULT '0' COMMENT '文件大小(以Bit为单位)',
  `author_id` int(11) DEFAULT '0' COMMENT '上传人ID',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态(0：禁用；1：正常；3：删除)',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  `memo` varchar(2000) DEFAULT NULL COMMENT '备用字段',
  `createtime` datetime DEFAULT NULL COMMENT '首次上传时间',
  `updatetime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_user_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`file_id`) USING BTREE,
  KEY `file_type_id` (`file_type_id`),
  KEY `file_sort` (`file_sort`)
) ENGINE=InnoDB AUTO_INCREMENT=500 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='文件列表';

-- ----------------------------
-- Table structure for sys_logs
-- ----------------------------
DROP TABLE IF EXISTS `sys_logs`;
CREATE TABLE `sys_logs` (
  `log_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `log_type_id` int(3) NOT NULL COMMENT '日志类型',
  `title` varchar(300) DEFAULT '' COMMENT '标题',
  `log_info` varchar(2000) DEFAULT NULL COMMENT '日志信息',
  `user_id` int(10) DEFAULT NULL COMMENT '操作用户(0-代表系统)',
  `user_name` varchar(255) DEFAULT '' COMMENT '操作人',
  `ip_addr` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime NOT NULL COMMENT '发生时间',
  `channel` varchar(255) DEFAULT NULL COMMENT '登陆方式',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3483 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统日志表';

-- ----------------------------
-- Table structure for sys_logs_history
-- ----------------------------
DROP TABLE IF EXISTS `sys_logs_history`;
CREATE TABLE `sys_logs_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `log_id` bigint(20) unsigned NOT NULL COMMENT '日志ID',
  `log_type_id` int(3) NOT NULL COMMENT '日志类型',
  `title` varchar(300) DEFAULT '' COMMENT '标题',
  `log_info` varchar(2000) DEFAULT NULL COMMENT '日志信息',
  `user_id` int(10) DEFAULT NULL COMMENT '操作用户(0-代表系统)',
  `user_name` varchar(255) DEFAULT '' COMMENT '操作人',
  `ip_addr` varchar(20) DEFAULT NULL COMMENT 'IP地址',
  `create_time` datetime NOT NULL COMMENT '发生时间',
  `channel` varchar(255) DEFAULT NULL COMMENT '登陆方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `role_code` varchar(30) DEFAULT NULL COMMENT '角色编码',
  `top_depart_id` int(11) DEFAULT NULL COMMENT '角色所属的政府/企业/机构对应的depart_id(0属于平台角色)',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_user_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE KEY `role_code` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色表';

-- ----------------------------
-- Table structure for sys_role_popedom
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_popedom`;
CREATE TABLE `sys_role_popedom` (
  `rpid` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `role_id` smallint(5) unsigned NOT NULL COMMENT '角色ID',
  `catalog_id` int(11) unsigned NOT NULL COMMENT '栏目ID',
  PRIMARY KEY (`rpid`) USING BTREE,
  KEY `fk_roledp_catalogid_idx` (`catalog_id`) USING BTREE,
  KEY `fk_rolepd_roleid_idx` (`role_id`) USING BTREE,
  CONSTRAINT `fk_roledp_catalogid` FOREIGN KEY (`catalog_id`) REFERENCES `sys_catalog` (`catalog_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_rolepd_roleid` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21753 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='角色权限表';

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `ruid` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户角色ID',
  `role_id` smallint(5) unsigned DEFAULT NULL COMMENT '角色ID',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`ruid`) USING BTREE,
  KEY `fk_roleuser_roleid_idx` (`role_id`) USING BTREE,
  KEY `fk_roleuser_userid_idx` (`user_id`) USING BTREE,
  CONSTRAINT `fk_roleuser_roleid` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9838 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户角色表';

-- ----------------------------
-- Table structure for sys_userinfo
-- ----------------------------
DROP TABLE IF EXISTS `sys_userinfo`;
CREATE TABLE `sys_userinfo` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `login_name` varchar(50) NOT NULL COMMENT '登录名(与企业号账号对应，不支持中文)',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `truename` varchar(50) NOT NULL COMMENT '真实姓名',
  `usercode` varchar(20) DEFAULT NULL COMMENT '用户代码',
  `status` tinyint(4) DEFAULT '0' COMMENT '账号状态（-1:锁定,0无效，1正常）',
  `usertype` tinyint(3) unsigned DEFAULT '0' COMMENT '用户类型(0-访客,1-员工,2-供应商)',
  `depart_id` smallint(5) unsigned DEFAULT '0' COMMENT ' 所属部门',
  `photo` varchar(255) DEFAULT '' COMMENT '头像地址',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机',
  `phone` varchar(50) DEFAULT NULL COMMENT '座机固话',
  `job_title` varchar(45) DEFAULT NULL COMMENT '职位（后期取枚举字典）',
  `degree` varchar(45) DEFAULT NULL COMMENT '职称（后期取枚举字典）',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_user_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_user_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `Idx_sysuser_loginname` (`login_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统用户表';

-- ----------------------------
-- Table structure for sys_version
-- ----------------------------
DROP TABLE IF EXISTS `sys_version`;
CREATE TABLE `sys_version` (
  `version_id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(30) DEFAULT '' COMMENT '版本编号',
  `remark` varchar(255) DEFAULT '' COMMENT '更新说明',
  `thetime` datetime DEFAULT NULL COMMENT '更新时间',
  `author_id` int(11) DEFAULT '0' COMMENT '更新人',
  `author` varchar(255) DEFAULT '' COMMENT '更新人',
  `updatetime` date DEFAULT NULL COMMENT '新更时间',
  `sort` int(11) DEFAULT '0' COMMENT '序排',
  `status` int(11) DEFAULT '0' COMMENT '态状',
  PRIMARY KEY (`version_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `sys_basedata` VALUES ('1', '0', 'Sex', '0', '性别', '', '1', '1', '1', '2020-02-28 22:03:57', '2020-02-28 22:03:57');
INSERT INTO `sys_basedata` VALUES ('2', '0', 'XueLi', '0', '学历', '', '2', '1', '1', '2020-02-28 22:03:57', '2020-02-28 22:03:57');
INSERT INTO `sys_basedata` VALUES ('3', '0', 'ZhiCheng', '0', '职称', '', '3', '1', '1', '2020-02-28 22:03:57', '2020-02-28 22:03:57');
INSERT INTO `sys_basedata` VALUES ('4', '1', 'Sex1', '1', '男', '', '3', '1', '1', '2020-02-28 22:03:57', '2020-02-28 22:03:57');
INSERT INTO `sys_basedata` VALUES ('5', '1', 'Sex2', '2', '女', '', '1', '1', '1', '2020-02-28 22:03:57', '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('1', 'System_Manager', '系统管理', '0', 'fa fa-cog', '', '1', '900', '600', '', '1', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('2', 'cataloglist', '模块管理', '1', 'fa fa-file-o', 'listCatalog', '1', '900', '600', '', '2', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('58', 'sysRole_list', '角色管理', '1', '', 'sysRole_list', '1', '900', '600', '', '5', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('59', 'sysrole_new', '添加', '58', 'resources/images/list/6.gif', 'sysrole_new', '2', '900', '600', '', '3', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('60', 'sysrole_edit', '修改', '58', '', 'sysrole_edit', '2', '900', '600', '', '4', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('61', 'sysrole_delete', '批量删除', '58', 'resources/images/list/7.gif', 'deleteSysRole', '2', '900', '600', '', '1', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('68', 'sysUser_list', '用户管理', '1', '', 'sysUser_list', '1', '900', '600', '', '4', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('69', 'sysuser_new', '添加', '68', 'resources/images/list/6.gif', 'sysUser_new', '2', '900', '600', '', '39', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('70', 'sysuser_edit', '修改', '68', '', 'sysUser_edit', '2', '900', '600', '', '40', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('71', 'sysuser_delete', '删除', '68', 'resources/images/list/7.gif', 'deleteSysUser', '2', '900', '600', '', '41', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('153', 'SysDepartment_list', '部门管理', '1', '', 'sysDepartment_list', '1', '900', '600', '', '3', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('154', 'sysdepartment_new', '添加', '153', 'resources/images/list/6.gif', 'sysDepartment_new', '2', '900', '600', '', '119', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('155', 'sysdepartment_edit', '修改', '153', '', 'sysDepartment_edit', '2', '900', '600', '', '120', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('156', 'sysdepartment_delete', '删除', '153', 'resources/images/list/7.gif', 'deleteSysDepartment', '2', '800', '500', '', '121', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('157', 'sysdepartment_sort', '排序', '153', 'resources/images/list/sort.gif', 'sysDepartment_sort', '2', '900', '600', '', '122', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('1173', 'syslogs_list', '系统日志', '1', '', 'listSysLogs', '1', '0', '0', '', '6', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('1174', 'syslogs_delete_batch', '批量删除', '1173', 'resources/images/list/7.gif', 'deleteSysLogs', '2', '800', '500', '', '822', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('1235', 'sysUser_setRole', '设置角色', '68', '', 'sysUser_setRole', '2', '900', '600', '', '876', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('1282', 'sysbasedata_list', '数据字典管理', '1', '', 'listSysBasedata', '1', '900', '600', '', '900', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('1283', 'sysbasedata_new', '新增', '1282', 'resources/images/list/6.gif', 'newSysBasedata', '2', '800', '500', '', '901', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('1284', 'sysbasedata_sort', '排序', '1282', 'resources/images/list/sort.gif', 'sortSysBasedata', '2', '800', '500', '', '902', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('1287', 'sysbasedata_view', '查看', '1282', 'resources/images/list/7.gif', 'viewSysBasedata', '3', '800', '500', '', '905', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('1288', 'sysbasedata_edit', '修改', '1282', '', 'editSysBasedata', '3', '800', '500', '', '906', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_catalog` VALUES ('1289', 'sysbasedata_delete', '删除', '1282', 'resources/images/list/7.gif', 'deleteSysBasedata', '3', '800', '500', '', '907', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_department` VALUES ('1', '0', 'root', '快码猿', '', '0', '0', null, null, '2020-02-28 22:03:57', '1', '2020-02-28 22:03:57');
INSERT INTO `sys_department` VALUES ('5720', '1', 'ywz', '运维组', '', '4', '0', null, null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_department` VALUES ('5721', '1', 'bgs', '办公室', '', '1', '0', null, null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_department` VALUES ('7711', '1', 'T', '技术组', '技术开发、维护', '3', '1', '2020-02-28 22:03:57', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
INSERT INTO `sys_role` VALUES ('1', '0', '系统管理员', 'sysadmin', '0', '系统管理员', '2', null, '2020-02-28 22:03:57', '1', '2020-02-28 22:03:57');
INSERT INTO `sys_role_popedom` VALUES ('21724', '1', '1');
INSERT INTO `sys_role_popedom` VALUES ('21725', '1', '2');
INSERT INTO `sys_role_popedom` VALUES ('21726', '1', '153');
INSERT INTO `sys_role_popedom` VALUES ('21727', '1', '154');
INSERT INTO `sys_role_popedom` VALUES ('21728', '1', '155');
INSERT INTO `sys_role_popedom` VALUES ('21729', '1', '156');
INSERT INTO `sys_role_popedom` VALUES ('21730', '1', '157');
INSERT INTO `sys_role_popedom` VALUES ('21731', '1', '68');
INSERT INTO `sys_role_popedom` VALUES ('21732', '1', '69');
INSERT INTO `sys_role_popedom` VALUES ('21733', '1', '70');
INSERT INTO `sys_role_popedom` VALUES ('21734', '1', '71');
INSERT INTO `sys_role_popedom` VALUES ('21735', '1', '1235');
INSERT INTO `sys_role_popedom` VALUES ('21736', '1', '58');
INSERT INTO `sys_role_popedom` VALUES ('21737', '1', '61');
INSERT INTO `sys_role_popedom` VALUES ('21738', '1', '59');
INSERT INTO `sys_role_popedom` VALUES ('21739', '1', '60');
INSERT INTO `sys_role_popedom` VALUES ('21740', '1', '1173');
INSERT INTO `sys_role_popedom` VALUES ('21741', '1', '1174');
INSERT INTO `sys_role_popedom` VALUES ('21747', '1', '1282');
INSERT INTO `sys_role_popedom` VALUES ('21748', '1', '1283');
INSERT INTO `sys_role_popedom` VALUES ('21749', '1', '1284');
INSERT INTO `sys_role_popedom` VALUES ('21750', '1', '1287');
INSERT INTO `sys_role_popedom` VALUES ('21751', '1', '1288');
INSERT INTO `sys_role_popedom` VALUES ('21752', '1', '1289');
INSERT INTO `sys_role_user` VALUES ('1', '1', '1');
INSERT INTO `sys_userinfo` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', '', '1', null, '7711', null, '', '', '', '', '', '', null, '2020-02-28 22:03:57', null, '2020-02-28 22:03:57');
