/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50728
Source Host           : localhost:3306
Source Database       : lcjy

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2020-03-08 17:29:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_bank_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_bank_info`;
CREATE TABLE `tb_bank_info` (
  `bank_id` varchar(32) NOT NULL,
  `bank_no` varchar(20) DEFAULT NULL,
  `order_no` int(3) DEFAULT NULL,
  `area_type` varchar(20) DEFAULT NULL,
  `bank_name` varchar(100) DEFAULT NULL,
  `train_type` varchar(10) DEFAULT NULL,
  `exercise_type` varchar(10) DEFAULT NULL,
  `exercise_num` int(11) DEFAULT NULL,
  `is_time` varchar(1) DEFAULT NULL,
  `time` int(11) DEFAULT NULL,
  `mark_type` varchar(10) DEFAULT NULL,
  `is_enable` varchar(1) DEFAULT NULL,
  `create_user` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`bank_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_class_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_class_info`;
CREATE TABLE `tb_class_info` (
  `class_id` varchar(32) NOT NULL,
  `class_name` varchar(100) DEFAULT NULL,
  `area_type` varchar(20) DEFAULT NULL,
  `create_user` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_dictionary_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_dictionary_info`;
CREATE TABLE `tb_dictionary_info` (
  `dictionary_id` varchar(32) NOT NULL,
  `type_name` varchar(100) DEFAULT NULL,
  `type_code` varchar(50) DEFAULT NULL,
  `dictionary_name` varchar(100) DEFAULT NULL,
  `dictionary_code` varchar(50) DEFAULT NULL,
  `remarks` varchar(1000) DEFAULT NULL,
  `order_no` int(3) DEFAULT NULL,
  `create_user` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`dictionary_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_error_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_error_info`;
CREATE TABLE `tb_error_info` (
  `error_id` varchar(32) NOT NULL,
  `student_id` varchar(32) DEFAULT NULL,
  `student_name` varchar(100) DEFAULT NULL,
  `train_type` varchar(10) DEFAULT NULL,
  `exercise_type` varchar(10) DEFAULT NULL,
  `bank_id` varchar(32) DEFAULT NULL,
  `bank_name` varchar(100) DEFAULT NULL,
  `excer_id` varchar(32) DEFAULT NULL,
  `order_no` int(11) DEFAULT NULL,
  `selection_answer` varchar(1) DEFAULT NULL,
  `is_remove` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`error_id`),
  KEY `idx_error_student_id` (`student_id`) USING HASH,
  KEY `idx_error_excer_id` (`excer_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_exercise_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_exercise_info`;
CREATE TABLE `tb_exercise_info` (
  `excer_id` varchar(100) NOT NULL,
  `bank_id` varchar(100) DEFAULT NULL,
  `order_no` int(3) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `selection_a` varchar(100) DEFAULT NULL,
  `selection_b` varchar(100) DEFAULT NULL,
  `selection_c` varchar(100) DEFAULT NULL,
  `selection_d` varchar(100) DEFAULT NULL,
  `selection_correct` varchar(20) DEFAULT NULL,
  `answer` varchar(1000) DEFAULT NULL,
  `own_domain` varchar(10) DEFAULT NULL,
  `own_process` varchar(10) DEFAULT NULL,
  `create_user` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`excer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource`;
CREATE TABLE `tb_resource` (
  `resource_id` varchar(32) NOT NULL,
  `resource_name` varchar(100) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `path` varchar(100) DEFAULT NULL,
  `icon` varchar(20) DEFAULT NULL,
  `parent_node` varchar(32) DEFAULT NULL,
  `create_user` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_role_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_info`;
CREATE TABLE `tb_role_info` (
  `role_id` varchar(32) NOT NULL,
  `role_code` varchar(100) DEFAULT NULL,
  `role_name` varchar(100) DEFAULT NULL,
  `role_remarks` varchar(1000) DEFAULT NULL,
  `create_user` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_resource`;
CREATE TABLE `tb_role_resource` (
  `id` varchar(32) NOT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `resource_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_role_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_user_info`;
CREATE TABLE `tb_role_user_info` (
  `role_user_id` varchar(32) NOT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `create_user` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_score_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_score_info`;
CREATE TABLE `tb_score_info` (
  `score_id` varchar(32) NOT NULL,
  `class_id` varchar(32) DEFAULT NULL,
  `class_name` varchar(100) DEFAULT NULL,
  `student_id` varchar(32) DEFAULT NULL,
  `student_name` varchar(100) DEFAULT NULL,
  `train_type` varchar(10) DEFAULT NULL,
  `exercise_type` varchar(10) DEFAULT NULL,
  `bank_id` varchar(100) DEFAULT NULL,
  `bank_name` varchar(100) DEFAULT NULL,
  `right_exercise_num` int(11) DEFAULT NULL,
  `exercise_num` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `score_time` datetime DEFAULT NULL,
  PRIMARY KEY (`score_id`),
  KEY `idx_score_student_id` (`student_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_student_answer_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_student_answer_info`;
CREATE TABLE `tb_student_answer_info` (
  `student_answer_id` varchar(32) NOT NULL,
  `area_type` varchar(20) DEFAULT NULL,
  `student_id` varchar(32) DEFAULT NULL,
  `student_name` varchar(100) DEFAULT NULL,
  `train_type` varchar(10) DEFAULT NULL,
  `exercise_type` varchar(10) DEFAULT NULL,
  `bank_id` varchar(32) DEFAULT NULL,
  `bank_name` varchar(100) DEFAULT NULL,
  `excer_id` varchar(32) DEFAULT NULL,
  `order_no` int(11) DEFAULT NULL,
  `selection_correct` varchar(1) DEFAULT NULL,
  `selection_answer` varchar(1) DEFAULT NULL,
  `is_right` varchar(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `is_error_book` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`student_answer_id`),
  KEY `idx_answer_student_id` (`student_id`) USING HASH,
  KEY `idx_answer_bank_id` (`bank_id`) USING HASH,
  KEY `idx_answer_excer_id` (`excer_id`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_student_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_student_info`;
CREATE TABLE `tb_student_info` (
  `student_id` varchar(32) NOT NULL,
  `class_id` varchar(32) DEFAULT NULL,
  `area_type` varchar(20) DEFAULT NULL,
  `company` varchar(100) DEFAULT NULL,
  `student_name` varchar(100) DEFAULT NULL,
  `sex` char(2) DEFAULT NULL,
  `phone_no` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `wechat` varchar(50) DEFAULT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `is_authorize` char(2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user` varchar(20) DEFAULT NULL,
  `openid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_user_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info` (
  `user_id` varchar(32) NOT NULL,
  `user_name` varchar(100) DEFAULT NULL,
  `sex` char(2) DEFAULT NULL,
  `phone_no` varchar(20) DEFAULT NULL,
  `account` varchar(10) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `is_enable` varchar(1) DEFAULT NULL,
  `expiry_date` datetime DEFAULT NULL,
  `create_user` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
