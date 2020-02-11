package org.jeecg.modules.demo.ypprocessinstock.service.impl;

import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstock;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstockFile;
import org.jeecg.modules.demo.ypprocessinstock.mapper.WptpYpInstockFileMapper;
import org.jeecg.modules.demo.ypprocessinstock.mapper.WptpYpInstockMapper;
import org.jeecg.modules.demo.ypprocessinstock.service.IWptpYpInstockService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 饮片加工-药材入库表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpYpInstockServiceImpl extends ServiceImpl<WptpYpInstockMapper, WptpYpInstock> implements IWptpYpInstockService {

	@Autowired
	private WptpYpInstockMapper wptpYpInstockMapper;
	@Autowired
	private WptpYpInstockFileMapper wptpYpInstockFileMapper;
	
	@Override
	@Transactional
	public void saveMain(WptpYpInstock wptpYpInstock, List<WptpYpInstockFile> wptpYpInstockFileList) {
		wptpYpInstockMapper.insert(wptpYpInstock);
		if(wptpYpInstockFileList!=null && wptpYpInstockFileList.size()>0) {
			for(WptpYpInstockFile entity:wptpYpInstockFileList) {
				//外键设置
				entity.setMainId(wptpYpInstock.getId());
				wptpYpInstockFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(WptpYpInstock wptpYpInstock,List<WptpYpInstockFile> wptpYpInstockFileList) {
		wptpYpInstockMapper.updateById(wptpYpInstock);
		
		//1.先删除子表数据
		wptpYpInstockFileMapper.deleteByMainId(wptpYpInstock.getId());
		
		//2.子表数据重新插入
		if(wptpYpInstockFileList!=null && wptpYpInstockFileList.size()>0) {
			for(WptpYpInstockFile entity:wptpYpInstockFileList) {
				//外键设置
				entity.setMainId(wptpYpInstock.getId());
				wptpYpInstockFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		wptpYpInstockFileMapper.deleteByMainId(id);
		wptpYpInstockMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			wptpYpInstockFileMapper.deleteByMainId(id.toString());
			wptpYpInstockMapper.deleteById(id);
		}
	}
	
}
