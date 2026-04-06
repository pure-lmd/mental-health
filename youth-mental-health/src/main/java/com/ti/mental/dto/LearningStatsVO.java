package com.ti.mental.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 学习统计VO
 *
 * @author Ti
 * @date 2026-04-06
 */
@Data
@Schema(description = "学习统计VO")
public class LearningStatsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "科普浏览数")
    private Integer knowledgeViewCount = 0;

    @Schema(description = "科普收藏数")
    private Integer knowledgeCollectCount = 0;

    @Schema(description = "活动参与数")
    private Integer activityJoinCount = 0;

    @Schema(description = "AI咨询次数")
    private Integer aiChatCount = 0;

    @Schema(description = "总学习时长(秒)")
    private Integer totalDuration = 0;

    @Schema(description = "当前积分")
    private Integer currentPoints = 0;

    @Schema(description = "累计获得积分")
    private Integer totalEarnedPoints = 0;
}
