package org.jeecg.modules.demo.trace.service.impl;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.baseinfo.entity.WptpBaseFile;
import org.jeecg.modules.demo.baseinfo.vo.WptpBaseVO;
import org.jeecg.modules.demo.blockmedicinal.vo.WptpBlockMeidicinalVO;
import org.jeecg.modules.demo.csinfo.vo.WptpCsInfoVO;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstockFile;
import org.jeecg.modules.demo.medicinebinstock.vo.WptpMedicineInstockVO;
import org.jeecg.modules.demo.medicinebsale.vo.WptpMedicineSaleVO;
import org.jeecg.modules.demo.plantinfo.vo.WptpPlantInfoVO;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.vo.WptpProcessInfoVO;
import org.jeecg.modules.demo.sale.entity.WptpSale;
import org.jeecg.modules.demo.trace.service.ConvertEntityToVOService;
import org.jeecg.modules.demo.trace.service.TraceBackService;
import org.jeecg.modules.demo.trace.service.TraceBaseDataService;
import org.jeecg.modules.demo.trace.vo.*;
import org.jeecg.modules.demo.ypbinstock.vo.WptpYpbInstockVO;
import org.jeecg.modules.demo.ypbsale.vo.WptpYpbSaleVO;
import org.jeecg.modules.demo.yppack.vo.WptpYpPackVO;
import org.jeecg.modules.demo.ypprocess.vo.WptpYpProcessVO;
import org.jeecg.modules.demo.ypprocessinstock.vo.WptpYpInstockVO;
import org.jeecg.modules.demo.ypprocesssale.vo.WptpYpSaleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 追溯
 */
@Service
@Transactional
public class TraceBackServiceImpl implements TraceBackService {
     @Autowired
     private TraceBaseDataService traceBaseDataService;
     @Autowired
     private ConvertEntityToVOService convertEntityToVOService;
     @Autowired
     private IWptpProcessMaterial wptpProcessMaterial;
     @Override
     public synchronized Result<TraceVO> plantTraceLink01(String traceCode) {
         WptpSale sale = traceBaseDataService.getSale(traceCode);
         PlantTraceVO plantTraceVO = new PlantTraceVO();
         TraceVO traceVO = new TraceVO();
         if (oConvertUtils.isEmpty(sale))return new Result().error500("未找到相关种植-销售记录");
         String hostCode = sale.getHostCode();
         String entName = traceBaseDataService.getEntNameByHostCode(hostCode);
         sale.setHostCode(entName);//所属企业
         plantTraceVO.setWptpSale(convertEntityToVOService.handleSale(sale));
         String medicineBatch = sale.getMedicineBatch();
         String source = sale.getSource();//来源(0:采收批次；1：初加工)
         if ((!source.contains("1"))&&(!source.contains("0"))) {
             traceVO.setPlantTraceVO(plantTraceVO);
             return new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
         }
         WptpProcessInfoVO processInfoVO=null;
         List<WptpProcessMaterial> wptpProcessMaterials=null;
         List<WptpProcessFile> wptpProcessFiles=null;
         List<WptpCsInfoVO> wptpCsInfoVOList=new ArrayList<WptpCsInfoVO>();
         List<WptpBlockMeidicinalVO> wptpBlockMeidicinalList=new ArrayList<WptpBlockMeidicinalVO>();
         if (source.contains("1")){
              processInfoVO = traceBaseDataService.getProcessInfoVO(medicineBatch);//加工
             plantTraceVO.setWptpProcessInfoVO(processInfoVO);
             if (oConvertUtils.isEmpty(processInfoVO)){
                 traceVO.setPlantTraceVO(plantTraceVO);
                 return new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
             }
              wptpProcessMaterials = traceBaseDataService.listProcessMaterials(processInfoVO.getProcessNo());//加工原料
              wptpProcessFiles = traceBaseDataService.listProcessFiles(processInfoVO.getProcessNo());//加工文件
             plantTraceVO.setWptpProcessFileList(wptpProcessFiles);
             plantTraceVO.setWptpProcessMaterialList(wptpProcessMaterials);
             /**
              * 采收批次（从加工环节追溯的话，查到的采收批次是多个）
              */
              if (!oConvertUtils.isEmpty(wptpProcessMaterials)){
                  for (WptpProcessMaterial w:
                          wptpProcessMaterials  ) {
                      WptpCsInfoVO csInfoVO = traceBaseDataService.getCsInfoVO(w.getCsBatch());
                      wptpCsInfoVOList.add(csInfoVO);
                  }
              }

         }else{
             /**
              * 采收批次（从销售追溯的话，查到的采收批次是1个）
              */
             WptpCsInfoVO csInfoVO = traceBaseDataService.getCsInfoVO(medicineBatch);
             plantTraceVO.setWptpCsInfoList(wptpCsInfoVOList);
             if (oConvertUtils.isEmpty(csInfoVO)){
                 traceVO.setPlantTraceVO(plantTraceVO);
                 return new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
             }
             wptpProcessMaterials = traceBaseDataService.listProcessMaterialsByCsNo(csInfoVO.getCsNo());
             if (!oConvertUtils.isEmpty(wptpProcessMaterials)){
                 processInfoVO = traceBaseDataService.getProcessInfoVO(wptpProcessMaterials.get(0).getProcessNo());//加工
             }
             if (!oConvertUtils.isEmpty(processInfoVO)){
                 wptpProcessFiles = traceBaseDataService.listProcessFiles(processInfoVO.getProcessNo());//加工文件
             }
             wptpCsInfoVOList.add(csInfoVO);
         }
         plantTraceVO.setWptpProcessInfoVO(processInfoVO);
         plantTraceVO.setWptpProcessFileList(wptpProcessFiles);
         plantTraceVO.setWptpProcessMaterialList(wptpProcessMaterials);
         if (!oConvertUtils.isEmpty(wptpCsInfoVOList))handleCsInfoList(wptpCsInfoVOList);//采收批次集合需要包含加工的原料相关数据
         plantTraceVO.setWptpCsInfoList(wptpCsInfoVOList);
         traceVO.setPlantTraceVO(plantTraceVO);
         return new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
    }

