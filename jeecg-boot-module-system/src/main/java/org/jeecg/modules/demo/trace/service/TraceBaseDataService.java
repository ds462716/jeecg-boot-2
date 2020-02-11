package org.jeecg.modules.demo.trace.service;

import org.jeecg.modules.demo.baseinfo.entity.WptpBaseFile;
import org.jeecg.modules.demo.baseinfo.vo.WptpBaseVO;
import org.jeecg.modules.demo.blockmedicinal.vo.WptpBlockMeidicinalVO;
import org.jeecg.modules.demo.csinfo.vo.WptpCsInfoVO;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstockFile;
import org.jeecg.modules.demo.medicinebinstock.vo.WptpMedicineInstockVO;
import org.jeecg.modules.demo.medicinebsale.vo.WptpMedicineSaleVO;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantFile;
import org.jeecg.modules.demo.plantinfo.vo.WptpPlantInfoVO;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.vo.WptpProcessInfoVO;
import org.jeecg.modules.demo.sale.entity.WptpSale;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstockFile;
import org.jeecg.modules.demo.ypbinstock.vo.WptpYpbInstockVO;
import org.jeecg.modules.demo.ypbsale.vo.WptpYpbSaleVO;
import org.jeecg.modules.demo.yppack.vo.WptpYpPackVO;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcessFile;
import org.jeecg.modules.demo.ypprocess.vo.WptpYpProcessVO;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstockFile;
import org.jeecg.modules.demo.ypprocessinstock.vo.WptpYpInstockVO;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSaleFile;
import org.jeecg.modules.demo.ypprocesssale.vo.WptpYpSaleVO;

import java.util.List;

/**
 * 追溯所需要的各个环节的基础数据
 * @author laowang
 */
public interface TraceBaseDataService {
    /**
     * 种植环节
     */
     WptpCsInfoVO getCsInfoVO(String medicineBatch);//采收批次

     List<WptpPlantInfoVO> listPlantInfoVO(String blockMedicinalId);//田间作业

     List<WptpPlantFile> listPlantFiles(String plantId);//田间作业文件

     WptpSale getSale(String traceCode);//出厂销售

     WptpProcessInfoVO getProcessInfoVO(String medicineBatch);//初加工

    List<WptpProcessFile> listProcessFiles(String processNo);//加工文件

    List<WptpProcessMaterial> listProcessMaterials(String processNo);//加工原料

    List<WptpProcessMaterial> listProcessMaterialsByCsNo(String csNo);//根据批次号查询加工原料

     WptpBlockMeidicinalVO getBlockMeidicinalVO(String blockMedicinalId);//档案

     WptpBaseVO  getWptpBaseVO(String baseCode);//基地

     List<WptpBaseFile> getBaseFile(String baseCode,String type);//基地图片或者报告
    /**
     * 药材经营
     */
     WptpMedicineInstockVO getMedicineInstockVO(String instockNumber);//药材入库

     List<WptpMedicineInstockFile> listWptpMedicineInstockFiles(String instockNumber);//药材入库文件

     WptpMedicineSaleVO getMedicineSaleVO(String traceCode);//药材销售
    /**
     * 饮片加工
     */
     WptpYpInstockVO getYpInstockVO(String instockNumber);//药材入库

    List<WptpYpInstockFile> listWptpYpInstockFiles(String instockNumber);//药材入库文件

     WptpYpProcessVO getYpProcessVO(String processNo);//饮片加工

    List<WptpYpProcessFile> listWptpYpProcessFiles(String processNo);//饮片加工文件

     WptpYpPackVO getYpPackVO(String packNo);//成品包装

     WptpYpSaleVO getYpSaleVO(String traceCode);//饮片销售

    List<WptpYpSaleFile> listWptpYpSaleFiles(String saleNumber);//销售文件
    /**
     * 饮片经营
     */
     WptpYpbInstockVO getYpbInstockVO(String instockNumber);//饮片入库

    List<WptpYpbInstockFile> listWptpYpbInstockFiles(String instockNo);//入库文件

     WptpYpbSaleVO getYpbSaleVO(String traceCode);//饮片销售

    /**
     * 把主机代码转化为企业名称
     * @param hostCode
     * @return
     */
     String getEntNameByHostCode(String hostCode);
}
