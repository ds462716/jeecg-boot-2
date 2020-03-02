package org.jeecg.modules.demo.ypbinstock.service.impl;

import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstockFile;
import org.jeecg.modules.demo.ypbinstock.mapper.WptpYpbInstockFileMapper;
import org.jeecg.modules.demo.ypbinstock.service.IWptpYpbInstockFileService;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 饮片经营药材入库附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpYpbInstockFileServiceImpl extends ServiceImpl<WptpYpbInstockFileMapper, WptpYpbInstockFile> implements IWptpYpbInstockFileService {

    @Autowired
    private WptpYpbInstockFileMapper wptpYpbInstockFileMapper;

    @Override
    public List<WptpYpbInstockFile> selectByMainId(String mainId) {
        return wptpYpbInstockFileMapper.selectByMainId(mainId);
    }
}
