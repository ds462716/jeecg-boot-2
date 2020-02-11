package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

import java.util.List;

/**
 * 采收购信息
 */
@Data
public class PurchaseOrderInfo {
    private long arrivalTime;//到货时间
    private String batchCode;//采购批次号
    private String contactPhone;//联系电话
    private String contactUser;//联系人
    private String custombatchCode;//自定义批次
    private long distributionTime;//配送时间
    private String file;//附件
    private String image;//图片
    private String outUrl;//引用外部链接

    private List<PurchaseOrderDetails> purchaseOrderDetailsList;//采收(购)订单详情

    private String purchaseOrderId;//采收(购)订单id
    private String remarks;//备注信息
    private String sourceMoudel;//来源模块 1药材采购 2饮片原料药材采购 3成药原料采购 4商品采购
    private long  purchaseTime;//采购时间

}
