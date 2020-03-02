package org.jeecg.modules.api.webservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.ValidField;
import org.jeecg.modules.api.HostCodeCheck;
import org.jeecg.modules.api.TovoidParam;
import org.jeecg.modules.demo.baseinfo.entity.WptpBase;
import org.jeecg.modules.demo.baseinfo.entity.WptpBaseFile;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseFileService;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseService;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstock;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstockFile;
import org.jeecg.modules.demo.medicinebinstock.service.IWptpMedicineInstockFileService;
import org.jeecg.modules.demo.medicinebinstock.service.IWptpMedicineInstockService;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantFile;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantInfo;
import org.jeecg.modules.demo.plantinfo.service.IWptpPlantFileService;
import org.jeecg.modules.demo.plantinfo.service.IWptpPlantInfoService;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessInfo;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessFileService;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessInfoService;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstock;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstockFile;
import org.jeecg.modules.demo.ypbinstock.service.IWptpYpbInstockFileService;
import org.jeecg.modules.demo.ypbinstock.service.IWptpYpbInstockService;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcess;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcessFile;
import org.jeecg.modules.demo.ypprocess.service.IWptpYpProcessFileService;
import org.jeecg.modules.demo.ypprocess.service.IWptpYpProcessService;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstock;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstockFile;
import org.jeecg.modules.demo.ypprocessinstock.service.IWptpYpInstockFileService;
import org.jeecg.modules.demo.ypprocessinstock.service.IWptpYpInstockService;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSale;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSaleFile;
import org.jeecg.modules.demo.ypprocesssale.service.IWptpYpSaleFileService;
import org.jeecg.modules.demo.ypprocesssale.service.IWptpYpSaleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@WebService(name = "toVoidService", targetNamespace = "http://webservice.api.modules.jeecg.org/", endpointInterface = "org.jeecg.modules.api.webservice.ToVoidService")
public class ToVoidServiceImpl implements ToVoidService {

    @Autowired
    private HostCodeCheck hostCodeCheck;
    @Autowired
    private IWptpYpSaleService wptpYpSaleService;
    @Autowired
    private IWptpYpInstockService wptpYpInstockService;
    @Autowired
    private IWptpYpProcessService wptpYpProcessService;
    @Autowired
    private IWptpYpSaleFileService iWptpYpSaleFileService;
    @Autowired
    private IWptpYpProcessFileService iWptpYpProcessFileService;
    @Autowired
    private IWptpYpInstockFileService iWptpYpInstockFileService;
    @Autowired
    private IWptpBaseService iWptpBaseService;
    @Autowired
    private IWptpPlantInfoService iWptpPlantInfoService;
    @Autowired
    private IWptpProcessInfoService iWptpProcessInfoService;
    @Autowired
    private IWptpBaseFileService iWptpBaseFileService;
    @Autowired
    private IWptpPlantFileService iWptpPlantFileService;
    @Autowired
    private IWptpProcessFileService iWptpProcessFileService;
    @Autowired
    private IWptpYpbInstockService wptpYpbInstockService;
    @Autowired
    private IWptpYpbInstockFileService iWptpYpbInstockFileService;
    @Autowired
    private IWptpMedicineInstockService iWptpMedicineInstockService;
    @Autowired
    private IWptpMedicineInstockFileService wptpMedicineInstockFileService;

