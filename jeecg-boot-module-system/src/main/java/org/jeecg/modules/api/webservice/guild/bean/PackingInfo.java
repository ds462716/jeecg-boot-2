package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

@Data
public class PackingInfo {
    private String environmental;
    private String file;
    private String image;
    private String outUrl;
    private String packingBatchCode;
    private long packingEndTime;
    private String packingId;
    private String packingMaterial;
    private String packingProductBatchCode;
    private String productId;
    private String remarks;
    private String responsibleUser;
    private String sourceMoudel;
    private String unitStandard;
    private long totalWeight;
    private long packingStartTime;
    private double packingNum;
    private String unit;
}
