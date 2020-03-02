package org.jeecg.modules.demo.trace.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.yppack.vo.WptpYpPackVO;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcessFile;
import org.jeecg.modules.demo.ypprocess.vo.WptpYpProcessVO;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstockFile;
import org.jeecg.modules.demo.ypprocessinstock.vo.WptpYpInstockVO;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSaleFile;
import org.jeecg.modules.demo.ypprocesssale.vo.WptpYpSaleVO;

import java.util.List;

/**
 * 饮片加工的追溯VO
 */
@Data
@ApiModel(value = "饮片加工追溯结果集", description = "饮片加工追溯结果集")
public class YpProcessTraceVO {
    @ApiModelProperty(value = "饮片加工-药材入库")
    private WptpYpInstockVO wptpYpInstockVO;
    @ApiModelProperty(value = "饮片加工-药材入库文件")
    List<WptpYpInstockFile> wptpYpInstockFileList;
    @ApiModelProperty(value = "饮片加工-饮片加工")
    private WptpYpProcessVO wptpYpProcessVO;
    @ApiModelProperty(value = "饮片加工-饮片加工文件")
    List<WptpYpProcessFile> wptpYpProcessFileList;
    @ApiModelProperty(value = "饮片加工-成品包装")
    private WptpYpPackVO wptpYpPackVO;
    @ApiModelProperty(value = "饮片加工-饮片销售")
    private WptpYpSaleVO wptpYpSaleVO;
    @ApiModelProperty(value = "饮片加工-饮片销售文件")
    List<WptpYpSaleFile> wptpYpSaleFileList;
}
