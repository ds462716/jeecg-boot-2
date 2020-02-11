package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

import java.util.List;

/**
 * 种植批次
 */
@Data
public class PlantingBatch {
    private Base base;

    private String cropArea;//种植面积
    private String custombatchCode;//自定义批次编号

    private List<FarmScheme> farmSchemeList;//农事

    private String file;//附件,多张用竖线(|)隔开，不超过10份
    private String germplasmPrimordium;//种质/基原
    private String image;//图片,多张用竖线(|)隔开，不超过10份
    private String outUrl;//引用外部链接
    private String placeProduction;//生产地
    private long plantEndTime;//种植结束时间
    private long plantSatartTime;//种植开始时间
    private long plantTime;//种植时间
    private String plantingBatchId;//种植批次id
    private String plantingStandard;//种植标准
    private String productId;//产品id
    private String purchaseBatchCode;//采购批次号
    private String qualityInspectionContent;//种子种苗质量确认
    private String quarantineResults;//检疫结果
    private String reproductiveSources;//繁殖材料来源
    private String responsibleUser;//负责人
    private String seedlingSupplier;//种苗供应商
    private String siteOfUse;//繁殖材料


}
