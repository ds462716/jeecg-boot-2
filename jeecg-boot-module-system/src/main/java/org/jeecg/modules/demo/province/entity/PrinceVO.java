package org.jeecg.modules.demo.province.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PrinceVO implements Serializable {
    String name;
    String code;
    List<CityVO> city;
}
