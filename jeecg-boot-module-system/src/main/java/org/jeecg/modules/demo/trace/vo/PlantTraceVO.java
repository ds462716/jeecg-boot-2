package org.jeecg.modules.demo.trace.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.baseinfo.vo.WptpBaseVO;
import org.jeecg.modules.demo.blockmedicinal.vo.WptpBlockMeidicinalVO;
import org.jeecg.modules.demo.csinfo.vo.WptpCsInfoVO;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantFile;
import org.jeecg.modules.demo.plantinfo.vo.WptpPlantInfoVO;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.vo.WptpProcessInfoVO;
import org.jeecg.modules.demo.sale.vo.WptpSaleVO;

import java.util.List;

/**
 * 种植追溯VO
 */
@Data
@ApiModel(value="种植追溯结果集", description="种植追溯结果集")
public class PlantTraceVO {
    @ApiModelProperty(value = "采收批次")
    private List<WptpCsInfoVO> wptpCsInfoList;
    @ApiModelProperty(value = "田间作业")
    private List<WptpPlantInfoVO> wptpPlantInfoVOList;
    @ApiModelProperty(value = "田间作业文件")
    List<WptpPlantFile> wptpPlantFileList;
    @ApiModelProperty(value = "出厂销售")
    private WptpSaleVO wptpSale;
    @ApiModelProperty(value = "初加工")
    private WptpProcessInfoVO wptpProcessInfoVO;
    @ApiModelProperty(value = "初加工文件附表")
    private List<WptpProcessFile> wptpProcessFileList;
    @ApiModelProperty(value = "初加工原料附表")
    private List<WptpProcessMaterial> wptpProcessMaterialList;
    @ApiModelProperty(value = "档案")
    private WptpBlockMeidicinalVO wptpBlockMeidicinalVO;
    @ApiModelProperty(value = "基地")
    private WptpBaseVO wptpBaseVO;
/*    @ApiModelProperty(value = "基地图片")
    private List<WptpBaseFile> wptpBaseFilelist;*/
}
