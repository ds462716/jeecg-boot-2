package org.jeecg.modules.demo.medicinebinstock.service.impl;

import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstock;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstockFile;
import org.jeecg.modules.demo.medicinebinstock.mapper.WptpMedicineInstockFileMapper;
import org.jeecg.modules.demo.medicinebinstock.mapper.WptpMedicineInstockMapper;
import org.jeecg.modules.demo.medicinebinstock.service.IWptpMedicineInstockService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 药材经营药材入库表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpMedicineInstockServiceImpl extends ServiceImpl<WptpMedicineInstockMapper, WptpMedicineInstock> implements IWptpMedicineInstockService {

    @Autowired
    private WptpMedicineInstockMapper wptpMedicineInstockMapper;
    @Autowired
    private WptpMedicineInstockFileMapper wptpMedicineInstockFileMapper;

    @Override
    @Transactional
    public void saveMain(WptpMedicineInstock wptpMedicineInstock, List<WptpMedicineInstockFile> wptpMedicineInstockFileList) {
        wptpMedicineInstockMapper.insert(wptpMedicineInstock);
        if (wptpMedicineInstockFileList != null && wptpMedicineInstockFileList.size() > 0) {
            for (WptpMedicineInstockFile entity : wptpMedicineInstockFileList) {
                //外键设置
                entity.setMainId(wptpMedicineInstock.getId());
                wptpMedicineInstockFileMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void updateMain(WptpMedicineInstock wptpMedicineInstock, List<WptpMedicineInstockFile> wptpMedicineInstockFileList) {
        wptpMedicineInstockMapper.updateById(wptpMedicineInstock);

        //1.先删除子表数据
        wptpMedicineInstockFileMapper.deleteByMainId(wptpMedicineInstock.getId());

        //2.子表数据重新插入
        if (wptpMedicineInstockFileList != null && wptpMedicineInstockFileList.size() > 0) {
            for (WptpMedicineInstockFile entity : wptpMedicineInstockFileList) {
                //外键设置
                entity.setMainId(wptpMedicineInstock.getId());
                wptpMedicineInstockFileMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void delMain(String id) {
        wptpMedicineInstockFileMapper.deleteByMainId(id);
        wptpMedicineInstockMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            wptpMedicineInstockFileMapper.deleteByMainId(id.toString());
            wptpMedicineInstockMapper.deleteById(id);
        }
    }

}
