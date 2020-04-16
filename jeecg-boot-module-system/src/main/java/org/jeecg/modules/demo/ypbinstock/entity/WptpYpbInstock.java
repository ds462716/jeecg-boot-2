package org.jeecg.modules.demo.ypbinstock.entity;

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
 * @Description: 饮片经营药材入库
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Data
@TableName("wptp_ypb_instock")
@ApiModel(value = "饮片经营-饮片入库实体类", description = "饮片经营-饮片入库实体类")
public class WptpYpbInstock implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
    /**
     * 创建人名称
     */
    @ApiModelProperty(value = "创建人名称")
    private java.lang.String createName;
    /**
     * 创建人登录名称
     */
    @ApiModelProperty(value = "创建人登录名称")
    private java.lang.String createBy;
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
     * 饮片入库流水号
     */
    @ApiModelProperty(value = "饮片入库流水号")
    private java.lang.String instockNumber;
    /**
     * 饮片入库单号
     */
    @ApiModelProperty(value = "饮片入库单号")
    private java.lang.String instockNo;
    /**
     * 饮片入库序列号
     */
    @ApiModelProperty(value = "饮片入库序列号")
    private java.lang.String instockNum;
    /**
     * 种类编码
     */
    @ApiModelProperty(value = "种类编码")
    @NotNull
    private java.lang.String categoryCode;
    /**
     * 种类名称
     */
    @ApiModelProperty(value = "种类名称")
    @NotNull
    private java.lang.String categoryName;
    /**
     * 主数据码
     */
    @ApiModelProperty(value = "主数据码")
    private java.lang.String mainCode;
    /**
     * 饮片名称
     */
    @ApiModelProperty(value = "饮片名称")
    @NotNull
    private java.lang.String ypName;
    /**
     * 饮片规格
     */
    @ApiModelProperty(value = "饮片规格")
    private java.lang.String guige;
    /**
     * 包装规格
     */
    @ApiModelProperty(value = "包装规格")
    private java.lang.String packGuige;
    /**
     * 来源追溯码
     */
    @ApiModelProperty(value = "来源追溯码")
    @NotNull
    private java.lang.String traceCode;
    /**
     * 来源环节
     */
    @ApiModelProperty(value = "来源环节")
    @NotNull
    private java.lang.String sourceFlag;
    /**
     * 产品批号
     */
    @ApiModelProperty(value = "产品批号")
    @NotNull
    private java.lang.String productBatch;
    /**
     * 到货时间
     */
    @ApiModelProperty(value = "到货时间")
    @NotNull
    private java.lang.String arrivalTime;
    /**
     * 采购数量
     */
    @ApiModelProperty(value = "采购数量")
    @NotNull
    private java.lang.Double num;
    /**
     * 生产日期
     */
    @ApiModelProperty(value = "生产日期")
    @NotNull
    private java.lang.String producedDate;
    /**
     * 有效日期
     */
    @ApiModelProperty(value = "有效日期")
    private java.lang.String expiredDate;
    /**
     * 药材产地
     */
    @ApiModelProperty(value = "药材产地")
    @NotNull
    private java.lang.String medicinalOrigin;
    /**
     * 生产厂商
     */
    @ApiModelProperty(value = "生产厂商")
    @NotNull
    private java.lang.String manufacturer;
    /**
     * 供应商企业编码
     */
    @ApiModelProperty(value = "供应商企业编码")
    @NotNull
    private java.lang.String entId;
    /**
     * 采购负责人
     */
    @ApiModelProperty(value = "采购负责人")
    @NotNull
    private java.lang.String purchaseResponsible;
    /**
     * 检验依据
     */
    @ApiModelProperty(value = "检验依据")
    private java.lang.String checkBasis;
    /**
     * 储存环境
     */
    @ApiModelProperty(value = "储存环境")
    private java.lang.String enviornment;
    /**
     * 养护方法
     */
    @ApiModelProperty(value = "养护方法")
    private java.lang.String method;
    /**
     * 审核标志
     */
    @ApiModelProperty(value = "审核标志")
    private java.lang.String auditStatus;
    /**
     * 工作流
     */
    @ApiModelProperty(value = "工作流")
    private java.lang.String flowId;
    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志")
    private java.lang.String deleted;
    /**
     * 主机代码
     */
    @ApiModelProperty(value = "主机代码")
    @NotNull
    private java.lang.String hostCode;
    /**
     * 仓库
     */
    @ApiModelProperty(value = "仓库")
    @NotNull
    private java.lang.String storeCode;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    @NotNull
    private java.lang.String unit;
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
     * 饮片code
     */
    @ApiModelProperty(value = "饮片code")
    private String ypCode;
}
