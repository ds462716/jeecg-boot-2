package org.jeecg.modules.demo.entinfo.service;

import org.jeecg.modules.demo.entinfo.entity.WptpEntFile;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 企业基本信息
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface IWptpEntInfoService extends IService<WptpEntInfo> {

    /**
     * 添加一对多
     */
    public void saveMain(WptpEntInfo wptpEntInfo, List<WptpEntFile> wptpEntFileList);

    /**
     * 修改一对多
     */
    public void updateMain(WptpEntInfo wptpEntInfo, List<WptpEntFile> wptpEntFileList);

    /**
     * 删除一对多
     */
    public void delMain(String id);

    /**
     * 批量删除一对多
     */
    public void delBatchMain(Collection<? extends Serializable> idList);

    /**
     * 根据区（县）的编码，查询一 个企 业 编 码
     */
    public String getEntId(String districtCode);

    /**
     * 根据企业ID查询一个企业
     */
    public Boolean getEntByEntId(String entId);


}
