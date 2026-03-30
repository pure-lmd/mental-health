package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 测试选项实体
 *
 * @author Ti
 * @date 2026-02-03
 */
@Data
@TableName("psy_option")
@Schema(description = "测试选项实体")
public class Option implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "题目ID")
    private Long questionId;

    @Schema(description = "选项标签(A/B/C/D)")
    private String label;

    @Schema(description = "选项内容")
    private String content;

    @Schema(description = "选项分值")
    private Integer score;

    @Schema(description = "排序")
    private Integer sort;
}
