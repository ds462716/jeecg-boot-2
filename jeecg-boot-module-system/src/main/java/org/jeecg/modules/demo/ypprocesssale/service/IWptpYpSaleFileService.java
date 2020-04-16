package org.jeecg.modules.demo.ypprocesssale.service;

import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSaleFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 饮片销售附表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
public interface IWptpYpSaleFileService extends IService<WptpYpSaleFile> {

    public List<WptpYpSaleFile> selectByMainId(String mainId);
}
