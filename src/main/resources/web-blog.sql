/*
Navicat MySQL Data Transfer

Source Server         : 最新链接
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : web-blog

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-07-09 22:52:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_blog
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog`;
CREATE TABLE `tb_blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `commentabled` bit(1) DEFAULT NULL,
  `content` longtext,
  `create_time` datetime DEFAULT NULL,
  `first_picture` varchar(255) DEFAULT NULL,
  `flag` varchar(255) DEFAULT NULL,
  `published` bit(1) DEFAULT NULL,
  `recommend` bit(1) DEFAULT NULL,
  `share` bit(1) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `views` int(11) DEFAULT NULL,
  `type_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `appreciation` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK789c9u6dqf5vu8tgb0c8w9hem` (`type_id`),
  KEY `FKha24n1lxdp27joiq3aqsvuem6` (`user_id`),
  CONSTRAINT `FK789c9u6dqf5vu8tgb0c8w9hem` FOREIGN KEY (`type_id`) REFERENCES `tb_type` (`id`),
  CONSTRAINT `FKha24n1lxdp27joiq3aqsvuem6` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_blog_tags
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_tags`;
CREATE TABLE `tb_blog_tags` (
  `blogs_id` bigint(20) NOT NULL,
  `tags_id` bigint(20) NOT NULL,
  KEY `FKl133cqmpsfr98uq90a9cuuma7` (`tags_id`),
  KEY `FKqkynpmid0t1102koh5icrfeo2` (`blogs_id`),
  CONSTRAINT `FKl133cqmpsfr98uq90a9cuuma7` FOREIGN KEY (`tags_id`) REFERENCES `tb_tag` (`id`),
  CONSTRAINT `FKqkynpmid0t1102koh5icrfeo2` FOREIGN KEY (`blogs_id`) REFERENCES `tb_blog` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_comment
-- ----------------------------
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `blog_id` bigint(20) DEFAULT NULL,
  `parent_comment_id` bigint(20) DEFAULT NULL,
  `admin_comment` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6tc9ipjhoo0ugxqehmv2m6vse` (`blog_id`),
  KEY `FK6lk1d0spfxribt4tdt81hlpl6` (`parent_comment_id`),
  CONSTRAINT `FK6lk1d0spfxribt4tdt81hlpl6` FOREIGN KEY (`parent_comment_id`) REFERENCES `tb_comment` (`id`),
  CONSTRAINT `FK6tc9ipjhoo0ugxqehmv2m6vse` FOREIGN KEY (`blog_id`) REFERENCES `tb_blog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_tag`;
CREATE TABLE `tb_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_type`;
CREATE TABLE `tb_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for tb_user_copy
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_copy`;
CREATE TABLE `tb_user_copy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
