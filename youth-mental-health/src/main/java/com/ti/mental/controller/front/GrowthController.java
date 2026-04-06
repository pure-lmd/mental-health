package com.ti.mental.controller.front;

import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.result.Result;
import com.ti.mental.dto.GrowthTrajectoryVO;
import com.ti.mental.dto.LearningStatsVO;
import com.ti.mental.service.GrowthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户成长轨迹Controller
 *
 * @author Ti
 * @date 2026-04-06
 */
@Tag(name = "用户成长轨迹")
@RestController
@RequestMapping("/api/front/user/growth")
public class GrowthController {

    @Resource
    private GrowthService growthService;

    @Operation(summary = "获取成长轨迹")
    @GetMapping("/trajectory")
    public Result<GrowthTrajectoryVO> getTrajectory(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        GrowthTrajectoryVO trajectory = growthService.getTrajectory(userId);
        return Result.success(trajectory);
    }

    @Operation(summary = "获取学习统计")
    @GetMapping("/statistics")
    public Result<LearningStatsVO> getStatistics(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        LearningStatsVO stats = growthService.getLearningStats(userId);
        return Result.success(stats);
    }
}
