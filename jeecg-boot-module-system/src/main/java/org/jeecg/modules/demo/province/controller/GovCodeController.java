package org.jeecg.modules.demo.province.controller;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.province.entity.*;
import org.jeecg.modules.demo.province.service.IWptpCityService;
import org.jeecg.modules.demo.province.service.IWptpDistrictService;
import org.jeecg.modules.demo.province.service.IWptpProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * 根据省市区获取行政编码
 *
 * @author laowang
 */
@RestController
@RequestMapping("/govCode")
public class GovCodeController {
    @Autowired
    private IWptpProvinceService wptpProvinceService;
    @Autowired
    private IWptpDistrictService wptpDistrictService;
    @Autowired
    private IWptpCityService wptpCityService;

    @PostMapping("/insertGovCode")
    public Result insertGovCode(@RequestBody List<PrinceVO> princes) throws IOException {
        //外层循环插入省
        for (int i = 0; i < princes.size(); i++) {
            WptpProvince wptpProvince = new WptpProvince();
            PrinceVO princeVO = princes.get(i);
            wptpProvince.setAdmiCode(princeVO.getCode());
            wptpProvince.setName(princeVO.getName());
            wptpProvinceService.save(wptpProvince);
            //第二层插入市
            for (int j = 0; j < princeVO.getCity().size(); j++) {
                WptpCity wptpCity = new WptpCity();
                CityVO cityVO = princeVO.getCity().get(j);
                wptpCity.setAdmiCode(cityVO.getCode());
                wptpCity.setCityName(cityVO.getName());
                wptpCity.setProvinceId(wptpProvince.getId());
                wptpCityService.save(wptpCity);
                //然后插入区和县
                for (int k = 0; k < cityVO.getArea().size(); k++) {
                    WptpDistrict wptpDistrict = new WptpDistrict();
                    DistrictVO districtVO = cityVO.getArea().get(k);
                    wptpDistrict.setAdmiCode(districtVO.getCode());
                    wptpDistrict.setDisName(districtVO.getName());
                    wptpDistrict.setCityId(wptpCity.getId());
                    wptpDistrictService.save(wptpDistrict);
                }
            }
        }
        System.out.println("成功");
        return new Result();
    }
}
