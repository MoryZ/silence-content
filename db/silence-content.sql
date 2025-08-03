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

 Date: 04/08/2025 00:20:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book_content_tag
-- ----------------------------
DROP TABLE IF EXISTS `book_content_tag`;
CREATE TABLE `book_content_tag`  (
  `book_id` bigint UNSIGNED NOT NULL COMMENT '图书ID',
  `tag_id` bigint UNSIGNED NOT NULL COMMENT '标签ID',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`book_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图书标签关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_content_tag
-- ----------------------------

-- ----------------------------
-- Table structure for content
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` tinyint UNSIGNED NOT NULL,
  `status` tinyint UNSIGNED NOT NULL,
  `published_at` datetime NULL DEFAULT NULL,
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `cover_image_reference` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cover_image_reference_mode` tinyint UNSIGNED NOT NULL,
  `content_reference_mode` tinyint UNSIGNED NOT NULL,
  `content_reference` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `keywords` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `audit_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `task_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tenant_id` bigint NOT NULL,
  `parent_id` bigint NOT NULL,
  `root_id` bigint NOT NULL,
  `is_sticky_top` bit(1) NULL DEFAULT NULL,
  `sticky_top_at` datetime NULL DEFAULT NULL,
  `sticky_top_expired_at` datetime NULL DEFAULT NULL,
  `is_disclosure` bit(1) NULL DEFAULT NULL,
  `is_leaf` bit(1) NULL DEFAULT NULL,
  `attributes` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `business_status` int UNSIGNED NOT NULL,
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `source` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `is_code_display_required` bit(1) NOT NULL DEFAULT b'0',
  `is_need_internal_review` bit(1) NOT NULL DEFAULT b'0',
  `expired_at` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 291 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content
-- ----------------------------
INSERT INTO `content` VALUES (9, '智能客服 与 保险API 的 保险杠杆对冲', 1, 3, '2025-06-30 18:15:17', 'author', '34fee181-5da2-4b40-a64a-007e4fe9615e', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-05 18:15:17', '2025-07-08 18:15:17', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:15:19', 'SYSTEM', '2025-07-30 10:15:19', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (11, '区块链保单 与 微服务架构 的 风险加权对冲', 3, 2, '2025-07-18 18:15:18', 'author', 'a741e6bd-a6a1-44d5-81e6-766d5f6c3f2d', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-19 18:15:18', '2025-07-25 18:15:18', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:15:19', 'SYSTEM', '2025-07-30 10:15:19', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (12, '从 分布式保单 到 保险API：一场 共保体 的 预警 之旅', 4, 1, '2025-07-08 18:15:18', 'author', 'bac4c9f3-dcd1-44a8-942a-953f7fbb122f', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-10 18:15:18', '2025-07-11 18:15:18', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:15:19', 'SYSTEM', '2025-07-30 10:15:19', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (13, '健康大数据 与 区块链保单 的 偿付能力共担', 5, 2, '2025-07-27 18:15:18', 'author', 'f653c183-df3d-4f09-83e5-7880bc0b8867', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-30 18:15:18', '2025-07-31 18:15:18', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:15:19', 'SYSTEM', '2025-07-30 10:15:19', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (14, '从 智能客服 到 保险科技：一场 生态化 的 托底 之旅', 6, 5, '2025-07-12 18:15:19', 'author', '62f7830f-acd7-4ad0-8269-6625fd04abfd', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-16 18:15:19', '2025-07-23 18:15:19', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:15:19', 'SYSTEM', '2025-07-30 10:15:19', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (15, '从 微服务架构 到 物联网保险：一场 免赔额 的 再保 之旅', 7, 4, '2025-07-01 18:15:19', 'author', 'efa2470a-5e52-4129-9f10-807042d19d5d', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-03 18:15:19', '2025-07-08 18:15:19', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:15:19', 'SYSTEM', '2025-07-30 10:15:19', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (16, '免赔额智能核保：批改时代的精算模型实践', 8, 4, '2025-07-26 18:15:19', 'author', '10ac714c-6f6d-4979-80dc-1f3caf8b9b0f', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-26 18:15:19', '2025-08-02 18:15:19', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:15:19', 'SYSTEM', '2025-07-30 10:15:19', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (17, '客户画像 与 保险SaaS 的 不可抗辩批改', 9, 2, '2025-07-03 18:15:19', 'author', '3f0e3e41-302f-40c4-b97a-4ce21b629f8f', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-05 18:15:19', '2025-07-12 18:15:19', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:15:19', 'SYSTEM', '2025-07-30 10:15:19', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (18, '风险加权分布式保单：批改时代的保险API实践', 10, 4, '2025-07-12 18:15:19', 'author', '3d79c6d2-2564-4fa6-915b-7207785a662a', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-17 18:15:19', '2025-07-22 18:15:19', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:15:19', 'SYSTEM', '2025-07-30 10:15:19', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (19, '从 智能核保 到 分布式保单：一场 风险对冲 的 批改 之旅', 1, 1, '2025-07-05 18:19:08', 'author', '1b1ddbaf-75bb-4d23-a8b7-989867ff47d2', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-10 18:19:08', '2025-07-14 18:19:08', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:19:09', 'SYSTEM', '2025-07-30 10:19:09', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (21, '从 反欺诈系统 到 AI风控：一场 免赔额 的 分保 之旅', 3, 4, '2025-07-18 18:19:08', 'author', 'eb9fd36f-7677-4647-b978-0eb2972106f7', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-18 18:19:08', '2025-07-24 18:19:08', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:19:09', 'SYSTEM', '2025-07-30 10:19:09', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (22, '不可抗辩健康大数据：兜底时代的客户画像实践', 4, 4, '2025-07-26 18:19:08', 'author', '61f11343-fe23-4a2c-bfb8-3fc480369132', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-30 18:19:08', '2025-08-05 18:19:08', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:19:09', 'SYSTEM', '2025-07-30 10:19:09', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (23, '从 保险中台 到 反欺诈系统：一场 不可抗辩 的 穿透 之旅', 5, 2, '2025-07-04 18:19:08', 'author', '76549460-dd91-49ea-85ec-4b1ba7fa0adf', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-05 18:19:08', '2025-07-12 18:19:08', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:19:09', 'SYSTEM', '2025-07-30 10:19:09', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (24, '损失补偿保险大数据：风控时代的风险定价实践', 6, 2, '2025-07-05 18:19:08', 'author', '3073a48a-5139-465e-8a0b-fc0aee4083d7', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-05 18:19:08', '2025-07-06 18:19:08', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:19:09', 'SYSTEM', '2025-07-30 10:19:09', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (25, '长尾效应保险云：护航时代的物联网保险实践', 7, 3, '2025-07-10 18:19:08', 'author', 'a81ddfdf-9ccc-4609-a286-7f59d047d0f4', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-11 18:19:08', '2025-07-13 18:19:08', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:19:09', 'SYSTEM', '2025-07-30 10:19:09', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (26, '再保险数字化营销：分保时代的保险搜索引擎实践', 8, 1, '2025-07-17 18:19:08', 'author', '53c5c901-2822-4a90-9212-2ad4b7115e3a', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-19 18:19:08', '2025-07-26 18:19:08', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:19:09', 'SYSTEM', '2025-07-30 10:19:09', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (27, '从 客户画像 到 UBI车险：一场 场景化 的 护航 之旅', 9, 4, '2025-07-22 18:19:08', 'author', '6a78d21b-0b02-446a-b8a8-d5eab44791fc', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-24 18:19:08', '2025-07-27 18:19:08', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:19:09', 'SYSTEM', '2025-07-30 10:19:09', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (28, '场景化UBI车险：核保时代的客户画像实践', 10, 4, '2025-07-17 18:19:09', 'author', 'af07e078-c853-405b-ac2c-3d1742fcaef3', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-18 18:19:09', '2025-07-19 18:19:09', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:19:09', 'SYSTEM', '2025-07-30 10:19:09', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (29, '大数法则智能核保：批改时代的微服务架构实践', 1, 1, '2025-07-06 18:21:07', 'author', 'f22c5dbb-c2b1-469e-b940-06dade368587', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-11 18:21:07', '2025-07-16 18:21:07', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:21:08', 'SYSTEM', '2025-07-30 10:21:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (30, '等待期健康大数据：核保时代的保险大数据实践', 2, 4, '2025-07-08 18:21:07', 'author', '6fe0a65b-a7c0-4bea-bbe6-fc8493740cd2', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-13 18:21:07', '2025-07-19 18:21:07', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:21:08', 'SYSTEM', '2025-07-30 10:21:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (31, '从 UBI车险 到 数字化营销：一场 保险杠杆 的 预警 之旅', 3, 4, '2025-07-09 18:21:07', 'author', '46d3db50-33d9-463e-a6f8-b77a9a869cad', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-13 18:21:07', '2025-07-15 18:21:07', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:21:08', 'SYSTEM', '2025-07-30 10:21:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (32, '精算模型 与 数字化营销 的 保险杠杆保全', 4, 6, '2025-07-23 18:21:07', 'author', '4668cd80-7025-47f2-80d1-4368efc29a05', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-23 18:21:07', '2025-07-25 18:21:07', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:21:08', 'SYSTEM', '2025-07-30 10:21:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (33, '共保体精算模型：追溯时代的物联网保险实践', 5, 4, '2025-07-21 18:21:07', 'author', '535927f2-e286-487b-885c-c31770592bf4', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-23 18:21:07', '2025-07-25 18:21:07', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:21:08', 'SYSTEM', '2025-07-30 10:21:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (34, '保险密度远程定损：批改时代的区块链保单实践', 6, 6, '2025-07-28 18:21:07', 'author', 'c23e2556-cc68-45ec-aeb6-dc738a2341ab', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-30 18:21:07', '2025-07-31 18:21:07', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:21:08', 'SYSTEM', '2025-07-30 10:21:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (35, '从 UBI车险 到 保险SaaS：一场 大数法则 的 理赔 之旅', 7, 4, '2025-07-29 18:21:07', 'author', 'a581fb2c-d89f-4cec-93f0-f692db4a251f', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-01 18:21:07', '2025-08-05 18:21:07', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:21:08', 'SYSTEM', '2025-07-30 10:21:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (36, '深度科技AI风控：核保时代的理赔自动化实践', 8, 5, '2025-07-26 18:21:07', 'author', '0d3ae205-9106-4891-8dfd-a6545682da9b', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-29 18:21:07', '2025-08-05 18:21:07', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:21:08', 'SYSTEM', '2025-07-30 10:21:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (37, '大数法则保险大数据：续期时代的客户画像实践', 9, 1, '2025-07-21 18:21:07', 'author', 'b21ee0f3-9b08-4fd2-a878-5d2af7813e2f', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-23 18:21:07', '2025-07-28 18:21:07', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:21:08', 'SYSTEM', '2025-07-30 10:21:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (38, '保险杠杆保险搜索引擎：对冲时代的智能客服实践', 10, 4, '2025-07-18 18:21:07', 'author', '4536a653-48a8-46d4-bdb3-04b2c5e7ee22', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-18 18:21:07', '2025-07-25 18:21:07', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:21:08', 'SYSTEM', '2025-07-30 10:21:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (39, '从 远程定损 到 分布式保单：一场 大数法则 的 对冲 之旅', 1, 5, '2025-07-25 18:21:54', 'author', 'f6a56687-2fe1-4992-96a1-1d948ab07bf5', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-26 18:21:54', '2025-07-28 18:21:54', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:22:28', 'SYSTEM', '2025-07-30 10:22:28', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (40, '偿付能力保险API：追溯时代的保险大数据实践', 2, 1, '2025-07-25 18:22:28', 'author', 'd6a2d720-a1a6-48e5-8ea7-f57c21d8844f', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-29 18:22:28', '2025-08-02 18:22:28', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:22:28', 'SYSTEM', '2025-07-30 10:22:28', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (41, '反欺诈系统 与 区块链保单 的 大数法则兜底', 3, 2, '2025-07-05 18:22:28', 'author', '93233b31-0884-4820-a8f4-fb4fdc91fbb6', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-05 18:22:28', '2025-07-09 18:22:28', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:22:28', 'SYSTEM', '2025-07-30 10:22:28', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (42, '从 远程定损 到 保险SaaS：一场 保险杠杆 的 护航 之旅', 4, 6, '2025-07-15 18:22:28', 'author', '169d7930-0996-4994-9cc2-ff1936050180', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-19 18:22:28', '2025-07-20 18:22:28', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:22:28', 'SYSTEM', '2025-07-30 10:22:28', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (43, '保险SaaS 与 理赔自动化 的 保险杠杆保全', 5, 6, '2025-07-29 18:22:28', 'author', '498d4b0d-2c9c-44ee-8a5a-f952e7ba3482', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-30 18:22:28', '2025-08-04 18:22:28', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:22:28', 'SYSTEM', '2025-07-30 10:22:28', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (44, '偿付能力微服务架构：续期时代的保险中台实践', 6, 6, '2025-06-30 18:22:28', 'author', 'f3d1b880-6837-4e48-be7f-6a55c9255431', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-01 18:22:28', '2025-07-08 18:22:28', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:22:28', 'SYSTEM', '2025-07-30 10:22:28', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (45, '穿戴设备数据 与 保险云 的 最大诚信追溯', 7, 3, '2025-07-21 18:22:28', 'author', '9635b3c6-9a9b-4538-ba5c-efa4988aef59', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-25 18:22:28', '2025-07-28 18:22:28', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:22:28', 'SYSTEM', '2025-07-30 10:22:28', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (46, '精算模型 与 UBI车险 的 风险对冲核保', 8, 6, '2025-07-20 18:22:28', 'author', 'd266bc81-7961-49f7-8e0b-b5fc023332fa', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-23 18:22:28', '2025-07-24 18:22:28', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:22:28', 'SYSTEM', '2025-07-30 10:22:28', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (47, '从 智能客服 到 保险搜索引擎：一场 场景化 的 保全 之旅', 9, 1, '2025-07-24 18:22:28', 'author', 'bd81385c-b7e5-40e9-9fc5-f5ff5323d32b', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-24 18:22:28', '2025-07-30 18:22:28', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:22:28', 'SYSTEM', '2025-07-30 10:22:28', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (48, '精算模型 与 穿戴设备数据 的 风险对冲穿透', 10, 6, '2025-07-27 18:22:28', 'author', 'c6d2e99c-7a23-4a32-b0b5-8ded404d5b36', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-31 18:22:28', '2025-08-05 18:22:28', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:22:29', 'SYSTEM', '2025-07-30 10:22:29', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (49, '最大诚信保险SaaS：共担时代的分布式保单实践', 1, 6, '2025-07-04 18:23:13', 'author', 'e0452105-bc84-4b92-a7b2-c7deb5bbc3b8', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-08 18:23:13', '2025-07-13 18:23:13', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:23:32', 'SYSTEM', '2025-07-30 10:23:32', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (50, '风险加权保险SaaS：保全时代的保险科技实践', 2, 2, '2025-07-02 18:23:31', 'author', '8bfdfeb3-3954-4c4b-b2da-ede2597154db', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-05 18:23:31', '2025-07-10 18:23:31', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:23:32', 'SYSTEM', '2025-07-30 10:23:32', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (51, '保险密度健康大数据：批改时代的区块链保单实践', 3, 5, '2025-07-04 18:23:31', 'author', '1ed46b4d-ded3-4bbc-9998-5c2305255117', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-06 18:23:31', '2025-07-07 18:23:31', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:23:32', 'SYSTEM', '2025-07-30 10:23:32', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (52, '保险SaaS 与 分布式保单 的 生态化共担', 4, 4, '2025-07-05 18:23:31', 'author', '58e1bef4-665b-49b7-93ff-01fb4ec87c61', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-07 18:23:31', '2025-07-08 18:23:31', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:23:32', 'SYSTEM', '2025-07-30 10:23:32', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (53, '穿透式保险云：追溯时代的反欺诈系统实践', 5, 1, '2025-07-18 18:23:31', 'author', '05a8b099-ef86-4704-9de7-dc9dc051813f', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-22 18:23:31', '2025-07-28 18:23:31', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:23:32', 'SYSTEM', '2025-07-30 10:23:32', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (54, '保险大数据 与 分布式保单 的 最大诚信对冲', 6, 4, '2025-07-28 18:23:31', 'author', 'fe342f7d-790b-40ca-9fe8-a5c5e4d50d53', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-31 18:23:31', '2025-08-01 18:23:31', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:23:32', 'SYSTEM', '2025-07-30 10:23:32', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (55, '智能核保 与 保险科技 的 偿付能力续期', 7, 3, '2025-07-07 18:23:31', 'author', 'b9eacd41-bfc4-4416-91ad-f2d69f06ffc8', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-11 18:23:31', '2025-07-14 18:23:31', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:23:32', 'SYSTEM', '2025-07-30 10:23:32', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (56, '等待期智能核保：分保时代的微服务架构实践', 8, 2, '2025-07-05 18:23:31', 'author', 'ff8ea43f-f5a6-4f4f-b961-080c9327aa29', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-09 18:23:31', '2025-07-16 18:23:31', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:23:32', 'SYSTEM', '2025-07-30 10:23:32', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (57, '保险SaaS 与 微服务架构 的 穿透式风控', 9, 2, '2025-07-23 18:23:31', 'author', '5a70d832-7205-4ff2-a0a0-ae9e78750021', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-28 18:23:31', '2025-08-02 18:23:31', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:23:32', 'SYSTEM', '2025-07-30 10:23:32', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (58, '从 保险SaaS 到 UBI车险：一场 等待期 的 风控 之旅', 10, 4, '2025-07-06 18:23:31', 'author', '5908ed06-18f6-4629-b3e0-1db99d148dad', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-09 18:23:31', '2025-07-13 18:23:31', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 10:23:32', 'SYSTEM', '2025-07-30 10:23:32', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (59, '场景化理赔自动化：再保时代的反欺诈系统实践', 1, 1, '2025-07-20 20:11:30', 'author', '40a0ba52-a64d-4fc3-94d4-4e0d03257061', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-21 20:11:30', '2025-07-26 20:11:30', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 12:11:56', 'SYSTEM', '2025-07-30 12:11:56', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (60, '从 健康大数据 到 数字化营销：一场 不可抗辩 的 分保 之旅', 2, 6, '2025-07-14 20:11:56', 'author', '4dd25d4a-485f-45f9-82c8-c70e24603111', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-19 20:11:56', '2025-07-24 20:11:56', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 12:11:57', 'SYSTEM', '2025-07-30 12:11:57', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (61, '风险对冲分布式保单：批改时代的保险搜索引擎实践', 3, 6, '2025-07-18 20:11:56', 'author', '78184f34-2add-40bc-b0f6-0f8422bb9b3d', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-22 20:11:56', '2025-07-25 20:11:56', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 12:11:57', 'SYSTEM', '2025-07-30 12:11:57', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (62, '客户画像 与 物联网保险 的 风险对冲承保', 4, 5, '2025-07-29 20:11:56', 'author', 'bbe2b9a7-d3f7-4864-a0f8-0fffe62c7660', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-29 20:11:56', '2025-07-30 20:11:56', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 12:11:57', 'SYSTEM', '2025-07-30 12:11:57', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (63, '保险大数据 与 分布式保单 的 风险对冲理赔', 5, 3, '2025-07-14 20:11:56', 'author', '9d085646-3045-463b-9ad4-1a7d2589d937', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-16 20:11:56', '2025-07-19 20:11:56', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 12:11:57', 'SYSTEM', '2025-07-30 12:11:57', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (64, '保险搜索引擎 与 智能核保 的 穿透式续期', 6, 5, '2025-07-05 20:11:56', 'author', '36de6727-fea4-4a28-b73c-16f9e6ea8506', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-05 20:11:56', '2025-07-06 20:11:56', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 12:11:57', 'SYSTEM', '2025-07-30 12:11:57', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (65, '从 保险云 到 分布式保单：一场 深度科技 的 承保 之旅', 7, 4, '2025-07-27 20:11:56', 'author', 'e1687f98-8fa4-40c6-9139-67c833d65cdb', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-01 20:11:56', '2025-08-06 20:11:56', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 12:11:57', 'SYSTEM', '2025-07-30 12:11:57', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (66, '微服务架构 与 风险定价 的 保险杠杆共保', 8, 5, '2025-06-30 20:11:56', 'author', 'ba0050e4-e33b-4ee2-99a7-9b6f68458106', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-01 20:11:56', '2025-07-03 20:11:56', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 12:11:57', 'SYSTEM', '2025-07-30 12:11:57', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (67, '风险定价 与 远程定损 的 共保体追溯', 9, 4, '2025-07-26 20:11:56', 'author', 'de67f198-8740-43a5-890b-bda3e07d307a', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-30 20:11:56', '2025-08-05 20:11:56', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 12:11:57', 'SYSTEM', '2025-07-30 12:11:57', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (68, '从 理赔自动化 到 保险SaaS：一场 保险密度 的 分保 之旅', 10, 4, '2025-07-26 20:11:57', 'author', 'a2a28fba-6298-4aa1-887d-350677ae2b78', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-27 20:11:57', '2025-07-31 20:11:57', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 12:11:57', 'SYSTEM', '2025-07-30 12:11:57', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (74, '从 AI风控 到 保险中台：一场 再保险 的 预警 之旅', 7, 2, '2025-07-08 20:14:34', 'author', 'e0b87993-6fcb-4a75-b07d-9b4724a72e68', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-13 20:14:34', '2025-07-17 20:14:34', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 12:14:35', 'SYSTEM', '2025-07-30 12:14:35', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (83, '物联网保险 与 反欺诈系统 的 长尾效应分保', 7, 6, '2025-07-16 20:19:14', 'author', '4322f3e8-a787-4f6a-814b-7e0a19926197', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-17 20:19:14', '2025-07-19 20:19:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-07-30 12:19:15', 'SYSTEM', '2025-07-30 12:19:15', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (164, '从 分布式保单 到 保险SaaS：一场 保险密度 的 托底 之旅', 1, 3, '2025-07-30 22:32:53', 'author', '6e8e8c0a-26e9-44d5-acbf-c50f3f34bbdd', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-01 22:32:53', '2025-08-05 22:32:53', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:32:53', 'SYSTEM', '2025-08-03 14:32:53', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (165, '物联网保险 与 远程定损 的 最大诚信分保', 1, 6, '2025-07-31 22:35:13', 'author', '1ac64647-4e3a-4a43-9e4c-655282bd5326', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-01 22:35:13', '2025-08-06 22:35:13', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:35:14', 'SYSTEM', '2025-08-03 14:35:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (166, '最大诚信穿戴设备数据：再保时代的分布式保单实践', 3, 4, '2025-07-23 22:35:14', 'author', '5734d546-f335-4ad1-914e-89462b286023', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-25 22:35:14', '2025-07-31 22:35:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:35:14', 'SYSTEM', '2025-08-03 14:35:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (167, '数字化营销 与 AI风控 的 生态化核保', 4, 3, '2025-07-24 22:35:14', 'author', 'f240a139-efa6-4a7a-b568-0012c2267984', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-29 22:35:14', '2025-08-03 22:35:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:35:14', 'SYSTEM', '2025-08-03 14:35:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (168, '保险搜索引擎 与 穿戴设备数据 的 共保体兜底', 5, 4, '2025-07-11 22:35:14', 'author', 'd0b07790-d24d-4d52-a8c3-cdc8fd4595e6', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-11 22:35:14', '2025-07-18 22:35:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:35:14', 'SYSTEM', '2025-08-03 14:35:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (169, '损失补偿穿戴设备数据：守护时代的保险中台实践', 6, 6, '2025-07-23 22:35:14', 'author', '2055819d-9934-467e-b4ba-2e862b83c539', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-26 22:35:14', '2025-07-27 22:35:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:35:14', 'SYSTEM', '2025-08-03 14:35:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (170, '从 保险云 到 物联网保险：一场 穿透式 的 精算 之旅', 7, 1, '2025-08-01 22:35:14', 'author', 'fecc1b45-6362-4624-9220-49282ef8814f', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-05 22:35:14', '2025-08-09 22:35:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:35:14', 'SYSTEM', '2025-08-03 14:35:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (171, '从 风险定价 到 穿戴设备数据：一场 风险加权 的 穿透 之旅', 8, 4, '2025-07-21 22:35:14', 'author', 'b63a5da0-0ddc-4b25-821e-90f2bc0c3519', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-23 22:35:14', '2025-07-29 22:35:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:35:14', 'SYSTEM', '2025-08-03 14:35:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (172, '再保险客户画像：共保时代的保险云实践', 9, 4, '2025-08-03 22:35:14', 'author', '6911cfa3-6ef6-4106-a817-b042bc26ba00', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-04 22:35:14', '2025-08-11 22:35:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:35:14', 'SYSTEM', '2025-08-03 14:35:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (173, '保险科技 与 客户画像 的 风险对冲分保', 10, 2, '2025-07-10 22:35:14', 'author', 'df2b2ccc-95b8-4030-8f3b-a5eb1df1e53e', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-15 22:35:14', '2025-07-22 22:35:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:35:14', 'SYSTEM', '2025-08-03 14:35:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (174, '远程定损 与 分布式保单 的 大数法则续期', 1, 4, '2025-08-01 22:36:45', 'author', '55a90c76-796a-4fd6-89be-de8c2591c922', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-06 22:36:45', '2025-08-13 22:36:45', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:36:46', 'SYSTEM', '2025-08-03 14:36:46', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (175, '损失补偿微服务架构：批改时代的理赔自动化实践', 3, 2, '2025-07-07 22:36:46', 'author', '9c0a24f2-aea8-42f8-ac1f-b33a992ea67e', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-11 22:36:46', '2025-07-15 22:36:46', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:36:46', 'SYSTEM', '2025-08-03 14:36:46', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (176, '从 精算模型 到 UBI车险：一场 再保险 的 预警 之旅', 4, 1, '2025-07-09 22:36:46', 'author', '5c046ae3-f078-456a-baa3-086d7485370f', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-11 22:36:46', '2025-07-12 22:36:46', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:36:46', 'SYSTEM', '2025-08-03 14:36:46', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (177, '反欺诈系统 与 保险SaaS 的 等待期承保', 5, 3, '2025-07-11 22:36:46', 'author', 'e1854085-92e4-41a0-81dc-ccfc88b392e9', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-16 22:36:46', '2025-07-17 22:36:46', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:36:46', 'SYSTEM', '2025-08-03 14:36:46', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (178, '从 智能核保 到 健康大数据：一场 等待期 的 共保 之旅', 6, 2, '2025-07-19 22:36:46', 'author', 'a2d9b07d-6191-41f4-b748-623d320e1912', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-22 22:36:46', '2025-07-28 22:36:46', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:36:46', 'SYSTEM', '2025-08-03 14:36:46', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (179, '从 保险SaaS 到 分布式保单：一场 再保险 的 承保 之旅', 7, 6, '2025-07-27 22:36:46', 'author', 'a58cf989-9a65-4046-90b6-03d0636cd991', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-30 22:36:46', '2025-08-01 22:36:46', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:36:46', 'SYSTEM', '2025-08-03 14:36:46', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (180, '物联网保险 与 理赔自动化 的 生态化理赔', 8, 3, '2025-07-16 22:36:46', 'author', '4a556563-2220-4af1-b52a-6c4f2ad21bf9', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-17 22:36:46', '2025-07-22 22:36:46', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:36:46', 'SYSTEM', '2025-08-03 14:36:46', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (181, '从 客户画像 到 保险搜索引擎：一场 生态化 的 对冲 之旅', 9, 4, '2025-07-27 22:36:46', 'author', 'b2bcfcbe-3ca4-4d4d-b5fd-e75685049dc8', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-31 22:36:46', '2025-08-04 22:36:46', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:36:46', 'SYSTEM', '2025-08-03 14:36:46', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (182, '保险云 与 保险大数据 的 保险密度共保', 10, 6, '2025-07-23 22:36:46', 'author', '3de970a2-9205-4bfc-8c7a-1283b92b07c1', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-27 22:36:46', '2025-07-31 22:36:46', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:36:46', 'SYSTEM', '2025-08-03 14:36:46', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (183, '健康大数据 与 风险定价 的 大数法则理赔', 1, 1, '2025-07-29 22:36:57', 'author', '7c432f63-9be0-4487-b15e-2b2463a8df95', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-29 22:36:57', '2025-08-01 22:36:57', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:36:57', 'SYSTEM', '2025-08-03 14:36:57', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (184, '共保体区块链保单：再保时代的保险API实践', 3, 5, '2025-07-31 22:37:27', 'author', 'ee6c5739-590a-4378-be69-e5028db0b5cc', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-31 22:37:27', '2025-08-04 22:37:27', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:37:27', 'SYSTEM', '2025-08-03 14:37:27', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (185, '微服务架构 与 保险中台 的 风险对冲穿透', 4, 2, '2025-07-16 22:37:27', 'author', '3ed3f6d1-196a-4934-8fb1-69f251d0c979', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-18 22:37:27', '2025-07-22 22:37:27', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:37:27', 'SYSTEM', '2025-08-03 14:37:27', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (186, '共保体UBI车险：保全时代的智能客服实践', 5, 3, '2025-07-06 22:37:27', 'author', '427d0cdf-b825-4fd9-abe2-d873b73afd76', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-09 22:37:27', '2025-07-14 22:37:27', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:37:27', 'SYSTEM', '2025-08-03 14:37:27', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (187, '生态化分布式保单：兜底时代的反欺诈系统实践', 6, 3, '2025-07-28 22:37:27', 'author', '1f877744-652b-445a-b21b-f0526a2238b1', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-30 22:37:27', '2025-07-31 22:37:27', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:37:27', 'SYSTEM', '2025-08-03 14:37:27', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (188, '场景化数字化营销：共保时代的保险搜索引擎实践', 7, 4, '2025-08-02 22:37:27', 'author', '12f869dc-5b1b-4755-998d-f8e684ff541c', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-06 22:37:27', '2025-08-08 22:37:27', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:37:27', 'SYSTEM', '2025-08-03 14:37:27', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (189, '从 保险中台 到 保险搜索引擎：一场 长尾效应 的 风控 之旅', 8, 2, '2025-07-17 22:37:27', 'author', '2c46cf2a-d8fb-4f91-b59c-34735058417c', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-19 22:37:27', '2025-07-23 22:37:27', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:37:28', 'SYSTEM', '2025-08-03 14:37:28', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (190, '穿透式理赔自动化：风控时代的保险科技实践', 9, 6, '2025-08-03 22:37:27', 'author', '9f78270d-1e4e-4303-9c32-79244f8dfa5c', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-08 22:37:27', '2025-08-12 22:37:27', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:37:28', 'SYSTEM', '2025-08-03 14:37:28', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (191, '从 区块链保单 到 保险搜索引擎：一场 保险杠杆 的 预警 之旅', 10, 6, '2025-07-19 22:37:27', 'author', '4b3eb942-9b30-4677-9d97-21be3e8e6b55', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-22 22:37:27', '2025-07-29 22:37:27', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:37:28', 'SYSTEM', '2025-08-03 14:37:28', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (192, '从 反欺诈系统 到 UBI车险：一场 最大诚信 的 追溯 之旅', 1, 4, '2025-07-04 22:39:20', 'author', 'a53bfb6f-153e-4ada-a083-e43ecbb38ba9', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-04 22:39:20', '2025-07-05 22:39:20', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:39:21', 'SYSTEM', '2025-08-03 14:39:21', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (193, '从 保险中台 到 客户画像：一场 再保险 的 批改 之旅', 3, 2, '2025-07-22 22:39:20', 'author', '212160af-fb4e-4dc7-a6d8-805229e5cce8', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-26 22:39:20', '2025-07-30 22:39:20', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:39:21', 'SYSTEM', '2025-08-03 14:39:21', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (194, '保险API 与 数字化营销 的 场景化批改', 4, 1, '2025-07-24 22:39:21', 'author', 'b42d6a6c-f69a-43da-8cde-c3961de9da6f', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-29 22:39:21', '2025-08-02 22:39:21', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:39:21', 'SYSTEM', '2025-08-03 14:39:21', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (195, '从 AI风控 到 健康大数据：一场 大数法则 的 穿透 之旅', 5, 3, '2025-07-13 22:39:21', 'author', '034e53ef-f2b0-4e4f-8b83-59b04e6d4a40', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-16 22:39:21', '2025-07-22 22:39:21', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:39:21', 'SYSTEM', '2025-08-03 14:39:21', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (196, '风险对冲保险API：分保时代的保险云实践', 6, 1, '2025-08-03 22:39:21', 'author', '0559940d-217d-4b7a-acd4-b56da733eb18', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-07 22:39:21', '2025-08-10 22:39:21', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:39:21', 'SYSTEM', '2025-08-03 14:39:21', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (197, '损失补偿保险搜索引擎：保全时代的数字化营销实践', 7, 4, '2025-07-23 22:39:21', 'author', '19f2f7bd-2235-4d15-bbfd-556bf97f823f', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-26 22:39:21', '2025-07-29 22:39:21', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:39:21', 'SYSTEM', '2025-08-03 14:39:21', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (198, '从 保险API 到 理赔自动化：一场 场景化 的 穿透 之旅', 8, 5, '2025-07-21 22:39:21', 'author', '4d105b87-27e9-4747-8130-3759cdf9fd69', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-24 22:39:21', '2025-07-25 22:39:21', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:39:21', 'SYSTEM', '2025-08-03 14:39:21', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (199, '健康大数据 与 保险云 的 最大诚信承保', 9, 1, '2025-07-09 22:39:21', 'author', '29ed8bdf-2a98-4dfc-a1a6-517613134ed3', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-09 22:39:21', '2025-07-15 22:39:21', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:39:21', 'SYSTEM', '2025-08-03 14:39:21', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (200, '数字化营销 与 远程定损 的 风险加权护航', 10, 6, '2025-07-14 22:39:21', 'author', 'b5cee962-9151-4697-8eba-8ef6653a02a3', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-15 22:39:21', '2025-07-22 22:39:21', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:39:21', 'SYSTEM', '2025-08-03 14:39:21', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (201, '风险对冲客户画像：承保时代的健康大数据实践', 1, 6, '2025-07-09 22:40:03', 'author', 'f452d3a8-8b46-456b-832e-ec72d24b53f4', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-11 22:40:03', '2025-07-12 22:40:03', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:03', 'SYSTEM', '2025-08-03 14:40:03', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (202, '从 保险API 到 穿戴设备数据：一场 长尾效应 的 再保 之旅', 2, 6, '2025-07-05 22:40:03', 'author', '88b46138-1fec-4139-9c6c-f4ba2dcaf5ef', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-09 22:40:03', '2025-07-16 22:40:03', b'0', b'1', '{\"count\":1}', 2003, 'SYSTEM', '2025-08-03 14:40:03', 'SYSTEM', '2025-08-03 14:40:03', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (203, '保险大数据 与 保险云 的 损失补偿兜底', 3, 3, '2025-07-10 22:40:03', 'author', '9eb6f54d-3dc2-4b8e-823c-c24fecc9c74a', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-14 22:40:03', '2025-07-18 22:40:03', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:03', 'SYSTEM', '2025-08-03 14:40:03', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (204, '从 数字化营销 到 物联网保险：一场 损失补偿 的 追溯 之旅', 4, 6, '2025-07-24 22:40:03', 'author', '6bccdae0-a272-4ee2-95a6-1027954cfa30', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-28 22:40:03', '2025-07-29 22:40:03', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:03', 'SYSTEM', '2025-08-03 14:40:03', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (205, '从 区块链保单 到 数字化营销：一场 长尾效应 的 精算 之旅', 5, 5, '2025-07-20 22:40:03', 'author', '3bde4395-02b3-4a98-a7f4-e296362105ae', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-21 22:40:03', '2025-07-24 22:40:03', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:03', 'SYSTEM', '2025-08-03 14:40:03', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (206, '数字化营销 与 分布式保单 的 穿透式守护', 6, 2, '2025-07-08 22:40:03', 'author', '361fdcbf-4d58-49ad-8703-786cd0fb3ad7', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-12 22:40:03', '2025-07-14 22:40:03', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:03', 'SYSTEM', '2025-08-03 14:40:03', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (207, '场景化客户画像：保全时代的保险中台实践', 7, 5, '2025-07-04 22:40:03', 'author', 'e8534f8f-14a8-468a-9256-4807584b9ec8', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-04 22:40:03', '2025-07-11 22:40:03', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:03', 'SYSTEM', '2025-08-03 14:40:03', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (208, '保险API 与 微服务架构 的 不可抗辩共担', 8, 2, '2025-07-23 22:40:03', 'author', 'c3563fea-6aa1-4d3a-b6a5-cc862ea7b7d8', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-24 22:40:03', '2025-07-29 22:40:03', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:04', 'SYSTEM', '2025-08-03 14:40:04', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (209, '保险大数据 与 保险中台 的 大数法则风控', 9, 1, '2025-07-12 22:40:03', 'author', '24b4940e-9b4b-474f-93b6-0f45e460bafe', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-13 22:40:03', '2025-07-16 22:40:03', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:04', 'SYSTEM', '2025-08-03 14:40:04', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (210, '从 AI风控 到 保险API：一场 长尾效应 的 分保 之旅', 10, 5, '2025-07-24 22:40:03', 'author', '690e9e35-bfef-4c45-89c5-8acf816425a1', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-26 22:40:03', '2025-07-29 22:40:03', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:04', 'SYSTEM', '2025-08-03 14:40:04', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (211, '区块链保单 与 智能核保 的 长尾效应共保', 1, 3, '2025-08-02 22:40:05', 'author', '01784329-9b03-459f-b3ff-efa7d6553541', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-05 22:40:05', '2025-08-09 22:40:05', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:06', 'SYSTEM', '2025-08-03 14:40:06', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (212, '从 微服务架构 到 智能客服：一场 长尾效应 的 核保 之旅', 2, 5, '2025-07-20 22:40:05', 'author', '0844961d-7b74-4c3a-a80a-0c352cacdd35', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-22 22:40:05', '2025-07-23 22:40:05', b'0', b'1', '{\"count\":1}', 2002, 'SYSTEM', '2025-08-03 14:40:06', 'SYSTEM', '2025-08-03 14:40:06', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (213, '从 保险云 到 远程定损：一场 保险杠杆 的 托底 之旅', 3, 4, '2025-07-05 22:40:05', 'author', '734dcb94-db65-4d44-955f-ae8c2a550a1b', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-06 22:40:05', '2025-07-09 22:40:05', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:06', 'SYSTEM', '2025-08-03 14:40:06', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (214, '客户画像 与 保险搜索引擎 的 保险杠杆守护', 4, 4, '2025-07-17 22:40:05', 'author', '03f9d35d-cfa6-4a23-a56d-6b0aab09e182', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-22 22:40:05', '2025-07-29 22:40:05', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:06', 'SYSTEM', '2025-08-03 14:40:06', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (215, '共保体微服务架构：续期时代的穿戴设备数据实践', 5, 6, '2025-07-08 22:40:05', 'author', '618c4da1-6b52-47f3-8b67-ef30982454db', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-10 22:40:05', '2025-07-12 22:40:05', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:06', 'SYSTEM', '2025-08-03 14:40:06', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (216, '保险中台 与 UBI车险 的 深度科技理赔', 6, 5, '2025-07-16 22:40:05', 'author', '90978de2-53ad-415f-8fec-1d03b2fdffa1', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-19 22:40:05', '2025-07-20 22:40:05', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:06', 'SYSTEM', '2025-08-03 14:40:06', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (217, '客户画像 与 数字化营销 的 生态化批改', 7, 3, '2025-07-15 22:40:05', 'author', 'b0870b55-9d50-4a1f-9ca7-d593f374cb3a', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-19 22:40:05', '2025-07-20 22:40:05', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:06', 'SYSTEM', '2025-08-03 14:40:06', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (218, '不可抗辩保险大数据：兜底时代的智能核保实践', 8, 2, '2025-07-18 22:40:05', 'author', '7961c420-c491-47a0-a6f1-234f0ddcd34d', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-21 22:40:05', '2025-07-24 22:40:05', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:06', 'SYSTEM', '2025-08-03 14:40:06', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (219, '不可抗辩精算模型：托底时代的保险SaaS实践', 9, 4, '2025-07-22 22:40:05', 'author', '4c96f10a-1f56-4d46-94d1-f355f1a656d1', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-23 22:40:05', '2025-07-30 22:40:05', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:06', 'SYSTEM', '2025-08-03 14:40:06', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (220, '微服务架构 与 反欺诈系统 的 穿透式穿透', 10, 1, '2025-07-07 22:40:05', 'author', '34563def-b5f6-4484-a6ba-6f066504d410', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-09 22:40:05', '2025-07-11 22:40:05', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:06', 'SYSTEM', '2025-08-03 14:40:06', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (221, '精算模型 与 UBI车险 的 深度科技共保', 1, 4, '2025-07-28 22:40:07', 'author', 'afd72b38-1571-42a1-9632-9ec45126e802', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-01 22:40:07', '2025-08-08 22:40:07', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:08', 'SYSTEM', '2025-08-03 14:40:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (222, '从 客户画像 到 保险大数据：一场 深度科技 的 续期 之旅', 2, 5, '2025-07-12 22:40:07', 'author', '2cbd9563-6f62-4a64-ad25-d12f6994e4a8', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-14 22:40:07', '2025-07-18 22:40:07', b'0', b'1', '{\"count\":1}', 2004, 'SYSTEM', '2025-08-03 14:40:08', 'SYSTEM', '2025-08-03 14:40:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (223, '从 智能客服 到 保险中台：一场 长尾效应 的 守护 之旅', 3, 5, '2025-07-12 22:40:07', 'author', '27c4352a-82ef-44d6-a586-acdcf5391aed', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-14 22:40:07', '2025-07-18 22:40:07', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:08', 'SYSTEM', '2025-08-03 14:40:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (224, '共保体风险定价：风控时代的保险搜索引擎实践', 4, 2, '2025-08-02 22:40:07', 'author', '7badba8d-5e62-4239-a168-1a1a30db3709', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-07 22:40:07', '2025-08-08 22:40:07', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:08', 'SYSTEM', '2025-08-03 14:40:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (225, '保险云 与 客户画像 的 免赔额守护', 5, 5, '2025-07-06 22:40:08', 'author', 'a1dd479a-d9ca-4afd-929a-674880d05e13', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-09 22:40:08', '2025-07-11 22:40:08', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:08', 'SYSTEM', '2025-08-03 14:40:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (226, '从 风险定价 到 保险API：一场 长尾效应 的 守护 之旅', 6, 6, '2025-07-11 22:40:08', 'author', '5195aec1-0955-48d2-8aeb-88f8039542f5', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-14 22:40:08', '2025-07-15 22:40:08', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:08', 'SYSTEM', '2025-08-03 14:40:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (227, '从 保险云 到 保险搜索引擎：一场 长尾效应 的 共保 之旅', 7, 1, '2025-08-03 22:40:08', 'author', '3809aef7-63ce-4f10-93dd-90c64fe10ce5', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-06 22:40:08', '2025-08-13 22:40:08', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:08', 'SYSTEM', '2025-08-03 14:40:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (228, '再保险精算模型：预警时代的数字化营销实践', 8, 2, '2025-08-03 22:40:08', 'author', 'f869bf41-d791-457d-b767-7a0aa877324c', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-08 22:40:08', '2025-08-10 22:40:08', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:08', 'SYSTEM', '2025-08-03 14:40:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (229, '理赔自动化 与 智能核保 的 大数法则风控', 9, 6, '2025-07-08 22:40:08', 'author', '23d7640f-8323-4027-b70d-673b8cabd434', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-10 22:40:08', '2025-07-11 22:40:08', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:08', 'SYSTEM', '2025-08-03 14:40:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (230, '风险加权保险搜索引擎：护航时代的物联网保险实践', 10, 2, '2025-08-03 22:40:08', 'author', '69e9e625-31a4-4735-a6b4-c798f47a64f3', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-03 22:40:08', '2025-08-09 22:40:08', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:08', 'SYSTEM', '2025-08-03 14:40:08', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (231, '共保体保险云：续期时代的保险搜索引擎实践', 1, 2, '2025-07-07 22:40:09', 'author', '42b77c59-ca3c-4d74-a6d7-cd8b62458053', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-11 22:40:09', '2025-07-14 22:40:09', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:10', 'SYSTEM', '2025-08-03 14:40:10', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (232, '智能核保 与 风险定价 的 场景化再保', 2, 5, '2025-07-05 22:40:10', 'author', '3d0210c0-3085-4f76-bfcc-c6e0e720c2a9', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-09 22:40:10', '2025-07-16 22:40:10', b'0', b'1', '{\"count\":1}', 2003, 'SYSTEM', '2025-08-03 14:40:10', 'SYSTEM', '2025-08-03 14:40:10', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (233, '生态化分布式保单：续期时代的保险科技实践', 3, 1, '2025-07-16 22:40:10', 'author', '7e905571-bcc9-4f30-a301-892f12166f27', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-20 22:40:10', '2025-07-27 22:40:10', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:10', 'SYSTEM', '2025-08-03 14:40:10', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (234, '生态化反欺诈系统：续期时代的风险定价实践', 4, 3, '2025-07-11 22:40:10', 'author', 'c888d7ff-720e-4c77-9876-251d6f4bffc5', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-14 22:40:10', '2025-07-15 22:40:10', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:10', 'SYSTEM', '2025-08-03 14:40:10', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (235, '分布式保单 与 理赔自动化 的 损失补偿守护', 5, 2, '2025-07-17 22:40:10', 'author', 'b9415a3d-95b5-4048-af1a-7960ea5769ed', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-19 22:40:10', '2025-07-25 22:40:10', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:10', 'SYSTEM', '2025-08-03 14:40:10', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (236, '从 智能客服 到 客户画像：一场 深度科技 的 再保 之旅', 6, 4, '2025-07-31 22:40:10', 'author', '0c3ca5c3-c30a-4521-baf2-5f58752774e6', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-01 22:40:10', '2025-08-08 22:40:10', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:10', 'SYSTEM', '2025-08-03 14:40:10', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (237, '保险中台 与 微服务架构 的 保险杠杆精算', 7, 5, '2025-07-23 22:40:10', 'author', '6ac357d4-3ddd-465d-9cd4-c1e140c8e7af', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-24 22:40:10', '2025-07-27 22:40:10', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:10', 'SYSTEM', '2025-08-03 14:40:10', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (238, '风险加权理赔自动化：分保时代的保险中台实践', 8, 1, '2025-07-06 22:40:10', 'author', 'fe5bbb6a-3cbc-48bf-ba1e-3c59983bb8cf', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-11 22:40:10', '2025-07-17 22:40:10', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:10', 'SYSTEM', '2025-08-03 14:40:10', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (239, '偿付能力保险API：承保时代的反欺诈系统实践', 9, 6, '2025-07-11 22:40:10', 'author', '2f2e2fec-8c59-45a9-aebb-993592081907', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-13 22:40:10', '2025-07-16 22:40:10', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:10', 'SYSTEM', '2025-08-03 14:40:10', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (240, '从 AI风控 到 区块链保单：一场 偿付能力 的 穿透 之旅', 10, 5, '2025-07-27 22:40:10', 'author', '1e7ef4c2-1fad-4762-807c-c2e3a7098b25', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-29 22:40:10', '2025-07-30 22:40:10', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:10', 'SYSTEM', '2025-08-03 14:40:10', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (241, '场景化微服务架构：对冲时代的UBI车险实践', 1, 1, '2025-07-22 22:40:12', 'author', '6130a0b5-c6a5-419f-93c0-d91c1b9beb74', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-24 22:40:12', '2025-07-28 22:40:12', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:12', 'SYSTEM', '2025-08-03 14:40:12', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (242, '从 分布式保单 到 理赔自动化：一场 保险密度 的 再保 之旅', 2, 6, '2025-07-12 22:40:12', 'author', '2d2cab3c-db73-45d1-9913-582aa84a3110', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-12 22:40:12', '2025-07-17 22:40:12', b'0', b'1', '{\"count\":1}', 2001, 'SYSTEM', '2025-08-03 14:40:12', 'SYSTEM', '2025-08-03 14:40:12', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (243, '保险中台 与 理赔自动化 的 保险杠杆托底', 3, 1, '2025-07-27 22:40:12', 'author', '58c3b6b7-b2d4-4652-bb82-31288d1ddffe', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-31 22:40:12', '2025-08-05 22:40:12', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:12', 'SYSTEM', '2025-08-03 14:40:12', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (244, '从 智能核保 到 保险SaaS：一场 最大诚信 的 续期 之旅', 4, 3, '2025-07-04 22:40:12', 'author', '46be875b-46be-475f-808b-2838d17f829d', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-06 22:40:12', '2025-07-10 22:40:12', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:12', 'SYSTEM', '2025-08-03 14:40:12', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (245, '长尾效应分布式保单：批改时代的理赔自动化实践', 5, 1, '2025-07-08 22:40:12', 'author', '17520c68-edf9-4e73-9b84-0d97735be7cc', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-08 22:40:12', '2025-07-15 22:40:12', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:12', 'SYSTEM', '2025-08-03 14:40:12', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (246, '从 客户画像 到 保险搜索引擎：一场 损失补偿 的 追溯 之旅', 6, 4, '2025-07-10 22:40:12', 'author', '55069a2c-70b3-4593-b15a-33cbbca71f5d', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-15 22:40:12', '2025-07-16 22:40:12', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:12', 'SYSTEM', '2025-08-03 14:40:12', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (247, '从 保险搜索引擎 到 AI风控：一场 大数法则 的 对冲 之旅', 7, 4, '2025-07-21 22:40:12', 'author', '93482f2c-ea71-4931-b4a3-cf22008daa78', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-23 22:40:12', '2025-07-26 22:40:12', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:12', 'SYSTEM', '2025-08-03 14:40:12', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (248, '保险大数据 与 保险SaaS 的 保险密度再保', 8, 1, '2025-07-11 22:40:12', 'author', 'ce122d6f-fd73-4c5d-90d3-d20540ff9dc0', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-14 22:40:12', '2025-07-17 22:40:12', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:12', 'SYSTEM', '2025-08-03 14:40:12', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (249, '风险对冲UBI车险：批改时代的保险中台实践', 9, 3, '2025-07-21 22:40:12', 'author', '5e2a2124-0f72-42b4-a9a0-7ff2623005da', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-24 22:40:12', '2025-07-30 22:40:12', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:12', 'SYSTEM', '2025-08-03 14:40:12', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (250, '从 保险搜索引擎 到 保险大数据：一场 免赔额 的 再保 之旅', 10, 3, '2025-07-10 22:40:12', 'author', 'd3815109-e4ca-4e4c-88b2-012018e4d9b3', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-13 22:40:12', '2025-07-17 22:40:12', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:12', 'SYSTEM', '2025-08-03 14:40:12', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (251, '从 数字化营销 到 保险搜索引擎：一场 免赔额 的 分保 之旅', 1, 3, '2025-07-05 22:40:14', 'author', '48d4b910-be5b-4fe2-8126-435f676f42e1', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-07 22:40:14', '2025-07-11 22:40:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:14', 'SYSTEM', '2025-08-03 14:40:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (252, 'AI风控 与 智能客服 的 再保险共保', 2, 6, '2025-07-31 22:40:14', 'author', '4b088173-8419-483e-b4ca-d5534ed108e5', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-03 22:40:14', '2025-08-08 22:40:14', b'0', b'1', '{\"count\":1}', 2000, 'SYSTEM', '2025-08-03 14:40:14', 'SYSTEM', '2025-08-03 14:40:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (253, '保险API 与 保险大数据 的 深度科技续期', 3, 4, '2025-07-15 22:40:14', 'author', 'bb9af471-d1c0-4e0b-aacd-80f35c5b6431', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-18 22:40:14', '2025-07-24 22:40:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:14', 'SYSTEM', '2025-08-03 14:40:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (254, '客户画像 与 远程定损 的 风险加权共担', 4, 3, '2025-07-11 22:40:14', 'author', '362a7d38-8906-4197-b8f2-827d2d0a22f7', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-13 22:40:14', '2025-07-19 22:40:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:14', 'SYSTEM', '2025-08-03 14:40:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (255, '从 智能客服 到 保险中台：一场 不可抗辩 的 核保 之旅', 5, 1, '2025-07-25 22:40:14', 'author', 'b3ab2362-2c87-4b97-98e9-d0fe218f487d', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-26 22:40:14', '2025-08-01 22:40:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:14', 'SYSTEM', '2025-08-03 14:40:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (256, '从 反欺诈系统 到 物联网保险：一场 不可抗辩 的 兜底 之旅', 6, 2, '2025-07-25 22:40:14', 'author', '72433887-6690-47ac-ac19-16fc357ef8e5', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-30 22:40:14', '2025-08-02 22:40:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:14', 'SYSTEM', '2025-08-03 14:40:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (257, '大数法则数字化营销：守护时代的保险API实践', 7, 6, '2025-07-07 22:40:14', 'author', '57f54000-f0f3-429a-8bae-2498a8d558c4', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-08 22:40:14', '2025-07-15 22:40:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:14', 'SYSTEM', '2025-08-03 14:40:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (258, '从 客户画像 到 物联网保险：一场 穿透式 的 保全 之旅', 8, 6, '2025-07-24 22:40:14', 'author', '8d199ad0-3593-4654-a020-9808d45b1fe8', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-24 22:40:14', '2025-07-31 22:40:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:14', 'SYSTEM', '2025-08-03 14:40:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (259, '智能客服 与 精算模型 的 等待期托底', 9, 6, '2025-07-31 22:40:14', 'author', '38ba5ad0-3cc9-40a3-a32c-e39c26f24e1d', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-05 22:40:14', '2025-08-06 22:40:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:14', 'SYSTEM', '2025-08-03 14:40:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (260, '生态化保险科技：共保时代的保险大数据实践', 10, 5, '2025-07-20 22:40:14', 'author', '4b2f3f48-52ff-4b92-a5e0-5f1c6bb722d4', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-21 22:40:14', '2025-07-23 22:40:14', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:14', 'SYSTEM', '2025-08-03 14:40:14', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (261, '损失补偿穿戴设备数据：理赔时代的保险科技实践', 1, 5, '2025-07-15 22:40:16', 'author', '471456d9-74e1-4a95-b2f2-30d137ea3a40', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-15 22:40:16', '2025-07-16 22:40:16', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:16', 'SYSTEM', '2025-08-03 14:40:16', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (262, '理赔自动化 与 UBI车险 的 大数法则追溯', 2, 4, '2025-08-01 22:40:16', 'author', '4b18c6f3-7ac6-4ff9-82c7-3005719a178e', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-05 22:40:16', '2025-08-08 22:40:16', b'0', b'1', '{\"count\":1}', 2000, 'SYSTEM', '2025-08-03 14:40:16', 'SYSTEM', '2025-08-03 14:40:16', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (263, '共保体保险SaaS：护航时代的微服务架构实践', 3, 3, '2025-07-24 22:40:16', 'author', 'd0df9056-3a9f-418f-882d-8a50aad882a0', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-29 22:40:16', '2025-08-01 22:40:16', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:16', 'SYSTEM', '2025-08-03 14:40:16', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (264, '客户画像 与 理赔自动化 的 场景化续期', 4, 2, '2025-07-29 22:40:16', 'author', '82fdf826-ca83-4f52-a377-bb2931b0d2cd', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-30 22:40:16', '2025-08-06 22:40:16', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:16', 'SYSTEM', '2025-08-03 14:40:16', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (265, '客户画像 与 微服务架构 的 免赔额共保', 5, 5, '2025-07-09 22:40:16', 'author', 'b67cb1bf-e36f-420c-8bb2-e32e558c5afb', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-09 22:40:16', '2025-07-16 22:40:16', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:16', 'SYSTEM', '2025-08-03 14:40:16', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (266, '保险杠杆精算模型：续期时代的保险搜索引擎实践', 6, 5, '2025-08-02 22:40:16', 'author', '52866998-e79e-40ac-8d23-e5ca5b427ccd', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-07 22:40:16', '2025-08-13 22:40:16', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:16', 'SYSTEM', '2025-08-03 14:40:16', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (267, '区块链保单 与 风险定价 的 偿付能力穿透', 7, 1, '2025-07-10 22:40:16', 'author', 'ac3a0547-36a1-4ee7-a2b4-3aad2d309b1e', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-14 22:40:16', '2025-07-21 22:40:16', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:16', 'SYSTEM', '2025-08-03 14:40:16', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (268, '偿付能力风险定价：风控时代的保险搜索引擎实践', 8, 6, '2025-07-22 22:40:16', 'author', '8d6d27b7-6217-4f8e-a3f1-9ec12ad03d59', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-26 22:40:16', '2025-07-29 22:40:16', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:16', 'SYSTEM', '2025-08-03 14:40:16', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (269, '从 数字化营销 到 保险API：一场 免赔额 的 分保 之旅', 9, 2, '2025-07-19 22:40:16', 'author', 'b29b2e79-6c7c-454d-9c7e-b6d21710d2ee', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-20 22:40:16', '2025-07-24 22:40:16', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:16', 'SYSTEM', '2025-08-03 14:40:16', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (270, '远程定损 与 分布式保单 的 保险密度护航', 10, 3, '2025-08-03 22:40:16', 'author', '4fee73b4-7475-4e89-9540-dbbdc7a4ab69', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-04 22:40:16', '2025-08-05 22:40:16', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:16', 'SYSTEM', '2025-08-03 14:40:16', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (271, '大数法则保险云：续期时代的物联网保险实践', 1, 5, '2025-07-07 22:40:18', 'author', 'eaf58f72-7e06-49bb-ad1b-c78e3bb2be99', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-11 22:40:18', '2025-07-18 22:40:18', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:18', 'SYSTEM', '2025-08-03 14:40:18', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (272, '从 AI风控 到 保险科技：一场 共保体 的 预警 之旅', 2, 5, '2025-07-11 22:40:18', 'author', 'e3d1a8ae-9300-45b4-ad5b-e81735c55322', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-16 22:40:18', '2025-07-23 22:40:18', b'0', b'1', '{\"count\":1}', 2003, 'SYSTEM', '2025-08-03 14:40:18', 'SYSTEM', '2025-08-03 14:40:18', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (273, '深度科技健康大数据：守护时代的保险SaaS实践', 3, 1, '2025-07-27 22:40:18', 'author', 'bd53b992-052c-4e1c-a2b9-2779ff1091b9', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-27 22:40:18', '2025-08-01 22:40:18', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:18', 'SYSTEM', '2025-08-03 14:40:18', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (274, 'AI风控 与 物联网保险 的 保险杠杆批改', 4, 4, '2025-07-16 22:40:18', 'author', '932fec5d-62af-4d02-b6bc-ef129a74d41c', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-19 22:40:18', '2025-07-21 22:40:18', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:18', 'SYSTEM', '2025-08-03 14:40:18', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (275, '风险对冲客户画像：守护时代的健康大数据实践', 5, 2, '2025-07-31 22:40:18', 'author', '301671f7-2280-4222-8de2-4f6d6647eb49', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-04 22:40:18', '2025-08-11 22:40:18', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:18', 'SYSTEM', '2025-08-03 14:40:18', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (276, '长尾效应保险SaaS：预警时代的健康大数据实践', 6, 3, '2025-07-23 22:40:18', 'author', '93e738b9-0430-4fda-9e06-9286d1d6cbe3', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-25 22:40:18', '2025-07-27 22:40:18', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:18', 'SYSTEM', '2025-08-03 14:40:18', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (277, '分布式保单 与 保险搜索引擎 的 大数法则分保', 7, 2, '2025-07-09 22:40:18', 'author', 'ec269df3-ccac-4ad7-8950-8d45400b009f', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-11 22:40:18', '2025-07-14 22:40:18', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:18', 'SYSTEM', '2025-08-03 14:40:18', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (278, '从 保险云 到 物联网保险：一场 深度科技 的 追溯 之旅', 8, 2, '2025-07-29 22:40:18', 'author', '88cd7cf9-02bf-40f2-85c6-dfe5a60670b6', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-30 22:40:18', '2025-08-04 22:40:18', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:18', 'SYSTEM', '2025-08-03 14:40:18', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (279, '保险搜索引擎 与 风险定价 的 风险对冲守护', 9, 5, '2025-07-18 22:40:18', 'author', '6a370502-2658-4943-bc76-ea77c098046c', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-18 22:40:18', '2025-07-25 22:40:18', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:18', 'SYSTEM', '2025-08-03 14:40:18', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (280, '大数法则保险大数据：承保时代的远程定损实践', 10, 2, '2025-08-03 22:40:18', 'author', '769217c1-6a7a-4c2b-9a9d-184d6c41cd1a', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-07 22:40:18', '2025-08-08 22:40:18', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 14:40:18', 'SYSTEM', '2025-08-03 14:40:18', NULL, b'0', b'0', NULL);
INSERT INTO `content` VALUES (281, '生态化AI风控：风控时代的微服务架构实践', 1, 3, '2025-07-31 23:05:56', 'author', 'e6f9c0e9-27e8-4927-ada4-01867e117331', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-05 23:05:56', '2025-08-08 23:05:56', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 15:05:57', 'SYSTEM', '2025-08-03 15:05:57', '来源', b'0', b'0', '2025-09-08 23:05:56');
INSERT INTO `content` VALUES (282, '从 反欺诈系统 到 精算模型：一场 再保险 的 对冲 之旅', 2, 4, '2025-07-09 23:05:57', 'author', 'ca669986-9fc3-4693-b8d4-6ba877ded635', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-11 23:05:57', '2025-07-16 23:05:57', b'0', b'1', '{\"count\":1}', 2001, 'SYSTEM', '2025-08-03 15:05:57', 'SYSTEM', '2025-08-03 15:05:57', '来源', b'0', b'0', '2025-07-23 23:05:57');
INSERT INTO `content` VALUES (283, '从 保险API 到 理赔自动化：一场 场景化 的 共保 之旅', 3, 4, '2025-07-24 23:05:57', 'author', '8cf63c06-65b1-4813-97eb-c006581fbbf8', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-26 23:05:57', '2025-07-30 23:05:57', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 15:05:57', 'SYSTEM', '2025-08-03 15:05:57', '来源', b'0', b'0', '2025-09-15 23:05:57');
INSERT INTO `content` VALUES (284, '从 健康大数据 到 分布式保单：一场 生态化 的 承保 之旅', 4, 2, '2025-07-11 23:05:57', 'author', '439b5bea-bff8-4362-86e2-641a07a39587', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-11 23:05:57', '2025-07-18 23:05:57', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 15:05:57', 'SYSTEM', '2025-08-03 15:05:57', '来源', b'0', b'0', '2025-09-01 23:05:57');
INSERT INTO `content` VALUES (285, '从 风险定价 到 分布式保单：一场 保险杠杆 的 精算 之旅', 5, 5, '2025-07-14 23:05:57', 'author', '1358de55-4b92-4b05-8704-a46ac5c9736d', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-14 23:05:57', '2025-07-19 23:05:57', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 15:05:57', 'SYSTEM', '2025-08-03 15:05:57', '来源', b'0', b'0', '2025-09-06 23:05:57');
INSERT INTO `content` VALUES (286, '从 精算模型 到 分布式保单：一场 等待期 的 理赔 之旅', 6, 2, '2025-07-12 23:05:57', 'author', '90db60bd-dae1-4d2f-8196-cac72ad0acdb', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-12 23:05:57', '2025-07-18 23:05:57', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 15:05:57', 'SYSTEM', '2025-08-03 15:05:57', '来源', b'0', b'0', '2025-08-03 23:05:57');
INSERT INTO `content` VALUES (287, '区块链保单 与 分布式保单 的 风险对冲再保', 7, 4, '2025-07-17 23:05:57', 'author', 'c917e1c0-7619-4880-8721-94ffebc15329', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-21 23:05:57', '2025-07-25 23:05:57', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 15:05:57', 'SYSTEM', '2025-08-03 15:05:57', '来源', b'0', b'0', '2025-09-19 23:05:57');
INSERT INTO `content` VALUES (288, 'UBI车险 与 数字化营销 的 穿透式兜底', 8, 2, '2025-07-11 23:05:57', 'author', '69974538-755a-4665-8b59-643057c1b740', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-16 23:05:57', '2025-07-17 23:05:57', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 15:05:57', 'SYSTEM', '2025-08-03 15:05:57', '来源', b'0', b'0', '2025-09-07 23:05:57');
INSERT INTO `content` VALUES (289, '最大诚信分布式保单：兜底时代的数字化营销实践', 9, 3, '2025-07-05 23:05:57', 'author', 'c33f3067-a2d8-41ee-a338-0b9cb4c85da3', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-07-07 23:05:57', '2025-07-09 23:05:57', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 15:05:57', 'SYSTEM', '2025-08-03 15:05:57', '来源', b'0', b'0', '2025-08-28 23:05:57');
INSERT INTO `content` VALUES (290, '远程定损 与 AI风控 的 风险加权保全', 10, 5, '2025-08-03 23:05:57', 'author', 'd608f65e-e63c-4077-bbcd-cec6716e92c5', 'www.baidu.com', 1, 1, 'content', '我很关键啊', 'code', 'taskCode', -1, 0, 0, b'0', '2025-08-08 23:05:57', '2025-08-15 23:05:57', b'0', b'1', '{\"count\":1}', 0, 'SYSTEM', '2025-08-03 15:05:57', 'SYSTEM', '2025-08-03 15:05:57', '来源', b'0', b'0', '2025-09-30 23:05:57');

-- ----------------------------
-- Table structure for content_article
-- ----------------------------
DROP TABLE IF EXISTS `content_article`;
CREATE TABLE `content_article`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `summary` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `reprint_declaration` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `applied_to_scenario_type` tinyint NULL DEFAULT 1,
  `publisher` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `small_image_url_reference` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 282 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '内容文章表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_article
-- ----------------------------
INSERT INTO `content_article` VALUES (9, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-07-30 10:15:19', 'SYSTEM', '2025-07-30 10:15:19', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (19, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-07-30 10:19:09', 'SYSTEM', '2025-07-30 10:19:09', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (29, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-07-30 10:21:08', 'SYSTEM', '2025-07-30 10:21:08', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (39, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-07-30 10:22:28', 'SYSTEM', '2025-07-30 10:22:28', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (49, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-07-30 10:23:32', 'SYSTEM', '2025-07-30 10:23:32', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (59, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-07-30 12:11:57', 'SYSTEM', '2025-07-30 12:11:57', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (164, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-08-03 14:33:06', 'SYSTEM', '2025-08-03 14:33:06', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (165, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-08-03 14:35:14', 'SYSTEM', '2025-08-03 14:35:14', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (174, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-08-03 14:36:46', 'SYSTEM', '2025-08-03 14:36:46', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (183, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-08-03 14:36:57', 'SYSTEM', '2025-08-03 14:36:57', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (192, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-08-03 14:39:21', 'SYSTEM', '2025-08-03 14:39:21', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (201, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-08-03 14:40:03', 'SYSTEM', '2025-08-03 14:40:03', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (211, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-08-03 14:40:06', 'SYSTEM', '2025-08-03 14:40:06', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (221, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-08-03 14:40:08', 'SYSTEM', '2025-08-03 14:40:08', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (231, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-08-03 14:40:10', 'SYSTEM', '2025-08-03 14:40:10', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (241, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-08-03 14:40:12', 'SYSTEM', '2025-08-03 14:40:12', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (251, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-08-03 14:40:14', 'SYSTEM', '2025-08-03 14:40:14', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (261, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-08-03 14:40:16', 'SYSTEM', '2025-08-03 14:40:16', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (271, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-08-03 14:40:18', 'SYSTEM', '2025-08-03 14:40:18', 1, NULL, NULL);
INSERT INTO `content_article` VALUES (281, 'Sample summary', 'Sample declaration', 'SYSTEM', '2025-08-03 15:05:57', 'SYSTEM', '2025-08-03 15:05:57', 1, 'SYSYSY', 'https://www.example.com/small.jpg');

-- ----------------------------
-- Table structure for content_book
-- ----------------------------
DROP TABLE IF EXISTS `content_book`;
CREATE TABLE `content_book`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `type` tinyint NOT NULL COMMENT '类型',
  `cover_image_reference_mode` tinyint NOT NULL COMMENT '封面图引用方式',
  `cover_image_reference` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图书封面引用方式',
  `content_reference_mode` tinyint NOT NULL COMMENT '图书正文引用方式',
  `content_reference` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '图书正文',
  `isbn` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '地址',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `press` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图书表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_book
-- ----------------------------

-- ----------------------------
-- Table structure for content_content_tag
-- ----------------------------
DROP TABLE IF EXISTS `content_content_tag`;
CREATE TABLE `content_content_tag`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content_id` bigint UNSIGNED NOT NULL COMMENT '内容ID',
  `tag_id` bigint UNSIGNED NOT NULL COMMENT '标签ID',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_tag_id`(`tag_id` ASC) USING BTREE,
  UNIQUE INDEX `uk_content_id_tag_id`(`content_id` ASC, `tag_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 799 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '内容标签关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_content_tag
-- ----------------------------
INSERT INTO `content_content_tag` VALUES (1, 164, 308, 'SYSTEM', '2025-08-03 14:32:53.226', 'SYSTEM', '2025-08-03 14:32:53.226');
INSERT INTO `content_content_tag` VALUES (2, 164, 94, 'SYSTEM', '2025-08-03 14:32:53.226', 'SYSTEM', '2025-08-03 14:32:53.226');
INSERT INTO `content_content_tag` VALUES (3, 164, 39, 'SYSTEM', '2025-08-03 14:32:53.226', 'SYSTEM', '2025-08-03 14:32:53.226');
INSERT INTO `content_content_tag` VALUES (4, 164, 72, 'SYSTEM', '2025-08-03 14:32:53.226', 'SYSTEM', '2025-08-03 14:32:53.226');
INSERT INTO `content_content_tag` VALUES (5, 164, 253, 'SYSTEM', '2025-08-03 14:32:53.226', 'SYSTEM', '2025-08-03 14:32:53.226');
INSERT INTO `content_content_tag` VALUES (6, 164, 354, 'SYSTEM', '2025-08-03 14:32:53.226', 'SYSTEM', '2025-08-03 14:32:53.226');
INSERT INTO `content_content_tag` VALUES (7, 164, 385, 'SYSTEM', '2025-08-03 14:32:53.226', 'SYSTEM', '2025-08-03 14:32:53.226');
INSERT INTO `content_content_tag` VALUES (8, 165, 109, 'SYSTEM', '2025-08-03 14:35:14.002', 'SYSTEM', '2025-08-03 14:35:14.002');
INSERT INTO `content_content_tag` VALUES (9, 165, 269, 'SYSTEM', '2025-08-03 14:35:14.002', 'SYSTEM', '2025-08-03 14:35:14.002');
INSERT INTO `content_content_tag` VALUES (10, 165, 225, 'SYSTEM', '2025-08-03 14:35:14.002', 'SYSTEM', '2025-08-03 14:35:14.002');
INSERT INTO `content_content_tag` VALUES (11, 165, 317, 'SYSTEM', '2025-08-03 14:35:14.002', 'SYSTEM', '2025-08-03 14:35:14.002');
INSERT INTO `content_content_tag` VALUES (12, 165, 30, 'SYSTEM', '2025-08-03 14:35:14.002', 'SYSTEM', '2025-08-03 14:35:14.002');
INSERT INTO `content_content_tag` VALUES (13, 165, 373, 'SYSTEM', '2025-08-03 14:35:14.002', 'SYSTEM', '2025-08-03 14:35:14.002');
INSERT INTO `content_content_tag` VALUES (14, 165, 386, 'SYSTEM', '2025-08-03 14:35:14.002', 'SYSTEM', '2025-08-03 14:35:14.002');
INSERT INTO `content_content_tag` VALUES (15, 166, 66, 'SYSTEM', '2025-08-03 14:35:14.078', 'SYSTEM', '2025-08-03 14:35:14.078');
INSERT INTO `content_content_tag` VALUES (16, 166, 214, 'SYSTEM', '2025-08-03 14:35:14.078', 'SYSTEM', '2025-08-03 14:35:14.078');
INSERT INTO `content_content_tag` VALUES (17, 166, 78, 'SYSTEM', '2025-08-03 14:35:14.078', 'SYSTEM', '2025-08-03 14:35:14.078');
INSERT INTO `content_content_tag` VALUES (18, 166, 83, 'SYSTEM', '2025-08-03 14:35:14.078', 'SYSTEM', '2025-08-03 14:35:14.078');
INSERT INTO `content_content_tag` VALUES (19, 166, 208, 'SYSTEM', '2025-08-03 14:35:14.078', 'SYSTEM', '2025-08-03 14:35:14.078');
INSERT INTO `content_content_tag` VALUES (20, 166, 359, 'SYSTEM', '2025-08-03 14:35:14.078', 'SYSTEM', '2025-08-03 14:35:14.078');
INSERT INTO `content_content_tag` VALUES (21, 166, 387, 'SYSTEM', '2025-08-03 14:35:14.078', 'SYSTEM', '2025-08-03 14:35:14.078');
INSERT INTO `content_content_tag` VALUES (22, 167, 28, 'SYSTEM', '2025-08-03 14:35:14.122', 'SYSTEM', '2025-08-03 14:35:14.122');
INSERT INTO `content_content_tag` VALUES (23, 167, 277, 'SYSTEM', '2025-08-03 14:35:14.122', 'SYSTEM', '2025-08-03 14:35:14.122');
INSERT INTO `content_content_tag` VALUES (24, 167, 216, 'SYSTEM', '2025-08-03 14:35:14.122', 'SYSTEM', '2025-08-03 14:35:14.122');
INSERT INTO `content_content_tag` VALUES (25, 167, 180, 'SYSTEM', '2025-08-03 14:35:14.123', 'SYSTEM', '2025-08-03 14:35:14.123');
INSERT INTO `content_content_tag` VALUES (26, 167, 102, 'SYSTEM', '2025-08-03 14:35:14.123', 'SYSTEM', '2025-08-03 14:35:14.123');
INSERT INTO `content_content_tag` VALUES (27, 167, 376, 'SYSTEM', '2025-08-03 14:35:14.123', 'SYSTEM', '2025-08-03 14:35:14.123');
INSERT INTO `content_content_tag` VALUES (28, 167, 384, 'SYSTEM', '2025-08-03 14:35:14.123', 'SYSTEM', '2025-08-03 14:35:14.123');
INSERT INTO `content_content_tag` VALUES (29, 168, 251, 'SYSTEM', '2025-08-03 14:35:14.151', 'SYSTEM', '2025-08-03 14:35:14.151');
INSERT INTO `content_content_tag` VALUES (30, 168, 177, 'SYSTEM', '2025-08-03 14:35:14.151', 'SYSTEM', '2025-08-03 14:35:14.151');
INSERT INTO `content_content_tag` VALUES (31, 168, 151, 'SYSTEM', '2025-08-03 14:35:14.151', 'SYSTEM', '2025-08-03 14:35:14.151');
INSERT INTO `content_content_tag` VALUES (32, 168, 76, 'SYSTEM', '2025-08-03 14:35:14.151', 'SYSTEM', '2025-08-03 14:35:14.151');
INSERT INTO `content_content_tag` VALUES (33, 168, 180, 'SYSTEM', '2025-08-03 14:35:14.151', 'SYSTEM', '2025-08-03 14:35:14.151');
INSERT INTO `content_content_tag` VALUES (34, 168, 365, 'SYSTEM', '2025-08-03 14:35:14.151', 'SYSTEM', '2025-08-03 14:35:14.151');
INSERT INTO `content_content_tag` VALUES (35, 168, 384, 'SYSTEM', '2025-08-03 14:35:14.151', 'SYSTEM', '2025-08-03 14:35:14.151');
INSERT INTO `content_content_tag` VALUES (36, 169, 124, 'SYSTEM', '2025-08-03 14:35:14.182', 'SYSTEM', '2025-08-03 14:35:14.182');
INSERT INTO `content_content_tag` VALUES (37, 169, 102, 'SYSTEM', '2025-08-03 14:35:14.182', 'SYSTEM', '2025-08-03 14:35:14.182');
INSERT INTO `content_content_tag` VALUES (38, 169, 312, 'SYSTEM', '2025-08-03 14:35:14.182', 'SYSTEM', '2025-08-03 14:35:14.182');
INSERT INTO `content_content_tag` VALUES (39, 169, 73, 'SYSTEM', '2025-08-03 14:35:14.182', 'SYSTEM', '2025-08-03 14:35:14.182');
INSERT INTO `content_content_tag` VALUES (40, 169, 98, 'SYSTEM', '2025-08-03 14:35:14.182', 'SYSTEM', '2025-08-03 14:35:14.182');
INSERT INTO `content_content_tag` VALUES (41, 169, 365, 'SYSTEM', '2025-08-03 14:35:14.183', 'SYSTEM', '2025-08-03 14:35:14.183');
INSERT INTO `content_content_tag` VALUES (42, 169, 385, 'SYSTEM', '2025-08-03 14:35:14.183', 'SYSTEM', '2025-08-03 14:35:14.183');
INSERT INTO `content_content_tag` VALUES (43, 171, 128, 'SYSTEM', '2025-08-03 14:35:14.235', 'SYSTEM', '2025-08-03 14:35:14.235');
INSERT INTO `content_content_tag` VALUES (44, 171, 243, 'SYSTEM', '2025-08-03 14:35:14.235', 'SYSTEM', '2025-08-03 14:35:14.235');
INSERT INTO `content_content_tag` VALUES (45, 171, 80, 'SYSTEM', '2025-08-03 14:35:14.235', 'SYSTEM', '2025-08-03 14:35:14.235');
INSERT INTO `content_content_tag` VALUES (46, 171, 95, 'SYSTEM', '2025-08-03 14:35:14.235', 'SYSTEM', '2025-08-03 14:35:14.235');
INSERT INTO `content_content_tag` VALUES (47, 171, 21, 'SYSTEM', '2025-08-03 14:35:14.235', 'SYSTEM', '2025-08-03 14:35:14.235');
INSERT INTO `content_content_tag` VALUES (48, 171, 367, 'SYSTEM', '2025-08-03 14:35:14.235', 'SYSTEM', '2025-08-03 14:35:14.235');
INSERT INTO `content_content_tag` VALUES (49, 171, 381, 'SYSTEM', '2025-08-03 14:35:14.236', 'SYSTEM', '2025-08-03 14:35:14.236');
INSERT INTO `content_content_tag` VALUES (50, 172, 77, 'SYSTEM', '2025-08-03 14:35:14.276', 'SYSTEM', '2025-08-03 14:35:14.276');
INSERT INTO `content_content_tag` VALUES (51, 172, 202, 'SYSTEM', '2025-08-03 14:35:14.276', 'SYSTEM', '2025-08-03 14:35:14.276');
INSERT INTO `content_content_tag` VALUES (52, 172, 52, 'SYSTEM', '2025-08-03 14:35:14.276', 'SYSTEM', '2025-08-03 14:35:14.276');
INSERT INTO `content_content_tag` VALUES (53, 172, 67, 'SYSTEM', '2025-08-03 14:35:14.276', 'SYSTEM', '2025-08-03 14:35:14.276');
INSERT INTO `content_content_tag` VALUES (54, 172, 131, 'SYSTEM', '2025-08-03 14:35:14.276', 'SYSTEM', '2025-08-03 14:35:14.276');
INSERT INTO `content_content_tag` VALUES (55, 172, 361, 'SYSTEM', '2025-08-03 14:35:14.276', 'SYSTEM', '2025-08-03 14:35:14.276');
INSERT INTO `content_content_tag` VALUES (56, 172, 381, 'SYSTEM', '2025-08-03 14:35:14.276', 'SYSTEM', '2025-08-03 14:35:14.276');
INSERT INTO `content_content_tag` VALUES (57, 173, 208, 'SYSTEM', '2025-08-03 14:35:14.293', 'SYSTEM', '2025-08-03 14:35:14.293');
INSERT INTO `content_content_tag` VALUES (58, 173, 310, 'SYSTEM', '2025-08-03 14:35:14.293', 'SYSTEM', '2025-08-03 14:35:14.293');
INSERT INTO `content_content_tag` VALUES (59, 173, 161, 'SYSTEM', '2025-08-03 14:35:14.293', 'SYSTEM', '2025-08-03 14:35:14.293');
INSERT INTO `content_content_tag` VALUES (60, 173, 221, 'SYSTEM', '2025-08-03 14:35:14.293', 'SYSTEM', '2025-08-03 14:35:14.293');
INSERT INTO `content_content_tag` VALUES (61, 173, 149, 'SYSTEM', '2025-08-03 14:35:14.293', 'SYSTEM', '2025-08-03 14:35:14.293');
INSERT INTO `content_content_tag` VALUES (62, 173, 357, 'SYSTEM', '2025-08-03 14:35:14.293', 'SYSTEM', '2025-08-03 14:35:14.293');
INSERT INTO `content_content_tag` VALUES (63, 173, 385, 'SYSTEM', '2025-08-03 14:35:14.293', 'SYSTEM', '2025-08-03 14:35:14.293');
INSERT INTO `content_content_tag` VALUES (64, 174, 214, 'SYSTEM', '2025-08-03 14:36:45.966', 'SYSTEM', '2025-08-03 14:36:45.966');
INSERT INTO `content_content_tag` VALUES (65, 174, 166, 'SYSTEM', '2025-08-03 14:36:45.966', 'SYSTEM', '2025-08-03 14:36:45.966');
INSERT INTO `content_content_tag` VALUES (66, 174, 309, 'SYSTEM', '2025-08-03 14:36:45.966', 'SYSTEM', '2025-08-03 14:36:45.966');
INSERT INTO `content_content_tag` VALUES (67, 174, 46, 'SYSTEM', '2025-08-03 14:36:45.966', 'SYSTEM', '2025-08-03 14:36:45.966');
INSERT INTO `content_content_tag` VALUES (68, 174, 281, 'SYSTEM', '2025-08-03 14:36:45.966', 'SYSTEM', '2025-08-03 14:36:45.966');
INSERT INTO `content_content_tag` VALUES (69, 174, 358, 'SYSTEM', '2025-08-03 14:36:45.966', 'SYSTEM', '2025-08-03 14:36:45.966');
INSERT INTO `content_content_tag` VALUES (70, 174, 386, 'SYSTEM', '2025-08-03 14:36:45.966', 'SYSTEM', '2025-08-03 14:36:45.966');
INSERT INTO `content_content_tag` VALUES (71, 175, 207, 'SYSTEM', '2025-08-03 14:36:46.012', 'SYSTEM', '2025-08-03 14:36:46.012');
INSERT INTO `content_content_tag` VALUES (72, 175, 239, 'SYSTEM', '2025-08-03 14:36:46.012', 'SYSTEM', '2025-08-03 14:36:46.012');
INSERT INTO `content_content_tag` VALUES (73, 175, 54, 'SYSTEM', '2025-08-03 14:36:46.012', 'SYSTEM', '2025-08-03 14:36:46.012');
INSERT INTO `content_content_tag` VALUES (74, 175, 278, 'SYSTEM', '2025-08-03 14:36:46.012', 'SYSTEM', '2025-08-03 14:36:46.012');
INSERT INTO `content_content_tag` VALUES (75, 175, 270, 'SYSTEM', '2025-08-03 14:36:46.012', 'SYSTEM', '2025-08-03 14:36:46.012');
INSERT INTO `content_content_tag` VALUES (76, 175, 353, 'SYSTEM', '2025-08-03 14:36:46.012', 'SYSTEM', '2025-08-03 14:36:46.012');
INSERT INTO `content_content_tag` VALUES (77, 175, 383, 'SYSTEM', '2025-08-03 14:36:46.012', 'SYSTEM', '2025-08-03 14:36:46.012');
INSERT INTO `content_content_tag` VALUES (78, 176, 265, 'SYSTEM', '2025-08-03 14:36:46.043', 'SYSTEM', '2025-08-03 14:36:46.043');
INSERT INTO `content_content_tag` VALUES (79, 176, 258, 'SYSTEM', '2025-08-03 14:36:46.043', 'SYSTEM', '2025-08-03 14:36:46.043');
INSERT INTO `content_content_tag` VALUES (80, 176, 334, 'SYSTEM', '2025-08-03 14:36:46.043', 'SYSTEM', '2025-08-03 14:36:46.043');
INSERT INTO `content_content_tag` VALUES (81, 176, 287, 'SYSTEM', '2025-08-03 14:36:46.043', 'SYSTEM', '2025-08-03 14:36:46.043');
INSERT INTO `content_content_tag` VALUES (82, 176, 243, 'SYSTEM', '2025-08-03 14:36:46.043', 'SYSTEM', '2025-08-03 14:36:46.043');
INSERT INTO `content_content_tag` VALUES (83, 176, 380, 'SYSTEM', '2025-08-03 14:36:46.043', 'SYSTEM', '2025-08-03 14:36:46.043');
INSERT INTO `content_content_tag` VALUES (84, 176, 382, 'SYSTEM', '2025-08-03 14:36:46.043', 'SYSTEM', '2025-08-03 14:36:46.043');
INSERT INTO `content_content_tag` VALUES (85, 177, 323, 'SYSTEM', '2025-08-03 14:36:46.075', 'SYSTEM', '2025-08-03 14:36:46.075');
INSERT INTO `content_content_tag` VALUES (86, 177, 257, 'SYSTEM', '2025-08-03 14:36:46.075', 'SYSTEM', '2025-08-03 14:36:46.075');
INSERT INTO `content_content_tag` VALUES (87, 177, 109, 'SYSTEM', '2025-08-03 14:36:46.075', 'SYSTEM', '2025-08-03 14:36:46.075');
INSERT INTO `content_content_tag` VALUES (88, 177, 280, 'SYSTEM', '2025-08-03 14:36:46.075', 'SYSTEM', '2025-08-03 14:36:46.075');
INSERT INTO `content_content_tag` VALUES (89, 177, 225, 'SYSTEM', '2025-08-03 14:36:46.075', 'SYSTEM', '2025-08-03 14:36:46.075');
INSERT INTO `content_content_tag` VALUES (90, 177, 376, 'SYSTEM', '2025-08-03 14:36:46.075', 'SYSTEM', '2025-08-03 14:36:46.075');
INSERT INTO `content_content_tag` VALUES (91, 177, 383, 'SYSTEM', '2025-08-03 14:36:46.075', 'SYSTEM', '2025-08-03 14:36:46.075');
INSERT INTO `content_content_tag` VALUES (92, 178, 119, 'SYSTEM', '2025-08-03 14:36:46.105', 'SYSTEM', '2025-08-03 14:36:46.105');
INSERT INTO `content_content_tag` VALUES (93, 178, 20, 'SYSTEM', '2025-08-03 14:36:46.105', 'SYSTEM', '2025-08-03 14:36:46.105');
INSERT INTO `content_content_tag` VALUES (94, 178, 312, 'SYSTEM', '2025-08-03 14:36:46.105', 'SYSTEM', '2025-08-03 14:36:46.105');
INSERT INTO `content_content_tag` VALUES (95, 178, 203, 'SYSTEM', '2025-08-03 14:36:46.105', 'SYSTEM', '2025-08-03 14:36:46.105');
INSERT INTO `content_content_tag` VALUES (96, 178, 254, 'SYSTEM', '2025-08-03 14:36:46.105', 'SYSTEM', '2025-08-03 14:36:46.105');
INSERT INTO `content_content_tag` VALUES (97, 178, 376, 'SYSTEM', '2025-08-03 14:36:46.105', 'SYSTEM', '2025-08-03 14:36:46.105');
INSERT INTO `content_content_tag` VALUES (98, 178, 387, 'SYSTEM', '2025-08-03 14:36:46.105', 'SYSTEM', '2025-08-03 14:36:46.105');
INSERT INTO `content_content_tag` VALUES (99, 180, 173, 'SYSTEM', '2025-08-03 14:36:46.168', 'SYSTEM', '2025-08-03 14:36:46.168');
INSERT INTO `content_content_tag` VALUES (100, 180, 119, 'SYSTEM', '2025-08-03 14:36:46.168', 'SYSTEM', '2025-08-03 14:36:46.168');
INSERT INTO `content_content_tag` VALUES (101, 180, 124, 'SYSTEM', '2025-08-03 14:36:46.168', 'SYSTEM', '2025-08-03 14:36:46.168');
INSERT INTO `content_content_tag` VALUES (102, 180, 84, 'SYSTEM', '2025-08-03 14:36:46.168', 'SYSTEM', '2025-08-03 14:36:46.168');
INSERT INTO `content_content_tag` VALUES (103, 180, 250, 'SYSTEM', '2025-08-03 14:36:46.168', 'SYSTEM', '2025-08-03 14:36:46.168');
INSERT INTO `content_content_tag` VALUES (104, 180, 365, 'SYSTEM', '2025-08-03 14:36:46.168', 'SYSTEM', '2025-08-03 14:36:46.168');
INSERT INTO `content_content_tag` VALUES (105, 180, 381, 'SYSTEM', '2025-08-03 14:36:46.168', 'SYSTEM', '2025-08-03 14:36:46.168');
INSERT INTO `content_content_tag` VALUES (106, 181, 239, 'SYSTEM', '2025-08-03 14:36:46.200', 'SYSTEM', '2025-08-03 14:36:46.200');
INSERT INTO `content_content_tag` VALUES (107, 181, 70, 'SYSTEM', '2025-08-03 14:36:46.200', 'SYSTEM', '2025-08-03 14:36:46.200');
INSERT INTO `content_content_tag` VALUES (108, 181, 101, 'SYSTEM', '2025-08-03 14:36:46.200', 'SYSTEM', '2025-08-03 14:36:46.200');
INSERT INTO `content_content_tag` VALUES (109, 181, 325, 'SYSTEM', '2025-08-03 14:36:46.200', 'SYSTEM', '2025-08-03 14:36:46.200');
INSERT INTO `content_content_tag` VALUES (110, 181, 210, 'SYSTEM', '2025-08-03 14:36:46.200', 'SYSTEM', '2025-08-03 14:36:46.200');
INSERT INTO `content_content_tag` VALUES (111, 181, 371, 'SYSTEM', '2025-08-03 14:36:46.200', 'SYSTEM', '2025-08-03 14:36:46.200');
INSERT INTO `content_content_tag` VALUES (112, 181, 382, 'SYSTEM', '2025-08-03 14:36:46.200', 'SYSTEM', '2025-08-03 14:36:46.200');
INSERT INTO `content_content_tag` VALUES (113, 182, 229, 'SYSTEM', '2025-08-03 14:36:46.229', 'SYSTEM', '2025-08-03 14:36:46.229');
INSERT INTO `content_content_tag` VALUES (114, 182, 294, 'SYSTEM', '2025-08-03 14:36:46.229', 'SYSTEM', '2025-08-03 14:36:46.229');
INSERT INTO `content_content_tag` VALUES (115, 182, 281, 'SYSTEM', '2025-08-03 14:36:46.229', 'SYSTEM', '2025-08-03 14:36:46.229');
INSERT INTO `content_content_tag` VALUES (116, 182, 56, 'SYSTEM', '2025-08-03 14:36:46.229', 'SYSTEM', '2025-08-03 14:36:46.229');
INSERT INTO `content_content_tag` VALUES (117, 182, 338, 'SYSTEM', '2025-08-03 14:36:46.229', 'SYSTEM', '2025-08-03 14:36:46.229');
INSERT INTO `content_content_tag` VALUES (118, 182, 357, 'SYSTEM', '2025-08-03 14:36:46.229', 'SYSTEM', '2025-08-03 14:36:46.229');
INSERT INTO `content_content_tag` VALUES (119, 182, 385, 'SYSTEM', '2025-08-03 14:36:46.229', 'SYSTEM', '2025-08-03 14:36:46.229');
INSERT INTO `content_content_tag` VALUES (120, 183, 348, 'SYSTEM', '2025-08-03 14:36:57.294', 'SYSTEM', '2025-08-03 14:36:57.294');
INSERT INTO `content_content_tag` VALUES (121, 183, 341, 'SYSTEM', '2025-08-03 14:36:57.294', 'SYSTEM', '2025-08-03 14:36:57.294');
INSERT INTO `content_content_tag` VALUES (122, 183, 127, 'SYSTEM', '2025-08-03 14:36:57.294', 'SYSTEM', '2025-08-03 14:36:57.294');
INSERT INTO `content_content_tag` VALUES (123, 183, 154, 'SYSTEM', '2025-08-03 14:36:57.294', 'SYSTEM', '2025-08-03 14:36:57.294');
INSERT INTO `content_content_tag` VALUES (124, 183, 257, 'SYSTEM', '2025-08-03 14:36:57.295', 'SYSTEM', '2025-08-03 14:36:57.295');
INSERT INTO `content_content_tag` VALUES (125, 183, 354, 'SYSTEM', '2025-08-03 14:36:57.295', 'SYSTEM', '2025-08-03 14:36:57.295');
INSERT INTO `content_content_tag` VALUES (126, 183, 382, 'SYSTEM', '2025-08-03 14:36:57.295', 'SYSTEM', '2025-08-03 14:36:57.295');
INSERT INTO `content_content_tag` VALUES (127, 184, 149, 'SYSTEM', '2025-08-03 14:37:27.384', 'SYSTEM', '2025-08-03 14:37:27.384');
INSERT INTO `content_content_tag` VALUES (128, 184, 24, 'SYSTEM', '2025-08-03 14:37:27.384', 'SYSTEM', '2025-08-03 14:37:27.384');
INSERT INTO `content_content_tag` VALUES (129, 184, 322, 'SYSTEM', '2025-08-03 14:37:27.384', 'SYSTEM', '2025-08-03 14:37:27.384');
INSERT INTO `content_content_tag` VALUES (130, 184, 293, 'SYSTEM', '2025-08-03 14:37:27.384', 'SYSTEM', '2025-08-03 14:37:27.384');
INSERT INTO `content_content_tag` VALUES (131, 184, 174, 'SYSTEM', '2025-08-03 14:37:27.384', 'SYSTEM', '2025-08-03 14:37:27.384');
INSERT INTO `content_content_tag` VALUES (132, 184, 354, 'SYSTEM', '2025-08-03 14:37:27.384', 'SYSTEM', '2025-08-03 14:37:27.384');
INSERT INTO `content_content_tag` VALUES (133, 184, 384, 'SYSTEM', '2025-08-03 14:37:27.384', 'SYSTEM', '2025-08-03 14:37:27.384');
INSERT INTO `content_content_tag` VALUES (134, 185, 195, 'SYSTEM', '2025-08-03 14:37:27.406', 'SYSTEM', '2025-08-03 14:37:27.406');
INSERT INTO `content_content_tag` VALUES (135, 185, 120, 'SYSTEM', '2025-08-03 14:37:27.406', 'SYSTEM', '2025-08-03 14:37:27.406');
INSERT INTO `content_content_tag` VALUES (136, 185, 291, 'SYSTEM', '2025-08-03 14:37:27.406', 'SYSTEM', '2025-08-03 14:37:27.406');
INSERT INTO `content_content_tag` VALUES (137, 185, 64, 'SYSTEM', '2025-08-03 14:37:27.406', 'SYSTEM', '2025-08-03 14:37:27.406');
INSERT INTO `content_content_tag` VALUES (138, 185, 124, 'SYSTEM', '2025-08-03 14:37:27.406', 'SYSTEM', '2025-08-03 14:37:27.406');
INSERT INTO `content_content_tag` VALUES (139, 185, 367, 'SYSTEM', '2025-08-03 14:37:27.406', 'SYSTEM', '2025-08-03 14:37:27.406');
INSERT INTO `content_content_tag` VALUES (140, 185, 384, 'SYSTEM', '2025-08-03 14:37:27.406', 'SYSTEM', '2025-08-03 14:37:27.406');
INSERT INTO `content_content_tag` VALUES (141, 186, 214, 'SYSTEM', '2025-08-03 14:37:27.439', 'SYSTEM', '2025-08-03 14:37:27.439');
INSERT INTO `content_content_tag` VALUES (142, 186, 99, 'SYSTEM', '2025-08-03 14:37:27.439', 'SYSTEM', '2025-08-03 14:37:27.439');
INSERT INTO `content_content_tag` VALUES (143, 186, 211, 'SYSTEM', '2025-08-03 14:37:27.439', 'SYSTEM', '2025-08-03 14:37:27.439');
INSERT INTO `content_content_tag` VALUES (144, 186, 171, 'SYSTEM', '2025-08-03 14:37:27.439', 'SYSTEM', '2025-08-03 14:37:27.439');
INSERT INTO `content_content_tag` VALUES (145, 186, 142, 'SYSTEM', '2025-08-03 14:37:27.439', 'SYSTEM', '2025-08-03 14:37:27.439');
INSERT INTO `content_content_tag` VALUES (146, 186, 372, 'SYSTEM', '2025-08-03 14:37:27.439', 'SYSTEM', '2025-08-03 14:37:27.439');
INSERT INTO `content_content_tag` VALUES (147, 186, 384, 'SYSTEM', '2025-08-03 14:37:27.439', 'SYSTEM', '2025-08-03 14:37:27.439');
INSERT INTO `content_content_tag` VALUES (148, 187, 230, 'SYSTEM', '2025-08-03 14:37:27.469', 'SYSTEM', '2025-08-03 14:37:27.469');
INSERT INTO `content_content_tag` VALUES (149, 187, 244, 'SYSTEM', '2025-08-03 14:37:27.469', 'SYSTEM', '2025-08-03 14:37:27.469');
INSERT INTO `content_content_tag` VALUES (150, 187, 154, 'SYSTEM', '2025-08-03 14:37:27.469', 'SYSTEM', '2025-08-03 14:37:27.469');
INSERT INTO `content_content_tag` VALUES (151, 187, 145, 'SYSTEM', '2025-08-03 14:37:27.469', 'SYSTEM', '2025-08-03 14:37:27.469');
INSERT INTO `content_content_tag` VALUES (152, 187, 83, 'SYSTEM', '2025-08-03 14:37:27.469', 'SYSTEM', '2025-08-03 14:37:27.469');
INSERT INTO `content_content_tag` VALUES (153, 187, 377, 'SYSTEM', '2025-08-03 14:37:27.469', 'SYSTEM', '2025-08-03 14:37:27.469');
INSERT INTO `content_content_tag` VALUES (154, 187, 385, 'SYSTEM', '2025-08-03 14:37:27.469', 'SYSTEM', '2025-08-03 14:37:27.469');
INSERT INTO `content_content_tag` VALUES (155, 189, 138, 'SYSTEM', '2025-08-03 14:37:27.530', 'SYSTEM', '2025-08-03 14:37:27.530');
INSERT INTO `content_content_tag` VALUES (156, 189, 170, 'SYSTEM', '2025-08-03 14:37:27.530', 'SYSTEM', '2025-08-03 14:37:27.530');
INSERT INTO `content_content_tag` VALUES (157, 189, 153, 'SYSTEM', '2025-08-03 14:37:27.530', 'SYSTEM', '2025-08-03 14:37:27.530');
INSERT INTO `content_content_tag` VALUES (158, 189, 189, 'SYSTEM', '2025-08-03 14:37:27.530', 'SYSTEM', '2025-08-03 14:37:27.530');
INSERT INTO `content_content_tag` VALUES (159, 189, 150, 'SYSTEM', '2025-08-03 14:37:27.530', 'SYSTEM', '2025-08-03 14:37:27.530');
INSERT INTO `content_content_tag` VALUES (160, 189, 366, 'SYSTEM', '2025-08-03 14:37:27.530', 'SYSTEM', '2025-08-03 14:37:27.530');
INSERT INTO `content_content_tag` VALUES (161, 189, 381, 'SYSTEM', '2025-08-03 14:37:27.530', 'SYSTEM', '2025-08-03 14:37:27.530');
INSERT INTO `content_content_tag` VALUES (162, 190, 318, 'SYSTEM', '2025-08-03 14:37:27.562', 'SYSTEM', '2025-08-03 14:37:27.562');
INSERT INTO `content_content_tag` VALUES (163, 190, 337, 'SYSTEM', '2025-08-03 14:37:27.562', 'SYSTEM', '2025-08-03 14:37:27.562');
INSERT INTO `content_content_tag` VALUES (164, 190, 201, 'SYSTEM', '2025-08-03 14:37:27.562', 'SYSTEM', '2025-08-03 14:37:27.562');
INSERT INTO `content_content_tag` VALUES (165, 190, 40, 'SYSTEM', '2025-08-03 14:37:27.562', 'SYSTEM', '2025-08-03 14:37:27.562');
INSERT INTO `content_content_tag` VALUES (166, 190, 154, 'SYSTEM', '2025-08-03 14:37:27.562', 'SYSTEM', '2025-08-03 14:37:27.562');
INSERT INTO `content_content_tag` VALUES (167, 190, 357, 'SYSTEM', '2025-08-03 14:37:27.562', 'SYSTEM', '2025-08-03 14:37:27.562');
INSERT INTO `content_content_tag` VALUES (168, 190, 385, 'SYSTEM', '2025-08-03 14:37:27.562', 'SYSTEM', '2025-08-03 14:37:27.562');
INSERT INTO `content_content_tag` VALUES (169, 191, 247, 'SYSTEM', '2025-08-03 14:37:27.594', 'SYSTEM', '2025-08-03 14:37:27.594');
INSERT INTO `content_content_tag` VALUES (170, 191, 76, 'SYSTEM', '2025-08-03 14:37:27.594', 'SYSTEM', '2025-08-03 14:37:27.594');
INSERT INTO `content_content_tag` VALUES (171, 191, 156, 'SYSTEM', '2025-08-03 14:37:27.594', 'SYSTEM', '2025-08-03 14:37:27.594');
INSERT INTO `content_content_tag` VALUES (172, 191, 20, 'SYSTEM', '2025-08-03 14:37:27.594', 'SYSTEM', '2025-08-03 14:37:27.594');
INSERT INTO `content_content_tag` VALUES (173, 191, 75, 'SYSTEM', '2025-08-03 14:37:27.594', 'SYSTEM', '2025-08-03 14:37:27.594');
INSERT INTO `content_content_tag` VALUES (174, 191, 376, 'SYSTEM', '2025-08-03 14:37:27.594', 'SYSTEM', '2025-08-03 14:37:27.594');
INSERT INTO `content_content_tag` VALUES (175, 191, 382, 'SYSTEM', '2025-08-03 14:37:27.594', 'SYSTEM', '2025-08-03 14:37:27.594');
INSERT INTO `content_content_tag` VALUES (176, 192, 10, 'SYSTEM', '2025-08-03 14:39:20.906', 'SYSTEM', '2025-08-03 14:39:20.906');
INSERT INTO `content_content_tag` VALUES (177, 192, 52, 'SYSTEM', '2025-08-03 14:39:20.906', 'SYSTEM', '2025-08-03 14:39:20.906');
INSERT INTO `content_content_tag` VALUES (178, 192, 259, 'SYSTEM', '2025-08-03 14:39:20.906', 'SYSTEM', '2025-08-03 14:39:20.906');
INSERT INTO `content_content_tag` VALUES (179, 192, 66, 'SYSTEM', '2025-08-03 14:39:20.906', 'SYSTEM', '2025-08-03 14:39:20.906');
INSERT INTO `content_content_tag` VALUES (180, 192, 41, 'SYSTEM', '2025-08-03 14:39:20.906', 'SYSTEM', '2025-08-03 14:39:20.906');
INSERT INTO `content_content_tag` VALUES (181, 192, 372, 'SYSTEM', '2025-08-03 14:39:20.906', 'SYSTEM', '2025-08-03 14:39:20.906');
INSERT INTO `content_content_tag` VALUES (182, 192, 385, 'SYSTEM', '2025-08-03 14:39:20.906', 'SYSTEM', '2025-08-03 14:39:20.906');
INSERT INTO `content_content_tag` VALUES (183, 193, 156, 'SYSTEM', '2025-08-03 14:39:21.003', 'SYSTEM', '2025-08-03 14:39:21.003');
INSERT INTO `content_content_tag` VALUES (184, 193, 98, 'SYSTEM', '2025-08-03 14:39:21.003', 'SYSTEM', '2025-08-03 14:39:21.003');
INSERT INTO `content_content_tag` VALUES (185, 193, 154, 'SYSTEM', '2025-08-03 14:39:21.003', 'SYSTEM', '2025-08-03 14:39:21.003');
INSERT INTO `content_content_tag` VALUES (186, 193, 11, 'SYSTEM', '2025-08-03 14:39:21.003', 'SYSTEM', '2025-08-03 14:39:21.003');
INSERT INTO `content_content_tag` VALUES (187, 193, 229, 'SYSTEM', '2025-08-03 14:39:21.003', 'SYSTEM', '2025-08-03 14:39:21.003');
INSERT INTO `content_content_tag` VALUES (188, 193, 370, 'SYSTEM', '2025-08-03 14:39:21.003', 'SYSTEM', '2025-08-03 14:39:21.003');
INSERT INTO `content_content_tag` VALUES (189, 193, 381, 'SYSTEM', '2025-08-03 14:39:21.003', 'SYSTEM', '2025-08-03 14:39:21.003');
INSERT INTO `content_content_tag` VALUES (190, 194, 243, 'SYSTEM', '2025-08-03 14:39:21.049', 'SYSTEM', '2025-08-03 14:39:21.049');
INSERT INTO `content_content_tag` VALUES (191, 194, 340, 'SYSTEM', '2025-08-03 14:39:21.049', 'SYSTEM', '2025-08-03 14:39:21.049');
INSERT INTO `content_content_tag` VALUES (192, 194, 75, 'SYSTEM', '2025-08-03 14:39:21.049', 'SYSTEM', '2025-08-03 14:39:21.049');
INSERT INTO `content_content_tag` VALUES (193, 194, 127, 'SYSTEM', '2025-08-03 14:39:21.049', 'SYSTEM', '2025-08-03 14:39:21.049');
INSERT INTO `content_content_tag` VALUES (194, 194, 151, 'SYSTEM', '2025-08-03 14:39:21.049', 'SYSTEM', '2025-08-03 14:39:21.049');
INSERT INTO `content_content_tag` VALUES (195, 194, 351, 'SYSTEM', '2025-08-03 14:39:21.049', 'SYSTEM', '2025-08-03 14:39:21.049');
INSERT INTO `content_content_tag` VALUES (196, 194, 386, 'SYSTEM', '2025-08-03 14:39:21.049', 'SYSTEM', '2025-08-03 14:39:21.049');
INSERT INTO `content_content_tag` VALUES (197, 195, 181, 'SYSTEM', '2025-08-03 14:39:21.092', 'SYSTEM', '2025-08-03 14:39:21.092');
INSERT INTO `content_content_tag` VALUES (198, 195, 162, 'SYSTEM', '2025-08-03 14:39:21.092', 'SYSTEM', '2025-08-03 14:39:21.092');
INSERT INTO `content_content_tag` VALUES (199, 195, 233, 'SYSTEM', '2025-08-03 14:39:21.092', 'SYSTEM', '2025-08-03 14:39:21.092');
INSERT INTO `content_content_tag` VALUES (200, 195, 97, 'SYSTEM', '2025-08-03 14:39:21.092', 'SYSTEM', '2025-08-03 14:39:21.092');
INSERT INTO `content_content_tag` VALUES (201, 195, 247, 'SYSTEM', '2025-08-03 14:39:21.092', 'SYSTEM', '2025-08-03 14:39:21.092');
INSERT INTO `content_content_tag` VALUES (202, 195, 354, 'SYSTEM', '2025-08-03 14:39:21.092', 'SYSTEM', '2025-08-03 14:39:21.092');
INSERT INTO `content_content_tag` VALUES (203, 195, 386, 'SYSTEM', '2025-08-03 14:39:21.092', 'SYSTEM', '2025-08-03 14:39:21.092');
INSERT INTO `content_content_tag` VALUES (204, 196, 182, 'SYSTEM', '2025-08-03 14:39:21.121', 'SYSTEM', '2025-08-03 14:39:21.121');
INSERT INTO `content_content_tag` VALUES (205, 196, 27, 'SYSTEM', '2025-08-03 14:39:21.121', 'SYSTEM', '2025-08-03 14:39:21.121');
INSERT INTO `content_content_tag` VALUES (206, 196, 200, 'SYSTEM', '2025-08-03 14:39:21.121', 'SYSTEM', '2025-08-03 14:39:21.121');
INSERT INTO `content_content_tag` VALUES (207, 196, 316, 'SYSTEM', '2025-08-03 14:39:21.121', 'SYSTEM', '2025-08-03 14:39:21.121');
INSERT INTO `content_content_tag` VALUES (208, 196, 330, 'SYSTEM', '2025-08-03 14:39:21.121', 'SYSTEM', '2025-08-03 14:39:21.121');
INSERT INTO `content_content_tag` VALUES (209, 196, 374, 'SYSTEM', '2025-08-03 14:39:21.121', 'SYSTEM', '2025-08-03 14:39:21.121');
INSERT INTO `content_content_tag` VALUES (210, 196, 382, 'SYSTEM', '2025-08-03 14:39:21.121', 'SYSTEM', '2025-08-03 14:39:21.121');
INSERT INTO `content_content_tag` VALUES (211, 198, 28, 'SYSTEM', '2025-08-03 14:39:21.198', 'SYSTEM', '2025-08-03 14:39:21.198');
INSERT INTO `content_content_tag` VALUES (212, 198, 188, 'SYSTEM', '2025-08-03 14:39:21.198', 'SYSTEM', '2025-08-03 14:39:21.198');
INSERT INTO `content_content_tag` VALUES (213, 198, 211, 'SYSTEM', '2025-08-03 14:39:21.198', 'SYSTEM', '2025-08-03 14:39:21.198');
INSERT INTO `content_content_tag` VALUES (214, 198, 269, 'SYSTEM', '2025-08-03 14:39:21.198', 'SYSTEM', '2025-08-03 14:39:21.198');
INSERT INTO `content_content_tag` VALUES (215, 198, 206, 'SYSTEM', '2025-08-03 14:39:21.199', 'SYSTEM', '2025-08-03 14:39:21.199');
INSERT INTO `content_content_tag` VALUES (216, 198, 366, 'SYSTEM', '2025-08-03 14:39:21.199', 'SYSTEM', '2025-08-03 14:39:21.199');
INSERT INTO `content_content_tag` VALUES (217, 198, 381, 'SYSTEM', '2025-08-03 14:39:21.199', 'SYSTEM', '2025-08-03 14:39:21.199');
INSERT INTO `content_content_tag` VALUES (218, 199, 132, 'SYSTEM', '2025-08-03 14:39:21.218', 'SYSTEM', '2025-08-03 14:39:21.218');
INSERT INTO `content_content_tag` VALUES (219, 199, 61, 'SYSTEM', '2025-08-03 14:39:21.218', 'SYSTEM', '2025-08-03 14:39:21.218');
INSERT INTO `content_content_tag` VALUES (220, 199, 73, 'SYSTEM', '2025-08-03 14:39:21.218', 'SYSTEM', '2025-08-03 14:39:21.218');
INSERT INTO `content_content_tag` VALUES (221, 199, 254, 'SYSTEM', '2025-08-03 14:39:21.218', 'SYSTEM', '2025-08-03 14:39:21.218');
INSERT INTO `content_content_tag` VALUES (222, 199, 111, 'SYSTEM', '2025-08-03 14:39:21.218', 'SYSTEM', '2025-08-03 14:39:21.218');
INSERT INTO `content_content_tag` VALUES (223, 199, 354, 'SYSTEM', '2025-08-03 14:39:21.218', 'SYSTEM', '2025-08-03 14:39:21.218');
INSERT INTO `content_content_tag` VALUES (224, 199, 385, 'SYSTEM', '2025-08-03 14:39:21.218', 'SYSTEM', '2025-08-03 14:39:21.218');
INSERT INTO `content_content_tag` VALUES (225, 200, 235, 'SYSTEM', '2025-08-03 14:39:21.262', 'SYSTEM', '2025-08-03 14:39:21.262');
INSERT INTO `content_content_tag` VALUES (226, 200, 254, 'SYSTEM', '2025-08-03 14:39:21.262', 'SYSTEM', '2025-08-03 14:39:21.262');
INSERT INTO `content_content_tag` VALUES (227, 200, 319, 'SYSTEM', '2025-08-03 14:39:21.262', 'SYSTEM', '2025-08-03 14:39:21.262');
INSERT INTO `content_content_tag` VALUES (228, 200, 127, 'SYSTEM', '2025-08-03 14:39:21.262', 'SYSTEM', '2025-08-03 14:39:21.262');
INSERT INTO `content_content_tag` VALUES (229, 200, 46, 'SYSTEM', '2025-08-03 14:39:21.262', 'SYSTEM', '2025-08-03 14:39:21.262');
INSERT INTO `content_content_tag` VALUES (230, 200, 371, 'SYSTEM', '2025-08-03 14:39:21.262', 'SYSTEM', '2025-08-03 14:39:21.262');
INSERT INTO `content_content_tag` VALUES (231, 200, 383, 'SYSTEM', '2025-08-03 14:39:21.262', 'SYSTEM', '2025-08-03 14:39:21.262');
INSERT INTO `content_content_tag` VALUES (232, 201, 152, 'SYSTEM', '2025-08-03 14:40:03.350', 'SYSTEM', '2025-08-03 14:40:03.350');
INSERT INTO `content_content_tag` VALUES (233, 201, 271, 'SYSTEM', '2025-08-03 14:40:03.350', 'SYSTEM', '2025-08-03 14:40:03.350');
INSERT INTO `content_content_tag` VALUES (234, 201, 289, 'SYSTEM', '2025-08-03 14:40:03.351', 'SYSTEM', '2025-08-03 14:40:03.351');
INSERT INTO `content_content_tag` VALUES (235, 201, 341, 'SYSTEM', '2025-08-03 14:40:03.351', 'SYSTEM', '2025-08-03 14:40:03.351');
INSERT INTO `content_content_tag` VALUES (236, 201, 259, 'SYSTEM', '2025-08-03 14:40:03.351', 'SYSTEM', '2025-08-03 14:40:03.351');
INSERT INTO `content_content_tag` VALUES (237, 201, 371, 'SYSTEM', '2025-08-03 14:40:03.351', 'SYSTEM', '2025-08-03 14:40:03.351');
INSERT INTO `content_content_tag` VALUES (238, 201, 384, 'SYSTEM', '2025-08-03 14:40:03.351', 'SYSTEM', '2025-08-03 14:40:03.351');
INSERT INTO `content_content_tag` VALUES (239, 202, 263, 'SYSTEM', '2025-08-03 14:40:03.378', 'SYSTEM', '2025-08-03 14:40:03.378');
INSERT INTO `content_content_tag` VALUES (240, 202, 26, 'SYSTEM', '2025-08-03 14:40:03.378', 'SYSTEM', '2025-08-03 14:40:03.378');
INSERT INTO `content_content_tag` VALUES (241, 202, 44, 'SYSTEM', '2025-08-03 14:40:03.378', 'SYSTEM', '2025-08-03 14:40:03.378');
INSERT INTO `content_content_tag` VALUES (242, 202, 111, 'SYSTEM', '2025-08-03 14:40:03.378', 'SYSTEM', '2025-08-03 14:40:03.378');
INSERT INTO `content_content_tag` VALUES (243, 202, 251, 'SYSTEM', '2025-08-03 14:40:03.378', 'SYSTEM', '2025-08-03 14:40:03.378');
INSERT INTO `content_content_tag` VALUES (244, 202, 374, 'SYSTEM', '2025-08-03 14:40:03.378', 'SYSTEM', '2025-08-03 14:40:03.378');
INSERT INTO `content_content_tag` VALUES (245, 202, 381, 'SYSTEM', '2025-08-03 14:40:03.378', 'SYSTEM', '2025-08-03 14:40:03.378');
INSERT INTO `content_content_tag` VALUES (246, 203, 27, 'SYSTEM', '2025-08-03 14:40:03.397', 'SYSTEM', '2025-08-03 14:40:03.397');
INSERT INTO `content_content_tag` VALUES (247, 203, 120, 'SYSTEM', '2025-08-03 14:40:03.397', 'SYSTEM', '2025-08-03 14:40:03.397');
INSERT INTO `content_content_tag` VALUES (248, 203, 71, 'SYSTEM', '2025-08-03 14:40:03.397', 'SYSTEM', '2025-08-03 14:40:03.397');
INSERT INTO `content_content_tag` VALUES (249, 203, 121, 'SYSTEM', '2025-08-03 14:40:03.397', 'SYSTEM', '2025-08-03 14:40:03.397');
INSERT INTO `content_content_tag` VALUES (250, 203, 122, 'SYSTEM', '2025-08-03 14:40:03.397', 'SYSTEM', '2025-08-03 14:40:03.397');
INSERT INTO `content_content_tag` VALUES (251, 203, 366, 'SYSTEM', '2025-08-03 14:40:03.397', 'SYSTEM', '2025-08-03 14:40:03.397');
INSERT INTO `content_content_tag` VALUES (252, 203, 382, 'SYSTEM', '2025-08-03 14:40:03.397', 'SYSTEM', '2025-08-03 14:40:03.397');
INSERT INTO `content_content_tag` VALUES (253, 204, 194, 'SYSTEM', '2025-08-03 14:40:03.414', 'SYSTEM', '2025-08-03 14:40:03.414');
INSERT INTO `content_content_tag` VALUES (254, 204, 56, 'SYSTEM', '2025-08-03 14:40:03.414', 'SYSTEM', '2025-08-03 14:40:03.414');
INSERT INTO `content_content_tag` VALUES (255, 204, 132, 'SYSTEM', '2025-08-03 14:40:03.414', 'SYSTEM', '2025-08-03 14:40:03.414');
INSERT INTO `content_content_tag` VALUES (256, 204, 213, 'SYSTEM', '2025-08-03 14:40:03.414', 'SYSTEM', '2025-08-03 14:40:03.414');
INSERT INTO `content_content_tag` VALUES (257, 204, 346, 'SYSTEM', '2025-08-03 14:40:03.414', 'SYSTEM', '2025-08-03 14:40:03.414');
INSERT INTO `content_content_tag` VALUES (258, 204, 364, 'SYSTEM', '2025-08-03 14:40:03.414', 'SYSTEM', '2025-08-03 14:40:03.414');
INSERT INTO `content_content_tag` VALUES (259, 204, 386, 'SYSTEM', '2025-08-03 14:40:03.414', 'SYSTEM', '2025-08-03 14:40:03.414');
INSERT INTO `content_content_tag` VALUES (260, 205, 209, 'SYSTEM', '2025-08-03 14:40:03.454', 'SYSTEM', '2025-08-03 14:40:03.454');
INSERT INTO `content_content_tag` VALUES (261, 205, 134, 'SYSTEM', '2025-08-03 14:40:03.454', 'SYSTEM', '2025-08-03 14:40:03.454');
INSERT INTO `content_content_tag` VALUES (262, 205, 161, 'SYSTEM', '2025-08-03 14:40:03.454', 'SYSTEM', '2025-08-03 14:40:03.454');
INSERT INTO `content_content_tag` VALUES (263, 205, 324, 'SYSTEM', '2025-08-03 14:40:03.454', 'SYSTEM', '2025-08-03 14:40:03.454');
INSERT INTO `content_content_tag` VALUES (264, 205, 327, 'SYSTEM', '2025-08-03 14:40:03.454', 'SYSTEM', '2025-08-03 14:40:03.454');
INSERT INTO `content_content_tag` VALUES (265, 205, 351, 'SYSTEM', '2025-08-03 14:40:03.454', 'SYSTEM', '2025-08-03 14:40:03.454');
INSERT INTO `content_content_tag` VALUES (266, 205, 382, 'SYSTEM', '2025-08-03 14:40:03.454', 'SYSTEM', '2025-08-03 14:40:03.454');
INSERT INTO `content_content_tag` VALUES (267, 206, 33, 'SYSTEM', '2025-08-03 14:40:03.470', 'SYSTEM', '2025-08-03 14:40:03.470');
INSERT INTO `content_content_tag` VALUES (268, 206, 150, 'SYSTEM', '2025-08-03 14:40:03.470', 'SYSTEM', '2025-08-03 14:40:03.470');
INSERT INTO `content_content_tag` VALUES (269, 206, 102, 'SYSTEM', '2025-08-03 14:40:03.470', 'SYSTEM', '2025-08-03 14:40:03.470');
INSERT INTO `content_content_tag` VALUES (270, 206, 85, 'SYSTEM', '2025-08-03 14:40:03.470', 'SYSTEM', '2025-08-03 14:40:03.470');
INSERT INTO `content_content_tag` VALUES (271, 206, 67, 'SYSTEM', '2025-08-03 14:40:03.470', 'SYSTEM', '2025-08-03 14:40:03.470');
INSERT INTO `content_content_tag` VALUES (272, 206, 355, 'SYSTEM', '2025-08-03 14:40:03.470', 'SYSTEM', '2025-08-03 14:40:03.470');
INSERT INTO `content_content_tag` VALUES (273, 206, 386, 'SYSTEM', '2025-08-03 14:40:03.470', 'SYSTEM', '2025-08-03 14:40:03.470');
INSERT INTO `content_content_tag` VALUES (274, 208, 172, 'SYSTEM', '2025-08-03 14:40:03.513', 'SYSTEM', '2025-08-03 14:40:03.513');
INSERT INTO `content_content_tag` VALUES (275, 208, 164, 'SYSTEM', '2025-08-03 14:40:03.513', 'SYSTEM', '2025-08-03 14:40:03.513');
INSERT INTO `content_content_tag` VALUES (276, 208, 1, 'SYSTEM', '2025-08-03 14:40:03.513', 'SYSTEM', '2025-08-03 14:40:03.513');
INSERT INTO `content_content_tag` VALUES (277, 208, 261, 'SYSTEM', '2025-08-03 14:40:03.513', 'SYSTEM', '2025-08-03 14:40:03.513');
INSERT INTO `content_content_tag` VALUES (278, 208, 142, 'SYSTEM', '2025-08-03 14:40:03.513', 'SYSTEM', '2025-08-03 14:40:03.513');
INSERT INTO `content_content_tag` VALUES (279, 208, 373, 'SYSTEM', '2025-08-03 14:40:03.513', 'SYSTEM', '2025-08-03 14:40:03.513');
INSERT INTO `content_content_tag` VALUES (280, 208, 382, 'SYSTEM', '2025-08-03 14:40:03.513', 'SYSTEM', '2025-08-03 14:40:03.513');
INSERT INTO `content_content_tag` VALUES (281, 209, 324, 'SYSTEM', '2025-08-03 14:40:03.536', 'SYSTEM', '2025-08-03 14:40:03.536');
INSERT INTO `content_content_tag` VALUES (282, 209, 342, 'SYSTEM', '2025-08-03 14:40:03.536', 'SYSTEM', '2025-08-03 14:40:03.536');
INSERT INTO `content_content_tag` VALUES (283, 209, 201, 'SYSTEM', '2025-08-03 14:40:03.536', 'SYSTEM', '2025-08-03 14:40:03.536');
INSERT INTO `content_content_tag` VALUES (284, 209, 23, 'SYSTEM', '2025-08-03 14:40:03.536', 'SYSTEM', '2025-08-03 14:40:03.536');
INSERT INTO `content_content_tag` VALUES (285, 209, 136, 'SYSTEM', '2025-08-03 14:40:03.536', 'SYSTEM', '2025-08-03 14:40:03.536');
INSERT INTO `content_content_tag` VALUES (286, 209, 373, 'SYSTEM', '2025-08-03 14:40:03.536', 'SYSTEM', '2025-08-03 14:40:03.536');
INSERT INTO `content_content_tag` VALUES (287, 209, 381, 'SYSTEM', '2025-08-03 14:40:03.536', 'SYSTEM', '2025-08-03 14:40:03.536');
INSERT INTO `content_content_tag` VALUES (288, 210, 83, 'SYSTEM', '2025-08-03 14:40:03.550', 'SYSTEM', '2025-08-03 14:40:03.550');
INSERT INTO `content_content_tag` VALUES (289, 210, 309, 'SYSTEM', '2025-08-03 14:40:03.550', 'SYSTEM', '2025-08-03 14:40:03.550');
INSERT INTO `content_content_tag` VALUES (290, 210, 286, 'SYSTEM', '2025-08-03 14:40:03.550', 'SYSTEM', '2025-08-03 14:40:03.550');
INSERT INTO `content_content_tag` VALUES (291, 210, 62, 'SYSTEM', '2025-08-03 14:40:03.550', 'SYSTEM', '2025-08-03 14:40:03.550');
INSERT INTO `content_content_tag` VALUES (292, 210, 136, 'SYSTEM', '2025-08-03 14:40:03.550', 'SYSTEM', '2025-08-03 14:40:03.550');
INSERT INTO `content_content_tag` VALUES (293, 210, 378, 'SYSTEM', '2025-08-03 14:40:03.550', 'SYSTEM', '2025-08-03 14:40:03.550');
INSERT INTO `content_content_tag` VALUES (294, 210, 381, 'SYSTEM', '2025-08-03 14:40:03.550', 'SYSTEM', '2025-08-03 14:40:03.550');
INSERT INTO `content_content_tag` VALUES (295, 211, 134, 'SYSTEM', '2025-08-03 14:40:05.686', 'SYSTEM', '2025-08-03 14:40:05.686');
INSERT INTO `content_content_tag` VALUES (296, 211, 309, 'SYSTEM', '2025-08-03 14:40:05.686', 'SYSTEM', '2025-08-03 14:40:05.686');
INSERT INTO `content_content_tag` VALUES (297, 211, 132, 'SYSTEM', '2025-08-03 14:40:05.686', 'SYSTEM', '2025-08-03 14:40:05.686');
INSERT INTO `content_content_tag` VALUES (298, 211, 203, 'SYSTEM', '2025-08-03 14:40:05.686', 'SYSTEM', '2025-08-03 14:40:05.686');
INSERT INTO `content_content_tag` VALUES (299, 211, 56, 'SYSTEM', '2025-08-03 14:40:05.686', 'SYSTEM', '2025-08-03 14:40:05.686');
INSERT INTO `content_content_tag` VALUES (300, 211, 379, 'SYSTEM', '2025-08-03 14:40:05.686', 'SYSTEM', '2025-08-03 14:40:05.686');
INSERT INTO `content_content_tag` VALUES (301, 211, 385, 'SYSTEM', '2025-08-03 14:40:05.686', 'SYSTEM', '2025-08-03 14:40:05.686');
INSERT INTO `content_content_tag` VALUES (302, 212, 245, 'SYSTEM', '2025-08-03 14:40:05.703', 'SYSTEM', '2025-08-03 14:40:05.703');
INSERT INTO `content_content_tag` VALUES (303, 212, 32, 'SYSTEM', '2025-08-03 14:40:05.703', 'SYSTEM', '2025-08-03 14:40:05.703');
INSERT INTO `content_content_tag` VALUES (304, 212, 196, 'SYSTEM', '2025-08-03 14:40:05.703', 'SYSTEM', '2025-08-03 14:40:05.703');
INSERT INTO `content_content_tag` VALUES (305, 212, 42, 'SYSTEM', '2025-08-03 14:40:05.703', 'SYSTEM', '2025-08-03 14:40:05.703');
INSERT INTO `content_content_tag` VALUES (306, 212, 8, 'SYSTEM', '2025-08-03 14:40:05.703', 'SYSTEM', '2025-08-03 14:40:05.703');
INSERT INTO `content_content_tag` VALUES (307, 212, 379, 'SYSTEM', '2025-08-03 14:40:05.703', 'SYSTEM', '2025-08-03 14:40:05.703');
INSERT INTO `content_content_tag` VALUES (308, 212, 381, 'SYSTEM', '2025-08-03 14:40:05.703', 'SYSTEM', '2025-08-03 14:40:05.703');
INSERT INTO `content_content_tag` VALUES (309, 213, 60, 'SYSTEM', '2025-08-03 14:40:05.721', 'SYSTEM', '2025-08-03 14:40:05.721');
INSERT INTO `content_content_tag` VALUES (310, 213, 248, 'SYSTEM', '2025-08-03 14:40:05.721', 'SYSTEM', '2025-08-03 14:40:05.721');
INSERT INTO `content_content_tag` VALUES (311, 213, 49, 'SYSTEM', '2025-08-03 14:40:05.721', 'SYSTEM', '2025-08-03 14:40:05.721');
INSERT INTO `content_content_tag` VALUES (312, 213, 128, 'SYSTEM', '2025-08-03 14:40:05.721', 'SYSTEM', '2025-08-03 14:40:05.721');
INSERT INTO `content_content_tag` VALUES (313, 213, 1, 'SYSTEM', '2025-08-03 14:40:05.721', 'SYSTEM', '2025-08-03 14:40:05.721');
INSERT INTO `content_content_tag` VALUES (314, 213, 352, 'SYSTEM', '2025-08-03 14:40:05.721', 'SYSTEM', '2025-08-03 14:40:05.721');
INSERT INTO `content_content_tag` VALUES (315, 213, 385, 'SYSTEM', '2025-08-03 14:40:05.721', 'SYSTEM', '2025-08-03 14:40:05.721');
INSERT INTO `content_content_tag` VALUES (316, 214, 117, 'SYSTEM', '2025-08-03 14:40:05.762', 'SYSTEM', '2025-08-03 14:40:05.762');
INSERT INTO `content_content_tag` VALUES (317, 214, 286, 'SYSTEM', '2025-08-03 14:40:05.762', 'SYSTEM', '2025-08-03 14:40:05.762');
INSERT INTO `content_content_tag` VALUES (318, 214, 79, 'SYSTEM', '2025-08-03 14:40:05.762', 'SYSTEM', '2025-08-03 14:40:05.762');
INSERT INTO `content_content_tag` VALUES (319, 214, 294, 'SYSTEM', '2025-08-03 14:40:05.762', 'SYSTEM', '2025-08-03 14:40:05.762');
INSERT INTO `content_content_tag` VALUES (320, 214, 49, 'SYSTEM', '2025-08-03 14:40:05.762', 'SYSTEM', '2025-08-03 14:40:05.762');
INSERT INTO `content_content_tag` VALUES (321, 214, 370, 'SYSTEM', '2025-08-03 14:40:05.762', 'SYSTEM', '2025-08-03 14:40:05.762');
INSERT INTO `content_content_tag` VALUES (322, 214, 382, 'SYSTEM', '2025-08-03 14:40:05.762', 'SYSTEM', '2025-08-03 14:40:05.762');
INSERT INTO `content_content_tag` VALUES (323, 215, 271, 'SYSTEM', '2025-08-03 14:40:05.794', 'SYSTEM', '2025-08-03 14:40:05.794');
INSERT INTO `content_content_tag` VALUES (324, 215, 263, 'SYSTEM', '2025-08-03 14:40:05.794', 'SYSTEM', '2025-08-03 14:40:05.794');
INSERT INTO `content_content_tag` VALUES (325, 215, 304, 'SYSTEM', '2025-08-03 14:40:05.794', 'SYSTEM', '2025-08-03 14:40:05.794');
INSERT INTO `content_content_tag` VALUES (326, 215, 298, 'SYSTEM', '2025-08-03 14:40:05.794', 'SYSTEM', '2025-08-03 14:40:05.794');
INSERT INTO `content_content_tag` VALUES (327, 215, 221, 'SYSTEM', '2025-08-03 14:40:05.794', 'SYSTEM', '2025-08-03 14:40:05.794');
INSERT INTO `content_content_tag` VALUES (328, 215, 364, 'SYSTEM', '2025-08-03 14:40:05.794', 'SYSTEM', '2025-08-03 14:40:05.794');
INSERT INTO `content_content_tag` VALUES (329, 215, 382, 'SYSTEM', '2025-08-03 14:40:05.794', 'SYSTEM', '2025-08-03 14:40:05.794');
INSERT INTO `content_content_tag` VALUES (330, 216, 81, 'SYSTEM', '2025-08-03 14:40:05.823', 'SYSTEM', '2025-08-03 14:40:05.823');
INSERT INTO `content_content_tag` VALUES (331, 216, 76, 'SYSTEM', '2025-08-03 14:40:05.823', 'SYSTEM', '2025-08-03 14:40:05.823');
INSERT INTO `content_content_tag` VALUES (332, 216, 90, 'SYSTEM', '2025-08-03 14:40:05.823', 'SYSTEM', '2025-08-03 14:40:05.823');
INSERT INTO `content_content_tag` VALUES (333, 216, 15, 'SYSTEM', '2025-08-03 14:40:05.823', 'SYSTEM', '2025-08-03 14:40:05.823');
INSERT INTO `content_content_tag` VALUES (334, 216, 319, 'SYSTEM', '2025-08-03 14:40:05.823', 'SYSTEM', '2025-08-03 14:40:05.823');
INSERT INTO `content_content_tag` VALUES (335, 216, 361, 'SYSTEM', '2025-08-03 14:40:05.823', 'SYSTEM', '2025-08-03 14:40:05.823');
INSERT INTO `content_content_tag` VALUES (336, 216, 384, 'SYSTEM', '2025-08-03 14:40:05.823', 'SYSTEM', '2025-08-03 14:40:05.823');
INSERT INTO `content_content_tag` VALUES (337, 218, 139, 'SYSTEM', '2025-08-03 14:40:05.885', 'SYSTEM', '2025-08-03 14:40:05.885');
INSERT INTO `content_content_tag` VALUES (338, 218, 180, 'SYSTEM', '2025-08-03 14:40:05.885', 'SYSTEM', '2025-08-03 14:40:05.885');
INSERT INTO `content_content_tag` VALUES (339, 218, 103, 'SYSTEM', '2025-08-03 14:40:05.885', 'SYSTEM', '2025-08-03 14:40:05.885');
INSERT INTO `content_content_tag` VALUES (340, 218, 174, 'SYSTEM', '2025-08-03 14:40:05.885', 'SYSTEM', '2025-08-03 14:40:05.885');
INSERT INTO `content_content_tag` VALUES (341, 218, 100, 'SYSTEM', '2025-08-03 14:40:05.885', 'SYSTEM', '2025-08-03 14:40:05.885');
INSERT INTO `content_content_tag` VALUES (342, 218, 366, 'SYSTEM', '2025-08-03 14:40:05.885', 'SYSTEM', '2025-08-03 14:40:05.885');
INSERT INTO `content_content_tag` VALUES (343, 218, 387, 'SYSTEM', '2025-08-03 14:40:05.885', 'SYSTEM', '2025-08-03 14:40:05.885');
INSERT INTO `content_content_tag` VALUES (344, 219, 348, 'SYSTEM', '2025-08-03 14:40:05.921', 'SYSTEM', '2025-08-03 14:40:05.921');
INSERT INTO `content_content_tag` VALUES (345, 219, 99, 'SYSTEM', '2025-08-03 14:40:05.921', 'SYSTEM', '2025-08-03 14:40:05.921');
INSERT INTO `content_content_tag` VALUES (346, 219, 225, 'SYSTEM', '2025-08-03 14:40:05.921', 'SYSTEM', '2025-08-03 14:40:05.921');
INSERT INTO `content_content_tag` VALUES (347, 219, 122, 'SYSTEM', '2025-08-03 14:40:05.921', 'SYSTEM', '2025-08-03 14:40:05.921');
INSERT INTO `content_content_tag` VALUES (348, 219, 139, 'SYSTEM', '2025-08-03 14:40:05.921', 'SYSTEM', '2025-08-03 14:40:05.921');
INSERT INTO `content_content_tag` VALUES (349, 219, 368, 'SYSTEM', '2025-08-03 14:40:05.921', 'SYSTEM', '2025-08-03 14:40:05.921');
INSERT INTO `content_content_tag` VALUES (350, 219, 381, 'SYSTEM', '2025-08-03 14:40:05.921', 'SYSTEM', '2025-08-03 14:40:05.921');
INSERT INTO `content_content_tag` VALUES (351, 220, 116, 'SYSTEM', '2025-08-03 14:40:05.949', 'SYSTEM', '2025-08-03 14:40:05.949');
INSERT INTO `content_content_tag` VALUES (352, 220, 253, 'SYSTEM', '2025-08-03 14:40:05.949', 'SYSTEM', '2025-08-03 14:40:05.949');
INSERT INTO `content_content_tag` VALUES (353, 220, 277, 'SYSTEM', '2025-08-03 14:40:05.949', 'SYSTEM', '2025-08-03 14:40:05.949');
INSERT INTO `content_content_tag` VALUES (354, 220, 32, 'SYSTEM', '2025-08-03 14:40:05.949', 'SYSTEM', '2025-08-03 14:40:05.949');
INSERT INTO `content_content_tag` VALUES (355, 220, 155, 'SYSTEM', '2025-08-03 14:40:05.949', 'SYSTEM', '2025-08-03 14:40:05.949');
INSERT INTO `content_content_tag` VALUES (356, 220, 372, 'SYSTEM', '2025-08-03 14:40:05.949', 'SYSTEM', '2025-08-03 14:40:05.949');
INSERT INTO `content_content_tag` VALUES (357, 220, 386, 'SYSTEM', '2025-08-03 14:40:05.949', 'SYSTEM', '2025-08-03 14:40:05.949');
INSERT INTO `content_content_tag` VALUES (358, 221, 155, 'SYSTEM', '2025-08-03 14:40:07.942', 'SYSTEM', '2025-08-03 14:40:07.942');
INSERT INTO `content_content_tag` VALUES (359, 221, 5, 'SYSTEM', '2025-08-03 14:40:07.942', 'SYSTEM', '2025-08-03 14:40:07.942');
INSERT INTO `content_content_tag` VALUES (360, 221, 342, 'SYSTEM', '2025-08-03 14:40:07.942', 'SYSTEM', '2025-08-03 14:40:07.942');
INSERT INTO `content_content_tag` VALUES (361, 221, 289, 'SYSTEM', '2025-08-03 14:40:07.942', 'SYSTEM', '2025-08-03 14:40:07.942');
INSERT INTO `content_content_tag` VALUES (362, 221, 49, 'SYSTEM', '2025-08-03 14:40:07.942', 'SYSTEM', '2025-08-03 14:40:07.942');
INSERT INTO `content_content_tag` VALUES (363, 221, 376, 'SYSTEM', '2025-08-03 14:40:07.942', 'SYSTEM', '2025-08-03 14:40:07.942');
INSERT INTO `content_content_tag` VALUES (364, 221, 381, 'SYSTEM', '2025-08-03 14:40:07.942', 'SYSTEM', '2025-08-03 14:40:07.942');
INSERT INTO `content_content_tag` VALUES (365, 222, 54, 'SYSTEM', '2025-08-03 14:40:07.960', 'SYSTEM', '2025-08-03 14:40:07.960');
INSERT INTO `content_content_tag` VALUES (366, 222, 12, 'SYSTEM', '2025-08-03 14:40:07.960', 'SYSTEM', '2025-08-03 14:40:07.960');
INSERT INTO `content_content_tag` VALUES (367, 222, 119, 'SYSTEM', '2025-08-03 14:40:07.960', 'SYSTEM', '2025-08-03 14:40:07.960');
INSERT INTO `content_content_tag` VALUES (368, 222, 334, 'SYSTEM', '2025-08-03 14:40:07.960', 'SYSTEM', '2025-08-03 14:40:07.960');
INSERT INTO `content_content_tag` VALUES (369, 222, 220, 'SYSTEM', '2025-08-03 14:40:07.960', 'SYSTEM', '2025-08-03 14:40:07.960');
INSERT INTO `content_content_tag` VALUES (370, 222, 380, 'SYSTEM', '2025-08-03 14:40:07.960', 'SYSTEM', '2025-08-03 14:40:07.960');
INSERT INTO `content_content_tag` VALUES (371, 222, 385, 'SYSTEM', '2025-08-03 14:40:07.960', 'SYSTEM', '2025-08-03 14:40:07.960');
INSERT INTO `content_content_tag` VALUES (372, 223, 286, 'SYSTEM', '2025-08-03 14:40:07.977', 'SYSTEM', '2025-08-03 14:40:07.977');
INSERT INTO `content_content_tag` VALUES (373, 223, 59, 'SYSTEM', '2025-08-03 14:40:07.977', 'SYSTEM', '2025-08-03 14:40:07.977');
INSERT INTO `content_content_tag` VALUES (374, 223, 60, 'SYSTEM', '2025-08-03 14:40:07.977', 'SYSTEM', '2025-08-03 14:40:07.977');
INSERT INTO `content_content_tag` VALUES (375, 223, 288, 'SYSTEM', '2025-08-03 14:40:07.977', 'SYSTEM', '2025-08-03 14:40:07.977');
INSERT INTO `content_content_tag` VALUES (376, 223, 4, 'SYSTEM', '2025-08-03 14:40:07.977', 'SYSTEM', '2025-08-03 14:40:07.977');
INSERT INTO `content_content_tag` VALUES (377, 223, 378, 'SYSTEM', '2025-08-03 14:40:07.977', 'SYSTEM', '2025-08-03 14:40:07.977');
INSERT INTO `content_content_tag` VALUES (378, 223, 384, 'SYSTEM', '2025-08-03 14:40:07.977', 'SYSTEM', '2025-08-03 14:40:07.977');
INSERT INTO `content_content_tag` VALUES (379, 224, 246, 'SYSTEM', '2025-08-03 14:40:07.993', 'SYSTEM', '2025-08-03 14:40:07.993');
INSERT INTO `content_content_tag` VALUES (380, 224, 123, 'SYSTEM', '2025-08-03 14:40:07.993', 'SYSTEM', '2025-08-03 14:40:07.993');
INSERT INTO `content_content_tag` VALUES (381, 224, 243, 'SYSTEM', '2025-08-03 14:40:07.993', 'SYSTEM', '2025-08-03 14:40:07.993');
INSERT INTO `content_content_tag` VALUES (382, 224, 185, 'SYSTEM', '2025-08-03 14:40:07.993', 'SYSTEM', '2025-08-03 14:40:07.993');
INSERT INTO `content_content_tag` VALUES (383, 224, 253, 'SYSTEM', '2025-08-03 14:40:07.993', 'SYSTEM', '2025-08-03 14:40:07.993');
INSERT INTO `content_content_tag` VALUES (384, 224, 351, 'SYSTEM', '2025-08-03 14:40:07.993', 'SYSTEM', '2025-08-03 14:40:07.993');
INSERT INTO `content_content_tag` VALUES (385, 224, 387, 'SYSTEM', '2025-08-03 14:40:07.993', 'SYSTEM', '2025-08-03 14:40:07.993');
INSERT INTO `content_content_tag` VALUES (386, 225, 149, 'SYSTEM', '2025-08-03 14:40:08.014', 'SYSTEM', '2025-08-03 14:40:08.014');
INSERT INTO `content_content_tag` VALUES (387, 225, 83, 'SYSTEM', '2025-08-03 14:40:08.014', 'SYSTEM', '2025-08-03 14:40:08.014');
INSERT INTO `content_content_tag` VALUES (388, 225, 162, 'SYSTEM', '2025-08-03 14:40:08.014', 'SYSTEM', '2025-08-03 14:40:08.014');
INSERT INTO `content_content_tag` VALUES (389, 225, 104, 'SYSTEM', '2025-08-03 14:40:08.014', 'SYSTEM', '2025-08-03 14:40:08.014');
INSERT INTO `content_content_tag` VALUES (390, 225, 55, 'SYSTEM', '2025-08-03 14:40:08.014', 'SYSTEM', '2025-08-03 14:40:08.014');
INSERT INTO `content_content_tag` VALUES (391, 225, 359, 'SYSTEM', '2025-08-03 14:40:08.014', 'SYSTEM', '2025-08-03 14:40:08.014');
INSERT INTO `content_content_tag` VALUES (392, 225, 386, 'SYSTEM', '2025-08-03 14:40:08.014', 'SYSTEM', '2025-08-03 14:40:08.014');
INSERT INTO `content_content_tag` VALUES (393, 226, 16, 'SYSTEM', '2025-08-03 14:40:08.041', 'SYSTEM', '2025-08-03 14:40:08.041');
INSERT INTO `content_content_tag` VALUES (394, 226, 322, 'SYSTEM', '2025-08-03 14:40:08.041', 'SYSTEM', '2025-08-03 14:40:08.041');
INSERT INTO `content_content_tag` VALUES (395, 226, 280, 'SYSTEM', '2025-08-03 14:40:08.041', 'SYSTEM', '2025-08-03 14:40:08.041');
INSERT INTO `content_content_tag` VALUES (396, 226, 156, 'SYSTEM', '2025-08-03 14:40:08.041', 'SYSTEM', '2025-08-03 14:40:08.041');
INSERT INTO `content_content_tag` VALUES (397, 226, 3, 'SYSTEM', '2025-08-03 14:40:08.041', 'SYSTEM', '2025-08-03 14:40:08.041');
INSERT INTO `content_content_tag` VALUES (398, 226, 377, 'SYSTEM', '2025-08-03 14:40:08.041', 'SYSTEM', '2025-08-03 14:40:08.041');
INSERT INTO `content_content_tag` VALUES (399, 226, 383, 'SYSTEM', '2025-08-03 14:40:08.041', 'SYSTEM', '2025-08-03 14:40:08.041');
INSERT INTO `content_content_tag` VALUES (400, 228, 206, 'SYSTEM', '2025-08-03 14:40:08.072', 'SYSTEM', '2025-08-03 14:40:08.072');
INSERT INTO `content_content_tag` VALUES (401, 228, 258, 'SYSTEM', '2025-08-03 14:40:08.072', 'SYSTEM', '2025-08-03 14:40:08.072');
INSERT INTO `content_content_tag` VALUES (402, 228, 317, 'SYSTEM', '2025-08-03 14:40:08.072', 'SYSTEM', '2025-08-03 14:40:08.072');
INSERT INTO `content_content_tag` VALUES (403, 228, 88, 'SYSTEM', '2025-08-03 14:40:08.072', 'SYSTEM', '2025-08-03 14:40:08.072');
INSERT INTO `content_content_tag` VALUES (404, 228, 183, 'SYSTEM', '2025-08-03 14:40:08.072', 'SYSTEM', '2025-08-03 14:40:08.072');
INSERT INTO `content_content_tag` VALUES (405, 228, 359, 'SYSTEM', '2025-08-03 14:40:08.072', 'SYSTEM', '2025-08-03 14:40:08.072');
INSERT INTO `content_content_tag` VALUES (406, 228, 385, 'SYSTEM', '2025-08-03 14:40:08.072', 'SYSTEM', '2025-08-03 14:40:08.072');
INSERT INTO `content_content_tag` VALUES (407, 229, 168, 'SYSTEM', '2025-08-03 14:40:08.072', 'SYSTEM', '2025-08-03 14:40:08.072');
INSERT INTO `content_content_tag` VALUES (408, 229, 311, 'SYSTEM', '2025-08-03 14:40:08.072', 'SYSTEM', '2025-08-03 14:40:08.072');
INSERT INTO `content_content_tag` VALUES (409, 229, 115, 'SYSTEM', '2025-08-03 14:40:08.072', 'SYSTEM', '2025-08-03 14:40:08.072');
INSERT INTO `content_content_tag` VALUES (410, 229, 160, 'SYSTEM', '2025-08-03 14:40:08.072', 'SYSTEM', '2025-08-03 14:40:08.072');
INSERT INTO `content_content_tag` VALUES (411, 229, 51, 'SYSTEM', '2025-08-03 14:40:08.072', 'SYSTEM', '2025-08-03 14:40:08.072');
INSERT INTO `content_content_tag` VALUES (412, 229, 361, 'SYSTEM', '2025-08-03 14:40:08.072', 'SYSTEM', '2025-08-03 14:40:08.072');
INSERT INTO `content_content_tag` VALUES (413, 229, 387, 'SYSTEM', '2025-08-03 14:40:08.072', 'SYSTEM', '2025-08-03 14:40:08.072');
INSERT INTO `content_content_tag` VALUES (414, 230, 312, 'SYSTEM', '2025-08-03 14:40:08.120', 'SYSTEM', '2025-08-03 14:40:08.120');
INSERT INTO `content_content_tag` VALUES (415, 230, 220, 'SYSTEM', '2025-08-03 14:40:08.120', 'SYSTEM', '2025-08-03 14:40:08.120');
INSERT INTO `content_content_tag` VALUES (416, 230, 254, 'SYSTEM', '2025-08-03 14:40:08.120', 'SYSTEM', '2025-08-03 14:40:08.120');
INSERT INTO `content_content_tag` VALUES (417, 230, 11, 'SYSTEM', '2025-08-03 14:40:08.120', 'SYSTEM', '2025-08-03 14:40:08.120');
INSERT INTO `content_content_tag` VALUES (418, 230, 59, 'SYSTEM', '2025-08-03 14:40:08.120', 'SYSTEM', '2025-08-03 14:40:08.120');
INSERT INTO `content_content_tag` VALUES (419, 230, 361, 'SYSTEM', '2025-08-03 14:40:08.120', 'SYSTEM', '2025-08-03 14:40:08.120');
INSERT INTO `content_content_tag` VALUES (420, 230, 387, 'SYSTEM', '2025-08-03 14:40:08.120', 'SYSTEM', '2025-08-03 14:40:08.120');
INSERT INTO `content_content_tag` VALUES (421, 231, 272, 'SYSTEM', '2025-08-03 14:40:09.988', 'SYSTEM', '2025-08-03 14:40:09.988');
INSERT INTO `content_content_tag` VALUES (422, 231, 45, 'SYSTEM', '2025-08-03 14:40:09.988', 'SYSTEM', '2025-08-03 14:40:09.988');
INSERT INTO `content_content_tag` VALUES (423, 231, 222, 'SYSTEM', '2025-08-03 14:40:09.988', 'SYSTEM', '2025-08-03 14:40:09.988');
INSERT INTO `content_content_tag` VALUES (424, 231, 157, 'SYSTEM', '2025-08-03 14:40:09.988', 'SYSTEM', '2025-08-03 14:40:09.988');
INSERT INTO `content_content_tag` VALUES (425, 231, 50, 'SYSTEM', '2025-08-03 14:40:09.988', 'SYSTEM', '2025-08-03 14:40:09.988');
INSERT INTO `content_content_tag` VALUES (426, 231, 380, 'SYSTEM', '2025-08-03 14:40:09.988', 'SYSTEM', '2025-08-03 14:40:09.988');
INSERT INTO `content_content_tag` VALUES (427, 231, 382, 'SYSTEM', '2025-08-03 14:40:09.988', 'SYSTEM', '2025-08-03 14:40:09.988');
INSERT INTO `content_content_tag` VALUES (428, 232, 53, 'SYSTEM', '2025-08-03 14:40:10.029', 'SYSTEM', '2025-08-03 14:40:10.029');
INSERT INTO `content_content_tag` VALUES (429, 232, 171, 'SYSTEM', '2025-08-03 14:40:10.029', 'SYSTEM', '2025-08-03 14:40:10.029');
INSERT INTO `content_content_tag` VALUES (430, 232, 255, 'SYSTEM', '2025-08-03 14:40:10.029', 'SYSTEM', '2025-08-03 14:40:10.029');
INSERT INTO `content_content_tag` VALUES (431, 232, 1, 'SYSTEM', '2025-08-03 14:40:10.029', 'SYSTEM', '2025-08-03 14:40:10.029');
INSERT INTO `content_content_tag` VALUES (432, 232, 124, 'SYSTEM', '2025-08-03 14:40:10.029', 'SYSTEM', '2025-08-03 14:40:10.029');
INSERT INTO `content_content_tag` VALUES (433, 232, 376, 'SYSTEM', '2025-08-03 14:40:10.029', 'SYSTEM', '2025-08-03 14:40:10.029');
INSERT INTO `content_content_tag` VALUES (434, 232, 383, 'SYSTEM', '2025-08-03 14:40:10.029', 'SYSTEM', '2025-08-03 14:40:10.029');
INSERT INTO `content_content_tag` VALUES (435, 233, 109, 'SYSTEM', '2025-08-03 14:40:10.059', 'SYSTEM', '2025-08-03 14:40:10.059');
INSERT INTO `content_content_tag` VALUES (436, 233, 170, 'SYSTEM', '2025-08-03 14:40:10.059', 'SYSTEM', '2025-08-03 14:40:10.059');
INSERT INTO `content_content_tag` VALUES (437, 233, 69, 'SYSTEM', '2025-08-03 14:40:10.059', 'SYSTEM', '2025-08-03 14:40:10.059');
INSERT INTO `content_content_tag` VALUES (438, 233, 257, 'SYSTEM', '2025-08-03 14:40:10.059', 'SYSTEM', '2025-08-03 14:40:10.059');
INSERT INTO `content_content_tag` VALUES (439, 233, 151, 'SYSTEM', '2025-08-03 14:40:10.059', 'SYSTEM', '2025-08-03 14:40:10.059');
INSERT INTO `content_content_tag` VALUES (440, 233, 360, 'SYSTEM', '2025-08-03 14:40:10.059', 'SYSTEM', '2025-08-03 14:40:10.059');
INSERT INTO `content_content_tag` VALUES (441, 233, 382, 'SYSTEM', '2025-08-03 14:40:10.059', 'SYSTEM', '2025-08-03 14:40:10.059');
INSERT INTO `content_content_tag` VALUES (442, 234, 110, 'SYSTEM', '2025-08-03 14:40:10.075', 'SYSTEM', '2025-08-03 14:40:10.075');
INSERT INTO `content_content_tag` VALUES (443, 234, 337, 'SYSTEM', '2025-08-03 14:40:10.075', 'SYSTEM', '2025-08-03 14:40:10.075');
INSERT INTO `content_content_tag` VALUES (444, 234, 298, 'SYSTEM', '2025-08-03 14:40:10.075', 'SYSTEM', '2025-08-03 14:40:10.075');
INSERT INTO `content_content_tag` VALUES (445, 234, 336, 'SYSTEM', '2025-08-03 14:40:10.075', 'SYSTEM', '2025-08-03 14:40:10.075');
INSERT INTO `content_content_tag` VALUES (446, 234, 17, 'SYSTEM', '2025-08-03 14:40:10.075', 'SYSTEM', '2025-08-03 14:40:10.075');
INSERT INTO `content_content_tag` VALUES (447, 234, 365, 'SYSTEM', '2025-08-03 14:40:10.075', 'SYSTEM', '2025-08-03 14:40:10.075');
INSERT INTO `content_content_tag` VALUES (448, 234, 382, 'SYSTEM', '2025-08-03 14:40:10.075', 'SYSTEM', '2025-08-03 14:40:10.075');
INSERT INTO `content_content_tag` VALUES (449, 235, 273, 'SYSTEM', '2025-08-03 14:40:10.101', 'SYSTEM', '2025-08-03 14:40:10.101');
INSERT INTO `content_content_tag` VALUES (450, 235, 131, 'SYSTEM', '2025-08-03 14:40:10.101', 'SYSTEM', '2025-08-03 14:40:10.101');
INSERT INTO `content_content_tag` VALUES (451, 235, 217, 'SYSTEM', '2025-08-03 14:40:10.101', 'SYSTEM', '2025-08-03 14:40:10.101');
INSERT INTO `content_content_tag` VALUES (452, 235, 77, 'SYSTEM', '2025-08-03 14:40:10.101', 'SYSTEM', '2025-08-03 14:40:10.101');
INSERT INTO `content_content_tag` VALUES (453, 235, 147, 'SYSTEM', '2025-08-03 14:40:10.101', 'SYSTEM', '2025-08-03 14:40:10.101');
INSERT INTO `content_content_tag` VALUES (454, 235, 377, 'SYSTEM', '2025-08-03 14:40:10.101', 'SYSTEM', '2025-08-03 14:40:10.101');
INSERT INTO `content_content_tag` VALUES (455, 235, 381, 'SYSTEM', '2025-08-03 14:40:10.101', 'SYSTEM', '2025-08-03 14:40:10.101');
INSERT INTO `content_content_tag` VALUES (456, 236, 195, 'SYSTEM', '2025-08-03 14:40:10.122', 'SYSTEM', '2025-08-03 14:40:10.122');
INSERT INTO `content_content_tag` VALUES (457, 236, 322, 'SYSTEM', '2025-08-03 14:40:10.122', 'SYSTEM', '2025-08-03 14:40:10.122');
INSERT INTO `content_content_tag` VALUES (458, 236, 258, 'SYSTEM', '2025-08-03 14:40:10.122', 'SYSTEM', '2025-08-03 14:40:10.122');
INSERT INTO `content_content_tag` VALUES (459, 236, 214, 'SYSTEM', '2025-08-03 14:40:10.122', 'SYSTEM', '2025-08-03 14:40:10.122');
INSERT INTO `content_content_tag` VALUES (460, 236, 240, 'SYSTEM', '2025-08-03 14:40:10.123', 'SYSTEM', '2025-08-03 14:40:10.123');
INSERT INTO `content_content_tag` VALUES (461, 236, 377, 'SYSTEM', '2025-08-03 14:40:10.123', 'SYSTEM', '2025-08-03 14:40:10.123');
INSERT INTO `content_content_tag` VALUES (462, 236, 381, 'SYSTEM', '2025-08-03 14:40:10.123', 'SYSTEM', '2025-08-03 14:40:10.123');
INSERT INTO `content_content_tag` VALUES (463, 238, 325, 'SYSTEM', '2025-08-03 14:40:10.149', 'SYSTEM', '2025-08-03 14:40:10.149');
INSERT INTO `content_content_tag` VALUES (464, 238, 69, 'SYSTEM', '2025-08-03 14:40:10.149', 'SYSTEM', '2025-08-03 14:40:10.149');
INSERT INTO `content_content_tag` VALUES (465, 238, 317, 'SYSTEM', '2025-08-03 14:40:10.149', 'SYSTEM', '2025-08-03 14:40:10.149');
INSERT INTO `content_content_tag` VALUES (466, 238, 259, 'SYSTEM', '2025-08-03 14:40:10.149', 'SYSTEM', '2025-08-03 14:40:10.149');
INSERT INTO `content_content_tag` VALUES (467, 238, 91, 'SYSTEM', '2025-08-03 14:40:10.149', 'SYSTEM', '2025-08-03 14:40:10.149');
INSERT INTO `content_content_tag` VALUES (468, 238, 354, 'SYSTEM', '2025-08-03 14:40:10.149', 'SYSTEM', '2025-08-03 14:40:10.149');
INSERT INTO `content_content_tag` VALUES (469, 238, 381, 'SYSTEM', '2025-08-03 14:40:10.149', 'SYSTEM', '2025-08-03 14:40:10.149');
INSERT INTO `content_content_tag` VALUES (470, 239, 13, 'SYSTEM', '2025-08-03 14:40:10.166', 'SYSTEM', '2025-08-03 14:40:10.166');
INSERT INTO `content_content_tag` VALUES (471, 239, 239, 'SYSTEM', '2025-08-03 14:40:10.166', 'SYSTEM', '2025-08-03 14:40:10.166');
INSERT INTO `content_content_tag` VALUES (472, 239, 82, 'SYSTEM', '2025-08-03 14:40:10.166', 'SYSTEM', '2025-08-03 14:40:10.166');
INSERT INTO `content_content_tag` VALUES (473, 239, 198, 'SYSTEM', '2025-08-03 14:40:10.166', 'SYSTEM', '2025-08-03 14:40:10.166');
INSERT INTO `content_content_tag` VALUES (474, 239, 17, 'SYSTEM', '2025-08-03 14:40:10.166', 'SYSTEM', '2025-08-03 14:40:10.166');
INSERT INTO `content_content_tag` VALUES (475, 239, 375, 'SYSTEM', '2025-08-03 14:40:10.166', 'SYSTEM', '2025-08-03 14:40:10.166');
INSERT INTO `content_content_tag` VALUES (476, 239, 382, 'SYSTEM', '2025-08-03 14:40:10.166', 'SYSTEM', '2025-08-03 14:40:10.166');
INSERT INTO `content_content_tag` VALUES (477, 240, 156, 'SYSTEM', '2025-08-03 14:40:10.181', 'SYSTEM', '2025-08-03 14:40:10.181');
INSERT INTO `content_content_tag` VALUES (478, 240, 117, 'SYSTEM', '2025-08-03 14:40:10.181', 'SYSTEM', '2025-08-03 14:40:10.181');
INSERT INTO `content_content_tag` VALUES (479, 240, 316, 'SYSTEM', '2025-08-03 14:40:10.181', 'SYSTEM', '2025-08-03 14:40:10.181');
INSERT INTO `content_content_tag` VALUES (480, 240, 80, 'SYSTEM', '2025-08-03 14:40:10.181', 'SYSTEM', '2025-08-03 14:40:10.181');
INSERT INTO `content_content_tag` VALUES (481, 240, 128, 'SYSTEM', '2025-08-03 14:40:10.181', 'SYSTEM', '2025-08-03 14:40:10.181');
INSERT INTO `content_content_tag` VALUES (482, 240, 369, 'SYSTEM', '2025-08-03 14:40:10.181', 'SYSTEM', '2025-08-03 14:40:10.181');
INSERT INTO `content_content_tag` VALUES (483, 240, 381, 'SYSTEM', '2025-08-03 14:40:10.181', 'SYSTEM', '2025-08-03 14:40:10.181');
INSERT INTO `content_content_tag` VALUES (484, 241, 59, 'SYSTEM', '2025-08-03 14:40:12.001', 'SYSTEM', '2025-08-03 14:40:12.001');
INSERT INTO `content_content_tag` VALUES (485, 241, 42, 'SYSTEM', '2025-08-03 14:40:12.001', 'SYSTEM', '2025-08-03 14:40:12.001');
INSERT INTO `content_content_tag` VALUES (486, 241, 183, 'SYSTEM', '2025-08-03 14:40:12.001', 'SYSTEM', '2025-08-03 14:40:12.001');
INSERT INTO `content_content_tag` VALUES (487, 241, 137, 'SYSTEM', '2025-08-03 14:40:12.001', 'SYSTEM', '2025-08-03 14:40:12.001');
INSERT INTO `content_content_tag` VALUES (488, 241, 163, 'SYSTEM', '2025-08-03 14:40:12.001', 'SYSTEM', '2025-08-03 14:40:12.001');
INSERT INTO `content_content_tag` VALUES (489, 241, 352, 'SYSTEM', '2025-08-03 14:40:12.001', 'SYSTEM', '2025-08-03 14:40:12.001');
INSERT INTO `content_content_tag` VALUES (490, 241, 386, 'SYSTEM', '2025-08-03 14:40:12.001', 'SYSTEM', '2025-08-03 14:40:12.001');
INSERT INTO `content_content_tag` VALUES (491, 242, 170, 'SYSTEM', '2025-08-03 14:40:12.035', 'SYSTEM', '2025-08-03 14:40:12.035');
INSERT INTO `content_content_tag` VALUES (492, 242, 103, 'SYSTEM', '2025-08-03 14:40:12.035', 'SYSTEM', '2025-08-03 14:40:12.035');
INSERT INTO `content_content_tag` VALUES (493, 242, 183, 'SYSTEM', '2025-08-03 14:40:12.035', 'SYSTEM', '2025-08-03 14:40:12.035');
INSERT INTO `content_content_tag` VALUES (494, 242, 115, 'SYSTEM', '2025-08-03 14:40:12.035', 'SYSTEM', '2025-08-03 14:40:12.035');
INSERT INTO `content_content_tag` VALUES (495, 242, 303, 'SYSTEM', '2025-08-03 14:40:12.035', 'SYSTEM', '2025-08-03 14:40:12.035');
INSERT INTO `content_content_tag` VALUES (496, 242, 351, 'SYSTEM', '2025-08-03 14:40:12.035', 'SYSTEM', '2025-08-03 14:40:12.035');
INSERT INTO `content_content_tag` VALUES (497, 242, 384, 'SYSTEM', '2025-08-03 14:40:12.035', 'SYSTEM', '2025-08-03 14:40:12.035');
INSERT INTO `content_content_tag` VALUES (498, 243, 13, 'SYSTEM', '2025-08-03 14:40:12.051', 'SYSTEM', '2025-08-03 14:40:12.051');
INSERT INTO `content_content_tag` VALUES (499, 243, 217, 'SYSTEM', '2025-08-03 14:40:12.051', 'SYSTEM', '2025-08-03 14:40:12.051');
INSERT INTO `content_content_tag` VALUES (500, 243, 58, 'SYSTEM', '2025-08-03 14:40:12.051', 'SYSTEM', '2025-08-03 14:40:12.051');
INSERT INTO `content_content_tag` VALUES (501, 243, 169, 'SYSTEM', '2025-08-03 14:40:12.051', 'SYSTEM', '2025-08-03 14:40:12.051');
INSERT INTO `content_content_tag` VALUES (502, 243, 288, 'SYSTEM', '2025-08-03 14:40:12.051', 'SYSTEM', '2025-08-03 14:40:12.051');
INSERT INTO `content_content_tag` VALUES (503, 243, 351, 'SYSTEM', '2025-08-03 14:40:12.051', 'SYSTEM', '2025-08-03 14:40:12.051');
INSERT INTO `content_content_tag` VALUES (504, 243, 386, 'SYSTEM', '2025-08-03 14:40:12.051', 'SYSTEM', '2025-08-03 14:40:12.051');
INSERT INTO `content_content_tag` VALUES (505, 244, 219, 'SYSTEM', '2025-08-03 14:40:12.084', 'SYSTEM', '2025-08-03 14:40:12.084');
INSERT INTO `content_content_tag` VALUES (506, 244, 169, 'SYSTEM', '2025-08-03 14:40:12.084', 'SYSTEM', '2025-08-03 14:40:12.084');
INSERT INTO `content_content_tag` VALUES (507, 244, 67, 'SYSTEM', '2025-08-03 14:40:12.084', 'SYSTEM', '2025-08-03 14:40:12.084');
INSERT INTO `content_content_tag` VALUES (508, 244, 128, 'SYSTEM', '2025-08-03 14:40:12.084', 'SYSTEM', '2025-08-03 14:40:12.084');
INSERT INTO `content_content_tag` VALUES (509, 244, 52, 'SYSTEM', '2025-08-03 14:40:12.084', 'SYSTEM', '2025-08-03 14:40:12.084');
INSERT INTO `content_content_tag` VALUES (510, 244, 353, 'SYSTEM', '2025-08-03 14:40:12.084', 'SYSTEM', '2025-08-03 14:40:12.084');
INSERT INTO `content_content_tag` VALUES (511, 244, 382, 'SYSTEM', '2025-08-03 14:40:12.084', 'SYSTEM', '2025-08-03 14:40:12.084');
INSERT INTO `content_content_tag` VALUES (512, 245, 44, 'SYSTEM', '2025-08-03 14:40:12.115', 'SYSTEM', '2025-08-03 14:40:12.115');
INSERT INTO `content_content_tag` VALUES (513, 245, 278, 'SYSTEM', '2025-08-03 14:40:12.115', 'SYSTEM', '2025-08-03 14:40:12.115');
INSERT INTO `content_content_tag` VALUES (514, 245, 203, 'SYSTEM', '2025-08-03 14:40:12.115', 'SYSTEM', '2025-08-03 14:40:12.115');
INSERT INTO `content_content_tag` VALUES (515, 245, 281, 'SYSTEM', '2025-08-03 14:40:12.115', 'SYSTEM', '2025-08-03 14:40:12.115');
INSERT INTO `content_content_tag` VALUES (516, 245, 260, 'SYSTEM', '2025-08-03 14:40:12.115', 'SYSTEM', '2025-08-03 14:40:12.115');
INSERT INTO `content_content_tag` VALUES (517, 245, 366, 'SYSTEM', '2025-08-03 14:40:12.115', 'SYSTEM', '2025-08-03 14:40:12.115');
INSERT INTO `content_content_tag` VALUES (518, 245, 382, 'SYSTEM', '2025-08-03 14:40:12.115', 'SYSTEM', '2025-08-03 14:40:12.115');
INSERT INTO `content_content_tag` VALUES (519, 246, 58, 'SYSTEM', '2025-08-03 14:40:12.131', 'SYSTEM', '2025-08-03 14:40:12.131');
INSERT INTO `content_content_tag` VALUES (520, 246, 159, 'SYSTEM', '2025-08-03 14:40:12.131', 'SYSTEM', '2025-08-03 14:40:12.131');
INSERT INTO `content_content_tag` VALUES (521, 246, 57, 'SYSTEM', '2025-08-03 14:40:12.131', 'SYSTEM', '2025-08-03 14:40:12.131');
INSERT INTO `content_content_tag` VALUES (522, 246, 76, 'SYSTEM', '2025-08-03 14:40:12.131', 'SYSTEM', '2025-08-03 14:40:12.131');
INSERT INTO `content_content_tag` VALUES (523, 246, 179, 'SYSTEM', '2025-08-03 14:40:12.131', 'SYSTEM', '2025-08-03 14:40:12.131');
INSERT INTO `content_content_tag` VALUES (524, 246, 367, 'SYSTEM', '2025-08-03 14:40:12.131', 'SYSTEM', '2025-08-03 14:40:12.131');
INSERT INTO `content_content_tag` VALUES (525, 246, 385, 'SYSTEM', '2025-08-03 14:40:12.131', 'SYSTEM', '2025-08-03 14:40:12.131');
INSERT INTO `content_content_tag` VALUES (526, 248, 83, 'SYSTEM', '2025-08-03 14:40:12.163', 'SYSTEM', '2025-08-03 14:40:12.163');
INSERT INTO `content_content_tag` VALUES (527, 248, 327, 'SYSTEM', '2025-08-03 14:40:12.163', 'SYSTEM', '2025-08-03 14:40:12.163');
INSERT INTO `content_content_tag` VALUES (528, 248, 166, 'SYSTEM', '2025-08-03 14:40:12.163', 'SYSTEM', '2025-08-03 14:40:12.163');
INSERT INTO `content_content_tag` VALUES (529, 248, 77, 'SYSTEM', '2025-08-03 14:40:12.163', 'SYSTEM', '2025-08-03 14:40:12.163');
INSERT INTO `content_content_tag` VALUES (530, 248, 244, 'SYSTEM', '2025-08-03 14:40:12.163', 'SYSTEM', '2025-08-03 14:40:12.163');
INSERT INTO `content_content_tag` VALUES (531, 248, 354, 'SYSTEM', '2025-08-03 14:40:12.163', 'SYSTEM', '2025-08-03 14:40:12.163');
INSERT INTO `content_content_tag` VALUES (532, 248, 387, 'SYSTEM', '2025-08-03 14:40:12.163', 'SYSTEM', '2025-08-03 14:40:12.163');
INSERT INTO `content_content_tag` VALUES (533, 249, 105, 'SYSTEM', '2025-08-03 14:40:12.183', 'SYSTEM', '2025-08-03 14:40:12.183');
INSERT INTO `content_content_tag` VALUES (534, 249, 305, 'SYSTEM', '2025-08-03 14:40:12.183', 'SYSTEM', '2025-08-03 14:40:12.183');
INSERT INTO `content_content_tag` VALUES (535, 249, 316, 'SYSTEM', '2025-08-03 14:40:12.183', 'SYSTEM', '2025-08-03 14:40:12.183');
INSERT INTO `content_content_tag` VALUES (536, 249, 312, 'SYSTEM', '2025-08-03 14:40:12.183', 'SYSTEM', '2025-08-03 14:40:12.183');
INSERT INTO `content_content_tag` VALUES (537, 249, 344, 'SYSTEM', '2025-08-03 14:40:12.183', 'SYSTEM', '2025-08-03 14:40:12.183');
INSERT INTO `content_content_tag` VALUES (538, 249, 359, 'SYSTEM', '2025-08-03 14:40:12.183', 'SYSTEM', '2025-08-03 14:40:12.183');
INSERT INTO `content_content_tag` VALUES (539, 249, 384, 'SYSTEM', '2025-08-03 14:40:12.183', 'SYSTEM', '2025-08-03 14:40:12.183');
INSERT INTO `content_content_tag` VALUES (540, 250, 266, 'SYSTEM', '2025-08-03 14:40:12.210', 'SYSTEM', '2025-08-03 14:40:12.210');
INSERT INTO `content_content_tag` VALUES (541, 250, 272, 'SYSTEM', '2025-08-03 14:40:12.210', 'SYSTEM', '2025-08-03 14:40:12.210');
INSERT INTO `content_content_tag` VALUES (542, 250, 201, 'SYSTEM', '2025-08-03 14:40:12.210', 'SYSTEM', '2025-08-03 14:40:12.210');
INSERT INTO `content_content_tag` VALUES (543, 250, 6, 'SYSTEM', '2025-08-03 14:40:12.210', 'SYSTEM', '2025-08-03 14:40:12.210');
INSERT INTO `content_content_tag` VALUES (544, 250, 62, 'SYSTEM', '2025-08-03 14:40:12.210', 'SYSTEM', '2025-08-03 14:40:12.210');
INSERT INTO `content_content_tag` VALUES (545, 250, 380, 'SYSTEM', '2025-08-03 14:40:12.210', 'SYSTEM', '2025-08-03 14:40:12.210');
INSERT INTO `content_content_tag` VALUES (546, 250, 382, 'SYSTEM', '2025-08-03 14:40:12.210', 'SYSTEM', '2025-08-03 14:40:12.210');
INSERT INTO `content_content_tag` VALUES (547, 251, 99, 'SYSTEM', '2025-08-03 14:40:14.074', 'SYSTEM', '2025-08-03 14:40:14.074');
INSERT INTO `content_content_tag` VALUES (548, 251, 284, 'SYSTEM', '2025-08-03 14:40:14.074', 'SYSTEM', '2025-08-03 14:40:14.074');
INSERT INTO `content_content_tag` VALUES (549, 251, 79, 'SYSTEM', '2025-08-03 14:40:14.074', 'SYSTEM', '2025-08-03 14:40:14.074');
INSERT INTO `content_content_tag` VALUES (550, 251, 223, 'SYSTEM', '2025-08-03 14:40:14.074', 'SYSTEM', '2025-08-03 14:40:14.074');
INSERT INTO `content_content_tag` VALUES (551, 251, 74, 'SYSTEM', '2025-08-03 14:40:14.074', 'SYSTEM', '2025-08-03 14:40:14.074');
INSERT INTO `content_content_tag` VALUES (552, 251, 367, 'SYSTEM', '2025-08-03 14:40:14.074', 'SYSTEM', '2025-08-03 14:40:14.074');
INSERT INTO `content_content_tag` VALUES (553, 251, 381, 'SYSTEM', '2025-08-03 14:40:14.074', 'SYSTEM', '2025-08-03 14:40:14.074');
INSERT INTO `content_content_tag` VALUES (554, 252, 283, 'SYSTEM', '2025-08-03 14:40:14.106', 'SYSTEM', '2025-08-03 14:40:14.106');
INSERT INTO `content_content_tag` VALUES (555, 252, 206, 'SYSTEM', '2025-08-03 14:40:14.106', 'SYSTEM', '2025-08-03 14:40:14.106');
INSERT INTO `content_content_tag` VALUES (556, 252, 75, 'SYSTEM', '2025-08-03 14:40:14.106', 'SYSTEM', '2025-08-03 14:40:14.106');
INSERT INTO `content_content_tag` VALUES (557, 252, 199, 'SYSTEM', '2025-08-03 14:40:14.106', 'SYSTEM', '2025-08-03 14:40:14.106');
INSERT INTO `content_content_tag` VALUES (558, 252, 285, 'SYSTEM', '2025-08-03 14:40:14.106', 'SYSTEM', '2025-08-03 14:40:14.106');
INSERT INTO `content_content_tag` VALUES (559, 252, 374, 'SYSTEM', '2025-08-03 14:40:14.106', 'SYSTEM', '2025-08-03 14:40:14.106');
INSERT INTO `content_content_tag` VALUES (560, 252, 381, 'SYSTEM', '2025-08-03 14:40:14.106', 'SYSTEM', '2025-08-03 14:40:14.106');
INSERT INTO `content_content_tag` VALUES (561, 253, 289, 'SYSTEM', '2025-08-03 14:40:14.121', 'SYSTEM', '2025-08-03 14:40:14.121');
INSERT INTO `content_content_tag` VALUES (562, 253, 108, 'SYSTEM', '2025-08-03 14:40:14.121', 'SYSTEM', '2025-08-03 14:40:14.121');
INSERT INTO `content_content_tag` VALUES (563, 253, 197, 'SYSTEM', '2025-08-03 14:40:14.121', 'SYSTEM', '2025-08-03 14:40:14.121');
INSERT INTO `content_content_tag` VALUES (564, 253, 330, 'SYSTEM', '2025-08-03 14:40:14.121', 'SYSTEM', '2025-08-03 14:40:14.121');
INSERT INTO `content_content_tag` VALUES (565, 253, 29, 'SYSTEM', '2025-08-03 14:40:14.121', 'SYSTEM', '2025-08-03 14:40:14.121');
INSERT INTO `content_content_tag` VALUES (566, 253, 380, 'SYSTEM', '2025-08-03 14:40:14.121', 'SYSTEM', '2025-08-03 14:40:14.121');
INSERT INTO `content_content_tag` VALUES (567, 253, 386, 'SYSTEM', '2025-08-03 14:40:14.121', 'SYSTEM', '2025-08-03 14:40:14.121');
INSERT INTO `content_content_tag` VALUES (568, 254, 89, 'SYSTEM', '2025-08-03 14:40:14.138', 'SYSTEM', '2025-08-03 14:40:14.138');
INSERT INTO `content_content_tag` VALUES (569, 254, 230, 'SYSTEM', '2025-08-03 14:40:14.138', 'SYSTEM', '2025-08-03 14:40:14.138');
INSERT INTO `content_content_tag` VALUES (570, 254, 345, 'SYSTEM', '2025-08-03 14:40:14.138', 'SYSTEM', '2025-08-03 14:40:14.138');
INSERT INTO `content_content_tag` VALUES (571, 254, 275, 'SYSTEM', '2025-08-03 14:40:14.138', 'SYSTEM', '2025-08-03 14:40:14.138');
INSERT INTO `content_content_tag` VALUES (572, 254, 306, 'SYSTEM', '2025-08-03 14:40:14.138', 'SYSTEM', '2025-08-03 14:40:14.138');
INSERT INTO `content_content_tag` VALUES (573, 254, 370, 'SYSTEM', '2025-08-03 14:40:14.138', 'SYSTEM', '2025-08-03 14:40:14.138');
INSERT INTO `content_content_tag` VALUES (574, 254, 382, 'SYSTEM', '2025-08-03 14:40:14.138', 'SYSTEM', '2025-08-03 14:40:14.138');
INSERT INTO `content_content_tag` VALUES (575, 255, 234, 'SYSTEM', '2025-08-03 14:40:14.154', 'SYSTEM', '2025-08-03 14:40:14.154');
INSERT INTO `content_content_tag` VALUES (576, 255, 189, 'SYSTEM', '2025-08-03 14:40:14.154', 'SYSTEM', '2025-08-03 14:40:14.154');
INSERT INTO `content_content_tag` VALUES (577, 255, 264, 'SYSTEM', '2025-08-03 14:40:14.154', 'SYSTEM', '2025-08-03 14:40:14.154');
INSERT INTO `content_content_tag` VALUES (578, 255, 280, 'SYSTEM', '2025-08-03 14:40:14.154', 'SYSTEM', '2025-08-03 14:40:14.154');
INSERT INTO `content_content_tag` VALUES (579, 255, 253, 'SYSTEM', '2025-08-03 14:40:14.154', 'SYSTEM', '2025-08-03 14:40:14.154');
INSERT INTO `content_content_tag` VALUES (580, 255, 372, 'SYSTEM', '2025-08-03 14:40:14.154', 'SYSTEM', '2025-08-03 14:40:14.154');
INSERT INTO `content_content_tag` VALUES (581, 255, 386, 'SYSTEM', '2025-08-03 14:40:14.154', 'SYSTEM', '2025-08-03 14:40:14.154');
INSERT INTO `content_content_tag` VALUES (582, 256, 344, 'SYSTEM', '2025-08-03 14:40:14.186', 'SYSTEM', '2025-08-03 14:40:14.186');
INSERT INTO `content_content_tag` VALUES (583, 256, 4, 'SYSTEM', '2025-08-03 14:40:14.186', 'SYSTEM', '2025-08-03 14:40:14.186');
INSERT INTO `content_content_tag` VALUES (584, 256, 326, 'SYSTEM', '2025-08-03 14:40:14.186', 'SYSTEM', '2025-08-03 14:40:14.186');
INSERT INTO `content_content_tag` VALUES (585, 256, 12, 'SYSTEM', '2025-08-03 14:40:14.186', 'SYSTEM', '2025-08-03 14:40:14.186');
INSERT INTO `content_content_tag` VALUES (586, 256, 28, 'SYSTEM', '2025-08-03 14:40:14.186', 'SYSTEM', '2025-08-03 14:40:14.186');
INSERT INTO `content_content_tag` VALUES (587, 256, 377, 'SYSTEM', '2025-08-03 14:40:14.186', 'SYSTEM', '2025-08-03 14:40:14.186');
INSERT INTO `content_content_tag` VALUES (588, 256, 383, 'SYSTEM', '2025-08-03 14:40:14.186', 'SYSTEM', '2025-08-03 14:40:14.186');
INSERT INTO `content_content_tag` VALUES (589, 258, 120, 'SYSTEM', '2025-08-03 14:40:14.217', 'SYSTEM', '2025-08-03 14:40:14.217');
INSERT INTO `content_content_tag` VALUES (590, 258, 258, 'SYSTEM', '2025-08-03 14:40:14.217', 'SYSTEM', '2025-08-03 14:40:14.217');
INSERT INTO `content_content_tag` VALUES (591, 258, 202, 'SYSTEM', '2025-08-03 14:40:14.217', 'SYSTEM', '2025-08-03 14:40:14.217');
INSERT INTO `content_content_tag` VALUES (592, 258, 130, 'SYSTEM', '2025-08-03 14:40:14.217', 'SYSTEM', '2025-08-03 14:40:14.217');
INSERT INTO `content_content_tag` VALUES (593, 258, 268, 'SYSTEM', '2025-08-03 14:40:14.217', 'SYSTEM', '2025-08-03 14:40:14.217');
INSERT INTO `content_content_tag` VALUES (594, 258, 354, 'SYSTEM', '2025-08-03 14:40:14.217', 'SYSTEM', '2025-08-03 14:40:14.217');
INSERT INTO `content_content_tag` VALUES (595, 258, 382, 'SYSTEM', '2025-08-03 14:40:14.217', 'SYSTEM', '2025-08-03 14:40:14.217');
INSERT INTO `content_content_tag` VALUES (596, 259, 287, 'SYSTEM', '2025-08-03 14:40:14.234', 'SYSTEM', '2025-08-03 14:40:14.234');
INSERT INTO `content_content_tag` VALUES (597, 259, 82, 'SYSTEM', '2025-08-03 14:40:14.234', 'SYSTEM', '2025-08-03 14:40:14.234');
INSERT INTO `content_content_tag` VALUES (598, 259, 331, 'SYSTEM', '2025-08-03 14:40:14.234', 'SYSTEM', '2025-08-03 14:40:14.234');
INSERT INTO `content_content_tag` VALUES (599, 259, 159, 'SYSTEM', '2025-08-03 14:40:14.234', 'SYSTEM', '2025-08-03 14:40:14.234');
INSERT INTO `content_content_tag` VALUES (600, 259, 30, 'SYSTEM', '2025-08-03 14:40:14.234', 'SYSTEM', '2025-08-03 14:40:14.234');
INSERT INTO `content_content_tag` VALUES (601, 259, 369, 'SYSTEM', '2025-08-03 14:40:14.234', 'SYSTEM', '2025-08-03 14:40:14.234');
INSERT INTO `content_content_tag` VALUES (602, 259, 384, 'SYSTEM', '2025-08-03 14:40:14.234', 'SYSTEM', '2025-08-03 14:40:14.234');
INSERT INTO `content_content_tag` VALUES (603, 260, 46, 'SYSTEM', '2025-08-03 14:40:14.250', 'SYSTEM', '2025-08-03 14:40:14.250');
INSERT INTO `content_content_tag` VALUES (604, 260, 103, 'SYSTEM', '2025-08-03 14:40:14.250', 'SYSTEM', '2025-08-03 14:40:14.250');
INSERT INTO `content_content_tag` VALUES (605, 260, 99, 'SYSTEM', '2025-08-03 14:40:14.250', 'SYSTEM', '2025-08-03 14:40:14.250');
INSERT INTO `content_content_tag` VALUES (606, 260, 41, 'SYSTEM', '2025-08-03 14:40:14.250', 'SYSTEM', '2025-08-03 14:40:14.250');
INSERT INTO `content_content_tag` VALUES (607, 260, 19, 'SYSTEM', '2025-08-03 14:40:14.250', 'SYSTEM', '2025-08-03 14:40:14.250');
INSERT INTO `content_content_tag` VALUES (608, 260, 355, 'SYSTEM', '2025-08-03 14:40:14.250', 'SYSTEM', '2025-08-03 14:40:14.250');
INSERT INTO `content_content_tag` VALUES (609, 260, 381, 'SYSTEM', '2025-08-03 14:40:14.250', 'SYSTEM', '2025-08-03 14:40:14.250');
INSERT INTO `content_content_tag` VALUES (610, 261, 200, 'SYSTEM', '2025-08-03 14:40:16.105', 'SYSTEM', '2025-08-03 14:40:16.105');
INSERT INTO `content_content_tag` VALUES (611, 261, 265, 'SYSTEM', '2025-08-03 14:40:16.105', 'SYSTEM', '2025-08-03 14:40:16.105');
INSERT INTO `content_content_tag` VALUES (612, 261, 332, 'SYSTEM', '2025-08-03 14:40:16.105', 'SYSTEM', '2025-08-03 14:40:16.105');
INSERT INTO `content_content_tag` VALUES (613, 261, 108, 'SYSTEM', '2025-08-03 14:40:16.105', 'SYSTEM', '2025-08-03 14:40:16.105');
INSERT INTO `content_content_tag` VALUES (614, 261, 115, 'SYSTEM', '2025-08-03 14:40:16.105', 'SYSTEM', '2025-08-03 14:40:16.105');
INSERT INTO `content_content_tag` VALUES (615, 261, 368, 'SYSTEM', '2025-08-03 14:40:16.105', 'SYSTEM', '2025-08-03 14:40:16.105');
INSERT INTO `content_content_tag` VALUES (616, 261, 386, 'SYSTEM', '2025-08-03 14:40:16.105', 'SYSTEM', '2025-08-03 14:40:16.105');
INSERT INTO `content_content_tag` VALUES (617, 262, 152, 'SYSTEM', '2025-08-03 14:40:16.122', 'SYSTEM', '2025-08-03 14:40:16.122');
INSERT INTO `content_content_tag` VALUES (618, 262, 4, 'SYSTEM', '2025-08-03 14:40:16.122', 'SYSTEM', '2025-08-03 14:40:16.122');
INSERT INTO `content_content_tag` VALUES (619, 262, 81, 'SYSTEM', '2025-08-03 14:40:16.122', 'SYSTEM', '2025-08-03 14:40:16.122');
INSERT INTO `content_content_tag` VALUES (620, 262, 12, 'SYSTEM', '2025-08-03 14:40:16.122', 'SYSTEM', '2025-08-03 14:40:16.122');
INSERT INTO `content_content_tag` VALUES (621, 262, 228, 'SYSTEM', '2025-08-03 14:40:16.122', 'SYSTEM', '2025-08-03 14:40:16.122');
INSERT INTO `content_content_tag` VALUES (622, 262, 351, 'SYSTEM', '2025-08-03 14:40:16.122', 'SYSTEM', '2025-08-03 14:40:16.122');
INSERT INTO `content_content_tag` VALUES (623, 262, 386, 'SYSTEM', '2025-08-03 14:40:16.122', 'SYSTEM', '2025-08-03 14:40:16.122');
INSERT INTO `content_content_tag` VALUES (624, 263, 7, 'SYSTEM', '2025-08-03 14:40:16.139', 'SYSTEM', '2025-08-03 14:40:16.139');
INSERT INTO `content_content_tag` VALUES (625, 263, 182, 'SYSTEM', '2025-08-03 14:40:16.139', 'SYSTEM', '2025-08-03 14:40:16.139');
INSERT INTO `content_content_tag` VALUES (626, 263, 271, 'SYSTEM', '2025-08-03 14:40:16.139', 'SYSTEM', '2025-08-03 14:40:16.139');
INSERT INTO `content_content_tag` VALUES (627, 263, 309, 'SYSTEM', '2025-08-03 14:40:16.139', 'SYSTEM', '2025-08-03 14:40:16.139');
INSERT INTO `content_content_tag` VALUES (628, 263, 257, 'SYSTEM', '2025-08-03 14:40:16.139', 'SYSTEM', '2025-08-03 14:40:16.139');
INSERT INTO `content_content_tag` VALUES (629, 263, 380, 'SYSTEM', '2025-08-03 14:40:16.139', 'SYSTEM', '2025-08-03 14:40:16.139');
INSERT INTO `content_content_tag` VALUES (630, 263, 387, 'SYSTEM', '2025-08-03 14:40:16.139', 'SYSTEM', '2025-08-03 14:40:16.139');
INSERT INTO `content_content_tag` VALUES (631, 264, 94, 'SYSTEM', '2025-08-03 14:40:16.171', 'SYSTEM', '2025-08-03 14:40:16.171');
INSERT INTO `content_content_tag` VALUES (632, 264, 213, 'SYSTEM', '2025-08-03 14:40:16.171', 'SYSTEM', '2025-08-03 14:40:16.171');
INSERT INTO `content_content_tag` VALUES (633, 264, 73, 'SYSTEM', '2025-08-03 14:40:16.171', 'SYSTEM', '2025-08-03 14:40:16.171');
INSERT INTO `content_content_tag` VALUES (634, 264, 171, 'SYSTEM', '2025-08-03 14:40:16.171', 'SYSTEM', '2025-08-03 14:40:16.171');
INSERT INTO `content_content_tag` VALUES (635, 264, 38, 'SYSTEM', '2025-08-03 14:40:16.171', 'SYSTEM', '2025-08-03 14:40:16.171');
INSERT INTO `content_content_tag` VALUES (636, 264, 370, 'SYSTEM', '2025-08-03 14:40:16.171', 'SYSTEM', '2025-08-03 14:40:16.171');
INSERT INTO `content_content_tag` VALUES (637, 264, 385, 'SYSTEM', '2025-08-03 14:40:16.171', 'SYSTEM', '2025-08-03 14:40:16.171');
INSERT INTO `content_content_tag` VALUES (638, 265, 126, 'SYSTEM', '2025-08-03 14:40:16.202', 'SYSTEM', '2025-08-03 14:40:16.202');
INSERT INTO `content_content_tag` VALUES (639, 265, 64, 'SYSTEM', '2025-08-03 14:40:16.202', 'SYSTEM', '2025-08-03 14:40:16.202');
INSERT INTO `content_content_tag` VALUES (640, 265, 216, 'SYSTEM', '2025-08-03 14:40:16.202', 'SYSTEM', '2025-08-03 14:40:16.202');
INSERT INTO `content_content_tag` VALUES (641, 265, 89, 'SYSTEM', '2025-08-03 14:40:16.202', 'SYSTEM', '2025-08-03 14:40:16.202');
INSERT INTO `content_content_tag` VALUES (642, 265, 124, 'SYSTEM', '2025-08-03 14:40:16.202', 'SYSTEM', '2025-08-03 14:40:16.202');
INSERT INTO `content_content_tag` VALUES (643, 265, 356, 'SYSTEM', '2025-08-03 14:40:16.202', 'SYSTEM', '2025-08-03 14:40:16.202');
INSERT INTO `content_content_tag` VALUES (644, 265, 381, 'SYSTEM', '2025-08-03 14:40:16.202', 'SYSTEM', '2025-08-03 14:40:16.202');
INSERT INTO `content_content_tag` VALUES (645, 266, 102, 'SYSTEM', '2025-08-03 14:40:16.233', 'SYSTEM', '2025-08-03 14:40:16.233');
INSERT INTO `content_content_tag` VALUES (646, 266, 152, 'SYSTEM', '2025-08-03 14:40:16.233', 'SYSTEM', '2025-08-03 14:40:16.233');
INSERT INTO `content_content_tag` VALUES (647, 266, 346, 'SYSTEM', '2025-08-03 14:40:16.233', 'SYSTEM', '2025-08-03 14:40:16.233');
INSERT INTO `content_content_tag` VALUES (648, 266, 189, 'SYSTEM', '2025-08-03 14:40:16.233', 'SYSTEM', '2025-08-03 14:40:16.233');
INSERT INTO `content_content_tag` VALUES (649, 266, 53, 'SYSTEM', '2025-08-03 14:40:16.233', 'SYSTEM', '2025-08-03 14:40:16.233');
INSERT INTO `content_content_tag` VALUES (650, 266, 352, 'SYSTEM', '2025-08-03 14:40:16.233', 'SYSTEM', '2025-08-03 14:40:16.233');
INSERT INTO `content_content_tag` VALUES (651, 266, 386, 'SYSTEM', '2025-08-03 14:40:16.233', 'SYSTEM', '2025-08-03 14:40:16.233');
INSERT INTO `content_content_tag` VALUES (652, 268, 194, 'SYSTEM', '2025-08-03 14:40:16.266', 'SYSTEM', '2025-08-03 14:40:16.266');
INSERT INTO `content_content_tag` VALUES (653, 268, 159, 'SYSTEM', '2025-08-03 14:40:16.266', 'SYSTEM', '2025-08-03 14:40:16.266');
INSERT INTO `content_content_tag` VALUES (654, 268, 232, 'SYSTEM', '2025-08-03 14:40:16.266', 'SYSTEM', '2025-08-03 14:40:16.266');
INSERT INTO `content_content_tag` VALUES (655, 268, 94, 'SYSTEM', '2025-08-03 14:40:16.266', 'SYSTEM', '2025-08-03 14:40:16.266');
INSERT INTO `content_content_tag` VALUES (656, 268, 217, 'SYSTEM', '2025-08-03 14:40:16.266', 'SYSTEM', '2025-08-03 14:40:16.266');
INSERT INTO `content_content_tag` VALUES (657, 268, 372, 'SYSTEM', '2025-08-03 14:40:16.266', 'SYSTEM', '2025-08-03 14:40:16.266');
INSERT INTO `content_content_tag` VALUES (658, 268, 385, 'SYSTEM', '2025-08-03 14:40:16.266', 'SYSTEM', '2025-08-03 14:40:16.266');
INSERT INTO `content_content_tag` VALUES (659, 269, 332, 'SYSTEM', '2025-08-03 14:40:16.282', 'SYSTEM', '2025-08-03 14:40:16.282');
INSERT INTO `content_content_tag` VALUES (660, 269, 343, 'SYSTEM', '2025-08-03 14:40:16.282', 'SYSTEM', '2025-08-03 14:40:16.282');
INSERT INTO `content_content_tag` VALUES (661, 269, 161, 'SYSTEM', '2025-08-03 14:40:16.282', 'SYSTEM', '2025-08-03 14:40:16.282');
INSERT INTO `content_content_tag` VALUES (662, 269, 267, 'SYSTEM', '2025-08-03 14:40:16.282', 'SYSTEM', '2025-08-03 14:40:16.282');
INSERT INTO `content_content_tag` VALUES (663, 269, 288, 'SYSTEM', '2025-08-03 14:40:16.282', 'SYSTEM', '2025-08-03 14:40:16.282');
INSERT INTO `content_content_tag` VALUES (664, 269, 357, 'SYSTEM', '2025-08-03 14:40:16.282', 'SYSTEM', '2025-08-03 14:40:16.282');
INSERT INTO `content_content_tag` VALUES (665, 269, 383, 'SYSTEM', '2025-08-03 14:40:16.282', 'SYSTEM', '2025-08-03 14:40:16.282');
INSERT INTO `content_content_tag` VALUES (666, 270, 3, 'SYSTEM', '2025-08-03 14:40:16.298', 'SYSTEM', '2025-08-03 14:40:16.298');
INSERT INTO `content_content_tag` VALUES (667, 270, 64, 'SYSTEM', '2025-08-03 14:40:16.298', 'SYSTEM', '2025-08-03 14:40:16.298');
INSERT INTO `content_content_tag` VALUES (668, 270, 230, 'SYSTEM', '2025-08-03 14:40:16.298', 'SYSTEM', '2025-08-03 14:40:16.298');
INSERT INTO `content_content_tag` VALUES (669, 270, 339, 'SYSTEM', '2025-08-03 14:40:16.298', 'SYSTEM', '2025-08-03 14:40:16.298');
INSERT INTO `content_content_tag` VALUES (670, 270, 183, 'SYSTEM', '2025-08-03 14:40:16.298', 'SYSTEM', '2025-08-03 14:40:16.298');
INSERT INTO `content_content_tag` VALUES (671, 270, 379, 'SYSTEM', '2025-08-03 14:40:16.298', 'SYSTEM', '2025-08-03 14:40:16.298');
INSERT INTO `content_content_tag` VALUES (672, 270, 382, 'SYSTEM', '2025-08-03 14:40:16.298', 'SYSTEM', '2025-08-03 14:40:16.298');
INSERT INTO `content_content_tag` VALUES (673, 271, 22, 'SYSTEM', '2025-08-03 14:40:18.075', 'SYSTEM', '2025-08-03 14:40:18.075');
INSERT INTO `content_content_tag` VALUES (674, 271, 56, 'SYSTEM', '2025-08-03 14:40:18.075', 'SYSTEM', '2025-08-03 14:40:18.075');
INSERT INTO `content_content_tag` VALUES (675, 271, 115, 'SYSTEM', '2025-08-03 14:40:18.075', 'SYSTEM', '2025-08-03 14:40:18.075');
INSERT INTO `content_content_tag` VALUES (676, 271, 345, 'SYSTEM', '2025-08-03 14:40:18.075', 'SYSTEM', '2025-08-03 14:40:18.075');
INSERT INTO `content_content_tag` VALUES (677, 271, 133, 'SYSTEM', '2025-08-03 14:40:18.075', 'SYSTEM', '2025-08-03 14:40:18.075');
INSERT INTO `content_content_tag` VALUES (678, 271, 361, 'SYSTEM', '2025-08-03 14:40:18.075', 'SYSTEM', '2025-08-03 14:40:18.075');
INSERT INTO `content_content_tag` VALUES (679, 271, 386, 'SYSTEM', '2025-08-03 14:40:18.075', 'SYSTEM', '2025-08-03 14:40:18.075');
INSERT INTO `content_content_tag` VALUES (680, 272, 52, 'SYSTEM', '2025-08-03 14:40:18.107', 'SYSTEM', '2025-08-03 14:40:18.107');
INSERT INTO `content_content_tag` VALUES (681, 272, 256, 'SYSTEM', '2025-08-03 14:40:18.107', 'SYSTEM', '2025-08-03 14:40:18.107');
INSERT INTO `content_content_tag` VALUES (682, 272, 202, 'SYSTEM', '2025-08-03 14:40:18.107', 'SYSTEM', '2025-08-03 14:40:18.107');
INSERT INTO `content_content_tag` VALUES (683, 272, 40, 'SYSTEM', '2025-08-03 14:40:18.107', 'SYSTEM', '2025-08-03 14:40:18.107');
INSERT INTO `content_content_tag` VALUES (684, 272, 62, 'SYSTEM', '2025-08-03 14:40:18.107', 'SYSTEM', '2025-08-03 14:40:18.107');
INSERT INTO `content_content_tag` VALUES (685, 272, 375, 'SYSTEM', '2025-08-03 14:40:18.107', 'SYSTEM', '2025-08-03 14:40:18.107');
INSERT INTO `content_content_tag` VALUES (686, 272, 384, 'SYSTEM', '2025-08-03 14:40:18.107', 'SYSTEM', '2025-08-03 14:40:18.107');
INSERT INTO `content_content_tag` VALUES (687, 273, 148, 'SYSTEM', '2025-08-03 14:40:18.123', 'SYSTEM', '2025-08-03 14:40:18.123');
INSERT INTO `content_content_tag` VALUES (688, 273, 332, 'SYSTEM', '2025-08-03 14:40:18.123', 'SYSTEM', '2025-08-03 14:40:18.123');
INSERT INTO `content_content_tag` VALUES (689, 273, 28, 'SYSTEM', '2025-08-03 14:40:18.123', 'SYSTEM', '2025-08-03 14:40:18.123');
INSERT INTO `content_content_tag` VALUES (690, 273, 131, 'SYSTEM', '2025-08-03 14:40:18.123', 'SYSTEM', '2025-08-03 14:40:18.123');
INSERT INTO `content_content_tag` VALUES (691, 273, 295, 'SYSTEM', '2025-08-03 14:40:18.123', 'SYSTEM', '2025-08-03 14:40:18.123');
INSERT INTO `content_content_tag` VALUES (692, 273, 369, 'SYSTEM', '2025-08-03 14:40:18.123', 'SYSTEM', '2025-08-03 14:40:18.123');
INSERT INTO `content_content_tag` VALUES (693, 273, 385, 'SYSTEM', '2025-08-03 14:40:18.123', 'SYSTEM', '2025-08-03 14:40:18.123');
INSERT INTO `content_content_tag` VALUES (694, 274, 115, 'SYSTEM', '2025-08-03 14:40:18.138', 'SYSTEM', '2025-08-03 14:40:18.138');
INSERT INTO `content_content_tag` VALUES (695, 274, 78, 'SYSTEM', '2025-08-03 14:40:18.138', 'SYSTEM', '2025-08-03 14:40:18.138');
INSERT INTO `content_content_tag` VALUES (696, 274, 290, 'SYSTEM', '2025-08-03 14:40:18.138', 'SYSTEM', '2025-08-03 14:40:18.138');
INSERT INTO `content_content_tag` VALUES (697, 274, 124, 'SYSTEM', '2025-08-03 14:40:18.138', 'SYSTEM', '2025-08-03 14:40:18.138');
INSERT INTO `content_content_tag` VALUES (698, 274, 17, 'SYSTEM', '2025-08-03 14:40:18.138', 'SYSTEM', '2025-08-03 14:40:18.138');
INSERT INTO `content_content_tag` VALUES (699, 274, 378, 'SYSTEM', '2025-08-03 14:40:18.138', 'SYSTEM', '2025-08-03 14:40:18.138');
INSERT INTO `content_content_tag` VALUES (700, 274, 383, 'SYSTEM', '2025-08-03 14:40:18.138', 'SYSTEM', '2025-08-03 14:40:18.138');
INSERT INTO `content_content_tag` VALUES (701, 275, 324, 'SYSTEM', '2025-08-03 14:40:18.154', 'SYSTEM', '2025-08-03 14:40:18.154');
INSERT INTO `content_content_tag` VALUES (702, 275, 65, 'SYSTEM', '2025-08-03 14:40:18.154', 'SYSTEM', '2025-08-03 14:40:18.154');
INSERT INTO `content_content_tag` VALUES (703, 275, 278, 'SYSTEM', '2025-08-03 14:40:18.154', 'SYSTEM', '2025-08-03 14:40:18.154');
INSERT INTO `content_content_tag` VALUES (704, 275, 108, 'SYSTEM', '2025-08-03 14:40:18.154', 'SYSTEM', '2025-08-03 14:40:18.154');
INSERT INTO `content_content_tag` VALUES (705, 275, 268, 'SYSTEM', '2025-08-03 14:40:18.154', 'SYSTEM', '2025-08-03 14:40:18.154');
INSERT INTO `content_content_tag` VALUES (706, 275, 360, 'SYSTEM', '2025-08-03 14:40:18.154', 'SYSTEM', '2025-08-03 14:40:18.154');
INSERT INTO `content_content_tag` VALUES (707, 275, 385, 'SYSTEM', '2025-08-03 14:40:18.154', 'SYSTEM', '2025-08-03 14:40:18.154');
INSERT INTO `content_content_tag` VALUES (708, 276, 191, 'SYSTEM', '2025-08-03 14:40:18.186', 'SYSTEM', '2025-08-03 14:40:18.186');
INSERT INTO `content_content_tag` VALUES (709, 276, 42, 'SYSTEM', '2025-08-03 14:40:18.186', 'SYSTEM', '2025-08-03 14:40:18.186');
INSERT INTO `content_content_tag` VALUES (710, 276, 135, 'SYSTEM', '2025-08-03 14:40:18.186', 'SYSTEM', '2025-08-03 14:40:18.186');
INSERT INTO `content_content_tag` VALUES (711, 276, 187, 'SYSTEM', '2025-08-03 14:40:18.186', 'SYSTEM', '2025-08-03 14:40:18.186');
INSERT INTO `content_content_tag` VALUES (712, 276, 26, 'SYSTEM', '2025-08-03 14:40:18.186', 'SYSTEM', '2025-08-03 14:40:18.186');
INSERT INTO `content_content_tag` VALUES (713, 276, 365, 'SYSTEM', '2025-08-03 14:40:18.186', 'SYSTEM', '2025-08-03 14:40:18.186');
INSERT INTO `content_content_tag` VALUES (714, 276, 386, 'SYSTEM', '2025-08-03 14:40:18.186', 'SYSTEM', '2025-08-03 14:40:18.186');
INSERT INTO `content_content_tag` VALUES (715, 278, 56, 'SYSTEM', '2025-08-03 14:40:18.218', 'SYSTEM', '2025-08-03 14:40:18.218');
INSERT INTO `content_content_tag` VALUES (716, 278, 110, 'SYSTEM', '2025-08-03 14:40:18.218', 'SYSTEM', '2025-08-03 14:40:18.218');
INSERT INTO `content_content_tag` VALUES (717, 278, 95, 'SYSTEM', '2025-08-03 14:40:18.218', 'SYSTEM', '2025-08-03 14:40:18.218');
INSERT INTO `content_content_tag` VALUES (718, 278, 322, 'SYSTEM', '2025-08-03 14:40:18.218', 'SYSTEM', '2025-08-03 14:40:18.218');
INSERT INTO `content_content_tag` VALUES (719, 278, 300, 'SYSTEM', '2025-08-03 14:40:18.218', 'SYSTEM', '2025-08-03 14:40:18.218');
INSERT INTO `content_content_tag` VALUES (720, 278, 355, 'SYSTEM', '2025-08-03 14:40:18.218', 'SYSTEM', '2025-08-03 14:40:18.218');
INSERT INTO `content_content_tag` VALUES (721, 278, 387, 'SYSTEM', '2025-08-03 14:40:18.218', 'SYSTEM', '2025-08-03 14:40:18.218');
INSERT INTO `content_content_tag` VALUES (722, 279, 188, 'SYSTEM', '2025-08-03 14:40:18.234', 'SYSTEM', '2025-08-03 14:40:18.234');
INSERT INTO `content_content_tag` VALUES (723, 279, 3, 'SYSTEM', '2025-08-03 14:40:18.234', 'SYSTEM', '2025-08-03 14:40:18.234');
INSERT INTO `content_content_tag` VALUES (724, 279, 331, 'SYSTEM', '2025-08-03 14:40:18.234', 'SYSTEM', '2025-08-03 14:40:18.234');
INSERT INTO `content_content_tag` VALUES (725, 279, 127, 'SYSTEM', '2025-08-03 14:40:18.234', 'SYSTEM', '2025-08-03 14:40:18.234');
INSERT INTO `content_content_tag` VALUES (726, 279, 181, 'SYSTEM', '2025-08-03 14:40:18.234', 'SYSTEM', '2025-08-03 14:40:18.234');
INSERT INTO `content_content_tag` VALUES (727, 279, 353, 'SYSTEM', '2025-08-03 14:40:18.234', 'SYSTEM', '2025-08-03 14:40:18.234');
INSERT INTO `content_content_tag` VALUES (728, 279, 387, 'SYSTEM', '2025-08-03 14:40:18.234', 'SYSTEM', '2025-08-03 14:40:18.234');
INSERT INTO `content_content_tag` VALUES (729, 280, 61, 'SYSTEM', '2025-08-03 14:40:18.250', 'SYSTEM', '2025-08-03 14:40:18.250');
INSERT INTO `content_content_tag` VALUES (730, 280, 15, 'SYSTEM', '2025-08-03 14:40:18.250', 'SYSTEM', '2025-08-03 14:40:18.250');
INSERT INTO `content_content_tag` VALUES (731, 280, 338, 'SYSTEM', '2025-08-03 14:40:18.250', 'SYSTEM', '2025-08-03 14:40:18.250');
INSERT INTO `content_content_tag` VALUES (732, 280, 167, 'SYSTEM', '2025-08-03 14:40:18.250', 'SYSTEM', '2025-08-03 14:40:18.250');
INSERT INTO `content_content_tag` VALUES (733, 280, 23, 'SYSTEM', '2025-08-03 14:40:18.250', 'SYSTEM', '2025-08-03 14:40:18.250');
INSERT INTO `content_content_tag` VALUES (734, 280, 375, 'SYSTEM', '2025-08-03 14:40:18.250', 'SYSTEM', '2025-08-03 14:40:18.250');
INSERT INTO `content_content_tag` VALUES (735, 280, 387, 'SYSTEM', '2025-08-03 14:40:18.250', 'SYSTEM', '2025-08-03 14:40:18.250');
INSERT INTO `content_content_tag` VALUES (736, 281, 176, 'SYSTEM', '2025-08-03 15:05:57.144', 'SYSTEM', '2025-08-03 15:05:57.144');
INSERT INTO `content_content_tag` VALUES (737, 281, 106, 'SYSTEM', '2025-08-03 15:05:57.144', 'SYSTEM', '2025-08-03 15:05:57.144');
INSERT INTO `content_content_tag` VALUES (738, 281, 59, 'SYSTEM', '2025-08-03 15:05:57.145', 'SYSTEM', '2025-08-03 15:05:57.145');
INSERT INTO `content_content_tag` VALUES (739, 281, 209, 'SYSTEM', '2025-08-03 15:05:57.145', 'SYSTEM', '2025-08-03 15:05:57.145');
INSERT INTO `content_content_tag` VALUES (740, 281, 170, 'SYSTEM', '2025-08-03 15:05:57.145', 'SYSTEM', '2025-08-03 15:05:57.145');
INSERT INTO `content_content_tag` VALUES (741, 281, 368, 'SYSTEM', '2025-08-03 15:05:57.145', 'SYSTEM', '2025-08-03 15:05:57.145');
INSERT INTO `content_content_tag` VALUES (742, 281, 381, 'SYSTEM', '2025-08-03 15:05:57.145', 'SYSTEM', '2025-08-03 15:05:57.145');
INSERT INTO `content_content_tag` VALUES (743, 282, 10, 'SYSTEM', '2025-08-03 15:05:57.183', 'SYSTEM', '2025-08-03 15:05:57.183');
INSERT INTO `content_content_tag` VALUES (744, 282, 320, 'SYSTEM', '2025-08-03 15:05:57.183', 'SYSTEM', '2025-08-03 15:05:57.183');
INSERT INTO `content_content_tag` VALUES (745, 282, 259, 'SYSTEM', '2025-08-03 15:05:57.183', 'SYSTEM', '2025-08-03 15:05:57.183');
INSERT INTO `content_content_tag` VALUES (746, 282, 225, 'SYSTEM', '2025-08-03 15:05:57.183', 'SYSTEM', '2025-08-03 15:05:57.183');
INSERT INTO `content_content_tag` VALUES (747, 282, 43, 'SYSTEM', '2025-08-03 15:05:57.183', 'SYSTEM', '2025-08-03 15:05:57.183');
INSERT INTO `content_content_tag` VALUES (748, 282, 361, 'SYSTEM', '2025-08-03 15:05:57.183', 'SYSTEM', '2025-08-03 15:05:57.183');
INSERT INTO `content_content_tag` VALUES (749, 282, 385, 'SYSTEM', '2025-08-03 15:05:57.183', 'SYSTEM', '2025-08-03 15:05:57.183');
INSERT INTO `content_content_tag` VALUES (750, 283, 296, 'SYSTEM', '2025-08-03 15:05:57.208', 'SYSTEM', '2025-08-03 15:05:57.208');
INSERT INTO `content_content_tag` VALUES (751, 283, 165, 'SYSTEM', '2025-08-03 15:05:57.208', 'SYSTEM', '2025-08-03 15:05:57.208');
INSERT INTO `content_content_tag` VALUES (752, 283, 308, 'SYSTEM', '2025-08-03 15:05:57.208', 'SYSTEM', '2025-08-03 15:05:57.208');
INSERT INTO `content_content_tag` VALUES (753, 283, 327, 'SYSTEM', '2025-08-03 15:05:57.208', 'SYSTEM', '2025-08-03 15:05:57.208');
INSERT INTO `content_content_tag` VALUES (754, 283, 48, 'SYSTEM', '2025-08-03 15:05:57.208', 'SYSTEM', '2025-08-03 15:05:57.208');
INSERT INTO `content_content_tag` VALUES (755, 283, 360, 'SYSTEM', '2025-08-03 15:05:57.208', 'SYSTEM', '2025-08-03 15:05:57.208');
INSERT INTO `content_content_tag` VALUES (756, 283, 386, 'SYSTEM', '2025-08-03 15:05:57.208', 'SYSTEM', '2025-08-03 15:05:57.208');
INSERT INTO `content_content_tag` VALUES (757, 284, 20, 'SYSTEM', '2025-08-03 15:05:57.260', 'SYSTEM', '2025-08-03 15:05:57.260');
INSERT INTO `content_content_tag` VALUES (758, 284, 295, 'SYSTEM', '2025-08-03 15:05:57.260', 'SYSTEM', '2025-08-03 15:05:57.260');
INSERT INTO `content_content_tag` VALUES (759, 284, 238, 'SYSTEM', '2025-08-03 15:05:57.260', 'SYSTEM', '2025-08-03 15:05:57.260');
INSERT INTO `content_content_tag` VALUES (760, 284, 151, 'SYSTEM', '2025-08-03 15:05:57.260', 'SYSTEM', '2025-08-03 15:05:57.260');
INSERT INTO `content_content_tag` VALUES (761, 284, 71, 'SYSTEM', '2025-08-03 15:05:57.260', 'SYSTEM', '2025-08-03 15:05:57.260');
INSERT INTO `content_content_tag` VALUES (762, 284, 360, 'SYSTEM', '2025-08-03 15:05:57.260', 'SYSTEM', '2025-08-03 15:05:57.260');
INSERT INTO `content_content_tag` VALUES (763, 284, 385, 'SYSTEM', '2025-08-03 15:05:57.260', 'SYSTEM', '2025-08-03 15:05:57.260');
INSERT INTO `content_content_tag` VALUES (764, 285, 135, 'SYSTEM', '2025-08-03 15:05:57.286', 'SYSTEM', '2025-08-03 15:05:57.286');
INSERT INTO `content_content_tag` VALUES (765, 285, 267, 'SYSTEM', '2025-08-03 15:05:57.286', 'SYSTEM', '2025-08-03 15:05:57.286');
INSERT INTO `content_content_tag` VALUES (766, 285, 23, 'SYSTEM', '2025-08-03 15:05:57.286', 'SYSTEM', '2025-08-03 15:05:57.286');
INSERT INTO `content_content_tag` VALUES (767, 285, 38, 'SYSTEM', '2025-08-03 15:05:57.286', 'SYSTEM', '2025-08-03 15:05:57.286');
INSERT INTO `content_content_tag` VALUES (768, 285, 14, 'SYSTEM', '2025-08-03 15:05:57.286', 'SYSTEM', '2025-08-03 15:05:57.286');
INSERT INTO `content_content_tag` VALUES (769, 285, 366, 'SYSTEM', '2025-08-03 15:05:57.286', 'SYSTEM', '2025-08-03 15:05:57.286');
INSERT INTO `content_content_tag` VALUES (770, 285, 387, 'SYSTEM', '2025-08-03 15:05:57.286', 'SYSTEM', '2025-08-03 15:05:57.286');
INSERT INTO `content_content_tag` VALUES (771, 286, 86, 'SYSTEM', '2025-08-03 15:05:57.303', 'SYSTEM', '2025-08-03 15:05:57.303');
INSERT INTO `content_content_tag` VALUES (772, 286, 219, 'SYSTEM', '2025-08-03 15:05:57.303', 'SYSTEM', '2025-08-03 15:05:57.303');
INSERT INTO `content_content_tag` VALUES (773, 286, 198, 'SYSTEM', '2025-08-03 15:05:57.303', 'SYSTEM', '2025-08-03 15:05:57.303');
INSERT INTO `content_content_tag` VALUES (774, 286, 48, 'SYSTEM', '2025-08-03 15:05:57.303', 'SYSTEM', '2025-08-03 15:05:57.303');
INSERT INTO `content_content_tag` VALUES (775, 286, 329, 'SYSTEM', '2025-08-03 15:05:57.303', 'SYSTEM', '2025-08-03 15:05:57.303');
INSERT INTO `content_content_tag` VALUES (776, 286, 375, 'SYSTEM', '2025-08-03 15:05:57.303', 'SYSTEM', '2025-08-03 15:05:57.303');
INSERT INTO `content_content_tag` VALUES (777, 286, 381, 'SYSTEM', '2025-08-03 15:05:57.303', 'SYSTEM', '2025-08-03 15:05:57.303');
INSERT INTO `content_content_tag` VALUES (778, 288, 274, 'SYSTEM', '2025-08-03 15:05:57.364', 'SYSTEM', '2025-08-03 15:05:57.364');
INSERT INTO `content_content_tag` VALUES (779, 288, 89, 'SYSTEM', '2025-08-03 15:05:57.364', 'SYSTEM', '2025-08-03 15:05:57.364');
INSERT INTO `content_content_tag` VALUES (780, 288, 179, 'SYSTEM', '2025-08-03 15:05:57.364', 'SYSTEM', '2025-08-03 15:05:57.364');
INSERT INTO `content_content_tag` VALUES (781, 288, 18, 'SYSTEM', '2025-08-03 15:05:57.364', 'SYSTEM', '2025-08-03 15:05:57.364');
INSERT INTO `content_content_tag` VALUES (782, 288, 321, 'SYSTEM', '2025-08-03 15:05:57.364', 'SYSTEM', '2025-08-03 15:05:57.364');
INSERT INTO `content_content_tag` VALUES (783, 288, 371, 'SYSTEM', '2025-08-03 15:05:57.364', 'SYSTEM', '2025-08-03 15:05:57.364');
INSERT INTO `content_content_tag` VALUES (784, 288, 382, 'SYSTEM', '2025-08-03 15:05:57.364', 'SYSTEM', '2025-08-03 15:05:57.364');
INSERT INTO `content_content_tag` VALUES (785, 289, 202, 'SYSTEM', '2025-08-03 15:05:57.394', 'SYSTEM', '2025-08-03 15:05:57.394');
INSERT INTO `content_content_tag` VALUES (786, 289, 260, 'SYSTEM', '2025-08-03 15:05:57.394', 'SYSTEM', '2025-08-03 15:05:57.394');
INSERT INTO `content_content_tag` VALUES (787, 289, 166, 'SYSTEM', '2025-08-03 15:05:57.394', 'SYSTEM', '2025-08-03 15:05:57.394');
INSERT INTO `content_content_tag` VALUES (788, 289, 186, 'SYSTEM', '2025-08-03 15:05:57.394', 'SYSTEM', '2025-08-03 15:05:57.394');
INSERT INTO `content_content_tag` VALUES (789, 289, 130, 'SYSTEM', '2025-08-03 15:05:57.394', 'SYSTEM', '2025-08-03 15:05:57.394');
INSERT INTO `content_content_tag` VALUES (790, 289, 358, 'SYSTEM', '2025-08-03 15:05:57.394', 'SYSTEM', '2025-08-03 15:05:57.394');
INSERT INTO `content_content_tag` VALUES (791, 289, 386, 'SYSTEM', '2025-08-03 15:05:57.395', 'SYSTEM', '2025-08-03 15:05:57.395');
INSERT INTO `content_content_tag` VALUES (792, 290, 20, 'SYSTEM', '2025-08-03 15:05:57.426', 'SYSTEM', '2025-08-03 15:05:57.426');
INSERT INTO `content_content_tag` VALUES (793, 290, 106, 'SYSTEM', '2025-08-03 15:05:57.426', 'SYSTEM', '2025-08-03 15:05:57.426');
INSERT INTO `content_content_tag` VALUES (794, 290, 220, 'SYSTEM', '2025-08-03 15:05:57.426', 'SYSTEM', '2025-08-03 15:05:57.426');
INSERT INTO `content_content_tag` VALUES (795, 290, 70, 'SYSTEM', '2025-08-03 15:05:57.426', 'SYSTEM', '2025-08-03 15:05:57.426');
INSERT INTO `content_content_tag` VALUES (796, 290, 52, 'SYSTEM', '2025-08-03 15:05:57.426', 'SYSTEM', '2025-08-03 15:05:57.426');
INSERT INTO `content_content_tag` VALUES (797, 290, 353, 'SYSTEM', '2025-08-03 15:05:57.426', 'SYSTEM', '2025-08-03 15:05:57.426');
INSERT INTO `content_content_tag` VALUES (798, 290, 384, 'SYSTEM', '2025-08-03 15:05:57.426', 'SYSTEM', '2025-08-03 15:05:57.426');

-- ----------------------------
-- Table structure for content_interaction_accumulation
-- ----------------------------
DROP TABLE IF EXISTS `content_interaction_accumulation`;
CREATE TABLE `content_interaction_accumulation`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` tinyint NOT NULL COMMENT '互动类型',
  `resource_type` tinyint NOT NULL COMMENT '资源类型',
  `resource_id` bigint UNSIGNED NOT NULL COMMENT '资源id',
  `accumulation` bigint UNSIGNED NOT NULL COMMENT '累积量',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '内容互动数据累积表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_interaction_accumulation
-- ----------------------------

-- ----------------------------
-- Table structure for content_live
-- ----------------------------
DROP TABLE IF EXISTS `content_live`;
CREATE TABLE `content_live`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `room_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '直播间id',
  `start_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `finish_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `active_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `live_status` tinyint UNSIGNED NOT NULL,
  `tabular_image_reference` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tabular_image_reference_mode` tinyint NULL DEFAULT NULL,
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 283 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '内容直播表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_live
-- ----------------------------
INSERT INTO `content_live` VALUES (30, '5531', '2025-07-31 18:21:07', '2025-08-12 18:21:07', '2025-08-02 18:21:07', '2025-08-19 18:21:07', 2, 'www.example.com/tabular.jpg', 2, 'SYSTEM', '2025-07-30 10:21:08', 'SYSTEM', '2025-07-30 10:21:08');
INSERT INTO `content_live` VALUES (40, '8144', '2025-08-01 18:22:28', '2025-08-08 18:22:28', '2025-07-31 18:22:28', '2025-08-15 18:22:28', 1, 'www.example.com/tabular.jpg', 2, 'SYSTEM', '2025-07-30 10:22:28', 'SYSTEM', '2025-07-30 10:22:28');
INSERT INTO `content_live` VALUES (50, '9347', '2025-08-06 18:23:31', '2025-08-10 18:23:31', '2025-08-01 18:23:31', '2025-08-20 18:23:31', 0, 'www.example.com/tabular.jpg', 2, 'SYSTEM', '2025-07-30 10:23:32', 'SYSTEM', '2025-07-30 10:23:32');
INSERT INTO `content_live` VALUES (60, '9588', '2025-08-03 20:11:56', '2025-08-12 20:11:56', '2025-08-01 20:11:56', '2025-08-25 20:11:56', 1, 'www.example.com/tabular.jpg', 2, 'SYSTEM', '2025-07-30 12:11:57', 'SYSTEM', '2025-07-30 12:11:57');
INSERT INTO `content_live` VALUES (202, '3140', '2025-08-06 22:40:03', '2025-08-15 22:40:03', '2025-08-05 22:40:03', '2025-08-25 22:40:03', 0, 'www.example.com/tabular.jpg', 2, 'SYSTEM', '2025-08-03 14:40:03', 'SYSTEM', '2025-08-03 14:40:03');
INSERT INTO `content_live` VALUES (212, '6096', '2025-08-09 22:40:05', '2025-08-16 22:40:05', '2025-08-04 22:40:05', '2025-08-18 22:40:05', 1, 'www.example.com/tabular.jpg', 2, 'SYSTEM', '2025-08-03 14:40:06', 'SYSTEM', '2025-08-03 14:40:06');
INSERT INTO `content_live` VALUES (222, '5575', '2025-08-05 22:40:07', '2025-08-12 22:40:07', '2025-08-06 22:40:07', '2025-08-25 22:40:07', 0, 'www.example.com/tabular.jpg', 2, 'SYSTEM', '2025-08-03 14:40:08', 'SYSTEM', '2025-08-03 14:40:08');
INSERT INTO `content_live` VALUES (232, '3266', '2025-08-08 22:40:10', '2025-08-14 22:40:10', '2025-08-06 22:40:10', '2025-08-25 22:40:10', 2, 'www.example.com/tabular.jpg', 2, 'SYSTEM', '2025-08-03 14:40:10', 'SYSTEM', '2025-08-03 14:40:10');
INSERT INTO `content_live` VALUES (242, '7832', '2025-08-05 22:40:12', '2025-08-13 22:40:12', '2025-08-04 22:40:12', '2025-08-25 22:40:12', 0, 'www.example.com/tabular.jpg', 2, 'SYSTEM', '2025-08-03 14:40:12', 'SYSTEM', '2025-08-03 14:40:12');
INSERT INTO `content_live` VALUES (252, '4954', '2025-08-04 22:40:14', '2025-08-17 22:40:14', '2025-08-06 22:40:14', '2025-09-02 22:40:14', 0, 'www.example.com/tabular.jpg', 2, 'SYSTEM', '2025-08-03 14:40:14', 'SYSTEM', '2025-08-03 14:40:14');
INSERT INTO `content_live` VALUES (262, '8510', '2025-08-06 22:40:16', '2025-08-16 22:40:16', '2025-08-05 22:40:16', '2025-09-02 22:40:16', 0, 'www.example.com/tabular.jpg', 2, 'SYSTEM', '2025-08-03 14:40:16', 'SYSTEM', '2025-08-03 14:40:16');
INSERT INTO `content_live` VALUES (272, '1953', '2025-08-06 22:40:18', '2025-08-17 22:40:18', '2025-08-06 22:40:18', '2025-09-02 22:40:18', 2, 'www.example.com/tabular.jpg', 2, 'SYSTEM', '2025-08-03 14:40:18', 'SYSTEM', '2025-08-03 14:40:18');
INSERT INTO `content_live` VALUES (282, '9318', '2025-08-09 23:05:57', '2025-08-13 23:05:57', '2025-08-04 23:05:57', '2025-08-21 23:05:57', 0, 'https://www.example.com/tabular.jpg', 2, 'SYSTEM', '2025-08-03 15:05:57', 'SYSTEM', '2025-08-03 15:05:57');

-- ----------------------------
-- Table structure for content_product_term
-- ----------------------------
DROP TABLE IF EXISTS `content_product_term`;
CREATE TABLE `content_product_term`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `product_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '产品编码',
  `on_sale_at` datetime NULL DEFAULT NULL,
  `off_sale_at` datetime NULL DEFAULT NULL,
  `display_order` int UNSIGNED NOT NULL,
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 288 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '内容产品条款表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_product_term
-- ----------------------------
INSERT INTO `content_product_term` VALUES (15, 'PROD7960', '2025-07-26 18:15:19', '2025-08-26 18:15:19', 23, 'SYSTEM', '2025-07-30 10:15:19', 'SYSTEM', '2025-07-30 10:15:19');
INSERT INTO `content_product_term` VALUES (25, 'PROD8011', '2025-07-28 18:19:08', '2025-08-09 18:19:08', 60, 'SYSTEM', '2025-07-30 10:19:09', 'SYSTEM', '2025-07-30 10:19:09');
INSERT INTO `content_product_term` VALUES (35, 'PROD3597', '2025-07-29 18:21:07', '2025-08-14 18:21:07', 12, 'SYSTEM', '2025-07-30 10:21:08', 'SYSTEM', '2025-07-30 10:21:08');
INSERT INTO `content_product_term` VALUES (45, 'PROD2621', '2025-07-25 18:22:28', '2025-08-23 18:22:28', 91, 'SYSTEM', '2025-07-30 10:22:28', 'SYSTEM', '2025-07-30 10:22:28');
INSERT INTO `content_product_term` VALUES (55, 'PROD6412', '2025-07-29 18:23:31', '2025-08-27 18:23:31', 58, 'SYSTEM', '2025-07-30 10:23:32', 'SYSTEM', '2025-07-30 10:23:32');
INSERT INTO `content_product_term` VALUES (65, 'PROD3779', '2025-07-27 20:11:56', '2025-08-22 20:11:56', 7, 'SYSTEM', '2025-07-30 12:11:57', 'SYSTEM', '2025-07-30 12:11:57');
INSERT INTO `content_product_term` VALUES (74, 'PROD7363', '2025-07-26 20:14:34', '2025-08-17 20:14:34', 12, 'SYSTEM', '2025-07-30 12:14:35', 'SYSTEM', '2025-07-30 12:14:35');
INSERT INTO `content_product_term` VALUES (83, 'PROD7657', '2025-07-24 20:19:14', '2025-08-15 20:19:14', 67, 'SYSTEM', '2025-07-30 12:19:30', 'SYSTEM', '2025-07-30 12:19:30');
INSERT INTO `content_product_term` VALUES (170, 'PROD8420', '2025-07-28 22:35:14', '2025-08-26 22:35:14', 81, 'SYSTEM', '2025-08-03 14:35:14', 'SYSTEM', '2025-08-03 14:35:14');
INSERT INTO `content_product_term` VALUES (179, 'PROD2705', '2025-07-31 22:36:46', '2025-08-26 22:36:46', 59, 'SYSTEM', '2025-08-03 14:36:46', 'SYSTEM', '2025-08-03 14:36:46');
INSERT INTO `content_product_term` VALUES (188, 'PROD1567', '2025-07-28 22:37:27', '2025-08-13 22:37:27', 31, 'SYSTEM', '2025-08-03 14:37:28', 'SYSTEM', '2025-08-03 14:37:28');
INSERT INTO `content_product_term` VALUES (197, 'PROD5452', '2025-07-31 22:39:21', '2025-08-28 22:39:21', 16, 'SYSTEM', '2025-08-03 14:39:21', 'SYSTEM', '2025-08-03 14:39:21');
INSERT INTO `content_product_term` VALUES (207, 'PROD1686', '2025-07-27 22:40:03', '2025-08-11 22:40:03', 90, 'SYSTEM', '2025-08-03 14:40:04', 'SYSTEM', '2025-08-03 14:40:04');
INSERT INTO `content_product_term` VALUES (217, 'PROD5159', '2025-08-02 22:40:05', '2025-08-23 22:40:05', 79, 'SYSTEM', '2025-08-03 14:40:06', 'SYSTEM', '2025-08-03 14:40:06');
INSERT INTO `content_product_term` VALUES (227, 'PROD1209', '2025-08-02 22:40:08', '2025-08-21 22:40:08', 76, 'SYSTEM', '2025-08-03 14:40:08', 'SYSTEM', '2025-08-03 14:40:08');
INSERT INTO `content_product_term` VALUES (237, 'PROD5870', '2025-07-28 22:40:10', '2025-09-02 22:40:10', 44, 'SYSTEM', '2025-08-03 14:40:10', 'SYSTEM', '2025-08-03 14:40:10');
INSERT INTO `content_product_term` VALUES (247, 'PROD9471', '2025-07-29 22:40:12', '2025-08-29 22:40:12', 69, 'SYSTEM', '2025-08-03 14:40:12', 'SYSTEM', '2025-08-03 14:40:12');
INSERT INTO `content_product_term` VALUES (257, 'PROD5616', '2025-07-28 22:40:14', '2025-08-23 22:40:14', 28, 'SYSTEM', '2025-08-03 14:40:14', 'SYSTEM', '2025-08-03 14:40:14');
INSERT INTO `content_product_term` VALUES (267, 'PROD1250', '2025-07-29 22:40:16', '2025-08-19 22:40:16', 85, 'SYSTEM', '2025-08-03 14:40:16', 'SYSTEM', '2025-08-03 14:40:16');
INSERT INTO `content_product_term` VALUES (277, 'PROD9174', '2025-08-01 22:40:18', '2025-08-12 22:40:18', 68, 'SYSTEM', '2025-08-03 14:40:18', 'SYSTEM', '2025-08-03 14:40:18');
INSERT INTO `content_product_term` VALUES (287, 'PROD3032', '2025-07-29 23:05:57', '2025-08-18 23:05:57', 100, 'SYSTEM', '2025-08-03 15:05:57', 'SYSTEM', '2025-08-03 15:05:57');

-- ----------------------------
-- Table structure for content_tag
-- ----------------------------
DROP TABLE IF EXISTS `content_tag`;
CREATE TABLE `content_tag`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `type` tinyint UNSIGNED NOT NULL COMMENT '标签类型',
  `parent_id` bigint UNSIGNED NOT NULL COMMENT '标签父id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签编码',
  `is_enabled` bit(1) NOT NULL COMMENT '是否可用',
  `sort` int UNSIGNED NOT NULL COMMENT '排序',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '我是一个小标签',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` timestamp(3) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_content_tag_type_code`(`type` ASC, `code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 388 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_tag
-- ----------------------------
INSERT INTO `content_tag` VALUES (1, 1, 0, '根茎类', 'root_rhizome', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (2, 1, 0, '果实类', 'root_fruit', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (3, 1, 0, '叶类', 'root_leaf', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (4, 1, 0, '花类', 'root_flower', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (5, 1, 0, '皮类', 'root_bark', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (6, 1, 0, '全草类', 'root_herb', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (7, 1, 0, '动物类', 'root_animal', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (8, 1, 0, '矿物类', 'root_mineral', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (9, 1, 1, '人参', 'ginseng', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (10, 1, 9, '红参', 'red_ginseng', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (11, 1, 9, '白参', 'white_ginseng', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (12, 1, 9, '高丽参', 'korean_ginseng', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (13, 1, 1, '黄芪', 'astragalus', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (14, 1, 13, '蒙古黄芪', 'mongolian_astragalus', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (15, 1, 13, '膜荚黄芪', 'membrane_astragalus', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (16, 1, 1, '当归', 'angelica', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (17, 1, 16, '全当归', 'whole_angelica', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (18, 1, 16, '当归头', 'angelica_head', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (19, 1, 16, '当归尾', 'angelica_tail', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (20, 1, 1, '白术', 'atractylodes', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (21, 1, 20, '生白术', 'raw_atractylodes', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (22, 1, 20, '炒白术', 'fried_atractylodes', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (23, 1, 1, '白芍', 'white_peony', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (24, 1, 23, '杭白芍', 'hangzhou_peony', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (25, 1, 23, '亳白芍', 'bozhou_peony', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (26, 1, 1, '甘草', 'licorice', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (27, 1, 26, '炙甘草', 'honey_licorice', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (28, 1, 26, '生甘草', 'raw_licorice', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (29, 1, 1, '川芎', 'chuanxiong', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (30, 1, 1, '地黄', 'rehmannia', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (31, 1, 30, '生地黄', 'raw_rehmannia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (32, 1, 30, '熟地黄', 'cooked_rehmannia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (33, 1, 1, '三七', 'pseudo_ginseng', b'1', 9, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (34, 1, 33, '田七', 'tianqi', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (35, 1, 1, '天麻', 'gastrodia', b'1', 10, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (36, 1, 35, '红天麻', 'red_gastrodia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (37, 1, 35, '乌天麻', 'black_gastrodia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (38, 1, 1, '麦冬', 'ophiopogon', b'1', 11, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (39, 1, 38, '川麦冬', 'sichuan_ophiopogon', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (40, 1, 38, '杭麦冬', 'hangzhou_ophiopogon', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (41, 1, 1, '黄连', 'coptis', b'1', 12, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (42, 1, 41, '味连', 'weilian', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (43, 1, 41, '雅连', 'yalian', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (44, 1, 41, '云连', 'yunlian', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (45, 1, 1, '贝母', 'fritillaria', b'1', 13, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (46, 1, 45, '川贝母', 'sichuan_fritillaria', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (47, 1, 45, '浙贝母', 'zhejiang_fritillaria', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (48, 1, 45, '平贝母', 'pingbei_fritillaria', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (49, 1, 1, '党参', 'codonopsis', b'1', 14, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (50, 1, 49, '潞党参', 'lu_dangshen', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (51, 1, 49, '纹党参', 'wen_dangshen', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (52, 1, 49, '板桥党参', 'banqiao_dangshen', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (53, 1, 1, '丹参', 'salvia', b'1', 15, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (54, 1, 53, '川丹参', 'sichuan_salvia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (55, 1, 53, '山东丹参', 'shandong_salvia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (56, 1, 1, '山药', 'dioscorea', b'1', 16, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (57, 1, 56, '怀山药', 'huai_shanyao', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (58, 1, 56, '广山药', 'guang_shanyao', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (59, 1, 1, '茯苓', 'poria', b'1', 17, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (60, 1, 59, '白茯苓', 'white_poria', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (61, 1, 59, '赤茯苓', 'red_poria', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (62, 1, 59, '茯神', 'poria_spirit', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (63, 1, 1, '泽泻', 'alisma', b'1', 18, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (64, 1, 63, '建泽泻', 'jian_zexie', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (65, 1, 63, '川泽泻', 'sichuan_zexie', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (66, 1, 1, '半夏', 'pinellia', b'1', 19, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (67, 1, 66, '法半夏', 'fa_banxia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (68, 1, 66, '姜半夏', 'jiang_banxia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (69, 1, 66, '清半夏', 'qing_banxia', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (70, 1, 1, '天南星', 'arisema', b'1', 20, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (71, 1, 70, '胆南星', 'dan_nanxing', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (72, 1, 1, '苍术', 'atractylodes_lancea', b'1', 21, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (73, 1, 72, '茅苍术', 'mao_cangzhu', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (74, 1, 72, '北苍术', 'bei_cangzhu', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (75, 1, 1, '黄精', 'polygonatum', b'1', 22, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (76, 1, 75, '鸡头黄精', 'jihou_huangjing', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (77, 1, 75, '姜形黄精', 'jiang_huangjing', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (78, 1, 1, '玉竹', 'polygonatum_odoratum', b'1', 23, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (79, 1, 78, '湘玉竹', 'xiang_yuzhu', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (80, 1, 78, '关玉竹', 'guan_yuzhu', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (81, 1, 1, '知母', 'anemarrhena', b'1', 24, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (82, 1, 81, '毛知母', 'mao_zhimu', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (83, 1, 81, '光知母', 'guang_zhimu', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (84, 1, 1, '葛根', 'pueraria', b'1', 25, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (85, 1, 84, '粉葛', 'fen_ge', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (86, 1, 84, '野葛', 'ye_ge', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (87, 1, 1, '柴胡', 'bupleurum', b'1', 26, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (88, 1, 87, '北柴胡', 'bei_chaihu', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (89, 1, 87, '南柴胡', 'nan_chaihu', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (90, 1, 1, '升麻', 'cimicifuga', b'1', 27, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (91, 1, 90, '关升麻', 'guan_shengma', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (92, 1, 90, '北升麻', 'bei_shengma', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (93, 1, 1, '防风', 'saposhnikovia', b'1', 28, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (94, 1, 93, '关防风', 'guan_fangfeng', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (95, 1, 93, '口防风', 'kou_fangfeng', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (96, 1, 1, '独活', 'angelica_pubescens', b'1', 29, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (97, 1, 96, '川独活', 'sichuan_duhuo', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (98, 1, 96, '香独活', 'xiang_duhuo', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (99, 1, 1, '羌活', 'notopterygium', b'1', 30, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (100, 1, 99, '蚕羌', 'can_qiang', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (101, 1, 99, '竹节羌', 'zhujie_qiang', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (102, 1, 2, '枸杞', 'lycium', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (103, 1, 102, '宁夏枸杞', 'ningxia_lycium', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (104, 1, 102, '中宁枸杞', 'zhongning_lycium', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (105, 1, 102, '黑枸杞', 'black_lycium', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (106, 1, 2, '五味子', 'schisandra', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (107, 1, 106, '北五味子', 'bei_schisandra', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (108, 1, 106, '南五味子', 'nan_schisandra', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (109, 1, 2, '山楂', 'hawthorn', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (110, 1, 109, '北山楂', 'bei_hawthorn', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (111, 1, 109, '南山楂', 'nan_hawthorn', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (112, 1, 2, '女贞子', 'ligustrum', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (113, 1, 112, '大叶女贞', 'big_ligustrum', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (114, 1, 112, '小叶女贞', 'small_ligustrum', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (115, 1, 2, '栀子', 'gardenia', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (116, 1, 115, '水栀子', 'water_gardenia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (117, 1, 115, '山栀子', 'mountain_gardenia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (118, 1, 2, '砂仁', 'amomum', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (119, 1, 118, '阳春砂', 'yangchun_amomum', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (120, 1, 118, '海南砂', 'hainan_amomum', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (121, 1, 2, '豆蔻', 'cardamom', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (122, 1, 121, '白豆蔻', 'white_cardamom', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (123, 1, 121, '草豆蔻', 'grass_cardamom', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (124, 1, 2, '益智仁', 'alpinia', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (125, 1, 124, '海南益智', 'hainan_alpinia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (126, 1, 124, '广东益智', 'guangdong_alpinia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (127, 1, 2, '草果', 'tsaoko', b'1', 9, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (128, 1, 127, '云南草果', 'yunnan_tsaoko', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (129, 1, 127, '广西草果', 'guangxi_tsaoko', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (130, 1, 2, '佛手', 'bergamot', b'1', 10, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (131, 1, 130, '广佛手', 'guang_bergamot', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (132, 1, 130, '川佛手', 'sichuan_bergamot', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (133, 1, 2, '香橼', 'citron', b'1', 11, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (134, 1, 133, '枸橼', 'juyuan', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (135, 1, 133, '香圆', 'xiangyuan', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (136, 1, 2, '木瓜', 'chaenomeles', b'1', 12, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (137, 1, 136, '宣木瓜', 'xuan_mugua', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (138, 1, 136, '川木瓜', 'sichuan_mugua', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (139, 1, 2, '乌梅', 'smoked_plum', b'1', 13, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (140, 1, 139, '福建乌梅', 'fujian_wumei', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (141, 1, 139, '四川乌梅', 'sichuan_wumei', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (142, 1, 2, '金樱子', 'rosa_laevigata', b'1', 14, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (143, 1, 142, '江西金樱子', 'jiangxi_jinyingzi', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (144, 1, 142, '湖南金樱子', 'hunan_jinyingzi', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (145, 1, 2, '覆盆子', 'rubus', b'1', 15, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (146, 1, 145, '华东覆盆子', 'huadong_fupenzi', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (147, 1, 145, '华中覆盆子', 'huazhong_fupenzi', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (148, 1, 2, '桑椹', 'mulberry', b'1', 16, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (149, 1, 148, '黑桑椹', 'black_mulberry', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (150, 1, 148, '白桑椹', 'white_mulberry', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (151, 1, 2, '使君子', 'quisqualis', b'1', 17, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (152, 1, 151, '四川使君子', 'sichuan_quisqualis', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (153, 1, 151, '广东使君子', 'guangdong_quisqualis', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (154, 1, 2, '诃子', 'chebula', b'1', 18, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (155, 1, 154, '云南诃子', 'yunnan_chebula', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (156, 1, 154, '广西诃子', 'guangxi_chebula', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (157, 1, 2, '川楝子', 'toosendan', b'1', 19, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (158, 1, 157, '四川川楝子', 'sichuan_toosendan', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (159, 1, 157, '云南川楝子', 'yunnan_toosendan', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (160, 1, 2, '吴茱萸', 'evodia', b'1', 20, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (161, 1, 160, '中花吴茱萸', 'zhonghua_evodia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (162, 1, 160, '小花吴茱萸', 'small_evodia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (163, 1, 2, '山茱萸', 'cornus', b'1', 21, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (164, 1, 163, '浙江山茱萸', 'zhejiang_cornus', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (165, 1, 163, '河南山茱萸', 'henan_cornus', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (166, 1, 2, '连翘', 'forsythia', b'1', 22, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (167, 1, 166, '青翘', 'qing_qiao', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (168, 1, 166, '老翘', 'lao_qiao', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (169, 1, 2, '枸杞子', 'lycium_fruit', b'1', 23, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (170, 1, 169, '宁夏枸杞子', 'ningxia_lycium_fruit', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (171, 1, 169, '新疆枸杞子', 'xinjiang_lycium_fruit', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (172, 1, 2, '瓜蒌', 'trichosanthes', b'1', 24, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (173, 1, 172, '全瓜蒌', 'whole_trichosanthes', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (174, 1, 172, '瓜蒌皮', 'trichosanthes_peel', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (175, 1, 172, '瓜蒌子', 'trichosanthes_seed', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (176, 1, 2, '薏苡仁', 'coix', b'1', 25, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (177, 1, 176, '贵州薏苡仁', 'guizhou_coix', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (178, 1, 176, '广西薏苡仁', 'guangxi_coix', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (179, 1, 3, '桑叶', 'mulberry_leaf', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (180, 1, 179, '霜桑叶', 'frost_mulberry_leaf', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (181, 1, 179, '嫩桑叶', 'tender_mulberry_leaf', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (182, 1, 3, '枇杷叶', 'loquat_leaf', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (183, 1, 182, '老枇杷叶', 'old_loquat_leaf', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (184, 1, 182, '嫩枇杷叶', 'tender_loquat_leaf', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (185, 1, 3, '荷叶', 'lotus_leaf', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (186, 1, 185, '鲜荷叶', 'fresh_lotus_leaf', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (187, 1, 185, '干荷叶', 'dry_lotus_leaf', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (188, 1, 3, '紫苏叶', 'perilla_leaf', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (189, 1, 188, '紫苏嫩叶', 'tender_perilla', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (190, 1, 188, '紫苏老叶', 'old_perilla', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (191, 1, 3, '艾叶', 'artemisia_leaf', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (192, 1, 191, '蕲艾', 'qi_ai', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (193, 1, 191, '普通艾叶', 'common_ai', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (194, 1, 3, '淡竹叶', 'lophatherum', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (195, 1, 194, '江西淡竹叶', 'jiangxi_lophatherum', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (196, 1, 194, '浙江淡竹叶', 'zhejiang_lophatherum', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (197, 1, 3, '侧柏叶', 'platycladus_leaf', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (198, 1, 197, '生侧柏叶', 'raw_platycladus', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (199, 1, 197, '侧柏炭', 'carbonized_platycladus', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (200, 1, 3, '大青叶', 'isatis_leaf', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (201, 1, 200, '菘蓝叶', 'songlan_leaf', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (202, 1, 200, '马蓝叶', 'malan_leaf', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (203, 1, 3, '番泻叶', 'senna_leaf', b'1', 9, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (204, 1, 203, '印度番泻叶', 'india_senna', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (205, 1, 203, '埃及番泻叶', 'egypt_senna', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (206, 1, 3, '银杏叶', 'ginkgo_leaf', b'1', 10, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (207, 1, 206, '鲜银杏叶', 'fresh_ginkgo', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (208, 1, 206, '干银杏叶', 'dry_ginkgo', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (209, 1, 4, '金银花', 'honeysuckle', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (210, 1, 209, '密银花', 'mi_honeysuckle', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (211, 1, 209, '济银花', 'ji_honeysuckle', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (212, 1, 4, '菊花', 'chrysanthemum', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (213, 1, 212, '杭菊', 'hang_ju', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (214, 1, 212, '亳菊', 'bo_ju', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (215, 1, 212, '滁菊', 'chu_ju', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (216, 1, 4, '红花', 'carthamus', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (217, 1, 216, '新疆红花', 'xinjiang_carthamus', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (218, 1, 216, '云南红花', 'yunnan_carthamus', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (219, 1, 4, '玫瑰花', 'rose', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (220, 1, 219, '苦水玫瑰', 'kushui_rose', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (221, 1, 219, '平阴玫瑰', 'pingyin_rose', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (222, 1, 4, '月季花', 'china_rose', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (223, 1, 222, '江苏月季', 'jiangsu_chinarose', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (224, 1, 222, '山东月季', 'shandong_chinarose', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (225, 1, 4, '槐花', 'sophora_flower', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (226, 1, 225, '生槐花', 'raw_sophora', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (227, 1, 225, '槐花炭', 'carbonized_sophora', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (228, 1, 4, '款冬花', 'tussilago', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (229, 1, 228, '甘肃款冬', 'gansu_tussilago', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (230, 1, 228, '陕西款冬', 'shanxi_tussilago', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (231, 1, 4, '辛夷', 'magnolia_flower', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (232, 1, 231, '望春花', 'wangchun_flower', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (233, 1, 231, '武当玉兰', 'wudang_magnolia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (234, 1, 4, '合欢花', 'albizia_flower', b'1', 9, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (235, 1, 234, '安徽合欢', 'anhui_albizia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (236, 1, 234, '江苏合欢', 'jiangsu_albizia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (237, 1, 4, '野菊花', 'wild_chrysanthemum', b'1', 10, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (238, 1, 237, '黄山野菊', 'huangshan_wildju', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (239, 1, 237, '天目野菊', 'tianmu_wildju', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (240, 1, 5, '牡丹皮', 'moutan_bark', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (241, 1, 240, '凤丹皮', 'feng_moutan', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (242, 1, 240, '川丹皮', 'sichuan_moutan', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (243, 1, 5, '厚朴', 'magnolia_bark', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (244, 1, 243, '川朴', 'sichuan_magnolia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (245, 1, 243, '温朴', 'wen_magnolia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (246, 1, 5, '肉桂', 'cinnamon', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (247, 1, 246, '官桂', 'guan_cinnamon', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (248, 1, 246, '企边桂', 'qibian_cinnamon', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (249, 1, 5, '杜仲', 'eucommia', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (250, 1, 249, '川杜仲', 'sichuan_eucommia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (251, 1, 249, '陕杜仲', 'shanxi_eucommia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (252, 1, 5, '黄柏', 'phellodendron', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (253, 1, 252, '川黄柏', 'sichuan_phellodendron', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (254, 1, 252, '关黄柏', 'guan_phellodendron', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (255, 1, 5, '桑白皮', 'mulberry_bark', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (256, 1, 255, '嫩桑皮', 'tender_mulberry_bark', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (257, 1, 255, '老桑皮', 'old_mulberry_bark', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (258, 1, 5, '地骨皮', 'lycium_bark', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (259, 1, 258, '宁夏地骨皮', 'ningxia_lycium_bark', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (260, 1, 258, '甘肃地骨皮', 'gansu_lycium_bark', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (261, 1, 5, '五加皮', 'acanthopanax', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (262, 1, 261, '南五加', 'nan_acanthopanax', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (263, 1, 261, '香加皮', 'xiang_acanthopanax', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (264, 1, 5, '合欢皮', 'albizia_bark', b'1', 9, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (265, 1, 264, '安徽合欢皮', 'anhui_albizia_bark', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (266, 1, 264, '江苏合欢皮', 'jiangsu_albizia_bark', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (267, 1, 5, '秦皮', 'fraxinus', b'1', 10, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (268, 1, 267, '陕西秦皮', 'shanxi_fraxinus', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (269, 1, 267, '东北秦皮', 'dongbei_fraxinus', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (270, 1, 6, '薄荷', 'mint', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (271, 1, 270, '苏薄荷', 'su_mint', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (272, 1, 270, '杭薄荷', 'hang_mint', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (273, 1, 6, '藿香', 'agastache', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (274, 1, 273, '广藿香', 'guang_agastache', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (275, 1, 273, '川藿香', 'sichuan_agastache', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (276, 1, 6, '荆芥', 'schizonepeta', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (277, 1, 276, '江苏荆芥', 'jiangsu_schizonepeta', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (278, 1, 276, '江西荆芥', 'jiangxi_schizonepeta', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (279, 1, 6, '益母草', 'leonurus', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (280, 1, 279, '江苏益母草', 'jiangsu_leonurus', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (281, 1, 279, '安徽益母草', 'anhui_leonurus', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (282, 1, 6, '车前草', 'plantago', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (283, 1, 282, '大车前', 'big_plantago', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (284, 1, 282, '小车前', 'small_plantago', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (285, 1, 6, '蒲公英', 'dandelion', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (286, 1, 285, '东北蒲公英', 'dongbei_dandelion', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (287, 1, 285, '华北蒲公英', 'huabei_dandelion', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (288, 1, 6, '鱼腥草', 'houttuynia', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (289, 1, 288, '四川鱼腥草', 'sichuan_houttuynia', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (290, 1, 288, '湖南鱼腥草', 'hunan_houttuynia', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (291, 1, 6, '紫花地丁', 'viola', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (292, 1, 291, '东北紫花地丁', 'dongbei_viola', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (293, 1, 291, '华北紫花地丁', 'huabei_viola', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (294, 1, 6, '半枝莲', 'scutellaria', b'1', 9, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (295, 1, 294, '江苏半枝莲', 'jiangsu_scutellaria', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (296, 1, 294, '浙江半枝莲', 'zhejiang_scutellaria', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (297, 1, 6, '白花蛇舌草', 'hedyotis', b'1', 10, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (298, 1, 297, '广东蛇舌草', 'guangdong_hedyotis', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (299, 1, 297, '广西蛇舌草', 'guangxi_hedyotis', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (300, 1, 7, '鹿茸', 'deer_antler', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (301, 1, 300, '梅花鹿茸', 'meihua_deer', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (302, 1, 300, '马鹿茸', 'malu_deer', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (303, 1, 7, '阿胶', 'donkey_gelatin', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (304, 1, 303, '东阿阿胶', 'dong_e', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (305, 1, 303, '福牌阿胶', 'fu_e', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (306, 1, 7, '龟板', 'tortoise_shell', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (307, 1, 306, '乌龟板', 'wu_shell', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (308, 1, 306, '海龟板', 'hai_shell', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (309, 1, 7, '鳖甲', 'turtle_shell', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (310, 1, 309, '中华鳖甲', 'zhonghua_turtle', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (311, 1, 309, '山瑞鳖甲', 'shanrui_turtle', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (312, 1, 7, '蛤蚧', 'gecko', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (313, 1, 312, '广西蛤蚧', 'guangxi_gecko', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (314, 1, 312, '云南蛤蚧', 'yunnan_gecko', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (315, 1, 7, '全蝎', 'scorpion', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (316, 1, 315, '河南全蝎', 'henan_scorpion', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (317, 1, 315, '山东全蝎', 'shandong_scorpion', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (318, 1, 7, '蜈蚣', 'centipede', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (319, 1, 318, '湖北蜈蚣', 'hubei_centipede', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (320, 1, 318, '浙江蜈蚣', 'zhejiang_centipede', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (321, 1, 7, '地龙', 'earthworm', b'1', 8, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (322, 1, 321, '广地龙', 'guang_earthworm', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (323, 1, 321, '沪地龙', 'hu_earthworm', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (324, 1, 7, '水蛭', 'leech', b'1', 9, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (325, 1, 324, '宽体金线蛭', 'kuanti_leech', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (326, 1, 324, '日本医蛭', 'riben_leech', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (327, 1, 7, '蝉蜕', 'cicada_slough', b'1', 10, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (328, 1, 327, '山东蝉蜕', 'shandong_cicada', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (329, 1, 327, '河南蝉蜕', 'henan_cicada', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (330, 1, 8, '朱砂', 'cinnabar', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (331, 1, 330, '辰砂', 'chen_sha', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (332, 1, 330, '镜面砂', 'mirror_sha', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (333, 1, 8, '石膏', 'gypsum', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (334, 1, 333, '湖北石膏', 'hubei_gypsum', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (335, 1, 333, '安徽石膏', 'anhui_gypsum', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (336, 1, 8, '滑石', 'talc', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (337, 1, 336, '江西滑石', 'jiangxi_talc', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (338, 1, 336, '山东滑石', 'shandong_talc', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (339, 1, 8, '龙骨', 'dragon_bone', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (340, 1, 339, '山西龙骨', 'shanxi_dragon', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (341, 1, 339, '陕西龙骨', 'shaanxi_dragon', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (342, 1, 8, '牡蛎', 'oyster', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (343, 1, 342, '大连牡蛎', 'dalian_oyster', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (344, 1, 342, '青岛牡蛎', 'qingdao_oyster', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (345, 1, 8, '赭石', 'hematite', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (346, 1, 345, '山西赭石', 'shanxi_hematite', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (347, 1, 345, '河北赭石', 'hebei_hematite', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (348, 1, 8, '磁石', 'magnetite', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (349, 1, 348, '安徽磁石', 'anhui_magnetite', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (350, 1, 348, '江苏磁石', 'jiangsu_magnetite', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (351, 2, 0, '江苏省', 'jiangsu', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (352, 2, 351, '南京市', 'nanjing', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (353, 2, 351, '苏州市', 'suzhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (354, 2, 351, '无锡市', 'wuxi', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (355, 2, 0, '浙江省', 'zhejiang', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (356, 2, 355, '杭州市', 'hangzhou', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (357, 2, 355, '宁波市', 'ningbo', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (358, 2, 355, '绍兴市', 'shaoxing', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (359, 2, 355, '温州市', 'wenzhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (360, 2, 355, '台州市', 'taizhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (361, 2, 355, '衢州市', 'quzhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (362, 2, 355, '金华市', 'jinhua', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (363, 2, 355, '丽水市', 'lishui', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (364, 2, 0, '安徽省', 'anhui', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (365, 2, 364, '合肥市', 'hefei', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (366, 2, 364, '芜湖市', 'wuhu', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (367, 2, 364, '蚌埠市', 'bengbu', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (368, 2, 364, '淮南市', 'huainan', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (369, 2, 364, '马鞍山市', 'maashan', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (370, 2, 364, '淮北市', 'huaibei', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (371, 2, 364, '铜陵市', 'tongling', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (372, 2, 364, '安庆市', 'anqing', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (373, 2, 364, '黄山市', 'huangshan', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (374, 2, 364, '阜阳市', 'fuyang', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (375, 2, 364, '宿州市', 'ah-suzhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (376, 2, 364, '滁州市', 'chuzhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (377, 2, 364, '六安市', 'luan', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (378, 2, 364, '宣城市', 'xuancheng', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (379, 2, 364, '池州市', 'chizhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (380, 2, 364, '亳州市', 'bozhou', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (381, 8, 0, '好助手', 'good_helper', b'1', 1, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (382, 8, 0, '小程序', 'mini_program', b'1', 2, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (383, 8, 0, '随身易', 'easy_go', b'1', 3, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (384, 8, 0, 'APP', 'app', b'1', 4, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (385, 8, 0, '金钥匙', 'golden_key', b'1', 5, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (386, 8, 0, '医健', 'medical', b'1', 6, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');
INSERT INTO `content_tag` VALUES (387, 8, 0, '战略客户', 'strategic_client', b'1', 7, '我是一个小标签', 'system', '2025-07-23 15:21:27.000', 'system', '2025-07-23 15:21:27.000');

-- ----------------------------
-- Table structure for content_video
-- ----------------------------
DROP TABLE IF EXISTS `content_video`;
CREATE TABLE `content_video`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `duration` int NOT NULL COMMENT '时长',
  `width` int NOT NULL COMMENT '宽',
  `height` int NOT NULL COMMENT '高',
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  `created_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `created_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `updated_by` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `updated_date` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `vertical_cover_image_reference` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `script_filename` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `script_file_key` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `video_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 284 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '内容视频表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_video
-- ----------------------------
INSERT INTO `content_video` VALUES (11, 2255, 1080, 720, 'Sample video description', 'SYSTEM', '2025-07-30 10:15:19', 'SYSTEM', '2025-07-30 10:15:19', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (21, 786, 1080, 360, 'Sample video description', 'SYSTEM', '2025-07-30 10:19:09', 'SYSTEM', '2025-07-30 10:19:09', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (31, 1283, 480, 360, 'Sample video description', 'SYSTEM', '2025-07-30 10:21:08', 'SYSTEM', '2025-07-30 10:21:08', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (41, 2239, 1080, 360, 'Sample video description', 'SYSTEM', '2025-07-30 10:22:28', 'SYSTEM', '2025-07-30 10:22:28', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (51, 448, 480, 360, 'Sample video description', 'SYSTEM', '2025-07-30 10:23:32', 'SYSTEM', '2025-07-30 10:23:32', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (61, 547, 480, 720, 'Sample video description', 'SYSTEM', '2025-07-30 12:11:57', 'SYSTEM', '2025-07-30 12:11:57', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (166, 723, 720, 720, 'Sample video description', 'SYSTEM', '2025-08-03 14:35:14', 'SYSTEM', '2025-08-03 14:35:14', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (175, 3238, 720, 360, 'Sample video description', 'SYSTEM', '2025-08-03 14:36:46', 'SYSTEM', '2025-08-03 14:36:46', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (184, 1284, 1080, 720, 'Sample video description', 'SYSTEM', '2025-08-03 14:37:27', 'SYSTEM', '2025-08-03 14:37:27', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (193, 903, 480, 720, 'Sample video description', 'SYSTEM', '2025-08-03 14:39:21', 'SYSTEM', '2025-08-03 14:39:21', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (203, 240, 480, 360, 'Sample video description', 'SYSTEM', '2025-08-03 14:40:03', 'SYSTEM', '2025-08-03 14:40:03', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (213, 2763, 1080, 360, 'Sample video description', 'SYSTEM', '2025-08-03 14:40:06', 'SYSTEM', '2025-08-03 14:40:06', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (223, 1540, 720, 480, 'Sample video description', 'SYSTEM', '2025-08-03 14:40:08', 'SYSTEM', '2025-08-03 14:40:08', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (233, 2281, 720, 360, 'Sample video description', 'SYSTEM', '2025-08-03 14:40:10', 'SYSTEM', '2025-08-03 14:40:10', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (243, 2142, 720, 720, 'Sample video description', 'SYSTEM', '2025-08-03 14:40:12', 'SYSTEM', '2025-08-03 14:40:12', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (253, 742, 480, 480, 'Sample video description', 'SYSTEM', '2025-08-03 14:40:14', 'SYSTEM', '2025-08-03 14:40:14', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (263, 681, 480, 480, 'Sample video description', 'SYSTEM', '2025-08-03 14:40:16', 'SYSTEM', '2025-08-03 14:40:16', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (273, 2757, 1080, 360, 'Sample video description', 'SYSTEM', '2025-08-03 14:40:18', 'SYSTEM', '2025-08-03 14:40:18', NULL, NULL, NULL, NULL);
INSERT INTO `content_video` VALUES (283, 223, 480, 720, 'Sample video description', 'SYSTEM', '2025-08-03 15:05:57', 'SYSTEM', '2025-08-03 15:05:57', 'https://www.example.com/vvv.jpg', 'aaa.excel', 'awsdsadqwdwqdqwdwq', 'apt.mp4');

-- ----------------------------
-- Table structure for pre_admin
-- ----------------------------
DROP TABLE IF EXISTS `pre_admin`;
CREATE TABLE `pre_admin`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pre_admin
-- ----------------------------

-- ----------------------------
-- Table structure for pre_category
-- ----------------------------
DROP TABLE IF EXISTS `pre_category`;
CREATE TABLE `pre_category`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `sort` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pre_category
-- ----------------------------
INSERT INTO `pre_category` VALUES (1, '⭐️点收藏福利【6款】', 1);
INSERT INTO `pre_category` VALUES (2, '🔥 热销', 2);
INSERT INTO `pre_category` VALUES (3, '👛 优惠', 3);
INSERT INTO `pre_category` VALUES (4, '🔥 人气菜品 【15款】', 4);
INSERT INTO `pre_category` VALUES (5, '流量套餐', 5);
INSERT INTO `pre_category` VALUES (6, '国 🍢 肉肉 【26款】', 6);
INSERT INTO `pre_category` VALUES (7, '麻 🍄 菌菇 【8款】', 7);
INSERT INTO `pre_category` VALUES (8, '好 蘸料 【11款】', 8);
INSERT INTO `pre_category` VALUES (9, '味 🧋 饮品 【款】', 9);
INSERT INTO `pre_category` VALUES (10, '⭐️ 🥢 必选', 10);

-- ----------------------------
-- Table structure for pre_food
-- ----------------------------
DROP TABLE IF EXISTS `pre_food`;
CREATE TABLE `pre_food`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `category_id` int UNSIGNED NOT NULL DEFAULT 0,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `price` decimal(10, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  `image_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `status` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL,
  `delete_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pre_food
-- ----------------------------
INSERT INTO `pre_food` VALUES (1, 1, '新客福利 点亮 收藏门店 新客立减2元', 0.00, 'images/1.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (2, 1, '收藏福利 点亮收藏送3元现金券', 0.00, 'images/2.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (3, 1, '宠粉福利 点亮 蔬菜6选1(多点不送)', 0.01, 'images/3.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (4, 1, '宠粉福利 点亮 荤菜4选1(多点不送)', 0.20, 'images/4.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (5, 1, '特惠福利 点亮 仅限1瓶饮料(多点不送)', 2.28, 'images/5.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (6, 2, '经典骨汤', 2.98, 'images/6.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (7, 2, '【慈爸爸力荐】横扫饥饿单人餐', 27.90, 'images/7.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (8, 2, '【赠*冰红茶】七荤八素加主食麻辣烫套餐', 32.88, 'images/8.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (9, 2, '方便面(1个）', 4.98, 'images/9.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (10, 2, '兰花干(1个）', 4.98, 'images/10.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (11, 2, '火锅油条(1个)', 4.98, 'images/11.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (12, 2, '午餐肉(2片)', 5.98, 'images/12.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (13, 2, '大里脊(1个)', 6.18, 'images/13.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (14, 2, '撒尿牛丸(2个)', 5.98, 'images/14.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (15, 2, '醇香麻辣烫(无汤)', 3.98, 'images/15.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (16, 3, '【优惠福利】点亮⭐️ 仅限1瓶饮料(多点不送)', 2.28, 'images/16.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (17, 3, '【慈爸爸力荐】横扫饥饿单人餐', 27.90, 'images/17.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (18, 3, '【冰雪节】福气满满单人套餐', 24.90, 'images/18.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (19, 3, '【手感火热】单人专属套餐直营', 27.90, 'images/19.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (20, 3, '超级A麻辣烫套餐', 31.88, 'images/20.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (21, 3, '【赠*冰红茶】七荤八素加主食麻辣烫套餐', 32.88, 'images/21.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (22, 3, '超级B麻辣烫套餐', 35.88, 'images/22.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (23, 3, '【赠*冰红茶】大片里脊麻辣烫套餐加主食', 31.88, 'images/23.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (24, 3, '【赠*冰红茶】人气低卡麻辣烫套餐加粉丝', 31.88, 'images/24.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (25, 3, '【童心未泯】单人餐', 24.90, 'images/25.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (26, 3, '【欢乐无限】双人餐', 38.90, 'images/26.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (27, 4, '土豆(3-4片)', 3.98, 'images/27.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (28, 4, '腐竹(3-4个)', 4.58, 'images/28.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (29, 4, '方便面(1个)', 4.98, 'images/29.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (30, 4, '日本豆腐(1个)', 4.98, 'images/30.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (31, 4, '自己煎的蛋(1个)', 5.68, 'images/31.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (32, 4, '奥尔良鸡肉片(2-3片)', 5.98, 'images/32.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (33, 4, '生菜(1份)', 3.98, 'images/33.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (34, 4, '火锅川粉(1份)', 4.98, 'images/34.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (35, 4, '金针菇(1份)', 4.98, 'images/35.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (36, 4, '鱼豆腐(2个)', 4.98, 'images/36.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (37, 4, '刀削面(1包)', 5.98, 'images/37.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (38, 4, '大里脊(1个)', 6.18, 'images/38.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (39, 4, '娃娃菜(1份)', 4.98, 'images/39.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (40, 5, '龙虾饼(1个)', 5.98, 'images/40.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (41, 5, '骨肉相连', 5.98, 'images/41.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (42, 5, '红油五花肉片(3片)', 5.88, 'images/42.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (43, 5, '掌中宝(1份)', 5.98, 'images/43.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (44, 5, '玉米香肠(1个)', 5.98, 'images/44.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (45, 5, '干猪皮(2片)', 5.98, 'images/45.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (46, 5, '臻品纯肉肠(1个)', 5.98, 'images/46.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (47, 5, '麻辣肉片(1份)', 5.98, 'images/47.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (48, 5, '虾滑(1根) ', 9.80, 'images/48.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (49, 5, '腊肠(3-4块)', 5.98, 'images/49.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (50, 6, '红糖酥饼', 10.00, 'images/50.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (51, 7, '耗油青菜小炒/大份', 12.00, 'images/51.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (52, 7, '耗油青菜小炒/小份', 10.00, 'images/52.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (53, 7, '鸡蛋木耳小炒/大份', 12.00, 'images/53.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (54, 7, '鸡蛋木耳小炒/小份', 10.00, 'images/54.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (55, 7, '可乐鸡翅', 11.00, 'images/55.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (56, 7, '油菜沙拉/大份', 11.00, 'images/56.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (57, 7, '油菜沙拉/小份', 9.00, 'images/57.jpg', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (58, 8, '美式咖啡/中杯', 8.00, 'images/58.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (59, 8, '香柠咖啡/大杯', 14.00, 'images/59.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (60, 8, '拿铁咖啡/大杯', 16.00, 'images/60.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (61, 8, '拿铁咖啡/中杯', 13.00, 'images/61.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (62, 8, '卡布奇诺/大杯', 16.00, 'images/62.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (63, 8, '卡布奇诺/中杯', 13.00, 'images/63.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (64, 8, '摩卡咖啡/大杯', 19.00, 'images/64.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (65, 8, '摩卡咖啡/中杯', 16.00, 'images/65.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (66, 8, '珍珠拿铁/大杯', 16.00, 'images/66.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (67, 8, '香草拿铁/大杯', 19.00, 'images/67.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (68, 8, '香草拿铁/中杯', 16.00, 'images/68.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (69, 8, '法式奶霜咖啡/大杯', 15.00, 'images/69.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (70, 8, '海盐焦糖拿铁/大杯', 18.00, 'images/70.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (71, 8, '海盐焦糖拿铁/中杯', 15.00, 'images/71.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (72, 8, '香柠咖啡', 14.00, 'images/72.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (73, 9, '法式奶霜草莓果茶（大杯）', 15.00, 'images/73.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (74, 9, '鲜百香双响炮/大杯', 13.00, 'images/74.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (75, 9, '柠檬霸/大杯', 13.00, 'images/75.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (76, 9, '金桔柠檬汁/中杯', 11.00, 'images/76.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (77, 9, '葡萄柚绿茶/中杯', 11.00, 'images/77.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (78, 9, '草莓果茶/中杯', 10.00, 'images/78.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (79, 9, '鲜柠檬红茶/中杯', 10.00, 'images/79.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (80, 9, '鲜柠檬绿茶/中杯', 10.00, 'images/80.webp', 1, '2024-05-29 20:30:07', NULL, NULL);
INSERT INTO `pre_food` VALUES (81, 9, '鲜百香绿茶/中杯', 10.00, 'images/81.webp', 1, '2024-05-29 20:30:07', NULL, NULL);

-- ----------------------------
-- Table structure for pre_order
-- ----------------------------
DROP TABLE IF EXISTS `pre_order`;
CREATE TABLE `pre_order`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `pickup_no` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `price` decimal(10, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  `promotion` decimal(10, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  `number` int UNSIGNED NOT NULL DEFAULT 0,
  `is_paid` bit(1) NOT NULL DEFAULT b'0',
  `is_taken` bit(1) NOT NULL DEFAULT b'0',
  `comment` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `status` tinyint UNSIGNED NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pay_time` datetime NULL DEFAULT NULL,
  `taken_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pre_order
-- ----------------------------
INSERT INTO `pre_order` VALUES (1, 'WD100320240531223729', 'A01', '1', 12.00, 3.00, 4, b'1', b'0', '不要辣，多放香菜', 3, '2024-05-30 01:10:35', '2024-05-30 01:10:39', '2024-05-30 01:10:43');
INSERT INTO `pre_order` VALUES (2, 'WD100420240531223729', 'A02', '1', 13.00, 2.00, 3, b'1', b'1', '放门把手', 3, '2024-05-30 01:12:20', '2024-05-30 01:11:44', '2024-05-30 01:11:46');
INSERT INTO `pre_order` VALUES (3, 'WD100520240531223729', 'A03', '1', 14.00, 0.00, 1, b'1', b'1', '放门把手', 3, '2024-05-30 01:12:15', '2024-05-30 01:12:24', '2024-05-30 01:12:28');
INSERT INTO `pre_order` VALUES (4, 'WD100620240531223729', 'A04', '1', 15.00, 0.00, 1, b'1', b'1', '放门把手', 3, '2024-05-30 01:13:37', '2024-05-30 01:12:44', NULL);
INSERT INTO `pre_order` VALUES (5, 'WD100720240531223729', 'A05', '1', 16.00, 0.00, 2, b'1', b'1', '放门把手', 3, '2024-05-30 01:13:37', '2024-05-30 01:13:43', NULL);
INSERT INTO `pre_order` VALUES (6, 'WD100820240531223729', 'A06', '1', 17.00, 0.00, 1, b'1', b'1', '放门把手', 3, '2024-05-30 01:13:37', '2024-05-30 01:13:46', NULL);
INSERT INTO `pre_order` VALUES (7, 'WD13', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 12.45, 0.00, 15, b'0', b'0', NULL, 1, '2024-06-01 19:03:56', NULL, NULL);
INSERT INTO `pre_order` VALUES (8, 'WD0', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 10.96, 0.00, 2, b'0', b'0', NULL, 1, '2024-06-01 19:12:45', NULL, NULL);
INSERT INTO `pre_order` VALUES (9, 'WD5', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 2.49, 0.00, 4, b'0', b'0', NULL, 1, '2024-06-01 19:16:06', NULL, NULL);
INSERT INTO `pre_order` VALUES (10, 'WD14', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 2.49, 0.00, 4, b'0', b'0', NULL, 1, '2024-06-01 19:25:11', NULL, NULL);
INSERT INTO `pre_order` VALUES (11, 'WD17', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 2.49, 0.00, 3, b'0', b'0', '我第一次下单', 1, '2024-06-01 20:11:39', NULL, NULL);
INSERT INTO `pre_order` VALUES (12, 'WD13', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 7.47, 0.00, 4, b'0', b'0', NULL, 1, '2024-06-01 20:15:10', NULL, NULL);
INSERT INTO `pre_order` VALUES (13, 'WD13', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 0.01, 0.00, 1, b'0', b'0', NULL, 1, '2024-06-01 20:20:06', NULL, NULL);
INSERT INTO `pre_order` VALUES (14, 'WD6', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 0.21, 0.00, 3, b'0', b'0', 'gyuguyguyguyy', 2, '2024-06-01 20:23:13', NULL, NULL);
INSERT INTO `pre_order` VALUES (15, 'WD13', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 0.01, 0.00, 1, b'0', b'0', '111111', 2, '2024-06-01 20:24:26', NULL, NULL);
INSERT INTO `pre_order` VALUES (16, 'WD10', NULL, 'oZXf_5Y60R51Ux5oc7Arhf1v1HUU', 0.01, 0.00, 3, b'0', b'0', '我不吃辣，嘻嘻嘻', 2, '2024-06-01 20:25:37', NULL, NULL);

-- ----------------------------
-- Table structure for pre_order_food
-- ----------------------------
DROP TABLE IF EXISTS `pre_order_food`;
CREATE TABLE `pre_order_food`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` int UNSIGNED NOT NULL DEFAULT 0,
  `food_id` int UNSIGNED NOT NULL DEFAULT 0,
  `number` int UNSIGNED NOT NULL DEFAULT 0,
  `price` decimal(10, 2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pre_order_food
-- ----------------------------
INSERT INTO `pre_order_food` VALUES (1, 1, 1, 1, 100.00);
INSERT INTO `pre_order_food` VALUES (2, 1, 2, 1, 10.00);
INSERT INTO `pre_order_food` VALUES (3, 2, 3, 1, 11.00);
INSERT INTO `pre_order_food` VALUES (4, 3, 3, 1, 111.00);
INSERT INTO `pre_order_food` VALUES (5, 4, 4, 4, 444.00);
INSERT INTO `pre_order_food` VALUES (6, 5, 5, 1, 111.00);
INSERT INTO `pre_order_food` VALUES (7, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (8, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (9, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (10, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (11, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (12, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (13, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (14, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (15, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (16, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (17, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (18, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (19, 7, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (20, 7, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (21, 7, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (22, 8, 11, 1, 4.98);
INSERT INTO `pre_order_food` VALUES (23, 8, 12, 1, 5.98);
INSERT INTO `pre_order_food` VALUES (24, 9, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (25, 9, 4, 2, 0.20);
INSERT INTO `pre_order_food` VALUES (26, 9, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (27, 10, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (28, 10, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (29, 10, 5, 2, 2.28);
INSERT INTO `pre_order_food` VALUES (30, 11, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (31, 11, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (32, 11, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (33, 12, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (34, 12, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (35, 12, 5, 1, 2.28);
INSERT INTO `pre_order_food` VALUES (36, 12, 11, 1, 4.98);
INSERT INTO `pre_order_food` VALUES (37, 13, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (38, 14, 1, 1, 0.00);
INSERT INTO `pre_order_food` VALUES (39, 14, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (40, 14, 4, 1, 0.20);
INSERT INTO `pre_order_food` VALUES (41, 15, 3, 1, 0.01);
INSERT INTO `pre_order_food` VALUES (42, 16, 1, 1, 0.00);
INSERT INTO `pre_order_food` VALUES (43, 16, 2, 1, 0.00);
INSERT INTO `pre_order_food` VALUES (44, 16, 3, 1, 0.01);

-- ----------------------------
-- Table structure for pre_setting
-- ----------------------------
DROP TABLE IF EXISTS `pre_setting`;
CREATE TABLE `pre_setting`  (
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pre_setting
-- ----------------------------
INSERT INTO `pre_setting` VALUES ('appid', '');
INSERT INTO `pre_setting` VALUES ('appsecret', '');
INSERT INTO `pre_setting` VALUES ('img_ad', '/static/uploads/default/image_ad.png');
INSERT INTO `pre_setting` VALUES ('img_category', '[\"\\/static\\/uploads\\/default\\/bottom_1.png\",\"\\/static\\/uploads\\/default\\/bottom_2.png\",\"\\/static\\/uploads\\/default\\/bottom_3.png\",\"\\/static\\/uploads\\/default\\/bottom_1.png\"]');
INSERT INTO `pre_setting` VALUES ('img_swiper', '[\"\\/static\\/uploads\\/default\\/banner_1.png\",\"\\/static\\/uploads\\/default\\/banner_2.png\",\"\\/static\\/uploads\\/default\\/banner_3.png\"]');
INSERT INTO `pre_setting` VALUES ('promotion', '[{\"k\":50,\"v\":10}]');

-- ----------------------------
-- Table structure for pre_user
-- ----------------------------
DROP TABLE IF EXISTS `pre_user`;
CREATE TABLE `pre_user`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `price` decimal(10, 2) UNSIGNED NOT NULL DEFAULT 0.00,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pre_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
