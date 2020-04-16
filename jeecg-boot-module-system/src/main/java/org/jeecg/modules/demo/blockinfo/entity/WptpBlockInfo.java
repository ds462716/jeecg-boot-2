package org.jeecg.modules.demo.blockinfo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 地块表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Data
@TableName("wptp_block_info")
public class WptpBlockInfo implements Serializable {
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
     * 主机代码
     */
    private java.lang.String hostCode;
    /**
     * 地块编号
     */
    private java.lang.String blockCode;
    /**
     * 所属企业
     */
    private java.lang.String entId;
    /**
     * 经度
     */
    private java.lang.String gpsLongitude;
    /**
     * 纬度
     */
    private java.lang.String gpsLatitude;
    /**
     * 面积
     */
    private java.lang.String baseArea;
    /**
     * 删除标志
     */
    private java.lang.String deleted;
    /**
     * 所属基地
     */
    private java.lang.String baseCode;
}
