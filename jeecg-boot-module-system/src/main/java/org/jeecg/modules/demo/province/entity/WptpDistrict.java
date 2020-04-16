package org.jeecg.modules.demo.province.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: wptp_district
 * @Author: jeecg-boot
 * @Date: 2019-10-09
 * @Version: V1.0
 */
@Data
@TableName("wptp_district")
public class WptpDistrict implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 区（县）ID
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private java.lang.String id;
    /**
     * 市ID
     */
    @Excel(name = "市ID", width = 15)
    private java.lang.String cityId;
    /**
     * 区（县）名
     */
    @Excel(name = "区（县）名", width = 15)
    private java.lang.String disName;
    /**
     * admiCode
     */
    @Excel(name = "admiCode", width = 15)
    private java.lang.String admiCode;
}
