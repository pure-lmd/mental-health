package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ti.mental.entity.Collect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 收藏Mapper接口
 *
 * @author Ti
 * @date 2026-02-03
 */
@Mapper
public interface CollectMapper extends BaseMapper<Collect> {

    /**
     * 分页查询用户收藏（科普）
     */
    @Select("<script>" +
            "SELECT c.*, u.nickname as user_nickname, k.title as target_title, k.cover as target_cover " +
            "FROM psy_collect c " +
            "LEFT JOIN sys_user u ON c.user_id = u.id " +
            "LEFT JOIN psy_knowledge k ON c.target_id = k.id AND c.type = 1 " +
            "WHERE c.type = 1 " +
            "<if test='userId != null'> AND c.user_id = #{userId}</if>" +
            "ORDER BY c.create_time DESC" +
            "</script>")
    IPage<Collect> selectKnowledgeCollectPage(Page<Collect> page, @Param("userId") Long userId);

    /**
     * 分页查询用户收藏（宣传）
     */
    @Select("<script>" +
            "SELECT c.*, u.nickname as user_nickname, p.title as target_title, p.cover as target_cover " +
            "FROM psy_collect c " +
            "LEFT JOIN sys_user u ON c.user_id = u.id " +
            "LEFT JOIN psy_publicity p ON c.target_id = p.id AND c.type = 2 " +
            "WHERE c.type = 2 " +
            "<if test='userId != null'> AND c.user_id = #{userId}</if>" +
            "ORDER BY c.create_time DESC" +
            "</script>")
    IPage<Collect> selectPublicityCollectPage(Page<Collect> page, @Param("userId") Long userId);

    /**
     * 分页查询用户收藏（资讯）
     */
    @Select("<script>" +
            "SELECT c.*, u.nickname as user_nickname, n.title as target_title, n.cover as target_cover " +
            "FROM psy_collect c " +
            "LEFT JOIN sys_user u ON c.user_id = u.id " +
            "LEFT JOIN psy_news n ON c.target_id = n.id AND c.type = 3 " +
            "WHERE c.type = 3 " +
            "<if test='userId != null'> AND c.user_id = #{userId}</if>" +
            "ORDER BY c.create_time DESC" +
            "</script>")
    IPage<Collect> selectNewsCollectPage(Page<Collect> page, @Param("userId") Long userId);

    /**
     * 分页查询所有收藏
     */
    @Select("<script>" +
            "SELECT c.*, u.nickname as user_nickname, " +
            "CASE c.type " +
            "  WHEN 1 THEN (SELECT title FROM psy_knowledge WHERE id = c.target_id) " +
            "  WHEN 2 THEN (SELECT title FROM psy_publicity WHERE id = c.target_id) " +
            "  WHEN 3 THEN (SELECT title FROM psy_news WHERE id = c.target_id) " +
            "END as target_title, " +
            "CASE c.type " +
            "  WHEN 1 THEN (SELECT cover FROM psy_knowledge WHERE id = c.target_id) " +
            "  WHEN 2 THEN (SELECT cover FROM psy_publicity WHERE id = c.target_id) " +
            "  WHEN 3 THEN (SELECT cover FROM psy_news WHERE id = c.target_id) " +
            "END as target_cover " +
            "FROM psy_collect c " +
            "LEFT JOIN sys_user u ON c.user_id = u.id " +
            "WHERE 1=1 " +
            "<if test='userId != null'> AND c.user_id = #{userId}</if>" +
            "<if test='type != null'> AND c.type = #{type}</if>" +
            "ORDER BY c.create_time DESC" +
            "</script>")
    IPage<Collect> selectPageWithUser(Page<Collect> page, 
                                      @Param("userId") Long userId,
                                      @Param("type") Integer type);
}
