package org.jeecg.modules.demo.ypbinstock.service;

import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstockFile;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstock;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 饮片经营药材入库
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface IWptpYpbInstockService extends IService<WptpYpbInstock> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(WptpYpbInstock wptpYpbInstock,List<WptpYpbInstockFile> wptpYpbInstockFileList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(WptpYpbInstock wptpYpbInstock,List<WptpYpbInstockFile> wptpYpbInstockFileList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
