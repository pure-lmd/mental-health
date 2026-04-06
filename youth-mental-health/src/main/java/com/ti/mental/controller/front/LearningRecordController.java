package com.ti.mental.controller.front;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.result.Result;
import com.ti.mental.entity.LearningRecord;
import com.ti.mental.service.LearningRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

/**
 * 用户学习记录Controller
 *
 * @author Ti
 * @date 2026-04-06
 */
@Tag(name = "用户学习记录")
@RestController
@RequestMapping("/api/front/user/learning")
public class LearningRecordController {

    @Resource
    private LearningRecordService learningRecordService;

    @Operation(summary = "获取我的学习记录")
    @GetMapping("/records")
    public Result<IPage<LearningRecord>> getRecords(
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer contentType) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        IPage<LearningRecord> records = learningRecordService.getUserRecords(userId, pageNum, pageSize, contentType);
        return Result.success(records);
    }

    @Operation(summary = "记录学习行为")
    @PostMapping("/record")
    public Result<Void> recordLearning(
            HttpServletRequest request,
            @RequestBody LearningRecord record) {
        Long userId = (Long) request.getAttribute(Constants.CURRENT_USER_ID);
        learningRecordService.record(
                userId,
                record.getContentType(),
                record.getContentId(),
                record.getActionType(),
                record.getDuration()
        );
        return Result.success();
    }
}
