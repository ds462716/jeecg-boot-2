package org.jeecg.modules.demo.processinfo.service.impl;

import org.jeecg.modules.demo.processinfo.entity.WptpProcessInfo;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import org.jeecg.modules.demo.processinfo.mapper.WptpProcessFileMapper;
import org.jeecg.modules.demo.processinfo.mapper.WptpProcessInfoMapper;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 产地初加工表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpProcessInfoServiceImpl extends ServiceImpl<WptpProcessInfoMapper, WptpProcessInfo> implements IWptpProcessInfoService {

	@Autowired
	private WptpProcessInfoMapper wptpProcessInfoMapper;
	@Autowired
	private WptpProcessFileMapper wptpProcessFileMapper;
	
	@Override
	@Transactional
	public void saveMain(WptpProcessInfo wptpProcessInfo, List<WptpProcessFile> wptpProcessFileList) {
		wptpProcessInfoMapper.insert(wptpProcessInfo);
		if(wptpProcessFileList!=null && wptpProcessFileList.size()>0) {
			for(WptpProcessFile entity:wptpProcessFileList) {
				//外键设置
				entity.setMainId(wptpProcessInfo.getId());
				wptpProcessFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(WptpProcessInfo wptpProcessInfo,List<WptpProcessFile> wptpProcessFileList) {
		wptpProcessInfoMapper.updateById(wptpProcessInfo);
		
		//1.先删除子表数据
		wptpProcessFileMapper.deleteByMainId(wptpProcessInfo.getId());
		
		//2.子表数据重新插入
		if(wptpProcessFileList!=null && wptpProcessFileList.size()>0) {
			for(WptpProcessFile entity:wptpProcessFileList) {
				//外键设置
				entity.setMainId(wptpProcessInfo.getId());
				wptpProcessFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		wptpProcessFileMapper.deleteByMainId(id);
		wptpProcessInfoMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			wptpProcessFileMapper.deleteByMainId(id.toString());
			wptpProcessInfoMapper.deleteById(id);
		}
	}
	
}
