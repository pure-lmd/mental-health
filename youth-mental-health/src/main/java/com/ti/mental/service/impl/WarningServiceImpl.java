package com.ti.mental.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.entity.User;
import com.ti.mental.entity.WarningRecord;
import com.ti.mental.mapper.UserMapper;
import com.ti.mental.mapper.WarningRecordMapper;
import com.ti.mental.service.WarningService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 预警服务实现
 *
 * @author Ti
 * @date 2026-04-06
 */
@Service
public class WarningServiceImpl implements WarningService {

    @Resource
    private WarningRecordMapper warningRecordMapper;

    @Resource
    private UserMapper userMapper;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public List<Map<String, Object>> getWarningUsers() {
        // 查询所有待处理的预警记录
        LambdaQueryWrapper<WarningRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WarningRecord::getStatus, 0)
                .orderByDesc(WarningRecord::getCreateTime);

        List<WarningRecord> warnings = warningRecordMapper.selectList(wrapper);

        if (CollUtil.isEmpty(warnings)) {
            return new ArrayList<>();
        }

        // 获取用户信息
        List<Long> userIds = warnings.stream()
                .map(WarningRecord::getUserId)
                .distinct()
                .collect(Collectors.toList());

        List<User> users = userMapper.selectBatchIds(userIds);
        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, u -> u));

        // 组装数据
        return warnings.stream().map(warning -> {
            Map<String, Object> map = new HashMap<>();
            map.put("warningId", warning.getId());
            map.put("userId", warning.getUserId());

            User user = userMap.get(warning.getUserId());
            if (user != null) {
                map.put("username", user.getUsername());
                map.put("nickname", user.getNickname());
                map.put("avatar", user.getAvatar());
                map.put("healthStatus", user.getHealthStatus());
            }

            map.put("riskLevel", warning.getRiskLevel());
            map.put("keywords", warning.getKeywords());
            map.put("createTime", warning.getCreateTime().format(DATE_FORMATTER));
            map.put("status", warning.getStatus());

            return map;
        }).collect(Collectors.toList());
    }

    @Override
    public void createWarning(Long userId, String riskLevel, String keywords) {
        WarningRecord record = new WarningRecord();
        record.setUserId(userId);
        record.setRiskLevel(riskLevel);
        record.setKeywords(keywords);
        record.setStatus(0); // 待处理
        warningRecordMapper.insert(record);
    }

    @Override
    public void handleWarning(Long warningId, Long handlerId, String handleRemark) {
        WarningRecord record = warningRecordMapper.selectById(warningId);
        if (record != null) {
            record.setStatus(1); // 已处理
            record.setHandlerId(handlerId);
            record.setHandleRemark(handleRemark);
            warningRecordMapper.updateById(record);
        }
    }
}
