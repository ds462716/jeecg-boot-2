package org.jeecg.modules.demo.blockmedicinal.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.blockmedicinal.entity.WptpBlockMeidicinal;

import java.io.Serializable;

@Data
@ApiModel(value = "种植环节档案结果集", description = "种植环节档案结果集")
public class WptpBlockMeidicinalVO extends WptpBlockMeidicinal implements Serializable {
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
     * 药材名称
     */
    @ApiModelProperty(value = "药材名称")
    private java.lang.String medicinalName;
    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    private java.lang.String entName;
    /**上一级企业名称*/
    /*  private java.lang.String upperEntName;*/
    /**
     * 基地名称
     */
    @ApiModelProperty(value = "基地名称")
    private java.lang.String baseName;
    /**
     * 面积
     */
    @ApiModelProperty(value = "面积")
    private java.lang.String baseArea;
}
