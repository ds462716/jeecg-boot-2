package org.jeecg.modules.demo.processinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.mapper.WptpProcessMaterialMapper;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WptpProcessMaterialImpl extends ServiceImpl<WptpProcessMaterialMapper, WptpProcessMaterial> implements IWptpProcessMaterial {
    @Autowired
    private WptpProcessMaterialMapper wptpProcessMaterialMapper;

    @Override
    public List<WptpProcessMaterial> listByCsNo(String csBatch) {
        QueryWrapper<WptpProcessMaterial> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cs_batch", csBatch);
        queryWrapper.eq("deleted", "0");
        List<WptpProcessMaterial> wptpProcessMaterials = wptpProcessMaterialMapper.selectList(queryWrapper);
        return wptpProcessMaterials;
    }
}
