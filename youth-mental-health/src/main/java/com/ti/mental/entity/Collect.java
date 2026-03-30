package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 收藏实体
 *
 * @author Ti
 * @date 2026-02-03
 */
@Data
@TableName("psy_collect")
@Schema(description = "收藏实体")
public class Collect implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "目标ID")
    private Long targetId;

    @Schema(description = "类型(1科普/2宣传/3资讯)")
    private Integer type;

    @Schema(description = "收藏时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "用户昵称")
    @TableField(exist = false)
    private String userNickname;

    @Schema(description = "目标标题")
    @TableField(exist = false)
    private String targetTitle;

    @Schema(description = "目标封面")
    @TableField(exist = false)
    private String targetCover;
}
