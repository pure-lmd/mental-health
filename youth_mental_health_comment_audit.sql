-- 评论表结构升级 SQL
-- 执行时间：2026-03-29
-- 说明：添加评论审核功能相关字段

-- 修改状态字段注释（状态：0 待审核/1 已通过/2 已拒绝）
ALTER TABLE `psy_comment` 
MODIFY COLUMN `status` tinyint(4) NULL DEFAULT 0 COMMENT '状态 (0 待审核/1 已通过/2 已拒绝)';

-- 添加审核时间字段
ALTER TABLE `psy_comment` 
ADD COLUMN `audit_time` datetime NULL DEFAULT NULL COMMENT '审核时间' AFTER `status`;

-- 添加审核备注字段
ALTER TABLE `psy_comment` 
ADD COLUMN `audit_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核备注' AFTER `audit_time`;

-- 更新现有评论数据为已通过状态（兼容旧数据）
UPDATE `psy_comment` SET `status` = 1 WHERE `status` = 1;
