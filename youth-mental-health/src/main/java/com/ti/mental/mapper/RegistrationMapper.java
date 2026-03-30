package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ti.mental.entity.Registration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 活动报名Mapper接口
 *
 * @author Ti
 * @date 2026-02-03
 */
@Mapper
public interface RegistrationMapper extends BaseMapper<Registration> {

    /**
     * 分页查询带用户和活动信息
     */
    @Select("<script>" +
            "SELECT r.*, u.nickname as user_nickname, u.phone as user_phone, a.title as activity_title " +
            "FROM psy_registration r " +
            "LEFT JOIN sys_user u ON r.user_id = u.id " +
            "LEFT JOIN psy_activity a ON r.activity_id = a.id " +
            "WHERE 1=1 " +
            "<if test='userId != null'> AND r.user_id = #{userId}</if>" +
            "<if test='activityId != null'> AND r.activity_id = #{activityId}</if>" +
            "<if test='status != null'> AND r.status = #{status}</if>" +
            "ORDER BY r.create_time DESC" +
            "</script>")
    IPage<Registration> selectPageWithInfo(Page<Registration> page,
                                           @Param("userId") Long userId,
                                           @Param("activityId") Long activityId,
                                           @Param("status") Integer status);
}
