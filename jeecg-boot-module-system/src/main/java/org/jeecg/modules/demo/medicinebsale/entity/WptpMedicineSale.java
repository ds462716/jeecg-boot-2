package org.jeecg.modules.demo.medicinebsale.entity;

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
 * @Description: 药材经营，药材销售
 * @Author: jeecg-boot
 * @Date:   2019-10-09
 * @Version: V1.0
 */
@Data
@TableName("wptp_medicine_sale")
@ApiModel(value="药材经营-药材销售实体类", description="药材经营-药材销售实体类")
public class WptpMedicineSale implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@ApiModelProperty(value = "主键")
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**创建日期*/
	@ApiModelProperty(value = "创建日期")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private java.lang.String updateBy;
	/**更新日期*/
	@ApiModelProperty(value = "更新日期")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/**主机代码*/
	@ApiModelProperty(value = "主机代码")
	@NotNull
	@Excel(name = "主机代码", width = 15)
	private java.lang.String hostCode;
	/**饮片销售流水号*/
	@ApiModelProperty(value = "饮片销售流水号")
	@Excel(name = "饮片销售流水号", width = 15)
	@NotNull
	private java.lang.String saleNumber;
	/**销售单号*/
	@ApiModelProperty(value = "销售单号")
	@NotNull
	@Excel(name = "销售单号", width = 15)
	private java.lang.String saleNo;
	/**追溯码*/
	@ApiModelProperty(value = "追溯码")
	@NotNull
	@Excel(name = "追溯码", width = 15)
	private java.lang.String traceCode;
	/**产品批号*/
	@ApiModelProperty(value = "产品批号")
	@NotNull
	@Excel(name = "产品批号", width = 15)
	private java.lang.String productBatch;
	/**药材品名*/
	@ApiModelProperty(value = "药材品名")
	@Excel(name = "药材品名", width = 15)
	@NotNull
	private java.lang.String medicineName;
	/**药材规格*/
	@ApiModelProperty(value = "药材规格")
	@Excel(name = "药材规格", width = 15)
	private java.lang.String guige;
	/**种类编码*/
	@ApiModelProperty(value = "种类编码")
	@Excel(name = "种类编码", width = 15)
	@NotNull
	private java.lang.String categoryCode;
	/**种类名称*/
	@ApiModelProperty(value = "种类名称")
	@NotNull
	@Excel(name = "种类名称", width = 15)
	private java.lang.String categoryName;
	/**主数据码*/
	@ApiModelProperty(value = "主数据码")
	@Excel(name = "主数据码", width = 15)
	private java.lang.String mainCode;
	/**药材入库流水号*/
	@ApiModelProperty(value = "药材入库流水号")
	@Excel(name = "药材入库流水号", width = 15)
	private java.lang.String instockNumber;
	/**出库时间*/
	@NotNull
	@ApiModelProperty(value = "出库时间")
	@Excel(name = "出库时间", width = 15)
	private java.lang.String outTime;
	/**出库数量*/
	@NotNull
	@ApiModelProperty(value = "出库数量")
	@Excel(name = "出库数量", width = 15)
	private java.lang.Double num;
	/**客户*/
	@ApiModelProperty(value = "企业Id")
	@Excel(name = "企业Id", width = 15)
	private java.lang.String entId;
	/**收货地址*/
	@ApiModelProperty(value = "收货地址")
	@Excel(name = "收货地址", width = 15)
	private java.lang.String address;
	/**联系人*/
	@ApiModelProperty(value = "联系人")
	@Excel(name = "联系人", width = 15)
	private java.lang.String contact;
	/**联系电话*/
	@ApiModelProperty(value = "联系电话")
	@Excel(name = "联系电话", width = 15)
	private java.lang.String telephone;
	/**运输责任人*/
	@ApiModelProperty(value = "运输责任人")
	@Excel(name = "运输责任人", width = 15)
	private java.lang.String responsible;
	/**审核标志*/
	@ApiModelProperty(value = "审核标志")
	private java.lang.String auditStatus;
	/**工作流*/
	@ApiModelProperty(value = "工作流")
	private java.lang.String flowId;
	/**删除标志*/
	@ApiModelProperty(value = "删除标志")
	private java.lang.String deleted;
	/**销售明细序列号*/
	@NotNull
	@ApiModelProperty(value = "销售明细序列号")
	@Excel(name = "销售明细序列号", width = 15)
	private java.lang.String saleNum;
	@Excel(name = "单位", width = 15)
	@NotNull
	@ApiModelProperty(value = "单位")
	private java.lang.String unit;
	@Excel(name = "协会追溯平台", width = 15)
	@ApiModelProperty(value = "协会追溯平台")
	private java.lang.String xhTraceCode;
	@ApiModelProperty(value = "药材产地")
	@Excel(name = "药材产地", width = 15)
	private String medicinalOrigin;
	@ApiModelProperty(value = "生产厂商")
	@Excel(name = "生产厂商", width = 15)
	private String manufacturer;

}
