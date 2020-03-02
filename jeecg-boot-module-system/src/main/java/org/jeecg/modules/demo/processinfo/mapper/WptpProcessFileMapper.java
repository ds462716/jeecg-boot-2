package org.jeecg.modules.demo.processinfo.mapper;

import java.util.List;

import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 产地初加工附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface WptpProcessFileMapper extends BaseMapper<WptpProcessFile> {

    public boolean deleteByMainId(@Param("mainId") String mainId);

    public List<WptpProcessFile> selectByMainId(@Param("mainId") String mainId);
}
