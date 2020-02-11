package org.jeecg.modules.demo.medicinalinfo.service;

import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicineFile;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 药材信息附表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface IWptpMedicineFileService extends IService<WptpMedicineFile> {

	public List<WptpMedicineFile> selectByMainId(String mainId);
}
