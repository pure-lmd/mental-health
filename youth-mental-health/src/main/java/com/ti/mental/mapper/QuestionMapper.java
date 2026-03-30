package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ti.mental.entity.Question;
import org.apache.ibatis.annotations.Mapper;

/**
 * 测试题Mapper接口
 *
 * @author Ti
 * @date 2026-02-03
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}
