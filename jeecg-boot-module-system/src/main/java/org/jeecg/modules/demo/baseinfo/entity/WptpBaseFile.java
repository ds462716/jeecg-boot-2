package org.jeecg.modules.demo.baseinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 基地信息附表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Data
@NoArgsConstructor
@TableName("wptp_base_file")
public class WptpBaseFile implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**创建人*/
	private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date createTime;
	/**更新人*/
	private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date updateTime;
	/**基地编码*/
	private java.lang.String mainId;
	/**路径*/
	@Excel(name = "路径", width = 15)
	private java.lang.String path;
	/**类型0：基地图片；1：基地报告*/
	private java.lang.String type;
	/**0:图片；1：视频*/
	@Excel(name = "0:图片；1：视频", width = 15)
	private java.lang.String fileType;
	/**删除标志*/
	private java.lang.String deleted;

	public WptpBaseFile(String mainId,String path, String type, String fileType, Date createTime,String deleted,String createBy) {
		this.path = path;
		this.type = type;
		this.fileType = fileType;
		this.createTime=createTime;
		this.mainId=mainId;
		this.deleted=deleted;
		this.createBy=createBy;
	}
}
