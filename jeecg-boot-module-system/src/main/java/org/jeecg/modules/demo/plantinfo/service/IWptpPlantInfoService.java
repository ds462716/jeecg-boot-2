package org.jeecg.modules.demo.plantinfo.service;

import org.jeecg.modules.demo.plantinfo.entity.WptpPlantFile;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 田间作业表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface IWptpPlantInfoService extends IService<WptpPlantInfo> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(WptpPlantInfo wptpPlantInfo,List<WptpPlantFile> wptpPlantFileList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(WptpPlantInfo wptpPlantInfo,List<WptpPlantFile> wptpPlantFileList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
