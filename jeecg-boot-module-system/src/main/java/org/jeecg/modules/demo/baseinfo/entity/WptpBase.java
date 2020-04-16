package org.jeecg.modules.demo.baseinfo.entity;

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
 * @Description: 基地信息管理表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Data
@TableName("wptp_base")
@ApiModel(value = "基地实体", description = "基地实体")
public class WptpBase implements Serializable {
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
    @NotNull
    private java.lang.String hostCode;
    /**
     * 基地编码
     */
    @ApiModelProperty(value = "基地编码")
    @NotNull
    private java.lang.String baseCode;
    /**
     * 基地名称
     */
    @ApiModelProperty(value = "基地名称")
    @NotNull
    private java.lang.String baseName;
    /**
     * 省份id
     */
    @ApiModelProperty(value = "省份id")
    @NotNull
    private java.lang.String province;
    /**
     * 市id
     */
    @ApiModelProperty(value = "市id")
    @NotNull
    private java.lang.String city;
    /**
     * 区（县）id
     */
    @ApiModelProperty(value = "区（县）id")
    @NotNull
    private java.lang.String area;
    /**
     * 基地位置
     */
    @ApiModelProperty(value = "基地位置")
    private java.lang.String baseAddress;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @NotNull
    private java.lang.String gpsLongitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    @NotNull
    private java.lang.String gpsLatitude;
    /**
     * 基地面积
     */
    @ApiModelProperty(value = "基地面积")
    private java.lang.String baseArea;
    /**
     * 地形
     */
    @ApiModelProperty(value = "地形")
    @NotNull
    private java.lang.String land;
    /**
     * 海拔
     */
    @ApiModelProperty(value = "海拔")
    @NotNull
    private java.lang.String heigh;
    /**
     * 土壤类型
     */
    @ApiModelProperty(value = "土壤类型")
    @NotNull
    private java.lang.String soilType;
    /**
     * 酸碱度
     */
    @ApiModelProperty(value = "酸碱度")
    @NotNull
    private java.lang.String ph;
    /**
     * 气候类型
     */
    @ApiModelProperty(value = "气候类型")
    private java.lang.String weatherType;
    /**
     * 年降雨量
     */
    @ApiModelProperty(value = "年降雨量")
    @NotNull
    private java.lang.String rearRainfall;
    /**
     * 年平均温度
     */
    @ApiModelProperty(value = "年平均温度")
    @NotNull
    private java.lang.String temperature;
    /**
     * 水源
     */
    @ApiModelProperty(value = "水源")
    @NotNull
    private java.lang.String waterSrc;
    /**
     * 企业编号
     */
    @ApiModelProperty(value = "企业编号")
    @NotNull
    private java.lang.String entId;
    /**
     * 所属基地公司
     */
    @ApiModelProperty(value = "所属基地公司")
    @NotNull
    private java.lang.String baseEnt;
    /**
     * 删除状态
     */
    @ApiModelProperty(value = "删除状态")
    private java.lang.String baseDesc;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private java.lang.String flowId;
    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    private java.lang.String auditStatus;
    /**
     * 删除状态
     */
    @ApiModelProperty(value = "删除状态")
    private java.lang.String deleted;
}
