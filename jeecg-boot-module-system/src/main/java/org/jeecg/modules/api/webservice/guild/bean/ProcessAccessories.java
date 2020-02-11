package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

@Data
public class ProcessAccessories {
    private String accessoriesId;//加工辅料id
    private String manufacturer;//生产商
    private String productBatchCode;//辅料批次
    private String productInfoId;//辅料id
    private String remarks;//备注信息


}
