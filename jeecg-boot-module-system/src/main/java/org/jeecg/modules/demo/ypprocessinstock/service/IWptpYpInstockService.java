package org.jeecg.modules.demo.ypprocessinstock.service;

import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstockFile;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstock;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 饮片加工-药材入库表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface IWptpYpInstockService extends IService<WptpYpInstock> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(WptpYpInstock wptpYpInstock,List<WptpYpInstockFile> wptpYpInstockFileList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(WptpYpInstock wptpYpInstock,List<WptpYpInstockFile> wptpYpInstockFileList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
