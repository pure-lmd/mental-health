package com.ti.mental.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.dto.RecommendVO;
import com.ti.mental.entity.Activity;
import com.ti.mental.entity.Knowledge;
import com.ti.mental.entity.RecommendRecord;
import com.ti.mental.mapper.ActivityMapper;
import com.ti.mental.mapper.KnowledgeMapper;
import com.ti.mental.mapper.RecommendRecordMapper;
import com.ti.mental.service.RecommendService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 推荐服务实现 - 基于健康状态的精准推荐
 *
 * @author Ti
 * @date 2026-04-06
 */
@Slf4j
@Service
public class RecommendServiceImpl implements RecommendService {

    @Resource
    private KnowledgeMapper knowledgeMapper;

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private RecommendRecordMapper recommendRecordMapper;

    // 高风险用户推荐关键词
    private static final List<String> HIGH_RISK_KNOWLEDGE_KEYWORDS = Arrays.asList(
            "抑郁", "情绪", "压力", "焦虑", "疏导", "放松", "调节", "心理"
    );
    
    private static final List<String> HIGH_RISK_ACTIVITY_KEYWORDS = Arrays.asList(
            "冥想", "放松", "减压", "瑜伽", "音乐", "艺术", "团辅"
    );

    // 中风险用户推荐关键词
    private static final List<String> MEDIUM_RISK_KNOWLEDGE_KEYWORDS = Arrays.asList(
            "健康", "预防", "调节", "情绪", "压力", "心理", "成长"
    );
    
    private static final List<String> MEDIUM_RISK_ACTIVITY_KEYWORDS = Arrays.asList(
            "讲座", "沙龙", "分享", "团辅", "户外", "运动"
    );

    // 低风险用户推荐关键词
    private static final List<String> LOW_RISK_KNOWLEDGE_KEYWORDS = Arrays.asList(
            "成长", "提升", "积极", "阳光", "心理", "健康"
    );
    
    private static final List<String> LOW_RISK_ACTIVITY_KEYWORDS = Arrays.asList(
            "分享", "志愿", "户外", "运动", "社团", "交流"
    );

    @Override
    public RecommendVO generateRecommend(Long userId, Integer healthStatus) {
        RecommendVO recommend = new RecommendVO();

        if (Constants.HEALTH_STATUS_NEED_TALK.equals(healthStatus)) {
            // 建议沟通 - 高风险
            log.info("为高风险用户 [userId={}] 生成推荐", userId);
            recommend.setKnowledgeList(getKnowledgeByKeywords(HIGH_RISK_KNOWLEDGE_KEYWORDS, 5));
            recommend.setActivityList(getActivityByKeywords(HIGH_RISK_ACTIVITY_KEYWORDS, 3));
            recommend.setSuggestAiChat(true);
            recommend.setSuggestMessage("⚠️ 检测到您最近心理状态需要关注，建议与AI心理咨询师聊聊或阅读心理疏导文章");
        } else if (Constants.HEALTH_STATUS_GOOD.equals(healthStatus)) {
            // 良好 - 中风险
            log.info("为中风险用户 [userId={}] 生成推荐", userId);
            recommend.setKnowledgeList(getKnowledgeByKeywords(MEDIUM_RISK_KNOWLEDGE_KEYWORDS, 5));
            recommend.setActivityList(getActivityByKeywords(MEDIUM_RISK_ACTIVITY_KEYWORDS, 3));
            recommend.setSuggestAiChat(false);
        } else if (Constants.HEALTH_STATUS_HEALTHY.equals(healthStatus)) {
            // 健康 - 低风险
            log.info("为低风险用户 [userId={}] 生成推荐", userId);
            recommend.setKnowledgeList(getKnowledgeByKeywords(LOW_RISK_KNOWLEDGE_KEYWORDS, 5));
            recommend.setActivityList(getActivityByKeywords(LOW_RISK_ACTIVITY_KEYWORDS, 3));
            recommend.setSuggestAiChat(false);
        } else {
            // 默认推荐热门内容
            recommend.setKnowledgeList(getHotKnowledge(5));
            recommend.setActivityList(getHotActivity(3));
            recommend.setSuggestAiChat(false);
        }

        // 记录推荐
        saveRecommendRecord(userId, recommend);

        return recommend;
    }

    @Override
    public void recordClick(Long userId, Integer recommendType, Long contentId) {
        RecommendRecord record = new RecommendRecord();
        record.setUserId(userId);
        record.setRecommendType(recommendType);
        record.setContentId(contentId);
        record.setIsClicked(1);
        recommendRecordMapper.insert(record);
    }

    /**
     * 根据关键词列表获取知识（匹配标题或摘要中的任意关键词）
     */
    private List<RecommendVO.KnowledgeItem> getKnowledgeByKeywords(List<String> keywords, int limit) {
        LambdaQueryWrapper<Knowledge> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Knowledge::getStatus, Constants.STATUS_ENABLE);
        
        // 构建OR条件：匹配任意关键词
        wrapper.and(w -> {
            for (int i = 0; i < keywords.size(); i++) {
                String keyword = keywords.get(i);
                if (i == 0) {
                    w.like(Knowledge::getTitle, keyword)
                     .or()
                     .like(Knowledge::getSummary, keyword);
                } else {
                    w.or().like(Knowledge::getTitle, keyword)
                     .or()
                     .like(Knowledge::getSummary, keyword);
                }
            }
        });
        
