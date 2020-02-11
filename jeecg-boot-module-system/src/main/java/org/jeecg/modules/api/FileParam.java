package org.jeecg.modules.api;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 上传文件相关的参数
 * @author laowang
 */
@Data
public class FileParam {
    @NotNull
    private String hostCode;//主机代码
    @NotNull
    private String mainId;//所属环节唯一标识，如饮片药材入库流水号

    private List<FileArray> file;//文件参数类
    @NotNull
    private String flag;//所属环节：饮片加工药材入库：20；饮片加工：21；饮片成品包装：22；饮片销售：23
}
