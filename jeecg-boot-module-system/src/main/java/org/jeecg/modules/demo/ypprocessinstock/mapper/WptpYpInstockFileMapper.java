package org.jeecg.modules.demo.ypprocessinstock.mapper;

import java.util.List;

import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstockFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 饮片入库附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface WptpYpInstockFileMapper extends BaseMapper<WptpYpInstockFile> {

    public boolean deleteByMainId(@Param("mainId") String mainId);

    public List<WptpYpInstockFile> selectByMainId(@Param("mainId") String mainId);
}