    @Override
    public Result<TraceVO> plantTraceLink02(String blockMedicinalId) {
        TraceVO traceVO = new TraceVO();
        WptpBlockMeidicinalVO blockMeidicinalVO = traceBaseDataService.getBlockMeidicinalVO(blockMedicinalId);//档案
        PlantTraceVO plantTraceVO = new PlantTraceVO();
        plantTraceVO.setWptpBlockMeidicinalVO(blockMeidicinalVO);
        if (!oConvertUtils.isEmpty(blockMeidicinalVO)){
            WptpBaseVO wptpBaseVO = traceBaseDataService.getWptpBaseVO(blockMeidicinalVO.getBaseCode());//基地
            if (!oConvertUtils.isEmpty(wptpBaseVO)){
                List<WptpBaseFile> wptpBaseFiles = traceBaseDataService.getBaseFile(wptpBaseVO.getBaseCode(),"0");//type为0就是图片
              if (!wptpBaseFiles.isEmpty())wptpBaseVO.setImgUrl(wptpBaseFiles.get(0).getPath());
                List<WptpBaseFile> wptpBaseReport = traceBaseDataService.getBaseFile(wptpBaseVO.getBaseCode(),"1");//type为1就是报告

                if (!oConvertUtils.isEmpty(wptpBaseReport)){
                    String[] reports = new String[wptpBaseReport.size()];
                    for (int i = 0; i < wptpBaseReport.size(); i++) {
                        reports[i]=wptpBaseReport.get(i).getPath();
                    }
                    wptpBaseVO.setReportUrl(reports);
                }

            }
            List<WptpPlantInfoVO> wptpPlantInfoVOList = traceBaseDataService.listPlantInfoVO(blockMeidicinalVO.getBlockMedicinalId());
            plantTraceVO.setWptpPlantInfoVOList(wptpPlantInfoVOList);
            plantTraceVO.setWptpBaseVO(wptpBaseVO);
        }
        traceVO.setPlantTraceVO(plantTraceVO);
        return  new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
    }

