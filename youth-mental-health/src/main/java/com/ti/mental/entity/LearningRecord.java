package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户学习记录实体
 *
 * @author Ti
 * @date 2026-04-06
 */
@Data
@TableName("user_learning_record")
@Schema(description = "用户学习记录实体")
public class LearningRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "内容类型(1科普/2宣传/3资讯/4活动)")
    private Integer contentType;

    @Schema(description = "内容ID")
    private Long contentId;

    @Schema(description = "行为类型(1浏览/2收藏/3评论/4报名)")
    private Integer actionType;

    @Schema(description = "浏览时长(秒)")
    private Integer duration;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
