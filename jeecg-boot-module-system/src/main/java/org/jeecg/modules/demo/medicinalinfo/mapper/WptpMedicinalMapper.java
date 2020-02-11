package org.jeecg.modules.demo.medicinalinfo.mapper;

import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 中药材品种
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface WptpMedicinalMapper extends BaseMapper<WptpMedicinal> {
     String getDescMedicineCode();
}
