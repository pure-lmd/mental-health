package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ti.mental.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 评论Mapper接口
 *
 * @author Ti
 * @date 2026-02-03
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 分页查询评论带用户信息
     */
    @Select("<script>" +
            "SELECT c.*, u.nickname as user_nickname, u.avatar as user_avatar " +
            "FROM psy_comment c " +
            "LEFT JOIN sys_user u ON c.user_id = u.id " +
            "WHERE c.parent_id = 0 " +
            "<if test='targetId != null'> AND c.target_id = #{targetId}</if>" +
            "<if test='type != null'> AND c.type = #{type}</if>" +
            "<if test='status != null'> AND c.status = #{status}</if>" +
            "ORDER BY c.create_time DESC" +
            "</script>")
    IPage<Comment> selectPageWithUser(Page<Comment> page,
                                      @Param("targetId") Long targetId,
                                      @Param("type") Integer type,
                                      @Param("status") Integer status);

    /**
     * 查询子评论
     */
    @Select("SELECT c.*, u.nickname as user_nickname, u.avatar as user_avatar " +
            "FROM psy_comment c " +
            "LEFT JOIN sys_user u ON c.user_id = u.id " +
            "WHERE c.parent_id = #{parentId} AND c.status = 1 " +
            "ORDER BY c.create_time ASC")
    List<Comment> selectChildren(@Param("parentId") Long parentId);

    /**
     * 管理端分页查询所有评论
     */
    @Select("<script>" +
            "SELECT c.*, u.nickname as user_nickname, u.avatar as user_avatar " +
            "FROM psy_comment c " +
            "LEFT JOIN sys_user u ON c.user_id = u.id " +
            "WHERE 1=1 " +
            "<if test='type != null'> AND c.type = #{type}</if>" +
            "<if test='status != null'> AND c.status = #{status}</if>" +
            "ORDER BY c.create_time DESC" +
            "</script>")
    IPage<Comment> selectAdminPage(Page<Comment> page,
                                   @Param("type") Integer type,
                                   @Param("status") Integer status);
}
