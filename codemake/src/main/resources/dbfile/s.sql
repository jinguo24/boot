
DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '权限名称[LIKE]',
  `available` varchar(1) DEFAULT 'Y' COMMENT '是否可用 Y/N',
  `parent_id` varchar(50) DEFAULT NULL COMMENT '父编号',
  `parent_ids` varchar(50) DEFAULT NULL COMMENT '父编号列表',
  `permission` varchar(50) DEFAULT NULL COMMENT '权限字符串[LIST]',
  `resource_type` varchar(50) DEFAULT NULL COMMENT '资源类型0菜单  1，按钮',
  `url` varchar(50) DEFAULT NULL COMMENT '资源路径',
  `sort` int(10) DEFAULT NULL COMMENT '排序[FROM][TO][GTE][GT][LTE][LT]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`name`,`available`,`parent_id`,`parent_ids`,`permission`,`resource_type`,`url`,`sort`) values (2,'xxx',NULL,'12',NULL,'2','0','/user/manage.html',2),(3,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',3),(4,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',4),(5,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',5),(6,'xxx',NULL,'12',NULL,'6','1','/adfas/asdf',6),(7,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',7),(8,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',8),(9,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',9),(10,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',10),(11,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',11),(12,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',12),(13,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',13),(14,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',14),(15,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',15),(16,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',16),(17,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',17),(18,'xxx','Y','12',NULL,NULL,'1','111',18),(19,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',19),(20,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',20),(21,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',21),(22,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',22),(23,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',23),(24,'xxx',NULL,'12',NULL,NULL,'1','/adfas/asdf',24),(25,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(26,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(27,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(28,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(29,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(30,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `available` varchar(1) DEFAULT NULL COMMENT '是否可用',
  `description` varchar(50) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`available`,`description`) values (2,'aaa','1','sdaf'),(3,'xxxxx','1','adfadsfasdf'),(4,'assadfa','1','asdadsa'),(5,'xxxsds','1','asdassddsdd'),(6,'xxxxxdad','1','adadsasd'),(7,'adsasdas','1','adads'),(8,'abc1',NULL,NULL),(9,'test1',NULL,NULL),(11,'abc',NULL,NULL);

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `permission_id` int(20) DEFAULT NULL COMMENT '权限ID',
  `role_id` int(20) DEFAULT NULL COMMENT '角色ID',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_role_permission` */

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `uid` int(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(20) DEFAULT NULL COMMENT '角色ID',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`uid`,`role_id`,`id`) values (16,1,1),(4,1,2),(4,2,3),(4,3,4),(1,1,5),(1,2,6),(1,3,7);

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `name` varchar(64) DEFAULT NULL COMMENT 'url描述',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` int(1) DEFAULT '1' COMMENT '1:有效，0:禁止登录',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_USERNAME` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

/*Data for the table `user_info` */

insert  into `user_info`(`id`,`username`,`name`,`password`,`create_time`,`last_login_time`,`status`) values (4,'张三1','aaa','123456','2018-01-06 00:11:41','2018-01-06 00:11:41',2),(5,'张三2','aaa','123456','2018-01-06 00:11:47','2018-01-06 00:11:47',1),(6,'张三3','aaa','123456','2018-01-06 00:11:50','2018-01-06 00:11:50',1),(7,'张三4','aaa','123456','2018-01-06 00:11:54','2018-01-06 00:11:54',1),(8,'张三5','aaa','123456','2018-01-06 00:11:58','2018-01-06 00:11:58',1),(9,'张三6','aaa','123456','2018-01-06 00:12:01','2018-01-06 00:12:01',1),(10,'张三7','aaa','123456','2018-01-06 00:12:04','2018-01-06 00:12:04',1),(11,'张三8','aaa','123456','2018-01-06 00:12:08','2018-01-06 00:12:08',1),(12,'张三9','aaa','123456','2018-01-06 00:12:11','2018-01-06 00:12:11',1),(13,'张三10','aaa','123456','2018-01-06 00:12:15','2018-01-06 00:12:15',1),(14,'张三11',NULL,'+XDIx3sdgHH4uMi9+foxFS9H7C7CkXs3kEYjgw686d7E0fUp/PxBww==','2018-01-07 13:04:07','2018-01-07 13:04:07',1),(15,'张三12',NULL,'+XDIx3sdgHH4uMi9+foxFS9H7C7CkXs3kEYjgw686d7E0fUp/PxBww==','2018-01-07 13:04:33','2018-01-07 13:04:33',1),(16,'testabc',NULL,'83029422aaca67c577bf2a434816591d','2018-01-07 15:58:25','2018-01-07 15:58:25',1),(17,'zhengfy1@lenovo.com','zfy','63f72eeec4248499fb07bbff40a3cb81','2018-01-15 09:00:43','2018-01-15 09:00:43',1),(18,'zhengfy2@lenovo.com','zfy','74d54bc66ea122a6b3150fb7461ea7d5','2018-01-15 09:00:59','2018-01-15 09:00:59',1),(20,'郑方元',NULL,'3887ce91c6f45597a5f06d6935f5fa50','2018-01-15 22:33:11','2018-01-15 22:33:11',1),(21,'test','测试','test','2018-01-30 16:52:16','2018-01-30 16:52:16',1),(25,'lq',NULL,'123','2018-02-01 12:23:18','2018-02-01 12:23:18',1);

