package org.jeecg.modules.demo.trace.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.demo.index.vo.MedicinalVO;

import java.util.List;

/**
 * 追溯展示类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "追溯结果集", description = "追溯结果集")
public class TraceVO {
    /**
     * 种植环节
     */
    @ApiModelProperty(value = "种植环节")
    private PlantTraceVO plantTraceVO;

    /**
     * 药材经营
     */
    @ApiModelProperty(value = "药材经营")
    private MedicineTraceVO medicineVO;

    /**
     * 饮片加工
     */
    @ApiModelProperty(value = "饮片加工")
    private YpProcessTraceVO ypProcessVO;

    /**
     * 饮片经营
     */
    @ApiModelProperty(value = "饮片经营")
    private List<YpBusinessTraceVO> ypBusinessTraceVOList;

    /**
     * 药材图片
     */

    @ApiModelProperty(value = "药材图片")
    private MedicinalVO medicinalImageVO;
}
