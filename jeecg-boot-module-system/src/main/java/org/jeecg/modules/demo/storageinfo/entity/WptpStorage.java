package org.jeecg.modules.demo.storageinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 仓库信息表
 * @Author: jeecg-boot
 * @Date: 2019-10-09
 * @Version: V1.0
 */
@Data
@TableName("wptp_storage")
public class WptpStorage implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
    /**
     * 创建人
     */
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
    /**
     * 仓库编码
     */
    @Excel(name = "仓库编码", width = 15)
    private java.lang.String storeCode;
    /**
     * 仓库名称
     */
    @Excel(name = "仓库名称", width = 15)
    private java.lang.String name;
    /**
     * 省
     */
    @Excel(name = "省", width = 15)
    private java.lang.String province;
    /**
     * 市
     */
    @Excel(name = "市", width = 15)
    private java.lang.String city;
    /**
     * 区
     */
    @Excel(name = "区", width = 15)
    private java.lang.String district;
    /**
     * 地址
     */
    @Excel(name = "地址", width = 15)
    private java.lang.String address;
    /**
     * gps经度
     */
    @Excel(name = "gps经度", width = 15)
    private java.lang.String gpsLongitude;
    /**
     * gps纬度
     */
    @Excel(name = "gps纬度", width = 15)
    private java.lang.String gpsLatitude;
    /**
     * 所属企业
     */
    @Excel(name = "所属企业", width = 15)
    private java.lang.String entId;
    /**
     * 所属基地
     */
    @Excel(name = "所属基地", width = 15)
    private java.lang.String baseCode;
    /**
     * 审核标志
     */
    private java.lang.String auditStatus;
    /**
     * 删除标志
     */
    private java.lang.String deleted;
    /**
     * 工作流
     */
    private java.lang.String flowId;
}
