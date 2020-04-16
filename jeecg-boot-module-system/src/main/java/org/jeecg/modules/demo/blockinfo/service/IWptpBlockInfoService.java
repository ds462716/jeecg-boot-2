package org.jeecg.modules.demo.blockinfo.service;

import org.jeecg.modules.demo.blockinfo.entity.WptpBlockFile;
import org.jeecg.modules.demo.blockinfo.entity.WptpBlockInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 地块表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface IWptpBlockInfoService extends IService<WptpBlockInfo> {

    /**
     * 添加一对多
     */
    public void saveMain(WptpBlockInfo wptpBlockInfo, List<WptpBlockFile> wptpBlockFileList);

    /**
     * 修改一对多
     */
    public void updateMain(WptpBlockInfo wptpBlockInfo, List<WptpBlockFile> wptpBlockFileList);

    /**
     * 删除一对多
     */
    public void delMain(String id);

    /**
     * 批量删除一对多
     */
    public void delBatchMain(Collection<? extends Serializable> idList);

}
