package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 通知公告实体
 *
 * @author Ti
 * @date 2026-02-03
 */
@Data
@TableName("sys_notice")
@Schema(description = "通知公告实体")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "公告标题")
    private String title;

    @Schema(description = "公告内容")
    private String content;

    @Schema(description = "类型(1通知/2公告)")
    private Integer type;

    @Schema(description = "状态(0禁用/1启用)")
    private Integer status;

    @Schema(description = "是否置顶(0否/1是)")
    private Integer isTop;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
