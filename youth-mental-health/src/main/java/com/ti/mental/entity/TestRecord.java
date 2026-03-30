package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 测试记录实体
 *
 * @author Ti
 * @date 2026-02-03
 */
@Data
@TableName("psy_test_record")
@Schema(description = "测试记录实体")
public class TestRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "测试卷ID")
    private Long testId;

    @Schema(description = "得分")
    private Integer totalScore;

    @Schema(description = "结果(0健康/1良好/2建议沟通)")
    private Integer result;

    @Schema(description = "答题详情(JSON)")
    private String answerDetail;

    @Schema(description = "测试时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "测试卷标题")
    @TableField(exist = false)
    private String testTitle;

    @Schema(description = "用户昵称")
    @TableField(exist = false)
    private String userNickname;
}
