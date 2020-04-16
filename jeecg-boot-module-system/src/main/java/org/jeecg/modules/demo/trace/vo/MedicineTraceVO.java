package org.jeecg.modules.demo.trace.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstockFile;
import org.jeecg.modules.demo.medicinebinstock.vo.WptpMedicineInstockVO;
import org.jeecg.modules.demo.medicinebsale.vo.WptpMedicineSaleVO;

import java.util.List;

/**
 * 药材经营的追溯VO
 */
@Data
@ApiModel(value = "药材经营追溯结果集", description = "药材经营追溯结果集")
public class MedicineTraceVO {
    @ApiModelProperty(value = "药材经营-药材入库")
    private WptpMedicineInstockVO wptpMedicineInstockVO;
    @ApiModelProperty(value = "药材经营-药材入库文件")
    List<WptpMedicineInstockFile> wptpMedicineInstockFileList;
    @ApiModelProperty(value = "药材经营-药材销售")
    private WptpMedicineSaleVO wptpMedicineSaleVO;
}
