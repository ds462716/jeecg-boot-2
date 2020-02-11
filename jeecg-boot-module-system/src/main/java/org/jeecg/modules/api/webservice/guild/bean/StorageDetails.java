package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

/**
 * 入库详情
 */
@Data
public class StorageDetails {
    private String boxNumber;//箱号
    private String productBatchCode;//产品批次
    private String productInfoName;//产品名称
    private double productNum;//出入库数量
    private String storageDetailsId;//出入库详情
    private String unit;//数量单位(包2,箱1,个3,份4,斤21,公斤22,升23,毫升24,克25)

}
