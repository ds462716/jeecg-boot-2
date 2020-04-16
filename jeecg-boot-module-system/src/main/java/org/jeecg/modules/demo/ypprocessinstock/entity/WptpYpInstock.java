package org.jeecg.modules.demo.ypprocessinstock.entity;

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
 * @Description: 饮片加工-药材入库表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Data
@TableName("wptp_yp_instock")
@ApiModel(value = "饮片加工-药材入库实体类", description = "饮片加工-药材入库实体类")
public class WptpYpInstock implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * 创建人名称
     */
    @ApiModelProperty(value = "创建人名称")
    private String createName;
    /**
     * 创建人登录名称
     */
    @ApiModelProperty(value = "创建人登录名称")
    private String createBy;
    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date createDate;
    /**
     * 更新人名称
     */
    @ApiModelProperty(value = "更新人名称")
    private java.lang.String updateName;
    /**
     * 更新人登录名称
     */
    @ApiModelProperty(value = "更新人登录名称")
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date updateDate;
    /**
     * 药材入库流水号
     */
    @NotNull
    @ApiModelProperty(value = "药材入库流水号")
    private String instockNumber;
    /**
     * 入库单号
     */
    @NotNull
    @ApiModelProperty(value = "入库单号")
    private String instockNo;
    /**
     * 入库明细序列号
     */
    @NotNull
    @ApiModelProperty(value = "入库明细序列号")
    private String instockNum;
    /**
     * 仓库
     */
    @NotNull
    @ApiModelProperty(value = "仓库")
    private String storeCode;
    /**
     * 主机代码
     */
    @NotNull
    @ApiModelProperty(value = "主机代码")
    private String hostCode;
    /**
     * 来源环节
     */
    @NotNull
    @ApiModelProperty(value = "来源环节")
    private String sourceFlag;
    /**
     * 产品批号
     */
    @NotNull
    @ApiModelProperty(value = "产品批号")
    private String productBatch;
    /**
     * 种类编码
     */
    @NotNull
    @ApiModelProperty(value = "种类编码")
    private String categoryCode;
    /**
     * 种类名称
     */
    @NotNull
    @ApiModelProperty(value = "种类名称")
    private String categoryName;
    /**
     * 原料品名
     */
    @NotNull
    @ApiModelProperty(value = "原料品名")
    private String materialName;
    /**
     * 原料品规
     */
    @ApiModelProperty(value = "原料品规")
    private String guige;
    /**
     * 原料内部批次
     */
    @NotNull
    @ApiModelProperty(value = "原料内部批次")
    private String materialBatch;
    /**
     * 到货时间
     */
    @NotNull
    @ApiModelProperty(value = "到货时间")
    private String arrivalTime;
    /**
     * 采购数量
     */
    @NotNull
    @ApiModelProperty(value = "采购数量")
    private Double num;
    /**
     * 原料产地
     */
    @NotNull
    @ApiModelProperty(value = "原料产地")
    private String materialOrigin;
    /**
     * 供应商编码
     */
    @NotNull
    @ApiModelProperty(value = "供应商编码")
    private String entId;
    /**
     * 采购负责人
     */
    @NotNull
    @ApiModelProperty(value = "采购负责人")
    private String responsible;
    /**
     * 质检单号
     */
    @NotNull
    @ApiModelProperty(value = "质检单号")
    private String checkNo;
    /**
     * 检品名称
     */
    @NotNull
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
    @NotNull
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
    @NotNull
    private String checkResult;
    /**
     * 质检负责人
     */
    @ApiModelProperty(value = "质检负责人")
    @NotNull
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
     * 来源追溯码
     */
    @ApiModelProperty(value = "来源追溯码")
    private String traceCode;
    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志")
    private String deleted;
    /**
     * 主数据码
     */
    @ApiModelProperty(value = "主数据码")
    private String mainCode;
    /**
     * 生产厂商
     */
    @ApiModelProperty(value = "生产厂商")
    private String manufacturer;
    /**
     * 进口批件
     */
    @ApiModelProperty(value = "进口批件")
    private String importApproval;
    /**
     * 批准文号
     */
    @ApiModelProperty(value = "批准文号")
    private String approvalNumber;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private String unit;
}
