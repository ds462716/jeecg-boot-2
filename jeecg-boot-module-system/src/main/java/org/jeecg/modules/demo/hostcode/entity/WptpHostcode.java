package org.jeecg.modules.demo.hostcode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 主机代码表
 * @Author: jeecg-boot
 * @Date: 2019-10-09
 * @Version: V1.0
 */
@Data
@TableName("wptp_hostcode")
public class WptpHostcode implements Serializable {
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
     * 企业id
     */
    @Excel(name = "企业id", width = 20)
    private java.lang.String entId;
    /**
     * 主机代码
     */
    @Excel(name = "主机代码", width = 15)
    private java.lang.String hostCode;
    /**
     * 备注
     */
    @Excel(name = "备注", width = 15)
    private java.lang.String remark;
}
