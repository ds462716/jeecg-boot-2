package org.jeecg.modules.api.webservice.guild.upload;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.HttpUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.api.webservice.guild.bean.*;
import org.jeecg.modules.demo.baseinfo.entity.WptpBase;
import org.jeecg.modules.demo.baseinfo.entity.WptpBaseFile;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseFileService;
import org.jeecg.modules.demo.baseinfo.vo.WptpBaseVO;
import org.jeecg.modules.demo.blockinfo.entity.WptpBlockInfo;
import org.jeecg.modules.demo.blockinfo.service.IWptpBlockInfoService;
import org.jeecg.modules.demo.blockmedicinal.entity.WptpBlockMeidicinal;
import org.jeecg.modules.demo.blockmedicinal.vo.WptpBlockMeidicinalVO;
import org.jeecg.modules.demo.csinfo.vo.WptpCsInfoVO;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.service.IWptpEntInfoService;
import org.jeecg.modules.demo.hostcode.entity.WptpHostcode;
import org.jeecg.modules.demo.hostcode.service.IWptpHostcodeService;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstockFile;
import org.jeecg.modules.demo.medicinebinstock.service.IWptpMedicineInstockFileService;
import org.jeecg.modules.demo.medicinebinstock.vo.WptpMedicineInstockVO;
import org.jeecg.modules.demo.medicinebsale.entity.WptpMedicineSale;
import org.jeecg.modules.demo.medicinebsale.service.IWptpMedicineSaleService;
import org.jeecg.modules.demo.medicinebsale.vo.WptpMedicineSaleVO;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantFile;
import org.jeecg.modules.demo.plantinfo.service.IWptpPlantFileService;
import org.jeecg.modules.demo.plantinfo.vo.WptpPlantInfoVO;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessInfo;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessFileService;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.vo.WptpProcessInfoVO;
import org.jeecg.modules.demo.sale.entity.WptpSale;
import org.jeecg.modules.demo.sale.service.IWptpSaleService;
import org.jeecg.modules.demo.sale.vo.WptpSaleVO;
import org.jeecg.modules.demo.trace.service.TraceBackService;
import org.jeecg.modules.demo.trace.service.TraceBaseDataService;
import org.jeecg.modules.demo.trace.vo.*;
import org.jeecg.modules.demo.uploadxh.entity.WptpUploadRecord;
import org.jeecg.modules.demo.uploadxh.service.IWptpUploadRecordService;
import org.jeecg.modules.demo.uploadxh.entity.WptpUploadRecord;
import org.jeecg.modules.demo.ypbinstock.vo.WptpYpbInstockVO;
import org.jeecg.modules.demo.ypbsale.entity.WptpYpbSale;
import org.jeecg.modules.demo.ypbsale.service.IWptpYpbSaleService;
import org.jeecg.modules.demo.ypbsale.vo.WptpYpbSaleVO;
import org.jeecg.modules.demo.yppack.vo.WptpYpPackVO;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcessFile;
import org.jeecg.modules.demo.ypprocess.service.IWptpYpProcessFileService;
import org.jeecg.modules.demo.ypprocess.vo.WptpYpProcessVO;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstockFile;
import org.jeecg.modules.demo.ypprocessinstock.service.IWptpYpInstockFileService;
import org.jeecg.modules.demo.ypprocessinstock.vo.WptpYpInstockVO;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSale;
import org.jeecg.modules.demo.ypprocesssale.service.IWptpYpSaleService;
import org.jeecg.modules.demo.ypprocesssale.vo.WptpYpSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * 上传相关数据到行业协会
 *
 * @author laowang
 */
@Component
@Slf4j
public class GuildUpload {
    private static final String APP_KEY = "be6gntvg";
    private static final String APP_SECRET = "f6c7ef91417e8ac0";
    private static final String MEDICINAL_PRODUCING_AREA = "3001002007";//库存类型：药材产区库存
    private static final String YP_MATERIALS = "3001002002";//库存类型：饮片原料库存
    private static final String YP_PRODUCTS = "3001002003";//库存类型：饮片成品库存
    private static final String TOKEN_URL = "http://citypt.yunyc.cn/mq_service/token/getTokenInfo";//获取token
    private static final String LINK_URL = "http://citypt.yunyc.cn/mq_service/linkDataProducer/send/traceAndResumeData";//节点履历
    private static final String IMG_URL_PREIX = "http://180.168.130.217:9010/img";//图片路径前缀
    @Autowired
    private IWptpUploadRecordService xhUploadRecordService;
    @Autowired
    private IWptpEntInfoService iWptpEntInfoService;
    /*    @Autowired
        private IWptpYpbInstockFileService iWptpYpbInstockFileService;*/
    @Autowired
    private IWptpYpProcessFileService iWptpYpProcessFileService;
    @Autowired
    private IWptpYpInstockFileService iWptpYpInstockFileService;
    @Autowired
    private IWptpMedicinalService iWptpMedicinalService;
    @Autowired
    private IWptpMedicineInstockFileService iWptpMedicineInstockFileService;
    @Autowired
    private IWptpHostcodeService iWptpHostcodeService;
    @Autowired
    private IWptpProcessFileService iWptpProcessFileService;
    @Autowired
    private IWptpProcessMaterial iWptpProcessMaterial;
    @Autowired
    private TraceBaseDataService traceBaseDataService;
    @Autowired
    private IWptpPlantFileService iWptpPlantFileService;
    @Autowired
    private IWptpBaseFileService iWptpBaseFileService;
    @Autowired
    private IWptpBlockInfoService iWptpBlockInfoService;
    @Autowired
    private TraceBackService traceBackService;
    @Autowired
    private IWptpMedicineSaleService iWptpMedicineSaleService;
    @Autowired
    private IWptpYpSaleService iWptpYpSaleService;
    @Autowired
    private IWptpYpbSaleService iWptpYpbSaleService;
    @Autowired
    private IWptpSaleService iWptpSaleService;

    /**
     * @param traceCode 追溯码
     * @param linkCode  药材：11，种植：04，饮片加工：23，饮片经营：31
     * @return
     */
    @Transactional
    public boolean upload(String traceCode, String operaterFlag) throws Exception {
        Map<String, String> param = new HashMap<>();
        param.put("traceCode", traceCode);
        Result<TraceVO> traceVOResult = traceByCode(traceCode);
        if (traceVOResult.getCode().equals("500")) return false;
       /* String token = getToken();
        if (oConvertUtils.isEmpty(token)) throw new Exception("toke不能为空！");*/
        UploadParam uploadParam = handleUploadParam(traceVOResult, traceCode, operaterFlag);
        String uploadParamJsonStr = JSON.toJSONString(uploadParam);//json格式参数字符串
        System.out.println(uploadParamJsonStr);
        String uploadResult = HttpUtils.doPost(LINK_URL, uploadParamJsonStr);//上传至行业协会;
        log.info("json格式字符串:" + uploadParamJsonStr);
        log.info("参数:" + uploadParam);
        log.info("结果:" + uploadResult);
        WptpUploadRecord uploadRecord = new WptpUploadRecord(new Date(), "成功", uploadResult, traceCode, uploadParamJsonStr, getLinkByTraceCode(traceCode));
        xhUploadRecordService.addWptpUploadRecord(uploadRecord);
        return true;
    }

    /**
     * 根据追溯码获得环节
     */
    private String getLinkByTraceCode(String traceCode) {
        String link = "";
        switch (traceCode.substring(1, 3)) {
            case "04":
                link = "种植";
                break;
            case "11":
                link = "药材经营";
                break;
            case "23":
                link = "饮片加工";
                break;
            case "31":
                link = "饮片经营";
        }
        return link;
    }

