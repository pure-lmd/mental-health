package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ti.mental.entity.PsyTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 测试卷Mapper接口
 *
 * @author Ti
 * @date 2026-02-03
 */
@Mapper
public interface PsyTestMapper extends BaseMapper<PsyTest> {

    /**
     * 查询测试卷及题目数量
     */
    @Select("SELECT t.*, (SELECT COUNT(*) FROM psy_question q WHERE q.test_id = t.id) as question_count " +
            "FROM psy_test t WHERE t.id = #{id}")
    PsyTest selectWithQuestionCount(Long id);
}
