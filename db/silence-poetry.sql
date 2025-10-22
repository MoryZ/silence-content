-- 用户基本信息表
CREATE TABLE `poetry_user` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`openid` VARCHAR (64) UNIQUE NOT NULL COMMENT '微信openid',
	`nickname` VARCHAR (100) COMMENT '用户昵称',
	`avatar_url` VARCHAR (500) COMMENT '头像URL',
	`grade_level` TINYINT UNSIGNED NOT NULL COMMENT '当前年级 1:小学, 2:初中 3:高中',
	`study_goal_daily` INT DEFAULT 10 COMMENT '每日学习目标数量',
	`created_by` VARCHAR (100) NOT NULL COMMENT '创建人员',
	`created_date` TIMESTAMP(3) NOT NULL COMMENT '创建时间',
	`updated_by` VARCHAR (100) NOT NULL COMMENT '更新人员',
	`updated_date` TIMESTAMP(3) NOT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`) USING BTREE
	) COMMENT = '用户基本信息表' ;

-- 用户学习设置表
CREATE TABLE `poetry_user_study_setting` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
	`daily_new_items` INT DEFAULT 5 COMMENT '每日新学数量',
	`daily_review_items` INT DEFAULT 10 COMMENT '每日复习数量',
	`study_reminder_time` TIME COMMENT '学习提醒时间',
	`enable_dark_mode` BOOLEAN DEFAULT FALSE COMMENT '是否开启护眼模式',
	`study_session_minutes` INT DEFAULT 25 COMMENT '单次学习时长(分钟)',
	`created_by` VARCHAR (100) NOT NULL COMMENT '创建人员',
	`created_date` TIMESTAMP(3) NOT NULL COMMENT '创建时间',
	`updated_by` VARCHAR (100) NOT NULL COMMENT '更新人员',
	`updated_date` TIMESTAMP(3) NOT NULL COMMENT '更新时间',
	 PRIMARY KEY (`id`) USING BTREE
) COMMENT = '用户学习设置表';

-- 年级表
CREATE TABLE `grade` (
	`id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`code` VARCHAR (20) NOT NULL COMMENT '年级编码',
	`name` VARCHAR (20) NOT NULL COMMENT '小学/初中/高中',
	`description` VARCHAR (100),
	`created_by` VARCHAR (100) NOT NULL COMMENT '创建人员',
	`created_date` TIMESTAMP(3) NOT NULL COMMENT '创建时间',
	`updated_by` VARCHAR (100) NOT NULL COMMENT '更新人员',
	`updated_date` TIMESTAMP(3) NOT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`) USING BTREE
) COMMENT = '年级表';

-- 内容分类表
CREATE TABLE `category` (
  `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` VARCHAR(50) NOT NULL COMMENT '诗词/文言文/阅读理解 唐诗/宋词/虚词/实词等',
	`code` VARCHAR(64) NOT NULL COMMENT '分类编码',
  `icon` VARCHAR(300) COMMENT '分类图标',
  `sort_order` INT DEFAULT 1,
	`parent_id` INT UNSIGNED NOT NULL COMMENT '父分类ID',
	`created_by` varchar(100) NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间'
)COMMENT = '内容分类表';


-- 学习内容主表 (诗词/文言文/知识点)
CREATE TABLE `learning_content` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(200) NOT NULL COMMENT '标题，如《静夜思》',
  `subtitle` VARCHAR(200) COMMENT '副标题，如"李白"',
  `content_type` ENUM('POETRY', 'CLASSICAL', 'KNOWLEDGE') NOT NULL,
  `grade_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  `subcategory_id` INT NOT NULL,
  `difficulty` ENUM('EASY', 'MEDIUM', 'HARD') DEFAULT 'MEDIUM',

  -- 诗词特有字段
  `original_text` TEXT COMMENT '原文',
  `author` VARCHAR(100) COMMENT '作者',
  `dynasty` VARCHAR(50) COMMENT '朝代',
  `background` TEXT COMMENT '创作背景',

  -- 文言文知识点字段
  `explanation` TEXT COMMENT '知识点解释',
  `usage_examples` JSON COMMENT '用法例句',

  -- 通用字段
  `annotations` JSON COMMENT '注释数组',
  `translation` TEXT COMMENT '翻译',
  `appreciation` TEXT COMMENT '赏析',
  `audio_url` VARCHAR(500) COMMENT '朗诵音频',
  `image_url` VARCHAR(500) COMMENT '配图URL',

  `view_count` INT DEFAULT 0 COMMENT '学习次数',
  `is_enabled` BOOLEAN DEFAULT TRUE,
  `created_by` varchar(100) NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
);

