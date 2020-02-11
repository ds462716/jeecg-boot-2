package org.jeecg.modules.demo.blockinfo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.demo.blockinfo.entity.WptpBlockFile;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @Description: 地块表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Data
public class WptpBlockInfoPage {
	
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
	/**地块编号*/
	@Excel(name = "地块编号", width = 15)
	private java.lang.String blockCode;
	/**所属企业*/
	@Excel(name = "所属企业", width = 15)
	private java.lang.String entId;
	/**经度*/
	@Excel(name = "经度", width = 15)
	private java.lang.String gpsLongitude;
	/**纬度*/
	@Excel(name = "纬度", width = 15)
	private java.lang.String gpsLatitude;
	/**面积*/
	@Excel(name = "面积", width = 15)
	private java.lang.String baseArea;
	/**删除标志*/
	private java.lang.String deleted;
	
/*	@ExcelCollection(name="地块表")*/
	private List<WptpBlockFile> wptpBlockFileList;	
	
}
