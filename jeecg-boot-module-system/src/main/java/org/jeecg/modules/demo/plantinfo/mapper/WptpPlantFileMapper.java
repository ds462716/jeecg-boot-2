package org.jeecg.modules.demo.plantinfo.mapper;

import java.util.List;

import org.jeecg.modules.demo.plantinfo.entity.WptpPlantFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 田间作业附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface WptpPlantFileMapper extends BaseMapper<WptpPlantFile> {

    public boolean deleteByMainId(@Param("mainId") String mainId);

    public List<WptpPlantFile> selectByMainId(@Param("mainId") String mainId);
}
