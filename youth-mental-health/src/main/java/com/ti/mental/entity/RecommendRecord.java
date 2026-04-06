package com.ti.mental.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户推荐记录实体
 *
 * @author Ti
 * @date 2026-04-06
 */
@Data
@TableName("user_recommend_record")
@Schema(description = "用户推荐记录实体")
public class RecommendRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "推荐类型(1科普/2活动/3AI咨询)")
    private Integer recommendType;

    @Schema(description = "内容ID")
    private Long contentId;

    @Schema(description = "推荐理由")
    private String reason;

    @Schema(description = "是否点击(0否/1是)")
    private Integer isClicked;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
