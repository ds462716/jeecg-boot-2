package org.jeecg.modules.demo.baseinfo.service.impl;

import org.jeecg.modules.demo.baseinfo.entity.WptpBase;
import org.jeecg.modules.demo.baseinfo.entity.WptpBaseFile;
import org.jeecg.modules.demo.baseinfo.mapper.WptpBaseFileMapper;
import org.jeecg.modules.demo.baseinfo.mapper.WptpBaseMapper;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 基地信息管理表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpBaseServiceImpl extends ServiceImpl<WptpBaseMapper, WptpBase> implements IWptpBaseService {

	@Autowired
	private WptpBaseMapper wptpBaseMapper;
	@Autowired
	private WptpBaseFileMapper wptpBaseFileMapper;
	
	@Override
	@Transactional
	public void saveMain(WptpBase wptpBase, List<WptpBaseFile> wptpBaseFileList) {
		wptpBaseMapper.insert(wptpBase);
		if(wptpBaseFileList!=null && wptpBaseFileList.size()>0) {
			for(WptpBaseFile entity:wptpBaseFileList) {
				//外键设置
				entity.setMainId(wptpBase.getId());
				wptpBaseFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(WptpBase wptpBase,List<WptpBaseFile> wptpBaseFileList) {
		wptpBaseMapper.updateById(wptpBase);
		
		//1.先删除子表数据
		wptpBaseFileMapper.deleteByMainId(wptpBase.getId());
		
		//2.子表数据重新插入
		if(wptpBaseFileList!=null && wptpBaseFileList.size()>0) {
			for(WptpBaseFile entity:wptpBaseFileList) {
				//外键设置
				entity.setMainId(wptpBase.getId());
				wptpBaseFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		wptpBaseFileMapper.deleteByMainId(id);
		wptpBaseMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			wptpBaseFileMapper.deleteByMainId(id.toString());
			wptpBaseMapper.deleteById(id);
		}
	}

	@Override
	public String getBaseCode(String entId) {
		return wptpBaseMapper.getBaseCode(entId);
	}

}
