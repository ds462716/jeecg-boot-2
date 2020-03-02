package org.jeecg.modules.demo.yppack.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 饮片包装
 * @Author: jeecg-boot
 * @Date: 2019-10-08
 * @Version: V1.0
 */
@Data
@TableName("wptp_yp_pack")
@ApiModel(value = "饮片加工-饮片包装实体类", description = "饮片加工-饮片包装实体类")
public class WptpYpPack implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    /**
     * 创建人名称
     */
    @ApiModelProperty(value = "创建人名称")
    private String createName;
    /**
     * 创建人登录名称
     */
    @ApiModelProperty(value = "创建人登录名称")
    private String createBy;
    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    /**
     * 更新人名称
     */
    @ApiModelProperty(value = "更新人名称")
    private String updateName;
    /**
     * 更新人登录名称
     */
    @ApiModelProperty(value = "更新人登录名称")
    private String updateBy;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateDate;
    /**
     * 主机代码
     */
    @ApiModelProperty(value = "主机代码")
    @NotNull
    @Excel(name = "主机代码", width = 15)

    private String hostCode;
    /**
     * 饮片包装流水号
     */
    @ApiModelProperty(value = "饮片包装流水号")
    @NotNull
    @Excel(name = "饮片包装流水号", width = 15)
    private String packNo;
    /**
     * 包装单号
     */
    @ApiModelProperty(value = "包装单号")
    @NotNull
    @Excel(name = "包装单号", width = 15)
    private String packBatch;
    /**
     * 包装明细序列号
     */
    @ApiModelProperty(value = "包装明细序列号")
    @NotNull
    @Excel(name = "包装明细序列号", width = 15)
    private String packNumber;
    /**
     * 种类编码
     */
    @ApiModelProperty(value = "种类编码")
    @NotNull
    @Excel(name = "种类编码", width = 15)

    private String categoryCode;
    /**
     * 种类名称
     */
    @ApiModelProperty(value = "种类名称")
    @NotNull
    @Excel(name = "种类名称", width = 15)

    private String categoryName;
    /**
     * 生产流水号
     */
    @ApiModelProperty(value = "生产流水号")
    @NotNull
    @Excel(name = "生产流水号", width = 15)
    private String processNo;
    /**
     * 饮片名称
     */
    @ApiModelProperty(value = "饮片名称")
    @NotNull
    @Excel(name = "饮片名称", width = 15)
    private String piecesName;
    /**
     * 产品批号
     */
    @NotNull
    @Excel(name = "产品批号", width = 15)
    @ApiModelProperty(value = "产品批号")
    private String productBatch;
    /**
     * 饮片规格
     */
    @Excel(name = "饮片规格", width = 15)
    @ApiModelProperty(value = "饮片规格")
    private String guige;
    /**
     * 主数据码
     */
    @Excel(name = "主数据码", width = 15)
    @ApiModelProperty(value = "主数据码")
    private String mainCode;
    /**
     * 数量
     */
    @NotNull
    @Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private Double number;
    /**
     * 计量单位
     */
    @Excel(name = "计量单位", width = 15)
    @ApiModelProperty(value = "计量单位")
    private String unit;
    /**
     * 包装规格
     */
    @ApiModelProperty(value = "包装规格")
    @Excel(name = "包装规格", width = 15)
    private String packGuige;
    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志")
    private java.lang.String deleted;
    /**
     * 饮片code
     */
    @ApiModelProperty(value = "饮片编码")
    @Excel(name = "饮片编码", width = 15)
    private String ypCode;
}
