package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户积分记录实体
 *
 * @author Ti
 * @date 2026-04-06
 */
@Data
@TableName("user_points_record")
@Schema(description = "用户积分记录实体")
public class PointsRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "积分(正为增加,负为减少)")
    private Integer points;

    @Schema(description = "来源类型(1测试/2学习/3活动/4签到)")
    private Integer sourceType;

    @Schema(description = "来源ID")
    private Long sourceId;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
