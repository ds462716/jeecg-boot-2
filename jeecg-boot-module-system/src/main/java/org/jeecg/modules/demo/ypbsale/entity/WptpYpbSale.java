package org.jeecg.modules.demo.ypbsale.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 饮片经营销售
 * @Author: jeecg-boot
 * @Date: 2019-10-09
 * @Version: V1.0
 */
@Data
@TableName("wptp_ypb_sale")
@ApiModel(value = "饮片经营-饮片销售实体类", description = "饮片经营-饮片销售实体类")
public class WptpYpbSale implements Serializable {
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
    private String updateName;
    /**
     * 更新人登录名称
     */
    @ApiModelProperty(value = "更新人登录名称")
    private String updateBy;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date updateDate;

    /**
     * 追溯码
     */
    @ApiModelProperty(value = "追溯码")
    @NotNull
    @Excel(name = "追溯码", width = 15)
    private String traceCode;
    /**
     * 协会追溯码
     */
    @ApiModelProperty(value = "协会追溯码")
    @Excel(name = "协会追溯码", width = 15)
    private String xhTraceCode;
    /**
     * 种类编码
     */
    @ApiModelProperty(value = "种类编码")
    @NotNull
    @Excel(name = "种类编码", width = 15)
    private String categoryCode;
    /**
     * 种类名称
     */
    @ApiModelProperty(value = "种类名称")
    @NotNull
    @Excel(name = "种类名称", width = 15)
    private String categoryName;
    /**
     * 饮片名称
     */
    @ApiModelProperty(value = "饮片名称")
    @NotNull
    @Excel(name = "饮片名称", width = 15)
    private String ypName;
    /**
     * 饮片规格
     */
    @ApiModelProperty(value = "饮片规格")
    @Excel(name = "饮片规格", width = 15)
    private String ypGuige;
    /**
     * 饮片入库流水号
     */
    @ApiModelProperty(value = "饮片入库流水号")
    @NotNull
    @Excel(name = "饮片入库流水号", width = 15)
    private String instockNo;
    /**
     * 销售时间
     */
    @ApiModelProperty(value = "销售时间")
    @NotNull
    @Excel(name = "销售时间", width = 15)
    private String saleTime;
    /**
     * 销售数量
     */
    @ApiModelProperty(value = "销售数量")
    @NotNull
    @Excel(name = "销售数量", width = 15)
    private Double num;
    /**
     * 出库时间
     */
    @ApiModelProperty(value = "出库时间")
    @NotNull
    @Excel(name = "出库时间", width = 15)
    private String outTime;
    /**
     * 产品批号
     */
    @ApiModelProperty(value = "产品批号")
    @Excel(name = "产品批号", width = 15)
    @NotNull
    private String productBatch;
    /**
     * 收货地址
     */
    @ApiModelProperty(value = "收货地址")
    @NotNull
    @Excel(name = "收货地址", width = 15)
    private String address;
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
     * 主机代码
     */
    @ApiModelProperty(value = "主机代码")
    @NotNull
    @Excel(name = "主机代码", width = 15)
    private String hostCode;
    /**
     * 客户
     */
    @ApiModelProperty(value = "客户")
    @Excel(name = "客户", width = 15)
    private String entId;
    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    @Excel(name = "联系人", width = 15)
    private String contact;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Excel(name = "联系电话", width = 15)
    private String telephone;
    /**
     * 运输责任人
     */
    @ApiModelProperty(value = "运输责任人")
    @Excel(name = "运输责任人", width = 15)
    private String responsible;
    /**
     * 饮片销售流水号
     */
    @ApiModelProperty(value = "饮片销售流水号")
    @Excel(name = "饮片销售流水号", width = 15)
    private String saleNumber;
    @ApiModelProperty(value = "销售单号")
    @Excel(name = "销售单号", width = 15)
    private String saleNo;
    @ApiModelProperty(value = "销售明细序列号")
    @Excel(name = "销售明细序列号", width = 15)
    private String saleNum;
    @ApiModelProperty(value = "药材产地")
    @Excel(name = "药材产地", width = 15)
    private String medicinalOrigin;
    @ApiModelProperty(value = "生产厂商")
    @Excel(name = "生产厂商", width = 15)
    private String manufacturer;
    @ApiModelProperty(value = "单位")
    @Excel(name = "单位", width = 15)
    private String unit;
    /**
     * 饮片code
     */
    @ApiModelProperty(value = "饮片编码")
    @Excel(name = "饮片编码", width = 15)
    private String ypCode;
    /**
     * 包装规格
     */
    @ApiModelProperty(value = "包装规格")
    @Excel(name = "包装规格", width = 15)
    private String packGuige;
}
