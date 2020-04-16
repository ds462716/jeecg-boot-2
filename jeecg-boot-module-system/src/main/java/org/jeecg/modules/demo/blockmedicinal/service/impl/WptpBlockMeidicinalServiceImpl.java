package org.jeecg.modules.demo.blockmedicinal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.demo.blockmedicinal.entity.WptpBlockMeidicinal;
import org.jeecg.modules.demo.blockmedicinal.mapper.WptpBlockMeidicinalMapper;
import org.jeecg.modules.demo.blockmedicinal.service.IWptpBlockMeidicinalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 生产档案表
 * @Author: jeecg-boot
 * @Date: 2019-10-09
 * @Version: V1.0
 */
@Service
public class WptpBlockMeidicinalServiceImpl extends ServiceImpl<WptpBlockMeidicinalMapper, WptpBlockMeidicinal> implements IWptpBlockMeidicinalService {

    @Autowired
    private WptpBlockMeidicinalMapper wptpBlockMeidicinalMapper;

    @Override
    @Transactional
    public void saveMain(WptpBlockMeidicinal wptpBlockMeidicinal) {
        wptpBlockMeidicinalMapper.insert(wptpBlockMeidicinal);
    }

    @Override
    @Transactional
    public void updateMain(WptpBlockMeidicinal wptpBlockMeidicinal) {
        wptpBlockMeidicinalMapper.updateById(wptpBlockMeidicinal);

        //1.先删除子表数据

        //2.子表数据重新插入
    }

    @Override
    @Transactional
    public void delMain(String id) {
        wptpBlockMeidicinalMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            wptpBlockMeidicinalMapper.deleteById(id);
        }
    }

}
