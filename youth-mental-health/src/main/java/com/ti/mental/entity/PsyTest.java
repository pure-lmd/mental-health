package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 测试卷实体
 *
 * @author Ti
 * @date 2026-02-03
 */
@Data
@TableName("psy_test")
@Schema(description = "测试卷实体")
public class PsyTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "测试标题")
    private String title;

    @Schema(description = "测试描述")
    private String description;

    @Schema(description = "封面图")
    private String cover;

    @Schema(description = "总分")
    private Integer totalScore;

    @Schema(description = "健康阈值(低于此分数为健康)")
    private Integer healthThreshold;

    @Schema(description = "良好阈值(高于等于此分数为建议沟通)")
    private Integer goodThreshold;

    @Schema(description = "状态(0下架/1上架)")
    private Integer status;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "题目列表")
    @TableField(exist = false)
    private List<Question> questions;

    @Schema(description = "题目数量")
    @TableField(exist = false)
    private Integer questionCount;
}
