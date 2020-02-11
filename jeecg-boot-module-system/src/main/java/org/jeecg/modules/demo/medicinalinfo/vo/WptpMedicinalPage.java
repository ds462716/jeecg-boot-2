package org.jeecg.modules.demo.medicinalinfo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicineFile;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @Description: 中药材品种
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Data
public class WptpMedicinalPage {
	
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
	/**药材种类编码*/
	@Excel(name = "药材种类编码", width = 15)
	private java.lang.String medicinalCode;
	/**名称*/
	@Excel(name = "名称", width = 15)
	private java.lang.String name;
	/**用药部位*/
	@Excel(name = "用药部位", width = 15)
	private java.lang.String yybw;
	/**生长环境*/
	@Excel(name = "生长环境", width = 15)
	private java.lang.String szhj;
	/**功效与主治*/
	@Excel(name = "功效与主治", width = 15)
	private java.lang.String func;
	/**采收时期*/
	@Excel(name = "采收时期", width = 15)
	private java.lang.String cssq;
	/**审核标志*/
	private java.lang.String auditStatus;
	/**删除状态*/
	private java.lang.String deleted;
	/**工作流ID*/
	private java.lang.String flowId;
	/**
	 * 成品还是原料
	 */
	private java.lang.String flag;
	/*	@ExcelCollection(name="药材信息附表")*/
	private List<WptpMedicineFile> wptpMedicineFileList;	
	
}
