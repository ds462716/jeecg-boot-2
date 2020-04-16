package org.jeecg.modules.demo.uploadxh.entity;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;

/**
 * @Description: 上传日志表
 * @Author: jeecg-boot
 * @Date: 2020-02-15
 * @Version: V1.0
 */
@Data
@TableName("wptp_upload_record")
@NoArgsConstructor
@ApiModel(value = "上传记录实体类", description = "上传记录实体类")
public class WptpUploadRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 上传时间
     */
    @Excel(name = "上传时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "上传时间")
    private java.util.Date uploadTime;
    /**
     * 上传结果
     */
    @Excel(name = "上传结果", width = 15)
    @ApiModelProperty(value = "上传结果")
    private java.lang.String result;
    /**
     * 结果描述
     */
    @Excel(name = "结果描述", width = 15)
    @ApiModelProperty(value = "结果描述")
    private java.lang.String resultDesc;
    /**
     * 追溯码
     */
    @Excel(name = "追溯码", width = 15)
    @ApiModelProperty(value = "追溯码")
    private java.lang.String traceCode;
    /**
     * 参数内容
     */
    @Excel(name = "参数内容", width = 15)
    @ApiModelProperty(value = "参数内容")
    private java.lang.String param;
    /**
     * 环节
     */
    @Excel(name = "环节", width = 15)
    @ApiModelProperty(value = "环节")
    private java.lang.String link;

    public WptpUploadRecord(Date uploadTime, String result, String resultDesc, String traceCode, String param, String link) {
        this.uploadTime = uploadTime;
        this.result = result;
        this.resultDesc = resultDesc;
        this.traceCode = traceCode;
        this.param = param;
        this.link = link;
    }
}
