package com.ti.mental.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 成长轨迹VO
 *
 * @author Ti
 * @date 2026-04-06
 */
@Data
@Schema(description = "成长轨迹VO")
public class GrowthTrajectoryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "测试成绩趋势")
    private List<TestTrendItem> testTrend;

    @Schema(description = "学习统计")
    private LearningStatsVO learningStats;

    @Schema(description = "健康状态历史")
    private List<HealthStatusItem> healthStatusHistory;

    @Data
    @Schema(description = "测试趋势项")
    public static class TestTrendItem implements Serializable {
        private static final long serialVersionUID = 1L;

        @Schema(description = "测试日期")
        private String testDate;

        @Schema(description = "测试标题")
        private String testTitle;

        @Schema(description = "得分")
        private Integer score;

        @Schema(description = "结果(0健康/1良好/2建议沟通)")
        private Integer result;
    }

    @Data
    @Schema(description = "健康状态项")
    public static class HealthStatusItem implements Serializable {
        private static final long serialVersionUID = 1L;

        @Schema(description = "日期")
        private String date;

        @Schema(description = "健康状态(0健康/1良好/2建议沟通)")
        private Integer healthStatus;

        @Schema(description = "状态文本")
        private String statusText;
    }
}
