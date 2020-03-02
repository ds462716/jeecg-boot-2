package org.jeecg.modules.demo.trace.service;

import org.jeecg.modules.demo.baseinfo.entity.WptpBase;
import org.jeecg.modules.demo.baseinfo.vo.WptpBaseVO;
import org.jeecg.modules.demo.blockmedicinal.entity.WptpBlockMeidicinal;
import org.jeecg.modules.demo.blockmedicinal.vo.WptpBlockMeidicinalVO;
import org.jeecg.modules.demo.csinfo.entity.WptpCsInfo;
import org.jeecg.modules.demo.csinfo.vo.WptpCsInfoVO;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstock;
import org.jeecg.modules.demo.medicinebinstock.vo.WptpMedicineInstockVO;
import org.jeecg.modules.demo.medicinebsale.entity.WptpMedicineSale;
import org.jeecg.modules.demo.medicinebsale.vo.WptpMedicineSaleVO;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantInfo;
import org.jeecg.modules.demo.plantinfo.vo.WptpPlantInfoVO;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessInfo;
import org.jeecg.modules.demo.processinfo.vo.WptpProcessInfoVO;
import org.jeecg.modules.demo.sale.entity.WptpSale;
import org.jeecg.modules.demo.sale.vo.WptpSaleVO;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstock;
import org.jeecg.modules.demo.ypbinstock.vo.WptpYpbInstockVO;
import org.jeecg.modules.demo.ypbsale.entity.WptpYpbSale;
import org.jeecg.modules.demo.ypbsale.vo.WptpYpbSaleVO;
import org.jeecg.modules.demo.yppack.entity.WptpYpPack;
import org.jeecg.modules.demo.yppack.vo.WptpYpPackVO;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcess;
import org.jeecg.modules.demo.ypprocess.vo.WptpYpProcessVO;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstock;
import org.jeecg.modules.demo.ypprocessinstock.vo.WptpYpInstockVO;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSale;
import org.jeecg.modules.demo.ypprocesssale.vo.WptpYpSaleVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConvertEntityToVOService {
    /**
     * 把作业信息实体类转为VO
     *
     * @return
     */
    List<WptpPlantInfoVO> handlePlantInfoList(List<WptpPlantInfo> entityList);

    /**
     * 把加工实体类转为VO
     *
     * @return
     */
    WptpProcessInfoVO handleProcessInfo(WptpProcessInfo wptpProcessInfo);

    /**
     * 把采收批次实体类转为VO
     *
     * @return
     */
    WptpCsInfoVO handleCsInfo(WptpCsInfo wptpCsInfo);

    /**
     * 把种植销售的实体类转为VO
     */
    WptpSaleVO handleSale(WptpSale wptpSale);

    /**
     * 把档案实体类转为VO
     *
     * @param pageList
     * @return
     */
    WptpBlockMeidicinalVO handleBlockMeidicinal(WptpBlockMeidicinal wptpBlockMeidicinal);

    /**
     * 把基地实体类转为VO
     */
    WptpBaseVO handleBase(WptpBase wptpBase);

    /**
     * 把药材销售实体类转为VO
     *
     * @param wptpMedicineSale
     * @return
     */
    WptpMedicineSaleVO handleMedicineSale(WptpMedicineSale wptpMedicineSale);

    /**
     * 把药材入库实体类转为VO
     *
     * @param wptpMedicineInstock
     * @return
     */
    WptpMedicineInstockVO handleMedicineInstock(WptpMedicineInstock wptpMedicineInstock);

    /**
     * 把饮片加工-饮片销售实体类转为VO
     *
     * @param wptpYpSale
     * @return
     */
    WptpYpSaleVO handleYpSale(WptpYpSale wptpYpSale);

    /**
     * 把饮片加工-饮片包装实体类转为VO
     */
    WptpYpPackVO handleYpPack(WptpYpPack wptpYpPack);

    /**
     * 把饮片加工实体类转为VO
     */
    WptpYpProcessVO handleYpProcess(WptpYpProcess wptpYpProcess);

    /**
     * 把饮片加工-饮片入库实体类转为VO
     *
     * @param wptpYpInstock
     * @return
     */
    WptpYpInstockVO handleYpInstock(WptpYpInstock wptpYpInstock);

    /**
     * 把饮片经营-饮片销售实体类转为VO
     *
     * @param pageList
     * @return
     */
    WptpYpbSaleVO handleYpbSale(WptpYpbSale wptpYpbSale);

    /**
     * 把饮片经营-饮片入库实体类转为VO
     *
     * @param pageList
     * @return
     */
    WptpYpbInstockVO handleYpbInstock(WptpYpbInstock wptpYpbInstock);

}
