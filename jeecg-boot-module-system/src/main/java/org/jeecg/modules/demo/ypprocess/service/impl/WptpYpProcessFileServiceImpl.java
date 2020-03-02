package org.jeecg.modules.demo.ypprocess.service.impl;

import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcessFile;
import org.jeecg.modules.demo.ypprocess.mapper.WptpYpProcessFileMapper;
import org.jeecg.modules.demo.ypprocess.service.IWptpYpProcessFileService;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 饮片加工附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpYpProcessFileServiceImpl extends ServiceImpl<WptpYpProcessFileMapper, WptpYpProcessFile> implements IWptpYpProcessFileService {

    @Autowired
    private WptpYpProcessFileMapper wptpYpProcessFileMapper;

    @Override
    public List<WptpYpProcessFile> selectByMainId(String mainId) {
        return wptpYpProcessFileMapper.selectByMainId(mainId);
    }
}
