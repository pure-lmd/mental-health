package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ti.mental.entity.Knowledge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 心理科普Mapper接口
 *
 * @author Ti
 * @date 2026-02-03
 */
@Mapper
public interface KnowledgeMapper extends BaseMapper<Knowledge> {

    /**
     * 分页查询带分类名称
     */
    @Select("<script>" +
            "SELECT k.*, c.name as category_name FROM psy_knowledge k " +
            "LEFT JOIN psy_category c ON k.category_id = c.id " +
            "WHERE 1=1 " +
            "<if test='categoryId != null'> AND k.category_id = #{categoryId}</if>" +
            "<if test='title != null and title != \"\"'> AND k.title LIKE CONCAT('%', #{title}, '%')</if>" +
            "<if test='status != null'> AND k.status = #{status}</if>" +
            "ORDER BY k.sort ASC, k.create_time DESC" +
            "</script>")
    IPage<Knowledge> selectPageWithCategory(Page<Knowledge> page, 
                                            @Param("categoryId") Long categoryId, 
                                            @Param("title") String title,
                                            @Param("status") Integer status);
}
