package org.jeecg.modules.demo.medicinebinstock.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstockFile;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @Description: 药材经营药材入库表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Data
public class WptpMedicineInstockPage {

    /**
     * 主键
     */
    private java.lang.String id;
    /**
     * 创建人
     */
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
    /**
     * 主机代码
     */
    @Excel(name = "主机代码", width = 15)
    private java.lang.String hostCode;
    /**
     * 药材入库流水号
     */
    @Excel(name = "药材入库流水号", width = 15)
    private java.lang.String instockNumber;
    /**
     * 入库单号
     */
    @Excel(name = "入库单号", width = 15)
    private java.lang.String instockNo;
    /**
     * 仓库
     */
    @Excel(name = "仓库", width = 15)
    private java.lang.String storeCode;
    /**
     * 原入库单号
     */
    @Excel(name = "原入库单号", width = 15)
    private java.lang.String oriInstockNo;
    /**
     * 原仓库
     */
    @Excel(name = "原仓库", width = 15)
    private java.lang.String oriStoreCode;
    /**
     * 来源追溯码
     */
    @Excel(name = "来源追溯码", width = 15)
    private java.lang.String traceCode;
    /**
     * 药材品名
     */
    @Excel(name = "药材品名", width = 15)
    private java.lang.String medicineName;
    /**
     * 药材规格
     */
    @Excel(name = "药材规格", width = 15)
    private java.lang.String guige;
    /**
     * 种类编码
     */
    @Excel(name = "种类编码", width = 15)
    private java.lang.String categoryCode;
    /**
     * 种类名称
     */
    @Excel(name = "种类名称", width = 15)
    private java.lang.String categoryName;
    /**
     * 主数据码
     */
    @Excel(name = "主数据码", width = 15)
    private java.lang.String mainCode;
    /**
     * 产品批号
     */
    @Excel(name = "产品批号", width = 15)
    private java.lang.String productBatch;
    /**
     * 批准文号
     */
    @Excel(name = "批准文号", width = 15)
    private java.lang.String approvalNumber;
    /**
     * 进口批件
     */
    @Excel(name = "进口批件", width = 15)
    private java.lang.String importApproval;
    /**
     * 到货时间
     */
    @Excel(name = "到货时间", width = 15)
    private java.lang.String arriveTime;
    /**
     * 采购数量
     */
    @Excel(name = "采购数量", width = 15)
    private java.lang.Double purchaseNum;
    /**
     * 药材产地
     */
    @Excel(name = "药材产地", width = 15)
    private java.lang.String medicinalOrigin;
    /**
     * 供应商编码
     */
    @Excel(name = "供应商编码", width = 15)
    private java.lang.String entId;
    /**
     * 采购负责人
     */
    @Excel(name = "采购负责人", width = 15)
    private java.lang.String purchaseLeader;
    /**
     * 质检单号
     */
    @Excel(name = "质检单号", width = 15)
    private java.lang.String checkNo;
    /**
     * 检品名称
     */
    @Excel(name = "检品名称", width = 15)
    private java.lang.String checkName;
    /**
     * 检验目的
     */
    @Excel(name = "检验目的", width = 15)
    private java.lang.String checkPurpose;
    /**
     * 检验项目及结论
     */
    @Excel(name = "检验项目及结论", width = 15)
    private java.lang.String checkItem;
    /**
     * 检测依据
     */
    @Excel(name = "检测依据", width = 15)
    private java.lang.String checkBasis;
    /**
     * 检测时间
     */
    @Excel(name = "检测时间", width = 15)
    private java.lang.String checkTime;
    /**
     * 检测结果
     */
    @Excel(name = "检测结果", width = 15)
    private java.lang.String checkResult;
    /**
     * 质检负责人
     */
    @Excel(name = "质检负责人", width = 15)
    private java.lang.String checkResponsible;
    /**
     * 储存环境
     */
    @Excel(name = "储存环境", width = 15)
    private java.lang.String enviornment;
    /**
     * 养护方法
     */
    @Excel(name = "养护方法", width = 15)
    private java.lang.String method;
    /**
     * 审核标志
     */
    private java.lang.String auditStatus;
    /**
     * 工作流
     */
    private java.lang.String flowId;
    /**
     * 删除标志
     */
    private java.lang.String deleted;
    /**
     * 入库明细序列号
     */
    @Excel(name = "入库明细序列号", width = 15)
    private java.lang.String instockNum;

    /*	@ExcelCollection(name="药材入库附表")*/
    private List<WptpMedicineInstockFile> wptpMedicineInstockFileList;

}
