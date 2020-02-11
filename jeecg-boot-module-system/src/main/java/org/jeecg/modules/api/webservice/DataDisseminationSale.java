package org.jeecg.modules.api.webservice;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 数据下发4个大环节包含的销售小环节，统一返回的销售类
 */
@Data
public class DataDisseminationSale implements Serializable {
    private String traceCode;//来源追溯码
    private String medicineName;//药材/饮片品名
    private String guige;//药材/饮片品名
    private String categoryCode;//种类编码
    private String categoryName;//主数据码
    private String mainCode;//主数据码
    private String productBatch;//出库时间
    private String outTime;//出库时间
    private double num;//数量
    private String medicinalOrigin;//药材产地
    private String manufacturer;//生产厂商
    private String entId;//企业ID
    private List<String> report;//上药检测报告   Source=04取种植端初加工报告；  Source=23取饮片加工环节的检测报告


}
