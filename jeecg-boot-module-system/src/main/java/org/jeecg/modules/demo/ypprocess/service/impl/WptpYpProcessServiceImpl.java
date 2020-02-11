package org.jeecg.modules.demo.ypprocess.service.impl;

import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcess;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcessFile;
import org.jeecg.modules.demo.ypprocess.mapper.WptpYpProcessFileMapper;
import org.jeecg.modules.demo.ypprocess.mapper.WptpYpProcessMapper;
import org.jeecg.modules.demo.ypprocess.service.IWptpYpProcessService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 饮片加工表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpYpProcessServiceImpl extends ServiceImpl<WptpYpProcessMapper, WptpYpProcess> implements IWptpYpProcessService {

	@Autowired
	private WptpYpProcessMapper wptpYpProcessMapper;
	@Autowired
	private WptpYpProcessFileMapper wptpYpProcessFileMapper;
	
	@Override
	@Transactional
	public void saveMain(WptpYpProcess wptpYpProcess, List<WptpYpProcessFile> wptpYpProcessFileList) {
		wptpYpProcessMapper.insert(wptpYpProcess);
		if(wptpYpProcessFileList!=null && wptpYpProcessFileList.size()>0) {
			for(WptpYpProcessFile entity:wptpYpProcessFileList) {
				//外键设置
				entity.setMainId(wptpYpProcess.getId());
				wptpYpProcessFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(WptpYpProcess wptpYpProcess,List<WptpYpProcessFile> wptpYpProcessFileList) {
		wptpYpProcessMapper.updateById(wptpYpProcess);
		
		//1.先删除子表数据
		wptpYpProcessFileMapper.deleteByMainId(wptpYpProcess.getId());
		
		//2.子表数据重新插入
		if(wptpYpProcessFileList!=null && wptpYpProcessFileList.size()>0) {
			for(WptpYpProcessFile entity:wptpYpProcessFileList) {
				//外键设置
				entity.setMainId(wptpYpProcess.getId());
				wptpYpProcessFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		wptpYpProcessFileMapper.deleteByMainId(id);
		wptpYpProcessMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			wptpYpProcessFileMapper.deleteByMainId(id.toString());
			wptpYpProcessMapper.deleteById(id);
		}
	}
	
}
