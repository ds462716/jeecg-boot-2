package org.jeecg.modules.demo.processinfo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessInfo;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessMaterial;

import java.io.Serializable;
import java.util.List;
@Data
@ApiModel(value="种植环节初加工结果集", description="种植环节初加工结果集")
public class WptpProcessInfoVO extends WptpProcessInfo implements Serializable {
    /**
     * 初加工原料表
     */
    @ApiModelProperty(value = "初加工原料表")
    private List<WptpProcessMaterial> batch;

}
