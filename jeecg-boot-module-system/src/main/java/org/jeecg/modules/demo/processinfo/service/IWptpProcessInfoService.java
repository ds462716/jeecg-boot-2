package org.jeecg.modules.demo.processinfo.service;

import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 产地初加工表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface IWptpProcessInfoService extends IService<WptpProcessInfo> {

    /**
     * 添加一对多
     */
    public void saveMain(WptpProcessInfo wptpProcessInfo, List<WptpProcessFile> wptpProcessFileList);

    /**
     * 修改一对多
     */
    public void updateMain(WptpProcessInfo wptpProcessInfo, List<WptpProcessFile> wptpProcessFileList);

    /**
     * 删除一对多
     */
    public void delMain(String id);

    /**
     * 批量删除一对多
     */
    public void delBatchMain(Collection<? extends Serializable> idList);

}
