package org.jeecg.modules.api.webservice.guild.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TraceCode {
    private String motherTraceCode;//母追溯码
    private List<String> traceCodeList;//子追溯码

}
