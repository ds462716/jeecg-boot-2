package org.jeecg.modules.demo.medicinalinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 中药材品种
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Data
@ApiModel(value="药材信息实体类", description="药材信息实体类")
@TableName("wptp_medicinal")
public class WptpMedicinal implements Serializable {
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
	/**药材种类编码*/
	@ApiModelProperty(value = "药材种类编码")
	private java.lang.String medicinalCode;
	/**名称*/
	@ApiModelProperty(value = "名称")
	private java.lang.String name;
	/**用药部位*/
	@ApiModelProperty(value = "用药部位")
	private java.lang.String yybw;
	/**生长环境*/
	@ApiModelProperty(value = "生长环境")
	private java.lang.String szhj;
	/**功效与主治*/
	@ApiModelProperty(value = "功效与主治")
	private java.lang.String func;
	/**采收时期*/
	@ApiModelProperty(value = "采收时期")
	private java.lang.String cssq;
	/**审核标志*/
	@ApiModelProperty(value = "审核标志")
	private java.lang.String auditStatus;
	/**删除状态*/
	@ApiModelProperty(value = "删除状态")
	private java.lang.String deleted;
	/**工作流ID*/
	@ApiModelProperty(value = "工作流ID")
	private java.lang.String flowId;
	/**成品还是原料*/
	@ApiModelProperty(value = "成品还是原料")
	private java.lang.String flag;
}
