-- =====================================================
-- 简单版数据库修复脚本 - 直接在Navicat中执行
-- =====================================================

USE youth_mental_health;

-- 1. 添加 recommend_preference 字段（如果已存在会报错，忽略即可）
ALTER TABLE sys_user ADD COLUMN recommend_preference VARCHAR(50) DEFAULT NULL COMMENT '推荐偏好(疏导/预防/进阶)' AFTER health_status;

-- 2. 添加 points 字段
ALTER TABLE sys_user ADD COLUMN points INT DEFAULT 0 COMMENT '用户积分' AFTER recommend_preference;

-- 3. 创建学习记录表
CREATE TABLE IF NOT EXISTS user_learning_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    content_type TINYINT NOT NULL COMMENT '内容类型(1科普/2宣传/3资讯/4活动)',
    content_id BIGINT NOT NULL COMMENT '内容ID',
    action_type TINYINT NOT NULL COMMENT '行为类型(1浏览/2收藏/3评论/4报名)',
    duration INT DEFAULT 0 COMMENT '浏览时长(秒)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_content (content_type, content_id),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户学习记录表';

-- 4. 创建推荐记录表
CREATE TABLE IF NOT EXISTS user_recommend_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    recommend_type TINYINT NOT NULL COMMENT '推荐类型(1科普/2活动/3AI咨询)',
    content_id BIGINT NOT NULL COMMENT '内容ID',
    reason VARCHAR(200) DEFAULT NULL COMMENT '推荐理由',
    is_clicked TINYINT DEFAULT 0 COMMENT '是否点击(0否/1是)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户推荐记录表';

-- 5. 创建积分记录表
CREATE TABLE IF NOT EXISTS user_points_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    points INT NOT NULL COMMENT '积分(正为增加,负为减少)',
    source_type TINYINT NOT NULL COMMENT '来源类型(1测试/2学习/3活动/4签到)',
    source_id BIGINT DEFAULT NULL COMMENT '来源ID',
    description VARCHAR(200) DEFAULT NULL COMMENT '描述',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_user_id (user_id),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户积分记录表';

-- 6. 创建预警记录表
CREATE TABLE IF NOT EXISTS user_warning_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    risk_level VARCHAR(20) NOT NULL COMMENT '风险等级(HIGH/MEDIUM/LOW)',
    keywords VARCHAR(500) DEFAULT NULL COMMENT '触发关键词',
    status TINYINT DEFAULT 0 COMMENT '状态(0待处理/1已处理)',
    handle_remark VARCHAR(500) DEFAULT NULL COMMENT '处理备注',
    handler_id BIGINT DEFAULT NULL COMMENT '处理人ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户预警记录表';

-- 完成
SELECT '数据库修复完成！请重启后端服务' AS message;
