package org.jeecg.modules.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.ValidField;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.baseinfo.entity.WptpBase;
import org.jeecg.modules.demo.baseinfo.entity.WptpBaseFile;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseFileService;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseService;
import org.jeecg.modules.demo.blockinfo.entity.WptpBlockFile;
import org.jeecg.modules.demo.blockinfo.service.IWptpBlockFileService;
import org.jeecg.modules.demo.blockmedicinal.entity.WptpBlockMeidicinal;
import org.jeecg.modules.demo.blockmedicinal.service.IWptpBlockMeidicinalService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图片和报告
 * @author laowang
 */
@RestController
@RequestMapping(value = "/fileAndReport")
@Slf4j
public class FileAndReportController {

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
    @Autowired
    private IWptpBlockMeidicinalService iWptpBlockMeidicinalService;
    @Autowired
    private IWptpBlockFileService iWptpBlockFileService;
    /**
     * 上传报告和图片
     * @param jsonStr
     * @return
     */
    @PostMapping("/upload")
    public synchronized String upload(@NotBlank String jsonStr) throws Exception{
        try {
            log.info(jsonStr);
            String trim = jsonStr.trim();
            FileParam fileParam = new FileParam();
            BeanUtils.copyProperties(JSONObject.parseObject(trim, FileParam.class),fileParam);
            Result result = ValidField.checkField(fileParam);

            if (!result.isSuccess()) return JSONArray.toJSON(result).toString();
            ValidField.trimSpace(fileParam);//除去属性首尾空格
            for (FileArray file:
                    fileParam.getFile()) {
                ValidField.trimSpace(file);//除去属性首尾空格
            }
            String hostCode = fileParam.getHostCode();
            Result checkResult = hostCodeCheck.checkHostCode(hostCode);
            if (!checkResult.isSuccess()) return JSONArray.toJSON(checkResult).toString();
            /**
             * 根据流水号类别判断插入到哪些表
             * 所属环节：
             * 饮片加工药材入库：20；饮片加工：21；饮片成品包装：22；饮片销售：23
             * 00：基地信息；01：档案信息；02：作业信息；初加工信息；03：加工信息；04：销售信息；05：采收批次
             * 饮片经营药材入库：30；饮片经营销售：31；
             * 药材经营药材入库：10；药材经营销售：11；
             */
            String flag = fileParam.getFlag();
            /*String fileType="";//标记是图片还是视频*/
            List<FileArray> files=fileParam.getFile();//文件参数
            if (oConvertUtils.isEmpty(files)) return JSONArray.toJSON(new Result().error500("参数file不能为空")).toString();
            String sourceNum = fileParam.getMainId();//环节流水号
            System.out.println(files.size());
        /*if (fileParam.getFileType().equals("1")){
            fileType= FileType.VIDEO.getType();
        }else {
            fileType=FileType.IMAGE.getType();
        }*/
            Map<String, Object> map = new HashMap<>();//保存查询参数
            /*String type = fileParam.getType();*/
            switch (flag){
                /**
                 * 药材经营-药材入库
                 */
                case "10":
                    map.put("instock_number",sourceNum);
                    List<WptpMedicineInstock> wptpMedicineInstocks = iWptpMedicineInstockService.getBaseMapper().selectByMap(map);//根据流水号查询一条主表记录
                    if (wptpMedicineInstocks.isEmpty()){
                        return JSONArray.toJSON(new Result(false, "根据流水号找不到需要绑定的数据！", 500, new Date().getTime())).toString() ;
                    }
           /*     for (String path:
                        paths) {
                    WptpMedicineInstockFile wptpMedicineInstockFile = new WptpMedicineInstockFile(path,type,fileType);
                    wptpMedicineInstockFileService.save(wptpMedicineInstockFile);
                }*/
                    QueryWrapper<WptpMedicineInstockFile> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("main_id",sourceNum);//判断是否有该流水号的记录
                    queryWrapper.eq("deleted","0");
                    List<WptpMedicineInstockFile> wptpMedicineInstockFiles = wptpMedicineInstockFileService.getBaseMapper().selectList(queryWrapper);
                    if (!wptpMedicineInstockFiles.isEmpty()) {
                        for (WptpMedicineInstockFile wptpMedicineInstockFile:
                                wptpMedicineInstockFiles) {
                            wptpMedicineInstockFile.setDeleted("1");
                            wptpMedicineInstockFile.setUpdateBy(hostCode);
                            wptpMedicineInstockFile.setUpdateTime(new Date());
                            wptpMedicineInstockFileService.getBaseMapper().updateById(wptpMedicineInstockFile);
                        }
                    }
                    for (FileArray file:
                            files) {
                        WptpMedicineInstockFile wptpMedicineInstockFile = new WptpMedicineInstockFile(sourceNum,file.getPath(),file.getType(),file.getFileType(),new Date(),"0",hostCode);

                        wptpMedicineInstockFileService.save(wptpMedicineInstockFile);
                    }
                    break;
                /**
                 * 饮片经营药材入库
                 */
                case "30":
                    map.put("instock_no",sourceNum);
                    List<WptpYpbInstock> wptpYpbInstocks = wptpYpbInstockService.getBaseMapper().selectByMap(map);//根据流水号查询一条主表记录
                    if (wptpYpbInstocks.isEmpty()){
                        return JSONArray.toJSON(new Result(false, "根据流水号找不到需要绑定的数据！", 500, new Date().getTime())).toString() ;
                    }
               /* for (String path:
                        paths) {
                    WptpYpbInstockFile wptpYpbInstockFile = new WptpYpbInstockFile(path,type,fileType);
                    iWptpYpbInstockFileService.save(wptpYpbInstockFile);
                }*/
                    QueryWrapper<WptpYpbInstockFile> ypbInstockFileQueryWrapper = new QueryWrapper<>();
                    ypbInstockFileQueryWrapper.eq("main_id",sourceNum);//判断是否有该流水号的记录
                    ypbInstockFileQueryWrapper.eq("deleted","0");
                    List<WptpYpbInstockFile> wptpYpbInstockFiles = iWptpYpbInstockFileService.getBaseMapper().selectList(ypbInstockFileQueryWrapper);
                    if (!wptpYpbInstockFiles.isEmpty()) {
                        for (WptpYpbInstockFile wptpYpbInstockFile:
                                wptpYpbInstockFiles) {
                            wptpYpbInstockFile.setDeleted("1");
                            wptpYpbInstockFile.setUpdateBy(hostCode);
                            wptpYpbInstockFile.setUpdateTime(new Date());
                            iWptpYpbInstockFileService.getBaseMapper().updateById(wptpYpbInstockFile);
                        }
                    }
                    for (FileArray file:
                            files) {
                        WptpYpbInstockFile wptpYpbInstockFile = new WptpYpbInstockFile(sourceNum,file.getPath(),file.getType(),file.getFileType(),new Date(),"0",hostCode);
                        iWptpYpbInstockFileService.save(wptpYpbInstockFile);
                    }
                    break;
                /**
                 * 饮片入库
                 */
                case "20":
                    map.put("instock_number",sourceNum);
                    List<WptpYpInstock> wptpYpInstocks = wptpYpInstockService.getBaseMapper().selectByMap(map);//根据流水号查询一条主表记录
                    if (wptpYpInstocks.isEmpty()){
                        return JSONArray.toJSON(new Result(false, "根据流水号找不到需要绑定的数据！", 500, new Date().getTime())).toString() ;
                    }
               /* for (String path:
                        paths) {
                    WptpYpInstockFile wptpYpInstockFile = new WptpYpInstockFile(path,type,fileType);
                    iWptpYpInstockFileService.save(wptpYpInstockFile);
                }*/
                    QueryWrapper<WptpYpInstockFile> ypInstockFileQueryWrapper = new QueryWrapper<>();
                    ypInstockFileQueryWrapper.eq("main_id",sourceNum);//判断是否有该流水号的记录
                    ypInstockFileQueryWrapper.eq("deleted","0");
                    List<WptpYpInstockFile> wptpYpInstockFiles = iWptpYpInstockFileService.getBaseMapper().selectList(ypInstockFileQueryWrapper);
                    if (!wptpYpInstockFiles.isEmpty()) {
                        for (WptpYpInstockFile wptpYpInstockFile:
                                wptpYpInstockFiles) {
                            wptpYpInstockFile.setDeleted("1");
                            wptpYpInstockFile.setUpdateBy(hostCode);
                            wptpYpInstockFile.setUpdateTime(new Date());
                            iWptpYpInstockFileService.getBaseMapper().updateById(wptpYpInstockFile);
                        }
                    }
                    for (FileArray file:
                            files) {
                        WptpYpInstockFile wptpYpInstockFile = new WptpYpInstockFile(sourceNum,file.getPath(),file.getType(),file.getFileType(),new Date(),"0",hostCode);
                        iWptpYpInstockFileService.save(wptpYpInstockFile);
                    }

                    break;
                /**
                 * 饮片加工
                 */
                case "21":
                    map.put("process_no",sourceNum);
                    List<WptpYpProcess> wptpYpProcesses = wptpYpProcessService.getBaseMapper().selectByMap(map);//根据流水号查询一条主表记录
                    if (wptpYpProcesses.isEmpty()){
                        return JSONArray.toJSON(new Result(false, "根据流水号找不到需要绑定的数据！", 500, new Date().getTime())).toString() ;
                    }
                /*for (String path:
                        paths) {
                    WptpYpProcessFile wptpYpProcessFile = new WptpYpProcessFile(path,type,fileType);
                    iWptpYpProcessFileService.save(wptpYpProcessFile);
                }*/
                    QueryWrapper<WptpYpProcessFile> ypProcessFileQueryWrapper = new QueryWrapper<>();
                    ypProcessFileQueryWrapper.eq("main_id",sourceNum);//判断是否有该流水号的记录
                    ypProcessFileQueryWrapper.eq("deleted","0");
                    List<WptpYpProcessFile> wptpYpProcessFiles = iWptpYpProcessFileService.getBaseMapper().selectList(ypProcessFileQueryWrapper);
                    if (!wptpYpProcessFiles.isEmpty()) {
                        for (WptpYpProcessFile wptpYpProcessFile:
                                wptpYpProcessFiles) {
                            wptpYpProcessFile.setDeleted("1");
                            wptpYpProcessFile.setUpdateBy(hostCode);
                            wptpYpProcessFile.setUpdateDate(new Date());
                            iWptpYpProcessFileService.getBaseMapper().updateById(wptpYpProcessFile);
                        }
                    }
                    for (FileArray file:
                            files) {
                        WptpYpProcessFile wptpYpProcessFile = new WptpYpProcessFile(sourceNum,file.getPath(),file.getType(),file.getFileType(),new Date(),"0",hostCode);
                        iWptpYpProcessFileService.save(wptpYpProcessFile);
                    }
                    break;
                /**
                 * 饮片销售
                 */
                case "23":
                    map.put("sale_number",sourceNum);
                    List<WptpYpSale> wptpYpSales = wptpYpSaleService.getBaseMapper().selectByMap(map);//根据流水号查询一条主表记录
                    if (wptpYpSales.isEmpty()){
                        return JSONArray.toJSON(new Result(false, "根据流水号找不到需要绑定的数据！", 500, new Date().getTime())).toString() ;
                    }
              /*  for (String path:
                        paths) {
                    WptpYpSaleFile wptpYpSaleFile = new WptpYpSaleFile(path,type,fileType);
                    iWptpYpSaleFileService.save(wptpYpSaleFile);
                }*/
                    QueryWrapper<WptpYpSaleFile> ypSaleFileQueryWrapper = new QueryWrapper<>();
                    ypSaleFileQueryWrapper.eq("main_id",sourceNum);//判断是否有该流水号的记录
                    ypSaleFileQueryWrapper.eq("deleted","0");
                    List<WptpYpSaleFile> wptpYpSaleFiles = iWptpYpSaleFileService.getBaseMapper().selectList(ypSaleFileQueryWrapper);
                    if (!wptpYpSaleFiles.isEmpty()) {
                        for (WptpYpSaleFile wptpYpSaleFile:
                                wptpYpSaleFiles) {
                            wptpYpSaleFile.setDeleted("1");
                            wptpYpSaleFile.setUpdateBy(hostCode);
                            wptpYpSaleFile.setUpdateDate(new Date());
                            iWptpYpSaleFileService.getBaseMapper().updateById(wptpYpSaleFile);
                        }
                    }
                    for (FileArray file:
                            files) {
                        WptpYpSaleFile wptpYpSaleFile = new WptpYpSaleFile(sourceNum,file.getPath(),file.getType(),file.getFileType(),new Date(),"0",hostCode);
                        iWptpYpSaleFileService.save(wptpYpSaleFile);
                    }
                    break;
                /**
                 * 种植-基地信息
                 */
                case "00":
                    map.put("base_code",sourceNum);
                    List<WptpBase> wptpBases = iWptpBaseService.getBaseMapper().selectByMap(map);//根据基地编号查询一条主表记录
                    if (wptpBases.isEmpty()){
                        return JSONArray.toJSON(new Result(false, "根据基地编号找不到需要绑定的数据！", 500, new Date().getTime())).toString() ;
                    }
               /* for (String path:
                        paths) {
                    WptpBaseFile wptpBaseFile = new WptpBaseFile(path,type,fileType);
                    iWptpBaseFileService.save(wptpBaseFile);
                }*/
                    QueryWrapper<WptpBaseFile> baseFileQueryWrapper = new QueryWrapper<>();
                    baseFileQueryWrapper.eq("main_id",sourceNum);//判断是否有该流水号的记录
                    baseFileQueryWrapper.eq("deleted","0");
                    List<WptpBaseFile> wptpBaseFiles = iWptpBaseFileService.getBaseMapper().selectList(baseFileQueryWrapper);
                    if (!wptpBaseFiles.isEmpty()) {
                        for (WptpBaseFile wptpBaseFile:
                                wptpBaseFiles) {
                            wptpBaseFile.setDeleted("1");
                            wptpBaseFile.setUpdateBy(hostCode);
                            wptpBaseFile.setUpdateTime(new Date());
                            iWptpBaseFileService.getBaseMapper().updateById(wptpBaseFile);
                        }
                    }
                    for (FileArray file:
                            files) {
                        WptpBaseFile wptpBaseFile = new WptpBaseFile(sourceNum,file.getPath(),file.getType(),file.getFileType(),new Date(),"0",hostCode);
                        iWptpBaseFileService.save(wptpBaseFile);
                    }
                    break;
                /**
                 * 种植-档案信息
                 */
                case "01":
                    map.put("block_medicinal_id",sourceNum);
                    List<WptpBlockMeidicinal> wptpBlockMeidicinals = iWptpBlockMeidicinalService.getBaseMapper().selectByMap(map);//根据档案编号查询一条主表记录
                    if (wptpBlockMeidicinals.isEmpty()){
                        return JSONArray.toJSON(new Result(false, "根据档案编号找不到需要绑定的数据！", 500, new Date().getTime())).toString() ;
                    }

                    QueryWrapper<WptpBlockFile> wptpBlockFileQueryWrapper = new QueryWrapper<>();
                    wptpBlockFileQueryWrapper.eq("main_id",sourceNum);//判断是否有该流水号的记录
                    wptpBlockFileQueryWrapper.eq("deleted","0");
                    List<WptpBlockFile> wptpBlockFiles = iWptpBlockFileService.getBaseMapper().selectList(wptpBlockFileQueryWrapper);
                    if (!wptpBlockFiles.isEmpty()) {
                        for (WptpBlockFile wptpBlockFile:
                                wptpBlockFiles) {
                            wptpBlockFile.setDeleted("1");
                            wptpBlockFile.setUpdateBy(hostCode);
                            wptpBlockFile.setUpdateTime(new Date());
                            iWptpBlockFileService.getBaseMapper().updateById(wptpBlockFile);
                        }
                    }
                    for (FileArray file:
                            files) {
                        WptpBlockFile wptpBlockFile = new WptpBlockFile(sourceNum,file.getPath(),file.getType(),file.getFileType(),new Date(),hostCode,"0");
                        iWptpBlockFileService.save(wptpBlockFile);
                    }
                    break;
                /**
                 * 种植-作业信息
                 */
                case "02":
                    map.put("plant_id",sourceNum);
                    List<WptpPlantInfo> wptpPlantInfos = iWptpPlantInfoService.getBaseMapper().selectByMap(map);//根据作业编号查询一条主表记录
                    if (wptpPlantInfos.isEmpty()){
                        return JSONArray.toJSON(new Result(false, "根据作业编号找不到需要绑定的数据！", 500, new Date().getTime())).toString() ;
                    }
               /* for (String path:
                        paths) {
                    WptpPlantFile wptpPlantFile = new WptpPlantFile(path,type,fileType);
                    iWptpPlantFileService.save(wptpPlantFile);
                }*/
                    QueryWrapper<WptpPlantFile> plantFileQueryWrapper = new QueryWrapper<>();
                    plantFileQueryWrapper.eq("main_id",sourceNum);//判断是否有该作业编号的记录
                    plantFileQueryWrapper.eq("deleted","0");
                    List<WptpPlantFile> wptpPlantFiles = iWptpPlantFileService.getBaseMapper().selectList(plantFileQueryWrapper);
                    if (!wptpPlantFiles.isEmpty()) {
                        for (WptpPlantFile wptpPlantFile:
                                wptpPlantFiles) {
                            wptpPlantFile.setDeleted("1");
                            wptpPlantFile.setUpdateBy(hostCode);
                            wptpPlantFile.setUpdateTime(new Date());
                            iWptpPlantFileService.getBaseMapper().updateById(wptpPlantFile);
                        }
                    }
                    for (FileArray file:
                            files) {
                        WptpPlantFile wptpPlantFile = new WptpPlantFile(sourceNum,file.getPath(),file.getType(),file.getFileType(),new Date(),"0",hostCode);
                        iWptpPlantFileService.save(wptpPlantFile);
                    }
                    break;
                /**
                 * 种植-初加工信息
                 */
                case "03":
                    map.put("process_no",sourceNum);
                    List<WptpProcessInfo> wptpProcessInfos = iWptpProcessInfoService.getBaseMapper().selectByMap(map);//根据加工单号查询一条主表记录
                    if (wptpProcessInfos.isEmpty()){
                        return JSONArray.toJSON(new Result(false, "根据加工单号找不到需要绑定的数据！", 500, new Date().getTime())).toString() ;
                    }
                /*for (String path:
                        paths) {
                    WptpProcessFile wptpProcessFile = new WptpProcessFile(path,type,fileType);
                    iWptpProcessFileService.save(wptpProcessFile);
                }*/
                    QueryWrapper<WptpProcessFile> processFileQueryWrapper = new QueryWrapper<>();
                    processFileQueryWrapper.eq("main_id",sourceNum);//判断是否有该流水号的记录
                    processFileQueryWrapper.eq("deleted","0");
                    List<WptpProcessFile> wptpProcessFiles = iWptpProcessFileService.getBaseMapper().selectList(processFileQueryWrapper);
                    if (!wptpProcessFiles.isEmpty()) {
                        for (WptpProcessFile wptpProcessFile:
                                wptpProcessFiles) {
                            wptpProcessFile.setDeleted("1");
                            wptpProcessFile.setUpdateBy(hostCode);
                            wptpProcessFile.setUpdateTime(new Date());
                            iWptpProcessFileService.getBaseMapper().updateById(wptpProcessFile);
                        }
                    }
                    for (FileArray file:
                            files) {
                        WptpProcessFile wptpProcessFile = new WptpProcessFile(sourceNum,file.getPath(),file.getType(),file.getFileType(),new Date(),"0",hostCode);
                        iWptpProcessFileService.save(wptpProcessFile);
                    }
            }
        }catch (Exception e){
            e.printStackTrace();
            return JSONArray.toJSON(new Result().error500(e.getMessage()+e.getCause())).toString();
        }
        return JSONArray.toJSON(new Result(true, "操作成功", 200, new Date().getTime())).toString();
    }


}
