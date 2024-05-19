/*
 Navicat Premium Dump SQL

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 90000 (9.0.0)
 Source Host           : localhost:3306
 Source Schema         : silence-content

 Target Server Type    : MySQL
 Target Server Version : 90000 (9.0.0)
 File Encoding         : 65001

 Date: 09/03/2025 23:50:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '图书名称',
  `status` tinyint UNSIGNED NOT NULL COMMENT '图书状态',
  `published_at` datetime NOT NULL COMMENT '发布时间',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '图书作者',
  `isbn` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '图书编码',
  `cover_image_reference` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '封面图引用',
  `content_reference` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '图书正文',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_book_isbn`(`isbn` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '图书表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, 'Spring Security 实战', 1, '2025-03-09 17:02:00', 100.00, '陈木鑫', '978-7-121-37143-1', 'www.baidu.com', 'www.baidu.com', 'admin', '2025-03-09 17:03:00.000', 'admin', '2025-03-09 17:03:07.000');

-- ----------------------------
-- Table structure for book_content_tag
-- ----------------------------
DROP TABLE IF EXISTS `book_content_tag`;
CREATE TABLE `book_content_tag`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `book_id` bigint UNSIGNED NOT NULL COMMENT '图书id',
  `tag_id` bigint UNSIGNED NOT NULL COMMENT '标签id',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_book_content_tag`(`book_id` ASC, `tag_id` ASC) USING BTREE,
  UNIQUE INDEX `idx_book_content_tag_tag_id`(`book_id` ASC, `tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '图书标签关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_content_tag
-- ----------------------------
INSERT INTO `book_content_tag` VALUES (1, 1, 1, 'admin', '2025-03-09 17:04:28.000', 'admin', '2025-03-09 17:04:31.000');

-- ----------------------------
-- Table structure for content
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `type` tinyint UNSIGNED NOT NULL COMMENT '内容类型',
  `status` tinyint UNSIGNED NOT NULL COMMENT '内容状态',
  `published_at` datetime NOT NULL COMMENT '发布时间',
  `author` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '作者',
  `content_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '内容编码',
  `cover_image_reference` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '封面图引用',
  `cover_image_reference_mode` tinyint UNSIGNED NOT NULL COMMENT '内容封面图引用方式',
  `content_reference_mode` tinyint UNSIGNED NOT NULL COMMENT '内容引用方式',
  `content_reference` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '内容正文',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_content_content_code`(`content_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '内容表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content
-- ----------------------------

-- ----------------------------
-- Table structure for content_tag
-- ----------------------------
DROP TABLE IF EXISTS `content_tag`;
CREATE TABLE `content_tag`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '标签名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '标签编码',
  `type` tinyint UNSIGNED NOT NULL COMMENT '标签类型',
  `parent_id` bigint UNSIGNED NOT NULL COMMENT '父标签id',
  `icon_reference` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '标签图标地址',
  `is_enabled` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用',
  `sort` int UNSIGNED NOT NULL COMMENT '标签排序',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '内容标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_tag
-- ----------------------------
INSERT INTO `content_tag` VALUES (1, '安全', 'B001', 1, 0, '', b'1', 1, 'admin', '2025-03-09 17:04:07.000', 'admin', '2025-03-09 17:04:10.000');
INSERT INTO `content_tag` VALUES (2, '框架', 'B002', 1, 0, '', b'1', 2, 'admin', '2025-03-09 17:04:07.000', 'admin', '2025-03-09 17:04:10.000');
INSERT INTO `content_tag` VALUES (3, '防御', 'B003', 1, 0, '', b'1', 3, 'admin', '2025-03-09 17:04:07.000', 'admin', '2025-03-09 17:04:10.000');
INSERT INTO `content_tag` VALUES (4, 'Spring', 'B006', 1, 1, '', b'1', 6, 'admin', '2025-03-09 17:04:07.000', 'admin', '2025-03-09 17:04:10.000');
INSERT INTO `content_tag` VALUES (5, '框架', 'B004', 1, 0, '', b'1', 4, 'admin', '2025-03-09 17:04:07.000', 'admin', '2025-03-09 17:04:10.000');
INSERT INTO `content_tag` VALUES (6, '防御', 'B005', 1, 0, '', b'1', 5, 'admin', '2025-03-09 17:04:07.000', 'admin', '2025-03-09 17:04:10.000');
INSERT INTO `content_tag` VALUES (7, 'Mybatis', 'B007', 1, 1, '', b'1', 7, 'admin', '2025-03-09 17:04:07.000', 'admin', '2025-03-09 17:04:10.000');
INSERT INTO `content_tag` VALUES (8, 'MySQL', 'B008', 1, 1, '', b'1', 8, 'admin', '2025-03-09 17:04:07.000', 'admin', '2025-03-09 17:04:10.000');
INSERT INTO `content_tag` VALUES (9, 'Redis', 'B009', 1, 1, '', b'1', 9, 'admin', '2025-03-09 17:04:07.000', 'admin', '2025-03-09 17:04:10.000');
INSERT INTO `content_tag` VALUES (10, '古典', '40bf70f7-fc45-4476-9052-8e10bfa84aff', 2, 0, '260.jpg', b'1', 1, 'SYSTEM', '2025-03-09 23:07:18.897', 'SYSTEM', '2025-03-09 23:07:18.897');

SET FOREIGN_KEY_CHECKS = 1;
