package org.jeecg.modules.demo.medicinebinstock.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 药材入库附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Data
@NoArgsConstructor
@TableName("wptp_medicine_instock_file")
@ApiModel(value = "药材经营-药材入库文件", description = "药材经营-药材入库文件")
public class WptpMedicineInstockFile implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
    /**
     * 药材入库流水号
     */
    @ApiModelProperty(value = "药材入库流水号")
    private java.lang.String mainId;
    /**
     * 路径
     */
    @ApiModelProperty(value = "路径")
    @Excel(name = "路径", width = 15)
    private java.lang.String path;
    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志")
    private java.lang.String deleted;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    @Excel(name = "类型", width = 15)
    private java.lang.String type;
    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    @Excel(name = "文件类型", width = 15)
    private java.lang.String fileType;

    public WptpMedicineInstockFile(String mainId, String path, String type, String fileType, Date createTime, String deleted, String createBy) {
        this.path = path;
        this.type = type;
        this.fileType = fileType;
        this.createTime = createTime;
        this.mainId = mainId;
        this.deleted = deleted;
        this.createBy = createBy;
    }
}
