package org.jeecg.modules.demo.entinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
@Data
@ApiModel(value="企业信息视图类", description="企业信息视图类")
public class WptpEntInfoVO extends WptpEntInfo implements Serializable {
    /**省份名*/
    @Excel(name = "省份名", width = 15)
    @ApiModelProperty(value = "省份名")
    private java.lang.String ProvinceName;
    /**市*/
    @Excel(name = "市", width = 15)
    @ApiModelProperty(value = "市")
    private java.lang.String cityName;
    /**区（县）名*/
    @Excel(name = "区（县）名", width = 15)
    @ApiModelProperty(value = "区（县）名")
    private java.lang.String districtName;
}
