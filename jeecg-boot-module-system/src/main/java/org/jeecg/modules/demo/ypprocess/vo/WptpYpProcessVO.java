package org.jeecg.modules.demo.ypprocess.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcess;

import java.io.Serializable;

@Data
@ApiModel(value = "饮片加工-饮片加工结果集", description = "饮片加工-饮片加工结果集")
public class WptpYpProcessVO extends WptpYpProcess implements Serializable {
    /**
     * 药材名称
     */
    @ApiModelProperty(value = "药材名称")
    private java.lang.String medicinalName;
    /**
     * 药材种类编码
     */
    @ApiModelProperty(value = "药材种类编码")
    private java.lang.String medicinalCode;
}
