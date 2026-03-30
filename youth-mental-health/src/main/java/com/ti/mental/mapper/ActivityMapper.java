package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ti.mental.entity.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 活动Mapper接口
 *
 * @author Ti
 * @date 2026-02-03
 */
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {

    /**
     * 获取活动报名统计
     *
     * @return 活动报名统计列表
     */
    @Select("SELECT a.title, COUNT(r.id) as registrationCount " +
            "FROM psy_activity a " +
            "LEFT JOIN psy_registration r ON a.id = r.activity_id AND r.status NOT IN (2, 3) " +
            "WHERE a.status > 0 " +
            "GROUP BY a.id, a.title " +
            "ORDER BY registrationCount DESC " +
            "LIMIT 10")
    List<Map<String, Object>> getActivityRegistrationStats();
}
