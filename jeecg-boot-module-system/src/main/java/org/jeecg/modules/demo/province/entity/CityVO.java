package org.jeecg.modules.demo.province.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CityVO implements Serializable {
    String name;
    String code;
    List<DistrictVO> area;

}
