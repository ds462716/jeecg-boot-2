package org.jeecg.modules.demo.baseinfo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.demo.baseinfo.entity.WptpBaseFile;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @Description: 基地信息管理表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Data
public class WptpBasePage {

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
     * 基地编码
     */
    @Excel(name = "基地编码", width = 15)
    private java.lang.String baseCode;
    /**
     * 基地名称
     */
    @Excel(name = "基地名称", width = 15)
    private java.lang.String baseName;
    /**
     * 省份id
     */
    @Excel(name = "省份id", width = 15)
    private java.lang.String province;
    /**
     * 市id
     */
    @Excel(name = "市id", width = 15)
    private java.lang.String city;
    /**
     * 区（县）id
     */
    @Excel(name = "区（县）id", width = 15)
    private java.lang.String area;
    /**
     * 基地位置
     */
    @Excel(name = "基地位置", width = 15)
    private java.lang.String baseAddress;
    /**
     * 经度
     */
    @Excel(name = "经度", width = 15)
    private java.lang.String gpsLongitude;
    /**
     * 纬度
     */
    @Excel(name = "纬度", width = 15)
    private java.lang.String gpsLatitude;
    /**
     * 基地面积
     */
    @Excel(name = "基地面积", width = 15)
    private java.lang.String baseArea;
    /**
     * 地形
     */
    @Excel(name = "地形", width = 15)
    private java.lang.String land;
    /**
     * 海拔
     */
    @Excel(name = "海拔", width = 15)
    private java.lang.String heigh;
    /**
     * 土壤类型
     */
    @Excel(name = "土壤类型", width = 15)
    private java.lang.String soilType;
    /**
     * 酸碱度
     */
    @Excel(name = "酸碱度", width = 15)
    private java.lang.String ph;
    /**
     * 气候类型
     */
    @Excel(name = "气候类型", width = 15)
    private java.lang.String weatherType;
    /**
     * 年降雨量
     */
    @Excel(name = "年降雨量", width = 15)
    private java.lang.String rearRainfall;
    /**
     * 年平均温度
     */
    @Excel(name = "年平均温度", width = 15)
    private java.lang.String temperature;
    /**
     * 水源
     */
    @Excel(name = "水源", width = 15)
    private java.lang.String waterSrc;
    /**
     * 企业编号
     */
    @Excel(name = "企业编号", width = 15)
    private java.lang.String entId;
    /**
     * 所属基地公司
     */
    @Excel(name = "所属基地公司", width = 15)
    private java.lang.String baseEnt;
    /**
     * 工作流id
     */
    private java.lang.String flowId;
    /**
     * 审核状态
     */
    private java.lang.String auditStatus;
    /**
     * 删除状态
     */
    private java.lang.String deleted;

    /*	@ExcelCollection(name="基地信息附表")*/
    private List<WptpBaseFile> wptpBaseFileList;

}
