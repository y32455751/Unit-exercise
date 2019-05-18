/*
Navicat MySQL Data Transfer

Source Server         : GameClient
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : game

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-06-19 08:03:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo` (
  `objuid` varchar(32) NOT NULL,
  `username` varchar(30) NOT NULL,
  `loginname` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL,
  `createtime` datetime(6) DEFAULT NULL,
  `updatetime` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`objuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('ec7d4df0398645f3b04f7ded3437e568', '刘晨', 'admin', 'admin', null, null);
