package org.jeecg.modules.demo.blockmedicinal.vo;

import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @Description: 生产档案表
 * @Author: jeecg-boot
 * @Date: 2019-10-09
 * @Version: V1.0
 */
@Data
public class WptpBlockMeidicinalPage {

    /**
     * 主键
     */
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
    @Excel(name = "主机代码", width = 15)
    private java.lang.String hostCode;
    /**
     * 生产档案编号
     */
    @Excel(name = "生产档案编号", width = 15)
    private java.lang.String blockMedicinalId;
    /**
     * 药材品种编号
     */
    @Excel(name = "药材品种编号", width = 15)
    private java.lang.String medicinalCode;
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
     * 地块编号
     */
    @Excel(name = "地块编号", width = 15)
    private java.lang.String blockCode;
    /**
     * 负责人
     */
    @Excel(name = "负责人", width = 15)
    private java.lang.String baseAdmin;
    /**
     * 档案时间
     */
    @Excel(name = "档案时间", width = 15)
    private java.lang.String fileTime;
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
