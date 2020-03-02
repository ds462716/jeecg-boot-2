package org.jeecg.modules.demo.blockinfo.vo;

import lombok.Data;
import org.jeecg.modules.demo.blockinfo.entity.WptpBlockInfo;

import java.io.Serializable;

@Data
public class WptpBlockInfoVO extends WptpBlockInfo implements Serializable {
    /**
     * 企业名称
     */
    private java.lang.String entName;
    /**上一级企业名称*/
    /* private java.lang.String upperEntName;*/
    /**
     * 基地名称
     */
    private java.lang.String baseName;

}
