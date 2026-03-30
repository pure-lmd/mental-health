package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 反馈实体
 *
 * @author Ti
 * @date 2026-02-03
 */
@Data
@TableName("psy_feedback")
@Schema(description = "反馈实体")
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "反馈标题")
    private String title;

    @Schema(description = "反馈内容")
    private String content;

    @Schema(description = "图片(JSON数组)")
    private String images;

    @Schema(description = "状态(0待回复/1已回复)")
    private Integer status;

    @Schema(description = "回复内容")
    private String reply;

    @Schema(description = "回复时间")
    private LocalDateTime replyTime;

    @Schema(description = "反馈时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "用户昵称")
    @TableField(exist = false)
    private String userNickname;
}
