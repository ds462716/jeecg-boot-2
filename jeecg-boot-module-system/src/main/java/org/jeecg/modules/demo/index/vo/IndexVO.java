package org.jeecg.modules.demo.index.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "主页展示信息", description = "主页展示信息")
public class IndexVO {
    /**
     * 企业分布
     */
    @ApiModelProperty(value = "企业信息")
    private List<EntVO> entList;
    /**
     * 整体状况
     */
    @ApiModelProperty(value = "整体状况")
    private List<SquareAreaVO> squareAreaVOList;
    /**
     * 追溯码数量
     */
    @ApiModelProperty(value = "环节")
    private String[] medicinalVarieties;
    @ApiModelProperty(value = "环节对应的追溯码的数量")
    private Integer[] traceCodeCount;
    /**
     * 药材图片
     */
    @ApiModelProperty(value = "药材相关")
    private List<MedicinalVO> medicinalList;


}

