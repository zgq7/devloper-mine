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

 Date: 12/07/2019 10:18:11
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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of aopi
-- ----------------------------
INSERT INTO `aopi` VALUES (1, '1111', 11111);
INSERT INTO `aopi` VALUES (2, '1', 1);
INSERT INTO `aopi` VALUES (3, '1', 1);
INSERT INTO `aopi` VALUES (4, '3', 3);
INSERT INTO `aopi` VALUES (5, '4', 4);

SET FOREIGN_KEY_CHECKS = 1;
