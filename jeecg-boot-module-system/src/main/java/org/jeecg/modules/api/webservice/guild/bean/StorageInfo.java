package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

import java.util.List;

/**
 * 入库信息
 */
@Data
public class StorageInfo {
    private String batchCode;//出入库批次
    private String beginHumidity;//湿度开始
    private String beginTemperature;//温度开始
    private String endHumidity;//湿度结束
    private String endTemperature;//温度结束
    private String file;//附件,多张用竖线(|)隔开，不超10份
    private String image;//合同图片,多张用竖线(|)隔开，不超10张
    private String remarks;//备注信息
    private String responsibleUser;//负责人
    private List<StorageDetails> storageDetailsList;//出入库详情


    private String storageInfoId;//出入库信息id
    private long storageTime;//出入库时间
    private long storageType;//出入库类型  1入库2出库

}
