/*
Navicat MySQL Data Transfer

Source Server         : ccc
Source Server Version : 80012
Source Host           : localhost:3306
Source Database       : serviceplatform

Target Server Type    : MYSQL
Target Server Version : 80012
File Encoding         : 65001

Date: 2019-10-13 13:51:37
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
  `prjname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `tag` int(11) NOT NULL,
  `sub_tag` int(11) DEFAULT NULL,
  `img` varchar(255) NOT NULL,
  `price` varchar(255) NOT NULL,
  `ad_price` float NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ad_project
-- ----------------------------
INSERT INTO `ad_project` VALUES ('1', '45.60', '2019-10-09', '黑客已侵入你的网站12', '1', '2', 'http://localhost:8080/prjimg/timg (2).jpg', '￥555', '2');
INSERT INTO `ad_project` VALUES ('2', '61.33', '2019-10-17', '人工智能', '0', '0', 'http://localhost:8080/prjimg/ph1015-p08165.jpg', '￥9990', '0');
INSERT INTO `ad_project` VALUES ('3', '77.00', '2019-12-06', '手机软件模块开发', '0', '0', 'http://localhost:8080/prjimg/timgD58PK0GH.jpg', '￥8000', '0');
INSERT INTO `ad_project` VALUES ('4', '4564.00', '2015-09-05', 'python爬虫爬取网易云评论', '1', '1', 'http://localhost:8080/prjimg/timg71T5JU6W.jpg', '￥68777', '2');
INSERT INTO `ad_project` VALUES ('5', '44.00', '2019-09-04', '这是一个测试项目', '1', '1', 'http://localhost:8080/prjimg/timg1FAOACUI.jpg\r\n', '￥8888', '7777');
INSERT INTO `ad_project` VALUES ('6', '33.00', '2019-09-28', '小爱同学', '1', '1', 'http://localhost:8080/prjimg/timgM15YJ0LZ.jpg', '￥886', '33');
INSERT INTO `ad_project` VALUES ('7', '44.00', '2019-09-13', '微软小冰', '1', '1', 'http://localhost:8080/prjimg/timgTFJ3U4SZ.jpg', '￥5867', '5');
INSERT INTO `ad_project` VALUES ('8', '43.00', '2019-09-20', 'c++算法学习', '1', '1', 'http://localhost:8080/prjimg/timgZEC31A2U.jpg', '￥2878', '24324');
INSERT INTO `ad_project` VALUES ('9', '33.00', '2019-09-05', 'just for test', '2', '2', 'http://localhost:8080/prjimg/timgD58PK0GH.jpg', '￥868', '3');
INSERT INTO `ad_project` VALUES ('10', '34.00', '2019-09-20', 'web', '2', '2', 'http://localhost:8080/prjimg/timgcbh.jpg', '￥868', '34');
INSERT INTO `ad_project` VALUES ('11', '33.00', '2019-08-16', '网页制作', '2', '2', 'http://localhost:8080/prjimg/timgKYSAJEN1.jpg', '￥678', '24');
INSERT INTO `ad_project` VALUES ('12', '33.00', '2019-08-31', 'hacker', '4', '0', 'http://localhost:8080/prjimg/timgS2ZAWAFT.jpg', '￥2678', '3');
INSERT INTO `ad_project` VALUES ('13', '44.00', '2019-09-28', 'hey siri', '1', '1', 'http://localhost:8080/prjimg/d8ef5g.jpg', '￥268', '2');
INSERT INTO `ad_project` VALUES ('14', '23.00', '2019-09-06', '图片打包下载', '1', '1', 'http://localhost:8080/prjimg/u=1448476059,3511315932&fm=26&gp=0.jpg', '￥5867', '1');
INSERT INTO `ad_project` VALUES ('15', '11.00', '2019-09-12', '扑街', '1', '1', 'http://localhost:8080/prjimg/csgo.jpg', '￥3678', '1');
INSERT INTO `ad_project` VALUES ('16', '33.00', '2019-08-29', 'qq破解软件', '2', '2', 'http://localhost:8080/prjimg/timg.jpg', '￥25555', '4');
INSERT INTO `ad_project` VALUES ('17', '33.00', '2019-09-06', 'bilibil视频下载', '4', '1', 'http://localhost:8080/prjimg/timgKMO118B8.jpg', '￥18888', '1');
INSERT INTO `ad_project` VALUES ('18', '33.00', '2019-09-14', 'vpn', '2', '2', 'http://localhost:8080/prjimg/timg5ZHLL4H5.jpg', '￥1877', '1');
INSERT INTO `ad_project` VALUES ('19', '22.00', '2019-09-04', '算法入门', '1', '1', 'http://localhost:8080/prjimg/u.jpg', '￥20000', '1');
INSERT INTO `ad_project` VALUES ('20', '2.00', '2019-09-06', 'it is a test to', '2', '1', 'http://localhost:8080/prjimg/timgX0F1RMXM.jpg', '￥200', '1');
INSERT INTO `ad_project` VALUES ('21', '22.00', '2019-08-29', 'c sharp', '1', '1', 'http://localhost:8080/prjimg/timgEVR6NOGQ.jpg', '￥2', '1');
INSERT INTO `ad_project` VALUES ('22', '3.00', '2019-08-29', 'unity', '3', '2', 'http://localhost:8080/prjimg/unity.jpg', '￥2', '23');
INSERT INTO `ad_project` VALUES ('23', '233.00', '2019-10-09', '智能车嵌入式系统', '1', '0', 'http://localhost:8080/prjimg/a603c865-d05e-4a92-9115-eba8d769600a.jpg', '￥1100', '45');
INSERT INTO `ad_project` VALUES ('24', '31.20', '2019-10-09', '黑客已侵入你的网站1', '1', '2', 'http://localhost:8080/prjimg/timg(1).jpg', '￥555', '2');
INSERT INTO `ad_project` VALUES ('25', '45.60', '2019-10-09', '黑客已侵入你的网站10', '1', '2', 'http://localhost:8080/prjimg/timg (11).jpg', '￥555', '2');
INSERT INTO `ad_project` VALUES ('26', '23.00', '2019-10-03', '黑客已侵入你的网站2', '1', '1', 'http://localhost:8080/prjimg/timg (2).jpg', '￥2222', '1');
INSERT INTO `ad_project` VALUES ('27', '222.00', '2019-10-04', '黑客已侵入你的网站3', '1', '1', 'http://localhost:8080/prjimg/timg (3).jpg', '￥222', '2');
INSERT INTO `ad_project` VALUES ('28', '22.00', '2019-10-11', '黑客已侵入你的网站4', '4', '0', 'http://localhost:8080/prjimg/timg (4).jpg', '￥66', '1');
INSERT INTO `ad_project` VALUES ('29', '55.00', '2019-10-09', '黑客已侵入你的网站4', '1', '2', 'http://localhost:8080/prjimg/timg (5).jpg', '￥5556', '2');
INSERT INTO `ad_project` VALUES ('30', '88.00', '2019-10-09', '黑客已侵入你的网站5', '1', '2', 'http://localhost:8080/prjimg/timg (6).jpg', '￥587', '22');
INSERT INTO `ad_project` VALUES ('31', '5.60', '2019-10-09', '黑客已侵入你的网站6', '1', '2', 'http://localhost:8080/prjimg/timg (7).jpg', '￥555', '2');
INSERT INTO `ad_project` VALUES ('32', '42.00', '2019-10-09', '黑客已侵入你的网站7', '1', '2', 'http://localhost:8080/prjimg/timg (8).jpg', '￥55', '2');
INSERT INTO `ad_project` VALUES ('33', '45.60', '2019-10-09', '黑客已侵入你的网站8', '1', '2', 'http://localhost:8080/prjimg/timg (9).jpg', '￥555', '2');
INSERT INTO `ad_project` VALUES ('34', '11.00', '2019-10-09', '黑客已侵入你的网站9', '1', '2', 'http://localhost:8080/prjimg/timg (10).jpg', '￥555', '2');
INSERT INTO `ad_project` VALUES ('35', '60.00', '2019-10-09', '黑客已侵入你的网站10', '1', '2', 'http://localhost:8080/prjimg/timg (11).jpg', '￥555', '2');
INSERT INTO `ad_project` VALUES ('36', '45.00', '2019-10-09', '黑客已侵入你的网站11', '1', '2', 'http://localhost:8080/prjimg/timg (5).jpg', '￥555', '2');
INSERT INTO `ad_project` VALUES ('37', '10.00', '2019-10-09', '黑客已侵入你的网站13', '1', '2', 'http://localhost:8080/prjimg/timg (11).jpg', '￥555', '2');
INSERT INTO `ad_project` VALUES ('38', '45.60', '2019-10-09', '黑客已侵入你的网站10', '1', '2', 'http://localhost:8080/prjimg/timg (11).jpg', '￥555', '2');
INSERT INTO `ad_project` VALUES ('39', '4.20', '2019-10-09', '黑客已侵入你的网站10', '1', '2', 'http://localhost:8080/prjimg/timg (11).jpg', '￥555', '2');
INSERT INTO `ad_project` VALUES ('40', '33.00', '2019-10-09', '黑客已侵入你的网站10', '1', '2', 'http://localhost:8080/prjimg/timg (11).jpg', '￥555', '2');
INSERT INTO `ad_project` VALUES ('41', '89.00', '2019-10-09', '黑客已侵入你的网站10', '1', '2', 'http://localhost:8080/prjimg/timg (11).jpg', '￥555', '2');
INSERT INTO `ad_project` VALUES ('42', '4.00', '2019-10-09', '黑客已侵入你的网站10', '1', '2', 'http://localhost:8080/prjimg/timg (11).jpg', '￥555', '2');
INSERT INTO `ad_project` VALUES ('43', '45.00', '2019-10-09', '黑客已侵入你的网站10', '1', '2', 'http://localhost:8080/prjimg/timg (11).jpg', '￥555', '2');

-- ----------------------------
-- Table structure for `bid`
-- ----------------------------
DROP TABLE IF EXISTS `bid`;
CREATE TABLE `bid` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL,
  `studio_id` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bid
-- ----------------------------

-- ----------------------------
-- Table structure for `file_project`
-- ----------------------------
DROP TABLE IF EXISTS `file_project`;
CREATE TABLE `file_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL,
  `prj_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `prj_id` (`prj_id`),
  CONSTRAINT `file_project_ibfk_1` FOREIGN KEY (`prj_id`) REFERENCES `project` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file_project
-- ----------------------------
INSERT INTO `file_project` VALUES ('1', 'http://localhost:8080/upload_file/todolist.txt', '1');

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
  `price` float NOT NULL,
  `releaseTime` date NOT NULL,
  `deadline` date NOT NULL,
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state` int(11) NOT NULL,
  `ifAd` int(11) NOT NULL,
  `ad_price` float DEFAULT NULL,
  `entity` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `companyId` (`companyId`) USING BTREE,
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`companyId`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', 'rqwrnnn', '无人车应用', '1', '1', 'http://localhost:8080/prj_img/12.png', '2', '1', '2019-08-30', '2019-08-30', '百度服务外包', '1', '1', '1', 'project');
INSERT INTO `project` VALUES ('2', 'fsnnn', '网页前端', '1', '0', 'http://localhost:8080/prj_img/12.png', '3', '555555', '2019-08-24', '2019-08-31', '需要人数5', '1', '1', '54555', 'project');
INSERT INTO `project` VALUES ('3', 'ssdfs', '移动应用开发', '2', '1', 'http://localhost:8080/prj_img/12.png', '1', '123132', '2019-08-31', '2019-09-01', '没有信息', '1', '0', '0', 'project');
INSERT INTO `project` VALUES ('4', 'fsdfs', '仿生测试', '5', '0', 'http://localhost:8080/prj_img/12.png', '2', '22', '2019-08-31', '2020-01-24', '2人', '1', '1', '535353', 'project');
INSERT INTO `project` VALUES ('5', '683fd53a-07ec-4b8d-8562-e1e815dfc3c7', '0', '0', '0', 'http://localhost:8080/prj_img/12.png', '1', '1', '2019-05-06', '2019-05-03', '0', '0', '0', null, 'project');
INSERT INTO `project` VALUES ('6', '29152106-5185-4a80-b500-e9b83add26f1', '逆袭', '1', '1', 'http://localhost:8080/prjimg/29152106-5185-4a80-b500-e9b83add26f1.jpg', '1', '1', '2019-05-06', '2019-05-03', '0', '0', '0', null, 'project');

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
