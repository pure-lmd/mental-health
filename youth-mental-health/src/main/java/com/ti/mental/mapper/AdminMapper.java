package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ti.mental.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员Mapper接口
 *
 * @author Ti
 * @date 2026-02-03
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
}
