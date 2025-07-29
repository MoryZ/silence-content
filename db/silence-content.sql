/*
 Navicat Premium Dump SQL

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80200 (8.2.0)
 Source Host           : localhost:3306
 Source Schema         : silence-content

 Target Server Type    : MySQL
 Target Server Version : 80200 (8.2.0)
 File Encoding         : 65001

 Date: 29/07/2025 20:26:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book_content_tag
-- ----------------------------
DROP TABLE IF EXISTS `book_content_tag`;
CREATE TABLE `book_content_tag` (
  `book_id` bigint unsigned NOT NULL COMMENT '图书ID',
  `tag_id` bigint unsigned NOT NULL COMMENT '标签ID',
  `created_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  `updated_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`book_id`,`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='图书标签关联表';

-- ----------------------------
-- Records of book_content_tag
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for content
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
  `title` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `type` tinyint unsigned NOT NULL,
  `status` tinyint unsigned NOT NULL,
  `published_at` datetime DEFAULT NULL,
  `author` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `content_code` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `cover_image_reference` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cover_image_reference_mode` tinyint unsigned NOT NULL,
  `content_reference_mode` tinyint unsigned NOT NULL,
  `content_reference` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `audit_code` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `task_code` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `tenant_id` bigint NOT NULL,
  `parent_id` bigint NOT NULL,
  `root_id` bigint NOT NULL,
  `is_sticky_top` bit(1) DEFAULT NULL,
  `sticky_top_at` datetime DEFAULT NULL,
  `sticky_top_expired_at` datetime DEFAULT NULL,
  `is_disclosure` bit(1) DEFAULT NULL,
  `is_leaf` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Records of content
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for content_article
-- ----------------------------
DROP TABLE IF EXISTS `content_article`;
CREATE TABLE `content_article` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `summary` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `reprint_declaration` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `created_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='内容文章表';

-- ----------------------------
-- Records of content_article
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for content_book
-- ----------------------------
DROP TABLE IF EXISTS `content_book`;
CREATE TABLE `content_book` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `type` tinyint NOT NULL COMMENT '类型',
  `cover_image_reference_mode` tinyint NOT NULL COMMENT '封面图引用方式',
  `cover_image_reference` varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '图书封面引用方式',
  `content_reference_mode` tinyint NOT NULL COMMENT '图书正文引用方式',
  `content_reference` varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '图书正文',
  `isbn` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '地址',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `press` varchar(256) COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `created_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  `updated_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='图书表';

-- ----------------------------
-- Records of content_book
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for content_content_tag
-- ----------------------------
DROP TABLE IF EXISTS `content_content_tag`;
CREATE TABLE `content_content_tag` (
  `content_id` bigint unsigned NOT NULL COMMENT '内容ID',
  `tag_id` bigint unsigned NOT NULL COMMENT '标签ID',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`content_id`,`tag_id`),
  KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='内容标签关联表';

-- ----------------------------
-- Records of content_content_tag
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for content_live
-- ----------------------------
DROP TABLE IF EXISTS `content_live`;
CREATE TABLE `content_live` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `room_id` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '直播间id',
  `started_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `finish_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `active_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `live_status` tinyint unsigned NOT NULL,
  `created_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='内容直播表';

-- ----------------------------
-- Records of content_live
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for content_product_term
-- ----------------------------
DROP TABLE IF EXISTS `content_product_term`;
CREATE TABLE `content_product_term` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `product_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品编码',
  `on_sale_at` datetime DEFAULT NULL,
  `off_sale_at` datetime DEFAULT NULL,
  `display_order` int unsigned NOT NULL,
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='内容产品条款表';

-- ----------------------------
-- Records of content_product_term
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for content_tag
-- ----------------------------
DROP TABLE IF EXISTS `content_tag`;
CREATE TABLE `content_tag` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `type` tinyint unsigned NOT NULL COMMENT '标签类型',
  `parent_id` bigint unsigned NOT NULL COMMENT '标签父id',
  `name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签名称',
  `code` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签编码',
  `is_enabled` bit(1) NOT NULL COMMENT '是否可用',
  `sort` int unsigned NOT NULL COMMENT '排序',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '我是一个小标签',
  `created_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  `updated_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_content_tag_type_code` (`type`,`code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=388 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='标签表';

-- ----------------------------
-- Records of content_tag
-- ----------------------------
BEGIN;
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (1, 1, 0, '根茎类', 'root_rhizome', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (2, 1, 0, '果实类', 'root_fruit', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (3, 1, 0, '叶类', 'root_leaf', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (4, 1, 0, '花类', 'root_flower', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (5, 1, 0, '皮类', 'root_bark', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (6, 1, 0, '全草类', 'root_herb', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (7, 1, 0, '动物类', 'root_animal', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (8, 1, 0, '矿物类', 'root_mineral', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (9, 1, 1, '人参', 'ginseng', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (10, 1, 9, '红参', 'red_ginseng', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (11, 1, 9, '白参', 'white_ginseng', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (12, 1, 9, '高丽参', 'korean_ginseng', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (13, 1, 1, '黄芪', 'astragalus', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (14, 1, 13, '蒙古黄芪', 'mongolian_astragalus', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (15, 1, 13, '膜荚黄芪', 'membrane_astragalus', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (16, 1, 1, '当归', 'angelica', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (17, 1, 16, '全当归', 'whole_angelica', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (18, 1, 16, '当归头', 'angelica_head', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (19, 1, 16, '当归尾', 'angelica_tail', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (20, 1, 1, '白术', 'atractylodes', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (21, 1, 20, '生白术', 'raw_atractylodes', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (22, 1, 20, '炒白术', 'fried_atractylodes', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (23, 1, 1, '白芍', 'white_peony', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (24, 1, 23, '杭白芍', 'hangzhou_peony', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (25, 1, 23, '亳白芍', 'bozhou_peony', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (26, 1, 1, '甘草', 'licorice', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (27, 1, 26, '炙甘草', 'honey_licorice', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (28, 1, 26, '生甘草', 'raw_licorice', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (29, 1, 1, '川芎', 'chuanxiong', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (30, 1, 1, '地黄', 'rehmannia', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (31, 1, 30, '生地黄', 'raw_rehmannia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (32, 1, 30, '熟地黄', 'cooked_rehmannia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (33, 1, 1, '三七', 'pseudo_ginseng', b'1', 9, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (34, 1, 33, '田七', 'tianqi', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (35, 1, 1, '天麻', 'gastrodia', b'1', 10, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (36, 1, 35, '红天麻', 'red_gastrodia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (37, 1, 35, '乌天麻', 'black_gastrodia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (38, 1, 1, '麦冬', 'ophiopogon', b'1', 11, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (39, 1, 38, '川麦冬', 'sichuan_ophiopogon', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (40, 1, 38, '杭麦冬', 'hangzhou_ophiopogon', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (41, 1, 1, '黄连', 'coptis', b'1', 12, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (42, 1, 41, '味连', 'weilian', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (43, 1, 41, '雅连', 'yalian', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (44, 1, 41, '云连', 'yunlian', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (45, 1, 1, '贝母', 'fritillaria', b'1', 13, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (46, 1, 45, '川贝母', 'sichuan_fritillaria', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (47, 1, 45, '浙贝母', 'zhejiang_fritillaria', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (48, 1, 45, '平贝母', 'pingbei_fritillaria', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (49, 1, 1, '党参', 'codonopsis', b'1', 14, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (50, 1, 49, '潞党参', 'lu_dangshen', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (51, 1, 49, '纹党参', 'wen_dangshen', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (52, 1, 49, '板桥党参', 'banqiao_dangshen', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (53, 1, 1, '丹参', 'salvia', b'1', 15, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (54, 1, 53, '川丹参', 'sichuan_salvia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (55, 1, 53, '山东丹参', 'shandong_salvia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (56, 1, 1, '山药', 'dioscorea', b'1', 16, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (57, 1, 56, '怀山药', 'huai_shanyao', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (58, 1, 56, '广山药', 'guang_shanyao', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (59, 1, 1, '茯苓', 'poria', b'1', 17, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (60, 1, 59, '白茯苓', 'white_poria', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (61, 1, 59, '赤茯苓', 'red_poria', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (62, 1, 59, '茯神', 'poria_spirit', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (63, 1, 1, '泽泻', 'alisma', b'1', 18, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (64, 1, 63, '建泽泻', 'jian_zexie', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (65, 1, 63, '川泽泻', 'sichuan_zexie', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (66, 1, 1, '半夏', 'pinellia', b'1', 19, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (67, 1, 66, '法半夏', 'fa_banxia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (68, 1, 66, '姜半夏', 'jiang_banxia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (69, 1, 66, '清半夏', 'qing_banxia', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (70, 1, 1, '天南星', 'arisema', b'1', 20, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (71, 1, 70, '胆南星', 'dan_nanxing', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (72, 1, 1, '苍术', 'atractylodes_lancea', b'1', 21, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (73, 1, 72, '茅苍术', 'mao_cangzhu', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (74, 1, 72, '北苍术', 'bei_cangzhu', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (75, 1, 1, '黄精', 'polygonatum', b'1', 22, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (76, 1, 75, '鸡头黄精', 'jihou_huangjing', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (77, 1, 75, '姜形黄精', 'jiang_huangjing', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (78, 1, 1, '玉竹', 'polygonatum_odoratum', b'1', 23, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (79, 1, 78, '湘玉竹', 'xiang_yuzhu', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (80, 1, 78, '关玉竹', 'guan_yuzhu', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (81, 1, 1, '知母', 'anemarrhena', b'1', 24, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (82, 1, 81, '毛知母', 'mao_zhimu', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (83, 1, 81, '光知母', 'guang_zhimu', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (84, 1, 1, '葛根', 'pueraria', b'1', 25, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (85, 1, 84, '粉葛', 'fen_ge', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (86, 1, 84, '野葛', 'ye_ge', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (87, 1, 1, '柴胡', 'bupleurum', b'1', 26, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (88, 1, 87, '北柴胡', 'bei_chaihu', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (89, 1, 87, '南柴胡', 'nan_chaihu', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (90, 1, 1, '升麻', 'cimicifuga', b'1', 27, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (91, 1, 90, '关升麻', 'guan_shengma', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (92, 1, 90, '北升麻', 'bei_shengma', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (93, 1, 1, '防风', 'saposhnikovia', b'1', 28, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (94, 1, 93, '关防风', 'guan_fangfeng', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (95, 1, 93, '口防风', 'kou_fangfeng', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (96, 1, 1, '独活', 'angelica_pubescens', b'1', 29, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (97, 1, 96, '川独活', 'sichuan_duhuo', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (98, 1, 96, '香独活', 'xiang_duhuo', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (99, 1, 1, '羌活', 'notopterygium', b'1', 30, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (100, 1, 99, '蚕羌', 'can_qiang', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (101, 1, 99, '竹节羌', 'zhujie_qiang', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (102, 1, 2, '枸杞', 'lycium', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (103, 1, 102, '宁夏枸杞', 'ningxia_lycium', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (104, 1, 102, '中宁枸杞', 'zhongning_lycium', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (105, 1, 102, '黑枸杞', 'black_lycium', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (106, 1, 2, '五味子', 'schisandra', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (107, 1, 106, '北五味子', 'bei_schisandra', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (108, 1, 106, '南五味子', 'nan_schisandra', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (109, 1, 2, '山楂', 'hawthorn', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (110, 1, 109, '北山楂', 'bei_hawthorn', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (111, 1, 109, '南山楂', 'nan_hawthorn', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (112, 1, 2, '女贞子', 'ligustrum', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (113, 1, 112, '大叶女贞', 'big_ligustrum', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (114, 1, 112, '小叶女贞', 'small_ligustrum', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (115, 1, 2, '栀子', 'gardenia', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (116, 1, 115, '水栀子', 'water_gardenia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (117, 1, 115, '山栀子', 'mountain_gardenia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (118, 1, 2, '砂仁', 'amomum', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (119, 1, 118, '阳春砂', 'yangchun_amomum', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (120, 1, 118, '海南砂', 'hainan_amomum', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (121, 1, 2, '豆蔻', 'cardamom', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (122, 1, 121, '白豆蔻', 'white_cardamom', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (123, 1, 121, '草豆蔻', 'grass_cardamom', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (124, 1, 2, '益智仁', 'alpinia', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (125, 1, 124, '海南益智', 'hainan_alpinia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (126, 1, 124, '广东益智', 'guangdong_alpinia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (127, 1, 2, '草果', 'tsaoko', b'1', 9, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (128, 1, 127, '云南草果', 'yunnan_tsaoko', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (129, 1, 127, '广西草果', 'guangxi_tsaoko', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (130, 1, 2, '佛手', 'bergamot', b'1', 10, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (131, 1, 130, '广佛手', 'guang_bergamot', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (132, 1, 130, '川佛手', 'sichuan_bergamot', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (133, 1, 2, '香橼', 'citron', b'1', 11, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (134, 1, 133, '枸橼', 'juyuan', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (135, 1, 133, '香圆', 'xiangyuan', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (136, 1, 2, '木瓜', 'chaenomeles', b'1', 12, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (137, 1, 136, '宣木瓜', 'xuan_mugua', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (138, 1, 136, '川木瓜', 'sichuan_mugua', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (139, 1, 2, '乌梅', 'smoked_plum', b'1', 13, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (140, 1, 139, '福建乌梅', 'fujian_wumei', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (141, 1, 139, '四川乌梅', 'sichuan_wumei', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (142, 1, 2, '金樱子', 'rosa_laevigata', b'1', 14, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (143, 1, 142, '江西金樱子', 'jiangxi_jinyingzi', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (144, 1, 142, '湖南金樱子', 'hunan_jinyingzi', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (145, 1, 2, '覆盆子', 'rubus', b'1', 15, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (146, 1, 145, '华东覆盆子', 'huadong_fupenzi', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (147, 1, 145, '华中覆盆子', 'huazhong_fupenzi', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (148, 1, 2, '桑椹', 'mulberry', b'1', 16, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (149, 1, 148, '黑桑椹', 'black_mulberry', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (150, 1, 148, '白桑椹', 'white_mulberry', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (151, 1, 2, '使君子', 'quisqualis', b'1', 17, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (152, 1, 151, '四川使君子', 'sichuan_quisqualis', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (153, 1, 151, '广东使君子', 'guangdong_quisqualis', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (154, 1, 2, '诃子', 'chebula', b'1', 18, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (155, 1, 154, '云南诃子', 'yunnan_chebula', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (156, 1, 154, '广西诃子', 'guangxi_chebula', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (157, 1, 2, '川楝子', 'toosendan', b'1', 19, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (158, 1, 157, '四川川楝子', 'sichuan_toosendan', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (159, 1, 157, '云南川楝子', 'yunnan_toosendan', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (160, 1, 2, '吴茱萸', 'evodia', b'1', 20, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (161, 1, 160, '中花吴茱萸', 'zhonghua_evodia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (162, 1, 160, '小花吴茱萸', 'small_evodia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (163, 1, 2, '山茱萸', 'cornus', b'1', 21, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (164, 1, 163, '浙江山茱萸', 'zhejiang_cornus', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (165, 1, 163, '河南山茱萸', 'henan_cornus', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (166, 1, 2, '连翘', 'forsythia', b'1', 22, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (167, 1, 166, '青翘', 'qing_qiao', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (168, 1, 166, '老翘', 'lao_qiao', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (169, 1, 2, '枸杞子', 'lycium_fruit', b'1', 23, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (170, 1, 169, '宁夏枸杞子', 'ningxia_lycium_fruit', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (171, 1, 169, '新疆枸杞子', 'xinjiang_lycium_fruit', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (172, 1, 2, '瓜蒌', 'trichosanthes', b'1', 24, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (173, 1, 172, '全瓜蒌', 'whole_trichosanthes', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (174, 1, 172, '瓜蒌皮', 'trichosanthes_peel', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (175, 1, 172, '瓜蒌子', 'trichosanthes_seed', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (176, 1, 2, '薏苡仁', 'coix', b'1', 25, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (177, 1, 176, '贵州薏苡仁', 'guizhou_coix', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (178, 1, 176, '广西薏苡仁', 'guangxi_coix', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (179, 1, 3, '桑叶', 'mulberry_leaf', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (180, 1, 179, '霜桑叶', 'frost_mulberry_leaf', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (181, 1, 179, '嫩桑叶', 'tender_mulberry_leaf', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (182, 1, 3, '枇杷叶', 'loquat_leaf', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (183, 1, 182, '老枇杷叶', 'old_loquat_leaf', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (184, 1, 182, '嫩枇杷叶', 'tender_loquat_leaf', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (185, 1, 3, '荷叶', 'lotus_leaf', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (186, 1, 185, '鲜荷叶', 'fresh_lotus_leaf', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (187, 1, 185, '干荷叶', 'dry_lotus_leaf', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (188, 1, 3, '紫苏叶', 'perilla_leaf', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (189, 1, 188, '紫苏嫩叶', 'tender_perilla', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (190, 1, 188, '紫苏老叶', 'old_perilla', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (191, 1, 3, '艾叶', 'artemisia_leaf', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (192, 1, 191, '蕲艾', 'qi_ai', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (193, 1, 191, '普通艾叶', 'common_ai', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (194, 1, 3, '淡竹叶', 'lophatherum', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (195, 1, 194, '江西淡竹叶', 'jiangxi_lophatherum', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (196, 1, 194, '浙江淡竹叶', 'zhejiang_lophatherum', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (197, 1, 3, '侧柏叶', 'platycladus_leaf', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (198, 1, 197, '生侧柏叶', 'raw_platycladus', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (199, 1, 197, '侧柏炭', 'carbonized_platycladus', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (200, 1, 3, '大青叶', 'isatis_leaf', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (201, 1, 200, '菘蓝叶', 'songlan_leaf', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (202, 1, 200, '马蓝叶', 'malan_leaf', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (203, 1, 3, '番泻叶', 'senna_leaf', b'1', 9, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (204, 1, 203, '印度番泻叶', 'india_senna', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (205, 1, 203, '埃及番泻叶', 'egypt_senna', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (206, 1, 3, '银杏叶', 'ginkgo_leaf', b'1', 10, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (207, 1, 206, '鲜银杏叶', 'fresh_ginkgo', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (208, 1, 206, '干银杏叶', 'dry_ginkgo', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (209, 1, 4, '金银花', 'honeysuckle', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (210, 1, 209, '密银花', 'mi_honeysuckle', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (211, 1, 209, '济银花', 'ji_honeysuckle', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (212, 1, 4, '菊花', 'chrysanthemum', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (213, 1, 212, '杭菊', 'hang_ju', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (214, 1, 212, '亳菊', 'bo_ju', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (215, 1, 212, '滁菊', 'chu_ju', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (216, 1, 4, '红花', 'carthamus', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (217, 1, 216, '新疆红花', 'xinjiang_carthamus', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (218, 1, 216, '云南红花', 'yunnan_carthamus', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (219, 1, 4, '玫瑰花', 'rose', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (220, 1, 219, '苦水玫瑰', 'kushui_rose', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (221, 1, 219, '平阴玫瑰', 'pingyin_rose', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (222, 1, 4, '月季花', 'china_rose', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (223, 1, 222, '江苏月季', 'jiangsu_chinarose', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (224, 1, 222, '山东月季', 'shandong_chinarose', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (225, 1, 4, '槐花', 'sophora_flower', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (226, 1, 225, '生槐花', 'raw_sophora', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (227, 1, 225, '槐花炭', 'carbonized_sophora', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (228, 1, 4, '款冬花', 'tussilago', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (229, 1, 228, '甘肃款冬', 'gansu_tussilago', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (230, 1, 228, '陕西款冬', 'shanxi_tussilago', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (231, 1, 4, '辛夷', 'magnolia_flower', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (232, 1, 231, '望春花', 'wangchun_flower', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (233, 1, 231, '武当玉兰', 'wudang_magnolia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (234, 1, 4, '合欢花', 'albizia_flower', b'1', 9, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (235, 1, 234, '安徽合欢', 'anhui_albizia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (236, 1, 234, '江苏合欢', 'jiangsu_albizia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (237, 1, 4, '野菊花', 'wild_chrysanthemum', b'1', 10, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (238, 1, 237, '黄山野菊', 'huangshan_wildju', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (239, 1, 237, '天目野菊', 'tianmu_wildju', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (240, 1, 5, '牡丹皮', 'moutan_bark', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (241, 1, 240, '凤丹皮', 'feng_moutan', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (242, 1, 240, '川丹皮', 'sichuan_moutan', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (243, 1, 5, '厚朴', 'magnolia_bark', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (244, 1, 243, '川朴', 'sichuan_magnolia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (245, 1, 243, '温朴', 'wen_magnolia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (246, 1, 5, '肉桂', 'cinnamon', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (247, 1, 246, '官桂', 'guan_cinnamon', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (248, 1, 246, '企边桂', 'qibian_cinnamon', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (249, 1, 5, '杜仲', 'eucommia', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (250, 1, 249, '川杜仲', 'sichuan_eucommia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (251, 1, 249, '陕杜仲', 'shanxi_eucommia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (252, 1, 5, '黄柏', 'phellodendron', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (253, 1, 252, '川黄柏', 'sichuan_phellodendron', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (254, 1, 252, '关黄柏', 'guan_phellodendron', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (255, 1, 5, '桑白皮', 'mulberry_bark', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (256, 1, 255, '嫩桑皮', 'tender_mulberry_bark', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (257, 1, 255, '老桑皮', 'old_mulberry_bark', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (258, 1, 5, '地骨皮', 'lycium_bark', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (259, 1, 258, '宁夏地骨皮', 'ningxia_lycium_bark', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (260, 1, 258, '甘肃地骨皮', 'gansu_lycium_bark', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (261, 1, 5, '五加皮', 'acanthopanax', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (262, 1, 261, '南五加', 'nan_acanthopanax', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (263, 1, 261, '香加皮', 'xiang_acanthopanax', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (264, 1, 5, '合欢皮', 'albizia_bark', b'1', 9, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (265, 1, 264, '安徽合欢皮', 'anhui_albizia_bark', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (266, 1, 264, '江苏合欢皮', 'jiangsu_albizia_bark', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (267, 1, 5, '秦皮', 'fraxinus', b'1', 10, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (268, 1, 267, '陕西秦皮', 'shanxi_fraxinus', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (269, 1, 267, '东北秦皮', 'dongbei_fraxinus', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (270, 1, 6, '薄荷', 'mint', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (271, 1, 270, '苏薄荷', 'su_mint', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (272, 1, 270, '杭薄荷', 'hang_mint', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (273, 1, 6, '藿香', 'agastache', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (274, 1, 273, '广藿香', 'guang_agastache', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (275, 1, 273, '川藿香', 'sichuan_agastache', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (276, 1, 6, '荆芥', 'schizonepeta', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (277, 1, 276, '江苏荆芥', 'jiangsu_schizonepeta', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (278, 1, 276, '江西荆芥', 'jiangxi_schizonepeta', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (279, 1, 6, '益母草', 'leonurus', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (280, 1, 279, '江苏益母草', 'jiangsu_leonurus', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (281, 1, 279, '安徽益母草', 'anhui_leonurus', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (282, 1, 6, '车前草', 'plantago', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (283, 1, 282, '大车前', 'big_plantago', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (284, 1, 282, '小车前', 'small_plantago', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (285, 1, 6, '蒲公英', 'dandelion', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (286, 1, 285, '东北蒲公英', 'dongbei_dandelion', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (287, 1, 285, '华北蒲公英', 'huabei_dandelion', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (288, 1, 6, '鱼腥草', 'houttuynia', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (289, 1, 288, '四川鱼腥草', 'sichuan_houttuynia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (290, 1, 288, '湖南鱼腥草', 'hunan_houttuynia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (291, 1, 6, '紫花地丁', 'viola', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (292, 1, 291, '东北紫花地丁', 'dongbei_viola', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (293, 1, 291, '华北紫花地丁', 'huabei_viola', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (294, 1, 6, '半枝莲', 'scutellaria', b'1', 9, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (295, 1, 294, '江苏半枝莲', 'jiangsu_scutellaria', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (296, 1, 294, '浙江半枝莲', 'zhejiang_scutellaria', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (297, 1, 6, '白花蛇舌草', 'hedyotis', b'1', 10, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (298, 1, 297, '广东蛇舌草', 'guangdong_hedyotis', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (299, 1, 297, '广西蛇舌草', 'guangxi_hedyotis', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (300, 1, 7, '鹿茸', 'deer_antler', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (301, 1, 300, '梅花鹿茸', 'meihua_deer', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (302, 1, 300, '马鹿茸', 'malu_deer', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (303, 1, 7, '阿胶', 'donkey_gelatin', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (304, 1, 303, '东阿阿胶', 'dong_e', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (305, 1, 303, '福牌阿胶', 'fu_e', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (306, 1, 7, '龟板', 'tortoise_shell', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (307, 1, 306, '乌龟板', 'wu_shell', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (308, 1, 306, '海龟板', 'hai_shell', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (309, 1, 7, '鳖甲', 'turtle_shell', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (310, 1, 309, '中华鳖甲', 'zhonghua_turtle', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (311, 1, 309, '山瑞鳖甲', 'shanrui_turtle', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (312, 1, 7, '蛤蚧', 'gecko', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (313, 1, 312, '广西蛤蚧', 'guangxi_gecko', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (314, 1, 312, '云南蛤蚧', 'yunnan_gecko', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (315, 1, 7, '全蝎', 'scorpion', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (316, 1, 315, '河南全蝎', 'henan_scorpion', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (317, 1, 315, '山东全蝎', 'shandong_scorpion', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (318, 1, 7, '蜈蚣', 'centipede', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (319, 1, 318, '湖北蜈蚣', 'hubei_centipede', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (320, 1, 318, '浙江蜈蚣', 'zhejiang_centipede', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (321, 1, 7, '地龙', 'earthworm', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (322, 1, 321, '广地龙', 'guang_earthworm', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (323, 1, 321, '沪地龙', 'hu_earthworm', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (324, 1, 7, '水蛭', 'leech', b'1', 9, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (325, 1, 324, '宽体金线蛭', 'kuanti_leech', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (326, 1, 324, '日本医蛭', 'riben_leech', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (327, 1, 7, '蝉蜕', 'cicada_slough', b'1', 10, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (328, 1, 327, '山东蝉蜕', 'shandong_cicada', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (329, 1, 327, '河南蝉蜕', 'henan_cicada', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (330, 1, 8, '朱砂', 'cinnabar', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (331, 1, 330, '辰砂', 'chen_sha', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (332, 1, 330, '镜面砂', 'mirror_sha', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (333, 1, 8, '石膏', 'gypsum', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (334, 1, 333, '湖北石膏', 'hubei_gypsum', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (335, 1, 333, '安徽石膏', 'anhui_gypsum', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (336, 1, 8, '滑石', 'talc', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (337, 1, 336, '江西滑石', 'jiangxi_talc', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (338, 1, 336, '山东滑石', 'shandong_talc', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (339, 1, 8, '龙骨', 'dragon_bone', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (340, 1, 339, '山西龙骨', 'shanxi_dragon', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (341, 1, 339, '陕西龙骨', 'shaanxi_dragon', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (342, 1, 8, '牡蛎', 'oyster', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (343, 1, 342, '大连牡蛎', 'dalian_oyster', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (344, 1, 342, '青岛牡蛎', 'qingdao_oyster', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (345, 1, 8, '赭石', 'hematite', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (346, 1, 345, '山西赭石', 'shanxi_hematite', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (347, 1, 345, '河北赭石', 'hebei_hematite', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (348, 1, 8, '磁石', 'magnetite', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (349, 1, 348, '安徽磁石', 'anhui_magnetite', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (350, 1, 348, '江苏磁石', 'jiangsu_magnetite', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (351, 2, 0, '江苏省', 'jiangsu', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (352, 2, 351, '南京市', 'nanjing', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (353, 2, 351, '苏州市', 'suzhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (354, 2, 351, '无锡市', 'wuxi', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (355, 2, 0, '浙江省', 'zhejiang', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (356, 2, 355, '杭州市', 'hangzhou', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (357, 2, 355, '宁波市', 'ningbo', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (358, 2, 355, '绍兴市', 'shaoxing', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (359, 2, 355, '温州市', 'wenzhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (360, 2, 355, '台州市', 'taizhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (361, 2, 355, '衢州市', 'quzhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (362, 2, 355, '金华市', 'jinhua', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (363, 2, 355, '丽水市', 'lishui', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (364, 2, 0, '安徽省', 'anhui', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (365, 2, 364, '合肥市', 'hefei', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (366, 2, 364, '芜湖市', 'wuhu', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (367, 2, 364, '蚌埠市', 'bengbu', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (368, 2, 364, '淮南市', 'huainan', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (369, 2, 364, '马鞍山市', 'maashan', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (370, 2, 364, '淮北市', 'huaibei', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (371, 2, 364, '铜陵市', 'tongling', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (372, 2, 364, '安庆市', 'anqing', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (373, 2, 364, '黄山市', 'huangshan', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (374, 2, 364, '阜阳市', 'fuyang', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (375, 2, 364, '宿州市', 'ah-suzhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (376, 2, 364, '滁州市', 'chuzhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (377, 2, 364, '六安市', 'luan', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (378, 2, 364, '宣城市', 'xuancheng', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (379, 2, 364, '池州市', 'chizhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (380, 2, 364, '亳州市', 'bozhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (381, 8, 0, '好助手', 'good_helper', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (382, 8, 0, '小程序', 'mini_program', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (383, 8, 0, '随身易', 'easy_go', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (384, 8, 0, 'APP', 'app', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (385, 8, 0, '金钥匙', 'golden_key', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (386, 8, 0, '医健', 'medical', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` (`id`, `type`, `parent_id`, `name`, `code`, `is_enabled`, `sort`, `description`, `created_by`, `created_date`, `updated_by`, `updated_date`) VALUES (387, 8, 0, '战略客户', 'strategic_client', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
COMMIT;

-- ----------------------------
-- Table structure for content_video
-- ----------------------------
DROP TABLE IF EXISTS `content_video`;
CREATE TABLE `content_video` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `duration` int NOT NULL COMMENT '时长',
  `width` int NOT NULL COMMENT '宽',
  `height` int NOT NULL COMMENT '高',
  `description` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  `created_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='内容视频表';

-- ----------------------------
-- Records of content_video
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pre_admin
-- ----------------------------
DROP TABLE IF EXISTS `pre_admin`;
CREATE TABLE `pre_admin` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  `salt` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of pre_admin
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pre_category
-- ----------------------------
DROP TABLE IF EXISTS `pre_category`;
CREATE TABLE `pre_category` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `sort` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of pre_category
-- ----------------------------
BEGIN;
INSERT INTO `pre_category` (`id`, `name`, `sort`) VALUES (1, '⭐️点收藏福利【6款】', 1);
INSERT INTO `pre_category` (`id`, `name`, `sort`) VALUES (2, '🔥 热销', 2);
INSERT INTO `pre_category` (`id`, `name`, `sort`) VALUES (3, '👛 优惠', 3);
INSERT INTO `pre_category` (`id`, `name`, `sort`) VALUES (4, '🔥 人气菜品 【15款】', 4);
INSERT INTO `pre_category` (`id`, `name`, `sort`) VALUES (5, '流量套餐', 5);
INSERT INTO `pre_category` (`id`, `name`, `sort`) VALUES (6, '国 🍢 肉肉 【26款】', 6);
INSERT INTO `pre_category` (`id`, `name`, `sort`) VALUES (7, '麻 🍄 菌菇 【8款】', 7);
INSERT INTO `pre_category` (`id`, `name`, `sort`) VALUES (8, '好 蘸料 【11款】', 8);
INSERT INTO `pre_category` (`id`, `name`, `sort`) VALUES (9, '味 🧋 饮品 【款】', 9);
INSERT INTO `pre_category` (`id`, `name`, `sort`) VALUES (10, '⭐️ 🥢 必选', 10);
COMMIT;

-- ----------------------------
-- Table structure for pre_food
-- ----------------------------
DROP TABLE IF EXISTS `pre_food`;
CREATE TABLE `pre_food` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `category_id` int unsigned NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL DEFAULT '',
  `price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `image_url` varchar(255) NOT NULL DEFAULT '',
  `status` tinyint unsigned NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of pre_food
-- ----------------------------
BEGIN;
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (1, 1, '新客福利 点亮 收藏门店 新客立减2元', 0.00, 'images/1.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (2, 1, '收藏福利 点亮收藏送3元现金券', 0.00, 'images/2.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (3, 1, '宠粉福利 点亮 蔬菜6选1(多点不送)', 0.01, 'images/3.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (4, 1, '宠粉福利 点亮 荤菜4选1(多点不送)', 0.20, 'images/4.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (5, 1, '特惠福利 点亮 仅限1瓶饮料(多点不送)', 2.28, 'images/5.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (6, 2, '经典骨汤', 2.98, 'images/6.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (7, 2, '【慈爸爸力荐】横扫饥饿单人餐', 27.90, 'images/7.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (8, 2, '【赠*冰红茶】七荤八素加主食麻辣烫套餐', 32.88, 'images/8.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (9, 2, '方便面(1个）', 4.98, 'images/9.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (10, 2, '兰花干(1个）', 4.98, 'images/10.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (11, 2, '火锅油条(1个)', 4.98, 'images/11.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (12, 2, '午餐肉(2片)', 5.98, 'images/12.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (13, 2, '大里脊(1个)', 6.18, 'images/13.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (14, 2, '撒尿牛丸(2个)', 5.98, 'images/14.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (15, 2, '醇香麻辣烫(无汤)', 3.98, 'images/15.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (16, 3, '【优惠福利】点亮⭐️ 仅限1瓶饮料(多点不送)', 2.28, 'images/16.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (17, 3, '【慈爸爸力荐】横扫饥饿单人餐', 27.90, 'images/17.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (18, 3, '【冰雪节】福气满满单人套餐', 24.90, 'images/18.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (19, 3, '【手感火热】单人专属套餐直营', 27.90, 'images/19.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (20, 3, '超级A麻辣烫套餐', 31.88, 'images/20.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (21, 3, '【赠*冰红茶】七荤八素加主食麻辣烫套餐', 32.88, 'images/21.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (22, 3, '超级B麻辣烫套餐', 35.88, 'images/22.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (23, 3, '【赠*冰红茶】大片里脊麻辣烫套餐加主食', 31.88, 'images/23.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (24, 3, '【赠*冰红茶】人气低卡麻辣烫套餐加粉丝', 31.88, 'images/24.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (25, 3, '【童心未泯】单人餐', 24.90, 'images/25.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (26, 3, '【欢乐无限】双人餐', 38.90, 'images/26.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (27, 4, '土豆(3-4片)', 3.98, 'images/27.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (28, 4, '腐竹(3-4个)', 4.58, 'images/28.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (29, 4, '方便面(1个)', 4.98, 'images/29.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (30, 4, '日本豆腐(1个)', 4.98, 'images/30.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (31, 4, '自己煎的蛋(1个)', 5.68, 'images/31.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (32, 4, '奥尔良鸡肉片(2-3片)', 5.98, 'images/32.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (33, 4, '生菜(1份)', 3.98, 'images/33.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (34, 4, '火锅川粉(1份)', 4.98, 'images/34.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (35, 4, '金针菇(1份)', 4.98, 'images/35.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (36, 4, '鱼豆腐(2个)', 4.98, 'images/36.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (37, 4, '刀削面(1包)', 5.98, 'images/37.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (38, 4, '大里脊(1个)', 6.18, 'images/38.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (39, 4, '娃娃菜(1份)', 4.98, 'images/39.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (40, 5, '龙虾饼(1个)', 5.98, 'images/40.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (41, 5, '骨肉相连', 5.98, 'images/41.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (42, 5, '红油五花肉片(3片)', 5.88, 'images/42.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (43, 5, '掌中宝(1份)', 5.98, 'images/43.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (44, 5, '玉米香肠(1个)', 5.98, 'images/44.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (45, 5, '干猪皮(2片)', 5.98, 'images/45.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (46, 5, '臻品纯肉肠(1个)', 5.98, 'images/46.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (47, 5, '麻辣肉片(1份)', 5.98, 'images/47.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (48, 5, '虾滑(1根) ', 9.80, 'images/48.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (49, 5, '腊肠(3-4块)', 5.98, 'images/49.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (50, 6, '红糖酥饼', 10.00, 'images/50.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (51, 7, '耗油青菜小炒/大份', 12.00, 'images/51.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (52, 7, '耗油青菜小炒/小份', 10.00, 'images/52.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (53, 7, '鸡蛋木耳小炒/大份', 12.00, 'images/53.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (54, 7, '鸡蛋木耳小炒/小份', 10.00, 'images/54.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (55, 7, '可乐鸡翅', 11.00, 'images/55.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (56, 7, '油菜沙拉/大份', 11.00, 'images/56.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (57, 7, '油菜沙拉/小份', 9.00, 'images/57.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (58, 8, '美式咖啡/中杯', 8.00, 'images/58.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (59, 8, '香柠咖啡/大杯', 14.00, 'images/59.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (60, 8, '拿铁咖啡/大杯', 16.00, 'images/60.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (61, 8, '拿铁咖啡/中杯', 13.00, 'images/61.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (62, 8, '卡布奇诺/大杯', 16.00, 'images/62.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (63, 8, '卡布奇诺/中杯', 13.00, 'images/63.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (64, 8, '摩卡咖啡/大杯', 19.00, 'images/64.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (65, 8, '摩卡咖啡/中杯', 16.00, 'images/65.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (66, 8, '珍珠拿铁/大杯', 16.00, 'images/66.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (67, 8, '香草拿铁/大杯', 19.00, 'images/67.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (68, 8, '香草拿铁/中杯', 16.00, 'images/68.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (69, 8, '法式奶霜咖啡/大杯', 15.00, 'images/69.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (70, 8, '海盐焦糖拿铁/大杯', 18.00, 'images/70.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (71, 8, '海盐焦糖拿铁/中杯', 15.00, 'images/71.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (72, 8, '香柠咖啡', 14.00, 'images/72.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (73, 9, '法式奶霜草莓果茶（大杯）', 15.00, 'images/73.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (74, 9, '鲜百香双响炮/大杯', 13.00, 'images/74.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (75, 9, '柠檬霸/大杯', 13.00, 'images/75.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (76, 9, '金桔柠檬汁/中杯', 11.00, 'images/76.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (77, 9, '葡萄柚绿茶/中杯', 11.00, 'images/77.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (78, 9, '草莓果茶/中杯', 10.00, 'images/78.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (79, 9, '鲜柠檬红茶/中杯', 10.00, 'images/79.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (80, 9, '鲜柠檬绿茶/中杯', 10.00, 'images/80.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` (`id`, `category_id`, `name`, `price`, `image_url`, `status`, `create_time`, `update_time`, `delete_time`) VALUES (81, 9, '鲜百香绿茶/中杯', 10.00, 'images/81.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for pre_order
-- ----------------------------
DROP TABLE IF EXISTS `pre_order`;
CREATE TABLE `pre_order` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(64) NOT NULL,
  `pickup_no` varchar(5) DEFAULT NULL,
  `user_id` varchar(64) NOT NULL DEFAULT '0',
  `price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `promotion` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `number` int unsigned NOT NULL DEFAULT '0',
  `is_paid` bit(1) NOT NULL DEFAULT b'0',
  `is_taken` bit(1) NOT NULL DEFAULT b'0',
  `comment` varchar(100) DEFAULT NULL,
  `status` tinyint unsigned NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pay_time` datetime DEFAULT NULL,
  `taken_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of pre_order
-- ----------------------------
BEGIN;
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (1, 'WD100320240531223729', 'A01', '1', 12.00, 3.00, 4, b'1', b'0', '不要辣，多放香菜', 3, '2024-05-30 01:10:35', '2024-05-30 01:10:39', '2024-05-30 01:10:43');
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (2, 'WD100420240531223729', 'A02', '1', 13.00, 2.00, 3, b'1', b'1', '放门把手', 3, '2024-05-30 01:12:20', '2024-05-30 01:11:44', '2024-05-30 01:11:46');
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (3, 'WD100520240531223729', 'A03', '1', 14.00, 0.00, 1, b'1', b'1', '放门把手', 3, '2024-05-30 01:12:15', '2024-05-30 01:12:24', '2024-05-30 01:12:28');
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (4, 'WD100620240531223729', 'A04', '1', 15.00, 0.00, 1, b'1', b'1', '放门把手', 3, '2024-05-30 01:13:37', '2024-05-30 01:12:44', NULL);
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (5, 'WD100720240531223729', 'A05', '1', 16.00, 0.00, 2, b'1', b'1', '放门把手', 3, '2024-05-30 01:13:37', '2024-05-30 01:13:43', NULL);
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (6, 'WD100820240531223729', 'A06', '1', 17.00, 0.00, 1, b'1', b'1', '放门把手', 3, '2024-05-30 01:13:37', '2024-05-30 01:13:46', NULL);
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (7, 'WD13', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 12.45, 0.00, 15, b'0', b'0', NULL, 1, '2024-06-01 19:03:56', NULL, NULL);
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (8, 'WD0', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 10.96, 0.00, 2, b'0', b'0', NULL, 1, '2024-06-01 19:12:45', NULL, NULL);
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (9, 'WD5', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 2.49, 0.00, 4, b'0', b'0', NULL, 1, '2024-06-01 19:16:06', NULL, NULL);
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (10, 'WD14', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 2.49, 0.00, 4, b'0', b'0', NULL, 1, '2024-06-01 19:25:11', NULL, NULL);
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (11, 'WD17', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 2.49, 0.00, 3, b'0', b'0', '我第一次下单', 1, '2024-06-01 20:11:39', NULL, NULL);
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (12, 'WD13', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 7.47, 0.00, 4, b'0', b'0', NULL, 1, '2024-06-01 20:15:10', NULL, NULL);
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (13, 'WD13', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 0.01, 0.00, 1, b'0', b'0', NULL, 1, '2024-06-01 20:20:06', NULL, NULL);
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (14, 'WD6', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 0.21, 0.00, 3, b'0', b'0', 'gyuguyguyguyy', 2, '2024-06-01 20:23:13', NULL, NULL);
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (15, 'WD13', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 0.01, 0.00, 1, b'0', b'0', '111111', 2, '2024-06-01 20:24:26', NULL, NULL);
INSERT INTO `pre_order` (`id`, `code`, `pickup_no`, `user_id`, `price`, `promotion`, `number`, `is_paid`, `is_taken`, `comment`, `status`, `create_time`, `pay_time`, `taken_time`) VALUES (16, 'WD10', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 0.01, 0.00, 3, b'0', b'0', '我不吃辣，嘻嘻嘻', 2, '2024-06-01 20:25:37', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for pre_order_food
-- ----------------------------
DROP TABLE IF EXISTS `pre_order_food`;
CREATE TABLE `pre_order_food` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `order_id` int unsigned NOT NULL DEFAULT '0',
  `food_id` int unsigned NOT NULL DEFAULT '0',
  `number` int unsigned NOT NULL DEFAULT '0',
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of pre_order_food
-- ----------------------------
BEGIN;
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (1, 1, 1, 1, 100.00);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (2, 1, 2, 1, 10.00);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (3, 2, 3, 1, 11.00);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (4, 3, 3, 1, 111.00);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (5, 4, 4, 4, 444.00);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (6, 5, 5, 1, 111.00);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (7, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (8, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (9, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (10, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (11, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (12, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (13, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (14, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (15, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (16, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (17, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (18, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (19, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (20, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (21, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (22, 8, 11, 1, 4.98);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (23, 8, 12, 1, 5.98);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (24, 9, 3, 1, 0.01);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (25, 9, 4, 2, 0.20);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (26, 9, 5, 1, 2.28);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (27, 10, 3, 1, 0.01);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (28, 10, 4, 1, 0.20);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (29, 10, 5, 2, 2.28);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (30, 11, 3, 1, 0.01);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (31, 11, 4, 1, 0.20);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (32, 11, 5, 1, 2.28);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (33, 12, 3, 1, 0.01);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (34, 12, 4, 1, 0.20);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (35, 12, 5, 1, 2.28);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (36, 12, 11, 1, 4.98);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (37, 13, 3, 1, 0.01);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (38, 14, 1, 1, 0.00);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (39, 14, 3, 1, 0.01);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (40, 14, 4, 1, 0.20);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (41, 15, 3, 1, 0.01);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (42, 16, 1, 1, 0.00);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (43, 16, 2, 1, 0.00);
INSERT INTO `pre_order_food` (`id`, `order_id`, `food_id`, `number`, `price`) VALUES (44, 16, 3, 1, 0.01);
COMMIT;

-- ----------------------------
-- Table structure for pre_setting
-- ----------------------------
DROP TABLE IF EXISTS `pre_setting`;
CREATE TABLE `pre_setting` (
  `name` varchar(50) NOT NULL,
  `value` longtext NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of pre_setting
-- ----------------------------
BEGIN;
INSERT INTO `pre_setting` (`name`, `value`) VALUES ('appid', '');
INSERT INTO `pre_setting` (`name`, `value`) VALUES ('appsecret', '');
INSERT INTO `pre_setting` (`name`, `value`) VALUES ('img_ad', '/static/uploads/default/image_ad.png');
INSERT INTO `pre_setting` (`name`, `value`) VALUES ('img_category', '[\"\\/static\\/uploads\\/default\\/bottom_1.png\",\"\\/static\\/uploads\\/default\\/bottom_2.png\",\"\\/static\\/uploads\\/default\\/bottom_3.png\",\"\\/static\\/uploads\\/default\\/bottom_1.png\"]');
INSERT INTO `pre_setting` (`name`, `value`) VALUES ('img_swiper', '[\"\\/static\\/uploads\\/default\\/banner_1.png\",\"\\/static\\/uploads\\/default\\/banner_2.png\",\"\\/static\\/uploads\\/default\\/banner_3.png\"]');
INSERT INTO `pre_setting` (`name`, `value`) VALUES ('promotion', '[{\"k\":50,\"v\":10}]');
COMMIT;

-- ----------------------------
-- Table structure for pre_user
-- ----------------------------
DROP TABLE IF EXISTS `pre_user`;
CREATE TABLE `pre_user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) NOT NULL DEFAULT '',
  `price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of pre_user
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
