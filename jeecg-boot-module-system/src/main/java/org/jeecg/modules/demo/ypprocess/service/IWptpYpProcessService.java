package org.jeecg.modules.demo.ypprocess.service;

import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcessFile;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcess;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 饮片加工表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface IWptpYpProcessService extends IService<WptpYpProcess> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(WptpYpProcess wptpYpProcess,List<WptpYpProcessFile> wptpYpProcessFileList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(WptpYpProcess wptpYpProcess,List<WptpYpProcessFile> wptpYpProcessFileList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
