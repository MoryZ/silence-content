/*
 Navicat Premium Dump SQL

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80044 (8.0.44)
 Source Host           : localhost:3306
 Source Schema         : silence-content

 Target Server Type    : MySQL
 Target Server Version : 80044 (8.0.44)
 File Encoding         : 65001

 Date: 24/04/2026 22:00:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tournament_challenge_record
-- ----------------------------
DROP TABLE IF EXISTS `tournament_challenge_record`;
CREATE TABLE `tournament_challenge_record` (
                                               `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                               `event_game_id` bigint unsigned NOT NULL COMMENT '玩法ID',
                                               `participant_id` varchar(64) NOT NULL COMMENT '参赛单位ID',
                                               `participant_type` tinyint unsigned NOT NULL COMMENT '参赛单位类型',
                                               `contributor_id` varchar(64) DEFAULT NULL COMMENT '贡献者ID',
                                               `cycle_number` int DEFAULT NULL COMMENT '周期号',
                                               `segment_number` int DEFAULT NULL COMMENT '片号',
                                               `stage_number` int DEFAULT NULL COMMENT '场次号',
                                               `group_id` bigint unsigned DEFAULT NULL COMMENT '分组ID',
                                               `status` tinyint unsigned NOT NULL COMMENT '挑战状态',
                                               `start_time` datetime DEFAULT NULL COMMENT '开始时间',
                                               `end_time` datetime DEFAULT NULL COMMENT '结束时间',
                                               `base_score` decimal(18,4) DEFAULT NULL COMMENT '基础分',
                                               `final_score` decimal(18,4) DEFAULT NULL COMMENT '最终分',
                                               `exercise_details` json DEFAULT NULL COMMENT '运动项详情JSON',
                                               `created_by` varchar(64) NOT NULL,
                                               `created_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                               `updated_by` varchar(64) NOT NULL,
                                               `updated_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                               `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                                               PRIMARY KEY (`id`),
                                               KEY `idx_challenge_stage` (`event_game_id`,`stage_number`,`status`),
                                               KEY `idx_challenge_participant` (`event_game_id`,`participant_id`,`participant_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='挑战记录表';

-- ----------------------------
-- Records of tournament_challenge_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tournament_config
-- ----------------------------
DROP TABLE IF EXISTS `tournament_config`;
CREATE TABLE `tournament_config` (
                                     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                     `event_game_id` bigint unsigned NOT NULL COMMENT '玩法ID',
                                     `tournament_type` tinyint unsigned NOT NULL COMMENT '赛事类型',
                                     `division_mode` tinyint unsigned NOT NULL COMMENT '赛区模式',
                                     `match_mode` tinyint unsigned NOT NULL COMMENT '匹配模式',
                                     `robot_mode` tinyint unsigned DEFAULT NULL COMMENT '机器人模式(可选)',
                                     `robot_enabled` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否启用机器人',
                                     `current_stage` tinyint unsigned DEFAULT NULL COMMENT '当前阶段',
                                     `total_stages` int NOT NULL DEFAULT '0' COMMENT '总场次数',
                                     `stage_duration_days` int NOT NULL DEFAULT '1' COMMENT '场次持续天数',
                                     `registration_start_time` datetime NOT NULL COMMENT '报名开始时间',
                                     `registration_end_time` datetime NOT NULL COMMENT '报名结束时间',
                                     `tournament_start_time` datetime NOT NULL COMMENT '比赛开始时间',
                                     `tournament_end_time` datetime NOT NULL COMMENT '比赛结束时间',
                                     `max_participants` int NOT NULL DEFAULT '0' COMMENT '最大报名人数',
                                     `min_participants` int NOT NULL DEFAULT '0' COMMENT '最小报名人数',
                                     `group_size` int NOT NULL DEFAULT '0' COMMENT '每组人数',
                                     `max_daily_challenges` int NOT NULL DEFAULT '0' COMMENT '每日最大挑战次数',
                                     `challenge_timeout_minutes` int NOT NULL DEFAULT '0' COMMENT '挑战超时时间(分钟)',
                                     `attributes` json DEFAULT NULL COMMENT '扩展配置',
                                     `created_by` varchar(64) NOT NULL,
                                     `created_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                     `updated_by` varchar(64) NOT NULL,
                                     `updated_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                     `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `uk_tournament_config_event_game_id` (`event_game_id`),
                                     KEY `idx_tournament_time_window` (`registration_end_time`,`tournament_end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='赛事配置表';

-- ----------------------------
-- Records of tournament_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tournament_group
-- ----------------------------
DROP TABLE IF EXISTS `tournament_group`;
CREATE TABLE `tournament_group` (
                                    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                    `event_game_id` bigint unsigned NOT NULL COMMENT '玩法ID',
                                    `stage_type` tinyint unsigned NOT NULL COMMENT '场次类型',
                                    `stage_number` int NOT NULL DEFAULT '0' COMMENT '场次号',
                                    `group_date` date DEFAULT NULL COMMENT '分组日期',
                                    `group_number` int NOT NULL DEFAULT '0' COMMENT '组号',
                                    `tier_name` varchar(64) DEFAULT NULL COMMENT '段位名称',
                                    `tier_order` int DEFAULT NULL COMMENT '段位顺序',
                                    `tier_image_url` varchar(300) DEFAULT NULL COMMENT '段位图片链接',
                                    `created_by` varchar(64) NOT NULL,
                                    `created_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                    `updated_by` varchar(64) NOT NULL,
                                    `updated_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                    `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                                    PRIMARY KEY (`id`),
                                    KEY `idx_group_event_stage` (`event_game_id`,`stage_type`,`stage_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='赛事分组表';

-- ----------------------------
-- Records of tournament_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tournament_group_record
-- ----------------------------
DROP TABLE IF EXISTS `tournament_group_record`;
CREATE TABLE `tournament_group_record` (
                                           `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                           `group_id` bigint unsigned NOT NULL COMMENT '分组ID',
                                           `participant_id` varchar(64) NOT NULL COMMENT '参赛单位ID',
                                           `participant_type` tinyint unsigned NOT NULL COMMENT '参赛单位类型',
                                           `created_by` varchar(64) NOT NULL,
                                           `created_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                           `updated_by` varchar(64) NOT NULL,
                                           `updated_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                           `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                                           PRIMARY KEY (`id`),
                                           UNIQUE KEY `uk_group_participant` (`group_id`,`participant_id`,`participant_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='分组记录表';

-- ----------------------------
-- Records of tournament_group_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tournament_participation_record
-- ----------------------------
DROP TABLE IF EXISTS `tournament_participation_record`;
CREATE TABLE `tournament_participation_record` (
                                                   `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                   `event_game_id` bigint unsigned NOT NULL COMMENT '玩法ID',
                                                   `participant_id` varchar(64) NOT NULL COMMENT '参赛单位ID',
                                                   `participant_type` tinyint unsigned NOT NULL COMMENT '参赛单位类型',
                                                   `status` tinyint unsigned NOT NULL COMMENT '参赛状态',
                                                   `registration_time` datetime NOT NULL COMMENT '报名时间',
                                                   `total_score` decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT '总得分',
                                                   `created_by` varchar(64) NOT NULL,
                                                   `created_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                                   `updated_by` varchar(64) NOT NULL,
                                                   `updated_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                                   `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                                                   PRIMARY KEY (`id`),
                                                   UNIQUE KEY `uk_participant_unique` (`event_game_id`,`participant_id`,`participant_type`),
                                                   KEY `idx_participant_query` (`event_game_id`,`status`,`participant_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='参赛记录表';

-- ----------------------------
-- Records of tournament_participation_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tournament_ranking
-- ----------------------------
DROP TABLE IF EXISTS `tournament_ranking`;
CREATE TABLE `tournament_ranking` (
                                      `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                      `event_game_id` bigint unsigned NOT NULL COMMENT '玩法ID',
                                      `group_id` bigint unsigned DEFAULT NULL COMMENT '分组ID',
                                      `participant_type` tinyint unsigned NOT NULL COMMENT '参赛单位类型',
                                      `participant_id` varchar(64) NOT NULL COMMENT '参赛单位ID',
                                      `ranking_type` tinyint unsigned NOT NULL COMMENT '排行榜类型',
                                      `score` decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT '分数',
                                      `rank_no` int NOT NULL DEFAULT '0' COMMENT '排名',
                                      `avatar_url` varchar(500) DEFAULT NULL COMMENT '头像',
                                      `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
                                      `created_by` varchar(64) NOT NULL,
                                      `created_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                      `updated_by` varchar(64) NOT NULL,
                                      `updated_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                      `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                                      PRIMARY KEY (`id`),
                                      KEY `idx_ranking_query` (`event_game_id`,`ranking_type`,`rank_no`),
                                      KEY `idx_ranking_participant` (`event_game_id`,`participant_id`,`participant_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='赛事排行榜表';

-- ----------------------------
-- Records of tournament_ranking
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tournament_robot_instance
-- ----------------------------
DROP TABLE IF EXISTS `tournament_robot_instance`;
CREATE TABLE `tournament_robot_instance` (
                                             `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                             `event_game_id` bigint unsigned NOT NULL COMMENT '玩法ID',
                                             `template_id` bigint unsigned DEFAULT NULL COMMENT '模板ID(CC配置)',
                                             `robot_instance_type` tinyint unsigned NOT NULL COMMENT '机器人实例类型',
                                             `robot_id` varchar(64) NOT NULL COMMENT '机器人ID',
                                             `nickname` varchar(100) NOT NULL COMMENT '机器人昵称',
                                             `avatar_url` varchar(500) DEFAULT NULL COMMENT '机器人头像',
                                             `status` tinyint unsigned NOT NULL COMMENT '机器人状态',
                                             `created_by` varchar(64) NOT NULL,
                                             `created_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                             `updated_by` varchar(64) NOT NULL,
                                             `updated_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                             PRIMARY KEY (`id`),
                                             KEY `idx_robot_event_status` (`event_game_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='赛事机器人实例表';

-- ----------------------------
-- Records of tournament_robot_instance
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tournament_score_record
-- ----------------------------
DROP TABLE IF EXISTS `tournament_score_record`;
CREATE TABLE `tournament_score_record` (
                                           `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                           `event_game_id` bigint unsigned NOT NULL COMMENT '玩法ID',
                                           `participant_id` varchar(64) NOT NULL COMMENT '参赛单位ID',
                                           `participant_type` tinyint unsigned NOT NULL COMMENT '参赛单位类型',
                                           `score_type` tinyint unsigned NOT NULL COMMENT '得分类型(STAGE/SEGMENT/CYCLE)',
                                           `cycle_number` int DEFAULT NULL COMMENT '周期号',
                                           `segment_number` int DEFAULT NULL COMMENT '片号',
                                           `stage_number` int DEFAULT NULL COMMENT '场次号',
                                           `group_id` bigint unsigned DEFAULT NULL COMMENT '分组ID',
                                           `score` decimal(18,4) NOT NULL DEFAULT '0.0000' COMMENT '得分',
                                           `created_by` varchar(64) NOT NULL,
                                           `created_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                           `updated_by` varchar(64) NOT NULL,
                                           `updated_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                           `is_deleted` bit(1) NOT NULL DEFAULT b'0',
                                           PRIMARY KEY (`id`),
                                           KEY `idx_score_event_type` (`event_game_id`,`score_type`),
                                           KEY `idx_score_cycle` (`event_game_id`,`cycle_number`,`score_type`),
                                           KEY `idx_score_segment` (`event_game_id`,`segment_number`,`score_type`),
                                           KEY `idx_score_stage` (`event_game_id`,`stage_number`,`score_type`),
                                           KEY `idx_score_participant` (`event_game_id`,`participant_id`,`participant_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='得分记录表';

-- ----------------------------
-- Records of tournament_score_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for tournament_task
-- ----------------------------
DROP TABLE IF EXISTS `tournament_task`;
CREATE TABLE `tournament_task` (
                                   `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                   `tournament_id` bigint unsigned NOT NULL COMMENT '赛事配置ID(tournament_config.id)',
                                   `event_game_id` bigint unsigned NOT NULL COMMENT '玩法ID(event_game_id)',
                                   `task_type` tinyint unsigned NOT NULL COMMENT '任务类型',
                                   `stage_no` int DEFAULT NULL COMMENT '场次号',
                                   `segment_no` int DEFAULT NULL COMMENT '片号',
                                   `cycle_no` int DEFAULT NULL COMMENT '周期号',
                                   `trigger_time` datetime NOT NULL COMMENT '触发时间',
                                   `status` tinyint unsigned NOT NULL COMMENT '任务状态',
                                   `retry_count` int NOT NULL DEFAULT '0' COMMENT '重试次数',
                                   `depends_on_task_id` bigint unsigned DEFAULT NULL COMMENT '前置任务ID',
                                   `depends_on_status` tinyint unsigned NOT NULL DEFAULT '3' COMMENT '前置任务期望状态(默认SUCCESS)',
                                   `run_trace_id` varchar(64) DEFAULT NULL COMMENT '任务链路追踪ID',
                                   `created_by` varchar(64) NOT NULL,
                                   `created_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                   `updated_by` varchar(64) NOT NULL,
                                   `updated_date` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                   PRIMARY KEY (`id`),
                                   KEY `idx_task_trigger_status` (`trigger_time`,`status`),
                                   KEY `idx_task_tournament_status` (`tournament_id`,`status`),
                                   KEY `idx_task_event_status` (`event_game_id`,`status`),
                                   KEY `idx_task_stage` (`tournament_id`,`stage_no`,`status`),
                                   KEY `idx_task_segment` (`tournament_id`,`segment_no`,`status`),
                                   KEY `idx_task_cycle` (`tournament_id`,`cycle_no`,`status`),
                                   KEY `idx_depends_on_task` (`depends_on_task_id`,`depends_on_status`),
                                   KEY `idx_task_run_trace` (`run_trace_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='赛事任务调度表';

-- ----------------------------
-- Records of tournament_task
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
