package org.jeecg.modules.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 作废接口的参数类
 * @author laowang
 */
@Data
public class TovoidParam {
    @NotBlank
    private String hostCode;//主机代码
    @NotBlank
    private String sourceNum;//所属环节唯一标识，如饮片药材入库流水号
    @NotBlank
    private String source;//所属环节
    @NotBlank
    private String flag;//作废：2
}
