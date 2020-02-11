package org.jeecg.modules.demo.processinfo.service.impl;

import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import org.jeecg.modules.demo.processinfo.mapper.WptpProcessFileMapper;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessFileService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 产地初加工附表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpProcessFileServiceImpl extends ServiceImpl<WptpProcessFileMapper, WptpProcessFile> implements IWptpProcessFileService {
	
	@Autowired
	private WptpProcessFileMapper wptpProcessFileMapper;
	
	@Override
	public List<WptpProcessFile> selectByMainId(String mainId) {
		return wptpProcessFileMapper.selectByMainId(mainId);
	}
}
