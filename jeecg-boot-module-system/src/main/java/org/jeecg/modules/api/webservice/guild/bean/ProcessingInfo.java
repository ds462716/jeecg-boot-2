package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

import java.util.List;

@Data
public class ProcessingInfo {
    private String actualYieldRate;//实际出成率(%)
    private String baseName;//加工基地
    private String beforeBatchCode;//投料批号
    private String file;//附件,多张用竖线(|)隔开
    private String image;//图片,多张用竖线(|)隔开
    private String outUnitName;//产出单位
    private String outUrl;//引用外部链接

    private List<ProcessAccessories> processAccessoriesList;//加工辅料

    private String processBatchCode;//加工产品批号
    private long processEndTime;//加工结束时间
    private String processEquipment;//加工设备
    private String processId;//加工id

    private List<ProcessMaterial> processMaterialList;//加工原料


    private String processMedthod;//加工方法
    private String processName;//工艺名称

    private List<ProcessProcedure> processProcedureList;//加工工序

    private String processProductName;//加工产品名称
    private String productBatchCode;//加工批次
    private String remarks;//备注信息
    private String responsibleUser;//负责人
    private String sourceMoudel;//来源模块 1初加工过程 2饮片加工过程 3成药加工过程 4制剂加工过程
    private String standard;//标准
    private String unit;//单位(包2,箱1,个3,份4,斤21,公斤22,升23,毫升24,
    private long processStartTime;//加工开始时间
    private double totalWeight;//产出重量

    private long processNum;//投料数量


}
