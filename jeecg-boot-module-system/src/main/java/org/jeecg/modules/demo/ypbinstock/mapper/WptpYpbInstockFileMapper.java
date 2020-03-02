package org.jeecg.modules.demo.ypbinstock.mapper;

import java.util.List;

import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstockFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 饮片经营药材入库附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface WptpYpbInstockFileMapper extends BaseMapper<WptpYpbInstockFile> {

    public boolean deleteByMainId(@Param("mainId") String mainId);

    public List<WptpYpbInstockFile> selectByMainId(@Param("mainId") String mainId);
}
