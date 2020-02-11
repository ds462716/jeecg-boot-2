package org.jeecg.modules.api;

import lombok.Data;

/**
 * 文件参数
 * @author laowang
 */
@Data
public class FileArray {
    private String path;//文件路径
    private String type;//0:基地图片，加工图片1：检测报告
    private String fileType;//0:图片；1：视频
}
