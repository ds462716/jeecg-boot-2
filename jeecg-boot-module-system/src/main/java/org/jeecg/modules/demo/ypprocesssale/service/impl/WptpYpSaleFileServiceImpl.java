package org.jeecg.modules.demo.ypprocesssale.service.impl;

import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSaleFile;
import org.jeecg.modules.demo.ypprocesssale.mapper.WptpYpSaleFileMapper;
import org.jeecg.modules.demo.ypprocesssale.service.IWptpYpSaleFileService;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 饮片销售附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpYpSaleFileServiceImpl extends ServiceImpl<WptpYpSaleFileMapper, WptpYpSaleFile> implements IWptpYpSaleFileService {

    @Autowired
    private WptpYpSaleFileMapper wptpYpSaleFileMapper;

    @Override
    public List<WptpYpSaleFile> selectByMainId(String mainId) {
        return wptpYpSaleFileMapper.selectByMainId(mainId);
    }
}
