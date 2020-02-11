package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

/**
 * 仓库养护
 */
@Data
public class Curing {
    private String curingId;//仓库养护id
    private String expectedResults;//养护措施
    private String image;//图片
    private long operationEndTime;//养护结束时间
    private long operationTime;//养护开始时间
    private String processingMethod;//操作方法
    private String remarks;//备注信息

}
