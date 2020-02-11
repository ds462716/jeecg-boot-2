package org.jeecg.modules.demo.province.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.demo.province.entity.WptpDistrict;

import java.util.List;

/**
 * @Description: wptp_district
 * @Author: jeecg-boot
 * @Date:   2019-10-09
 * @Version: V1.0
 */
public interface WptpDistrictMapper extends BaseMapper<WptpDistrict> {
    /**
     * 根据市d和区（县）名，模糊查询一个District对象
     * @param wptpDistrict
     * @return
     */
    public List<WptpDistrict> listByDisNameAndCityId(WptpDistrict wptpDistrict);
}
