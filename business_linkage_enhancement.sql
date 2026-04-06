-- =====================================================
-- 业务联动增强 - 数据库变更脚本
-- 功能: 实现测试→推荐→学习→成长轨迹的业务闭环
-- =====================================================

-- 1. 用户表增加字段
USE youth_mental_health;

-- 增加推荐偏好字段
ALTER TABLE sys_user 
ADD COLUMN recommend_preference VARCHAR(50) DEFAULT NULL COMMENT '推荐偏好(疏导/预防/进阶)' AFTER health_status;

-- 增加用户积分字段
ALTER TABLE sys_user 
ADD COLUMN points INT DEFAULT 0 COMMENT '用户积分' AFTER recommend_preference;

-- 2. 用户学习记录表
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

-- 3. 用户推荐记录表
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

-- 4. 用户积分记录表
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

-- 5. 用户预警记录表
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

-- 6. 插入测试数据（可选）
-- 为现有用户初始化积分
UPDATE sys_user SET points = 0 WHERE points IS NULL;

-- 完成提示
SELECT '业务联动增强数据库变更完成！' AS message;
