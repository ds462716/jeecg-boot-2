package org.jeecg.modules.demo.entinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 企业信息附表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Data
@NoArgsConstructor
@TableName("wptp_ent_file")
public class WptpEntFile implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**编号*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**企业编号*/
	private java.lang.String mainId;
	/**路径*/
	@Excel(name = "路径", width = 15)
	private java.lang.String path;
	/**文件类型 0：图片；1：视频；2：pdf*/
	@Excel(name = "文件类型 0：图片；1：视频；2：pdf", width = 15)
	private java.lang.String fileType;
	/**1：证件照片*/
	@Excel(name = "1：证件照片", width = 15)
	private java.lang.String type;
	/**0:未删除；1：删除*/
	private java.lang.String deleted;
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
}
