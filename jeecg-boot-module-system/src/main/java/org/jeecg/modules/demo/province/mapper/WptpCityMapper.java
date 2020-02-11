package org.jeecg.modules.demo.province.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.demo.province.entity.WptpCity;

import java.util.List;

/**
 * @Description: wptp_city
 * @Author: jeecg-boot
 * @Date:   2019-10-09
 * @Version: V1.0
 */
public interface WptpCityMapper extends BaseMapper<WptpCity> {
    /**
     * 根据省份id和市名，模糊查询一个City对象
     * @param wptpCity
     * @return
     */
    public List<WptpCity> listByCityNameAndProvinceId(WptpCity wptpCity);
}
