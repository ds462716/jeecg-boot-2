package org.jeecg.modules.demo.ypbinstock.service.impl;

import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstock;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstockFile;
import org.jeecg.modules.demo.ypbinstock.mapper.WptpYpbInstockFileMapper;
import org.jeecg.modules.demo.ypbinstock.mapper.WptpYpbInstockMapper;
import org.jeecg.modules.demo.ypbinstock.service.IWptpYpbInstockService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 饮片经营药材入库
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpYpbInstockServiceImpl extends ServiceImpl<WptpYpbInstockMapper, WptpYpbInstock> implements IWptpYpbInstockService {

    @Autowired
    private WptpYpbInstockMapper wptpYpbInstockMapper;
    @Autowired
    private WptpYpbInstockFileMapper wptpYpbInstockFileMapper;

    @Override
    @Transactional
    public void saveMain(WptpYpbInstock wptpYpbInstock, List<WptpYpbInstockFile> wptpYpbInstockFileList) {
        wptpYpbInstockMapper.insert(wptpYpbInstock);
        if (wptpYpbInstockFileList != null && wptpYpbInstockFileList.size() > 0) {
            for (WptpYpbInstockFile entity : wptpYpbInstockFileList) {
                //外键设置
                entity.setMainId(wptpYpbInstock.getId());
                wptpYpbInstockFileMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void updateMain(WptpYpbInstock wptpYpbInstock, List<WptpYpbInstockFile> wptpYpbInstockFileList) {
        wptpYpbInstockMapper.updateById(wptpYpbInstock);

        //1.先删除子表数据
        wptpYpbInstockFileMapper.deleteByMainId(wptpYpbInstock.getId());

        //2.子表数据重新插入
        if (wptpYpbInstockFileList != null && wptpYpbInstockFileList.size() > 0) {
            for (WptpYpbInstockFile entity : wptpYpbInstockFileList) {
                //外键设置
                entity.setMainId(wptpYpbInstock.getId());
                wptpYpbInstockFileMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void delMain(String id) {
        wptpYpbInstockFileMapper.deleteByMainId(id);
        wptpYpbInstockMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            wptpYpbInstockFileMapper.deleteByMainId(id.toString());
            wptpYpbInstockMapper.deleteById(id);
        }
    }

}
