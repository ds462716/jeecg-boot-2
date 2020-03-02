package org.jeecg.modules.demo.medicinebinstock.service;

import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstockFile;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstock;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 药材经营药材入库表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface IWptpMedicineInstockService extends IService<WptpMedicineInstock> {

    /**
     * 添加一对多
     */
    public void saveMain(WptpMedicineInstock wptpMedicineInstock, List<WptpMedicineInstockFile> wptpMedicineInstockFileList);

    /**
     * 修改一对多
     */
    public void updateMain(WptpMedicineInstock wptpMedicineInstock, List<WptpMedicineInstockFile> wptpMedicineInstockFileList);

    /**
     * 删除一对多
     */
    public void delMain(String id);

    /**
     * 批量删除一对多
     */
    public void delBatchMain(Collection<? extends Serializable> idList);

}
