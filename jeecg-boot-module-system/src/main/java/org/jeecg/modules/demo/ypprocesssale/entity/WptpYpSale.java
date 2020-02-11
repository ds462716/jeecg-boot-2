package org.jeecg.modules.demo.ypprocesssale.entity;

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
 * @Description: 饮片销售
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Data
@TableName("wptp_yp_sale")
@ApiModel(value="饮片加工-饮片销售实体类", description="饮片加工-饮片销售实体类")
public class WptpYpSale implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@ApiModelProperty(value = "主键")
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**创建人名称*/
	@ApiModelProperty(value = "创建人名称")
	private java.lang.String createName;
	/**创建人登录名称*/
	@ApiModelProperty(value = "创建人登录名称")
	private java.lang.String createBy;
	/**创建日期*/
	@ApiModelProperty(value = "创建日期")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createDate;
	/**更新人名称*/
	@ApiModelProperty(value = "更新人名称")
	private java.lang.String updateName;
	/**更新人登录名称*/
	@ApiModelProperty(value = "更新人登录名称")
	private java.lang.String updateBy;
	/**更新日期*/
	@ApiModelProperty(value = "更新日期")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date updateDate;
	/**饮片销售水号*/
	@ApiModelProperty(value = "饮片销售水号")
	@NotNull
	private java.lang.String saleNumber;
	/**销售单号*/
	@ApiModelProperty(value = "销售单号")
	@NotNull
	private java.lang.String saleNo;
	/**销售明细序列号*/
	@ApiModelProperty(value = "销售明细序列号")
	@NotNull
	private java.lang.String saleNum;
	/**追溯码*/
	@ApiModelProperty(value = "追溯码")
	@NotNull
	private java.lang.String traceCode;
	/**产品来源流水号*/
	@ApiModelProperty(value = "产品来源流水号")
	@NotNull
	private java.lang.String sourceNo;
	/**产品来源*/
	@ApiModelProperty(value = "产品来源")
	@NotNull
	private java.lang.String source;
	/**产品批号*/
	@ApiModelProperty(value = "产品批号")
	@NotNull
	private java.lang.String batchNo;
	/**种类编码*/
	@ApiModelProperty(value = "种类编码")
	@NotNull
	private java.lang.String categoryCode;
	/**种类名称*/
	@ApiModelProperty(value = "种类名称")
	@NotNull
	private java.lang.String categoryName;
	/**主数据码*/
	@ApiModelProperty(value = "主数据码")
	private java.lang.String mainCode;
	/**饮片名称*/
	@ApiModelProperty(value = "饮片名称")
	@NotNull
	private java.lang.String ypName;
	/**产品规格*/
	@ApiModelProperty(value = "产品规格")
	private java.lang.String guige;
	/**发货数量*/
	@ApiModelProperty(value = "发货数量")
	@NotNull
	private java.lang.Double num;
	/**计量单位*/
	@ApiModelProperty(value = "计量单位")
	private java.lang.String unit;
	/**包装规格*/
	@ApiModelProperty(value = "包装规格")
	private java.lang.String packGuige;
	/**销售时间*/
	@ApiModelProperty(value = "销售时间")
	@NotNull
	private java.lang.String saleTime;
	/**药材产地*/
	@ApiModelProperty(value = "药材产地")
	private java.lang.String medicineOrigin;
	/**流向单位*/
	@ApiModelProperty(value = "流向单位")
	@NotNull
	private java.lang.String entId;
	/**审核标志*/
	@ApiModelProperty(value = "审核标志")
	private java.lang.String auditStatus;
	/**工作流*/
	@ApiModelProperty(value = "工作流")
	private java.lang.String flowId;
	/**删除标志*/
	@ApiModelProperty(value = "删除标志")
	private java.lang.String deleted;
	/**主机代码*/
	@ApiModelProperty(value = "主机代码")
	@NotNull
	private java.lang.String hostCode;
	/**收货地址*/
	@ApiModelProperty(value = "收货地址")
	private java.lang.String address;
	/**联系人*/
	@ApiModelProperty(value = "联系人")
	private java.lang.String contact;
	/**联系电话*/
	@ApiModelProperty(value = "联系电话")
	private java.lang.String telephone;
	/**运输责任人*/
	@ApiModelProperty(value = "运输责任人")
	private java.lang.String responsible;
	/**协会追溯码*/
	@ApiModelProperty(value = "协会追溯码")
	private java.lang.String xhTraceCode;
	/**生产厂商*/
	@ApiModelProperty(value = "生产厂商")
	private java.lang.String manufacturer;
	/**饮片code*/
	@ApiModelProperty(value = "饮片编码")
	private String ypCode;
}
