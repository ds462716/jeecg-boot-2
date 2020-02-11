package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

@Data
public class OrderShipment {
    private double deliveryNumber;//发货数量
    private long deliveryTime;//发货时间
    private String flowEnterName;//流向单位
    private String invoiceNumCode;//发货单号
    private String orderContract;//订单合同
    private String orderShipmentId;//订单发货id
    private String packSpec;//包装规格
    private String productId;//产品id
    private String remarks;//备注信息
    private String tranPerson;//运输责任人
    private String unit;//单位(包2,箱1,个3,份4,斤21,公斤22,升23,毫升24,克25)
    private String productBatch;//产品批号
    private String sourceMoudel;//3药材订单发货4药材流通订单发货5饮片订单发货6饮片流通订单发货

}
