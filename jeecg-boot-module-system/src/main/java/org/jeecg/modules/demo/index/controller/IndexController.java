package org.jeecg.modules.demo.index.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.baseinfo.entity.WptpBase;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseService;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.service.IWptpEntInfoService;
import org.jeecg.modules.demo.hostcode.service.IWptpHostcodeService;
import org.jeecg.modules.demo.index.vo.EntVO;
import org.jeecg.modules.demo.index.vo.IndexVO;
import org.jeecg.modules.demo.index.vo.MedicinalVO;
import org.jeecg.modules.demo.index.vo.SquareAreaVO;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicineFile;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicineFileService;
import org.jeecg.modules.demo.medicinebsale.entity.WptpMedicineSale;
import org.jeecg.modules.demo.medicinebsale.service.IWptpMedicineSaleService;
import org.jeecg.modules.demo.sale.entity.WptpSale;
import org.jeecg.modules.demo.sale.service.IWptpSaleService;
import org.jeecg.modules.demo.scanner.service.IWptpScannerTimeService;
import org.jeecg.modules.demo.ypbsale.entity.WptpYpbSale;
import org.jeecg.modules.demo.ypbsale.service.IWptpYpbSaleService;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSale;
import org.jeecg.modules.demo.ypprocesssale.service.IWptpYpSaleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 主页
 */
