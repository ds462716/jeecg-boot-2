package org.jeecg.modules.demo.uploadxh.service.impl;

import org.jeecg.modules.demo.uploadxh.entity.WptpUploadRecord;
import org.jeecg.modules.demo.uploadxh.mapper.WptpUploadRecordMapper;
import org.jeecg.modules.demo.uploadxh.service.IWptpUploadRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 上传日志表
 * @Author: jeecg-boot
 * @Date:   2020-02-15
 * @Version: V1.0
 */
@Service
public class WptpUploadRecordServiceImpl extends ServiceImpl<WptpUploadRecordMapper, WptpUploadRecord> implements IWptpUploadRecordService {
    @Autowired
    private WptpUploadRecordMapper wptpUploadRecordMappe;
    @Override
    public int addWptpUploadRecord(WptpUploadRecord uploadRecord) {
        return wptpUploadRecordMappe.addWptpUploadRecord(uploadRecord);
    }
}
