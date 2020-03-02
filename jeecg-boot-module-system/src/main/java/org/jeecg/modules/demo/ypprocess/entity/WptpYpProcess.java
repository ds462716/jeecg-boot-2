package org.jeecg.modules.demo.ypprocess.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description: 饮片加工表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Data
@TableName("wptp_yp_process")
@ApiModel(value = "饮片加工-饮片加工实体类", description = "饮片加工-饮片加工实体类")
public class WptpYpProcess implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ID_WORKER_STR)
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
     * 主机代码
     */
    @NotNull
    @ApiModelProperty(value = "主机代码")
    private java.lang.String hostCode;
    /**
     * 生产流水号
     */
    @NotNull
    @ApiModelProperty(value = "生产流水号")
    private java.lang.String processNo;
    /**
     * 饮片品名
     */
    @NotNull
    @ApiModelProperty(value = "饮片品名")
    private java.lang.String ypName;
    /**
     * 饮片规格
     */
    @ApiModelProperty(value = "饮片规格")
    private java.lang.String ypGuige;
    /**
     * 种类编码
     */
    @NotNull
    @ApiModelProperty(value = "种类编码")
    private java.lang.String categoryCode;
    /**
     * 种类名称
     */
    @NotNull
    @ApiModelProperty(value = "种类名称")
    private java.lang.String categoryName;
    /**
     * 主数据码
     */
    @ApiModelProperty(value = "主数据码")
    private java.lang.String mainCode;
    /**
     * 生产批号
     */
    @NotNull
    @ApiModelProperty(value = "生产批号")
    private java.lang.String productBatch;
    /**
     * 原料来源
     */
    @ApiModelProperty(value = "原料来源")
    private java.lang.String source;
    /**
     * 原生产流水号
     */
    @ApiModelProperty(value = "原生产流水号")
    private java.lang.String oriProcessNo;
    /**
     * 药材入库流水号
     */
    @ApiModelProperty(value = "药材入库流水号")
    @NotNull
    private java.lang.String instockNo;
    /**
     * 原料数量
     */
    @ApiModelProperty(value = "原料数量")
    @NotNull
    private java.lang.Double materialNum;
    /**
     * 原料内部批号
     */
    @ApiModelProperty(value = "原料内部批号")
    @NotNull
    private java.lang.String instockNumber;
    /**
     * 成品数量
     */
    @ApiModelProperty(value = "成品数量")
    @NotNull
    private java.lang.Double productNum;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private java.lang.String unit;
    /**
     * 生产日期
     */
    @ApiModelProperty(value = "生产日期")
    @NotNull
    private java.lang.String producedDate;
    /**
     * 有效日期
     */
    @ApiModelProperty(value = "有效日期")
    @NotNull
    private java.lang.String expiredDate;
    /**
     * 加工地点
     */
    @ApiModelProperty(value = "加工地点")
    @NotNull
    private java.lang.String processPlace;
    /**
     * 负责人
     */
    @ApiModelProperty(value = "负责人")
    @NotNull
    private java.lang.String processResponsible;
    /**
     * 入库时间
     */
    @ApiModelProperty(value = "入库时间")
    @NotNull
    private java.lang.String inTime;
    /**
     * 质检单号
     */
    @ApiModelProperty(value = "质检单号")
    @NotNull
    private java.lang.String qualCheckNum;
    /**
     * 检品名称
     */
    @ApiModelProperty(value = "检品名称")
    @NotNull
    private java.lang.String checkName;
    /**
     * 检验目的
     */
    @ApiModelProperty(value = "检验目的")
    private java.lang.String checkPurpose;
    /**
     * 检验项目及结论
     */
    @ApiModelProperty(value = "检验项目及结论")
    @NotNull
    private java.lang.String checkItem;
    /**
     * 检测依据
     */
    @ApiModelProperty(value = "检测依据")
    @NotNull
    private java.lang.String checkBasis;
    /**
     * 检测结果
     */
    @ApiModelProperty(value = "检测结果")
    @NotNull
    private java.lang.String checkResult;
    /**
     * 质检负责人
     */
    @ApiModelProperty(value = "质检负责人")
    @NotNull
    private java.lang.String checkResponsible;
    /**
     * 审核标志
     */
    @ApiModelProperty(value = "审核标志")
    private java.lang.String auditStatus;
    /**
     * 工作流
     */
    @ApiModelProperty(value = "工作流")
    private java.lang.String flowId;
    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志")
    private java.lang.String deleted;
    /**
     * 检测时间
     */
    @ApiModelProperty(value = "检测时间")
    private java.lang.String checkTime;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    private java.lang.String punit;
    /**
     * 生产工艺
     */
    @ApiModelProperty(value = "生产工艺")
    private java.lang.String process;
    /**
     * 储存环境
     */
    @ApiModelProperty(value = "储存环境")
    @NotNull
    private java.lang.String enviornment;
    /**
     * 养护方法
     */
    @NotNull
    @ApiModelProperty(value = "养护方法")
    private java.lang.String method;
    /**
     * 饮片code
     */
    @ApiModelProperty(value = "饮片编码")
    private String ypCode;
}
