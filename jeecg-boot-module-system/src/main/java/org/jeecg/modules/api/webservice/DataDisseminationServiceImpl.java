package org.jeecg.modules.api.webservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.ValidField;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.api.DataDissemination;
import org.jeecg.modules.api.HostCodeCheck;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseService;
import org.jeecg.modules.demo.csinfo.entity.WptpCsInfo;
import org.jeecg.modules.demo.csinfo.service.IWptpCsInfoService;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
import org.jeecg.modules.demo.medicinebsale.entity.WptpMedicineSale;
import org.jeecg.modules.demo.medicinebsale.service.IWptpMedicineSaleService;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessFileService;
import org.jeecg.modules.demo.sale.entity.WptpSale;
import org.jeecg.modules.demo.sale.service.IWptpSaleService;
import org.jeecg.modules.demo.trace.service.ConvertEntityToVOService;
import org.jeecg.modules.demo.ypbsale.entity.WptpYpbSale;
import org.jeecg.modules.demo.ypbsale.service.IWptpYpbSaleService;
import org.jeecg.modules.demo.yppack.entity.WptpYpPack;
import org.jeecg.modules.demo.yppack.service.IWptpYpPackService;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcess;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcessFile;
import org.jeecg.modules.demo.ypprocess.service.IWptpYpProcessFileService;
import org.jeecg.modules.demo.ypprocess.service.IWptpYpProcessService;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSale;
import org.jeecg.modules.demo.ypprocesssale.service.IWptpYpSaleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据下发
 */
@Component
@WebService(name = "dataDisseminationService", targetNamespace = "http://webservice.esb.sphchina.com", endpointInterface = "org.jeecg.modules.api.webservice.DataDisseminationService")
public class DataDisseminationServiceImpl implements DataDisseminationService {

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

