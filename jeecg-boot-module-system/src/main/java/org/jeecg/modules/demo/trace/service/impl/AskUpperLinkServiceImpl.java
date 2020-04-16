package org.jeecg.modules.demo.trace.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.api.HostCodeCheck;
import org.jeecg.modules.api.webservice.DataDisseminationCsInfo;
import org.jeecg.modules.api.webservice.DataDisseminationSale;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseService;
import org.jeecg.modules.demo.csinfo.entity.WptpCsInfo;
import org.jeecg.modules.demo.csinfo.service.IWptpCsInfoService;
import org.jeecg.modules.demo.csinfo.vo.WptpCsInfoVO;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
import org.jeecg.modules.demo.medicinebsale.entity.WptpMedicineSale;
import org.jeecg.modules.demo.medicinebsale.service.IWptpMedicineSaleService;
import org.jeecg.modules.demo.medicinebsale.vo.WptpMedicineSaleVO;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessFileService;
import org.jeecg.modules.demo.sale.entity.WptpSale;
import org.jeecg.modules.demo.sale.service.IWptpSaleService;
import org.jeecg.modules.demo.sale.vo.WptpSaleVO;
import org.jeecg.modules.demo.trace.service.AskUpperLinkService;
import org.jeecg.modules.demo.trace.service.ConvertEntityToVOService;
import org.jeecg.modules.demo.ypbsale.entity.WptpYpbSale;
import org.jeecg.modules.demo.ypbsale.service.IWptpYpbSaleService;
import org.jeecg.modules.demo.ypbsale.vo.WptpYpbSaleVO;
import org.jeecg.modules.demo.yppack.entity.WptpYpPack;
import org.jeecg.modules.demo.yppack.service.IWptpYpPackService;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcess;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcessFile;
import org.jeecg.modules.demo.ypprocess.service.IWptpYpProcessFileService;
import org.jeecg.modules.demo.ypprocess.service.IWptpYpProcessService;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSale;
import org.jeecg.modules.demo.ypprocesssale.service.IWptpYpSaleService;
import org.jeecg.modules.demo.ypprocesssale.vo.WptpYpSaleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class AskUpperLinkServiceImpl implements AskUpperLinkService {

    @Autowired
    private HostCodeCheck hostCodeCheck;
    @Autowired
    private IWptpCsInfoService iWptpCsInfoService;
    @Autowired
    private IWptpMedicineSaleService iWptpMedicineSaleService;
    @Autowired
    private IWptpYpbSaleService iWptpYpbSaleService;
    @Autowired
    private IWptpYpSaleService iWptpYpSaleService;
    @Autowired
    private IWptpSaleService iWptpSaleService;
    @Autowired
    private IWptpBaseService iWptpBaseService;
    @Autowired
    private IWptpProcessFileService iWptpProcessFileService;
    @Autowired
    private IWptpYpPackService wptpYpPackService;
    @Autowired
    private IWptpYpProcessService iWptpYpProcessService;
    @Autowired
    private IWptpYpProcessFileService iWptpYpProcessFileService;
    @Autowired
    private ConvertEntityToVOService convertEntityToVOService;
    @Autowired
    private IWptpMedicinalService iWptpMedicinalService;
    private static final String IMG_URL_PREIX = "http://180.168.130.217:9010/img";//图片路径前缀

    @Override
    public String askUpperLinkForOneDate(@NotBlank String traceCode) {
        String trim = traceCode.replace(" ", "");
        /**
         * 根据source判断操作哪张表
         * 05：采收批次信息
         * 04：种植端出厂销售;
         * 11: 药材经营出厂销售;
         * 23: 饮片加工饮片销售;
         * 31: 饮片经营饮片销售;
         */

        switch (trim.substring(1, 3)) {
            /**
             * 采收批次信息
             */
            case "05":
                Map<String, Object> map = new HashMap<>();

                QueryWrapper<WptpCsInfo> csInfoQueryWrapper = new QueryWrapper<>();
                csInfoQueryWrapper.eq("trace_code", traceCode);
                csInfoQueryWrapper.eq("deleted", "0");
                WptpCsInfo wptpCsInfo = iWptpCsInfoService.getBaseMapper().selectOne(csInfoQueryWrapper);
                if (oConvertUtils.isEmpty(wptpCsInfo))
                    return JSONArray.toJSON(new Result(true, "无数据", 200, new Date().getTime())).toString();
                WptpCsInfoVO wptpCsInfoVO = convertEntityToVOService.handleCsInfo(wptpCsInfo);
                DataDisseminationCsInfo dataDisseminationCsInfo = new DataDisseminationCsInfo();
                BeanUtils.copyProperties(wptpCsInfoVO,dataDisseminationCsInfo);
                return JSONArray.toJSON(new Result(true, "操作成功", 200,dataDisseminationCsInfo, new Date().getTime())).toString();
            case "11":
                QueryWrapper<WptpMedicineSale> medicineSaleQueryWrapper = new QueryWrapper<>();
                medicineSaleQueryWrapper.eq("trace_code", traceCode);
                medicineSaleQueryWrapper.eq("deleted", "0");
                WptpMedicineSale wptpMedicineSale = iWptpMedicineSaleService.getBaseMapper().selectOne(medicineSaleQueryWrapper);
                if (oConvertUtils.isEmpty(wptpMedicineSale))
                    return JSONArray.toJSON(new Result(true, "无相关销售数据", 200, new Date().getTime())).toString();
                WptpMedicineSaleVO medicineSaleVO = convertEntityToVOService.handleMedicineSale(wptpMedicineSale);
                DataDisseminationSale dataDisseminationMedicine = new DataDisseminationSale();
                BeanUtils.copyProperties(medicineSaleVO,dataDisseminationMedicine);
                return JSONArray.toJSON(new Result(true, "操作成功", 200, dataDisseminationMedicine, new Date().getTime())).toString();
            case "31":
                QueryWrapper<WptpYpbSale> ypbSaleQueryWrapper = new QueryWrapper<>();
                ypbSaleQueryWrapper.eq("trace_code", traceCode);
                ypbSaleQueryWrapper.eq("deleted", "0");
                WptpYpbSale wptpYpbSale = iWptpYpbSaleService.getBaseMapper().selectOne(ypbSaleQueryWrapper);
                if (oConvertUtils.isEmpty(wptpYpbSale))
                    return JSONArray.toJSON(new Result(true, "无相关销售数据", 200, new Date().getTime())).toString();
                WptpYpbSaleVO wptpYpbSaleVO = convertEntityToVOService.handleYpbSale(wptpYpbSale);
                DataDisseminationSale dataDisseminationYpb = new DataDisseminationSale();
                BeanUtils.copyProperties(wptpYpbSaleVO,dataDisseminationYpb);
                dataDisseminationYpb.setMedicineName(wptpYpbSaleVO.getYpName());
                dataDisseminationYpb.setGuige(wptpYpbSaleVO.getYpGuige());
                /**
                 * 主数据码以后再说
                 */
                dataDisseminationYpb.setMainCode("");
                return JSONArray.toJSON(new Result(true, "操作成功", 200, dataDisseminationYpb, new Date().getTime())).toString();
            case "23":
                QueryWrapper<WptpYpSale> ypSaleQueryWrapper = new QueryWrapper<>();
                ypSaleQueryWrapper.eq("trace_code", traceCode);
                ypSaleQueryWrapper.eq("deleted", "0");
                WptpYpSale wptpYpSale = iWptpYpSaleService.getBaseMapper().selectOne(ypSaleQueryWrapper);
                if (oConvertUtils.isEmpty(wptpYpSale))
                    return JSONArray.toJSON(new Result(true, "无相关销售数据", 200, new Date().getTime())).toString();
                ConcurrentHashMap<String, Object> paramMap = new ConcurrentHashMap();
                String processNo = "";//加工单号
                if (wptpYpSale.getSource().contains("0")) {//0代表来源是饮片包装
                    QueryWrapper<WptpYpPack> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("pack_no", wptpYpSale.getSourceNo());
                    queryWrapper.eq("deleted", "0");
                    WptpYpPack wptpYpPack = wptpYpPackService.getBaseMapper().selectOne(queryWrapper);
                    if (!oConvertUtils.isEmpty(wptpYpPack)) {
                        processNo = wptpYpPack.getProcessNo();
                    }
                } else if (wptpYpSale.getSource().contains("1")) {
                    QueryWrapper<WptpYpProcess> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("process_no", wptpYpSale.getSourceNo());
                    queryWrapper.eq("deleted", "0");
                    WptpYpProcess wptpYpProcess = iWptpYpProcessService.getBaseMapper().selectOne(queryWrapper);
                    if (!oConvertUtils.isEmpty(wptpYpProcess)) {
                        processNo = wptpYpProcess.getProcessNo();
                    }
                }

                paramMap.put("main_id", processNo);
                paramMap.put("deleted", "0");
                List<WptpYpProcessFile> fileList = iWptpYpProcessFileService.getBaseMapper().selectByMap(paramMap);
                List<String> paths = new ArrayList<>();//保存图片路径
                for (WptpYpProcessFile file :
                        fileList) {
                    paths.add(file.getPath());
                }
                WptpYpSaleVO wptpYpSaleVO = convertEntityToVOService.handleYpSale(wptpYpSale);
                wptpYpSaleVO.setReport(paths);
                paramMap.clear();
                DataDisseminationSale dataDisseminationYp = new DataDisseminationSale();
                BeanUtils.copyProperties(wptpYpSaleVO,dataDisseminationYp);
                dataDisseminationYp.setProductBatch(wptpYpSaleVO.getBatchNo());
                dataDisseminationYp.setMedicineName(wptpYpSaleVO.getYpName());
                dataDisseminationYp.setMedicinalOrigin(wptpYpSaleVO.getMedicineOrigin());
                dataDisseminationYp.setOutTime(wptpYpSaleVO.getSaleTime());
                return JSONArray.toJSON(new Result(true, "操作成功", 200, dataDisseminationYp, new Date().getTime())).toString();

            case "04":
                QueryWrapper<WptpSale> saleQueryWrapper = new QueryWrapper<>();
                saleQueryWrapper.eq("trace_code", traceCode);
                saleQueryWrapper.eq("deleted", "0");
                WptpSale sale = iWptpSaleService.getBaseMapper().selectOne(saleQueryWrapper);
                if (oConvertUtils.isEmpty(sale))
                    return JSONArray.toJSON(new Result(true, "无相关销售数据", 200, new Date().getTime())).toString();
                if (!sale.getSource().contains("1"))
                    return JSONArray.toJSON(new Result(true, "无相关初加工文件数据", 200, new Date().getTime())).toString();
                QueryWrapper<WptpProcessFile> processFileQueryWrapper = new QueryWrapper<>();
                processFileQueryWrapper.eq("main_id", sale.getMedicineBatch());
                processFileQueryWrapper.eq("deleted", "0");
                List<WptpProcessFile> files = iWptpProcessFileService.getBaseMapper().selectList(processFileQueryWrapper);
                List<String> filePaths = new ArrayList<>();//保存图片路径
                for (WptpProcessFile file :
                        files) {
                    filePaths.add(file.getPath());
                }
                WptpSaleVO wptpSaleVO = new WptpSaleVO();//展示类
                BeanUtils.copyProperties(sale, wptpSaleVO);
                wptpSaleVO.setReport(filePaths);
                DataDisseminationSale dataDisseminationSale = new DataDisseminationSale();
                BeanUtils.copyProperties(wptpSaleVO,dataDisseminationSale);
                dataDisseminationSale.setOutTime(wptpSaleVO.getSaleTime());
                QueryWrapper<WptpMedicinal> wptpMedicinalQueryWrapper = new QueryWrapper<>();
                wptpMedicinalQueryWrapper.eq("medicinal_code",wptpSaleVO.getMedicineName());
                wptpMedicinalQueryWrapper.eq("deleted","0");
                WptpMedicinal wptpMedicinal = iWptpMedicinalService.getBaseMapper().selectOne(wptpMedicinalQueryWrapper);
                if (!oConvertUtils.isEmpty(wptpMedicinal)){
                    dataDisseminationSale.setMedicineName(wptpMedicinal.getName());
                    dataDisseminationSale.setCategoryName(wptpMedicinal.getName());
                }
                dataDisseminationSale.setCategoryCode(wptpSaleVO.getMedicineName());

                return JSONArray.toJSON(new Result(true, "操作成功", 200, dataDisseminationSale, new Date().getTime())).toString();

        }
        return JSONArray.toJSON(new Result(false, "操作失败没找到相应流程", 500, new Date().getTime())).toString();
    }
}
