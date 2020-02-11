package org.jeecg.modules.demo.ypprocessinstock.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstockFile;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @Description: 饮片加工-药材入库表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Data
public class WptpYpInstockPage {
	
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
	/**药材入库流水号*/
	@Excel(name = "药材入库流水号", width = 15)
	private java.lang.String instockNumber;
	/**入库单号*/
	@Excel(name = "入库单号", width = 15)
	private java.lang.String instockNo;
	/**入库明细序列号*/
	@Excel(name = "入库明细序列号", width = 15)
	private java.lang.String instockNum;
	/**仓库*/
	@Excel(name = "仓库", width = 15)
	private java.lang.String storeCode;
	/**主机代码*/
	@Excel(name = "主机代码", width = 15)
	private java.lang.String hostCode;
	/**来源环节*/
	@Excel(name = "来源环节", width = 15)
	private java.lang.String sourceFlag;
	/**产品批号*/
	@Excel(name = "产品批号", width = 15)
	private java.lang.String productBatch;
	/**种类编码*/
	@Excel(name = "种类编码", width = 15)
	private java.lang.String categoryCode;
	/**种类名称*/
	@Excel(name = "种类名称", width = 15)
	private java.lang.String categoryName;
	/**原料品名*/
	@Excel(name = "原料品名", width = 15)
	private java.lang.String materialName;
	/**原料品规*/
	@Excel(name = "原料品规", width = 15)
	private java.lang.String guige;
	/**原料内部批次*/
	@Excel(name = "原料内部批次", width = 15)
	private java.lang.String materialBatch;
	/**到货时间*/
	@Excel(name = "到货时间", width = 15)
	private java.lang.String arrivalTime;
	/**采购数量*/
	@Excel(name = "采购数量", width = 15)
	private java.lang.Double num;
	/**原料产地*/
	@Excel(name = "原料产地", width = 15)
	private java.lang.String materialOrigin;
	/**供应商编码*/
	@Excel(name = "供应商编码", width = 15)
	private java.lang.String entId;
	/**采购负责人*/
	@Excel(name = "采购负责人", width = 15)
	private java.lang.String responsible;
	/**质检单号*/
	@Excel(name = "质检单号", width = 15)
	private java.lang.String checkNo;
	/**检品名称*/
	@Excel(name = "检品名称", width = 15)
	private java.lang.String checkName;
	/**检验目的*/
	@Excel(name = "检验目的", width = 15)
	private java.lang.String checkPurpose;
	/**检验项目及结论*/
	@Excel(name = "检验项目及结论", width = 15)
	private java.lang.String checkItem;
	/**检测依据*/
	@Excel(name = "检测依据", width = 15)
	private java.lang.String checkBasis;
	/**检测时间*/
	@Excel(name = "检测时间", width = 15)
	private java.lang.String checkTime;
	/**检测结果*/
	@Excel(name = "检测结果", width = 15)
	private java.lang.String checkResult;
	/**质检负责人*/
	@Excel(name = "质检负责人", width = 15)
	private java.lang.String checkResponsible;
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
	/**来源追溯码*/
	@Excel(name = "来源追溯码", width = 15)
	private java.lang.String traceCode;
	/**删除标志*/
	private java.lang.String deleted;
	/**主数据码*/
	@Excel(name = "主数据码", width = 15)
	private java.lang.String mainCode;
	
/*	@ExcelCollection(name="饮片入库附表")*/
	private List<WptpYpInstockFile> wptpYpInstockFileList;	
	
}
