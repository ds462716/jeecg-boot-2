package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

@Data
public class HarvestBatch {
    private String batchCode;//采收批号
    private String customBatchCode;//自定义批次
    private String file;//附件
    private String harvestBatchId;//采收批次id
    private String harvestPosition;//采收部位
    private String harvestProductName;//采收产品名称
    private String harvestStandard;//采收标准
    private String harvestType;//采收方式
    private String harvestUtensils;//采收器皿
    private long endOperateTime;//采收结束时间
    private String harvestVolume;//采收量（KG）
    private String image;//图片
    private String operateName;//负责人
    private String outUrl;//引用外部链接
    private String remarks;//备注信息
    private long startOperateTime;//采收开始时间
    private String responsibleUser;//负责人
    private long plantTime;//种植时间
    private long plantSatartTime;//种植开始时间
    private long plantEndTime;//种植结束时间
    private String harvestUnitName;//采收单位
}
