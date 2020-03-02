package org.jeecg.modules.demo.trace.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstockFile;
import org.jeecg.modules.demo.ypbinstock.vo.WptpYpbInstockVO;
import org.jeecg.modules.demo.ypbsale.vo.WptpYpbSaleVO;

import java.util.List;

/**
 * 饮片经营的追溯VO
 */
@Data
@ApiModel(value = "饮片经营追溯结果集", description = "饮片经营追溯结果集")
public class YpBusinessTraceVO {
    @ApiModelProperty(value = "饮片经营-饮片入库")
    private WptpYpbInstockVO wptpYpbInstockVO;
    @ApiModelProperty(value = "饮片经营-饮片入库文件")
    List<WptpYpbInstockFile> wptpYpbInstockFileList;
    @ApiModelProperty(value = "饮片经营-饮片销售")
    private WptpYpbSaleVO wptpYpbSaleVO;
}
