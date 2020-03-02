package org.jeecg.modules.demo.medicinalinfo.mapper;

import java.util.List;

import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicineFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 药材信息附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface WptpMedicineFileMapper extends BaseMapper<WptpMedicineFile> {

    public boolean deleteByMainId(@Param("mainId") String mainId);

    public List<WptpMedicineFile> selectByMainId(@Param("mainId") String mainId);
}
