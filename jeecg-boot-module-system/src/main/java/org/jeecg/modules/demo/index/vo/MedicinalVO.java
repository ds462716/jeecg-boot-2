package org.jeecg.modules.demo.index.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;

@Data@AllArgsConstructor@NoArgsConstructor
@ApiModel(value="主页下方药材图片", description="主页下方药材图片")
public class MedicinalVO extends WptpMedicinal {
    @ApiModelProperty(value = "药材图片")
    private String medicinalImages;
 /*   @ApiModelProperty(value = "药材id")
    private String medicinalId;*/
}
