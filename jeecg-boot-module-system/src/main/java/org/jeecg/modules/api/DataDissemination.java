package org.jeecg.modules.api;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DataDissemination {
    @NotBlank
    private String hostCode;//主机代码
    /**
     * 05：采收批次信息
     * 04：种植端出厂销售;
     * 11: 药材经营出厂销售;
     * 23: 饮片加工饮片销售;
     * 31:饮片经营饮片销售;
     */
    @NotBlank
    private String source;
}
