package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ti.mental.entity.TestRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 测试记录Mapper接口
 *
 * @author Ti
 * @date 2026-02-03
 */
@Mapper
public interface TestRecordMapper extends BaseMapper<TestRecord> {

    /**
     * 分页查询带用户和测试卷信息
     */
    @Select("<script>" +
            "SELECT r.*, t.title as test_title, u.nickname as user_nickname " +
            "FROM psy_test_record r " +
            "LEFT JOIN psy_test t ON r.test_id = t.id " +
            "LEFT JOIN sys_user u ON r.user_id = u.id " +
            "WHERE 1=1 " +
            "<if test='userId != null'> AND r.user_id = #{userId}</if>" +
            "<if test='testId != null'> AND r.test_id = #{testId}</if>" +
            "ORDER BY r.create_time DESC" +
            "</script>")
    IPage<TestRecord> selectPageWithInfo(Page<TestRecord> page,
                                         @Param("userId") Long userId,
                                         @Param("testId") Long testId);

    /**
     * 统计各结果人数
     */
    @Select("SELECT result, COUNT(DISTINCT user_id) as count FROM psy_test_record GROUP BY result")
    List<Map<String, Object>> countByResult();

    /**
     * 获取分数段统计
     */
    @Select("SELECT " +
            "CASE " +
            "  WHEN total_score >= 0 AND total_score < 20 THEN '0-20' " +
            "  WHEN total_score >= 20 AND total_score < 40 THEN '20-40' " +
            "  WHEN total_score >= 40 AND total_score < 60 THEN '40-60' " +
            "  WHEN total_score >= 60 AND total_score < 80 THEN '60-80' " +
            "  WHEN total_score >= 80 AND total_score <= 100 THEN '80-100' " +
            "END as scoreRange, " +
            "COUNT(*) as count " +
            "FROM psy_test_record " +
            "WHERE total_score IS NOT NULL " +
            "GROUP BY scoreRange " +
            "ORDER BY scoreRange")
    List<Map<String, Object>> getScoreRangeStats();
}
