package org.jeecg.modules.api.webservice.guild.bean;

import lombok.Data;

@Data
public class FarmScheme {
    private String farmOperationTypeName;//农事操作
    private String farmSchemeId;//农事id
    private String farmingContent;//农事内容
    private String file;//附件,多张用竖线(|)隔开，不超10份
    private String  image;//图片,多张用竖线(|)隔开，不超10份
    private String inputsDevices;//投入设备
    private String inputsProduct;//投入品
    private String remarks;//备注信息
    private String responsiblePerson;//负责人
    private long endOperateTime;//操作结束时间
    private long operateTime;//操作开始时间
}
