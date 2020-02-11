package org.jeecg.modules.demo.blockmedicinal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 生产档案表
 * @Author: jeecg-boot
 * @Date:   2019-10-09
 * @Version: V1.0
 */
@Data
@TableName("wptp_block_meidicinal")
@ApiModel(value="种植环节档案实体类", description="种植环节档案实体类")
public class WptpBlockMeidicinal implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**主键*/
	@ApiModelProperty(value = "主键")
	@TableId(type = IdType.ID_WORKER_STR)
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
	@NotNull
	@ApiModelProperty(value = "主机代码")
	private java.lang.String hostCode;
	/**生产档案编号*/
	@NotNull
	@ApiModelProperty(value = "生产档案编号")
	private java.lang.String blockMedicinalId;
	/**药材品种编号*/
	@NotNull
	@ApiModelProperty(value = "药材品种编号")
	private java.lang.String medicinalCode;
	/**所属企业*/
	@NotNull
	@ApiModelProperty(value = "所属企业")
	private java.lang.String entId;
	/**所属基地*/
	@NotNull
	@ApiModelProperty(value = "所属基地")
	private java.lang.String baseCode;
	/**地块编号*/
	@NotNull
	@ApiModelProperty(value = "地块编号")
	private java.lang.String blockCode;
	/**负责人*/
	@ApiModelProperty(value = "负责人")
	private java.lang.String baseAdmin;
	/**档案时间*/
	@NotNull
	@ApiModelProperty(value = "档案时间")
	private java.lang.String fileTime;
	/**审核标志*/
	@ApiModelProperty(value = "审核标志")
	private java.lang.String auditStatus;
	/**删除标志*/
	@ApiModelProperty(value = "删除标志")
	private java.lang.String deleted;
	/**工作流*/
	@ApiModelProperty(value = "工作流")
	private java.lang.String flowId;
}
