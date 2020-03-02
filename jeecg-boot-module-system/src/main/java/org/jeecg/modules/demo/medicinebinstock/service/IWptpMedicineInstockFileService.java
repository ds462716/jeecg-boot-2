package org.jeecg.modules.demo.medicinebinstock.service;

import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstockFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 药材入库附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface IWptpMedicineInstockFileService extends IService<WptpMedicineInstockFile> {

    public List<WptpMedicineInstockFile> selectByMainId(String mainId);
}
