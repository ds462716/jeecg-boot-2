package org.jeecg.modules.demo.uploadxh.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.uploadxh.entity.WptpUploadRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 上传日志表
 * @Author: jeecg-boot
 * @Date: 2020-02-15
 * @Version: V1.0
 */
public interface WptpUploadRecordMapper extends BaseMapper<WptpUploadRecord> {
    public int addWptpUploadRecord(@Param("uploadRecord") WptpUploadRecord uploadRecord);
}
