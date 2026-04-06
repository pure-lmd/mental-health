package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ti.mental.entity.LearningRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户学习记录Mapper
 *
 * @author Ti
 * @date 2026-04-06
 */
@Mapper
public interface LearningRecordMapper extends BaseMapper<LearningRecord> {
}
