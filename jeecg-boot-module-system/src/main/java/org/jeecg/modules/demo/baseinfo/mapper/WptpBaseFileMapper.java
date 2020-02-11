package org.jeecg.modules.demo.baseinfo.mapper;

import java.util.List;
import org.jeecg.modules.demo.baseinfo.entity.WptpBaseFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 基地信息附表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface WptpBaseFileMapper extends BaseMapper<WptpBaseFile> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<WptpBaseFile> selectByMainId(@Param("mainId") String mainId);
}
