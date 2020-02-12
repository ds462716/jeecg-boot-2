package org.jeecg.modules.demo.processinfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessMaterial;

import java.util.List;

public interface IWptpProcessMaterial extends IService<WptpProcessMaterial> {
    public List<WptpProcessMaterial> listByCsNo(String csBatch);
}
