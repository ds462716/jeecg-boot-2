package org.jeecg.modules.demo.ypprocess.entity;

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
 * @Description: 饮片加工附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Data
@NoArgsConstructor
@TableName("wptp_yp_process_file")
@ApiModel(value = "饮片加工-饮片加工文件", description = "饮片加工-饮片加工文件")
public class WptpYpProcessFile implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**
     * 创建人名称
     */
    @ApiModelProperty(value = "创建人名称")
    private java.lang.String createName;
    /**
     * 创建人登录名称
     */
    @ApiModelProperty(value = "创建人登录名称")
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date createDate;
    /**
     * 更新人名称
     */
    @ApiModelProperty(value = "更新人名称")
    private java.lang.String updateName;
    /**
     * 更新人登录名称
     */
    @ApiModelProperty(value = "更新人登录名称")
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date updateDate;
    /**
     * 生产流水号
     */
    @ApiModelProperty(value = "生产流水号")
    private java.lang.String mainId;
    /**
     * 路径
     */
    @ApiModelProperty(value = "路径")
    @Excel(name = "路径", width = 15)
    private java.lang.String path;
    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    @Excel(name = "类型", width = 15)
    private java.lang.String type;
    /**
     * 删除标志
     */
    @Excel(name = "删除标志", width = 15)
    private java.lang.String deleted;
    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    @Excel(name = "文件类型", width = 15)
    private java.lang.String fileType;

    public WptpYpProcessFile(String mainId, String path, String type, String fileType, Date createDate, String deleted, String createBy) {
        this.path = path;
        this.type = type;
        this.fileType = fileType;
        this.createDate = createDate;
        this.mainId = mainId;
        this.deleted = deleted;
        this.createBy = createBy;
    }

}
