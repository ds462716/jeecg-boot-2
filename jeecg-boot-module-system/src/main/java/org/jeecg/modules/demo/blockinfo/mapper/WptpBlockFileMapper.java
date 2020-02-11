package org.jeecg.modules.demo.blockinfo.mapper;

import java.util.List;
import org.jeecg.modules.demo.blockinfo.entity.WptpBlockFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 地块表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface WptpBlockFileMapper extends BaseMapper<WptpBlockFile> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<WptpBlockFile> selectByMainId(@Param("mainId") String mainId);
}