    @Override
    public synchronized String toviod(@NotBlank String jsonStr) {
        String trim = jsonStr.trim();
        TovoidParam tovoidParam = new TovoidParam();
        BeanUtils.copyProperties(JSONObject.parseObject(trim, TovoidParam.class), tovoidParam);
        Result result = ValidField.checkField(tovoidParam);
        if (!result.isSuccess()) return JSONArray.toJSON(result).toString();
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(tovoidParam);
        String hostCode = tovoidParam.getHostCode();

        Result checkResult = hostCodeCheck.checkHostCode(hostCode);
        if (!checkResult.isSuccess()) return JSONArray.toJSON(checkResult).toString();
        /**
         * 根据流水号类别判断操作哪些表
         * 所属环节（source）：
         * 饮片加工药材入库：20；饮片加工：21；饮片成品包装：22；饮片销售：23
         * 基地信息 : 00；档案信息：01；作业信息：02；加工信息 : 03；销售信息: 04
         * 饮片经营药材入库：30；饮片经营销售：31；
         * 药材经营药材入库：10；药材经营销售：11；
         */
        String source = tovoidParam.getSource();
        String sourceNum = tovoidParam.getSourceNum();//环节流水号
        String flag = tovoidParam.getFlag();

        Map<String, Object> map = new HashMap<>();//保存查询参数
        switch (source) {
            /**
             * 药材经营-药材入库
             */
            case "10":
                map.put("instockNumber", sourceNum);
                List<WptpMedicineInstock> wptpMedicineInstocks = iWptpMedicineInstockService.getBaseMapper().selectByMap(map);//根据流水号查询一条主表记录
                if (wptpMedicineInstocks.isEmpty()) {
                    return JSONArray.toJSON(new Result(false, "根据流水号找不到需要绑定的数据！", 500, new Date().getTime())).toString();
                }
                WptpMedicineInstock wptpMedicineInstock = wptpMedicineInstocks.get(0);
                wptpMedicineInstock.setDeleted(flag);
                wptpMedicineInstock.setUpdateTime(new Date());
                wptpMedicineInstock.setUpdateBy(hostCode);
                iWptpMedicineInstockService.updateById(wptpMedicineInstock);
                QueryWrapper<WptpMedicineInstockFile> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("main_id", sourceNum);//判断是否有该流水号的记录
                queryWrapper.eq("deleted", "0");
                List<WptpMedicineInstockFile> wptpMedicineInstockFiles = wptpMedicineInstockFileService.getBaseMapper().selectList(queryWrapper);
                if (!wptpMedicineInstockFiles.isEmpty()) {
                    for (WptpMedicineInstockFile wptpMedicineInstockFile :
                            wptpMedicineInstockFiles) {
                        wptpMedicineInstockFile.setDeleted(flag);
                        wptpMedicineInstockFile.setUpdateTime(new Date());
                        wptpMedicineInstockFile.setUpdateBy(hostCode);
                        wptpMedicineInstockFileService.getBaseMapper().updateById(wptpMedicineInstockFile);
                    }
                }
                break;
            /**
             * 饮片经营药材入库
             */
            case "30":
                map.put("instockNo", sourceNum);
                List<WptpYpbInstock> wptpYpbInstocks = wptpYpbInstockService.getBaseMapper().selectByMap(map);//根据流水号查询一条主表记录
                if (wptpYpbInstocks.isEmpty()) {
                    return JSONArray.toJSON(new Result(false, "根据流水号找不到需要绑定的数据！", 500, new Date().getTime())).toString();
                }
                WptpYpbInstock wptpYpbInstock = wptpYpbInstocks.get(0);
                wptpYpbInstock.setDeleted(flag);
                wptpYpbInstock.setUpdateDate(new Date());
                wptpYpbInstock.setUpdateBy(hostCode);
                wptpYpbInstockService.updateById(wptpYpbInstock);
                QueryWrapper<WptpYpbInstockFile> ypbInstockFileQueryWrapper = new QueryWrapper<>();
                ypbInstockFileQueryWrapper.eq("main_id", sourceNum);//判断是否有该流水号的记录
                ypbInstockFileQueryWrapper.eq("deleted", "0");
                List<WptpYpbInstockFile> wptpYpbInstockFiles = iWptpYpbInstockFileService.getBaseMapper().selectList(ypbInstockFileQueryWrapper);
                if (!wptpYpbInstockFiles.isEmpty()) {
                    for (WptpYpbInstockFile wptpYpbInstockFile :
                            wptpYpbInstockFiles) {
                        wptpYpbInstockFile.setDeleted(flag);

                        wptpYpbInstockFile.setUpdateTime(new Date());
                        wptpYpbInstockFile.setUpdateBy(hostCode);
                        iWptpYpbInstockFileService.getBaseMapper().updateById(wptpYpbInstockFile);
                    }
                }

                break;
            /**
             * 饮片入库
             */
            case "20":
                map.put("instock_number", sourceNum);
                List<WptpYpInstock> wptpYpInstocks = wptpYpInstockService.getBaseMapper().selectByMap(map);//根据流水号查询一条主表记录
                if (wptpYpInstocks.isEmpty()) {
                    return JSONArray.toJSON(new Result(false, "根据流水号找不到需要绑定的数据！", 500, new Date().getTime())).toString();
                }
                WptpYpInstock wptpYpInstock = wptpYpInstocks.get(0);
                wptpYpInstock.setDeleted(flag);
                wptpYpInstock.setUpdateDate(new Date());
                wptpYpInstock.setUpdateBy(hostCode);
                wptpYpInstockService.updateById(wptpYpInstock);
                QueryWrapper<WptpYpInstockFile> ypInstockFileQueryWrapper = new QueryWrapper<>();
                ypInstockFileQueryWrapper.eq("main_id", sourceNum);//判断是否有该流水号的记录
                ypInstockFileQueryWrapper.eq("deleted", "0");
                List<WptpYpInstockFile> wptpYpInstockFiles = iWptpYpInstockFileService.getBaseMapper().selectList(ypInstockFileQueryWrapper);
                if (!wptpYpInstockFiles.isEmpty()) {
                    for (WptpYpInstockFile wptpYpInstockFile :
                            wptpYpInstockFiles) {
                        wptpYpInstockFile.setDeleted(flag);
                        wptpYpInstockFile.setUpdateTime(new Date());
                        wptpYpInstockFile.setUpdateBy(hostCode);
                        iWptpYpInstockFileService.getBaseMapper().updateById(wptpYpInstockFile);
                    }
                }

                break;
            /**
             * 饮片加工
             */
            case "21":
                map.put("process_no", sourceNum);
                List<WptpYpProcess> wptpYpProcesses = wptpYpProcessService.getBaseMapper().selectByMap(map);//根据流水号查询一条主表记录
                if (wptpYpProcesses.isEmpty()) {
                    return JSONArray.toJSON(new Result(false, "根据流水号找不到需要绑定的数据！", 500, new Date().getTime())).toString();
                }
                WptpYpProcess wptpYpProcess = wptpYpProcesses.get(0);
                wptpYpProcess.setDeleted(flag);
                wptpYpProcess.setUpdateDate(new Date());
                wptpYpProcess.setUpdateBy(hostCode);
                wptpYpProcessService.updateById(wptpYpProcess);
                QueryWrapper<WptpYpProcessFile> ypProcessFileQueryWrapper = new QueryWrapper<>();
                ypProcessFileQueryWrapper.eq("main_id", sourceNum);//判断是否有该流水号的记录
                ypProcessFileQueryWrapper.eq("deleted", "0");
                List<WptpYpProcessFile> wptpYpProcessFiles = iWptpYpProcessFileService.getBaseMapper().selectList(ypProcessFileQueryWrapper);
                if (!wptpYpProcessFiles.isEmpty()) {
                    for (WptpYpProcessFile wptpYpProcessFile :
                            wptpYpProcessFiles) {
                        wptpYpProcessFile.setDeleted(flag);
                        wptpYpProcessFile.setUpdateDate(new Date());
                        wptpYpProcessFile.setUpdateBy(hostCode);
                        iWptpYpProcessFileService.getBaseMapper().updateById(wptpYpProcessFile);
                    }
                }

                break;
            /**
             * 饮片销售
             */
            case "23":
                map.put("sale_number", sourceNum);
                List<WptpYpSale> wptpYpSales = wptpYpSaleService.getBaseMapper().selectByMap(map);//根据流水号查询一条主表记录
                if (wptpYpSales.isEmpty()) {
                    return JSONArray.toJSON(new Result(false, "根据流水号找不到需要绑定的数据！", 500, new Date().getTime())).toString();
                }
                WptpYpSale wptpYpSale = wptpYpSales.get(0);
                wptpYpSale.setDeleted(flag);
                wptpYpSale.setUpdateDate(new Date());
                wptpYpSale.setUpdateBy(hostCode);
                wptpYpSaleService.updateById(wptpYpSale);
                QueryWrapper<WptpYpSaleFile> ypSaleFileQueryWrapper = new QueryWrapper<>();
                ypSaleFileQueryWrapper.eq("main_id", sourceNum);//判断是否有该流水号的记录
                ypSaleFileQueryWrapper.eq("deleted", "0");
                List<WptpYpSaleFile> wptpYpSaleFiles = iWptpYpSaleFileService.getBaseMapper().selectList(ypSaleFileQueryWrapper);
                if (!wptpYpSaleFiles.isEmpty()) {
                    for (WptpYpSaleFile wptpYpSaleFile :
                            wptpYpSaleFiles) {
                        wptpYpSaleFile.setDeleted(flag);
                        wptpYpSaleFile.setUpdateDate(new Date());
                        wptpYpSaleFile.setUpdateBy(hostCode);
                        iWptpYpSaleFileService.getBaseMapper().updateById(wptpYpSaleFile);
                    }
                }

                break;
            /**
             * 种植-基地信息
             */
            case "00":
                map.put("base_code", sourceNum);
                List<WptpBase> wptpBases = iWptpBaseService.getBaseMapper().selectByMap(map);//根据基地编号查询一条主表记录
                if (wptpBases.isEmpty()) {
                    return JSONArray.toJSON(new Result(false, "根据基地编号找不到需要绑定的数据！", 500, new Date().getTime())).toString();
                }
                WptpBase wptpBase = wptpBases.get(0);
                wptpBase.setDeleted(flag);
                wptpBase.setUpdateTime(new Date());
                wptpBase.setUpdateBy(hostCode);
                iWptpBaseService.updateById(wptpBase);
                QueryWrapper<WptpBaseFile> baseFileQueryWrapper = new QueryWrapper<>();
                baseFileQueryWrapper.eq("main_id", sourceNum);//判断是否有该流水号的记录
                baseFileQueryWrapper.eq("deleted", "0");
                List<WptpBaseFile> wptpBaseFiles = iWptpBaseFileService.getBaseMapper().selectList(baseFileQueryWrapper);
                if (!wptpBaseFiles.isEmpty()) {
                    for (WptpBaseFile wptpBaseFile :
                            wptpBaseFiles) {
                        wptpBaseFile.setDeleted(flag);
                        wptpBaseFile.setUpdateTime(new Date());
                        wptpBaseFile.setUpdateBy(hostCode);
                        iWptpBaseFileService.getBaseMapper().updateById(wptpBaseFile);
                    }
                }

                break;
            /**
             * 种植-档案信息
             */
            case "01":
                map.put("plant_id", sourceNum);
                List<WptpPlantInfo> wptpPlantInfos = iWptpPlantInfoService.getBaseMapper().selectByMap(map);//根据档案编号查询一条主表记录
                if (wptpPlantInfos.isEmpty()) {
                    return JSONArray.toJSON(new Result(false, "根据档案编号找不到需要绑定的数据！", 500, new Date().getTime())).toString();
                }
                WptpPlantInfo wptpPlantInfo = wptpPlantInfos.get(0);
                wptpPlantInfo.setDeleted(flag);
                wptpPlantInfo.setUpdateTime(new Date());
                wptpPlantInfo.setUpdateBy(hostCode);
                iWptpPlantInfoService.updateById(wptpPlantInfo);
                QueryWrapper<WptpPlantFile> plantFileQueryWrapper = new QueryWrapper<>();
                plantFileQueryWrapper.eq("main_id", sourceNum);//判断是否有该流水号的记录
                plantFileQueryWrapper.eq("deleted", "0");
                List<WptpPlantFile> wptpPlantFiles = iWptpPlantFileService.getBaseMapper().selectList(plantFileQueryWrapper);
                if (!wptpPlantFiles.isEmpty()) {
                    for (WptpPlantFile wptpPlantFile :
                            wptpPlantFiles) {
                        wptpPlantFile.setDeleted(flag);
                        wptpPlantFile.setUpdateTime(new Date());
                        wptpPlantFile.setUpdateBy(hostCode);
                        iWptpPlantFileService.getBaseMapper().updateById(wptpPlantFile);
                    }
                }

                break;
            /**
             * 种植-作业信息
             */
           /* case "02":
                map.put("plant_id", sourceNum);
                List<WptpPlantInfo> wptpPlantInfos = iWptpPlantInfoService.getBaseMapper().selectByMap(map);//根据作业编号查询一条主表记录
                if (wptpPlantInfos.isEmpty()) {
                    return JSONArray.toJSON(new Result(false, "根据作业编号找不到需要绑定的数据！", 500, new Date().getTime())).toString();
                }
                WptpPlantInfo wptpPlantInfo = wptpPlantInfos.get(0);
                wptpPlantInfo.setDeleted(flag);
                wptpPlantInfo.setUpdateTime(new Date());
                wptpPlantInfo.setUpdateBy(hostCode);
                iWptpPlantInfoService.updateById(wptpPlantInfo);
                QueryWrapper<WptpPlantFile> plantFileQueryWrapper = new QueryWrapper<>();
                plantFileQueryWrapper.eq("main_id", sourceNum);//判断是否有该流水号的记录
                plantFileQueryWrapper.eq("deleted", "0");
                List<WptpPlantFile> wptpPlantFiles = iWptpPlantFileService.getBaseMapper().selectList(plantFileQueryWrapper);
                if (!wptpPlantFiles.isEmpty()) {
                    for (WptpPlantFile wptpPlantFile :
                            wptpPlantFiles) {
                        wptpPlantFile.setDeleted(flag);
                        wptpPlantFile.setUpdateTime(new Date());
                        wptpPlantFile.setUpdateBy(hostCode);
                        iWptpPlantFileService.getBaseMapper().updateById(wptpPlantFile);
                    }
                }

                break;*/
            /**
             * 种植-初加工信息
             */
            case "03":
                map.put("process_no", sourceNum);
                List<WptpProcessInfo> wptpProcessInfos = iWptpProcessInfoService.getBaseMapper().selectByMap(map);//根据加工单号查询一条主表记录
                if (wptpProcessInfos.isEmpty()) {
                    return JSONArray.toJSON(new Result(false, "根据加工单号找不到需要绑定的数据！", 500, new Date().getTime())).toString();
                }
                WptpProcessInfo wptpProcessInfo = wptpProcessInfos.get(0);
                wptpProcessInfo.setDeleted(flag);
                wptpProcessInfo.setUpdateTime(new Date());
                wptpProcessInfo.setUpdateBy(hostCode);
                iWptpProcessInfoService.updateById(wptpProcessInfo);
                QueryWrapper<WptpProcessFile> processFileQueryWrapper = new QueryWrapper<>();
                processFileQueryWrapper.eq("main_id", sourceNum);//判断是否有该流水号的记录
                processFileQueryWrapper.eq("deleted", "0");
                List<WptpProcessFile> wptpProcessFiles = iWptpProcessFileService.getBaseMapper().selectList(processFileQueryWrapper);
                if (!wptpProcessFiles.isEmpty()) {
                    for (WptpProcessFile wptpProcessFile :
                            wptpProcessFiles) {
                        wptpProcessFile.setDeleted(flag);
                        wptpProcessFile.setUpdateTime(new Date());
                        wptpProcessFile.setUpdateBy(hostCode);
                        iWptpProcessFileService.getBaseMapper().updateById(wptpProcessFile);
                    }
                }

        }
        return JSONArray.toJSON(new Result(true, "操作成功", 200, new Date().getTime())).toString();
    }
}
