package org.jeecg.modules.demo.baseinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.baseinfo.entity.WptpBase;

import java.io.Serializable;

@Data
@ApiModel(value = "基地结果集", description = "基地结果集")
public class WptpBaseVO extends WptpBase implements Serializable {
    /**
     * 省份名
     */
    @ApiModelProperty(value = "省份名")
    private java.lang.String ProvinceName;
    /**
     * 市
     */
    @ApiModelProperty(value = "市")
    private java.lang.String cityName;
    /**
     * 区（县）名
     */
    @ApiModelProperty(value = "区（县）名")
    private java.lang.String districtName;
    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    private java.lang.String entName;
    /**
     * 上一级企业名称
     */
    @ApiModelProperty(value = "上一级企业名称")
    private java.lang.String upperEntName;
    @ApiModelProperty(value = "基地图片")
    private String imgUrl;
    @ApiModelProperty(value = "基地报告")
    private String[] reportUrl;
}
