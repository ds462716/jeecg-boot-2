package org.jeecg.modules.api.webservice;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据下发，采收批次
 */
@Data
public class DataDisseminationCsInfo implements Serializable {
    private String csNo;//采收批次号
    private String guige;//规格
    private String weight;//重量
    private String unit;//单位
    private String baseName;//基地名称
    private String medicinalCode;//药材品种编码
    private String entId;//所属企业
    private String blockCode;//地块编号
    private String flag;//标志0:自有基地；1：供应商；

}
