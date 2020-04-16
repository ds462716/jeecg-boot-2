package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

@Data
public class ProcessMaterial {
    private String processMaterialId;//加工原料id
    private String productBatchCode;//原料批次
    private String productInfoId;//原料id
    private double processNum;//投料数量
    private String remarks;//备注信息
    private long totalWeight;//总重量（KG）
    private String unit;
}
