package org.jeecg.modules.demo.medicinebinstock.mapper;

import java.util.List;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstockFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 药材入库附表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface WptpMedicineInstockFileMapper extends BaseMapper<WptpMedicineInstockFile> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<WptpMedicineInstockFile> selectByMainId(@Param("mainId") String mainId);
}
