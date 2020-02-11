package org.jeecg.modules.demo.ypprocess.mapper;

import java.util.List;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcessFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 饮片加工附表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface WptpYpProcessFileMapper extends BaseMapper<WptpYpProcessFile> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<WptpYpProcessFile> selectByMainId(@Param("mainId") String mainId);
}
