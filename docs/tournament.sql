-- =========================================================
-- 1) 赛事配置
-- =========================================================
CREATE TABLE IF NOT EXISTS tournament_config (
                                                 id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                 event_game_id BIGINT UNSIGNED NOT NULL COMMENT '玩法ID',
                                                 tournament_type TINYINT(3) UNSIGNED NOT NULL COMMENT '赛事类型',
                                                 division_mode TINYINT(3) UNSIGNED NOT NULL COMMENT '赛区模式',
                                                 match_mode TINYINT(3) UNSIGNED NOT NULL COMMENT '匹配模式',
                                                 robot_mode TINYINT(3) UNSIGNED NULL COMMENT '机器人模式(可选)',
                                                 robot_enabled BIT(1) NOT NULL DEFAULT b'0' COMMENT '是否启用机器人',
                                                 current_stage TINYINT(3) UNSIGNED NULL COMMENT '当前阶段',
                                                 total_stages INT NOT NULL DEFAULT 0 COMMENT '总场次数',
                                                 stage_duration_days INT NOT NULL DEFAULT 1 COMMENT '场次持续天数',
                                                 registration_start_time DATETIME NOT NULL COMMENT '报名开始时间',
                                                 registration_end_time DATETIME NOT NULL COMMENT '报名结束时间',
                                                 tournament_start_time DATETIME NOT NULL COMMENT '比赛开始时间',
                                                 tournament_end_time DATETIME NOT NULL COMMENT '比赛结束时间',
                                                 max_participants INT NOT NULL DEFAULT 0 COMMENT '最大报名人数',
                                                 min_participants INT NOT NULL DEFAULT 0 COMMENT '最小报名人数',
                                                 group_size INT NOT NULL DEFAULT 0 COMMENT '每组人数',
                                                 max_daily_challenges INT NOT NULL DEFAULT 0 COMMENT '每日最大挑战次数',
                                                 challenge_timeout_minutes INT NOT NULL DEFAULT 0 COMMENT '挑战超时时间(分钟)',
                                                 attributes JSON NULL COMMENT '扩展配置',
                                                 created_by VARCHAR(64) NOT NULL,
                                                 created_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                                 updated_by VARCHAR(64) NOT NULL,
                                                 updated_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                                 is_deleted BIT(1) NOT NULL DEFAULT b'0',
                                                 PRIMARY KEY (id),
                                                 UNIQUE KEY uk_tournament_config_event_game_id (event_game_id),
                                                 KEY idx_tournament_time_window (registration_end_time, tournament_end_time),
                                                 KEY idx_tournament_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='赛事配置表';

-- =========================================================
-- 2) 参赛记录
-- =========================================================
CREATE TABLE IF NOT EXISTS tournament_participation_record (
                                                               id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                               event_game_id BIGINT UNSIGNED NOT NULL COMMENT '玩法ID',
                                                               participant_id VARCHAR(64) NOT NULL COMMENT '参赛单位ID',
                                                               participant_type TINYINT(3) UNSIGNED NOT NULL COMMENT '参赛单位类型',
                                                               status TINYINT(3) UNSIGNED NOT NULL COMMENT '参赛状态',
                                                               registration_time DATETIME NOT NULL COMMENT '报名时间',
                                                               total_score DECIMAL(18,4) NOT NULL DEFAULT 0 COMMENT '总得分',
                                                               created_by VARCHAR(64) NOT NULL,
                                                               created_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                                               updated_by VARCHAR(64) NOT NULL,
                                                               updated_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                                               is_deleted BIT(1) NOT NULL DEFAULT b'0',
                                                               PRIMARY KEY (id),
                                                               UNIQUE KEY uk_participant_unique (event_game_id, participant_id, participant_type, is_deleted),
                                                               KEY idx_participant_query (event_game_id, status, participant_type),
                                                               KEY idx_participant_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='参赛记录表';

-- =========================================================
-- 3) 机器人实例
-- =========================================================
CREATE TABLE IF NOT EXISTS tournament_robot_instance (
                                                         id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                         event_game_id BIGINT UNSIGNED NOT NULL COMMENT '玩法ID',
                                                         template_id BIGINT UNSIGNED NULL COMMENT '模板ID(CC配置)',
                                                         robot_instance_type TINYINT(3) UNSIGNED NOT NULL COMMENT '机器人实例类型',
                                                         robot_id VARCHAR(64) NOT NULL COMMENT '机器人ID',
                                                         nickname VARCHAR(100) NOT NULL COMMENT '机器人昵称',
                                                         avatar_url VARCHAR(500) NULL COMMENT '机器人头像',
                                                         status TINYINT(3) UNSIGNED NOT NULL COMMENT '机器人状态',
                                                         created_by VARCHAR(64) NOT NULL,
                                                         created_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                                         updated_by VARCHAR(64) NOT NULL,
                                                         updated_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                                         is_deleted BIT(1) NOT NULL DEFAULT b'0',
                                                         PRIMARY KEY (id),
                                                         UNIQUE KEY uk_robot_unique (event_game_id, robot_id, is_deleted),
                                                         KEY idx_robot_event_status (event_game_id, status),
                                                         KEY idx_robot_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='赛事机器人实例表';

-- =========================================================
-- 4) 分组
-- =========================================================
CREATE TABLE IF NOT EXISTS tournament_group (
                                                id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                event_game_id BIGINT UNSIGNED NOT NULL COMMENT '玩法ID',
                                                stage_type TINYINT(3) UNSIGNED NOT NULL COMMENT '场次类型',
                                                stage_number INT NOT NULL DEFAULT 0 COMMENT '场次号',
                                                group_date DATE NULL COMMENT '分组日期',
                                                group_number INT NOT NULL DEFAULT 0 COMMENT '组号',
                                                tier_name VARCHAR(64) NULL COMMENT '段位名称',
                                                tier_order INT NULL COMMENT '段位顺序',
                                                created_by VARCHAR(64) NOT NULL,
                                                created_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                                updated_by VARCHAR(64) NOT NULL,
                                                updated_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                                is_deleted BIT(1) NOT NULL DEFAULT b'0',
                                                PRIMARY KEY (id),
                                                KEY idx_group_event_stage (event_game_id, stage_type, stage_number),
                                                KEY idx_group_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='赛事分组表';

-- =========================================================
-- 5) 分组人员关联
-- =========================================================
CREATE TABLE IF NOT EXISTS tournament_group_record (
                                                       id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                       group_id BIGINT UNSIGNED NOT NULL COMMENT '分组ID',
                                                       participant_id VARCHAR(64) NOT NULL COMMENT '参赛单位ID',
                                                       participant_type TINYINT(3) UNSIGNED NOT NULL COMMENT '参赛单位类型',
                                                       created_by VARCHAR(64) NOT NULL,
                                                       created_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                                       updated_by VARCHAR(64) NOT NULL,
                                                       updated_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                                       is_deleted BIT(1) NOT NULL DEFAULT b'0',
                                                       PRIMARY KEY (id),
                                                       UNIQUE KEY uk_group_participant (group_id, participant_id, participant_type, is_deleted),
                                                       KEY idx_group_record_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分组记录表';

-- =========================================================
-- 6) 挑战记录
-- =========================================================
CREATE TABLE IF NOT EXISTS tournament_challenge_record (
                                                           id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                           event_game_id BIGINT UNSIGNED NOT NULL COMMENT '玩法ID',
                                                           participant_id VARCHAR(64) NOT NULL COMMENT '参赛单位ID',
                                                           participant_type TINYINT(3) UNSIGNED NOT NULL COMMENT '参赛单位类型',
                                                           contributor_id VARCHAR(64) NULL COMMENT '贡献者ID',
                                                           cycle_number INT NULL COMMENT '周期号',
                                                           segment_number INT NULL COMMENT '片号',
                                                           stage_number INT NULL COMMENT '场次号',
                                                           group_id BIGINT UNSIGNED NULL COMMENT '分组ID',
                                                           status TINYINT(3) UNSIGNED NOT NULL COMMENT '挑战状态',
                                                           start_time DATETIME NULL COMMENT '开始时间',
                                                           end_time DATETIME NULL COMMENT '结束时间',
                                                           base_score DECIMAL(18,4) NULL COMMENT '基础分',
                                                           final_score DECIMAL(18,4) NULL COMMENT '最终分',
                                                           exercise_details JSON NULL COMMENT '运动项详情JSON',
                                                           created_by VARCHAR(64) NOT NULL,
                                                           created_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                                           updated_by VARCHAR(64) NOT NULL,
                                                           updated_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                                           is_deleted BIT(1) NOT NULL DEFAULT b'0',
                                                           PRIMARY KEY (id),
                                                           KEY idx_challenge_stage (event_game_id, stage_number, status),
                                                           KEY idx_challenge_participant (event_game_id, participant_id, participant_type),
                                                           KEY idx_challenge_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='挑战记录表';

-- =========================================================
-- 7) 得分记录
-- =========================================================
CREATE TABLE IF NOT EXISTS tournament_score_record (
                                                       id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                       event_game_id BIGINT UNSIGNED NOT NULL COMMENT '玩法ID',
                                                       participant_id VARCHAR(64) NOT NULL COMMENT '参赛单位ID',
                                                       participant_type TINYINT(3) UNSIGNED NOT NULL COMMENT '参赛单位类型',
                                                       score_type TINYINT(3) UNSIGNED NOT NULL COMMENT '得分类型(STAGE/SEGMENT/CYCLE)',
                                                       cycle_number INT NULL COMMENT '周期号',
                                                       segment_number INT NULL COMMENT '片号',
                                                       stage_number INT NULL COMMENT '场次号',
                                                       group_id BIGINT UNSIGNED NULL COMMENT '分组ID',
                                                       score DECIMAL(18,4) NOT NULL DEFAULT 0 COMMENT '得分',
                                                       created_by VARCHAR(64) NOT NULL,
                                                       created_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                                       updated_by VARCHAR(64) NOT NULL,
                                                       updated_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                                       is_deleted BIT(1) NOT NULL DEFAULT b'0',
                                                       PRIMARY KEY (id),
                                                       KEY idx_score_event_type (event_game_id, score_type),
                                                       KEY idx_score_cycle (event_game_id, cycle_number, score_type),
                                                       KEY idx_score_segment (event_game_id, segment_number, score_type),
                                                       KEY idx_score_stage (event_game_id, stage_number, score_type),
                                                       KEY idx_score_participant (event_game_id, participant_id, participant_type),
                                                       KEY idx_score_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='得分记录表';

-- =========================================================
-- 8) 排行榜
-- =========================================================
CREATE TABLE IF NOT EXISTS tournament_ranking (
                                                  id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                  event_game_id BIGINT UNSIGNED NOT NULL COMMENT '玩法ID',
                                                  group_id BIGINT UNSIGNED NULL COMMENT '分组ID',
                                                  participant_type TINYINT(3) UNSIGNED NOT NULL COMMENT '参赛单位类型',
                                                  participant_id VARCHAR(64) NOT NULL COMMENT '参赛单位ID',
                                                  ranking_type TINYINT(3) UNSIGNED NOT NULL COMMENT '排行榜类型',
                                                  score DECIMAL(18,4) NOT NULL DEFAULT 0 COMMENT '分数',
                                                  rank_no INT NOT NULL DEFAULT 0 COMMENT '排名',
                                                  avatar_url VARCHAR(500) NULL COMMENT '头像',
                                                  nickname VARCHAR(100) NULL COMMENT '昵称',
                                                  created_by VARCHAR(64) NOT NULL,
                                                  created_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                                  updated_by VARCHAR(64) NOT NULL,
                                                  updated_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                                  is_deleted BIT(1) NOT NULL DEFAULT b'0',
                                                  PRIMARY KEY (id),
                                                  KEY idx_ranking_query (event_game_id, ranking_type, rank_no),
                                                  KEY idx_ranking_participant (event_game_id, participant_id, participant_type),
                                                  KEY idx_ranking_deleted (is_deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='赛事排行榜表';

-- =========================================================
-- 9) 任务调度表
-- =========================================================
CREATE TABLE IF NOT EXISTS tournament_task (
                                               id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
                                               tournament_id BIGINT UNSIGNED NOT NULL COMMENT '赛事ID',
                                               task_type TINYINT(3) UNSIGNED NOT NULL COMMENT '任务类型',
                                               period_no INT NOT NULL DEFAULT 0 COMMENT '期号',
                                               trigger_time DATETIME NOT NULL COMMENT '触发时间',
                                               status TINYINT(3) UNSIGNED NOT NULL COMMENT '任务状态',
                                               retry_count INT NOT NULL DEFAULT 0 COMMENT '重试次数',
                                               depends_on_task_id BIGINT UNSIGNED NULL COMMENT '前置任务ID',
                                               depends_on_status TINYINT(3) UNSIGNED NOT NULL DEFAULT 3 COMMENT '前置任务期望状态(默认SUCCESS)',
                                               run_trace_id VARCHAR(64) NULL COMMENT '任务链路追踪ID',
                                               created_by VARCHAR(64) NOT NULL,
                                               created_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                                               updated_by VARCHAR(64) NOT NULL,
                                               updated_date TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
                                               PRIMARY KEY (id),
                                               KEY idx_task_trigger_status (trigger_time, status),
                                               KEY idx_task_tournament_status (tournament_id, status),
                                               KEY idx_depends_on_task (depends_on_task_id, depends_on_status),
                                               KEY idx_task_run_trace (run_trace_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='赛事任务调度表';