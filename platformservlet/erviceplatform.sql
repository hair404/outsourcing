/*
Navicat MySQL Data Transfer

Source Server         : ccc
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : serviceplatform

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-10-07 12:38:09
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', '111', '123');
INSERT INTO `account` VALUES ('2', '222', '123');
INSERT INTO `account` VALUES ('3', '333', '123');
INSERT INTO `account` VALUES ('4', '444', '123');

-- ----------------------------
-- Table structure for `ad_project`
-- ----------------------------
DROP TABLE IF EXISTS `ad_project`;
CREATE TABLE `ad_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `weight` float(11,2) DEFAULT NULL,
  `due_time` date NOT NULL,
  `prj_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tag` int(11) NOT NULL,
  `sub_tag` int(11) DEFAULT NULL,
  `img` varchar(255) NOT NULL,
  `price` float DEFAULT NULL,
  `ad_price` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ad_project
-- ----------------------------
INSERT INTO `ad_project` VALUES ('2', '61.33', '2019-10-17', 'qww', '0', '0', 'http://localhost:8080/userimg/timg.jpg', '0', '0');
INSERT INTO `ad_project` VALUES ('3', '77.00', '2019-12-06', 'qw', '0', '0', 'http://localhost:8080/userimg/timg.jpg', '0', '0');
INSERT INTO `ad_project` VALUES ('4', '4564.00', '2015-09-05', 'kjkj', '1', '5', 'http://localhost:8080/userimg/timg.jpg', '6', '2');
INSERT INTO `ad_project` VALUES ('5', '44.00', '2019-09-04', '3rtw', '1', '1', 'http://localhost:8080/userimg/timg.jpg', '1', '7777');
INSERT INTO `ad_project` VALUES ('6', '33.00', '2019-09-28', '3rr3', '1', '1', 'http://localhost:8080/userimg/timg.jpg', '3', '33');
INSERT INTO `ad_project` VALUES ('7', '44.00', '2019-09-13', 'etewt', '1', '1', 'http://localhost:8080/userimg/12.png', '5', '5');
INSERT INTO `ad_project` VALUES ('8', '43.00', '2019-09-20', '234', '1', '1', 'http://localhost:8080/userimg/12.png', '2', '24324');
INSERT INTO `ad_project` VALUES ('9', '33.00', '2019-09-05', '234', '2', '2', 'http://localhost:8080/userimg/12.png', '2', '3');
INSERT INTO `ad_project` VALUES ('10', '34.00', '2019-09-20', '234', '2', '2', 'http://localhost:8080/userimg/12.png', '3', '34');
INSERT INTO `ad_project` VALUES ('11', '33.00', '2019-08-16', '343', '2', '2', 'http://localhost:8080/userimg/12.png', '2', '24');
INSERT INTO `ad_project` VALUES ('12', '33.00', '2019-08-31', '324', '4', '0', 'http://localhost:8080/userimg/12.png', '2', '3');
INSERT INTO `ad_project` VALUES ('13', '44.00', '2019-09-28', '34324', '1', '1', 'http://localhost:8080/userimg/12.png', '2', '2');
INSERT INTO `ad_project` VALUES ('14', '23.00', '2019-09-06', '32', '1', '1', 'http://localhost:8080/userimg/12.png', '1', '1');
INSERT INTO `ad_project` VALUES ('15', '11.00', '2019-09-12', '331', '1', '1', 'http://localhost:8080/userimg/12.png', '3', '1');
INSERT INTO `ad_project` VALUES ('16', '33.00', '2019-08-29', '234', '2', '2', 'http://localhost:8080/userimg/12.png', '2', '4');
INSERT INTO `ad_project` VALUES ('17', '33.00', '2019-09-06', '34', '4', '1', 'http://localhost:8080/userimg/12.png', '1', '1');
INSERT INTO `ad_project` VALUES ('18', '33.00', '2019-09-14', '334', '2', '2', 'http://localhost:8080/userimg/12.png', '1', '1');
INSERT INTO `ad_project` VALUES ('19', '22.00', '2019-09-04', '13', '1', '1', 'http://localhost:8080/userimg/12.png', '2', '1');
INSERT INTO `ad_project` VALUES ('20', '2.00', '2019-09-06', 're', '2', '1', 'http://localhost:8080/userimg/12.png', '2', '1');
INSERT INTO `ad_project` VALUES ('21', '22.00', '2019-08-29', 'q', '1', '1', 'http://localhost:8080/userimg/12.png', '2', '1');
INSERT INTO `ad_project` VALUES ('22', '3.00', '2019-08-29', '33', '3', '3', 'http://localhost:8080/userimg/12.png', '2', '23');

-- ----------------------------
-- Table structure for `ad_studio`
-- ----------------------------
DROP TABLE IF EXISTS `ad_studio`;
CREATE TABLE `ad_studio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `weight` float(11,2) DEFAULT NULL,
  `due_time` date NOT NULL,
  `prj_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tag` int(11) NOT NULL,
  `sub_tag` int(11) DEFAULT NULL,
  `img` varchar(255) NOT NULL,
  `price` float DEFAULT NULL,
  `ad_price` float NOT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ad_studio
-- ----------------------------
INSERT INTO `ad_studio` VALUES ('1', '65.22', '2019-08-26', 'qw', '0', '0', 'http://localhost:8080/userimg/timg.jpg', '0', '0', '232323', 'dgf', 'sf');
INSERT INTO `ad_studio` VALUES ('2', '61.33', '2019-10-17', 'qww', '0', '0', 'http://localhost:8080/userimg/timg.jpg', '0', '0', 'sdfs', 'sdfs', 'sdf');
INSERT INTO `ad_studio` VALUES ('3', '77.00', '2019-12-06', 'qw', '0', '0', 'http://localhost:8080/userimg/timg.jpg', '0', '0', 'sdf', 'sdfs', 'sdf');
INSERT INTO `ad_studio` VALUES ('4', '4564.00', '2015-09-05', 'kjkj', '1', '5', 'http://localhost:8080/userimg/timg.jpg', '6', '2', '55', '55', '1');
INSERT INTO `ad_studio` VALUES ('5', '44.00', '2019-09-04', '3rtw', '1', '1', 'http://localhost:8080/userimg/timg.jpg', '1', '7777', 'yy', 'yyy', 'yyy');
INSERT INTO `ad_studio` VALUES ('6', '33.00', '2019-09-28', '3rr3', '1', '1', 'http://localhost:8080/userimg/timg.jpg', '3', '33', '3443', '34', '34');
INSERT INTO `ad_studio` VALUES ('7', '44.00', '2019-09-13', 'etewt', '1', '1', 'http://localhost:8080/userimg/12.png', '5', '5', '235', '523', '54325');
INSERT INTO `ad_studio` VALUES ('8', '43.00', '2019-09-20', '234', '1', '1', 'http://localhost:8080/userimg/12.png', '2', '24324', '42', '34', '43');
INSERT INTO `ad_studio` VALUES ('9', '33.00', '2019-09-05', '234', '2', '2', 'http://localhost:8080/userimg/12.png', '2', '3', '34', '243', '234');
INSERT INTO `ad_studio` VALUES ('10', '34.00', '2019-09-20', '234', '2', '2', 'http://localhost:8080/userimg/12.png', '3', '34', '234', '243', '234');
INSERT INTO `ad_studio` VALUES ('11', '33.00', '2019-08-16', '343', '2', '2', 'http://localhost:8080/userimg/12.png', '2', '24', '43', '34', '34');
INSERT INTO `ad_studio` VALUES ('12', '33.00', '2019-08-31', '324', '4', '0', 'http://localhost:8080/userimg/12.png', '2', '3', '324', '234', '234');
INSERT INTO `ad_studio` VALUES ('13', '44.00', '2019-09-28', '34324', '1', '1', 'http://localhost:8080/userimg/12.png', '2', '2', '2', '2', '2');
INSERT INTO `ad_studio` VALUES ('14', '23.00', '2019-09-06', '32', '1', '1', 'http://localhost:8080/userimg/12.png', '1', '1', '1', '1', '1');
INSERT INTO `ad_studio` VALUES ('15', '11.00', '2019-09-12', '331', '1', '1', 'http://localhost:8080/userimg/12.png', '3', '1', '345', '1', '1');
INSERT INTO `ad_studio` VALUES ('16', '33.00', '2019-08-29', '234', '2', '2', 'http://localhost:8080/userimg/12.png', '2', '4', 'c', '4', '4');
INSERT INTO `ad_studio` VALUES ('17', '33.00', '2019-09-06', '34', '4', '1', 'http://localhost:8080/userimg/12.png', '1', '1', '1', '1', '1');
INSERT INTO `ad_studio` VALUES ('18', '33.00', '2019-09-14', '334', '2', '2', 'http://localhost:8080/userimg/12.png', '1', '1', '1', '1', '1');
INSERT INTO `ad_studio` VALUES ('19', '22.00', '2019-09-04', '13', '1', '1', 'http://localhost:8080/userimg/12.png', '2', '1', '1', '1', '1');
INSERT INTO `ad_studio` VALUES ('20', '2.00', '2019-09-06', 're', '2', '1', 'http://localhost:8080/userimg/12.png', '2', '1', '1', '11', '11');
INSERT INTO `ad_studio` VALUES ('21', '22.00', '2019-08-29', 'q', '1', '1', 'http://localhost:8080/userimg/12.png', '2', '1', '1', '1', '1');
INSERT INTO `ad_studio` VALUES ('22', '3.00', '2019-08-29', '33', '3', '3', 'http://localhost:8080/userimg/12.png', '2', '23', '22', '3', '4');

-- ----------------------------
-- Table structure for `file`
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `filename` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `user_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `file_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES ('1', '服务外包', 'http://localhost:8080/userimg/\" ', '1', '0');

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
-- Table structure for `project`
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `solr_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `prjname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tag` int(11) NOT NULL,
  `sub_tag` int(11) NOT NULL,
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `companyId` int(11) NOT NULL,
  `studioId` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `price` float NOT NULL,
  `releaseTime` date NOT NULL,
  `deadline` date NOT NULL,
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state` int(11) NOT NULL,
  `ifAd` int(11) NOT NULL,
  `ad_price` float DEFAULT NULL,
  `entity` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `studioId` (`studioId`),
  KEY `companyId` (`companyId`) USING BTREE,
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`companyId`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', 'rqwrnnn', '无人车应用', '1', '1', 'http://localhost:8080/prj_img/12.png', '2', '1', '1', '2019-08-30', '2019-08-30', '百度服务外包', '1', '1', '1', 'project');
INSERT INTO `project` VALUES ('2', 'fsnnn', '网页前端', '1', '0', 'http://localhost:8080/prj_img/12.png', '3', '1', '555555', '2019-08-24', '2019-08-31', '需要人数5', '1', '1', '54555', 'project');
INSERT INTO `project` VALUES ('3', 'ssdfs', '移动应用开发', '2', '1', 'http://localhost:8080/prj_img/12.png', '1', '1', '123132', '2019-08-31', '2019-09-01', '没有信息', '1', '0', '0', 'project');
INSERT INTO `project` VALUES ('4', 'fsdfs', '仿生测试', '5', '0', 'http://localhost:8080/prj_img/12.png', '2', '1', '22', '2019-08-31', '2020-01-24', '2人', '1', '1', '535353', 'project');
INSERT INTO `project` VALUES ('5', '683fd53a-07ec-4b8d-8562-e1e815dfc3c7', '0', '0', '0', 'http://localhost:8080/prj_img/12.png', '1', '1', '1', '2019-05-06', '2019-05-03', '0', '0', '0', null, 'project');

-- ----------------------------
-- Table structure for `schedule`
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `companyId` int(11) NOT NULL,
  `studioId` int(11) NOT NULL,
  `prjId` int(11) NOT NULL,
  `progress` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schedule
-- ----------------------------

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
  PRIMARY KEY (`id`),
  KEY `user_id` (`account_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'aaaas', '1', '天猫', '马云', '111', 'unknow', null, 'mayun@alibaba.com', '0', 'http://localhost:8080/userimg/tianmao.jpg', 'company');
INSERT INTO `user` VALUES ('2', 'ssss', '2', '雷电工作室', '李波', '222', 'java,h5,c++', null, 'leidian@163.com', '1', 'http://localhost:8080/userimg/12.png', 'studio');
INSERT INTO `user` VALUES ('3', 'qqqq', '3', '百度', '李彦宏', '333', '百度人工智能，无人车', null, 'lyh@baidu.com', '0', 'http://localhost:8080/userimg/12.png', 'company');
INSERT INTO `user` VALUES ('4', 'qqqqqq', '4', '传讯工作室', '张三', '444', 'c+++', null, 'zs@163.com', '1', 'http://localhost:8080/userimg/12.png', 'studio');
INSERT INTO `user` VALUES ('5', 'qqqqqqfddd', '2', '人工智能工作室', '李四', '555', 'python', null, 'ls@163.com', '0', 'http://localhost:8080/userimg/12.png', 'studio');
