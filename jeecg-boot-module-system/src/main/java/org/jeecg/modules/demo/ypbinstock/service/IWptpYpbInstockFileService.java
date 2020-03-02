package org.jeecg.modules.demo.ypbinstock.service;

import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstockFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 饮片经营药材入库附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface IWptpYpbInstockFileService extends IService<WptpYpbInstockFile> {

    public List<WptpYpbInstockFile> selectByMainId(String mainId);
}
