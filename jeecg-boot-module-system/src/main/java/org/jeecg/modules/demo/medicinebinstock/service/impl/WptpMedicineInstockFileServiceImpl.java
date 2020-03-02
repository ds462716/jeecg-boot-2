package org.jeecg.modules.demo.medicinebinstock.service.impl;

import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstockFile;
import org.jeecg.modules.demo.medicinebinstock.mapper.WptpMedicineInstockFileMapper;
import org.jeecg.modules.demo.medicinebinstock.service.IWptpMedicineInstockFileService;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 药材入库附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpMedicineInstockFileServiceImpl extends ServiceImpl<WptpMedicineInstockFileMapper, WptpMedicineInstockFile> implements IWptpMedicineInstockFileService {

    @Autowired
    private WptpMedicineInstockFileMapper wptpMedicineInstockFileMapper;

    @Override
    public List<WptpMedicineInstockFile> selectByMainId(String mainId) {
        return wptpMedicineInstockFileMapper.selectByMainId(mainId);
    }
}
