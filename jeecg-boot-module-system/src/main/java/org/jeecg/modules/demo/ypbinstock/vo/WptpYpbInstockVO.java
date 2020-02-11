package org.jeecg.modules.demo.ypbinstock.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstock;

import java.io.Serializable;
@Data
@ApiModel(value="饮片经营-饮片入库结果集", description="饮片经营-饮片入库结果集")
public class WptpYpbInstockVO extends WptpYpbInstock implements Serializable {
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
