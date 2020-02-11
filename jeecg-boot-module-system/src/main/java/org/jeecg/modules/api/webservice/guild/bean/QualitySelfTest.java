package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

@Data
public class QualitySelfTest {
    private String detectionBasis;//检测依据
    private String detectionProductBatchCode;//检测产品批次
    private String detectionType;//检测类型
    private String file;//附件
    private String image;//图片
    private String operationName;//质量放行人
    private String outUrl;//引用外部链接
    private String productInfoId;//产品id
    private String purpose;//目的
    private String qualitySelfTestId;//检测记录id
    private String recordName;//记录人
    private String remarks;//备注信息
    private String reviewName;//复核人
    private String sourceMoudel;//来源模块 1饮片原料质检 2饮片成品质检 3成药质检 ,
    private String testSummary;//检测结果
    private long checkDate;//检查日期
    private long presentationDate;//报告日期

}
