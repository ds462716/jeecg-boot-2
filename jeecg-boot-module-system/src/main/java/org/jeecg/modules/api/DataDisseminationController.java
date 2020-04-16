package org.jeecg.modules.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.ValidField;
import org.jeecg.modules.demo.baseinfo.entity.WptpBase;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseService;
import org.jeecg.modules.demo.csinfo.entity.WptpCsInfo;
import org.jeecg.modules.demo.csinfo.service.IWptpCsInfoService;
import org.jeecg.modules.demo.medicinebsale.entity.WptpMedicineSale;
import org.jeecg.modules.demo.medicinebsale.service.IWptpMedicineSaleService;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessFileService;
import org.jeecg.modules.demo.sale.entity.WptpSale;
import org.jeecg.modules.demo.sale.service.IWptpSaleService;
import org.jeecg.modules.demo.sale.vo.WptpSaleVO;
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
import org.jeecg.modules.demo.ypprocesssale.vo.WptpYpSaleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class DataDisseminationController {
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

    @RequestMapping("/dataDissemination")
    public synchronized String dataDissemination(@NotBlank String jsonStr) {
        DataDissemination dataDissemination = new DataDissemination();
        BeanUtils.copyProperties(JSONObject.parseObject(jsonStr, DataDissemination.class), dataDissemination);
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
                List<WptpCsInfo> wptpCsInfos = iWptpCsInfoService.getBaseMapper().selectList(csInfoQueryWrapper);
                if (wptpCsInfos.isEmpty())
                    return JSONArray.toJSON(new Result(true, "无数据", 200, new Date().getTime())).toString();
                for (WptpCsInfo csInfo :
                        wptpCsInfos) {
                    map.put("base_code", csInfo.getBaseCode());
                    List<WptpBase> wptpBases = iWptpBaseService.getBaseMapper().selectByMap(map);
                    csInfo.setBaseName(wptpBases.get(0).getBaseName());
                    map.clear();
                }
                return JSONArray.toJSON(new Result(true, "操作成功", 200, wptpCsInfos, new Date().getTime())).toString();
            case "11":
                QueryWrapper<WptpMedicineSale> medicineSaleQueryWrapper = new QueryWrapper<>();
                medicineSaleQueryWrapper.between("create_time", DateUtils.getDateBefore(new Date(), 2), new Date());
                List<WptpMedicineSale> wptpMedicineSales = iWptpMedicineSaleService.getBaseMapper().selectList(medicineSaleQueryWrapper);
                if (wptpMedicineSales.isEmpty())
                    return JSONArray.toJSON(new Result(true, "无数据", 200, new Date().getTime())).toString();
                return JSONArray.toJSON(new Result(true, "操作成功", 200, wptpMedicineSales, new Date().getTime())).toString();
            case "31":
                QueryWrapper<WptpYpbSale> ypbSaleQueryWrapper = new QueryWrapper<>();
                ypbSaleQueryWrapper.between("create_time", DateUtils.getDateBefore(new Date(), 2), new Date());
                List<WptpYpbSale> wptpYpbSales = iWptpYpbSaleService.getBaseMapper().selectList(ypbSaleQueryWrapper);
                if (wptpYpbSales.isEmpty())
                    return JSONArray.toJSON(new Result(true, "无数据", 200, new Date().getTime())).toString();
                return JSONArray.toJSON(new Result(true, "操作成功", 200, wptpYpbSales, new Date().getTime())).toString();
            case "23":
                QueryWrapper<WptpYpSale> ypSaleQueryWrapper = new QueryWrapper<>();
                ypSaleQueryWrapper.between("create_time", DateUtils.getDateBefore(new Date(), 2), new Date());
                List<WptpYpSale> wptpYpSales = iWptpYpSaleService.getBaseMapper().selectList(ypSaleQueryWrapper);
                if (wptpYpSales.isEmpty())
                    return JSONArray.toJSON(new Result(true, "无数据", 200, new Date().getTime())).toString();
                List<WptpYpSaleVO> wptpYpSaleVOS = new ArrayList<>();
                for (WptpYpSale w :
                        wptpYpSales) {
                    ConcurrentHashMap<String, Object> paramMap = new ConcurrentHashMap();
                    String processNo = "";//加工单号
                    if ("0".equals(w.getSource())) {//0代表来源是饮片包装
                        QueryWrapper<WptpYpPack> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("pack_no", w.getSourceNo());
                        WptpYpPack wptpYpPack = wptpYpPackService.getBaseMapper().selectOne(queryWrapper);
                        processNo = wptpYpPack.getProcessNo();
                    } else if ("1".equals(w.getSource())) {
                        QueryWrapper<WptpYpProcess> queryWrapper = new QueryWrapper<>();
                        queryWrapper.eq("process_no", w.getSourceNo());
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
                    WptpYpSaleVO wptpYpSaleVO = new WptpYpSaleVO();//展示类
                    BeanUtils.copyProperties(w, wptpYpSaleVO);
                    wptpYpSaleVO.setReport(paths);
                    wptpYpSaleVOS.add(wptpYpSaleVO);
                    paramMap.clear();
                }
                return JSONArray.toJSON(new Result(true, "操作成功", 200, wptpYpSaleVOS, new Date().getTime())).toString();
            case "04":
                QueryWrapper<WptpSale> saleQueryWrapper = new QueryWrapper<>();
                saleQueryWrapper.between("create_time", DateUtils.getDateBefore(new Date(), 2), new Date());
                List<WptpSale> sales = iWptpSaleService.getBaseMapper().selectList(saleQueryWrapper);
                if (sales.isEmpty())
                    return JSONArray.toJSON(new Result(true, "无数据", 200, new Date().getTime())).toString();
                List<WptpSaleVO> wptpSaleVOS = new ArrayList<>();
                for (WptpSale w :
                        sales) {
                    ConcurrentHashMap<String, Object> paramMap = new ConcurrentHashMap();
                    paramMap.put("main_id", w.getMedicineBatch());
                    List<WptpProcessFile> fileList = iWptpProcessFileService.getBaseMapper().selectByMap(paramMap);
                    List<String> paths = new ArrayList<>();//保存图片路径
                    for (WptpProcessFile file :
                            fileList) {
                        paths.add(file.getPath());
                    }
                    WptpSaleVO wptpSaleVO = new WptpSaleVO();//展示类
                    BeanUtils.copyProperties(w, wptpSaleVO);
                    wptpSaleVO.setReport(paths);
                    wptpSaleVOS.add(wptpSaleVO);
                    paramMap.clear();
                }
                return JSONArray.toJSON(new Result(true, "操作成功", 200, wptpSaleVOS, new Date().getTime())).toString();

        }
        return JSONArray.toJSON(new Result(false, "操作失败没找到相应流程", 500, new Date().getTime())).toString();
    }
}
