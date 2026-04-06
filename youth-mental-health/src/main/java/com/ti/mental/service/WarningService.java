package com.ti.mental.service;

import java.util.List;
import java.util.Map;

/**
 * 预警服务接口
 *
 * @author Ti
 * @date 2026-04-06
 */
public interface WarningService {

    /**
     * 获取高风险用户预警列表
     *
     * @return 预警用户列表
     */
    List<Map<String, Object>> getWarningUsers();

    /**
     * 创建预警记录
     *
     * @param userId    用户ID
     * @param riskLevel 风险等级
     * @param keywords  触发关键词
     */
    void createWarning(Long userId, String riskLevel, String keywords);

    /**
     * 处理预警
     *
     * @param warningId   预警ID
     * @param handlerId   处理人ID
     * @param handleRemark 处理备注
     */
    void handleWarning(Long warningId, Long handlerId, String handleRemark);
}
