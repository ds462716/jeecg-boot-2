package org.jeecg.modules.demo.plantinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantInfo;

import java.io.Serializable;
@Data
@ApiModel(value="种植环节作业结果集", description="种植环节作业结果集")
public class WptpPlantInfoVO extends WptpPlantInfo implements Serializable {
    /**生产档案编号*/
    @ApiModelProperty(value = "生产档案编号")
    private java.lang.String blockMedicinalId;
    /**药材品种编号*/
    @ApiModelProperty(value = "药材品种编号")
    private java.lang.String medicinalCode;
    /**所属企业*/
    @ApiModelProperty(value = "所属企业ID")
    private java.lang.String entId;
    /**所属企业*/
    @ApiModelProperty(value = "所属企业名称")
    private java.lang.String entName;
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
}
