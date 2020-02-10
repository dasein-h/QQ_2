/*
Navicat MySQL Data Transfer

Source Server         : 我
Source Server Version : 80018
Source Host           : localhost:3306
Source Database       : qq

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-02-09 21:32:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qq_entry
-- ----------------------------
DROP TABLE IF EXISTS `qq_entry`;
CREATE TABLE `qq_entry` (
  `the_id` int(11) NOT NULL AUTO_INCREMENT,
  `the_name` int(11) DEFAULT NULL,
  `the_password` int(11) DEFAULT NULL,
  PRIMARY KEY (`the_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of qq_entry
-- ----------------------------
INSERT INTO `qq_entry` VALUES ('1', '111111', '111111');
INSERT INTO `qq_entry` VALUES ('2', '222222', '222222');
INSERT INTO `qq_entry` VALUES ('3', '333333', '333333');
