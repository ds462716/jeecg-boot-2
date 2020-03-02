package org.jeecg.modules.demo.ypbsale.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.ypbsale.entity.WptpYpbSale;

import java.io.Serializable;

@Data
@ApiModel(value = "饮片经营-饮片销售结果集", description = "饮片经营-饮片销售结果集")
public class WptpYpbSaleVO extends WptpYpbSale implements Serializable {
    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称")
    private java.lang.String entName;
}
