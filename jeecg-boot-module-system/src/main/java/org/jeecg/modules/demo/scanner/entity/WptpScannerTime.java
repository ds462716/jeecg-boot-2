package org.jeecg.modules.demo.scanner.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: wptp_scanner_time
 * @Author: jeecg-boot
 * @Date:   2019-12-18
 * @Version: V1.0
 */
@Data
@NoArgsConstructor
@TableName("wptp_scanner_time")
public class WptpScannerTime implements Serializable {
    private static final long serialVersionUID = 1L;
    
	/**id*/
	@TableId(type = IdType.ID_WORKER_STR)
	private java.lang.String id;
	/**traceCode*/
	@Excel(name = "traceCode", width = 15)
	private java.lang.String traceCode;
	/**scannerTime*/
	@Excel(name = "scannerTime", width = 15)
	private Date scannerTime;

	public WptpScannerTime(String traceCode, Date scannerTime) {
		this.traceCode = traceCode;
		this.scannerTime = scannerTime;
	}

}