    /**
     * 组装上传参数类uploadParam
     *
     * @param result
     * @param traceCode
     * @return
     */
    private UploadParam handleUploadParam(Result<TraceVO> result, String traceCode, String operaterFlag) throws Exception {
        UploadParam uploadParam = new UploadParam();
        if (oConvertUtils.isEmpty(result.getResult())) return null;
        String currentTraceCode = "";//当前环节追溯码
        String lastTraceCode = "";//上游环节追溯码
        String xhCode = "";//当前环节的xhCode
        String lastXhCode = "";//上游环节协会code
        /**
         * 判断追溯码的第二、三位属于哪个环节
         * 药材：11，种植：04，饮片加工：23，饮片经营：31
         */
        switch (traceCode.substring(1, 3)) {
            case "04":
                PlantTraceVO plantTraceVO = result.getResult().getPlantTraceVO();
                if (!oConvertUtils.isEmpty(plantTraceVO)) {
                    WptpSaleVO wptpSale = plantTraceVO.getWptpSale();
                    String medicinalCode = wptpSale.getMedicinalCode();
                    String saleTime = wptpSale.getSaleTime();
                    WptpProcessInfoVO wptpProcessInfoVO = plantTraceVO.getWptpProcessInfoVO();
                    List<WptpCsInfoVO> wptpCsInfoList = plantTraceVO.getWptpCsInfoList();
                    if (!oConvertUtils.isEmpty(wptpSale)) {
                        List<OrderShipment> orderShipments = handlePlantSale(wptpSale);//销售
                        String entId = getEntIdByEntName(wptpSale.getHostCode());
                        currentTraceCode = wptpSale.getTraceCode();
                        xhCode = getEntXhCode(entId);
                        EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
                        enterpriseInfo.setEnterpriseId(xhCode);
                        enterpriseInfo.setEnterpriseCode(entId);
                        String medicinalName = getMedicinalName(wptpSale.getMedicinalCode());
                        uploadParam.setCommodityName(medicinalName);
                        uploadParam.setEnterpriseInfo(enterpriseInfo);
                        uploadParam.setOrderShipment(orderShipments);

                        if (!oConvertUtils.isEmpty(saleTime)) uploadParam.setNodeDate(DateUtils.StringToDate(saleTime));
                    }
                    if (!oConvertUtils.isEmpty(wptpCsInfoList)) {
                        Map map = handlePlantCs(wptpCsInfoList);//采收
                        uploadParam.setHarvestBatch((List<HarvestBatch>) map.get("harvestBatchList"));
                        uploadParam.setPlantingBatch((List<PlantingBatch>) map.get("plantingBatchList"));
                    }
                    if (!oConvertUtils.isEmpty(wptpProcessInfoVO)) {
                        if (!oConvertUtils.isEmpty(wptpCsInfoList)) {
                            ProcessingInfo processingInfo = handlePlantProcess(wptpProcessInfoVO, wptpCsInfoList.get(0).getMedicinalCode());//初加工
                            processingInfo.setProcessProductName(medicinalCode);
                            List<ProcessingInfo> processingInfos = new ArrayList<>();
                            processingInfos.add(processingInfo);
                            uploadParam.setProcessingInfo(processingInfos);
                        }
                    }
                    uploadParam.setTraceStage("6");//种植：6 药材/饮片经营：3 饮片加工：1
                    WptpSale byId = iWptpSaleService.getById(wptpSale.getId());
                    byId.setXhTraceCode(xhCode + "-" + currentTraceCode);
                    byId.setUpdateBy(byId.getHostCode());
                    byId.setUpdateTime(new Date());
                    iWptpSaleService.getBaseMapper().updateById(byId);

                }
                break;
            case "11":
                List<MedicineTraceVO> medicineTraceVOList = result.getResult().getMedicineVOList();
                MedicineTraceVO medicineVO=null;
                if (!oConvertUtils.isEmpty(medicineTraceVOList)){
                     medicineVO = medicineTraceVOList.get(0);
                }
                if (!oConvertUtils.isEmpty(medicineVO)) {
                    WptpMedicineSaleVO wptpMedicineSaleVO = medicineVO.getWptpMedicineSaleVO();
                    String entId = getEntIdByEntName(wptpMedicineSaleVO.getHostCode());

                    WptpMedicineInstockVO wptpMedicineInstockVO = medicineVO.getWptpMedicineInstockVO();
                    String outTime = wptpMedicineSaleVO.getOutTime();
                    List<QualitySelfTest> qualitySelfTestList = new ArrayList<>();//检测报告
                    /**
                     * 销售
                     */
                    if (!oConvertUtils.isEmpty(wptpMedicineSaleVO)) {
                        List<OrderShipment> orderShipment = handleMedicineSale(wptpMedicineSaleVO);
                        uploadParam.setOrderShipment(orderShipment);
                        xhCode = getEntXhCode(entId);
                        currentTraceCode = wptpMedicineSaleVO.getTraceCode();
                        EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
                        enterpriseInfo.setEnterpriseId(xhCode);
                        enterpriseInfo.setEnterpriseCode(entId);
                        uploadParam.setEnterpriseInfo(enterpriseInfo);
                        String medicinalName = getMedicinalName(wptpMedicineSaleVO.getCategoryCode());
                        uploadParam.setCommodityName(medicinalName);
                        if (!oConvertUtils.isEmpty(outTime)) uploadParam.setNodeDate(DateUtils.StringToDate(outTime));
                    }
                    /**
                     * 入库
                     */
                    if (!oConvertUtils.isEmpty(wptpMedicineInstockVO)) {
                        Map<String, Object> resultMap = handleMedicineInstock(wptpMedicineInstockVO);
                        PurchaseOrderInfo purchaseOrderInfo = (PurchaseOrderInfo) resultMap.get("purchaseOrderInfo");
                        WarehouseInfo warehouseInfo = (WarehouseInfo) resultMap.get("warehouseInfo");
                        /*  EnterpriseInfo enterpriseInfo=(EnterpriseInfo)resultMap.get("enterpriseInfo");*/
                        List<WarehouseInfo> warehouseInfoList = new ArrayList<>();
                        List<PurchaseOrderInfo> purchaseOrderInfoList = new ArrayList<>();
                        purchaseOrderInfoList.add(purchaseOrderInfo);
                        warehouseInfoList.add(warehouseInfo);
                        uploadParam.setPurchaseOrderInfo(purchaseOrderInfoList);
                        uploadParam.setWarehouseInfo(warehouseInfoList);
                        /*      uploadParam.setEnterpriseInfo(enterpriseInfo);*/
                        QualitySelfTest qualitySelfTest = handleMedicineSaleQualitySelfTest(wptpMedicineInstockVO);
                        qualitySelfTestList.add(qualitySelfTest);
                        lastTraceCode = wptpMedicineInstockVO.getTraceCode();

                        /*   lastXhCode = getEntXhCode(wptpMedicineInstockVO.getEntId());*/
                        lastXhCode = chooseLink(lastTraceCode);
                    }
                    uploadParam.setQualitySelfTest(qualitySelfTestList);
                    uploadParam.setTraceStage("3");//种植：6 药材/饮片经营：3 饮片加工：1
                    WptpMedicineSale byId = iWptpMedicineSaleService.getById(wptpMedicineSaleVO.getId());
                    byId.setXhTraceCode(xhCode + "-" + currentTraceCode);
                    byId.setUpdateBy(byId.getHostCode());
                    byId.setUpdateTime(new Date());
                    iWptpMedicineSaleService.getBaseMapper().updateById(byId);
                }
                break;
            case "23":
                YpProcessTraceVO ypProcessVO = result.getResult().getYpProcessVO();
                if (!oConvertUtils.isEmpty(ypProcessVO)) {
                    WptpYpSaleVO wptpYpSaleVO = ypProcessVO.getWptpYpSaleVO();
                    WptpYpInstockVO wptpYpInstockVO = ypProcessVO.getWptpYpInstockVO();
                    WptpYpProcessVO wptpYpProcessVO = ypProcessVO.getWptpYpProcessVO();
                    WptpYpPackVO wptpYpPackVO = ypProcessVO.getWptpYpPackVO();
                    List<QualitySelfTest> qualitySelfTestList = new ArrayList<>();//检测报告
                    String entId = getEntIdByEntName(wptpYpSaleVO.getHostCode());
                    EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
                    enterpriseInfo.setEnterpriseId(getEntXhCode(entId));
                    enterpriseInfo.setEnterpriseCode(entId);
                    uploadParam.setEnterpriseInfo(enterpriseInfo);
                    String saleTime = wptpYpSaleVO.getSaleTime();
                    /**
                     * 销售
                     */
                    if (!oConvertUtils.isEmpty(wptpYpSaleVO)) {
                        List<OrderShipment> orderShipment = handleYpProcessSale(wptpYpSaleVO);
                        xhCode = getEntXhCode(entId);
                        currentTraceCode = wptpYpSaleVO.getTraceCode();
                        String medicinalName = getMedicinalName(wptpYpSaleVO.getYpCode());
                        uploadParam.setCommodityName(medicinalName);
                        uploadParam.setOrderShipment(orderShipment);
                        if (!oConvertUtils.isEmpty(saleTime)) uploadParam.setNodeDate(DateUtils.StringToDate(saleTime));
                    }
                    /**
                     * 入库
                     */
                    if (!oConvertUtils.isEmpty(wptpYpInstockVO)) {
                        Map<String, Object> resultMap = handleYpProcessInstock(wptpYpInstockVO);
                        PurchaseOrderInfo purchaseOrderInfo = (PurchaseOrderInfo) resultMap.get("purchaseOrderInfo");
                        WarehouseInfo warehouseInfo = (WarehouseInfo) resultMap.get("warehouseInfo");
                        /*  EnterpriseInfo enterpriseInfo=(EnterpriseInfo)resultMap.get("enterpriseInfo");*/
                        List<WarehouseInfo> warehouseInfoList = new ArrayList<>();
                        List<PurchaseOrderInfo> purchaseOrderInfoList = new ArrayList<>();
                        purchaseOrderInfoList.add(purchaseOrderInfo);
                        warehouseInfoList.add(warehouseInfo);
                        uploadParam.setPurchaseOrderInfo(purchaseOrderInfoList);
                        uploadParam.setWarehouseInfo(warehouseInfoList);
                        /* uploadParam.setEnterpriseInfo(enterpriseInfo);*/
                        QualitySelfTest qualitySelfTest = handleYpProcessInStockQualitySelfTest(wptpYpInstockVO);
                        qualitySelfTestList.add(qualitySelfTest);
                        lastTraceCode = wptpYpInstockVO.getTraceCode();
                        /* lastXhCode = getEntXhCode(wptpYpInstockVO.getEntId());*/
                        lastXhCode = chooseLink(lastTraceCode);
                    }
                    uploadParam.setEnterpriseInfo(enterpriseInfo);
                    /**
                     * 加工
                     */
                    if (!oConvertUtils.isEmpty(wptpYpProcessVO)) {
                        ProcessingInfo processingInfo = handleYpProcessProcess(wptpYpProcessVO);
                        List<ProcessingInfo> processingInfoList = new ArrayList<>();
                        QualitySelfTest qualitySelfTest = handleYpProcessQualitySelfTest(wptpYpProcessVO);
                        qualitySelfTestList.add(qualitySelfTest);
                        processingInfoList.add(processingInfo);
                        uploadParam.setProcessingInfo(processingInfoList);
                    }
                    /**
                     * 包装
                     */
                    if (!oConvertUtils.isEmpty(wptpYpPackVO)) {
                        List<PackingInfo> packingInfo = handleYpProcessPack(wptpYpPackVO);
                        uploadParam.setPackingInfo(packingInfo);
                    }
                    uploadParam.setQualitySelfTest(qualitySelfTestList);
                    uploadParam.setTraceStage("1");//种植：6 药材/饮片经营：3 饮片加工：1
                    WptpYpSale byId = iWptpYpSaleService.getById(wptpYpSaleVO.getId());
                    byId.setXhTraceCode(xhCode + "-" + currentTraceCode);
                    byId.setUpdateBy(byId.getHostCode());
                    byId.setUpdateDate(new Date());
                    iWptpYpSaleService.getBaseMapper().updateById(byId);
                }

                break;
            case "31":
                List<YpBusinessTraceVO> ypBusinessTraceVOList = result.getResult().getYpBusinessTraceVOList();
                if (!oConvertUtils.isEmpty(ypBusinessTraceVOList)) {
                    YpBusinessTraceVO ypBusinessTraceVO = ypBusinessTraceVOList.get(0);
                    if (!oConvertUtils.isEmpty(ypBusinessTraceVO)) {
                        WptpYpbSaleVO wptpYpbSaleVO = ypBusinessTraceVO.getWptpYpbSaleVO();
                        WptpYpbInstockVO wptpYpbInstockVO = ypBusinessTraceVO.getWptpYpbInstockVO();
                        String saleTime = wptpYpbSaleVO.getSaleTime();

                        if (!oConvertUtils.isEmpty(wptpYpbSaleVO)) {
                            List<OrderShipment> orderShipment = handleYpBusinessSale(wptpYpbSaleVO);
                            String entId = getEntIdByEntName(wptpYpbSaleVO.getHostCode());
                            EnterpriseInfo enterpriseInfo = new EnterpriseInfo();

                            uploadParam.setOrderShipment(orderShipment);
                            xhCode = getEntXhCode(entId);
                            currentTraceCode = wptpYpbSaleVO.getTraceCode();
                            enterpriseInfo.setEnterpriseId(xhCode);
                            enterpriseInfo.setEnterpriseCode(entId);
                            uploadParam.setEnterpriseInfo(enterpriseInfo);
                            String medicinalName = getMedicinalName(wptpYpbSaleVO.getYpCode());
                            uploadParam.setCommodityName(medicinalName);
                            uploadParam.setEnterpriseInfo(enterpriseInfo);
                            if (!oConvertUtils.isEmpty(saleTime))
                                uploadParam.setNodeDate(DateUtils.StringToDate(saleTime));
                        }
                        if (!oConvertUtils.isEmpty(wptpYpbInstockVO)) {
                            Map<String, Object> resultMap = handleYpBusinessInstock(wptpYpbInstockVO);
                            PurchaseOrderInfo purchaseOrderInfo = (PurchaseOrderInfo) resultMap.get("purchaseOrderInfo");
                            WarehouseInfo warehouseInfo = (WarehouseInfo) resultMap.get("warehouseInfo");
                            /* EnterpriseInfo enterpriseInfo=(EnterpriseInfo)resultMap.get("enterpriseInfo");*/
                            List<WarehouseInfo> warehouseInfoList = new ArrayList<>();
                            List<PurchaseOrderInfo> purchaseOrderInfoList = new ArrayList<>();
                            purchaseOrderInfoList.add(purchaseOrderInfo);
                            warehouseInfoList.add(warehouseInfo);
                            uploadParam.setPurchaseOrderInfo(purchaseOrderInfoList);
                            uploadParam.setWarehouseInfo(warehouseInfoList);

                            lastTraceCode = wptpYpbInstockVO.getTraceCode();
                            /* lastXhCode = getEntXhCode(wptpYpbInstockVO.getEntId());*/
                            lastXhCode = chooseLink(lastTraceCode);
                        }
                        uploadParam.setTraceStage("3");//种植：6 药材/饮片经营：3 饮片加工：1
                        WptpYpbSale byId = iWptpYpbSaleService.getById(wptpYpbSaleVO.getId());
                        byId.setXhTraceCode(xhCode + "-" + currentTraceCode);
                        byId.setUpdateBy(byId.getHostCode());
                        byId.setUpdateDate(new Date());
                        iWptpYpbSaleService.getBaseMapper().updateById(byId);
                    }
                }

        }
        uploadParam.setAppKey(APP_KEY);
        uploadParam.setAppToken(getToken());
        uploadParam.setOperaterFlag(operaterFlag);
        uploadParam.setCurrentNodeId(xhCode + "-" + currentTraceCode);
        if (!oConvertUtils.isEmpty(lastTraceCode)) {
            if (!oConvertUtils.isEmpty(lastXhCode)) {
                uploadParam.setLastNodeId(lastXhCode + "-" + lastTraceCode);
            } else {
                uploadParam.setLastNodeId(lastTraceCode);
            }

        }
        uploadParam.setTraceCode(new TraceCode(xhCode + "-" + currentTraceCode, null));
        System.out.println("当前环节:" + uploadParam.getCurrentNodeId());
        System.out.println("上游环节:" + uploadParam.getLastNodeId());
        System.out.println("追溯码" + uploadParam.getTraceCode());
        return uploadParam;
    }

