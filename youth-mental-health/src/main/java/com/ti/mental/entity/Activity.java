package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 活动实体
 *
 * @author Ti
 * @date 2026-02-03
 */
@Data
@TableName("psy_activity")
@Schema(description = "活动实体")
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "活动标题")
    private String title;

    @Schema(description = "封面图")
    private String cover;

    @Schema(description = "活动描述")
    private String description;

    @Schema(description = "活动详情")
    private String content;

    @Schema(description = "活动地址")
    private String address;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "最大报名人数")
    private Integer maxNum;

    @Schema(description = "当前报名人数")
    private Integer currentNum;

    @Schema(description = "状态(0下架/1报名中/2进行中/3已结束)")
    private Integer status;

    @Schema(description = "是否热门(0否/1是)")
    private Integer isHot;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
