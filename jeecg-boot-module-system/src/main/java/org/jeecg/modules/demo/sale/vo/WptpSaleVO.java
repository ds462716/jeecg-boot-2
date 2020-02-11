package org.jeecg.modules.demo.sale.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.sale.entity.WptpSale;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value="种植环节销售结果集", description="种植环节销售结果集")
public class WptpSaleVO extends WptpSale implements Serializable {
    /**
     * 初加工的图片路径集合
     */
    @ApiModelProperty(value = "初加工的图片路径集合")
    private List<String > report;
    /**药材种类编码*/
    @ApiModelProperty(value = "药材种类编码")
    private java.lang.String medicinalCode;
    /**企业名称*/
    @ApiModelProperty(value = "企业名称")
    private java.lang.String entName;
}
