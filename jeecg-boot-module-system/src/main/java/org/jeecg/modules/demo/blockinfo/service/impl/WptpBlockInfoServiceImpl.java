package org.jeecg.modules.demo.blockinfo.service.impl;

import org.jeecg.modules.demo.blockinfo.entity.WptpBlockInfo;
import org.jeecg.modules.demo.blockinfo.entity.WptpBlockFile;
import org.jeecg.modules.demo.blockinfo.mapper.WptpBlockFileMapper;
import org.jeecg.modules.demo.blockinfo.mapper.WptpBlockInfoMapper;
import org.jeecg.modules.demo.blockinfo.service.IWptpBlockInfoService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 地块表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpBlockInfoServiceImpl extends ServiceImpl<WptpBlockInfoMapper, WptpBlockInfo> implements IWptpBlockInfoService {

	@Autowired
	private WptpBlockInfoMapper wptpBlockInfoMapper;
	@Autowired
	private WptpBlockFileMapper wptpBlockFileMapper;
	
	@Override
	@Transactional
	public void saveMain(WptpBlockInfo wptpBlockInfo, List<WptpBlockFile> wptpBlockFileList) {
		wptpBlockInfoMapper.insert(wptpBlockInfo);
		if(wptpBlockFileList!=null && wptpBlockFileList.size()>0) {
			for(WptpBlockFile entity:wptpBlockFileList) {
				//外键设置
				entity.setMainId(wptpBlockInfo.getId());
				wptpBlockFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(WptpBlockInfo wptpBlockInfo,List<WptpBlockFile> wptpBlockFileList) {
		wptpBlockInfoMapper.updateById(wptpBlockInfo);
		
		//1.先删除子表数据
		wptpBlockFileMapper.deleteByMainId(wptpBlockInfo.getId());
		
		//2.子表数据重新插入
		if(wptpBlockFileList!=null && wptpBlockFileList.size()>0) {
			for(WptpBlockFile entity:wptpBlockFileList) {
				//外键设置
				entity.setMainId(wptpBlockInfo.getId());
				wptpBlockFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		wptpBlockFileMapper.deleteByMainId(id);
		wptpBlockInfoMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			wptpBlockFileMapper.deleteByMainId(id.toString());
			wptpBlockInfoMapper.deleteById(id);
		}
	}
	
}
