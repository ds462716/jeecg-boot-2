package org.jeecg.modules.demo.uploadxh.service;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.uploadxh.entity.WptpUploadRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 上传日志表
 * @Author: jeecg-boot
 * @Date:   2020-02-15
 * @Version: V1.0
 */
public interface IWptpUploadRecordService extends IService<WptpUploadRecord> {
    public int addWptpUploadRecord( WptpUploadRecord uploadRecord);
}
