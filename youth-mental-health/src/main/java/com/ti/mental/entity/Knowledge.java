package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 心理科普实体
 *
 * @author Ti
 * @date 2026-02-03
 */
@Data
@TableName("psy_knowledge")
@Schema(description = "心理科普实体")
public class Knowledge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "封面图")
    private String cover;

    @Schema(description = "摘要")
    private String summary;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "浏览量")
    private Integer viewCount;

    @Schema(description = "收藏量")
    private Integer collectCount;

    @Schema(description = "状态(0下架/1上架)")
    private Integer status;

    @Schema(description = "是否热门(0否/1是)")
    private Integer isHot;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "分类名称")
    @TableField(exist = false)
    private String categoryName;
}
