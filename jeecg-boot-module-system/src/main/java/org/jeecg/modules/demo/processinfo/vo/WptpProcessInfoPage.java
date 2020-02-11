package org.jeecg.modules.demo.processinfo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @Description: 产地初加工表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Data
public class WptpProcessInfoPage {
	
	/**主键*/
	private java.lang.String id;
	/**创建人*/
	private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**更新人*/
	private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/**主机代码*/
	@Excel(name = "主机代码", width = 15)
	private java.lang.String hostCode;
	/**加工单号*/
	@Excel(name = "加工单号", width = 15)
	private java.lang.String processNo;
	/**投料批次*/
	@Excel(name = "投料批次", width = 15)
	private java.lang.String csBatch;
	/**投料数量*/
	@Excel(name = "投料数量", width = 15)
	private java.lang.Double materialNum;
	/**加工工艺*/
	@Excel(name = "加工工艺", width = 15)
	private java.lang.String processGy;
	/**加工方法*/
	@Excel(name = "加工方法", width = 15)
	private java.lang.String processMethod;
	/**加工开始时间*/
	@Excel(name = "加工开始时间", width = 15)
	private java.lang.String startTime;
	/**加工结束时间*/
	@Excel(name = "加工结束时间", width = 15)
	private java.lang.String endTime;
	/**负责人*/
	@Excel(name = "负责人", width = 15)
	private java.lang.String responsible;
	/**加工数量*/
	@Excel(name = "加工数量", width = 15)
	private java.lang.Double num;
	/**加工设备*/
	@Excel(name = "加工设备", width = 15)
	private java.lang.String device;
	/**工作流*/
	private java.lang.String flowId;
	/**删除标志*/
	private java.lang.String deleted;
	/**审核标志*/
	private java.lang.String auditStatus;
	
/*	@ExcelCollection(name="产地初加工附表")*/
	private List<WptpProcessFile> wptpProcessFileList;	
	
}
