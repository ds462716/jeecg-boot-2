package org.jeecg.modules.demo.plantinfo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantFile;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @Description: 田间作业表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Data
public class WptpPlantInfoPage {
	
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
	/**地块药材编号*/
	@Excel(name = "地块药材编号", width = 15)
	private java.lang.String blockMedicinalId;
	/**作业编号*/
	@Excel(name = "作业编号", width = 15)
	private java.lang.String plantId;
	/**采收批次号*/
	@Excel(name = "采收批次号", width = 15)
	private java.lang.String batchCode;
	/**作业类别*/
	@Excel(name = "作业类别", width = 15)
	private java.lang.String plantSatus;
	/**繁殖方法*/
	@Excel(name = "繁殖方法", width = 15)
	private java.lang.String plantMethod;
	/**繁殖时间*/
	@Excel(name = "繁殖时间", width = 15)
	private java.lang.String fzTime;
	/**操作时间*/
	@Excel(name = "操作时间", width = 15)
	private java.lang.String operateTime;
	/**投入品*/
	@Excel(name = "投入品", width = 15)
	private java.lang.String inputInto;
	/**量*/
	@Excel(name = "量", width = 15)
	private java.lang.String number;
	/**采收部位/繁殖地点*/
	@Excel(name = "采收部位/繁殖地点", width = 15)
	private java.lang.String csPart;
	/**来源渠道*/
	@Excel(name = "来源渠道", width = 15)
	private java.lang.String source;
	/**植物来源*/
	@Excel(name = "植物来源", width = 15)
	private java.lang.String plantSource;
	/**责任人*/
	@Excel(name = "责任人", width = 15)
	private java.lang.String responsible;
	/**审核标志*/
	private java.lang.String auditStatus;
	/**工作流*/
	private java.lang.String flowId;
	/**删除标志*/
	private java.lang.String deleted;
	/**作业方式*/
	@Excel(name = "作业方式", width = 15)
	private java.lang.String plantType;
	
/*	@ExcelCollection(name="田间作业附表")*/
	private List<WptpPlantFile> wptpPlantFileList;	
	
}
