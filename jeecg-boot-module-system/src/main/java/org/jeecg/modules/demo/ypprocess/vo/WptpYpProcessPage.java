package org.jeecg.modules.demo.ypprocess.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcessFile;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @Description: 饮片加工表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Data
public class WptpYpProcessPage {

    /**
     * 主键
     */
    private java.lang.String id;
    /**
     * 创建人名称
     */
    private java.lang.String createName;
    /**
     * 创建人登录名称
     */
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date createDate;
    /**
     * 更新人名称
     */
    private java.lang.String updateName;
    /**
     * 更新人登录名称
     */
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date updateDate;
    /**
     * 主机代码
     */
    @Excel(name = "主机代码", width = 15)
    private java.lang.String hostCode;
    /**
     * 生产流水号
     */
    @Excel(name = "生产流水号", width = 15)
    private java.lang.String processNo;
    /**
     * 饮片品名
     */
    @Excel(name = "饮片品名", width = 15)
    private java.lang.String ypName;
    /**
     * 饮片规格
     */
    @Excel(name = "饮片规格", width = 15)
    private java.lang.String ypGuige;
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
     * 生产批号
     */
    @Excel(name = "生产批号", width = 15)
    private java.lang.String productBatch;
    /**
     * 原料来源
     */
    @Excel(name = "原料来源", width = 15)
    private java.lang.String source;
    /**
     * 原生产流水号
     */
    @Excel(name = "原生产流水号", width = 15)
    private java.lang.String oriProcessNo;
    /**
     * 药材入库流水号
     */
    @Excel(name = "药材入库流水号", width = 15)
    private java.lang.String instockNo;
    /**
     * 原料数量
     */
    @Excel(name = "原料数量", width = 15)
    private java.lang.Double materialNum;
    /**
     * 原料内部批号
     */
    @Excel(name = "原料内部批号", width = 15)
    private java.lang.String instockNumber;
    /**
     * 成品数量
     */
    @Excel(name = "成品数量", width = 15)
    private java.lang.Double productNum;
    /**
     * 单位
     */
    @Excel(name = "单位", width = 15)
    private java.lang.String unit;
    /**
     * 生产日期
     */
    @Excel(name = "生产日期", width = 15)
    private java.lang.String producedDate;
    /**
     * 有效日期
     */
    @Excel(name = "有效日期", width = 15)
    private java.lang.String expiredDate;
    /**
     * 加工地点
     */
    @Excel(name = "加工地点", width = 15)
    private java.lang.String processPlace;
    /**
     * 负责人
     */
    @Excel(name = "负责人", width = 15)
    private java.lang.String processResponsible;
    /**
     * 入库时间
     */
    @Excel(name = "入库时间", width = 15)
    private java.lang.String inTime;
    /**
     * 质检单号
     */
    @Excel(name = "质检单号", width = 15)
    private java.lang.String qualCheckNum;
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
     * 检测时间
     */
    @Excel(name = "检测时间", width = 15)
    private java.lang.String checkTime;
    /*
        @ExcelCollection(name="饮片加工附表")*/
    private List<WptpYpProcessFile> wptpYpProcessFileList;

}
