package org.jeecg.modules.demo.plantinfo.service.impl;

import org.jeecg.modules.demo.plantinfo.entity.WptpPlantFile;
import org.jeecg.modules.demo.plantinfo.mapper.WptpPlantFileMapper;
import org.jeecg.modules.demo.plantinfo.service.IWptpPlantFileService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 田间作业附表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpPlantFileServiceImpl extends ServiceImpl<WptpPlantFileMapper, WptpPlantFile> implements IWptpPlantFileService {
	
	@Autowired
	private WptpPlantFileMapper wptpPlantFileMapper;
	
	@Override
	public List<WptpPlantFile> selectByMainId(String mainId) {
		return wptpPlantFileMapper.selectByMainId(mainId);
	}
}