-- 用户学习记录表 (核心表)
CREATE TABLE `user_learning_record` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `content_id` BIGINT NOT NULL,

  -- 记忆曲线相关字段
  `first_studied_at` DATETIME NOT NULL COMMENT '首次学习时间',
  `last_reviewed_at` DATETIME COMMENT '最后复习时间',
  `next_review_at` DATETIME NOT NULL COMMENT '下次复习时间',
  `review_count` INT DEFAULT 0 COMMENT '已复习次数',
  `memory_strength` DECIMAL(3,2) DEFAULT 1.0 COMMENT '记忆强度(0-1)',
  `easiness_factor` DECIMAL(4,2) DEFAULT 2.5 COMMENT '难度系数(1.3-2.5)',
  `learning_phase` ENUM('NEW', 'LEARNING', 'REVIEW', 'MASTERED') DEFAULT 'NEW',

  -- 学习状态
  `is_remembered` BOOLEAN DEFAULT FALSE COMMENT '是否记住',
  `study_duration` INT DEFAULT 0 COMMENT '学习时长(秒)',

  `created_by` varchar(100) NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',

  UNIQUE KEY `uk_user_content` (`user_id`, `content_id`),
  INDEX `idx_next_review` (`next_review_at`),
  INDEX `idx_user_phase` (`user_id`, `learning_phase`)
);

-- 每日学习计划表
CREATE TABLE `daily_study_plan` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `plan_date` DATE NOT NULL COMMENT '计划日期',

  -- 计划内容
  `new_item_ids` JSON COMMENT '新学内容ID列表',
  `review_item_ids` JSON COMMENT '复习内容ID列表',

  -- 完成情况
  `completed_new_items` JSON DEFAULT '[]' COMMENT '已完成新学内容',
  `completed_review_items` JSON DEFAULT '[]' COMMENT '已完成复习内容',
  `completion_rate` DECIMAL(5,2) DEFAULT 0.0 COMMENT '完成率',

  `created_by` varchar(100) NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  UNIQUE KEY `uk_user_date` (`user_id`, `plan_date`)
);

-- 用户学习统计表
CREATE TABLE `user_learning_stats` (
  `user_id` BIGINT PRIMARY KEY,
  `total_study_days` INT DEFAULT 0,
  `total_items_learned` INT DEFAULT 0,
  `total_study_minutes` INT DEFAULT 0,
  `current_streak` INT DEFAULT 0 COMMENT '当前连续学习天数',
  `longest_streak` INT DEFAULT 0 COMMENT '最长连续学习天数',
  `last_study_date` DATE COMMENT '最后学习日期',
  `created_by` varchar(100) NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间'
);

-- 学习笔记表
CREATE TABLE `study_note` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `content_id` BIGINT NOT NULL,
  `note_content` TEXT NOT NULL,
  `tags` JSON COMMENT '笔记标签',
  `is_public` BOOLEAN DEFAULT FALSE COMMENT '是否公开',
 `created_by` varchar(100) NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',

  INDEX `idx_user_content` (`user_id`, `content_id`)
);

-- 收藏表
CREATE TABLE `user_favorite` (
  `id` BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `content_id` BIGINT UNSIGNED NOT NULL,
  `created_by` varchar(100) NOT NULL COMMENT '创建人员',
  `created_date` timestamp(3) NOT NULL COMMENT '创建时间',
  `updated_by` varchar(100) NOT NULL COMMENT '更新人员',
  `updated_date` timestamp(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_favorite` (`user_id`, `content_id`)
);