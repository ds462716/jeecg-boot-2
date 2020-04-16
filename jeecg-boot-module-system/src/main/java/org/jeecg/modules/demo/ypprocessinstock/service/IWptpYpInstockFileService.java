package org.jeecg.modules.demo.ypprocessinstock.service;

import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstockFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 饮片入库附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface IWptpYpInstockFileService extends IService<WptpYpInstockFile> {

    public List<WptpYpInstockFile> selectByMainId(String mainId);
}
