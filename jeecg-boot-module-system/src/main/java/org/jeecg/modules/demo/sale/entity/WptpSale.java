package org.jeecg.modules.demo.sale.entity;

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
 * @Description: 出库销售表
 * @Author: jeecg-boot
 * @Date:   2019-10-09
 * @Version: V1.0
 */
@Data
@TableName("wptp_sale")
@ApiModel(value="种植销售结果集", description="种植销售结果集")
public class WptpSale implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@ApiModelProperty(value = "主键")
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建日期*/
	@ApiModelProperty(value = "创建日期")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private String updateBy;
	/**更新日期*/
	@ApiModelProperty(value = "更新日期")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/**主机代码*/
	@ApiModelProperty(value = "主机代码")
	@Excel(name = "主机代码", width = 15)
	@NotNull
	private String hostCode;
	/**销售单号*/
	@ApiModelProperty(value = "销售单号")
	@NotNull
	@Excel(name = "销售单号", width = 15)
	private String saleNo;
	/**追溯码*/
	@ApiModelProperty(value = "追溯码")
	@NotNull
	@Excel(name = "追溯码", width = 15)
	private String traceCode;
	/**产品批号*/
	@ApiModelProperty(value = "产品批号")
	@NotNull
	@Excel(name = "产品批号", width = 15)
	private String productBatch;
	/**药材品名*/
	@ApiModelProperty(value = "药材品名")
	@NotNull
	@Excel(name = "药材品名", width = 15)
	private String medicineName;
	/**采收批次或者初加工号*/
	@ApiModelProperty(value = "采收批次或者初加工号")
	@NotNull
	@Excel(name = "采收批次或者初加工号", width = 15)
	private String medicineBatch;
	/**收货地址*/
	@ApiModelProperty(value = "收货地址")
	@Excel(name = "收货地址", width = 15)
	private String address;
	/**联系人*/
	@ApiModelProperty(value = "联系人")
	@Excel(name = "联系人", width = 15)
	private String contact;
	/**联系电话*/
	@ApiModelProperty(value = "联系电话")
	@Excel(name = "联系电话", width = 15)
	private String telephone;
	/**规格*/
	@ApiModelProperty(value = "规格")
	@Excel(name = "规格", width = 15)
	private String guige;
	/**数量*/
	@ApiModelProperty(value = "数量")
	@NotNull
	@Excel(name = "数量", width = 15)
	private Double num;
	/**单价*/
	@ApiModelProperty(value = "单价")
	@Excel(name = "单价", width = 15)
	private Double price;
	/**总价*/
	@ApiModelProperty(value = "总价")
	@Excel(name = "总价", width = 15)
	private Double totalPrice;
	/**备注*/
	@ApiModelProperty(value = "备注")
	@Excel(name = "备注", width = 15)
	private String remark;
	/**审核标志*/
	@ApiModelProperty(value = "审核标志")
	private String auditStatus;
	/**工作流*/
	@ApiModelProperty(value = "工作流")
	private String flowId;
	/**删除标志*/
	@ApiModelProperty(value = "删除标志")
	private String deleted;
	@ApiModelProperty(value = "来源标志标志")
	@Excel(name = "来源标志标志", width = 15)
	@NotNull
	private String source;
	@ApiModelProperty(value = "客户")
	@Excel(name = "客户", width = 15)
	private String entId;
	@ApiModelProperty(value = "销售流水号")
	@Excel(name = "销售流水号", width = 15)
	private String saleNumber;
	@ApiModelProperty(value = "销售明细序列号")
	@Excel(name = "销售明细序列号", width = 15)
	private String saleNum;
	@ApiModelProperty(value = "运输责任人")
	@Excel(name = "运输责任人", width = 15)
	private String responsible;
	@ApiModelProperty(value = "协会追溯平台")
	private java.lang.String xhTraceCode;
	@ApiModelProperty(value = "药材产地")
	@Excel(name = "药材产地", width = 15)
	private String medicinalOrigin;
	@ApiModelProperty(value = "生产厂商")
	@Excel(name = "生产厂商", width = 15)
	private String manufacturer;
	@ApiModelProperty(value = "计量单位")
	@Excel(name = "计量单位", width = 15)
	private String unit;
	@ApiModelProperty(value = "销售时间")
	@Excel(name = "销售时间", width = 15)
	private String saleTime;
}
