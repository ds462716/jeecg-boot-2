package org.jeecg.modules.demo.baseinfo.service;

import org.jeecg.modules.demo.baseinfo.entity.WptpBaseFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 基地信息附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface IWptpBaseFileService extends IService<WptpBaseFile> {

    public List<WptpBaseFile> selectByMainId(String mainId);

    public boolean deleteByPath(String path);
}
