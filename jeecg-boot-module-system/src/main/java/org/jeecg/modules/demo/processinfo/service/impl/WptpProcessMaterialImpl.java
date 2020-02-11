package org.jeecg.modules.demo.processinfo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.mapper.WptpProcessMaterialMapper;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessMaterial;
import org.springframework.stereotype.Service;

@Service
public class WptpProcessMaterialImpl extends ServiceImpl<WptpProcessMaterialMapper, WptpProcessMaterial> implements IWptpProcessMaterial {
}
