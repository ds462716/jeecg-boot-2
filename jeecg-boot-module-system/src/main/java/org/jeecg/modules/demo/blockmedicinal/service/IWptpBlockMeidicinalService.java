package org.jeecg.modules.demo.blockmedicinal.service;

import org.jeecg.modules.demo.blockmedicinal.entity.WptpBlockMeidicinal;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;

/**
 * @Description: 生产档案表
 * @Author: jeecg-boot
 * @Date:   2019-10-09
 * @Version: V1.0
 */
public interface IWptpBlockMeidicinalService extends IService<WptpBlockMeidicinal> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(WptpBlockMeidicinal wptpBlockMeidicinal) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(WptpBlockMeidicinal wptpBlockMeidicinal);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
