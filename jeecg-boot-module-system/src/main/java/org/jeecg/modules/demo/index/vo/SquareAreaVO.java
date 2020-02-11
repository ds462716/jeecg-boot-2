package org.jeecg.modules.demo.index.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data@AllArgsConstructor
@ApiModel(value="主页右上角四个正方形", description="主页右上角四个正方形")
public class SquareAreaVO {
    @ApiModelProperty(value = "数量")
    private Integer count;
    @ApiModelProperty(value = "颜色")
    private String  color;
    @ApiModelProperty(value = "名称")
    private String  name;
}
