package com.ti.mental.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 推荐VO
 *
 * @author Ti
 * @date 2026-04-06
 */
@Data
@Schema(description = "推荐VO")
public class RecommendVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "推荐的知识列表")
    private List<KnowledgeItem> knowledgeList;

    @Schema(description = "推荐的活动列表")
    private List<ActivityItem> activityList;

    @Schema(description = "是否建议AI咨询")
    private Boolean suggestAiChat;

    @Schema(description = "建议消息")
    private String suggestMessage;

    @Data
    @Schema(description = "知识推荐项")
    public static class KnowledgeItem implements Serializable {
        private static final long serialVersionUID = 1L;

        @Schema(description = "知识ID")
        private Long id;

        @Schema(description = "标题")
        private String title;

        @Schema(description = "封面")
        private String cover;

        @Schema(description = "摘要")
        private String summary;
    }

    @Data
    @Schema(description = "活动推荐项")
    public static class ActivityItem implements Serializable {
        private static final long serialVersionUID = 1L;

        @Schema(description = "活动ID")
        private Long id;

        @Schema(description = "标题")
        private String title;

        @Schema(description = "封面")
        private String cover;

        @Schema(description = "开始时间")
        private String startTime;
    }
}
