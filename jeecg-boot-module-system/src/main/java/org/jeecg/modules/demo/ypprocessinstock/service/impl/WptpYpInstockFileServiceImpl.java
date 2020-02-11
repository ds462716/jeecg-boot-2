package org.jeecg.modules.demo.ypprocessinstock.service.impl;

import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstockFile;
import org.jeecg.modules.demo.ypprocessinstock.mapper.WptpYpInstockFileMapper;
import org.jeecg.modules.demo.ypprocessinstock.service.IWptpYpInstockFileService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 饮片入库附表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpYpInstockFileServiceImpl extends ServiceImpl<WptpYpInstockFileMapper, WptpYpInstockFile> implements IWptpYpInstockFileService {
	
	@Autowired
	private WptpYpInstockFileMapper wptpYpInstockFileMapper;
	
	@Override
	public List<WptpYpInstockFile> selectByMainId(String mainId) {
		return wptpYpInstockFileMapper.selectByMainId(mainId);
	}
}
