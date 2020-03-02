package org.jeecg.modules.demo.province.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.province.entity.WptpProvince;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: wptp_province
 * @Author: jeecg-boot
 * @Date: 2019-10-09
 * @Version: V1.0
 */
public interface IWptpProvinceService extends IService<WptpProvince> {
    /**
     * 根据省市区名。查询一个区的行政编码
     */
    Result getDistrictCode(String provinceName, String cityName, String DistrictName);
}
