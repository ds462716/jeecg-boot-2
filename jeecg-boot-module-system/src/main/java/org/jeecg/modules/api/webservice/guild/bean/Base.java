package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

@Data
public class Base {
    private String address;//地址
    private String affiliatedEnterprises;//所属企业
    private String altitude;//海拔
    private String annualRainfall;//年降雨量
    private String averageTemperature;//年平均温度
    private String baseArea;//基地面积
    private String baseId;//基地id
    private String baseName;//基地名称
    private String basesContent;//基地介绍
    private String climateType;//气候类型
    private String climateZone;//气候带
    private String file;//附件,多张用竖线(|)隔开，不超10份
    private String image;//图片,多张用竖线(|)隔开，不超10张
    private String landType;//土地类型
    private String lastCrop;//上一茬作物
    private String latitude;//纬度
    private String longitude;//经度
    private String remarks;//备注信息
    private String soilPh;//土壤酸碱度
    private String soilType;//土壤类型
    private String terrain;//地形
    private String waterSource;//使用水源
    private String yearsAccumuTemp;//年积温

}
