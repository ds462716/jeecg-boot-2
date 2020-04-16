package org.jeecg.modules.demo.entinfo.mapper;

import java.util.List;

import org.jeecg.modules.demo.entinfo.entity.WptpEntFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 企业信息附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface WptpEntFileMapper extends BaseMapper<WptpEntFile> {

    public boolean deleteByMainId(@Param("mainId") String mainId);

    public List<WptpEntFile> selectByMainId(@Param("mainId") String mainId);
}
