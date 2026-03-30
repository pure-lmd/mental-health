package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 活动报名实体
 *
 * @author Ti
 * @date 2026-02-03
 */
@Data
@TableName("psy_registration")
@Schema(description = "活动报名实体")
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "活动ID")
    private Long activityId;

    @Schema(description = "状态(0待审核/1已通过/2已拒绝/3已取消)")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "审核时间")
    private LocalDateTime auditTime;

    @Schema(description = "审核备注")
    private String auditRemark;

    @Schema(description = "报名时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "用户昵称")
    @TableField(exist = false)
    private String userNickname;

    @Schema(description = "用户手机号")
    @TableField(exist = false)
    private String userPhone;

    @Schema(description = "活动标题")
    @TableField(exist = false)
    private String activityTitle;
}
