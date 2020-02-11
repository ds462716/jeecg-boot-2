package org.jeecg.modules.demo.processinfo.service;

import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 产地初加工附表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface IWptpProcessFileService extends IService<WptpProcessFile> {

	public List<WptpProcessFile> selectByMainId(String mainId);
}
