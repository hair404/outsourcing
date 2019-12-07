/*
Navicat MySQL Data Transfer

Source Server         : ccc
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : serviceplatform

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-11-21 14:56:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tel` (`tel`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', '111', '123');
INSERT INTO `account` VALUES ('2', '222', '123');
INSERT INTO `account` VALUES ('3', '333', '123');
INSERT INTO `account` VALUES ('4', '444', '123');
INSERT INTO `account` VALUES ('5', '555', '123');
INSERT INTO `account` VALUES ('7', '13777581342', '123456');
INSERT INTO `account` VALUES ('8', '888', '123');

-- ----------------------------
-- Table structure for `activity`
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES ('1', '/activity_img/timgddd.jpg', '111');
INSERT INTO `activity` VALUES ('2', '/activity_img/a8dc1d33f56516782c62989ce7b46f.jpg', '111');
INSERT INTO `activity` VALUES ('3', '/activity_img/u=3213720596,1829163755&fm=26&gp=0.jpg.png', '222');

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `account` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', '管理员', '888', '123', '0');

-- ----------------------------
-- Table structure for `ad_project`
-- ----------------------------
DROP TABLE IF EXISTS `ad_project`;
CREATE TABLE `ad_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `weight` float(11,2) DEFAULT NULL,
  `due_time` date NOT NULL,
  `prjname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tag` int(11) NOT NULL,
  `subtag` int(11) DEFAULT NULL,
  `img` varchar(255) NOT NULL,
  `price` float(255,0) NOT NULL,
  `ad_price` float NOT NULL,
  `solr_id` varchar(11) NOT NULL,
  `prj_id` int(11) NOT NULL,
  `state` int(2) NOT NULL,
  PRIMARY KEY (`id`,`solr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ad_project
-- ----------------------------
INSERT INTO `ad_project` VALUES ('1', '45.60', '2019-10-09', '黑客已侵入你的网站12', '1', '1', '/prjimg/timg (2).jpg', '555', '2', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('2', '61.33', '2019-10-17', '人工智能', '5', '0', '/prjimg/ph1015-p08165.jpg', '9990', '0', 'aff', '1', '1');
INSERT INTO `ad_project` VALUES ('3', '77.00', '2019-12-06', '手机软件模块开发', '4', '0', '/prjimg/timgD58PK0GH.jpg', '8000', '0', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('4', '4564.00', '2015-09-05', 'python爬虫爬取网易云评论', '1', '1', '/prjimg/timg71T5JU6W.jpg', '68777', '2', '1', '1', '1');
INSERT INTO `ad_project` VALUES ('5', '44.00', '2019-09-04', '这是一个测试项目', '1', '1', '/prjimg/timg1FAOACUI.jpg\r\n', '8888', '7777', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('6', '33.00', '2019-09-28', '小爱同学', '1', '1', '/prjimg/timgM15YJ0LZ.jpg', '886', '33', 'df', '1', '1');
INSERT INTO `ad_project` VALUES ('7', '44.00', '2019-09-13', '微软小冰', '1', '1', '/prjimg/timgTFJ3U4SZ.jpg', '5867', '5', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('8', '43.00', '2019-09-20', 'c++算法学习', '1', '1', '://localhost:8080/prjimg/timgZEC31A2U.jpg', '2878', '24324', 'aff', '1', '1');
INSERT INTO `ad_project` VALUES ('9', '33.00', '2019-09-05', 'just for test', '2', '1', '/prjimg/timgD58PK0GH.jpg', '868', '3', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('10', '34.00', '2019-09-20', 'web', '2', '1', '/prjimg/timgcbh.jpg', '868', '34', 'aff', '1', '1');
INSERT INTO `ad_project` VALUES ('11', '33.00', '2019-08-16', '网页制作', '2', '1', '/prjimg/timgKYSAJEN1.jpg', '678', '24', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('12', '33.00', '2019-08-31', 'hacker', '4', '0', '/prjimg/timgS2ZAWAFT.jpg', '2678', '3', 'df', '1', '1');
INSERT INTO `ad_project` VALUES ('13', '44.00', '2019-09-28', 'hey siri', '1', '1', '/prjimg/d8ef5g.jpg', '268', '2', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('14', '23.00', '2019-09-06', '图片打包下载', '1', '1', '/prjimg/u=1448476059,3511315932&fm=26&gp=0.jpg', '5867', '1', 'aff', '1', '1');
INSERT INTO `ad_project` VALUES ('15', '11.00', '2019-09-12', '扑街', '1', '1', '/prjimg/csgo.jpg', '3678', '1', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('16', '33.00', '2019-08-29', 'qq破解软件', '2', '1', '/prjimg/timg.jpg', '25555', '4', 'aff', '1', '1');
INSERT INTO `ad_project` VALUES ('17', '33.00', '2019-09-06', 'bilibil视频下载', '4', '1', '/prjimg/timgKMO118B8.jpg', '18888', '1', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('18', '33.00', '2019-09-14', 'vpn', '2', '1', '/prjimg/timg5ZHLL4H5.jpg', '1877', '1', 'df', '1', '1');
INSERT INTO `ad_project` VALUES ('19', '22.00', '2019-09-04', '算法入门', '1', '1', '/prjimg/u.jpg', '20000', '1', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('20', '2.00', '2019-09-06', 'it is a test to', '2', '1', '/prjimg/timgX0F1RMXM.jpg', '200', '1', 'aff', '1', '1');
INSERT INTO `ad_project` VALUES ('21', '22.00', '2019-08-29', 'c sharp', '1', '1', '/prjimg/timgEVR6NOGQ.jpg', '2', '1', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('22', '3.00', '2019-08-29', 'unity', '3', '1', '/prjimg/unity.jpg', '2', '23', 'aff', '1', '1');
INSERT INTO `ad_project` VALUES ('23', '233.00', '2019-10-09', '智能车嵌入式系统', '1', '0', '/prjimg/a603c865-d05e-4a92-9115-eba8d769600a.jpg', '1100', '45', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('24', '31.20', '2019-10-09', '黑客已侵入你的网站1', '1', '1', '/prjimg/timg(1).jpg', '555', '2', 'df', '1', '1');
INSERT INTO `ad_project` VALUES ('25', '45.60', '2019-10-09', '黑客已侵入你的网站10', '1', '1', '/prjimg/timg (11).jpg', '555', '2', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('26', '23.00', '2019-10-03', '黑客已侵入你的网站2', '1', '1', '/prjimg/timg (2).jpg', '2222', '1', 'aff', '1', '1');
INSERT INTO `ad_project` VALUES ('27', '222.00', '2019-10-04', '黑客已侵入你的网站3', '1', '1', '/prjimg/timg (3).jpg', '222', '2', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('28', '22.00', '2019-10-11', '黑客已侵入你的网站4', '4', '0', '/prjimg/timg (4).jpg', '66', '1', 'aff', '1', '1');
INSERT INTO `ad_project` VALUES ('29', '55.00', '2019-10-09', '黑客已侵入你的网站4', '1', '1', '/prjimg/timg (5).jpg', '5556', '2', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('30', '88.00', '2019-10-09', '黑客已侵入你的网站5', '1', '1', '/prjimg/timg (6).jpg', '587', '22', 'df', '1', '1');
INSERT INTO `ad_project` VALUES ('31', '5.60', '2019-10-09', '黑客已侵入你的网站6', '1', '0', '/prjimg/timg (7).jpg', '555', '2', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('32', '42.00', '2019-10-09', '黑客已侵入你的网站7', '1', '0', '/prjimg/timg (8).jpg', '55', '2', 'aff', '1', '1');
INSERT INTO `ad_project` VALUES ('33', '45.60', '2019-10-09', '黑客已侵入你的网站8', '1', '0', '/prjimg/timg (9).jpg', '555', '2', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('34', '11.00', '2019-10-09', '黑客已侵入你的网站9', '1', '1', '/prjimg/timg (10).jpg', '555', '2', 'aff', '1', '1');
INSERT INTO `ad_project` VALUES ('35', '60.00', '2019-10-09', '黑客已侵入你的网站10', '1', '0', '/prjimg/timg (11).jpg', '555', '2', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('36', '45.00', '2019-10-09', '黑客已侵入你的网站11', '1', '1', '/prjimg/timg (5).jpg', '555', '2', 'df', '1', '1');
INSERT INTO `ad_project` VALUES ('37', '10.00', '2019-10-09', '黑客已侵入你的网站13', '1', '0', '/prjimg/timg (11).jpg', '555', '2', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('38', '45.60', '2019-10-09', '黑客已侵入你的网站10', '1', '1', '/prjimg/timg (11).jpg', '555', '2', 'aff', '1', '1');
INSERT INTO `ad_project` VALUES ('39', '4.20', '2019-10-09', '黑客已侵入你的网站10', '1', '0', '/prjimg/timg (11).jpg', '555', '2', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('40', '33.00', '2019-10-09', '黑客已侵入你的网站10', '1', '0', '/prjimg/timg (11).jpg', '555', '2', 'aff', '1', '1');
INSERT INTO `ad_project` VALUES ('41', '89.00', '2019-10-09', '黑客已侵入你的网站10', '1', '1', '/prjimg/timg (11).jpg', '555', '2', 'sefe', '1', '1');
INSERT INTO `ad_project` VALUES ('42', '4.00', '2019-10-09', '黑客已侵入你的网站10', '1', '1', '/prjimg/timg (11).jpg', '555', '2', 'df', '1', '1');
INSERT INTO `ad_project` VALUES ('43', '45.00', '2019-10-09', '黑客已侵入你的网站10', '1', '0', '/prjimg/timg (11).jpg', '555', '2', 'sefe', '1', '1');

-- ----------------------------
-- Table structure for `ad_studio`
-- ----------------------------
DROP TABLE IF EXISTS `ad_studio`;
CREATE TABLE `ad_studio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tag` varchar(255) NOT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `avatar` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `tel` varchar(255) NOT NULL,
  `ad_price` float NOT NULL,
  `weight` float(11,2) NOT NULL,
  `account_id` int(11) NOT NULL,
  `solr_id` varchar(255) NOT NULL,
  `credit` float NOT NULL,
  `state` int(2) NOT NULL,
  PRIMARY KEY (`id`,`solr_id`),
  KEY `account_id` (`account_id`),
  CONSTRAINT `ad_studio_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ad_studio
-- ----------------------------
INSERT INTO `ad_studio` VALUES ('1', '黑客已侵入你的网站吗', '1', '/prjimg/timg (2).jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '2', '45.60', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('2', '人工智能', '4', '/prjimg/ph1015-p08165.jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '0', '61.33', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('3', '手机软件模块开发', '5', '/prjimg/timgD58PK0GH.jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '0', '77.00', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('4', 'python爬虫爬取网易云评论', '1', '/prjimg/timg71T5JU6W.jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '2', '4564.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('5', '这是一个测试项目', '1', '/prjimg/timg1FAOACUI.jpg\r\n', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '7777', '44.00', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('6', '小爱同学', '1', '/prjimg/timgM15YJ0LZ.jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '33', '33.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('7', '微软小冰', '1', '/prjimg/timgTFJ3U4SZ.jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '5', '44.00', '1', '1', '5', '1');
INSERT INTO `ad_studio` VALUES ('8', 'c++算法学习', '1', '/prjimg/timgZEC31A2U.jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '24324', '43.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('9', 'just for test', '2', '/prjimg/timgD58PK0GH.jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '3', '33.00', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('10', 'web', '2', '/prjimg/timgcbh.jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '34', '34.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('11', '网页制作', '2', '/prjimg/timgKYSAJEN1.jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '24', '33.00', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('12', 'hacker', '4', '/prjimg/timgS2ZAWAFT.jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '3', '33.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('13', 'hey siri', '1', '/prjimg/d8ef5g.jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '2', '44.00', '1', '1', '3.5', '1');
INSERT INTO `ad_studio` VALUES ('14', '图片打包下载', '1', '/prjimg/u=1448476059,3511315932&fm=26&gp=0.jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '1', '23.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('15', '扑街', '1', '/prjimg/csgo.jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '1', '11.00', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('16', 'qq破解软件', '2', '/prjimg/timg.jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '4', '33.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('17', 'bilibil视频下载', '4', '/prjimg/timgKMO118B8.jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '1', '33.00', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('18', 'vpn', '2', '/prjimg/timg5ZHLL4H5.jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '1', '33.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('19', '算法入门', '1', '/prjimg/u.jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '1', '22.00', '1', '1', '3.8', '1');
INSERT INTO `ad_studio` VALUES ('20', 'it is a test to', '2', '/prjimg/timgX0F1RMXM.jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '1', '2.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('21', 'c sharp', '1', '/prjimg/timgEVR6NOGQ.jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '1', '22.00', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('22', 'unity', '3', '/prjimg/unity.jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '23', '3.00', '1', '1', '4', '1');
INSERT INTO `ad_studio` VALUES ('23', '智能车嵌入式系统', '1', '/prjimg/a603c865-d05e-4a92-9115-eba8d769600a.jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '45', '233.00', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('24', '热热热', '1', '/prjimg/timg(1).jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '2', '31.20', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('25', '融入', '1', '/prjimg/timg (11).jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '2', '45.60', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('26', '嘀嘀咕咕', '1', '/prjimg/timg (2).jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '1', '23.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('27', '华人', '1', '/prjimg/timg (3).jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '2', '222.00', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('28', 'good', '4', '/prjimg/timg (4).jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '1', '22.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('29', '大概', '1', '/prjimg/timg (5).jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '2', '55.00', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('30', '递归', '1', '/prjimg/timg (6).jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '22', '88.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('31', '44', '1', '/prjimg/timg (7).jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '2', '5.60', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('32', 'not bad', '1', '/prjimg/timg (8).jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '2', '42.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('33', '订单', '1', '/prjimg/timg (9).jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '2', '45.60', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('34', 'boring day', '1', '/prjimg/timg (10).jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '2', '11.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('35', 'pear', '1', '/prjimg/timg (11).jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '2', '60.00', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('36', '符号', '1', '/prjimg/timg (5).jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '2', '45.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('37', '说的对', '1', '/prjimg/timg (11).jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '2', '10.00', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('38', '淡淡', '1', '/prjimg/timg (11).jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '2', '45.60', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('39', 'yyy', '1', '/prjimg/timg (11).jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '2', '4.20', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('40', 'happy day', '1', '/prjimg/timg (11).jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '2', '33.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('41', '规划', '1', '/prjimg/timg (11).jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '2', '89.00', '1', '1', '3', '1');
INSERT INTO `ad_studio` VALUES ('42', '高度', '1', '/prjimg/timg (11).jpg', '/prjimg/timg (2).jpg', '12@163.com', '18886666688', '2', '4.00', '1', '1', '4.6', '1');
INSERT INTO `ad_studio` VALUES ('43', 'apple', '1', '/prjimg/timg (11).jpg', '/prjimg/timg (2).jpg', '666@163.com', '18888888888', '2', '45.00', '1', '1', '3', '1');

-- ----------------------------
-- Table structure for `bid`
-- ----------------------------
DROP TABLE IF EXISTS `bid`;
CREATE TABLE `bid` (
  `id` int(11) NOT NULL,
  `studio_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  `quote` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `prj_id` (`project_id`),
  KEY `company_id` (`company_id`),
  CONSTRAINT `bid_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `bid_ibfk_2` FOREIGN KEY (`company_id`) REFERENCES `project` (`companyid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bid
-- ----------------------------
INSERT INTO `bid` VALUES ('1', '2', '1', '3', '0', '0');
INSERT INTO `bid` VALUES ('2', '4', '1', '3', '2', '0');
INSERT INTO `bid` VALUES ('3', '5', '1', '3', '0', '0');

-- ----------------------------
-- Table structure for `cancel_reason`
-- ----------------------------
DROP TABLE IF EXISTS `cancel_reason`;
CREATE TABLE `cancel_reason` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `studioid` int(11) NOT NULL,
  `reason` varchar(255) NOT NULL,
  `project_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `studioid` (`studioid`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `cancel_reason_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cancel_reason
-- ----------------------------
INSERT INTO `cancel_reason` VALUES ('1', '2', 'why', '1');

-- ----------------------------
-- Table structure for `child_form`
-- ----------------------------
DROP TABLE IF EXISTS `child_form`;
CREATE TABLE `child_form` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `time` int(11) NOT NULL,
  `price` float NOT NULL,
  `project_id` int(11) NOT NULL,
  `part` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  `info` varchar(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `child_form_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of child_form
-- ----------------------------
INSERT INTO `child_form` VALUES ('1', '第一', '12', '1', '1', '1', '0', '反对反对');
INSERT INTO `child_form` VALUES ('2', '第二', '12', '1', '1', '1', '0', '反对');
INSERT INTO `child_form` VALUES ('5', '第一', '12', '0', '2', '1', '0', '反对反对');
INSERT INTO `child_form` VALUES ('6', '第二', '12', '0', '2', '1', '0', '反对');
INSERT INTO `child_form` VALUES ('7', '第一', '12', '0', '2', '1', '0', '反对反对');
INSERT INTO `child_form` VALUES ('8', '第一', '12', '0', '2', '1', '0', '反对反对');
INSERT INTO `child_form` VALUES ('9', '第一', '12', '0', '2', '1', '0', '反对反对');
INSERT INTO `child_form` VALUES ('10', '第fdf', '12', '0', '2', '1', '0', '反对');
INSERT INTO `child_form` VALUES ('20', '首页', '12', '123', '3', '0', '0', '121');
INSERT INTO `child_form` VALUES ('21', '1221', '12', '123', '3', '1', '1', '12');
INSERT INTO `child_form` VALUES ('22', '212', '12', '123', '3', '2', '0', '12');

-- ----------------------------
-- Table structure for `complain_reason`
-- ----------------------------
DROP TABLE IF EXISTS `complain_reason`;
CREATE TABLE `complain_reason` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `entity` int(11) NOT NULL,
  `reason` varchar(255) NOT NULL,
  `project_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `studioid` (`entity`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `complain_reason_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of complain_reason
-- ----------------------------
INSERT INTO `complain_reason` VALUES ('1', '2', 'why', '1');

-- ----------------------------
-- Table structure for `ctg`
-- ----------------------------
DROP TABLE IF EXISTS `ctg`;
CREATE TABLE `ctg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ctg` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ctg
-- ----------------------------
INSERT INTO `ctg` VALUES ('1', '[{name: \'全部\'},{name: \'网站开发\',subctg: [\'前端开发\', \'网站维护\']},{name: \'移动应用开发\',subctg: [\'安卓APP\', \'苹果APP\']},{name: \'H5开发\',subctg: [\'H5模板\', \'H5定制\']},{name: \'UI设计\',subctg: [\'网站UI\', \'移动UI\']},{ name: \'测试运维\' },{ name: \'云服务\' },{ name: \'IT综合服务\' }]');

-- ----------------------------
-- Table structure for `file_project`
-- ----------------------------
DROP TABLE IF EXISTS `file_project`;
CREATE TABLE `file_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL,
  `prj_id` int(11) NOT NULL,
  `ispassed` int(11) NOT NULL,
  `step_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `prj_id` (`prj_id`),
  CONSTRAINT `file_project_ibfk_1` FOREIGN KEY (`prj_id`) REFERENCES `project` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_project
-- ----------------------------
INSERT INTO `file_project` VALUES ('1', 'http://localhost:8080/upload_file/todolist.txt', '1', '0', '0');
INSERT INTO `file_project` VALUES ('2', 'http://localhost:8080/upload_file/todolist.txt', '2', '0', '0');
INSERT INTO `file_project` VALUES ('3', '/upload_file/action.jpg', '1', '0', '2');
INSERT INTO `file_project` VALUES ('4', '/file/1/2/action.jpg', '1', '0', '2');
INSERT INTO `file_project` VALUES ('5', '/file/1/3/副本.xlsx', '1', '0', '3');
INSERT INTO `file_project` VALUES ('6', '/file/3/0/滑稽.jpg', '3', '0', '0');

-- ----------------------------
-- Table structure for `fund`
-- ----------------------------
DROP TABLE IF EXISTS `fund`;
CREATE TABLE `fund` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `money` float NOT NULL,
  `usage` varchar(255) NOT NULL,
  `from` varchar(255) NOT NULL,
  `to` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fund
-- ----------------------------
INSERT INTO `fund` VALUES ('1', '1', 'dddd', '12', '13');

-- ----------------------------
-- Table structure for `like`
-- ----------------------------
DROP TABLE IF EXISTS `like`;
CREATE TABLE `like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prjId` int(11) NOT NULL,
  `date` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `userid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of like
-- ----------------------------

-- ----------------------------
-- Table structure for `member`
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `tel` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `info` varchar(255) NOT NULL,
  `studioid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `member_ibfk_1` (`studioid`),
  CONSTRAINT `member_ibfk_1` FOREIGN KEY (`studioid`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('4', '24234232', '123244234', 'CommAdama@outlook.com', '3423423', '4');

-- ----------------------------
-- Table structure for `project`
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prjname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tag` int(11) NOT NULL,
  `subtag` int(11) NOT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `company_name` varchar(255) NOT NULL,
  `companyid` int(11) NOT NULL,
  `price` float NOT NULL,
  `release_time` date NOT NULL,
  `deadline` date NOT NULL,
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state` int(11) NOT NULL,
  `if_ad` int(11) NOT NULL,
  `entity` varchar(255) NOT NULL,
  `studioid` int(11) NOT NULL,
  `solr_id` varchar(255) NOT NULL,
  `isdeposit` int(11) NOT NULL,
  `ispia` int(11) NOT NULL,
  `has_paid` int(11) NOT NULL,
  `payinadvance` int(11) NOT NULL,
  `isform` int(11) NOT NULL,
  `issetprice` int(11) NOT NULL,
  `total_part` int(11) DEFAULT NULL,
  `current` int(11) DEFAULT NULL,
  `isconfirm` int(11) NOT NULL,
  `start_time` date DEFAULT NULL,
  `pay_deadline` date DEFAULT NULL,
  `company_rate` float(11,0) DEFAULT NULL,
  `studio_rate` float(11,0) DEFAULT NULL,
  `countdown` date DEFAULT NULL,
  PRIMARY KEY (`id`,`solr_id`),
  KEY `companyId` (`companyid`) USING BTREE,
  KEY `studioid` (`studioid`),
  KEY `solr_id` (`solr_id`),
  KEY `id` (`id`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`companyid`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', '无人车应用', '1', '1', '/prjimg/d8ef5g.jpg', '天猫', '1', '11110', '2019-08-30', '2019-12-07', '百度服务外包', '7', '1', 'project', '2', '12', '0', '0', '0', '2222', '1', '0', '5', '1', '1', '2019-08-31', '2019-11-05', null, '2', '2019-11-23');
INSERT INTO `project` VALUES ('2', '网页前端', '1', '0', '/prjimg/u.jpg', '百度', '3', '55550', '2019-08-24', '2019-08-31', '需要人数5', '1', '1', 'project', '0', 'aff', '0', '0', '0', '5000', '0', '0', '3', '1', '0', '2020-01-01', null, '1', '3', null);
INSERT INTO `project` VALUES ('3', '移动应用开发', '2', '1', '/prjimg/timg71T5JU6W.jpg', '天猫', '1', '12310', '2019-08-31', '2019-09-01', '没有信息', '3', '0', 'project', '4', 'df', '0', '1', '1', '1000', '1', '1', '0', '0', '1', '2019-11-14', '2019-11-16', '0', '0', '2019-11-22');
INSERT INTO `project` VALUES ('4', '仿生测试', '5', '0', '/prjimg/timgKYSAJEN1.jpg', '百度', '3', '22000', '2019-08-31', '2020-01-24', '2人', '2', '1', 'project', '0', 'sefe', '0', '0', '0', '1111', '0', '0', '1', null, '0', null, null, null, null, null);
INSERT INTO `project` VALUES ('5', '0', '1', '0', '/prjimg/timgcbh.jpg', '天猫', '1', '10000', '2019-05-06', '2019-05-03', '0', '1', '0', 'project', '0', 'sffs', '0', '0', '0', '1444', '0', '0', null, null, '0', null, null, null, null, null);
INSERT INTO `project` VALUES ('6', '逆袭', '1', '1', '/prjimg/timgZEC31A2U.jpg', '天猫', '1', '100001', '2019-05-06', '2019-05-03', '0', '2', '0', 'project', '0', 'sfsf', '0', '0', '0', '2000', '0', '0', null, null, '0', null, null, null, null, null);
INSERT INTO `project` VALUES ('7', 'ddd', '1', '1', 'http://localhost:8080/prjimg/sMg24PGM.jpg', '', '1', '11', '2019-11-21', '2019-01-22', 'sdsd', '0', '0', 'project', '0', 'sMg24PGM', '0', '0', '0', '1', '0', '0', null, null, '0', null, null, null, null, null);
INSERT INTO `project` VALUES ('8', '人工智能', '1', '1', 'http://localhost:8080/prjimg/KwQ6nXCx.jpg', '', '1', '12345', '2019-11-21', '2019-12-20', '开发', '1', '0', 'project', '0', 'KwQ6nXCx', '0', '0', '0', '12', '0', '0', null, null, '0', null, null, null, null, null);

-- ----------------------------
-- Table structure for `complain`
-- ----------------------------
DROP TABLE IF EXISTS `complain`;
CREATE TABLE `complain` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `money` float NOT NULL,
  `project_id` int(11) NOT NULL,
  `studio_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `reason` varchar(255) NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of complain
-- ----------------------------
INSERT INTO `complain` VALUES ('1', '12', '3', '4', '1', '1', '卧槽', '0');
INSERT INTO `complain` VALUES ('2', '12', '3', '4', '1', '1', 'wjdbaw', '0');
INSERT INTO `complain` VALUES ('3', '12', '3', '4', '1', '1', 'wjdbaw', '0');
INSERT INTO `complain` VALUES ('4', '12', '3', '4', '1', '1', 'dawdaw', '0');
INSERT INTO `complain` VALUES ('5', '12', '3', '4', '1', '1', 'dakjbw', '0');
INSERT INTO `complain` VALUES ('6', '12', '3', '4', '1', '0', 'djkabw', '0');

-- ----------------------------
-- Table structure for `tag`
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `tag` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `tag_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES ('1', '1', '2');
INSERT INTO `tag` VALUES ('2', '1', '3');
INSERT INTO `tag` VALUES ('3', '2', '1');
INSERT INTO `tag` VALUES ('4', '3', '2');
INSERT INTO `tag` VALUES ('5', '4', '1');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `solr_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `account_id` int(11) DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tel` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `alipay` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  `img` varchar(255) DEFAULT NULL,
  `entity` varchar(255) NOT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `credit` float NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `is_valid` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`account_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '1', '1', '天猫', '马云', '111', 'unknow', null, 'mayun@alibaba.com', '0', '/userimg/head1.jpg', 'company', '/avatar/30JWbkfI.jpg', '1', null, '1');
INSERT INTO `user` VALUES ('2', 'ssss', '2', '雷电工作室', '李波', '222', 'java,h5,c++', null, 'leidian@163.com', '1', '/userimg/12.png', 'studio', '/avatar/timg3.jpg', '2.5', null, '1');
INSERT INTO `user` VALUES ('3', 'qqqq', '3', '百度', '李彦宏', '333', '百度人工智能，无人车', null, 'lyh@baidu.com', '0', '/userimg/12.png', 'company', '/avatar/timg1.jpg', '5', null, '1');
INSERT INTO `user` VALUES ('4', 'qqqqqq', '4', '传讯工作室', '张三', '444', 'c+++', null, 'zs@163.com', '1', '/avatar/qqqq.png', 'studio', '/avatar/qqqq.png', '2', null, '1');
INSERT INTO `user` VALUES ('5', 'qqqqqqfddd', '5', '人工智能工作室', '李四', '555', 'python', null, 'ls@163.com', '0', '/userimg/12.png', 'studio', '/avatar/timg1.jpg', '3', null, '1');
INSERT INTO `user` VALUES ('6', 'ddd', '7', 'TencentStudio', '罗成', '13777581342', 'bb', '', 'bb@123.com', '1', null, 'studio', null, '4.3', 'f974f4ba-ed1f-4ac3-abde-a7243b723ee5', '1');
