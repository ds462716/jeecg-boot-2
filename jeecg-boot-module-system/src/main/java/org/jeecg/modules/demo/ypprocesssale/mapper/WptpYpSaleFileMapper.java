package org.jeecg.modules.demo.ypprocesssale.mapper;

import java.util.List;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSaleFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 饮片销售附表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface WptpYpSaleFileMapper extends BaseMapper<WptpYpSaleFile> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<WptpYpSaleFile> selectByMainId(@Param("mainId") String mainId);
}
