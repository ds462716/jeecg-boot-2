package org.jeecg.modules.demo.province.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.demo.province.entity.WptpProvince;
import org.springframework.data.repository.query.Param;

/**
 * @Description: wptp_province
 * @Author: jeecg-boot
 * @Date: 2019-10-09
 * @Version: V1.0
 */
public interface WptpProvinceMapper extends BaseMapper<WptpProvince> {
    /**
     * 根据省份名，模糊查询一个Province对象
     *
     * @param name
     * @return
     */
    public WptpProvince getByProvinceName(@Param("name") String name);
}
