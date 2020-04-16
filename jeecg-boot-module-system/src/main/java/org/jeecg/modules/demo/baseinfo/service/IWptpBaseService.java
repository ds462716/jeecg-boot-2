package org.jeecg.modules.demo.baseinfo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.demo.baseinfo.entity.WptpBase;
import org.jeecg.modules.demo.baseinfo.entity.WptpBaseFile;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 基地信息管理表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface IWptpBaseService extends IService<WptpBase> {

    /**
     * 添加一对多
     */
    public void saveMain(WptpBase wptpBase, List<WptpBaseFile> wptpBaseFileList);

    /**
     * 修改一对多
     */
    public void updateMain(WptpBase wptpBase, List<WptpBaseFile> wptpBaseFileList);

    /**
     * 删除一对多
     */
    public void delMain(String id);

    /**
     * 批量删除一对多
     */
    public void delBatchMain(Collection<? extends Serializable> idList);

    /**
     * 根据企业id，查询一个基地编码
     *
     * @return
     */
    public String getBaseCode(String entId);

}
