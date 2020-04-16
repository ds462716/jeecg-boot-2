package org.jeecg.modules.demo.entinfo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.jeecg.modules.demo.entinfo.entity.WptpEntFile;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

/**
 * @Description: 企业基本信息
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Data
public class WptpEntInfoPage {

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
     * 企业编码
     */
    @Excel(name = "企业编码", width = 15)
    private java.lang.String entId;
    /**
     * 企业类别
     */
    @Excel(name = "企业类别", width = 15)
    private java.lang.String entType;
    /**
     * 组织机构代码
     */
    @Excel(name = "组织机构代码", width = 15)
    private java.lang.String orgCode;
    /**
     * 法人
     */
    @Excel(name = "法人", width = 15)
    private java.lang.String entPrincipal;
    /**
     * 授权人
     */
    @Excel(name = "授权人", width = 15)
    private java.lang.String authorize;
    /**
     * 联系电话
     */
    @Excel(name = "联系电话", width = 15)
    private java.lang.String phone;
    /**
     * 注册资金
     */
    @Excel(name = "注册资金", width = 15)
    private java.lang.String regCapi;
    /**
     * 工商注册号
     */
    @Excel(name = "工商注册号", width = 15)
    private java.lang.String busRegisNum;
    /**
     * 企业地址
     */
    @Excel(name = "企业地址", width = 15)
    private java.lang.String entAdd;
    /**
     * 成立时间
     */
    @Excel(name = "成立时间", width = 15)
    private java.lang.String establiTime;
    /**
     * 企业网址
     */
    @Excel(name = "企业网址", width = 15)
    private java.lang.String entWebsite;
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
     * 区id
     */
    @Excel(name = "区id", width = 15)
    private java.lang.String area;
    /**
     * 地址
     */
    @Excel(name = "地址", width = 15)
    private java.lang.String address;
    /**
     * 主体介绍
     */
    @Excel(name = "主体介绍", width = 15)
    private java.lang.String mainIntro;
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
     * 删除标志
     */
    private java.lang.String deleted;
    /**
     * 审核状态
     */
    private java.lang.String auditStatus;
    /**
     * 工作流id
     */
    private java.lang.String flowId;
    /**
     * 企业名称
     */
    @Excel(name = "企业名称", width = 15)
    private java.lang.String entName;
    /**
     * 协会编码
     */
    @Excel(name = "协会编码", width = 20)
    private java.lang.String xhCode;
    /*	@ExcelCollection(name="企业信息附表")*/
    private List<WptpEntFile> wptpEntFileList;

}
