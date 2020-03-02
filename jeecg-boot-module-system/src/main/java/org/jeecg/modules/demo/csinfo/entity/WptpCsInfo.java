package org.jeecg.modules.demo.csinfo.entity;

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

/**
 * @Description: 采收批次信息
 * @Author: jeecg-boot
 * @Date: 2019-10-16
 * @Version: V1.0
 */
@Data
@TableName("wptp_cs_info")
@ApiModel(value = "种植环节采收批次实体类", description = "种植环节采收批次实体类")
public class WptpCsInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
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
    @Excel(name = "主机代码", width = 15)
    private java.lang.String hostCode;
    /**
     * 采收批次号
     */
    @ApiModelProperty(value = "采收批次号")
    @Excel(name = "采收批次号", width = 15)
    private java.lang.String csNo;
    /**
     * 规格
     */
    @ApiModelProperty(value = "规格")
    @Excel(name = "规格", width = 15)
    private java.lang.String guige;
    /**
     * 重量
     */
    @ApiModelProperty(value = "重量")
    @Excel(name = "重量", width = 15)
    private java.lang.Double weight;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    @Excel(name = "单位", width = 15)
    private java.lang.String unit;
    /**
     * 生产档案编号
     */
    @ApiModelProperty(value = "生产档案编号")
    @Excel(name = "生产档案编号", width = 15)
    private java.lang.String blockMedicinalId;
    /**
     * 药材品种
     */
    @ApiModelProperty(value = "药材品种")
    @Excel(name = "药材品种", width = 15)
    private java.lang.String medicinalCode;
    /**
     * 所属企业
     */
    @ApiModelProperty(value = "所属企业")
    @Excel(name = "所属企业", width = 15)
    private java.lang.String entId;
    /**
     * 所属基地
     */
    @ApiModelProperty(value = "所属基地")
    @Excel(name = "所属基地", width = 15)
    private java.lang.String baseCode;
    /**
     * 地块编号
     */
    @ApiModelProperty(value = "地块编号")
    @Excel(name = "地块编号", width = 15)
    private java.lang.String blockCode;
    /**
     * 标志
     */
    @ApiModelProperty(value = "标志")
    private java.lang.String flag;
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
    @ApiModelProperty(value = "基地名称")
    @Excel(name = "基地名称", width = 15)
    private java.lang.String baseName;
}
