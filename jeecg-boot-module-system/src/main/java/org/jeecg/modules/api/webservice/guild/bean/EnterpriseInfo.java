package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

@Data
public class EnterpriseInfo {
    private String enterpriseAddr;//企业地址
    private String enterpriseCerCode;//企业证件号
    private String enterpriseCode;//企业编号
    private String enterpriseId;//企业id
    private String enterpriseName;//企业名称
    private long latitude;//企业纬度
    private long longitude;//企业经度
}
