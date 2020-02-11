package org.jeecg.modules.demo.entinfo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.springframework.data.repository.query.Param;

/**
 * @Description: 企业基本信息
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface WptpEntInfoMapper extends BaseMapper<WptpEntInfo> {
    /**
     * 根据区（县）的编码，查询一个企 业 编 码
     * @param districtCode
     * @return
     */
    public String getEntId(@Param("districtCode") String districtCode);
}
