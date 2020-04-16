package org.jeecg.modules.demo.plantinfo.entity;

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
 * @Description: 田间作业表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Data
@TableName("wptp_plant_info")
@ApiModel(value = "种植环节作业实体类", description = "种植环节作业实体类")
public class WptpPlantInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
    /**
     * 主机代码
     */
    @ApiModelProperty(value = "主机代码")
    @NotNull
    private java.lang.String hostCode;
    /**
     * 地块药材编号
     */
    @ApiModelProperty(value = "地块药材编号")
    @NotNull
    private java.lang.String blockMedicinalId;
    /**
     * 作业编号
     */
    @ApiModelProperty(value = "作业编号")
    @NotNull
    private java.lang.String plantId;
    /**
     * 采收批次号
     */
    @ApiModelProperty(value = "采收批次号")
    private java.lang.String batchCode;
    /**
     * 作业类别
     */
    @NotNull
    @ApiModelProperty(value = "作业类别")
    private java.lang.String plantSatus;
    /**
     * 繁殖方法
     */
    @ApiModelProperty(value = "繁殖方法")
    private java.lang.String plantMethod;
    /**
     * 繁殖时间
     */
    @ApiModelProperty(value = "繁殖时间")
    private java.lang.String fzTime;
    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间")
    private java.lang.String operateTime;
    /**
     * 投入品
     */
    @ApiModelProperty(value = "投入品")
    private java.lang.String inputInto;
    /**
     * 量
     */
    @ApiModelProperty(value = "量")
    private java.lang.String number;
    /**
     * 采收部位/繁殖地点
     */
    @ApiModelProperty(value = "采收部位/繁殖地点")
    private java.lang.String csPart;
    /**
     * 来源渠道
     */
    @ApiModelProperty(value = "来源渠道")
    private java.lang.String source;
    /**
     * 植物来源
     */
    @ApiModelProperty(value = "植物来源")
    private java.lang.String plantSource;
    /**
     * 责任人
     */
    @ApiModelProperty(value = "责任人")
    private java.lang.String responsible;
    /**
     * 审核标志
     */
    @ApiModelProperty(value = "审核标志")
    private java.lang.String auditStatus;
    /**
     * 工作流
     */
    @ApiModelProperty(value = "工作流")
    private java.lang.String flowId;
    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志")
    private java.lang.String deleted;
    /**
     * 作业方式
     */
    @ApiModelProperty(value = "作业方式")
    private java.lang.String plantType;//种原
}
