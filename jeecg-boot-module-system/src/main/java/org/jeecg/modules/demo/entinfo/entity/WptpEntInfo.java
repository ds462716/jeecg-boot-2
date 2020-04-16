package org.jeecg.modules.demo.entinfo.entity;

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
 * @Description: 企业基本信息
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Data
@TableName("wptp_ent_info")
@ApiModel(value = "企业信息实体类", description = "企业信息实体类")
public class WptpEntInfo implements Serializable {
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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
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
    @NotNull
    @ApiModelProperty(value = "主机代码")
    private java.lang.String hostCode;
    /**
     * 企业编码
     */
    @ApiModelProperty(value = "企业编码")
    private java.lang.String entId;
    /**
     * 企业类别
     */
    @ApiModelProperty(value = "企业类别")
    private java.lang.String entType;
    /**
     * 组织机构代码
     */
    @ApiModelProperty(value = "组织机构代码")
    private java.lang.String orgCode;
    /**
     * 法人
     */
    @ApiModelProperty(value = "法人")
    private java.lang.String entPrincipal;
    /**
     * 授权人
     */
    @ApiModelProperty(value = "授权人")
    private java.lang.String authorize;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private java.lang.String phone;
    /**
     * 注册资金
     */
    @ApiModelProperty(value = "注册资金")
    private java.lang.String regCapi;
    /**
     * 工商注册号
     */
    @ApiModelProperty(value = "工商注册号")
    private java.lang.String busRegisNum;
    /**
     * 企业地址
     */
    @ApiModelProperty(value = "企业地址")
    private java.lang.String entAdd;
    /**
     * 成立时间
     */
    @ApiModelProperty(value = "成立时间")
    private java.lang.String establiTime;
    /**
     * 企业网址
     */
    @ApiModelProperty(value = "企业网址")
    private java.lang.String entWebsite;
    /**
     * 省份id
     */
    @ApiModelProperty(value = "省份code")
    private java.lang.String province;
    /**
     * 市id
     */
    @ApiModelProperty(value = "市code")
    private java.lang.String city;
    /**
     * 区id
     */
    @ApiModelProperty(value = "区code")
    private java.lang.String area;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private java.lang.String address;
    /**
     * 主体介绍
     */
    @ApiModelProperty(value = "主体介绍")
    private java.lang.String mainIntro;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private java.lang.String gpsLongitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private java.lang.String gpsLatitude;
    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志")
    private java.lang.String deleted;
    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    private java.lang.String auditStatus;
    /**
     * 工作流id
     */
    @ApiModelProperty(value = "工作流id")
    private java.lang.String flowId;
    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    private java.lang.String entName;
    /**
     * 协会编码
     */
    @ApiModelProperty(value = "协会编码")
    private java.lang.String xhCode;
}