@Api(tags="主页信息")
@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private IWptpEntInfoService iWptpEntInfoService;
    @Autowired
    private IWptpBaseService iWptpBaseService;
    @Autowired
    private IWptpHostcodeService iWptpHostcodeService;
    @Autowired
    private IWptpMedicinalService iWptpMedicinalService;
    @Autowired
    private IWptpSaleService iWptpSaleService;
    @Autowired
    private IWptpYpbSaleService iWptpYpbSaleService;
    @Autowired
    private IWptpYpSaleService iWptpYpSaleService;
    @Autowired
    private IWptpMedicineSaleService iWptpMedicineSaleService;
    @Autowired
    private IWptpMedicineFileService iWptpMedicineFileService;
    @Autowired
    private IWptpScannerTimeService iWptpScannerTimeService;

    /**
     * 主页页面展示数据
     * @return
     */
    @ApiOperation(value = "主页相关展示数据", notes = "主页相关展示数据")
    @GetMapping(value = "/getIndexView")
    public Result<IndexVO> getIndexView() {
        /*QueryWrapper<WptpScannerTime> wptpScannerTimeQueryWrapper = new QueryWrapper<>();*/
/*        wptpScannerTimeQueryWrapper.eq("id","1");*/
/*        List<WptpScannerTime> wptpScannerTimes = iWptpScannerTimeService.getBaseMapper().selectList(wptpScannerTimeQueryWrapper);*/
        Integer count = iWptpScannerTimeService.getBaseMapper().selectCount(null);
        System.out.println(count);
        List<EntVO> entVOS = handleEntInfo();//企业信息分布
         handleBaseInfo();//基地数量
         handleHostInfo();//对接系统数量
        handletraceVarietiesCountInfo();//追溯品种数量
        List<SquareAreaVO> squareAreaVOS = new ArrayList<>();
        squareAreaVOS.add(handleBaseInfo());
        squareAreaVOS.add(handleHostInfo());
        squareAreaVOS.add(handletraceVarietiesCountInfo());
        squareAreaVOS.add(new SquareAreaVO(count,"#EEC900","追溯码扫描次数"));
        String[] medicinalVarieties = new String[]{"种植环节","药材经营","饮片加工","饮片经营"};
        Integer[] traceCodeCount = handleTraceCodeCountInfo();
        List<MedicinalVO> medicinalImages = handleMedicinalImagesInfo();
        IndexVO indexVO = new IndexVO(entVOS, squareAreaVOS, medicinalVarieties, traceCodeCount, medicinalImages);
        Result<IndexVO> result = new Result<>(true,"操作成功",200,indexVO,new Date().getTime());
        return result;
    }
    /**
     * 处理查询出的企业信息
     * @return
     */
    private List<EntVO> handleEntInfo(){
        List<WptpEntInfo> wptpEntInfoList = iWptpEntInfoService.getBaseMapper().selectByMap(null);
        List<EntVO> entVOList = new ArrayList<EntVO>();
        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        for (WptpEntInfo wptpEntInfo:
        wptpEntInfoList) {
            String[] gps = new String[2];
            EntVO entVO =new EntVO();
            BeanUtils.copyProperties(wptpEntInfo,entVO);
            gps[0]= wptpEntInfo.getGpsLongitude();
            gps[1]=wptpEntInfo.getGpsLatitude();
            entVO.setGps(gps);
            entVOList.add(entVO);
        }



        return entVOList;
    }
    /**
     * 处理查询出的基地信息
     * @return
     */
    private SquareAreaVO handleBaseInfo(){
        QueryWrapper<WptpBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted","0");
        Integer baseCount = iWptpBaseService.getBaseMapper().selectCount(queryWrapper);
        SquareAreaVO baseArea = new SquareAreaVO(baseCount, "#1E90FF", "基地数量");
        return baseArea;
    }
    /**
     * 处理查询出的对接系统数量信息
     * @return
     */
    private SquareAreaVO handleHostInfo(){
        Integer hostCount = iWptpHostcodeService.getBaseMapper().selectCount(null);
        SquareAreaVO hostArea = new SquareAreaVO(hostCount, "#76EE00", "对接系统数量");
        return hostArea;
    }
    /**
     * 处理查询出的追溯品种数量信息
     * @return
     */
    private SquareAreaVO handletraceVarietiesCountInfo(){
        QueryWrapper<WptpMedicinal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted","0");
        Integer traceVarietiesCount = iWptpMedicinalService.getBaseMapper().selectCount(queryWrapper);
        SquareAreaVO traceVarietieArea = new SquareAreaVO(traceVarietiesCount, "#EE0000", "追溯品种数量");
        return traceVarietieArea;
    }
    /**
     * 处理查询出的追溯码数量信息
     * @return
     */
    private Integer[] handleTraceCodeCountInfo(){
        QueryWrapper<WptpSale> queryWrapperWptpSale = new QueryWrapper<>();//种植环节
        QueryWrapper<WptpMedicineSale> queryWrapperWptpMedicineSale = new QueryWrapper<>();//药材经营
        QueryWrapper<WptpYpSale> queryWrapperWptpYpSale = new QueryWrapper<>();//饮片加工
        QueryWrapper<WptpYpbSale> queryWrapperWptpYpbSale = new QueryWrapper<>();//饮片经营
        queryWrapperWptpSale.eq("deleted","0");
        queryWrapperWptpMedicineSale.eq("deleted","0");
        queryWrapperWptpYpSale.eq("deleted","0");
        queryWrapperWptpYpbSale.eq("deleted","0");
        Integer wptpSaleCount = iWptpSaleService.getBaseMapper().selectCount(queryWrapperWptpSale);
        Integer wptpMedicineSaleCount = iWptpMedicineSaleService.getBaseMapper().selectCount(queryWrapperWptpMedicineSale);
        Integer wptpYpSaleCount = iWptpYpSaleService.getBaseMapper().selectCount(queryWrapperWptpYpSale);
        Integer wptpYpbSaleCount = iWptpYpbSaleService.getBaseMapper().selectCount(queryWrapperWptpYpbSale);

        Integer[] traceCodeCount = new Integer[]{wptpSaleCount,wptpMedicineSaleCount,wptpYpSaleCount,wptpYpbSaleCount};
        return traceCodeCount;
    }
    /**
     * 药材图片
     * @return
     */
    private List<MedicinalVO>  handleMedicinalImagesInfo(){
        QueryWrapper<WptpMedicinal> queryWrapperWptpMedicinal = new QueryWrapper<>();
        queryWrapperWptpMedicinal.eq("deleted","0");
        queryWrapperWptpMedicinal.eq("audit_status","2");
        List<WptpMedicinal> wptpMedicinals = iWptpMedicinalService.getBaseMapper().selectList(queryWrapperWptpMedicinal);
        List<MedicinalVO> medicinalVOS = new ArrayList<>();
        for (int i = 0; i < wptpMedicinals.size(); i++) {
            HashMap<String, Object> queryMap = new HashMap<>();
            queryMap.put("main_id",wptpMedicinals.get(i).getMedicinalCode());
            List<WptpMedicineFile> wptpMedicineFiles = iWptpMedicineFileService.getBaseMapper().selectByMap(queryMap);
            MedicinalVO medicinalVO = new MedicinalVO();
            if (!wptpMedicineFiles.isEmpty()) {
                medicinalVO.setMedicinalImages( wptpMedicineFiles.get(0).getPath());
                /*medicinalVO.setMedicinalId(wptpMedicinals.get(i).getMedicinalCode());*/
            }
            BeanUtils.copyProperties(wptpMedicinals.get(i),medicinalVO);
            medicinalVOS.add(medicinalVO);
            queryMap.clear();
        }
        return medicinalVOS;
    }
}
