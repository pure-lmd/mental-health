package com.ti.mental.controller.front;

import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.result.Result;
import com.ti.mental.dto.RecommendVO;
import com.ti.mental.service.RecommendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * 推荐Controller（用户端）
 *
 * @author Ti
 * @date 2026-04-06
 */
@Tag(name = "推荐服务")
@RestController
@RequestMapping("/api/front/recommend")
public class RecommendController {

    @Resource
    private RecommendService recommendService;

    @Operation(summary = "根据健康状态获取推荐内容")
    @GetMapping("/by-health-status")
    public Result<RecommendVO> getByHealthStatus(HttpServletRequest request, @RequestParam Integer healthStatus) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        RecommendVO recommend = recommendService.generateRecommend(userId, healthStatus);
        return Result.success(recommend);
    }
}
