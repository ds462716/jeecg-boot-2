package org.jeecg.modules.demo.medicinalinfo.service.impl;

import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicineFile;
import org.jeecg.modules.demo.medicinalinfo.mapper.WptpMedicineFileMapper;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicineFileService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 药材信息附表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpMedicineFileServiceImpl extends ServiceImpl<WptpMedicineFileMapper, WptpMedicineFile> implements IWptpMedicineFileService {
	
	@Autowired
	private WptpMedicineFileMapper wptpMedicineFileMapper;
	
	@Override
	public List<WptpMedicineFile> selectByMainId(String mainId) {
		return wptpMedicineFileMapper.selectByMainId(mainId);
	}
}
