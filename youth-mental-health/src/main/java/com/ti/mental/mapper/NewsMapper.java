package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ti.mental.entity.News;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资讯Mapper接口
 *
 * @author Ti
 * @date 2026-02-03
 */
@Mapper
public interface NewsMapper extends BaseMapper<News> {
}