        wrapper.orderByDesc(Knowledge::getViewCount)
               .last("LIMIT " + limit);

        List<Knowledge> list = knowledgeMapper.selectList(wrapper);
        
        if (CollUtil.isEmpty(list)) {
            log.warn("根据关键词 {} 未找到知识，返回热门知识", keywords);
            return getHotKnowledge(limit);
        }

        log.info("根据关键词 {} 找到 {} 条知识", keywords, list.size());
        return list.stream().map(k -> {
            RecommendVO.KnowledgeItem item = new RecommendVO.KnowledgeItem();
            item.setId(k.getId());
            item.setTitle(k.getTitle());
            item.setCover(k.getCover());
            item.setSummary(k.getSummary());
            return item;
        }).collect(Collectors.toList());
    }

    /**
     * 获取热门知识
     */
    private List<RecommendVO.KnowledgeItem> getHotKnowledge(int limit) {
        LambdaQueryWrapper<Knowledge> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Knowledge::getStatus, Constants.STATUS_ENABLE)
                .orderByDesc(Knowledge::getViewCount)
                .last("LIMIT " + limit);

        List<Knowledge> list = knowledgeMapper.selectList(wrapper);
        return list.stream().map(k -> {
            RecommendVO.KnowledgeItem item = new RecommendVO.KnowledgeItem();
            item.setId(k.getId());
            item.setTitle(k.getTitle());
            item.setCover(k.getCover());
            item.setSummary(k.getSummary());
            return item;
        }).collect(Collectors.toList());
    }

    /**
     * 根据关键词列表获取活动（匹配标题或描述中的任意关键词）
     */
    private List<RecommendVO.ActivityItem> getActivityByKeywords(List<String> keywords, int limit) {
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Activity::getStatus, 1); // 报名中
        
        // 构建OR条件：匹配任意关键词
        wrapper.and(w -> {
            for (int i = 0; i < keywords.size(); i++) {
                String keyword = keywords.get(i);
                if (i == 0) {
                    w.like(Activity::getTitle, keyword)
                     .or()
                     .like(Activity::getDescription, keyword);
                } else {
                    w.or().like(Activity::getTitle, keyword)
                     .or()
                     .like(Activity::getDescription, keyword);
                }
            }
        });
        
        wrapper.orderByDesc(Activity::getCurrentNum)
               .last("LIMIT " + limit);

        List<Activity> list = activityMapper.selectList(wrapper);
        
        if (CollUtil.isEmpty(list)) {
            log.warn("根据关键词 {} 未找到活动，返回热门活动", keywords);
            return getHotActivity(limit);
        }

        log.info("根据关键词 {} 找到 {} 条活动", keywords, list.size());
        return list.stream().map(a -> {
            RecommendVO.ActivityItem item = new RecommendVO.ActivityItem();
            item.setId(a.getId());
            item.setTitle(a.getTitle());
            item.setCover(a.getCover());
            item.setStartTime(a.getStartTime() != null ? a.getStartTime().toString() : "");
            return item;
        }).collect(Collectors.toList());
    }

    /**
     * 获取热门活动
     */
    private List<RecommendVO.ActivityItem> getHotActivity(int limit) {
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Activity::getStatus, 1)
                .orderByDesc(Activity::getCurrentNum)
                .last("LIMIT " + limit);

        List<Activity> list = activityMapper.selectList(wrapper);
        return list.stream().map(a -> {
            RecommendVO.ActivityItem item = new RecommendVO.ActivityItem();
            item.setId(a.getId());
            item.setTitle(a.getTitle());
            item.setCover(a.getCover());
            item.setStartTime(a.getStartTime() != null ? a.getStartTime().toString() : "");
            return item;
        }).collect(Collectors.toList());
    }

    /**
     * 保存推荐记录
     */
    private void saveRecommendRecord(Long userId, RecommendVO recommend) {
        List<RecommendRecord> records = new ArrayList<>();

        // 记录知识推荐
        if (CollUtil.isNotEmpty(recommend.getKnowledgeList())) {
            for (RecommendVO.KnowledgeItem item : recommend.getKnowledgeList()) {
                RecommendRecord record = new RecommendRecord();
                record.setUserId(userId);
                record.setRecommendType(1); // 科普
                record.setContentId(item.getId());
                record.setReason("基于测试结果推荐");
                record.setIsClicked(0);
                records.add(record);
            }
        }

        // 记录活动推荐
        if (CollUtil.isNotEmpty(recommend.getActivityList())) {
            for (RecommendVO.ActivityItem item : recommend.getActivityList()) {
                RecommendRecord record = new RecommendRecord();
                record.setUserId(userId);
                record.setRecommendType(2); // 活动
                record.setContentId(item.getId());
                record.setReason("基于测试结果推荐");
                record.setIsClicked(0);
                records.add(record);
            }
        }

        // 批量插入
        if (CollUtil.isNotEmpty(records)) {
            records.forEach(recommendRecordMapper::insert);
        }
    }
}
