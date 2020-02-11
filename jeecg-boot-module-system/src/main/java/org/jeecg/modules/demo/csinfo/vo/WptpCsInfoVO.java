package org.jeecg.modules.demo.csinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.csinfo.entity.WptpCsInfo;

import java.io.Serializable;
@Data
@ApiModel(value="种植环节采收批次结果集", description="种植环节采收批次结果集")
public class WptpCsInfoVO extends WptpCsInfo implements Serializable {
    /**企业名称*/
    @ApiModelProperty(value = "企业名称")
    private java.lang.String entName;
    /**上一级企业名称*/
    @ApiModelProperty(value = "上一级企业名称")
    private java.lang.String upperEntName;
    /**生产档案编号*/
    @ApiModelProperty(value = "生产档案编号")
    private java.lang.String blockMedicinalId;
    /**药材品种编号*/
    @ApiModelProperty(value = "药材品种编号")
    private java.lang.String medicinalCode;
    /**所属企业*/
    @ApiModelProperty(value = "所属企业")
    private java.lang.String entId;
    /**所属基地*/
    @ApiModelProperty(value = "所属基地")
    private java.lang.String baseCode;
    /**地块编号*/
    @ApiModelProperty(value = "地块编号")
    private java.lang.String blockCode;
    /**负责人*/
    @ApiModelProperty(value = "负责人")
    private java.lang.String baseAdmin;
    /**档案时间*/
    @ApiModelProperty(value = "档案时间")
    private java.lang.String fileTime;
    /**药材名称*/
    @ApiModelProperty(value = "药材名称")
    private java.lang.String medicinalName;
    /**基地名称*/
    @ApiModelProperty(value = "基地名称")
    private java.lang.String baseName;
}
