package com.ti.mental.common.constant;

/**
 * 系统常量
 *
 * @author Ti
 * @date 2026-02-03
 */
public class Constants {

    private Constants() {}

    /**
     * Token请求头
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * 当前用户ID
     */
    public static final String CURRENT_USER_ID = "currentUserId";

    /**
     * 当前用户角色
     */
    public static final String CURRENT_USER_ROLE = "currentUserRole";

    /**
     * 角色：管理员
     */
    public static final String ROLE_ADMIN = "ADMIN";

    /**
     * 角色：用户
     */
    public static final String ROLE_USER = "USER";

    /**
     * 状态：启用
     */
    public static final Integer STATUS_ENABLE = 1;

    /**
     * 状态：禁用
     */
    public static final Integer STATUS_DISABLE = 0;

    /**
     * 健康状态：健康
     */
    public static final Integer HEALTH_STATUS_HEALTHY = 0;

    /**
     * 健康状态：良好
     */
    public static final Integer HEALTH_STATUS_GOOD = 1;

    /**
     * 健康状态：建议沟通
     */
    public static final Integer HEALTH_STATUS_NEED_TALK = 2;

    /**
     * 收藏类型：科普
     */
    public static final Integer COLLECT_TYPE_KNOWLEDGE = 1;

    /**
     * 收藏类型：宣传
     */
    public static final Integer COLLECT_TYPE_PUBLICITY = 2;

    /**
     * 收藏类型：资讯
     */
    public static final Integer COLLECT_TYPE_NEWS = 3;

    /**
     * 报名状态：待审核
     */
    public static final Integer REGISTRATION_STATUS_PENDING = 0;

    /**
     * 报名状态：已通过
     */
    public static final Integer REGISTRATION_STATUS_APPROVED = 1;

    /**
     * 报名状态：已拒绝
     */
    public static final Integer REGISTRATION_STATUS_REJECTED = 2;

    /**
     * 报名状态：已取消
     */
    public static final Integer REGISTRATION_STATUS_CANCELLED = 3;

    /**
     * 反馈状态：待回复
     */
    public static final Integer FEEDBACK_STATUS_PENDING = 0;

    /**
     * 反馈状态：已回复
     */
    public static final Integer FEEDBACK_STATUS_REPLIED = 1;

    /**
     * 评论状态：待审核
     */
    public static final Integer COMMENT_STATUS_PENDING = 0;

    /**
     * 评论状态：已通过
     */
    public static final Integer COMMENT_STATUS_APPROVED = 1;

    /**
     * 评论状态：已拒绝
     */
    public static final Integer COMMENT_STATUS_REJECTED = 2;
}
