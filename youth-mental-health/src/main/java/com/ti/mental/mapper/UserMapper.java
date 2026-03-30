package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ti.mental.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * 用户Mapper接口
 *
 * @author Ti
 * @date 2026-02-03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 统计各健康状态人数
     */
    @Select("SELECT health_status, COUNT(*) as count FROM sys_user WHERE status = 1 GROUP BY health_status")
    java.util.List<Map<String, Object>> countByHealthStatus();
}
