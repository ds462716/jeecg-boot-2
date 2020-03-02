package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

@Data
public class PurchaseOrderDetails {
    private String batchCode;//产品批次号
    private String manufacturingEnterprise;//生产企业
    private String medicinalOrigin;//药材产地
    private String packingSpecification;//包装规格
    private String produceBatchCode;//产出批号
    private String productInfoId;//产品id
    private double productNum;//数量
    private String providerInfoName;//供应商名称
    private String purchaseOrderDetailsId;//采收(购)订单详情id
    private String remarks;//备注信息
    private long totalPrice;//单价
    private String unit;//单位（包2,箱1,个3,份4,斤21,公斤22,升23,毫升24,克25）
    private String rawGauge;//原料品规

}
