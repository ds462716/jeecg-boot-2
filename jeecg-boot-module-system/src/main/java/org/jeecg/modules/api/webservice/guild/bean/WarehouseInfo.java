package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

import java.util.List;

/**
 * 仓库信息
 */
@Data
public class WarehouseInfo {

    private String address;//地址
    private String beginHumidity;//仓库湿度开始
    private String beginTemperature;//仓库温度开始
    private String curingDescribe;//养护描述
    private long areaNumber;//实际可用面积
    private List<Curing> curingList;//仓库养护信息

    private String endHumidity;//仓库湿度结束
    private String endTemperature;//仓库温度结束
    private String facilityCode;//设施编号
    private String image;//图片
    private String inventoryType;//库存类型（必填）药材产区库存3001002001，饮片原料库存3001002002，饮片成品库存3001002003，成药库存3001002004 ,
    private String remarks;//备注信息

    private String warehouseId;//仓库id
    private String warehouseName;//仓库名称
    private String warehouseType;//仓库类型

    private List<StorageInfo> storageInfoList;//出入库信息


}
