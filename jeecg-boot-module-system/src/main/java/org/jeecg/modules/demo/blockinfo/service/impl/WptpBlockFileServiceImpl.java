package org.jeecg.modules.demo.blockinfo.service.impl;

import org.jeecg.modules.demo.blockinfo.entity.WptpBlockFile;
import org.jeecg.modules.demo.blockinfo.mapper.WptpBlockFileMapper;
import org.jeecg.modules.demo.blockinfo.service.IWptpBlockFileService;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 地块表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpBlockFileServiceImpl extends ServiceImpl<WptpBlockFileMapper, WptpBlockFile> implements IWptpBlockFileService {

    @Autowired
    private WptpBlockFileMapper wptpBlockFileMapper;

    @Override
    public List<WptpBlockFile> selectByMainId(String mainId) {
        return wptpBlockFileMapper.selectByMainId(mainId);
    }
}
