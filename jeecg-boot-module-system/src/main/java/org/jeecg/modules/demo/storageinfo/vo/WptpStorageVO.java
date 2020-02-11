package org.jeecg.modules.demo.storageinfo.vo;

import lombok.Data;
import org.jeecg.modules.demo.storageinfo.entity.WptpStorage;

import java.io.Serializable;
@Data
public class WptpStorageVO extends WptpStorage  implements Serializable {
    /**省份名*/
    private java.lang.String ProvinceName;
    /**市*/
    private java.lang.String cityName;
    /**区（县）名*/
    private java.lang.String districtName;
    /**企业名称*/
    private java.lang.String entName;
 /*   *//**上一级企业名称*//*
    private java.lang.String upperEntName;*/
    /**基地名称*/
    private java.lang.String baseName;
}
