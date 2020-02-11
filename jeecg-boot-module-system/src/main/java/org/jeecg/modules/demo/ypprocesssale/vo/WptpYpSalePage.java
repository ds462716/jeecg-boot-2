package org.jeecg.modules.demo.ypprocesssale.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSaleFile;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @Description: 饮片销售
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Data
public class WptpYpSalePage {
	
	/**主键*/
	private java.lang.String id;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date updateDate;
	/**饮片销售水号*/
	@Excel(name = "饮片销售水号", width = 15)
	private java.lang.String saleNumber;
	/**销售单号*/
	@Excel(name = "销售单号", width = 15)
	private java.lang.String saleNo;
	/**销售明细序列号*/
	@Excel(name = "销售明细序列号", width = 15)
	private java.lang.String saleNum;
	/**追溯码*/
	@Excel(name = "追溯码", width = 15)
	private java.lang.String traceCode;
	/**产品来源流水号*/
	@Excel(name = "产品来源流水号", width = 15)
	private java.lang.String sourceNo;
	/**产品来源*/
	@Excel(name = "产品来源", width = 15)
	private java.lang.String source;
	/**产品批号*/
	@Excel(name = "产品批号", width = 15)
	private java.lang.String batchNo;
	/**种类编码*/
	@Excel(name = "种类编码", width = 15)
	private java.lang.String categoryCode;
	/**种类名称*/
	@Excel(name = "种类名称", width = 15)
	private java.lang.String categoryName;
	/**主数据码*/
	@Excel(name = "主数据码", width = 15)
	private java.lang.String mainCode;
	/**饮片名称*/
	@Excel(name = "饮片名称", width = 15)
	private java.lang.String ypName;
	/**产品规格*/
	@Excel(name = "产品规格", width = 15)
	private java.lang.String guige;
	/**发货数量*/
	@Excel(name = "发货数量", width = 15)
	private java.lang.Double num;
	/**计量单位*/
	@Excel(name = "计量单位", width = 15)
	private java.lang.String unit;
	/**包装规格*/
	@Excel(name = "包装规格", width = 15)
	private java.lang.String packGuige;
	/**销售时间*/
	@Excel(name = "销售时间", width = 15)
	private java.lang.String saleTime;
	/**药材产地*/
	@Excel(name = "药材产地", width = 15)
	private java.lang.String medicineOrigin;
	/**流向单位*/
	@Excel(name = "流向单位", width = 15)
	private java.lang.String entId;
	/**审核标志*/
	private java.lang.String auditStatus;
	/**工作流*/
	private java.lang.String flowId;
	/**删除标志*/
	private java.lang.String deleted;
	/**主机代码*/
	@Excel(name = "主机代码", width = 15)
	private java.lang.String hostCode;
	/**饮片code*/
	@Excel(name = "饮片编码", width = 15)
	private String ypCode;
/*	@ExcelCollection(name="饮片销售附表")*/
	private List<WptpYpSaleFile> wptpYpSaleFileList;	
	
}
