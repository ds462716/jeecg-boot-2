package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

@Data
public class ProcessProcedure {
    private String environmentalEnd;//工序环境范围结束
    private String environmentalStart;//工序环境范围起始
    private String name;//工序名称
    private String procedureId;//加工工序Id
    private long procedureEndTime;//工序结束时间
    private long procedureStartTime;//工序开始时间
    private String processClean;//工序后清场
    private String processInspect;//工序前检查
    private String remarks;//备注信息
    private String requirement;//工序要求
    private String workMethod;//工序方法
    private String yieldRate;//出成率(%)

}
