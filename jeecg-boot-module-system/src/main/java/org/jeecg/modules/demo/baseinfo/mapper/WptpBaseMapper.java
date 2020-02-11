package org.jeecg.modules.demo.baseinfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.demo.baseinfo.entity.WptpBase;
import org.springframework.data.repository.query.Param;

/**
 * @Description: 基地信息管理表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface WptpBaseMapper extends BaseMapper<WptpBase> {
    /**
     * 根据企业id，查询一个基地编码
     * @return
     */
    public String getBaseCode(@Param("entId") String entId);
}
