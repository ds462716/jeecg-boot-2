package org.jeecg.modules.demo.blockinfo.service;

import org.jeecg.modules.demo.blockinfo.entity.WptpBlockFile;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 地块表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
public interface IWptpBlockFileService extends IService<WptpBlockFile> {

	public List<WptpBlockFile> selectByMainId(String mainId);
}
