package org.jeecg.modules.demo.ypprocesssale.service.impl;

import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSale;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSaleFile;
import org.jeecg.modules.demo.ypprocesssale.mapper.WptpYpSaleFileMapper;
import org.jeecg.modules.demo.ypprocesssale.mapper.WptpYpSaleMapper;
import org.jeecg.modules.demo.ypprocesssale.service.IWptpYpSaleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 饮片销售
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpYpSaleServiceImpl extends ServiceImpl<WptpYpSaleMapper, WptpYpSale> implements IWptpYpSaleService {

	@Autowired
	private WptpYpSaleMapper wptpYpSaleMapper;
	@Autowired
	private WptpYpSaleFileMapper wptpYpSaleFileMapper;
	
	@Override
	@Transactional
	public void saveMain(WptpYpSale wptpYpSale, List<WptpYpSaleFile> wptpYpSaleFileList) {
		wptpYpSaleMapper.insert(wptpYpSale);
		if(wptpYpSaleFileList!=null && wptpYpSaleFileList.size()>0) {
			for(WptpYpSaleFile entity:wptpYpSaleFileList) {
				//外键设置
				entity.setMainId(wptpYpSale.getId());
				wptpYpSaleFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(WptpYpSale wptpYpSale,List<WptpYpSaleFile> wptpYpSaleFileList) {
		wptpYpSaleMapper.updateById(wptpYpSale);
		
		//1.先删除子表数据
		wptpYpSaleFileMapper.deleteByMainId(wptpYpSale.getId());
		
		//2.子表数据重新插入
		if(wptpYpSaleFileList!=null && wptpYpSaleFileList.size()>0) {
			for(WptpYpSaleFile entity:wptpYpSaleFileList) {
				//外键设置
				entity.setMainId(wptpYpSale.getId());
				wptpYpSaleFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		wptpYpSaleFileMapper.deleteByMainId(id);
		wptpYpSaleMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			wptpYpSaleFileMapper.deleteByMainId(id.toString());
			wptpYpSaleMapper.deleteById(id);
		}
	}
	
}
