package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ti.mental.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 反馈Mapper接口
 *
 * @author Ti
 * @date 2026-02-03
 */
@Mapper
public interface FeedbackMapper extends BaseMapper<Feedback> {

    /**
     * 分页查询带用户信息
     */
    @Select("<script>" +
            "SELECT f.*, u.nickname as user_nickname " +
            "FROM psy_feedback f " +
            "LEFT JOIN sys_user u ON f.user_id = u.id " +
            "WHERE 1=1 " +
            "<if test='userId != null'> AND f.user_id = #{userId}</if>" +
            "<if test='status != null'> AND f.status = #{status}</if>" +
            "ORDER BY f.create_time DESC" +
            "</script>")
    IPage<Feedback> selectPageWithUser(Page<Feedback> page,
                                       @Param("userId") Long userId,
                                       @Param("status") Integer status);
}
