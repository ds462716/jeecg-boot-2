package org.jeecg.modules.demo.ypprocessinstock.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstock;

import java.io.Serializable;
@Data
@ApiModel(value="饮片加工-药材入库结果集", description="饮片加工-药材入库结果集")
public class WptpYpInstockVO extends WptpYpInstock implements Serializable {
    /**药材名称*/
    @ApiModelProperty(value = "药材名称")
    private java.lang.String medicinalName;
    /**药材种类编码*/
    @ApiModelProperty(value = "药材种类编码")
    private java.lang.String medicinalCode;
    /**企业名称*/
    @ApiModelProperty(value = "企业名称")
    private java.lang.String entName;
}
