package org.jeecg.modules.demo.plantinfo.service;

import org.jeecg.modules.demo.plantinfo.entity.WptpPlantFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 田间作业附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface IWptpPlantFileService extends IService<WptpPlantFile> {

    public List<WptpPlantFile> selectByMainId(String mainId);
}
