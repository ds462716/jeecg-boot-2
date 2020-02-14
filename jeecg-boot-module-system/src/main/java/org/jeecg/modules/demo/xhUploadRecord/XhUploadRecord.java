package org.jeecg.modules.demo.xhUploadRecord;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName("xh_upload_record")
public class XhUploadRecord {
    private Date uploadTime;//上传时间
    private String result;//结果
    private String resultDesc;//结果描述
    private String traceCode;//追溯码
    private String param;
    public XhUploadRecord(Date uploadTime, String result, String resultDesc, String traceCode,String param) {
        this.uploadTime = uploadTime;
        this.result = result;
        this.resultDesc = resultDesc;
        this.traceCode = traceCode;
        this.param=param;
    }
}
