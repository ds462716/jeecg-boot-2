package org.jeecg.modules.demo.trace.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.api.HostCodeCheck;
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
import org.jeecg.modules.demo.trace.service.AskUpperLinkService;
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
                return JSONArray.toJSON(new Result(true, "操作成功", 200, convertEntityToVOService.handleCsInfo(wptpCsInfo), new Date().getTime())).toString();
            case "11":
                QueryWrapper<WptpMedicineSale> medicineSaleQueryWrapper = new QueryWrapper<>();
                medicineSaleQueryWrapper.eq("trace_code", traceCode);
                medicineSaleQueryWrapper.eq("deleted", "0");
                WptpMedicineSale wptpMedicineSale = iWptpMedicineSaleService.getBaseMapper().selectOne(medicineSaleQueryWrapper);
                if (oConvertUtils.isEmpty(wptpMedicineSale))
                    return JSONArray.toJSON(new Result(true, "无相关销售数据", 200, new Date().getTime())).toString();
                return JSONArray.toJSON(new Result(true, "操作成功", 200, convertEntityToVOService.handleMedicineSale(wptpMedicineSale), new Date().getTime())).toString();
            case "31":
                QueryWrapper<WptpYpbSale> ypbSaleQueryWrapper = new QueryWrapper<>();
                ypbSaleQueryWrapper.eq("trace_code", traceCode);
                ypbSaleQueryWrapper.eq("deleted", "0");
                WptpYpbSale wptpYpbSale = iWptpYpbSaleService.getBaseMapper().selectOne(ypbSaleQueryWrapper);
                if (oConvertUtils.isEmpty(wptpYpbSale))
                    return JSONArray.toJSON(new Result(true, "无相关销售数据", 200, new Date().getTime())).toString();
                return JSONArray.toJSON(new Result(true, "操作成功", 200, convertEntityToVOService.handleYpbSale(wptpYpbSale), new Date().getTime())).toString();
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
                    WptpYpPack wptpYpPack = wptpYpPackService.getBaseMapper().selectOne(queryWrapper);
                    if (!oConvertUtils.isEmpty(wptpYpPack)) {
                        processNo = wptpYpPack.getProcessNo();
                    }
                } else if (wptpYpSale.getSource().contains("1")) {
                    QueryWrapper<WptpYpProcess> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("process_no", wptpYpSale.getSourceNo());
                    WptpYpProcess wptpYpProcess = iWptpYpProcessService.getBaseMapper().selectOne(queryWrapper);
                    if (!oConvertUtils.isEmpty(wptpYpProcess)) {
                        processNo = wptpYpProcess.getProcessNo();
                    }
                }

                paramMap.put("main_id", processNo);
                List<WptpYpProcessFile> fileList = iWptpYpProcessFileService.getBaseMapper().selectByMap(paramMap);
                List<String> paths = new ArrayList<>();//保存图片路径
                for (WptpYpProcessFile file :
                        fileList) {
                    paths.add(file.getPath());
                }
                WptpYpSaleVO wptpYpSaleVO = convertEntityToVOService.handleYpSale(wptpYpSale);
                wptpYpSaleVO.setReport(paths);
                paramMap.clear();
                return JSONArray.toJSON(new Result(true, "操作成功", 200, wptpYpSaleVO, new Date().getTime())).toString();

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
                List<WptpProcessFile> files = iWptpProcessFileService.getBaseMapper().selectList(processFileQueryWrapper);
                List<String> filePaths = new ArrayList<>();//保存图片路径
                for (WptpProcessFile file :
                        files) {
                    filePaths.add(file.getPath());
                }
                WptpSaleVO wptpSaleVO = new WptpSaleVO();//展示类
                BeanUtils.copyProperties(sale, wptpSaleVO);
                wptpSaleVO.setReport(filePaths);
                return JSONArray.toJSON(new Result(true, "操作成功", 200, wptpSaleVO, new Date().getTime())).toString();

        }
        return JSONArray.toJSON(new Result(false, "操作失败没找到相应流程", 500, new Date().getTime())).toString();
    }
}
