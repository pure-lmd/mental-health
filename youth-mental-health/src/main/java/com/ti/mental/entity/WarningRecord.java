package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户预警记录实体
 *
 * @author Ti
 * @date 2026-04-06
 */
@Data
@TableName("user_warning_record")
@Schema(description = "用户预警记录实体")
public class WarningRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "风险等级(HIGH/MEDIUM/LOW)")
    private String riskLevel;

    @Schema(description = "触发关键词")
    private String keywords;

    @Schema(description = "状态(0待处理/1已处理)")
    private Integer status;

    @Schema(description = "处理备注")
    private String handleRemark;

    @Schema(description = "处理人ID")
    private Long handlerId;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