    /**
     * 获得token
     *
     * @return
     */
    private static String getToken() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appKey", APP_KEY);
        paramMap.put("appSecret", APP_SECRET);
        paramMap.put("time", new Date().getTime() + "");
        String result = HttpUtils.sendGet(TOKEN_URL, paramMap);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String dataStr = jsonObject.get("data").toString();
        JSONObject dataJson = JSONObject.parseObject(dataStr);
        String accessToken = dataJson.get("accessToken").toString();
        if (oConvertUtils.isEmpty(accessToken)) return "";
        return accessToken;
    }

    /**
     * 判断去哪个上游环节取追溯码
     *
     * @return
     */
    private String chooseLink(String traceCode) {  /**
     * 判断追溯码的第二、三位属于哪个环节
     * 药材：11，种植：04，饮片加工：23，饮片经营：31
     */
        String xhCode = "";
        switch (traceCode.substring(1, 3)) {
            case "11":
                xhCode = getMedicineSaleXhCodeByTraceCode(traceCode);
                break;
            case "04":
                xhCode = getPlantSaleXhCodeByTraceCode(traceCode);
                break;
            case "23":
                xhCode = getYpProcessXhCodeByTraceCode(traceCode);
                break;
            case "31":
                xhCode = getYpBusinessXhCodeByTraceCode(traceCode);
        }
        return xhCode;
    }

    /**
     * 种植-根据追溯码查询xhCode
     */
    private String getPlantSaleXhCodeByTraceCode(String traceCode) {
        QueryWrapper<WptpSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trace_code", traceCode);
        queryWrapper.eq("deleted", "0");
        WptpSale wptpSale = iWptpSaleService.getBaseMapper().selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpSale)) return null;
        String entId = "";
        entId = getEntIdByEntName(wptpSale.getHostCode());
        if (oConvertUtils.isEmpty(entId)) entId = getEntIdByHostCode(wptpSale.getHostCode());
        String entXhCode = getEntXhCode(entId);
        return entXhCode;
    }

    /**
     * 药材经营-根据追溯码查询xhCode
     */
    private String getMedicineSaleXhCodeByTraceCode(String traceCode) {
        QueryWrapper<WptpMedicineSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trace_code", traceCode);
        queryWrapper.eq("deleted", "0");
        WptpMedicineSale wptpMedicineSale = iWptpMedicineSaleService.getBaseMapper().selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpMedicineSale)) return null;
        String entIdByHostCode = getEntIdByHostCode(wptpMedicineSale.getHostCode());//主机代码
        String entXhCode = getEntXhCode(entIdByHostCode);
        return entXhCode;
    }

    /**
     * 饮片加工-根据追溯码查询xhCode
     */
    private String getYpProcessXhCodeByTraceCode(String traceCode) {
        QueryWrapper<WptpYpSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trace_code", traceCode);
        queryWrapper.eq("deleted", "0");
        WptpYpSale wptpYpSale = iWptpYpSaleService.getBaseMapper().selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpYpSale)) return null;
        String entIdByHostCode = getEntIdByHostCode(wptpYpSale.getHostCode());//主机代码
        String entXhCode = getEntXhCode(entIdByHostCode);
        return entXhCode;
    }

    /**
     * 饮片经营-根据追溯码查询xhCode
     */
    private String getYpBusinessXhCodeByTraceCode(String traceCode) {
        QueryWrapper<WptpYpbSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trace_code", traceCode);
        queryWrapper.eq("deleted", "0");
        WptpYpbSale wptpYpbSale = iWptpYpbSaleService.getBaseMapper().selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpYpbSale)) return null;
        String entIdByHostCode = getEntIdByHostCode(wptpYpbSale.getHostCode());//主机代码
        String entXhCode = getEntXhCode(entIdByHostCode);
        return entXhCode;
    }

    /**
     * 根据企业名称获取entID
     *
     * @param entName
     * @return
     */
    private String getEntIdByEntName(String entName) {
        QueryWrapper<WptpEntInfo> entInfoQueryWrapper = new QueryWrapper<>();
        if (oConvertUtils.isEmpty(entName)) return null;
        entInfoQueryWrapper.eq("ent_name", entName.trim());
        entInfoQueryWrapper.eq("deleted", "0");
        WptpEntInfo wptpEntInfo = iWptpEntInfoService.getBaseMapper().selectOne(entInfoQueryWrapper);
        if (!oConvertUtils.isEmpty(wptpEntInfo)) return wptpEntInfo.getEntId();
        return null;
    }
