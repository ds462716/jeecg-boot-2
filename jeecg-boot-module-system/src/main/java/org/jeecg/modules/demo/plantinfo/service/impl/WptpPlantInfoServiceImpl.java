package org.jeecg.modules.demo.plantinfo.service.impl;

import org.jeecg.modules.demo.plantinfo.entity.WptpPlantInfo;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantFile;
import org.jeecg.modules.demo.plantinfo.mapper.WptpPlantFileMapper;
import org.jeecg.modules.demo.plantinfo.mapper.WptpPlantInfoMapper;
import org.jeecg.modules.demo.plantinfo.service.IWptpPlantInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 田间作业表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpPlantInfoServiceImpl extends ServiceImpl<WptpPlantInfoMapper, WptpPlantInfo> implements IWptpPlantInfoService {

    @Autowired
    private WptpPlantInfoMapper wptpPlantInfoMapper;
    @Autowired
    private WptpPlantFileMapper wptpPlantFileMapper;

    @Override
    @Transactional
    public void saveMain(WptpPlantInfo wptpPlantInfo, List<WptpPlantFile> wptpPlantFileList) {
        wptpPlantInfoMapper.insert(wptpPlantInfo);
        if (wptpPlantFileList != null && wptpPlantFileList.size() > 0) {
            for (WptpPlantFile entity : wptpPlantFileList) {
                //外键设置
                entity.setMainId(wptpPlantInfo.getId());
                wptpPlantFileMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void updateMain(WptpPlantInfo wptpPlantInfo, List<WptpPlantFile> wptpPlantFileList) {
        wptpPlantInfoMapper.updateById(wptpPlantInfo);

        //1.先删除子表数据
        wptpPlantFileMapper.deleteByMainId(wptpPlantInfo.getId());

        //2.子表数据重新插入
        if (wptpPlantFileList != null && wptpPlantFileList.size() > 0) {
            for (WptpPlantFile entity : wptpPlantFileList) {
                //外键设置
                entity.setMainId(wptpPlantInfo.getId());
                wptpPlantFileMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void delMain(String id) {
        wptpPlantFileMapper.deleteByMainId(id);
        wptpPlantInfoMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            wptpPlantFileMapper.deleteByMainId(id.toString());
            wptpPlantInfoMapper.deleteById(id);
        }
    }

}
