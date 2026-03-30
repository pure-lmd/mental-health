package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 测试题实体
 *
 * @author Ti
 * @date 2026-02-03
 */
@Data
@TableName("psy_question")
@Schema(description = "测试题实体")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "测试卷ID")
    private Long testId;

    @Schema(description = "题目内容")
    private String title;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "选项列表")
    @TableField(exist = false)
    private List<Option> options;
}
