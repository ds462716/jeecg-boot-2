package org.jeecg.modules.demo.province.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: wptp_city
 * @Author: jeecg-boot
 * @Date:   2019-10-09
 * @Version: V1.0
 */
@Data
@TableName("wptp_city")
public class WptpCity implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**市ID*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**省份ID*/
	@Excel(name = "省份ID", width = 15)
	private java.lang.String provinceId;
	/**市*/
	@Excel(name = "市", width = 15)
	private java.lang.String cityName;
	/**admiCode*/
	@Excel(name = "admiCode", width = 15)
	private java.lang.String admiCode;
}
