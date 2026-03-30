package com.ti.mental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ti.mental.entity.AiChat;
import org.apache.ibatis.annotations.Mapper;

/**
 * AI对话记录Mapper接口
 *
 * @author Ti
 * @date 2026-02-03
 */
@Mapper
public interface AiChatMapper extends BaseMapper<AiChat> {
}
