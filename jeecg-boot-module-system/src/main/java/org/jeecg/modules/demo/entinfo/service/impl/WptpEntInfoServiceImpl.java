package org.jeecg.modules.demo.entinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.entinfo.entity.WptpEntFile;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.mapper.WptpEntFileMapper;
import org.jeecg.modules.demo.entinfo.mapper.WptpEntInfoMapper;
import org.jeecg.modules.demo.entinfo.service.IWptpEntInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 企业基本信息
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpEntInfoServiceImpl extends ServiceImpl<WptpEntInfoMapper, WptpEntInfo> implements IWptpEntInfoService {

    @Autowired
    private WptpEntInfoMapper wptpEntInfoMapper;
    @Autowired
    private WptpEntFileMapper wptpEntFileMapper;

    @Override
    @Transactional
    public void saveMain(WptpEntInfo wptpEntInfo, List<WptpEntFile> wptpEntFileList) {
        wptpEntInfoMapper.insert(wptpEntInfo);
        if (wptpEntFileList != null && wptpEntFileList.size() > 0) {
            for (WptpEntFile entity : wptpEntFileList) {
                //外键设置
                entity.setMainId(wptpEntInfo.getId());
                wptpEntFileMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void updateMain(WptpEntInfo wptpEntInfo, List<WptpEntFile> wptpEntFileList) {
        wptpEntInfoMapper.updateById(wptpEntInfo);

        //1.先删除子表数据
        wptpEntFileMapper.deleteByMainId(wptpEntInfo.getId());

        //2.子表数据重新插入
        if (wptpEntFileList != null && wptpEntFileList.size() > 0) {
            for (WptpEntFile entity : wptpEntFileList) {
                //外键设置
                entity.setMainId(wptpEntInfo.getId());
                wptpEntFileMapper.insert(entity);
            }
        }
    }

    @Override
    @Transactional
    public void delMain(String id) {
        wptpEntFileMapper.deleteByMainId(id);
        wptpEntInfoMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void delBatchMain(Collection<? extends Serializable> idList) {
        for (Serializable id : idList) {
            wptpEntFileMapper.deleteByMainId(id.toString());
            wptpEntInfoMapper.deleteById(id);
        }
    }

    @Override
    @Transactional
    public String getEntId(String districtCode) {
        return wptpEntInfoMapper.getEntId(districtCode);
    }

    @Override
    public Boolean getEntByEntId(String entId) {
        QueryWrapper<WptpEntInfo> entInfoQueryWrapper = new QueryWrapper<>();
        entInfoQueryWrapper.eq("ent_id", entId);//判断是否有该流水号的记录
        entInfoQueryWrapper.eq("deleted", "0");
        WptpEntInfo wptpEntInfo = wptpEntInfoMapper.selectOne(entInfoQueryWrapper);
        if (oConvertUtils.isEmpty(wptpEntInfo)) {
            return true;
        }
        return false;
    }
}
