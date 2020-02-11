package org.jeecg.modules.demo.province.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.province.entity.WptpCity;
import org.jeecg.modules.demo.province.entity.WptpDistrict;
import org.jeecg.modules.demo.province.entity.WptpProvince;
import org.jeecg.modules.demo.province.mapper.WptpCityMapper;
import org.jeecg.modules.demo.province.mapper.WptpDistrictMapper;
import org.jeecg.modules.demo.province.mapper.WptpProvinceMapper;
import org.jeecg.modules.demo.province.service.IWptpProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Description: wptp_province
 * @Author: jeecg-boot
 * @Date:   2019-10-09
 * @Version: V1.0
 */
@Service
public class WptpProvinceServiceImpl extends ServiceImpl<WptpProvinceMapper, WptpProvince> implements IWptpProvinceService {

    @Autowired
    private WptpDistrictMapper wptpDistrictMapper;
    @Autowired
    private WptpCityMapper wptpCityMapper;

    @Override
    public Result getDistrictCode(String provinceName, String cityName, String DistrictName) {
        QueryWrapper<WptpProvince> wptpProvinceQueryWrapper = new QueryWrapper<>();
        wptpProvinceQueryWrapper.like("name",provinceName);
        List<WptpProvince> wptpProvinces = getBaseMapper().selectList(wptpProvinceQueryWrapper);
        if (wptpProvinces.isEmpty())return new Result(false, "未查到相关省份信息", 500, new Date().getTime());
        QueryWrapper<WptpCity> wptpCityQueryWrapper = new QueryWrapper<>();
        wptpCityQueryWrapper.like("city_name",cityName);
        wptpCityQueryWrapper.like("province_id",wptpProvinces.get(0).getId());
        WptpCity wptpCity = new WptpCity();
        wptpCity.setProvinceId(wptpProvinces.get(0).getId());
        wptpCity.setCityName(cityName);
        List<WptpCity> wptpCities = wptpCityMapper.selectList(wptpCityQueryWrapper);
        if (wptpCities.isEmpty())return new Result(false, "未查到相关市信息", 500, new Date().getTime());
        QueryWrapper<WptpDistrict> wptpDistrictQueryWrapper = new QueryWrapper<>();
        wptpDistrictQueryWrapper.like("dis_name",DistrictName);
        wptpDistrictQueryWrapper.like("city_id",wptpCities.get(0).getId());
        WptpDistrict wptpDistrict = new WptpDistrict();
        wptpDistrict.setCityId(wptpCities.get(0).getId());
        wptpDistrict.setDisName(DistrictName);
        List<WptpDistrict> wptpDistricts = wptpDistrictMapper.selectList(wptpDistrictQueryWrapper);
        if (wptpDistricts.isEmpty())return new Result(false, "未查到相关地区（县）信息", 500, new Date().getTime());
        String districtCode = wptpDistricts.get(0).getAdmiCode();
        ArrayList<String> res = new ArrayList<>();
        res.add(wptpProvinces.get(0).getAdmiCode());
        res.add(wptpCities.get(0).getAdmiCode());
        res.add(wptpDistricts.get(0).getAdmiCode());
        return new Result(true, "操作成功", 200,districtCode,res, new Date().getTime());
    }
}