    @Override
    public synchronized Result<TraceVO> ypProcessTrace(String traceCode) {
        WptpYpSaleVO ypSaleVO = traceBaseDataService.getYpSaleVO(traceCode);
        if (oConvertUtils.isEmpty(ypSaleVO))return new Result().error500("未找到相关饮片销售记录");
        String source = ypSaleVO.getSource();//来源0：包装；1：加工
        String sourceNo = ypSaleVO.getSourceNo();//包装或者加工的流水号
        String hostCode = ypSaleVO.getHostCode();
        String entName = traceBaseDataService.getEntNameByHostCode(hostCode);
        ypSaleVO.setHostCode(entName);//所属企业
        TraceVO traceVO = new TraceVO();
        YpProcessTraceVO ypProcessTraceVO = new YpProcessTraceVO();
        ypProcessTraceVO.setWptpYpSaleVO(ypSaleVO);
        if ((!source.contains("1"))&&(!source.contains("0"))) {
                traceVO.setYpProcessVO(ypProcessTraceVO);
                return new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
        }
        WptpYpProcessVO ypProcessVO=null;
        if (source.contains("0")){
            WptpYpPackVO ypPackVO = traceBaseDataService.getYpPackVO(sourceNo);
            ypProcessTraceVO.setWptpYpPackVO(ypPackVO);
            if (oConvertUtils.isEmpty(ypPackVO)) {
                traceVO.setYpProcessVO(ypProcessTraceVO);
                return new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
            }

             ypProcessVO = traceBaseDataService.getYpProcessVO(ypPackVO.getProcessNo());
        }else {
            ypProcessVO = traceBaseDataService.getYpProcessVO(sourceNo);
        }
        ypProcessTraceVO.setWptpYpProcessVO(ypProcessVO);
        if (oConvertUtils.isEmpty(ypProcessVO)){
            traceVO.setYpProcessVO(ypProcessTraceVO);
            return new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
        }
        String instockNo = ypProcessVO.getInstockNo();//入库流水号
        WptpYpInstockVO ypInstockVO = traceBaseDataService.getYpInstockVO(instockNo);
        ypProcessTraceVO.setWptpYpInstockVO(ypInstockVO);
        if (oConvertUtils.isEmpty(ypInstockVO)){
            traceVO.setYpProcessVO(ypProcessTraceVO);
            return new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
        }
        /**
         * 0:种植端；1：药材经营；
         * 2：饮片加工；3：饮片经营
         */
         String sourceFlag = ypInstockVO.getSourceFlag();
        Result<TraceVO> traceVOResult=null;
        if (sourceFlag.contains("0")){
            traceVOResult = plantTraceLink01(ypInstockVO.getTraceCode());
        }else if (sourceFlag.contains("1")){
             traceVOResult = medicinalTrace(ypInstockVO.getTraceCode());
        }
        if (!oConvertUtils.isEmpty(traceVOResult)){
            TraceVO result = traceVOResult.getResult();
            if (!oConvertUtils.isEmpty(result)){
                BeanUtils.copyProperties(result,traceVO);
            }
        }
        ypProcessTraceVO.setWptpYpProcessVO(ypProcessVO);
        ypProcessTraceVO.setWptpYpInstockVO(ypInstockVO);
        ypProcessTraceVO.setWptpYpSaleVO(ypSaleVO);
        ypProcessTraceVO.setWptpYpInstockFileList(traceBaseDataService.listWptpYpInstockFiles(ypInstockVO.getInstockNumber()));
        ypProcessTraceVO.setWptpYpProcessFileList(traceBaseDataService.listWptpYpProcessFiles(ypProcessVO.getProcessNo()));
        ypProcessTraceVO.setWptpYpSaleFileList(traceBaseDataService.listWptpYpSaleFiles(ypSaleVO.getSaleNumber()));
        traceVO.setYpProcessVO(ypProcessTraceVO);
        return new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
    }

