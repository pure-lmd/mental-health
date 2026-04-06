package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ti.mental.entity.RecommendRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户推荐记录Mapper
 *
 * @author Ti
 * @date 2026-04-06
 */
@Mapper
public interface RecommendRecordMapper extends BaseMapper<RecommendRecord> {
}
