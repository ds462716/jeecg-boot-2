package org.jeecg.modules.demo.entinfo.service;

import org.jeecg.modules.demo.entinfo.entity.WptpEntFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 企业信息附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface IWptpEntFileService extends IService<WptpEntFile> {

    public List<WptpEntFile> selectByMainId(String mainId);

    public boolean deleteByPath(String path);
}
