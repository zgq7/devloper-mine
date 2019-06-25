/*
 Navicat Premium Data Transfer

 Source Server         : mysql-admin
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : dsos_zgq

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 25/06/2019 17:59:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for aopi
-- ----------------------------
DROP TABLE IF EXISTS `aopi`;
CREATE TABLE `aopi`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `age` int(10) NOT NULL COMMENT '年龄',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dsos_live_adminuser
-- ----------------------------
DROP TABLE IF EXISTS `dsos_live_adminuser`;
CREATE TABLE `dsos_live_adminuser`  (
  `adminId` int(11) NOT NULL AUTO_INCREMENT,
  `roleNo` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'lsr001' COMMENT '角色编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `sex` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `adminAccount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录账户',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `idCard` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `homeAt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `birthday` datetime(6) NULL DEFAULT NULL COMMENT '生日',
  `imgRoot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像存放地址',
  `md5Passowrd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'md5加密的密码',
  PRIMARY KEY (`adminId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dsos_live_chainneruser
-- ----------------------------
DROP TABLE IF EXISTS `dsos_live_chainneruser`;
CREATE TABLE `dsos_live_chainneruser`  (
  `chainId` int(11) NOT NULL AUTO_INCREMENT,
  `roleNo` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'lsr003' COMMENT '角色编号',
  `linkedStore` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属门店编号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `sex` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `chainAccount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录账户',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '123456' COMMENT '登录密码',
  `mobile` int(255) NULL DEFAULT NULL COMMENT '手机号',
  `idCard` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证',
  `homeAt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `birthday` datetime(6) NULL DEFAULT NULL COMMENT '生日',
  `imgRoot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像存放地址',
  `md5Passowrd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'md5加密的密码',
  `level` int(10) NOT NULL DEFAULT 1 COMMENT '职位级别',
  `lectruer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '店员' COMMENT '职称',
  PRIMARY KEY (`chainId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dsos_live_memberinfo
-- ----------------------------
DROP TABLE IF EXISTS `dsos_live_memberinfo`;
CREATE TABLE `dsos_live_memberinfo`  (
  `infoId` int(50) NOT NULL AUTO_INCREMENT COMMENT '会员信息标识',
  `cardNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属卡号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `birthday` datetime(0) NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '家庭住址/收货地址',
  `intergration` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '积分',
  `amount` double(50, 0) NULL DEFAULT 0 COMMENT '余额',
  `discount` double(255, 0) NULL DEFAULT 100 COMMENT '折扣率',
  `imgRoot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像存放地址',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `leavel` int(50) NULL DEFAULT 0 COMMENT '会员等级',
  `statu` int(255) NULL DEFAULT 1 COMMENT '会员状态',
  PRIMARY KEY (`infoId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 131 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dsos_live_memberuser
-- ----------------------------
DROP TABLE IF EXISTS `dsos_live_memberuser`;
CREATE TABLE `dsos_live_memberuser`  (
  `cardId` int(50) UNSIGNED NOT NULL AUTO_INCREMENT,
  `roleNo` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'lsr002' COMMENT '角色编号',
  `cardNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录账户，也是卡号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '123456',
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `storeId` int(50) NULL DEFAULT 0 COMMENT '所属门店标识',
  `md5Password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MD5加密的密码',
  PRIMARY KEY (`cardId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 230 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dsos_perms
-- ----------------------------
DROP TABLE IF EXISTS `dsos_perms`;
CREATE TABLE `dsos_perms`  (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `permNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限编号',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限',
  `permUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所能访问的url',
  `permTest` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '权限备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dsos_role
-- ----------------------------
DROP TABLE IF EXISTS `dsos_role`;
CREATE TABLE `dsos_role`  (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `roleNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编号',
  `roleName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色备注',
  `roleText` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dsos_vot_chainrecord
-- ----------------------------
DROP TABLE IF EXISTS `dsos_vot_chainrecord`;
CREATE TABLE `dsos_vot_chainrecord`  (
  `chainId` int(10) NOT NULL COMMENT '连锁ID',
  `chainNo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '连锁编号',
  `chainName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '连锁名称',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地址',
  `businessNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '经营许可证',
  `hodler` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '负责人名称',
  `workNum` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '负责人工作编号',
  `telephone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '连锁热线',
  `logoRoot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '连锁logo图片地址',
  PRIMARY KEY (`chainId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dsos_vot_drugrecord
-- ----------------------------
DROP TABLE IF EXISTS `dsos_vot_drugrecord`;
CREATE TABLE `dsos_vot_drugrecord`  (
  `drugId` int(50) NOT NULL AUTO_INCREMENT,
  `chainId` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '所属连锁',
  `drugName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `drugData` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药品图片地址',
  `drugKind` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '西药' COMMENT '药品种类',
  `drugCode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品编码',
  `barCode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品内码',
  `unitPrice` double(10, 2) NULL DEFAULT 0.00 COMMENT '连锁的单价',
  `storePrice` double(10, 2) NULL DEFAULT NULL COMMENT '门店的单价',
  `costPrice` double(10, 2) NULL DEFAULT NULL COMMENT '进价',
  `unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位',
  `spec` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格',
  `company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '进货厂商',
  `purchaseDate` datetime(0) NULL DEFAULT '2019-01-01 00:00:00' COMMENT '进货日期',
  `produceDate` datetime(0) NULL DEFAULT '2018-12-31 00:00:00' COMMENT '生产日期',
  `effectDate` datetime(0) NULL DEFAULT '2020-01-01 00:00:00' COMMENT '保质日期',
  `approval` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国产准字',
  `explaination` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `isAllowedTrade` tinyint(4) NULL DEFAULT 0 COMMENT '是否允许交易',
  PRIMARY KEY (`drugId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1018 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dsos_vot_drugrecord_chain
-- ----------------------------
DROP TABLE IF EXISTS `dsos_vot_drugrecord_chain`;
CREATE TABLE `dsos_vot_drugrecord_chain`  (
  `drugId` int(50) NOT NULL AUTO_INCREMENT,
  `chainId` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '所属连锁',
  `drugName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `drugData` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药品图片地址',
  `drugKind` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '西药' COMMENT '药品种类',
  `drugCode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品编码',
  `barCode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品内码',
  `unitPrice` double(10, 2) NULL DEFAULT 0.00 COMMENT '连锁的单价',
  `storePrice` double(10, 2) NULL DEFAULT NULL COMMENT '门店的单价',
  `costPrice` double(10, 2) NULL DEFAULT NULL COMMENT '进价',
  `unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位',
  `spec` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格',
  `company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '进货厂商',
  `purchaseDate` datetime(0) NULL DEFAULT '2019-01-01 00:00:00' COMMENT '进货日期',
  `produceDate` datetime(0) NULL DEFAULT '2018-12-31 00:00:00' COMMENT '生产日期',
  `effectDate` datetime(0) NULL DEFAULT '2020-01-01 00:00:00' COMMENT '保质日期',
  `approval` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国产准字',
  `explaination` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `isAllowedTrade` tinyint(4) NULL DEFAULT 0 COMMENT '是否允许交易',
  PRIMARY KEY (`drugId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1018 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dsos_vot_drugrecord_store
-- ----------------------------
DROP TABLE IF EXISTS `dsos_vot_drugrecord_store`;
CREATE TABLE `dsos_vot_drugrecord_store`  (
  `drugId` int(50) NOT NULL AUTO_INCREMENT,
  `chainId` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '所属连锁',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属门店',
  `drugName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药品名称',
  `drugData` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '药品图片地址',
  `drugKind` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '西药' COMMENT '药品种类',
  `drugCode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品编码',
  `barCode` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品内码',
  `unitPrice` double(10, 2) NULL DEFAULT 0.00 COMMENT '连锁的单价',
  `storePrice` double(10, 2) NULL DEFAULT NULL COMMENT '门店的单价',
  `costPrice` double(10, 2) NULL DEFAULT NULL COMMENT '进价',
  `unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位',
  `count` int(11) NULL DEFAULT 100 COMMENT '剩余数量',
  `spec` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格',
  `company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '进货厂商',
  `purchaseDate` datetime(0) NULL DEFAULT '2019-01-01 00:00:00' COMMENT '进货日期',
  `produceDate` datetime(0) NULL DEFAULT '2018-12-31 00:00:00' COMMENT '生产日期',
  `effectDate` datetime(0) NULL DEFAULT '2020-01-01 00:00:00' COMMENT '保质日期',
  `approval` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国产准字',
  `explaination` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `isAllowedTrade` tinyint(4) NULL DEFAULT 0 COMMENT '是否允许交易',
  PRIMARY KEY (`drugId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1018 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dsos_vot_storerecord
-- ----------------------------
DROP TABLE IF EXISTS `dsos_vot_storerecord`;
CREATE TABLE `dsos_vot_storerecord`  (
  `storeId` int(10) NOT NULL AUTO_INCREMENT,
  `linkedId` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '所属连锁',
  `shopHolder` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店负责人编号',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店名称',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店地址',
  `telephone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `businessNo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '经营许可证',
  `logoRoot` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '../images/store/default.jpg' COMMENT '门店照片',
  PRIMARY KEY (`storeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Procedure structure for cus_buy_drug
-- ----------------------------
DROP PROCEDURE IF EXISTS `cus_buy_drug`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `cus_buy_drug`(IN `cardNoz` varchar(50),IN `chainIdz` varchar(50),IN `codez` varchar(50),
IN `drugCodez` varchar(50),IN `buyCount` int)
BEGIN
	  #购买药品存储过程
DECLARE purchasePrice DOUBLE;      #购买总金额
DECLARE remainAmount DOUBLE ;      #所剩金额
DECLARE remainCount  INTEGER;      #药品所剩数量

  set purchasePrice = 
	(SELECT storePrice from dsos_vot_drugrecord_store where chainId = chainIdz and code = codez and drugCode =drugCodez ) * buyCount ;
	
	set remainAmount = (SELECT amount from dsos_live_memberinfo where cardNo = cardNoz);
	
	
	if purchasePrice <= remainAmount then
	    select '1' ;
			else 
			SELECT '0';
	end if;
	
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for login_admin
-- ----------------------------
DROP PROCEDURE IF EXISTS `login_admin`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `login_admin`(IN `account` varchar(19),IN `password` varchar(19))
BEGIN
	#管理员登录
	SELECT * from dsos_live_adminuser where adminAccount = account and `password` = `password`;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for login_chain
-- ----------------------------
DROP PROCEDURE IF EXISTS `login_chain`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `login_chain`(IN `account` varchar(19),IN `password` varchar(19))
BEGIN
	#连锁人员登录
	SELECT * from dsos_live_chainneruser where chainAccount =account and `password` = `password`;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for login_member
-- ----------------------------
DROP PROCEDURE IF EXISTS `login_member`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `login_member`(IN `account` varchar(19),IN `passwordz` varchar(19))
BEGIN
	#普通会员登录
	SELECT * from dsos_live_memberuser where cardNo = account and `password` = `passwordz`;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for pos_add_drug
-- ----------------------------
DROP PROCEDURE IF EXISTS `pos_add_drug`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pos_add_drug`(IN `chainIdz` varchar(19),IN `drugName` varchar(19),IN `drugKind` varchar(19),IN `drugCodez` varchar(19),IN `barCode` varchar(19)
,IN `unitPrice` varchar(19),IN `storePrice` varchar(19),IN `costPrice` varchar(19)
,IN `unit` varchar(19),IN `spec` varchar(19),IN `company` varchar(19),IN `purchaseDate` varchar(19),IN `produceDate` varchar(19),IN `effectDate` varchar(19),IN `approval` varchar(19))
BEGIN
	#添加药品
	if (select count(*) from dsos_vot_drugrecord where drugCode = drugCodez and chainId = chainIdz) > 0 
	then
	###主动抛出异常
	SIGNAL SQLSTATE 'HY000' SET MESSAGE_TEXT = '该信息已存在';
	else
	insert into dsos_vot_drugrecord(chainId,drugName,drugKind,drugCode,barCode,unitPrice,storePrice,costPrice
	                    ,unit,spec,company,purchaseDate,produceDate,effectDate,approval)
	select chainIdz,drugName,drugKind,drugCodez,barCode,unitPrice,storePrice,costPrice
	                    ,unit,spec,company,purchaseDate,produceDate,effectDate,approval ;
	end if;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for pos_add_member
-- ----------------------------
DROP PROCEDURE IF EXISTS `pos_add_member`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pos_add_member`(IN `name` varchar(19),IN `password` varchar(19),IN `mobilez` varchar(19),IN `sex` varchar(19))
BEGIN
	#member 注册
	DECLARE maxCardNo VARCHAR(19);
	set maxCardNo := (SELECT CONCAT('rsd',max(SUBSTRING(cardNo,4))+1)FROM dsos_live_memberuser);
	#select maxCardNo;
	
	if (SELECT count(*) from dsos_live_memberuser WHERE mobile = mobilez) = 0
	then
	
	INSERT into dsos_live_memberuser(cardNo,`password`,mobile)
					VALUES(maxCardNo,IF(`password`='','123456',`password`),mobilez);
					
	INSERT into dsos_live_memberinfo(cardNo,`name`,sex)
	        VALUES(maxCardNo,`name`,sex);
					
					SELECT maxCardNo;
	else
				  select '000';
	end if;
	
					

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for pos_get_chainList
-- ----------------------------
DROP PROCEDURE IF EXISTS `pos_get_chainList`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pos_get_chainList`(IN `chainNoz` varchar(19),IN page integer,IN limitz integer)
BEGIN
	#查询连锁
DECLARE start integer;
set start = (page-1)*limitz;
set @sql = 'select * from dsos_vot_chainrecord where 1 = 1';
  
	if chainNoz <> '' then 
	  set @sql = CONCAT(@sql,' and chainNo= ','''',chainNoz,'''');
	end if;
	
		set @sql = CONCAT(@sql,' limit ',start,', ',limitz);
		
	PREPARE distSQL FROM @SQL ;
     EXECUTE distSQL;
 	DEALLOCATE PREPARE distSQL ;

#select @sql;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for pos_get_chainnerList
-- ----------------------------
DROP PROCEDURE IF EXISTS `pos_get_chainnerList`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pos_get_chainnerList`(IN `chainIdz` varchar(19),IN `codez` varchar(19),IN `accountz` varchar(19),IN `mobilez` varchar(19),IN page integer,IN limitz integer)
BEGIN
	#查询连锁
DECLARE start integer;
set start = (page-1)*limitz;
set @sql = 'select * from dsos_live_chainneruser where 1 = 1';
  
	if chainIdz <> '' then 
	  set @sql = CONCAT(@sql,' and chainId= ','''',chainIdz,'''');
	end if;
	
	if codez <> '' then 
	  set @sql = CONCAT(@sql,' and linkedStore= ','''',codez,'''');
	end if;
	
	if accountz <> '' then 
	  set @sql = CONCAT(@sql,' and chainAccount= ','''',accountz,'''');
	end if;
	
	if mobilez <> '' then 
	  set @sql = CONCAT(@sql,' and mobile= ','''',mobilez,'''');
	end if;
	
		set @sql = CONCAT(@sql,' limit ',start,', ',limitz);
		
	PREPARE distSQL FROM @SQL ;
     EXECUTE distSQL;
 	DEALLOCATE PREPARE distSQL ;

#select @sql;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for pos_get_drugInList
-- ----------------------------
DROP PROCEDURE IF EXISTS `pos_get_drugInList`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pos_get_drugInList`(IN page integer,IN limitz integer,IN drugCodez VARCHAR(50),IN effectDatez VARCHAR(50),IN chainIdz VARCHAR(19),IN updownz VARCHAR(2))
BEGIN
DECLARE start integer;
set start = (page-1)*limitz;
set @sql = 'select * from dsos_vot_drugrecord where 1 = 1';

	#获取药品信息（最多一千条）

	if drugCodez <> '' then 
	  set @sql = CONCAT(@sql,' and drugCode= ','''',drugCodez,'''');
	end if;
	
	if effectDatez <> '' then 
	  set @sql = CONCAT(@sql,' and effectDate <= ','''',effectDatez,'''');
	end if;
	
	if chainIdz <> '' then 
	  set @sql = CONCAT(@sql,' and chainId= ',chainIdz);
	end if;
	
	if updownz <> '' then 
	  set @sql = CONCAT(@sql,' and isAllowedTrade= ',updownz);
	end if;
	
	set @sql = CONCAT(@sql,' limit ',start,', ',limitz);
	
 	PREPARE distSQL FROM @SQL ;
     EXECUTE distSQL;
 	DEALLOCATE PREPARE distSQL ;

#select @sql;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for pos_get_drugInListCount
-- ----------------------------
DROP PROCEDURE IF EXISTS `pos_get_drugInListCount`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pos_get_drugInListCount`(IN drugCodez VARCHAR(50),IN effectDatez VARCHAR(50),IN chainIdz VARCHAR(19),IN updownz VARCHAR(2))
BEGIN

set @sql = 'select count(*) from dsos_vot_drugrecord where 1 = 1';

	#获取药品信息（最多一千条）

	if drugCodez <> '' then 
	  set @sql = CONCAT(@sql,' and drugCode= ',drugCodez);
	end if;
	
	if effectDatez <> '' then 
	  set @sql = CONCAT(@sql,' and effectDate= ','''',effectDatez,'''');
	end if;
	
	if chainIdz <> '' then 
	  set @sql = CONCAT(@sql,' and chainId= ',chainIdz);
	end if;
	
	if updownz <> '' then 
	  set @sql = CONCAT(@sql,' and isAllowedTrade= ',updownz);
	end if;
	
 	PREPARE distSQL FROM @SQL ;
     EXECUTE distSQL;
 	DEALLOCATE PREPARE distSQL ;

#select @sql2;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for pos_get_drugOutList
-- ----------------------------
DROP PROCEDURE IF EXISTS `pos_get_drugOutList`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pos_get_drugOutList`(IN page integer,IN limitz integer)
BEGIN
DECLARE start integer;
set start = (page-1)*limitz;
	#获取药品信息（最多一千条）
	#select start,end;
	SELECT * from dsos_vot_drugrecord LIMIT start,limitz;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for pos_get_memberInfo
-- ----------------------------
DROP PROCEDURE IF EXISTS `pos_get_memberInfo`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pos_get_memberInfo`(IN `account` varchar(50))
BEGIN
	#Routine body goes here...
	SELECT a.`password`,b.*	from dsos_live_memberuser a left join dsos_live_memberinfo b
	on a.cardNo = b.cardNo
	where a.cardNo = account;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for pos_get_memberList
-- ----------------------------
DROP PROCEDURE IF EXISTS `pos_get_memberList`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pos_get_memberList`(IN `codez` varchar(19),IN `accountz` varchar(19),IN `mobilez` varchar(19),IN page integer,IN limitz integer)
BEGIN
	#查询连锁
DECLARE start integer;
set start = (page-1)*limitz;
set @sql = 'select * from dsos_live_memberuser where 1 = 1';
	
	if codez <> '' then 
	  set @sql = CONCAT(@sql,' and storeId= ','''',codez,'''');
	end if;
	
	if accountz <> '' then 
	  set @sql = CONCAT(@sql,' and cardNo= ','''',accountz,'''');
	end if;
	
	if mobilez <> '' then 
	  set @sql = CONCAT(@sql,' and mobile= ','''',mobilez,'''');
	end if;
	
		set @sql = CONCAT(@sql,' limit ',start,', ',limitz);
		
	PREPARE distSQL FROM @SQL ;
     EXECUTE distSQL;
 	DEALLOCATE PREPARE distSQL ;

#select @sql;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for pos_get_memberListCount
-- ----------------------------
DROP PROCEDURE IF EXISTS `pos_get_memberListCount`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pos_get_memberListCount`(IN `codez` varchar(19),IN `accountz` varchar(19),IN `mobilez` varchar(19))
BEGIN
	#查询连锁

set @sql = 'select count(*) from dsos_live_memberuser where 1 = 1';
	
	if codez <> '' then 
	  set @sql = CONCAT(@sql,' and storeId= ','''',codez,'''');
	end if;
	
	if accountz <> '' then 
	  set @sql = CONCAT(@sql,' and cardNo= ','''',accountz,'''');
	end if;
	
	if mobilez <> '' then 
	  set @sql = CONCAT(@sql,' and mobile= ','''',mobilez,'''');
	end if;
		
	PREPARE distSQL FROM @SQL ;
     EXECUTE distSQL;
 	DEALLOCATE PREPARE distSQL ;

#select @sql;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for pos_get_role
-- ----------------------------
DROP PROCEDURE IF EXISTS `pos_get_role`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pos_get_role`(IN `roleNum` varchar(19))
BEGIN
	#以认证登录的账号进行角色查询；
	SELECT * from dsos_role where roleNo = roleNum;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for pos_get_storeList
-- ----------------------------
DROP PROCEDURE IF EXISTS `pos_get_storeList`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pos_get_storeList`(IN `chainNoz` varchar(19),IN `codez` varchar(19),IN page integer,IN limitz integer)
BEGIN
	#查询连锁
DECLARE start integer;
set start = (page-1)*limitz;
set @sql = 'select * from dsos_vot_storerecord where 1 = 1';
  
	if chainNoz <> '' then 
	  set @sql = CONCAT(@sql,' and linkedId= ','''',chainNoz,'''');
	end if;
	
	if codez <> '' then 
	  set @sql = CONCAT(@sql,' and `code`= ','''',codez,'''');
	end if;
	
		set @sql = CONCAT(@sql,' limit ',start,', ',limitz);
		
	PREPARE distSQL FROM @SQL ;
     EXECUTE distSQL;
 	DEALLOCATE PREPARE distSQL ;

#select @sql;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for pos_update_memberInfo
-- ----------------------------
DROP PROCEDURE IF EXISTS `pos_update_memberInfo`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `pos_update_memberInfo`(IN `cardNoz` varchar(19),IN `mobilez` varchar(19),IN `emailz` varchar(19),IN `birthdayz` varchar(19),IN `oldPassword` varchar(19),IN `newPassword` varchar(19),IN `addressz` varchar(19),IN `sexz` varchar(19))
BEGIN
	#修改个人资料
	#Update dsos_live_memberinfo set ;
	if(select count(*) from dsos_live_memberuser where cardNo = `cardNoz` and `password` = oldPassword) > 0
	then
	
	update dsos_live_memberinfo set email = emailz , birthday = birthdayz ,address = addressz	, sex = sexz
	where cardNo = cardNoz;
	
	update dsos_live_memberuser set mobile = mobilez ,`password` = newPassword where cardNo = cardNo ;
	
-- 	else select '0' ;
	end if ;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
