package org.jeecg.modules.demo.medicinebinstock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 药材经营药材入库表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Data
@TableName("wptp_medicine_instock")
@ApiModel(value = "药材经营-药材入库实体类", description = "药材经营-药材入库实体类")
public class WptpMedicineInstock implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
    /**
     * 主机代码
     */
    @ApiModelProperty(value = "主机代码")
    @NotNull
    private String hostCode;
    /**
     * 药材入库流水号
     */
    @ApiModelProperty(value = "药材入库流水号")
    @NotNull
    private String instockNumber;
    /**
     * 入库单号
     */
    @ApiModelProperty(value = "入库单号")
    @NotNull
    private String instockNo;
    /**
     * 仓库
     */
    @ApiModelProperty(value = "仓库")
    private String storeCode;
    /**
     * 原入库单号
     */
    @ApiModelProperty(value = "原入库单号")
    private String oriInstockNo;
    /**
     * 原仓库
     */
    @ApiModelProperty(value = "原仓库")
    private String oriStoreCode;
    /**
     * 来源追溯码
     */
    @ApiModelProperty(value = "来源追溯码")
    @NotNull
    private String traceCode;
    /**
     * 药材品名
     */
    @ApiModelProperty(value = "药材品名")
    @NotNull
    private String medicineName;
    /**
     * 药材规格
     */
    @ApiModelProperty(value = "药材规格")
    private String guige;
    /**
     * 种类编码
     */
    @ApiModelProperty(value = "种类编码")
    @NotNull
    private String categoryCode;
    /**
     * 种类名称
     */
    @ApiModelProperty(value = "种类名称")
    private String categoryName;
    /**
     * 主数据码
     */
    @ApiModelProperty(value = "主数据码")
    private String mainCode;
    /**
     * 产品批号
     */
    @ApiModelProperty(value = "产品批号")
    @NotNull
    private String productBatch;
    /**
     * 批准文号
     */
    @ApiModelProperty(value = "批准文号")
    private String approvalNumber;
    /**
     * 进口批件
     */
    @ApiModelProperty(value = "进口批件")
    private String importApproval;
    /**
     * 到货时间
     */
    @ApiModelProperty(value = "到货时间")
    @NotNull
    private String arriveTime;
    /**
     * 采购数量
     */
    @ApiModelProperty(value = "采购数量")
    @NotNull
    private Double purchaseNum;
    /**
     * 药材产地
     */
    @ApiModelProperty(value = "药材产地")
    @NotNull
    private String medicinalOrigin;
    /**
     * 供应商编码
     */
    @ApiModelProperty(value = "供应商编码")
    @NotNull
    private String entId;
    /**
     * 采购负责人
     */
    @ApiModelProperty(value = "采购负责人")
    @NotNull
    private String purchaseLeader;
    /**
     * 质检单号
     */
    @ApiModelProperty(value = "质检单号")
    private String checkNo;
    /**
     * 检品名称
     */
    @ApiModelProperty(value = "检品名称")
    private String checkName;
    /**
     * 检验目的
     */
    @ApiModelProperty(value = "检验目的")
    private String checkPurpose;
    /**
     * 检验项目及结论
     */
    @ApiModelProperty(value = "检验项目及结论")
    private String checkItem;
    /**
     * 检测依据
     */
    @ApiModelProperty(value = "检测依据")
    @NotNull
    private String checkBasis;
    /**
     * 检测时间
     */
    @ApiModelProperty(value = "检测时间")
    @NotNull
    private String checkTime;
    /**
     * 检测结果
     */
    @ApiModelProperty(value = "检测结果")
    private String checkResult;
    /**
     * 质检负责人
     */
    @ApiModelProperty(value = "质检负责人")
    private String checkResponsible;
    /**
     * 储存环境
     */
    @ApiModelProperty(value = "储存环境")
    @NotNull
    private String enviornment;
    /**
     * 养护方法
     */
    @ApiModelProperty(value = "养护方法")
    @NotNull
    private String method;
    /**
     * 审核标志
     */
    @ApiModelProperty(value = "审核标志")
    private String auditStatus;
    /**
     * 工作流
     */
    @ApiModelProperty(value = "工作流")
    private String flowId;
    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志")
    private String deleted;
    /**
     * 入库明细序列号
     */
    @ApiModelProperty(value = "入库明细序列号")
    @NotNull
    private String instockNum;
    /**
     * 生产厂商
     */
    @ApiModelProperty(value = "生产厂商")
    private String manufacturer;
    /*	*//**协会追溯码*//*
	@ApiModelProperty(value = "协会追溯码")
	private String xhTraceCode;*/
    /**
     * 来源批次号
     */
    @ApiModelProperty(value = "来源批次号")
    private String sourceBatch;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    @NotNull
    private String unit;

}
