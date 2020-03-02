package org.jeecg.modules.demo.ypprocess.service;

import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcessFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 饮片加工附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface IWptpYpProcessFileService extends IService<WptpYpProcessFile> {

    public List<WptpYpProcessFile> selectByMainId(String mainId);
}
