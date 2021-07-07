/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3307
 Source Schema         : maruko

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 07/07/2021 10:39:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sql_info
-- ----------------------------
DROP TABLE IF EXISTS `sql_info`;
CREATE TABLE `sql_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `jdbc_id` bigint(20) NOT NULL COMMENT '数据库id',
  `sql` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行SQL',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'sql信息表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
