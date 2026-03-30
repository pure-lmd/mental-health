package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ti.mental.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * 心理分类Mapper接口
 *
 * @author Ti
 * @date 2026-02-03
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