    @Override
    public String dataDissemination(@NotBlank String jsonStr) {
        String trim = jsonStr.trim();
        DataDissemination dataDissemination = new DataDissemination();
        BeanUtils.copyProperties(JSONObject.parseObject(trim, DataDissemination.class), dataDissemination);
        Result result = ValidField.checkField(dataDissemination);
        if (!result.isSuccess()) return JSONArray.toJSON(result).toString();
        String hostCode = dataDissemination.getHostCode();

        Result checkResult = hostCodeCheck.checkHostCode(hostCode);
        if (!checkResult.isSuccess()) return JSONArray.toJSON(checkResult).toString();
        /**
         * 根据source判断操作哪张表
         * 05：采收批次信息
         * 04：种植端出厂销售;
         * 11: 药材经营出厂销售;
         * 23: 饮片加工饮片销售;
         * 31: 饮片经营饮片销售;
         */
        String source = dataDissemination.getSource();

        switch (source) {
            /**
             * 采收批次信息
             */
            case "05":
                Map<String, Object> map = new HashMap<>();

                QueryWrapper<WptpCsInfo> csInfoQueryWrapper = new QueryWrapper<>();
                csInfoQueryWrapper.between("create_time", DateUtils.getDateBefore(new Date(), 2), new Date());
                csInfoQueryWrapper.eq("deleted", "0");
                List<WptpCsInfo> wptpCsInfos = iWptpCsInfoService.getBaseMapper().selectList(csInfoQueryWrapper);
                if (wptpCsInfos.isEmpty())
                    return JSONArray.toJSON(new Result(true, "无数据", 200, new Date().getTime())).toString();
                List<DataDisseminationCsInfo> dataDisseminationCsInfoList = new ArrayList<>();//展示类集合
                for (WptpCsInfo csInfo :
                        wptpCsInfos) {
                    /* WptpCsInfoVO wptpCsInfoVO = convertEntityToVOService.handleCsInfo(csInfo);
                     */
                    DataDisseminationCsInfo dataDisseminationCsInfo = new DataDisseminationCsInfo();
                    BeanUtils.copyProperties(csInfo, dataDisseminationCsInfo);
                    dataDisseminationCsInfoList.add(dataDisseminationCsInfo);
                }
                return JSONArray.toJSON(new Result(true, "操作成功", 200, dataDisseminationCsInfoList, new Date().getTime())).toString();
            case "11":
                QueryWrapper<WptpMedicineSale> medicineSaleQueryWrapper = new QueryWrapper<>();
                medicineSaleQueryWrapper.between("create_time", DateUtils.getDateBefore(new Date(), 2), new Date());
                medicineSaleQueryWrapper.eq("deleted", "0");
                List<WptpMedicineSale> wptpMedicineSales = iWptpMedicineSaleService.getBaseMapper().selectList(medicineSaleQueryWrapper);

                if (wptpMedicineSales.isEmpty())
                    return JSONArray.toJSON(new Result(true, "无数据", 200, new Date().getTime())).toString();
                List<DataDisseminationSale> dataDisseminationSaleList = new ArrayList<>();//展示类集合
                for (WptpMedicineSale w :
                        wptpMedicineSales) {
                    /*  WptpMedicineSaleVO wptpMedicineSaleVO = convertEntityToVOService.handleMedicineSale(w);*/
                    DataDisseminationSale disseminationSale = new DataDisseminationSale();
                    BeanUtils.copyProperties(w, disseminationSale);
                    dataDisseminationSaleList.add(disseminationSale);
                }
                return JSONArray.toJSON(new Result(true, "操作成功", 200, dataDisseminationSaleList, new Date().getTime())).toString();
            case "31":
                QueryWrapper<WptpYpbSale> ypbSaleQueryWrapper = new QueryWrapper<>();
                ypbSaleQueryWrapper.between("create_date", DateUtils.getDateBefore(new Date(), 2), new Date());
                ypbSaleQueryWrapper.eq("deleted", "0");
                List<WptpYpbSale> wptpYpbSales = iWptpYpbSaleService.getBaseMapper().selectList(ypbSaleQueryWrapper);
                if (wptpYpbSales.isEmpty())
                    return JSONArray.toJSON(new Result(true, "无数据", 200, new Date().getTime())).toString();
                List<DataDisseminationSale> ypBusinessSaleList = new ArrayList<>();//展示类集合
                for (WptpYpbSale w :
                        wptpYpbSales) {
                    /* WptpYpbSaleVO wptpYpbSaleVO = convertEntityToVOService.handleYpbSale(w);*/
                    DataDisseminationSale disseminationSale = new DataDisseminationSale();
                    disseminationSale.setMedicineName(w.getYpName());
                    disseminationSale.setGuige(w.getYpGuige());
                    ypBusinessSaleList.add(disseminationSale);
                }
                return JSONArray.toJSON(new Result(true, "操作成功", 200, ypBusinessSaleList, new Date().getTime())).toString();
            case "23":
                QueryWrapper<WptpYpSale> ypSaleQueryWrapper = new QueryWrapper<>();
                ypSaleQueryWrapper.eq("deleted", "0");
                ypSaleQueryWrapper.between("create_date", DateUtils.getDateBefore(new Date(), 2), new Date());
                List<WptpYpSale> wptpYpSales = iWptpYpSaleService.getBaseMapper().selectList(ypSaleQueryWrapper);
                if (wptpYpSales.isEmpty())
                    return JSONArray.toJSON(new Result(true, "无数据", 200, new Date().getTime())).toString();
                List<DataDisseminationSale> ypProcessSaleList = new ArrayList<>();
                for (WptpYpSale w :
                        wptpYpSales) {
                    ConcurrentHashMap<String, Object> paramMap = new ConcurrentHashMap();
                    String processNo = "";//加工单号
                    if ("0".equals(w.getSource())) {//0代表来源是饮片包装
                        QueryWrapper<WptpYpPack> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("pack_no", w.getSourceNo());
                        queryWrapper.eq("deleted", "0");
                        WptpYpPack wptpYpPack = wptpYpPackService.getBaseMapper().selectOne(queryWrapper);
                        if (oConvertUtils.isEmpty(wptpYpPack)) continue;
                        processNo = wptpYpPack.getProcessNo();
                    } else if ("1".equals(w.getSource())) {
                        QueryWrapper<WptpYpProcess> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("process_no", w.getSourceNo());
                        queryWrapper.eq("deleted", "0");
                        WptpYpProcess wptpYpProcess = iWptpYpProcessService.getBaseMapper().selectOne(queryWrapper);
                        processNo = wptpYpProcess.getProcessNo();
                    }

                    paramMap.put("main_id", processNo);
                    List<WptpYpProcessFile> fileList = iWptpYpProcessFileService.getBaseMapper().selectByMap(paramMap);
                    List<String> paths = new ArrayList<>();//保存图片路径
                    for (WptpYpProcessFile file :
                            fileList) {
                        paths.add(file.getPath());
                    }
      /*              WptpYpSaleVO wptpYpSaleVO = new WptpYpSaleVO();//展示类
                    BeanUtils.copyProperties(w,wptpYpSaleVO);*/
                    /*WptpYpSaleVO wptpYpSaleVO = convertEntityToVOService.handleYpSale(w);*/
                    DataDisseminationSale disseminationSale = new DataDisseminationSale();
                    BeanUtils.copyProperties(w, disseminationSale);
                    disseminationSale.setReport(paths);
                    disseminationSale.setMedicinalOrigin(w.getMedicineOrigin());
                    disseminationSale.setOutTime(w.getSaleTime());
                    disseminationSale.setMedicineName(w.getYpName());
                    ypProcessSaleList.add(disseminationSale);
                    paramMap.clear();
                }
                return JSONArray.toJSON(new Result(true, "操作成功", 200, ypProcessSaleList, new Date().getTime())).toString();
            case "04":
                QueryWrapper<WptpSale> saleQueryWrapper = new QueryWrapper<>();
                saleQueryWrapper.eq("deleted", "0");
                saleQueryWrapper.between("create_time", DateUtils.getDateBefore(new Date(), 2), new Date());
                List<WptpSale> sales = iWptpSaleService.getBaseMapper().selectList(saleQueryWrapper);
                if (sales.isEmpty())
                    return JSONArray.toJSON(new Result(true, "无数据", 200, new Date().getTime())).toString();
                List<DataDisseminationSale> plantDissemination = new ArrayList<>();
                for (WptpSale w :
                        sales) {
                    if (!w.getSource().contains("1")) continue;
                    ConcurrentHashMap<String, Object> paramMap = new ConcurrentHashMap();
                    paramMap.put("main_id", w.getMedicineBatch());
                    List<WptpProcessFile> fileList = iWptpProcessFileService.getBaseMapper().selectByMap(paramMap);
                    List<String> paths = new ArrayList<>();//保存图片路径
                    for (WptpProcessFile file :
                            fileList) {
                        paths.add(file.getPath());
                    }
                    DataDisseminationSale disseminationSale = new DataDisseminationSale();//展示类
                    BeanUtils.copyProperties(w, disseminationSale);
                    disseminationSale.setCategoryCode(w.getMedicineName());
                    disseminationSale.setOutTime(w.getSaleTime());
                    /**
                     * 根据药材编码查询一个药材
                     */
                    QueryWrapper<WptpMedicinal> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("medicinal_code", w.getMedicineName());
                    queryWrapper.eq("deleted", "0");
                    WptpMedicinal wptpMedicinal = iWptpMedicinalService.getBaseMapper().selectOne(queryWrapper);
                    disseminationSale.setReport(paths);
                    if (!oConvertUtils.isEmpty(wptpMedicinal)) {
                        disseminationSale.setCategoryName(wptpMedicinal.getName());
                        disseminationSale.setMedicineName(wptpMedicinal.getName());
                    }
                    plantDissemination.add(disseminationSale);
                    paramMap.clear();
                }
                return JSONArray.toJSON(new Result(true, "操作成功", 200, plantDissemination, new Date().getTime())).toString();

        }
        return JSONArray.toJSON(new Result(false, "操作失败没找到相应流程", 500, new Date().getTime())).toString();
    }


}