/**
 * 上传成品或者原料相关到行业协会
 */

    /**
     * 根据药材或者饮片编码
     *
     * @param code
     * @return
     */
    private String getMedicinalName(String code) throws Exception {
        QueryWrapper<WptpMedicinal> queryWrapper = new QueryWrapper<>();
        if (oConvertUtils.isEmpty(code)) return null;
        queryWrapper.eq("medicinal_code", code.trim());
        queryWrapper.eq("deleted", "0");
        WptpMedicinal wptpMedicinal = iWptpMedicinalService.getBaseMapper().selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpMedicinal)) return wptpMedicinal.getName();
        return "";
    }

    /**
     * 根据主机代码查询企业id
     *
     * @return
     */
    private String getEntIdByHostCode(String hostCode) {
        QueryWrapper<WptpHostcode> entInfoQueryWrapper = new QueryWrapper<>();
        if (oConvertUtils.isEmpty(hostCode)) return null;
        entInfoQueryWrapper.eq("host_code", hostCode.trim());
        WptpHostcode wptpHostcode = iWptpHostcodeService.getBaseMapper().selectOne(entInfoQueryWrapper);
        if (!oConvertUtils.isEmpty(wptpHostcode)) return wptpHostcode.getEntId();
        return null;
    }

    /**
     * 根据企业id查询xhCode
     *
     * @return
     */
    private String getEntXhCode(String entId) {
        QueryWrapper<WptpEntInfo> entInfoQueryWrapper = new QueryWrapper<>();
        if (oConvertUtils.isEmpty(entId)) return null;
        entInfoQueryWrapper.eq("ent_id", entId.trim());
        entInfoQueryWrapper.eq("deleted", "0");
        WptpEntInfo wptpEntInfo = iWptpEntInfoService.getBaseMapper().selectOne(entInfoQueryWrapper);
        if (!oConvertUtils.isEmpty(wptpEntInfo)) return wptpEntInfo.getXhCode();
        return null;
    }

    /**
     * 根据企业id查询name
     *
     * @return
     */
    private String getEntName(String entId) {
        QueryWrapper<WptpEntInfo> entInfoQueryWrapper = new QueryWrapper<>();
        if (oConvertUtils.isEmpty(entId)) return null;
        entInfoQueryWrapper.eq("ent_id", entId.trim());
        entInfoQueryWrapper.eq("deleted", "0");
        WptpEntInfo wptpEntInfo = iWptpEntInfoService.getBaseMapper().selectOne(entInfoQueryWrapper);
        if (!oConvertUtils.isEmpty(wptpEntInfo)) return wptpEntInfo.getEntName();
        return null;
    }

    /**
     * 根据档案id查询一个作业
     *
     * @param blockMedicinalId
     * @return
     */
    private WptpPlantInfoVO getPlantInfo(String blockMedicinalId, String plantType) {
        WptpBlockMeidicinalVO blockMeidicinalVO = traceBaseDataService.getBlockMeidicinalVO(blockMedicinalId);//档案
        if (!oConvertUtils.isEmpty(blockMeidicinalVO)) {
            List<WptpPlantInfoVO> wptpPlantInfoVOList = traceBaseDataService.listPlantInfoVO(blockMeidicinalVO.getBlockMedicinalId());
            if (!wptpPlantInfoVOList.isEmpty()) {
                for (WptpPlantInfoVO w :
                        wptpPlantInfoVOList) {
                    if (plantType.equals(w.getPlantSatus())) return w;//如果是采收的作业
                }
            }
        }
        return null;
    }

    /**
     * 按照行业协会的接口，处理企业信息参数
     *
     * @param entId
     * @param xhCode
     * @return
     */
    private EnterpriseInfo handleEnterpriseInfo(String entId, String xhCode) {
        EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
        enterpriseInfo.setEnterpriseCode(entId);
        enterpriseInfo.setEnterpriseId(xhCode);
        return enterpriseInfo;
    }

    /**
     * 按照行业协会的接口，处理饮片经营的销售环节参数
     *
     * @return
     */
    private List<OrderShipment> handleYpBusinessSale(WptpYpbSaleVO wptpYpbSaleVO) {
        OrderShipment orderShipment = new OrderShipment();
        String saleTime = wptpYpbSaleVO.getSaleTime();
        orderShipment.setOrderShipmentId(wptpYpbSaleVO.getSaleNumber());
        orderShipment.setInvoiceNumCode(wptpYpbSaleVO.getSaleNo());
        if (!oConvertUtils.isEmpty(saleTime)) orderShipment.setDeliveryTime(DateUtils.StringToDate(saleTime));
        orderShipment.setDeliveryNumber(wptpYpbSaleVO.getNum());
        orderShipment.setUnit(wptpYpbSaleVO.getUnit());
        orderShipment.setPackSpec(wptpYpbSaleVO.getYpGuige());
        orderShipment.setTranPerson(wptpYpbSaleVO.getResponsible());
        orderShipment.setFlowEnterName(getEntName(wptpYpbSaleVO.getEntId()));
        orderShipment.setProductId(wptpYpbSaleVO.getYpCode());
        orderShipment.setProductBatch(wptpYpbSaleVO.getProductBatch());
        orderShipment.setSourceMoudel("6");
        List<OrderShipment> orderShipments = new ArrayList<>();
        orderShipments.add(orderShipment);
        return orderShipments;
    }

    /**
     * 按照行业协会的接口，处理饮片经营的入库环节参数
     *
     * @return
     */
    private Map<String, Object> handleYpBusinessInstock(WptpYpbInstockVO wptpYpbInstock) {

        /**
         * 需要把饮片经营-饮片入库的字段，插入到行业协会的仓库表、入库信息表，采购信息表中
         */
        String instockNumber = wptpYpbInstock.getInstockNumber();
        String instockNo = wptpYpbInstock.getInstockNo();
        String purchaseResponsible = wptpYpbInstock.getPurchaseResponsible();
        String arrivalTime = wptpYpbInstock.getArrivalTime();
        String ypName = wptpYpbInstock.getYpName();
        String productBatch = wptpYpbInstock.getProductBatch();
        Double num = wptpYpbInstock.getNum();
        String unit = wptpYpbInstock.getUnit();
        String ypCode = wptpYpbInstock.getYpCode();
        String manufacturer = wptpYpbInstock.getManufacturer();
        String packGuige = wptpYpbInstock.getPackGuige();
        String medicinalOrigin = wptpYpbInstock.getMedicinalOrigin();
        String guige = wptpYpbInstock.getGuige();
        /**
         * 仓库信息
         */
        WarehouseInfo warehouseInfo = new WarehouseInfo();
        warehouseInfo.setWarehouseName(wptpYpbInstock.getStoreCode());
        warehouseInfo.setInventoryType(YP_PRODUCTS);
        List<Curing> curings = new ArrayList<>();
        Curing curing = new Curing();//仓库养护
        curing.setCuringId(instockNumber);
        curing.setProcessingMethod(wptpYpbInstock.getMethod());
        curings.add(curing);
        warehouseInfo.setCuringList(curings);
        /**
         * 入库信息
         */
        List<StorageInfo> storageInfoList = new ArrayList<>();
        StorageInfo storageInfo = new StorageInfo();
        storageInfo.setStorageInfoId(instockNumber);
        storageInfo.setStorageType(1);
        storageInfo.setBatchCode(instockNo);
        storageInfo.setResponsibleUser(purchaseResponsible);
        if (!oConvertUtils.isEmpty(arrivalTime)) storageInfo.setStorageTime(DateUtils.StringToDate(arrivalTime));

        List<StorageDetails> storageDetailsList = new ArrayList<>();
        StorageDetails storageDetails = new StorageDetails();
        storageDetails.setStorageDetailsId(instockNumber);
        storageDetails.setProductInfoName(ypName);
        storageDetails.setProductBatchCode(productBatch);
        storageDetails.setProductNum(num);
        storageDetails.setUnit(unit);
        storageDetailsList.add(storageDetails);
        storageInfo.setStorageDetailsList(storageDetailsList);
        storageInfoList.add(storageInfo);
        warehouseInfo.setStorageInfoList(storageInfoList);
        /**
         * 采收购信息
         */
        PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo();
        purchaseOrderInfo.setPurchaseOrderId(instockNumber);
        purchaseOrderInfo.setSourceMoudel("4");
        purchaseOrderInfo.setBatchCode(instockNo);
        purchaseOrderInfo.setContactUser(purchaseResponsible);
        if (!oConvertUtils.isEmpty(arrivalTime)) purchaseOrderInfo.setArrivalTime(DateUtils.StringToDate(arrivalTime));

        List<PurchaseOrderDetails> purchaseOrdersList = new ArrayList<>();
        PurchaseOrderDetails purchaseOrderDetails = new PurchaseOrderDetails();
        purchaseOrderDetails.setPurchaseOrderDetailsId(instockNumber);
        purchaseOrderDetails.setProductInfoId(ypCode);
        purchaseOrderDetails.setMedicinalOrigin(medicinalOrigin);
        purchaseOrderDetails.setPackingSpecification(packGuige);
        purchaseOrderDetails.setProviderInfoName(getEntName(wptpYpbInstock.getEntId()));
        purchaseOrderDetails.setProductNum(num);
        purchaseOrderDetails.setUnit(unit);
        purchaseOrderDetails.setManufacturingEnterprise(manufacturer);
        purchaseOrderDetails.setProduceBatchCode(productBatch);
        purchaseOrderDetails.setRawGauge(guige);
        purchaseOrdersList.add(purchaseOrderDetails);
        purchaseOrderInfo.setPurchaseOrderDetailsList(purchaseOrdersList);

        /**
         * 企业信息
         */
        /*String entId = wptpYpbInstock.getEntId();*/
        String entId = getEntIdByHostCode(wptpYpbInstock.getHostCode());
        String entXhCode = getEntXhCode(entId);
        EnterpriseInfo enterpriseInfo = handleEnterpriseInfo(entId, entXhCode);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("purchaseOrderInfo", purchaseOrderInfo);
        resultMap.put("warehouseInfo", warehouseInfo);
        resultMap.put("enterpriseInfo", enterpriseInfo);
        return resultMap;
    }

    /**
     * 按照行业协会的接口，处理饮片加工的销售环节参数
     *
     * @return
     */
    private List<OrderShipment> handleYpProcessSale(WptpYpSaleVO wptpYpSaleVO) {
        OrderShipment orderShipment = new OrderShipment();
        orderShipment.setOrderShipmentId(wptpYpSaleVO.getSaleNumber());
        orderShipment.setInvoiceNumCode(wptpYpSaleVO.getSaleNo());
        String saleTime = wptpYpSaleVO.getSaleTime();
        if (!oConvertUtils.isEmpty(saleTime)) orderShipment.setDeliveryTime(DateUtils.StringToDate(saleTime));
        orderShipment.setDeliveryNumber(wptpYpSaleVO.getNum());
        orderShipment.setUnit(wptpYpSaleVO.getUnit());
        orderShipment.setTranPerson(wptpYpSaleVO.getResponsible());
        orderShipment.setFlowEnterName(getEntName(wptpYpSaleVO.getEntId()));
        orderShipment.setProductId(wptpYpSaleVO.getYpCode());
        orderShipment.setProductBatch(wptpYpSaleVO.getBatchNo());
        orderShipment.setSourceMoudel("5");
        orderShipment.setPackSpec(wptpYpSaleVO.getPackGuige());
        List<OrderShipment> orderShipments = new ArrayList<>();
        orderShipments.add(orderShipment);
        return orderShipments;
    }

    /**
     * 按照行业协会的接口，处理饮片加工的加工环节参数
     *
     * @return
     */
    private ProcessingInfo handleYpProcessProcess(WptpYpProcessVO wptpYpProcessVO) {
        ProcessingInfo processingInfo = new ProcessingInfo();

        String processNo = wptpYpProcessVO.getProcessNo();
        String process = wptpYpProcessVO.getProcess();
        Double productNum = wptpYpProcessVO.getProductNum();
        String punit = wptpYpProcessVO.getPunit();
        String producedDate = wptpYpProcessVO.getProducedDate();
        String processResponsible = wptpYpProcessVO.getProcessResponsible();
        String instockNumber = wptpYpProcessVO.getInstockNumber();
        String instockNo = wptpYpProcessVO.getInstockNo();
        Double materialNum = wptpYpProcessVO.getMaterialNum();
        String unit = wptpYpProcessVO.getUnit();
        String categoryCode = wptpYpProcessVO.getCategoryCode();

        processingInfo.setProcessId(processNo);
        processingInfo.setSourceMoudel("2");
        processingInfo.setProcessName(process);
        processingInfo.setTotalWeight(productNum);
        processingInfo.setOutUnitName(punit);
        if (!oConvertUtils.isEmpty(producedDate))
            processingInfo.setProcessStartTime(DateUtils.StringToDate(producedDate));
        processingInfo.setResponsibleUser(processResponsible);

        ProcessMaterial processMaterial = new ProcessMaterial();
        List<ProcessMaterial> processMaterialList = new ArrayList<>();
        processMaterial.setProcessMaterialId(instockNo);
        processMaterial.setProductInfoId(categoryCode);
        processMaterial.setProductBatchCode(instockNumber);
        processMaterial.setProcessNum(materialNum);
        processMaterial.setUnit(unit);
        processMaterialList.add(processMaterial);
        processingInfo.setProcessMaterialList(processMaterialList);
        return processingInfo;
    }

    /**
     * 按照行业协会的接口，处理饮片加工的入库环节参数
     *
     * @return
     */
    private Map<String, Object> handleYpProcessInstock(WptpYpInstockVO wptpYpInstock) {

        /**
         * 需要把饮片加工-饮片入库的字段，插入到行业协会的仓库表、入库信息表，采购信息表中
         */
        String instockNumber = wptpYpInstock.getInstockNumber();
        String instockNo = wptpYpInstock.getInstockNo();
        String responsible = wptpYpInstock.getResponsible();
        String arrivalTime = wptpYpInstock.getArrivalTime();
        String categoryName = wptpYpInstock.getCategoryName();
        String productBatch = wptpYpInstock.getProductBatch();
        Double num = wptpYpInstock.getNum();
        String categoryCode = wptpYpInstock.getCategoryCode();
        String manufacturer = wptpYpInstock.getManufacturer();
        /*String entId=getEntIdByHostCode(wptpYpInstock.getHostCode());*/
        String entId = wptpYpInstock.getEntId();
        String storeCode = wptpYpInstock.getStoreCode();
        String method = wptpYpInstock.getMethod();
        String materialOrigin = wptpYpInstock.getMaterialOrigin();
        String guige = wptpYpInstock.getGuige();
        String unit = wptpYpInstock.getUnit();
        String materialBatch = wptpYpInstock.getMaterialBatch();
        /**
         * 仓库信息
         */
        WarehouseInfo warehouseInfo = new WarehouseInfo();
        warehouseInfo.setWarehouseName(storeCode);
        warehouseInfo.setInventoryType(YP_MATERIALS);
        List<Curing> curings = new ArrayList<>();
        Curing curing = new Curing();//仓库养护
        curing.setCuringId(instockNumber);
        curing.setProcessingMethod(method);
        curings.add(curing);
        warehouseInfo.setCuringList(curings);
        /**
         * 入库信息
         */
        List<StorageInfo> storageInfoList = new ArrayList<>();
        StorageInfo storageInfo = new StorageInfo();
        storageInfo.setStorageInfoId(instockNumber);
        storageInfo.setStorageType(1);
        storageInfo.setBatchCode(instockNo);
        storageInfo.setResponsibleUser(responsible);
        if (!oConvertUtils.isEmpty(arrivalTime)) storageInfo.setStorageTime(DateUtils.StringToDate(arrivalTime));

        List<StorageDetails> storageDetailsList = new ArrayList<>();
        StorageDetails storageDetails = new StorageDetails();
        storageDetails.setStorageDetailsId(instockNo);
        storageDetails.setProductInfoName(categoryName);
        storageDetails.setProductBatchCode(materialBatch);
        storageDetails.setProductNum(num);
        storageDetails.setUnit(unit);
        storageDetailsList.add(storageDetails);
        storageInfo.setStorageDetailsList(storageDetailsList);
        storageInfoList.add(storageInfo);
        warehouseInfo.setStorageInfoList(storageInfoList);
        /**
         * 采收购信息
         */
        PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo();
        purchaseOrderInfo.setPurchaseOrderId(instockNumber);
        purchaseOrderInfo.setSourceMoudel("2");
        purchaseOrderInfo.setBatchCode(instockNo);
        purchaseOrderInfo.setContactUser(responsible);
        if (!oConvertUtils.isEmpty(arrivalTime)) purchaseOrderInfo.setArrivalTime(DateUtils.StringToDate(arrivalTime));

        List<PurchaseOrderDetails> purchaseOrdersList = new ArrayList<>();
        PurchaseOrderDetails purchaseOrderDetails = new PurchaseOrderDetails();
        purchaseOrderDetails.setPurchaseOrderDetailsId(instockNumber);
        purchaseOrderDetails.setProductInfoId(categoryCode);
        purchaseOrderDetails.setProviderInfoName(getEntName(entId));
        purchaseOrderDetails.setProductNum(num);
        purchaseOrderDetails.setManufacturingEnterprise(manufacturer);
        purchaseOrderDetails.setProduceBatchCode(materialBatch);
        purchaseOrderDetails.setMedicinalOrigin(materialOrigin);
        purchaseOrderDetails.setRawGauge(guige);
        purchaseOrderDetails.setUnit(unit);
        purchaseOrdersList.add(purchaseOrderDetails);
        purchaseOrderInfo.setPurchaseOrderDetailsList(purchaseOrdersList);

        /**
         * 企业信息
         */
      /*  String entXhCode = getEntXhCode(entId);
        EnterpriseInfo enterpriseInfo = handleEnterpriseInfo(entId, entXhCode);*/

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("purchaseOrderInfo", purchaseOrderInfo);
        resultMap.put("warehouseInfo", warehouseInfo);
        /*resultMap.put("enterpriseInfo",enterpriseInfo);*/
        return resultMap;
    }

    /**
     * 按照行业协会的接口，处理饮片加工-饮片入库的检测记录相关参数（来源1：饮片加工-饮片入库，2：饮片加工-饮片加工）
     *
     * @return
     */
    private QualitySelfTest handleYpProcessInStockQualitySelfTest(WptpYpInstockVO wptpYpInstockVO) {
        QualitySelfTest qualitySelfTest = new QualitySelfTest();
        qualitySelfTest.setQualitySelfTestId(wptpYpInstockVO.getCheckNo());
        qualitySelfTest.setSourceMoudel("1");
        qualitySelfTest.setProductInfoId(wptpYpInstockVO.getCategoryCode());
        qualitySelfTest.setDetectionProductBatchCode(wptpYpInstockVO.getMaterialBatch());
        String checkTime = wptpYpInstockVO.getCheckTime();
        if (!oConvertUtils.isEmpty(checkTime)) qualitySelfTest.setCheckDate(DateUtils.StringToDate(checkTime));
        qualitySelfTest.setPurpose(wptpYpInstockVO.getCheckPurpose());
        qualitySelfTest.setTestSummary(wptpYpInstockVO.getCheckResult());
        qualitySelfTest.setReviewName(wptpYpInstockVO.getCheckResponsible());
        qualitySelfTest.setDetectionBasis(wptpYpInstockVO.getCheckBasis());
        /**
         * 入库文件
         */
        QueryWrapper<WptpYpInstockFile> ypInstockFileQueryWrapper = new QueryWrapper<>();
        ypInstockFileQueryWrapper.eq("deleted", "0");
        ypInstockFileQueryWrapper.eq("main_id", wptpYpInstockVO.getInstockNumber());
        List<WptpYpInstockFile> wptpYpInstockFiles = iWptpYpInstockFileService.getBaseMapper().selectList(ypInstockFileQueryWrapper);
        StringBuilder sb = new StringBuilder();
        for (WptpYpInstockFile w :
                wptpYpInstockFiles) {
            if (!"0".equals(w.getFileType())) continue;
            sb.append("|");
            sb.append(IMG_URL_PREIX + w.getPath());
        }
        String imgUrlArray = sb.toString();
        if (!oConvertUtils.isEmpty(imgUrlArray))
            qualitySelfTest.setImage(imgUrlArray.substring(1, imgUrlArray.length()));//
        return qualitySelfTest;
    }

    /**
     * 按照行业协会的接口，处理饮片加工-饮片加工的检测记录相关参数（来源1：饮片加工-饮片入库，2：饮片加工-饮片加工）
     *
     * @param wptpYpProcessVO
     * @return
     */
    private QualitySelfTest handleYpProcessQualitySelfTest(WptpYpProcessVO wptpYpProcessVO) {
        QualitySelfTest qualitySelfTest = new QualitySelfTest();
        String checkTime = wptpYpProcessVO.getCheckTime();
        qualitySelfTest.setQualitySelfTestId(wptpYpProcessVO.getQualCheckNum());
        qualitySelfTest.setSourceMoudel("2");
        qualitySelfTest.setProductInfoId(wptpYpProcessVO.getYpCode());
        qualitySelfTest.setDetectionProductBatchCode(wptpYpProcessVO.getProductBatch());
        if (!oConvertUtils.isEmpty(checkTime)) qualitySelfTest.setCheckDate(DateUtils.StringToDate(checkTime));
        qualitySelfTest.setPurpose(wptpYpProcessVO.getCheckPurpose());
        qualitySelfTest.setTestSummary(wptpYpProcessVO.getCheckResult());
        qualitySelfTest.setReviewName(wptpYpProcessVO.getCheckResponsible());
        qualitySelfTest.setDetectionBasis(wptpYpProcessVO.getCheckBasis());
        /**
         * 加工文件
         */
        QueryWrapper<WptpYpProcessFile> ypProcessFileQueryWrapper = new QueryWrapper<>();
        ypProcessFileQueryWrapper.eq("deleted", "0");
        ypProcessFileQueryWrapper.eq("main_id", wptpYpProcessVO.getProcessNo());
        List<WptpYpProcessFile> wptpYpProcessFiles = iWptpYpProcessFileService.getBaseMapper().selectList(ypProcessFileQueryWrapper);
        StringBuilder sb = new StringBuilder();
        for (WptpYpProcessFile w :
                wptpYpProcessFiles) {
            if (!"0".equals(w.getFileType())) continue;
            sb.append("|");
            sb.append(IMG_URL_PREIX + w.getPath());
        }
        String imgUrlArray = sb.toString();
        if (!oConvertUtils.isEmpty(imgUrlArray))
            qualitySelfTest.setImage(imgUrlArray.substring(1, imgUrlArray.length()));//去除第一个竖线"|"
        return qualitySelfTest;
    }

    /**
     * 按照行业协会的接口，处理饮片加工的包装环节参数
     *
     * @return
     */
    private List<PackingInfo> handleYpProcessPack(WptpYpPackVO wptpYpPackVO) {
        PackingInfo packingInfo = new PackingInfo();
        packingInfo.setSourceMoudel("1");
        packingInfo.setPackingBatchCode(wptpYpPackVO.getPackNo());
        packingInfo.setProductId(wptpYpPackVO.getCategoryCode());
        packingInfo.setPackingBatchCode(wptpYpPackVO.getProductBatch());
        packingInfo.setPackingNum(wptpYpPackVO.getNumber());
        packingInfo.setUnitStandard(wptpYpPackVO.getPackGuige());
        packingInfo.setUnit(wptpYpPackVO.getUnit());
        List<PackingInfo> packingInfoList = new ArrayList<>();
        packingInfoList.add(packingInfo);
        return packingInfoList;
    }

    /**
     * 按照行业协会的接口，处理药材经营的入库环节参数
     *
     * @return
     */
    private Map<String, Object> handleMedicineInstock(WptpMedicineInstockVO wptpMedicineInstockVO) {

        /**
         * 需要把药材经营-药材入库的字段，插入到行业协会的仓库表、入库信息表，采购信息表中
         */
        String instockNumber = wptpMedicineInstockVO.getInstockNumber();
        String instockNo = wptpMedicineInstockVO.getInstockNo();
        String categoryName = wptpMedicineInstockVO.getCategoryName();
        String productBatch = wptpMedicineInstockVO.getProductBatch();
        String categoryCode = wptpMedicineInstockVO.getCategoryCode();
        String manufacturer = wptpMedicineInstockVO.getManufacturer();
        /* String entId=getEntIdByHostCode(wptpMedicineInstockVO.getHostCode());*/
        String entId = wptpMedicineInstockVO.getEntId();
        String storeCode = wptpMedicineInstockVO.getStoreCode();
        String method = wptpMedicineInstockVO.getMethod();
        String purchaseLeader = wptpMedicineInstockVO.getPurchaseLeader();
        String arriveTime = wptpMedicineInstockVO.getArriveTime();
        Double purchaseNum = wptpMedicineInstockVO.getPurchaseNum();
        String unit = wptpMedicineInstockVO.getUnit();
        String medicinalOrigin = wptpMedicineInstockVO.getMedicinalOrigin();
        String guige = wptpMedicineInstockVO.getGuige();
        /**
         * 仓库信息
         */
        WarehouseInfo warehouseInfo = new WarehouseInfo();
        warehouseInfo.setWarehouseName(storeCode);
        warehouseInfo.setInventoryType(MEDICINAL_PRODUCING_AREA);
        List<Curing> curings = new ArrayList<>();
        Curing curing = new Curing();//仓库养护
        curing.setCuringId(instockNumber);
        curing.setProcessingMethod(method);
        curings.add(curing);
        warehouseInfo.setCuringList(curings);
        /**
         * 入库信息
         */
        List<StorageInfo> storageInfoList = new ArrayList<>();
        StorageInfo storageInfo = new StorageInfo();
        storageInfo.setStorageInfoId(instockNumber);
        storageInfo.setStorageType(1);
        storageInfo.setBatchCode(instockNo);
        storageInfo.setResponsibleUser(purchaseLeader);
        if (!oConvertUtils.isEmpty(arriveTime)) storageInfo.setStorageTime(DateUtils.StringToDate(arriveTime));

        List<StorageDetails> storageDetailsList = new ArrayList<>();
        StorageDetails storageDetails = new StorageDetails();
        storageDetails.setStorageDetailsId(instockNumber);
        storageDetails.setProductInfoName(categoryCode);
        storageDetails.setProductBatchCode(productBatch);
        storageDetails.setProductNum(purchaseNum);
        storageDetails.setUnit(unit);
        storageDetailsList.add(storageDetails);
        storageInfo.setStorageDetailsList(storageDetailsList);
        storageInfoList.add(storageInfo);
        warehouseInfo.setStorageInfoList(storageInfoList);
        /**
         * 采收购信息
         */
        PurchaseOrderInfo purchaseOrderInfo = new PurchaseOrderInfo();
        purchaseOrderInfo.setPurchaseOrderId(instockNumber);
        purchaseOrderInfo.setSourceMoudel("1");
        purchaseOrderInfo.setBatchCode(instockNo);
        purchaseOrderInfo.setContactUser(purchaseLeader);
        if (!oConvertUtils.isEmpty(arriveTime)) purchaseOrderInfo.setArrivalTime(DateUtils.StringToDate(arriveTime));

        List<PurchaseOrderDetails> purchaseOrdersList = new ArrayList<>();
        PurchaseOrderDetails purchaseOrderDetails = new PurchaseOrderDetails();
        purchaseOrderDetails.setPurchaseOrderDetailsId(instockNumber);
        purchaseOrderDetails.setProductInfoId(categoryCode);
        purchaseOrderDetails.setProviderInfoName(getEntName(entId));
        purchaseOrderDetails.setManufacturingEnterprise(manufacturer);
        purchaseOrderDetails.setUnit(unit);
        purchaseOrderDetails.setProduceBatchCode(productBatch);
        purchaseOrderDetails.setProductNum(purchaseNum);
        purchaseOrderDetails.setMedicinalOrigin(medicinalOrigin);
        purchaseOrderDetails.setRawGauge(guige);
        purchaseOrdersList.add(purchaseOrderDetails);
        purchaseOrderInfo.setPurchaseOrderDetailsList(purchaseOrdersList);

        /**
         * 企业信息
         */
     /*   String entXhCode = getEntXhCode(entId);
        EnterpriseInfo enterpriseInfo = handleEnterpriseInfo(entId, entXhCode);*/

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("purchaseOrderInfo", purchaseOrderInfo);
        resultMap.put("warehouseInfo", warehouseInfo);
        /*resultMap.put("enterpriseInfo",enterpriseInfo);*/
        return resultMap;
    }

    /**
     * 按照行业协会的接口，处理药材经营的销售环节参数
     *
     * @return
     */
    private List<OrderShipment> handleMedicineSale(WptpMedicineSale wptpMedicineSale) {
        OrderShipment orderShipment = new OrderShipment();
        orderShipment.setOrderShipmentId(wptpMedicineSale.getSaleNumber());
        orderShipment.setInvoiceNumCode(wptpMedicineSale.getSaleNo());
        String outTime = wptpMedicineSale.getOutTime();
        if (!oConvertUtils.isEmpty(outTime)) orderShipment.setDeliveryTime(DateUtils.StringToDate(outTime));
        orderShipment.setDeliveryNumber(wptpMedicineSale.getNum());
        orderShipment.setUnit(wptpMedicineSale.getUnit());
        orderShipment.setTranPerson(wptpMedicineSale.getResponsible());
        orderShipment.setFlowEnterName(getEntName(wptpMedicineSale.getEntId()));
        orderShipment.setProductId(wptpMedicineSale.getCategoryCode());
        orderShipment.setSourceMoudel("4");
        List<OrderShipment> orderShipments = new ArrayList<>();
        orderShipments.add(orderShipment);
        return orderShipments;
    }

    /**
     * 按照行业协会的接口，处理药材经营-药材入库的检测记录相关参数
     *
     * @param wptpYpProcessVO
     * @return
     */
    private QualitySelfTest handleMedicineSaleQualitySelfTest(WptpMedicineInstockVO wptpMedicineInstockVO) {
        QualitySelfTest qualitySelfTest = new QualitySelfTest();
        String checkTime = wptpMedicineInstockVO.getCheckTime();
        qualitySelfTest.setQualitySelfTestId(wptpMedicineInstockVO.getCheckNo());
        qualitySelfTest.setSourceMoudel("4");
        qualitySelfTest.setProductInfoId(wptpMedicineInstockVO.getCategoryCode());
        qualitySelfTest.setDetectionProductBatchCode(wptpMedicineInstockVO.getInstockNo());
        if (!oConvertUtils.isEmpty(checkTime)) qualitySelfTest.setCheckDate(DateUtils.StringToDate(checkTime));
        qualitySelfTest.setPurpose(wptpMedicineInstockVO.getCheckPurpose());
        qualitySelfTest.setTestSummary(wptpMedicineInstockVO.getCheckResult());
        qualitySelfTest.setReviewName(wptpMedicineInstockVO.getCheckResponsible());
        qualitySelfTest.setDetectionBasis(wptpMedicineInstockVO.getCheckBasis());
        /**
         * 加工文件
         */
        QueryWrapper<WptpMedicineInstockFile> medicineInstockFileQueryWrapper = new QueryWrapper<>();
        medicineInstockFileQueryWrapper.eq("deleted", "0");
        medicineInstockFileQueryWrapper.eq("main_id", wptpMedicineInstockVO.getInstockNumber());
        List<WptpMedicineInstockFile> wptpMedicineInstockFileList = iWptpMedicineInstockFileService.getBaseMapper().selectList(medicineInstockFileQueryWrapper);
        StringBuilder sb = new StringBuilder();
        for (WptpMedicineInstockFile w :
                wptpMedicineInstockFileList) {
            if (!"0".equals(w.getFileType())) continue;
            sb.append("|");
            sb.append(IMG_URL_PREIX + w.getPath());
        }
        String imgUrlArray = sb.toString();
        if (!oConvertUtils.isEmpty(imgUrlArray))
            qualitySelfTest.setImage(imgUrlArray.substring(1, imgUrlArray.length()));//去除第一个竖线"|"
        return qualitySelfTest;
    }

    /**
     * 按照行业协会的接口，处理种植的销售环节参数
     *
     * @return
     */
    private List<OrderShipment> handlePlantSale(WptpSaleVO wptpSale) {
        OrderShipment orderShipment = new OrderShipment();
        String saleTime = wptpSale.getSaleTime();
        orderShipment.setOrderShipmentId(wptpSale.getSaleNumber());
        orderShipment.setInvoiceNumCode(wptpSale.getSaleNo());
        if (!oConvertUtils.isEmpty(saleTime)) orderShipment.setDeliveryTime(DateUtils.StringToDate(saleTime));
        orderShipment.setDeliveryNumber(wptpSale.getNum());
        orderShipment.setUnit(wptpSale.getUnit());
        orderShipment.setTranPerson(wptpSale.getResponsible());
        orderShipment.setFlowEnterName(getEntName(wptpSale.getEntId()));
        orderShipment.setProductId(wptpSale.getMedicinalCode());
        orderShipment.setSourceMoudel("3");
        List<OrderShipment> orderShipments = new ArrayList<>();
        orderShipments.add(orderShipment);
        return orderShipments;
    }

    /**
     * 按照行业协会的接口，处理种植环节的初加工环节参数
     *
     * @return
     */
    private ProcessingInfo handlePlantProcess(WptpProcessInfo wptpProcessInfo, String medicineCode) {
        ProcessingInfo processingInfo = new ProcessingInfo();

        String processNo = wptpProcessInfo.getProcessNo();
        String processGy = wptpProcessInfo.getProcessGy();
        String processMethod = wptpProcessInfo.getProcessMethod();
        String unit = wptpProcessInfo.getUnit();
        Double num = wptpProcessInfo.getNum();
        String guige = wptpProcessInfo.getGuige();
        String startTime = wptpProcessInfo.getStartTime();
        String endTime = wptpProcessInfo.getEndTime();
        String responsible = wptpProcessInfo.getResponsible();
        String device = wptpProcessInfo.getDevice();

        processingInfo.setProcessId(processNo);
        processingInfo.setSourceMoudel("1");//种植端为1；饮片加工：2

        processingInfo.setProcessName(processGy);
        processingInfo.setProcessMedthod(processMethod);
        /*processingInfo.setUnit(unit);*/
        /* processingInfo.setTotalWeight(num);*/
        processingInfo.setOutUnitName(unit);
        if (!oConvertUtils.isEmpty(startTime)) processingInfo.setProcessStartTime(DateUtils.StringToDate(startTime));
        if (!oConvertUtils.isEmpty(endTime)) processingInfo.setProcessStartTime(DateUtils.StringToDate(endTime));
        processingInfo.setResponsibleUser(responsible);
        processingInfo.setProcessEquipment(device);

        QueryWrapper<WptpProcessFile> processFileQueryWrapper = new QueryWrapper<>();
        processFileQueryWrapper.eq("deleted", "0");
        processFileQueryWrapper.eq("main_id", wptpProcessInfo.getProcessNo());
        List<WptpProcessFile> wptpProcessFileList = iWptpProcessFileService.getBaseMapper().selectList(processFileQueryWrapper);
        StringBuilder sb = new StringBuilder();
        for (WptpProcessFile w :
                wptpProcessFileList) {
            if (!"0".equals(w.getFileType())) continue;
            sb.append("|");
            sb.append(IMG_URL_PREIX + w.getPath());
        }
        String imgUrlArray = sb.toString();
        if (!oConvertUtils.isEmpty(imgUrlArray))
            processingInfo.setImage(imgUrlArray.substring(1, imgUrlArray.length()));


        /**
         * 加工原料
         */
        QueryWrapper<WptpProcessMaterial> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("deleted", "0");
        queryWrapper.eq("process_no", wptpProcessInfo.getProcessNo());
        List<WptpProcessMaterial> wptpProcessMaterialList = iWptpProcessMaterial.getBaseMapper().selectList(queryWrapper);
        List<ProcessMaterial> processMaterialList = new ArrayList<>();
        for (WptpProcessMaterial w :
                wptpProcessMaterialList) {
            ProcessMaterial processMaterial = new ProcessMaterial();
            /* processMaterial.setProcessMaterialId(w.getCsBatch());*/
            processMaterial.setProductInfoId(medicineCode);
            /* processMaterial.setProductBatchCode(w.getCsBatch());*/
            /*processMaterial.setProcessNum(w.getMaterialNum());*/
            processMaterial.setUnit(w.getUnit());
            processMaterialList.add(processMaterial);
        }
        processingInfo.setProcessMaterialList(processMaterialList);
        return processingInfo;
    }

    /**
     * 按照行业协会的接口，处理种植环节的采收批次环节参数
     *
     * @return
     */
    private Map handlePlantCs(List<WptpCsInfoVO> wptpCsInfoList) {
        Map<String, Object> resultMap = new HashMap<>();
        List<HarvestBatch> harvestBatchList = new ArrayList<>();
        List<PlantingBatch> plantingBatchList = new ArrayList<>();
        for (WptpCsInfoVO w :
                wptpCsInfoList) {
            WptpPlantInfoVO plantInfo = getPlantInfo(w.getBlockMedicinalId(), "采收");//采收对应的作业
            if (oConvertUtils.isEmpty(plantInfo)) continue;
            PlantingBatch plantingBatch = handlePlantingBatch(traceBaseDataService.getBlockMeidicinalVO(w.getBlockMedicinalId()));
            if (!oConvertUtils.isEmpty(plantingBatch)) plantingBatchList.add(plantingBatch);
            String csNo = w.getCsNo();

            String csPart = plantInfo.getCsPart();
            String number = plantInfo.getNumber();
            String plantType = plantInfo.getPlantType();
            String operateTime = plantInfo.getOperateTime();
            String responsible = plantInfo.getResponsible();


            if (number.contains("KG") || number.contains("kg")) number = number.substring(0, number.length() - 2);
            HarvestBatch harvestBatch = new HarvestBatch();
            harvestBatch.setHarvestBatchId(csNo);
            harvestBatch.setBatchCode(csNo);
            harvestBatch.setHarvestPosition(csPart);
            harvestBatch.setHarvestVolume(number);
            harvestBatch.setHarvestType(plantType);
            if (!oConvertUtils.isEmpty(operateTime))
                harvestBatch.setEndOperateTime(DateUtils.StringToDate(operateTime));
            harvestBatch.setOperateName(responsible);
            harvestBatch.setHarvestProductName(w.getMedicinalCode());
            harvestBatch.setHarvestUnitName(w.getUnit());

            /**
             * 作业对应的图片和视频
             */
            QueryWrapper<WptpPlantFile> plantFileQueryWrapper = new QueryWrapper<>();
            plantFileQueryWrapper.eq("deleted", "0");
            plantFileQueryWrapper.eq("main_id", plantInfo.getPlantId());
            List<WptpPlantFile> wptpPlantFileList = iWptpPlantFileService.getBaseMapper().selectList(plantFileQueryWrapper);
            StringBuilder sb = new StringBuilder();//图片
            StringBuilder video = new StringBuilder();//视频
            for (WptpPlantFile wptpPlantFile :
                    wptpPlantFileList) {
                if ("0".equals(wptpPlantFile.getFileType())) {
                    sb.append("|");
                    sb.append(IMG_URL_PREIX + wptpPlantFile.getPath());
                }/*else if ("1".equals(wptpPlantFile.getFileType())){
                    sb.append("|");
                    sb.append(IMG_URL_PREIX+wptpPlantFile.getPath());
                }*/
            }
            String imgUrlArray = sb.toString();
            if (!oConvertUtils.isEmpty(imgUrlArray))
                harvestBatch.setImage(imgUrlArray.substring(1, imgUrlArray.length()));//去除第一个竖线"|"

          /*  String videoStr = video.toString();
            if (!oConvertUtils.isEmpty(videoStr)) harvestBatch.setFile(videoStr.substring(1,videoStr.length()));//去除第一个竖线"|"*/

            harvestBatchList.add(harvestBatch);
        }
        resultMap.put("harvestBatchList", harvestBatchList);
        resultMap.put("plantingBatchList", plantingBatchList);
        return resultMap;
    }

    /**
     * 按照行业协会的接口，处理种植环节的作业环节参数
     *
     * @return
     */
    private List<FarmScheme> handlePlantBlockMeidicinal(List<WptpPlantInfoVO> wptpPlantInfoVOList) {
        List<FarmScheme> farmSchemes = new ArrayList<>();
        for (WptpPlantInfoVO wptpPlantInfo :
                wptpPlantInfoVOList) {
            FarmScheme farmScheme = new FarmScheme();

            String plantId = wptpPlantInfo.getPlantId();
            String operateTime = wptpPlantInfo.getOperateTime();
            String plantSatus = wptpPlantInfo.getPlantSatus();
            String responsible = wptpPlantInfo.getResponsible();
            String inputInto = wptpPlantInfo.getInputInto();

            farmScheme.setFarmSchemeId(plantId);
            if (!oConvertUtils.isEmpty(operateTime)) farmScheme.setOperateTime(DateUtils.StringToDate(operateTime));
            farmScheme.setFarmOperationTypeName(plantSatus);
            farmScheme.setInputsProduct(inputInto);
            farmScheme.setResponsiblePerson(responsible);
            /**
             * 图片和视频
             */
            QueryWrapper<WptpPlantFile> plantFileQueryWrapper = new QueryWrapper<>();
            plantFileQueryWrapper.eq("main_id", wptpPlantInfo.getPlantId());
            plantFileQueryWrapper.eq("deleted", "0");
            List<WptpPlantFile> wptpPlantFileList = iWptpPlantFileService.getBaseMapper().selectList(plantFileQueryWrapper);

            StringBuilder sb = new StringBuilder();//图片
            StringBuilder video = new StringBuilder();//视频
            for (WptpPlantFile w :
                    wptpPlantFileList) {
                if ("0".equals(w.getFileType())) {
                    sb.append("|");
                    sb.append(IMG_URL_PREIX + w.getPath());
                }/*else if ("1".equals(w.getFileType())){
                    video.append("|");
                    video.append(IMG_URL_PREIX+w.getPath());
                }*/
            }
            String imgUrlArray = sb.toString();
            if (!oConvertUtils.isEmpty(imgUrlArray))
                farmScheme.setImage(imgUrlArray.substring(1, imgUrlArray.length()));

          /*  String videoStr = video.toString();
            if (!oConvertUtils.isEmpty(videoStr)) farmScheme.setFile(videoStr.substring(1,videoStr.length()));*///去除第一个竖线"|"
            farmSchemes.add(farmScheme);
        }


        return farmSchemes;
    }

    /**
     * 按照行业协会的接口，处理种植环节的基地环节参数
     *
     * @return
     */
    private Base handlePlantBase(WptpBase wptpBase) {
        Base base = new Base();
        String baseCode = wptpBase.getBaseCode();
        String baseName = wptpBase.getBaseName();
        String baseAddress = wptpBase.getBaseAddress();
        String gpsLatitude = wptpBase.getGpsLatitude();
        String gpsLongitude = wptpBase.getGpsLongitude();
        String waterSrc = wptpBase.getWaterSrc();
        String land = wptpBase.getLand();
        String heigh = wptpBase.getHeigh();
        String soilType = wptpBase.getSoilType();
        String ph = wptpBase.getPh();
        String weatherType = wptpBase.getWeatherType();
        String rearRainfall = wptpBase.getRearRainfall();
        String temperature = wptpBase.getTemperature();
        String baseDesc = wptpBase.getBaseDesc();
        String entName = getEntName(wptpBase.getEntId());


        base.setBaseId(baseCode);
        base.setBaseName(baseName);
        base.setAddress(baseAddress);
        base.setLatitude(gpsLatitude);
        base.setLongitude(gpsLongitude);
        base.setWaterSource(waterSrc);
        base.setTerrain(land);
        base.setAltitude(heigh);
        base.setSoilType(soilType);
        base.setSoilPh(ph);
        base.setClimateType(weatherType);
        base.setAnnualRainfall(rearRainfall);
        base.setAverageTemperature(temperature);
        base.setAffiliatedEnterprises(entName);
        base.setBasesContent(baseDesc);
        /**
         * 图片和报告
         */
        QueryWrapper<WptpBaseFile> baseFileQueryWrapper = new QueryWrapper<>();
        baseFileQueryWrapper.eq("main_id", wptpBase.getBaseCode());
        baseFileQueryWrapper.eq("deleted", "0");
        List<WptpBaseFile> wptpBaseFileList = iWptpBaseFileService.getBaseMapper().selectList(baseFileQueryWrapper);
        StringBuilder sb = new StringBuilder();//图片
        StringBuilder report = new StringBuilder();//视频
        for (WptpBaseFile w :
                wptpBaseFileList) {
            if ("0".equals(w.getType())) {
                sb.append("|");
                sb.append(IMG_URL_PREIX + w.getPath());
            }/*else if ("1".equals(w.getType())){
                report.append("|");
                report.append(IMG_URL_PREIX+w.getPath());
            }*/
        }
        String imgUrlArray = sb.toString();
        if (!oConvertUtils.isEmpty(imgUrlArray)) base.setImage(imgUrlArray.substring(1, imgUrlArray.length()));

       /* String reportStr = report.toString();
        if (!oConvertUtils.isEmpty(reportStr)) base.setFile(reportStr.substring(1,reportStr.length()));*///去除第一个竖线"|"

        return base;
    }

    /**
     * 按照行业协会的接口，处理种植环节的档案环节参数
     *
     * @return
     */
    private PlantingBatch handlePlantingBatch(WptpBlockMeidicinal wptpBlockMeidicinal) {
        /*  WptpPlantInfoVO plantInfo = getPlantInfo(wptpBlockMeidicinal.getBlockMedicinalId(),"采收");*///采收对应的作业
        /**
         * 查找档案底下对应的作业集合
         */
        List<WptpPlantInfoVO> wptpPlantInfoVOList = traceBaseDataService.listPlantInfoVO(wptpBlockMeidicinal.getBlockMedicinalId());
        List<FarmScheme> farmSchemeList = handlePlantBlockMeidicinal(wptpPlantInfoVOList);//处理作业参数
        WptpBaseVO wptpBaseVO = traceBaseDataService.getWptpBaseVO(wptpBlockMeidicinal.getBaseCode());
        Base base = handlePlantBase(wptpBaseVO);
        PlantingBatch plantingBatch = new PlantingBatch();
        plantingBatch.setBase(base);
        plantingBatch.setFarmSchemeList(farmSchemeList);
        String medicinalCode = wptpBlockMeidicinal.getMedicinalCode();
        String blockMedicinalId = wptpBlockMeidicinal.getBlockMedicinalId();
        String baseAdmin = wptpBlockMeidicinal.getBaseAdmin();
        String fileTime = wptpBlockMeidicinal.getFileTime();
        if (!oConvertUtils.isEmpty(fileTime)) plantingBatch.setPlantSatartTime(DateUtils.StringToDate(fileTime));
        plantingBatch.setPlantingBatchId(blockMedicinalId);
        plantingBatch.setProductId(medicinalCode);
        plantingBatch.setResponsibleUser(baseAdmin);
        WptpPlantInfoVO plantSource = getPlantInfo(wptpBlockMeidicinal.getBlockMedicinalId(), "种原");//种原对应的作业
        if (!oConvertUtils.isEmpty(plantSource)) {
            plantingBatch.setReproductiveSources(plantSource.getSource());
        }
        /**
         * 从地块表中取种植面积
         */
        QueryWrapper<WptpBlockInfo> wptpBlockInfoQueryWrapper = new QueryWrapper<>();
        wptpBlockInfoQueryWrapper.eq("deleted", "0");
        wptpBlockInfoQueryWrapper.eq("block_code", wptpBlockMeidicinal.getBlockCode());
        WptpBlockInfo wptpBlockInfo = iWptpBlockInfoService.getBaseMapper().selectOne(wptpBlockInfoQueryWrapper);
        if (!oConvertUtils.isEmpty(wptpBlockInfo)) {
            plantingBatch.setCropArea(wptpBlockInfo.getBaseArea());
        }
        return plantingBatch;
    }

    /**
     * 拿到追溯结果
     */
    public Result<TraceVO> traceByCode(String traceCode) {
        Result<TraceVO> result = null;
        /**
         * 判断追溯码的第二、三位属于哪个环节
         * 药材：11，种植：04，饮片加工：23，饮片经营：31
         */
        switch (traceCode.substring(1, 3)) {
            case "11":
                result = traceBackService.medicinalTrace(traceCode);

                break;
            case "04":
                result = traceBackService.plantTraceLink01(traceCode);

                break;
            case "23":
                result = traceBackService.ypProcessTrace(traceCode);
                break;
            case "31":
                result = traceBackService.ypBusinessTrace(traceCode);
        }
        return result;
    }

}
