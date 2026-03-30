package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论实体
 *
 * @author Ti
 * @date 2026-02-03
 */
@Data
@TableName("psy_comment")
@Schema(description = "评论实体")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键 ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "目标 ID")
    private Long targetId;

    @Schema(description = "类型 (1 科普/2 宣传/3 资讯)")
    private Integer type;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "父评论 ID(回复)")
    private Long parentId;

    @Schema(description = "状态 (0 待审核/1 已通过/2 已拒绝)")
    private Integer status;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核备注")
    private String auditRemark;

    @Schema(description = "评论时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "用户昵称")
    @TableField(exist = false)
    private String userNickname;

    @Schema(description = "用户头像")
    @TableField(exist = false)
    private String userAvatar;

    @Schema(description = "目标标题")
    @TableField(exist = false)
    private String targetTitle;

    @Schema(description = "子评论列表")
    @TableField(exist = false)
    private List<Comment> children;
}
