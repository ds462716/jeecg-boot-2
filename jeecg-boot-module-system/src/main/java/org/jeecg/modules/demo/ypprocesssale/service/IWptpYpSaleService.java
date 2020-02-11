package org.jeecg.modules.demo.ypprocesssale.service;

import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSaleFile;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSale;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 饮片销售
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface IWptpYpSaleService extends IService<WptpYpSale> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(WptpYpSale wptpYpSale,List<WptpYpSaleFile> wptpYpSaleFileList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(WptpYpSale wptpYpSale,List<WptpYpSaleFile> wptpYpSaleFileList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
