package org.jeecg.modules.demo.ypprocesssale.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSale;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "饮片加工-饮片销售结果集", description = "饮片加工-饮片销售结果集")
public class WptpYpSaleVO extends WptpYpSale implements Serializable {
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
    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    private java.lang.String entName;
    /**
     * 饮片加工的图片路径
     */
    @ApiModelProperty(value = "饮片加工的图片路径")
    private List<String> report;
}
