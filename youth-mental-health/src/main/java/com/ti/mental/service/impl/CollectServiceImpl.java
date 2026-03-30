package com.ti.mental.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ti.mental.common.constant.Constants;
import com.ti.mental.common.exception.BusinessException;
import com.ti.mental.common.result.ResultCode;
import com.ti.mental.entity.Collect;
import com.ti.mental.mapper.CollectMapper;
import com.ti.mental.service.CollectService;
import com.ti.mental.service.KnowledgeService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 收藏Service实现类
 *
 * @author Ti
 * @date 2026-02-03
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Resource
    private KnowledgeService knowledgeService;

    @Override
    public IPage<Collect> page(Integer pageNum, Integer pageSize, Long userId, Integer type) {
        Page<Collect> page = new Page<>(pageNum, pageSize);
        return baseMapper.selectPageWithUser(page, userId, type);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Long userId, Long targetId, Integer type) {
        // 检查是否已收藏
        if (hasCollected(userId, targetId, type)) {
            throw new BusinessException(ResultCode.ALREADY_COLLECTED);
        }
        // 创建收藏记录
        Collect collect = new Collect();
        collect.setUserId(userId);
        collect.setTargetId(targetId);
        collect.setType(type);
        this.save(collect);
        // 如果是科普，增加收藏量
        if (Constants.COLLECT_TYPE_KNOWLEDGE.equals(type)) {
            knowledgeService.incrCollectCount(targetId, 1);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long userId, Long targetId, Integer type) {
        this.remove(new LambdaQueryWrapper<Collect>()
                .eq(Collect::getUserId, userId)
                .eq(Collect::getTargetId, targetId)
                .eq(Collect::getType, type));
        // 如果是科普，减少收藏量
        if (Constants.COLLECT_TYPE_KNOWLEDGE.equals(type)) {
            knowledgeService.incrCollectCount(targetId, -1);
        }
    }

    @Override
    public boolean hasCollected(Long userId, Long targetId, Integer type) {
        return this.lambdaQuery()
                .eq(Collect::getUserId, userId)
                .eq(Collect::getTargetId, targetId)
                .eq(Collect::getType, type)
                .count() > 0;
    }

    @Override
    public IPage<Collect> listByUserId(Long userId, Integer type, Integer pageNum, Integer pageSize) {
        Page<Collect> page = new Page<>(pageNum, pageSize);
        if (type == null) {
            return baseMapper.selectPageWithUser(page, userId, null);
        }
        switch (type) {
            case 1:
                return baseMapper.selectKnowledgeCollectPage(page, userId);
            case 2:
                return baseMapper.selectPublicityCollectPage(page, userId);
            case 3:
                return baseMapper.selectNewsCollectPage(page, userId);
            default:
                return baseMapper.selectPageWithUser(page, userId, type);
        }
    }
}
