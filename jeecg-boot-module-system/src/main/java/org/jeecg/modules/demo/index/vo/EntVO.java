package org.jeecg.modules.demo.index.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;

import java.util.Map;

/**
 * 主页企业信息相关
 */
@Data
@ApiModel(value = "主页地图", description = "主页地图")
public class EntVO extends WptpEntInfo {
    @ApiModelProperty(value = "经纬度")
    private String[] gps;
    /**企业名称*/
    /*@ApiModelProperty(value = "企业名称")
    private java.lang.String entName;
    *//**主键*//*
    @ApiModelProperty(value = "企业表主键")
    private java.lang.String id;*/
}