    @Override
    public synchronized Result<TraceVO> ypBusinessTrace(String traceCode) {
        List<YpBusinessTraceVO> ypBusinessTraceVOS = listYpBusinessTraceVO(traceCode, new ArrayList<YpBusinessTraceVO>());
        if (oConvertUtils.isEmpty(ypBusinessTraceVOS))return new Result().error500("未找到相关饮片销售的记录");
        YpBusinessTraceVO ypBusinessTraceVO = ypBusinessTraceVOS.get(ypBusinessTraceVOS.size() - 1);
        TraceVO traceVO = new TraceVO();
        traceVO.setYpBusinessTraceVOList(ypBusinessTraceVOS);
        WptpYpbInstockVO wptpYpbInstockVO = ypBusinessTraceVO.getWptpYpbInstockVO();
        if (oConvertUtils.isEmpty(wptpYpbInstockVO))return  new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
        String preTraceCode = wptpYpbInstockVO.getTraceCode();//上游环节追溯码
        Result<TraceVO> traceVOResult=null;
        switch (preTraceCode.substring(1,3)){
            case "04"://种植
                traceVOResult = plantTraceLink01(preTraceCode);
                break;
            case "11"://药材经营
                traceVOResult = medicinalTrace(preTraceCode);
                break;
            case "23"://饮片加工
                traceVOResult = ypProcessTrace(preTraceCode);
        }
        TraceVO result = traceVOResult.getResult();
        if (!oConvertUtils.isEmpty(result)) BeanUtils.copyProperties(result,traceVO);
        traceVO.setYpBusinessTraceVOList(ypBusinessTraceVOS);
        return new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
/*        WptpYpbSaleVO ypbSaleVO = traceBaseDataService.getYpbSaleVO(traceCode);
        if (oConvertUtils.isEmpty(ypbSaleVO))return new Result().error500("未找到相关饮片销售记录");
         String instockNo = ypbSaleVO.getInstockNo();//入库流水号
        WptpYpbInstockVO ypbInstockVO = traceBaseDataService.getYpbInstockVO(instockNo);
        if (oConvertUtils.isEmpty(ypbInstockVO))return new Result().error500("未找到相关饮片入库记录");
        TraceVO traceVO = new TraceVO();
        YpBusinessTraceVO ypBusinessTraceVO = new YpBusinessTraceVO();
        *//**
         * 0:种植端；1：药材经营；
         * 2：饮片加工；3：饮片经营
         *//*
        String sourceFlag = ypbInstockVO.getSourceFlag();
        String preTraceCode = ypbInstockVO.getTraceCode();//上游环节追溯码
        Result<TraceVO> traceVOResult=null;
        switch (preTraceCode.substring(1,3)){
            case "04":
                traceVOResult = plantTraceLink01(preTraceCode);
                break;
            case "11":
                traceVOResult = medicinalTrace(preTraceCode);
                break;
            case "23":
                traceVOResult = ypProcessTrace(preTraceCode);
                break;
            case "31":
                traceVOResult = ypBusinessTrace(preTraceCode);
        }
       *//* if (traceVOResult.getCode()==500)return traceVOResult;*//*
        TraceVO result = traceVOResult.getResult();
        BeanUtils.copyProperties(result,traceVO);
        ypBusinessTraceVO.setWptpYpbSaleVO(ypbSaleVO);
        ypBusinessTraceVO.setWptpYpbInstockVO(ypbInstockVO);
        ypBusinessTraceVO.setWptpYpbInstockFileList(traceBaseDataService.listWptpYpbInstockFiles(ypbInstockVO.getInstockNo()));
        traceVO.setYpBusinessVO(ypBusinessTraceVO);
        return new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());*/
    }
    @Override
    public List<YpBusinessTraceVO>  listYpBusinessTraceVO(String traceCode,List<YpBusinessTraceVO> sourceList) {
        WptpYpbSaleVO ypbSaleVO = traceBaseDataService.getYpbSaleVO(traceCode);
        YpBusinessTraceVO ypBusinessTraceVO = new YpBusinessTraceVO();
        if (oConvertUtils.isEmpty(ypbSaleVO))return null;
        String instockNo = ypbSaleVO.getInstockNo();//入库流水号
        WptpYpbInstockVO ypbInstockVO = traceBaseDataService.getYpbInstockVO(instockNo);
        String hostCode = ypbSaleVO.getHostCode();
        String entName = traceBaseDataService.getEntNameByHostCode(hostCode);
        ypbSaleVO.setHostCode(entName);//所属企业
        ypBusinessTraceVO.setWptpYpbSaleVO(ypbSaleVO);
        ypBusinessTraceVO.setWptpYpbInstockVO(ypbInstockVO);
        if (!oConvertUtils.isEmpty(ypbInstockVO)){
            ypBusinessTraceVO.setWptpYpbInstockFileList(traceBaseDataService.listWptpYpbInstockFiles(ypbInstockVO.getInstockNo()));
        }
        sourceList.add(ypBusinessTraceVO);
        /**
         * 31：饮片经营
         */
        if (!oConvertUtils.isEmpty(ypbInstockVO)){
            String preTraceCode = ypbInstockVO.getTraceCode();//上游环节追溯码
            String link = preTraceCode.substring(1, 3);
            if (link.contains("31")){
                return listYpBusinessTraceVO(preTraceCode, sourceList);
            }
        }
        return sourceList;
    }
    @Override
    public synchronized Result<TraceVO> medicinalTrace(String traceCode) {
        TraceVO traceVO = new TraceVO();
        MedicineTraceVO medicineTraceVO = new MedicineTraceVO();
        WptpMedicineSaleVO medicineSaleVO = traceBaseDataService.getMedicineSaleVO(traceCode);
        medicineTraceVO.setWptpMedicineSaleVO(medicineSaleVO);
        if (oConvertUtils.isEmpty(medicineSaleVO))return new Result().error500("未找到相关药材-销售记录");
        String instockNumber = medicineSaleVO.getInstockNumber();//入库流水号
        WptpMedicineInstockVO medicineInstockVO = traceBaseDataService.getMedicineInstockVO(instockNumber);
        medicineTraceVO.setWptpMedicineInstockVO(medicineInstockVO);
        traceVO.setMedicineVO(medicineTraceVO);
        if (oConvertUtils.isEmpty(medicineInstockVO)){
            return new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
        }
        String preTraceCode= medicineInstockVO.getTraceCode();//是否是种端端追溯码
        /**
         * 04则是种植端追溯码
         */
        if (preTraceCode.substring(1,3).contains("04")){
            Result<TraceVO> traceVOResult = plantTraceLink01(preTraceCode);
            if (traceVOResult.getCode()==500)return new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
            TraceVO result = traceVOResult.getResult();
            BeanUtils.copyProperties(result,traceVO);
        }
        List<WptpMedicineInstockFile> wptpMedicineInstockFiles = traceBaseDataService.listWptpMedicineInstockFiles(medicineInstockVO.getInstockNumber());//药材入库文件
        String hostCode = medicineSaleVO.getHostCode();
        String entName = traceBaseDataService.getEntNameByHostCode(hostCode);
        medicineSaleVO.setHostCode(entName);//所属企业
        medicineTraceVO.setWptpMedicineSaleVO(medicineSaleVO);
        medicineTraceVO.setWptpMedicineInstockVO(medicineInstockVO);
        medicineTraceVO.setWptpMedicineInstockFileList(wptpMedicineInstockFiles);
        traceVO.setMedicineVO(medicineTraceVO);
        return new Result<TraceVO>(true,"操作成功",200,traceVO,new Date().getTime());
    }


    /**
     * 处理采收批次
     */
    private void handleCsInfoList(List<WptpCsInfoVO> wptpCsInfoVOList){
        if (!wptpCsInfoVOList.isEmpty()){
            for (WptpCsInfoVO w:
                    wptpCsInfoVOList) {
                if (null==w)continue;
                List<WptpProcessMaterial> listByCsNo = wptpProcessMaterial.listByCsNo(w.getCsNo());
                w.setProcessMaterialList(listByCsNo);
            }
        }
    }
}
