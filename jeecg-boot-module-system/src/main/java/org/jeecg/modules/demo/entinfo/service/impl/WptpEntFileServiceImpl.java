package org.jeecg.modules.demo.entinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.demo.baseinfo.entity.WptpBaseFile;
import org.jeecg.modules.demo.entinfo.entity.WptpEntFile;
import org.jeecg.modules.demo.entinfo.mapper.WptpEntFileMapper;
import org.jeecg.modules.demo.entinfo.service.IWptpEntFileService;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 企业信息附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpEntFileServiceImpl extends ServiceImpl<WptpEntFileMapper, WptpEntFile> implements IWptpEntFileService {

    @Autowired
    private WptpEntFileMapper wptpEntFileMapper;

    @Override
    public List<WptpEntFile> selectByMainId(String mainId) {
        return wptpEntFileMapper.selectByMainId(mainId);
    }

    @Override
    public boolean deleteByPath(String path) {
        QueryWrapper<WptpEntFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("path", path);
        return remove(queryWrapper);
    }
}
