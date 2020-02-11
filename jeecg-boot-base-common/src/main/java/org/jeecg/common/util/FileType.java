package org.jeecg.common.util;

/**
 * 文件类型
 * @author laowang
 */
public enum FileType {
    IMAGE("0","图片"),
    VIDEO("1","视频"),
    PDF("2","pdf");
    private String type;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    FileType(String code,String type) {
        this.type = type;
    }
}
