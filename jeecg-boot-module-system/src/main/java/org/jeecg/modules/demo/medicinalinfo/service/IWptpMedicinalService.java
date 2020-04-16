package org.jeecg.modules.demo.medicinalinfo.service;

import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicineFile;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 中药材品种
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface IWptpMedicinalService extends IService<WptpMedicinal> {

    /**
     * 添加一对多
     */
    public void saveMain(WptpMedicinal wptpMedicinal, List<WptpMedicineFile> wptpMedicineFileList);

    /**
     * 修改一对多
     */
    public void updateMain(WptpMedicinal wptpMedicinal, List<WptpMedicineFile> wptpMedicineFileList);

    /**
     * 删除一对多
     */
    public void delMain(String id);

    /**
     * 批量删除一对多
     */
    public void delBatchMain(Collection<? extends Serializable> idList);

    /**
     * 获取最大的一个药材编码
     *
     * @return
     */
    String getDescMedicineCode();

    /**
     * 上传药材到行业协会
     */
    String uploadXh(WptpMedicinal wptpMedicinal) throws Exception;

}
