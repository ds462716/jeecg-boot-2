package org.jeecg.modules.demo.baseinfo.service.impl;

import org.jeecg.modules.demo.baseinfo.entity.WptpBaseFile;
import org.jeecg.modules.demo.baseinfo.mapper.WptpBaseFileMapper;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseFileService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 基地信息附表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpBaseFileServiceImpl extends ServiceImpl<WptpBaseFileMapper, WptpBaseFile> implements IWptpBaseFileService {
	
	@Autowired
	private WptpBaseFileMapper wptpBaseFileMapper;
	
	@Override
	public List<WptpBaseFile> selectByMainId(String mainId) {
		return wptpBaseFileMapper.selectByMainId(mainId);
	}
}
