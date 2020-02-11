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

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 产地初加工表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Data
@TableName("wptp_process_info")
@ApiModel(value="种植环节初加工实体类", description="种植环节初加工实体类")
public class WptpProcessInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@TableId(type = IdType.ID_WORKER_STR)
	@ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**创建日期*/
	@ApiModelProperty(value = "创建日期")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private java.lang.String updateBy;
	/**更新日期*/
	@ApiModelProperty(value = "更新日期")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateTime;
	/**主机代码*/
	@ApiModelProperty(value = "主机代码")
	@NotNull
	private java.lang.String hostCode;
	/**加工单号*/
	@ApiModelProperty(value = "加工单号")
	@NotNull
	private java.lang.String processNo;
	/**投料批次*//*
	@NotNull
	private java.lang.String csBatch;
	*//**投料数量*//*
	@NotNull
	private java.lang.Double materialNum;*/
	/**加工工艺*/
	@ApiModelProperty(value = "加工工艺")
	private java.lang.String processGy;
	/**加工方法*/
	@ApiModelProperty(value = "加工方法")
	private java.lang.String processMethod;
	/**加工开始时间*/
	@ApiModelProperty(value = "加工开始时间")
	private java.lang.String startTime;
	/**加工结束时间*/
	@ApiModelProperty(value = "加工结束时间")
	private java.lang.String endTime;
	/**负责人*/
	@ApiModelProperty(value = "负责人")
	private java.lang.String responsible;
	/**加工数量*/
	@ApiModelProperty(value = "加工数量")
	@NotNull
	private java.lang.Double num;
	/**加工设备*/
	@ApiModelProperty(value = "加工设备")
	private java.lang.String device;
	/**工作流*/
	@ApiModelProperty(value = "工作流")
	private java.lang.String flowId;
	/**删除标志*/
	@ApiModelProperty(value = "删除标志")
	private java.lang.String deleted;
	/**审核标志*/
	@ApiModelProperty(value = "审核标志")
	private java.lang.String auditStatus;
	/**单位*/
	@ApiModelProperty(value = "单位")
	private java.lang.String unit;
	/**规格*/
	@ApiModelProperty(value = "规格")
	private java.lang.String guige;
	@Excel(name = "来源标志标志", width = 15)
	@ApiModelProperty(value = "来源标志标志")
	private java.lang.String source;
	@Excel(name = "客户", width = 15)
	@ApiModelProperty(value = "客户")
	private java.lang.String entId;
}
