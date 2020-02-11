package org.jeecg.modules.demo.ypbinstock.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstockFile;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @Description: 饮片经营药材入库
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Data
public class WptpYpbInstockPage {
	
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
	/**饮片入库流水号*/
	@Excel(name = "饮片入库流水号", width = 15)
	private java.lang.String instockNo;
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
	/**饮片规格*/
	@Excel(name = "饮片规格", width = 15)
	private java.lang.String guige;
	/**包装规格*/
	@Excel(name = "包装规格", width = 15)
	private java.lang.String packGuige;
	/**来源追溯码*/
	@Excel(name = "来源追溯码", width = 15)
	private java.lang.String traceCode;
	/**来源环节*/
	@Excel(name = "来源环节", width = 15)
	private java.lang.String sourceFlag;
	/**产品批号*/
	@Excel(name = "产品批号", width = 15)
	private java.lang.String productBatch;
	/**到货时间*/
	@Excel(name = "到货时间", width = 15)
	private java.lang.String arrivalTime;
	/**采购数量*/
	@Excel(name = "采购数量", width = 15)
	private java.lang.Double num;
	/**生产日期*/
	@Excel(name = "生产日期", width = 15)
	private java.lang.String producedDate;
	/**有效日期*/
	@Excel(name = "有效日期", width = 15)
	private java.lang.String expiredDate;
	/**药材产地*/
	@Excel(name = "药材产地", width = 15)
	private java.lang.String medicinalOrigin;
	/**生产厂商*/
	@Excel(name = "生产厂商", width = 15)
	private java.lang.String manufacturer;
	/**供应商企业编码*/
	@Excel(name = "供应商企业编码", width = 15)
	private java.lang.String entId;
	/**采购负责人*/
	@Excel(name = "采购负责人", width = 15)
	private java.lang.String purchaseResponsible;
	/**检验依据*/
	@Excel(name = "检验依据", width = 15)
	private java.lang.String checkBasis;
	/**储存环境*/
	@Excel(name = "储存环境", width = 15)
	private java.lang.String enviornment;
	/**养护方法*/
	@Excel(name = "养护方法", width = 15)
	private java.lang.String method;
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
/*	@ExcelCollection(name="饮片经营药材入库附表")*/
	private List<WptpYpbInstockFile> wptpYpbInstockFileList;	
	
}
