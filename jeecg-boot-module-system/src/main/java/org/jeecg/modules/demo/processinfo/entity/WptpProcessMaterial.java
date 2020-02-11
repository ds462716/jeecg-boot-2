package org.jeecg.modules.demo.processinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 初加工原料表
 * @Author: jeecg-boot
 * @Date:   2019-10-16
 * @Version: V1.0
 */
@Data
@TableName("wptp_process_material")
@ApiModel(value="种植环节初加工原料结果集", description="种植环节初加工原料结果集")
public class WptpProcessMaterial implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@ApiModelProperty(value = "主键")
	@TableId(type = IdType.ID_WORKER_STR)
	private String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private String createBy;
	/**创建日期*/
	@ApiModelProperty(value = "创建日期")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private String updateBy;
	/**更新日期*/
	@ApiModelProperty(value = "更新日期")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	/**加工单号*/
	@ApiModelProperty(value = "加工单号")
	@Excel(name = "加工单号", width = 15)
	public String processNo;
	/**投料批次*/
	@ApiModelProperty(value = "投料批次")
	@Excel(name = "投料批次", width = 15)
	public String csBatch;
	/**投料数量*/
	@ApiModelProperty(value = "投料数量")
	@Excel(name = "投料数量", width = 15)
	public Double materialNum;
	/**单位*/
	@ApiModelProperty(value = "单位")
	@Excel(name = "单位", width = 15)
	public String unit;
	/**删除标志*/
	@ApiModelProperty(value = "删除标志")
	private String deleted;
}
