package org.jeecg.modules.demo.trace.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.api.webservice.guild.upload.GuildUpload;
import org.jeecg.modules.demo.csinfo.vo.WptpCsInfoVO;
import org.jeecg.modules.demo.index.vo.MedicinalVO;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicineFile;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicineFileService;
import org.jeecg.modules.demo.medicinebsale.vo.WptpMedicineSaleVO;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessMaterial;
import org.jeecg.modules.demo.sale.vo.WptpSaleVO;
import org.jeecg.modules.demo.scanner.entity.WptpScannerTime;
import org.jeecg.modules.demo.scanner.service.IWptpScannerTimeService;
import org.jeecg.modules.demo.trace.service.TraceBackService;
import org.jeecg.modules.demo.trace.vo.*;
import org.jeecg.modules.demo.uploadxh.service.IWptpUploadRecordService;
import org.jeecg.modules.demo.uploadxh.entity.WptpUploadRecord;
import org.jeecg.modules.demo.ypbsale.vo.WptpYpbSaleVO;
import org.jeecg.modules.demo.ypprocesssale.vo.WptpYpSaleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 追溯
 * @author laowang
 */
@Api(tags="追溯")
@RestController
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RequestMapping("/trace")
public class TraceBackController {
    @Autowired
    private TraceBackService traceBackService;
    @Autowired
    private IWptpMedicinalService wptpMedicinalService;
    @Autowired
    private IWptpMedicineFileService wptpMedicineFileService;
    @Autowired
    private IWptpScannerTimeService scannerTimeService;
    @Autowired
    private GuildUpload guildUpload;
    @Autowired
    private IWptpUploadRecordService xhUploadRecordService;

    @GetMapping(value = "/traceByCode")
    @ApiOperation(value = "追溯结果", notes = "追溯结果")
    public Result<TraceVO> traceByCode(@RequestParam(name="traceCode",required=true)  @NotNull String traceCode,String scannerFlag) {
        if (traceCode.contains("-")){
            String[] split = traceCode.split("-");
            traceCode=split[1];
        }
        Result<TraceVO> result=null;
        /**
         * 判断是否扫描二维码,是则统计次数加1
         */
        if (!oConvertUtils.isEmpty(scannerFlag))scannerTimeService.getBaseMapper().insert(new WptpScannerTime(traceCode,new Date()));
        /**
         * 判断追溯码的第二、三位属于哪个环节
         * 药材：11，种植：04，饮片加工：23，饮片经营：31
         */
        switch (traceCode.substring(1,3)){
            case "11":
                result=traceBackService.medicinalTrace(traceCode);
                if (!oConvertUtils.isEmpty( result.getResult())){
                    MedicineTraceVO medicineVO = result.getResult().getMedicineVO();
                    if (!oConvertUtils.isEmpty( medicineVO)){
                        WptpMedicineSaleVO wptpMedicineSaleVO = medicineVO.getWptpMedicineSaleVO();
                        if (!oConvertUtils.isEmpty(wptpMedicineSaleVO)){
                            String medicinalCode = wptpMedicineSaleVO.getCategoryCode();
                            MedicinalVO medicinalVO = listMedicineImageByCode(medicinalCode);
                            TraceVO traceVO = result.getResult();
                            traceVO.setMedicinalImageVO(medicinalVO);
                            result.setResult(traceVO);
                        }
                    }
                }
                break;
            case "04":
                result = traceBackService.plantTraceLink01(traceCode);
                if (!oConvertUtils.isEmpty( result.getResult())){
                    PlantTraceVO plantTraceVO = result.getResult().getPlantTraceVO();
                    if (!oConvertUtils.isEmpty( plantTraceVO)){
                        WptpSaleVO wptpSale = plantTraceVO.getWptpSale();
                        if (!oConvertUtils.isEmpty(wptpSale)){
                            String medicinalCode = wptpSale.getMedicinalCode();
                            MedicinalVO medicinalVO = listMedicineImageByCode(medicinalCode);
                            TraceVO traceVO = result.getResult();
                            traceVO.setMedicinalImageVO(medicinalVO);
                            result.setResult(traceVO);
                        }
                    }
                }
                break;
            case "23":
                result=traceBackService.ypProcessTrace(traceCode);
                if (!oConvertUtils.isEmpty( result.getResult())){
                    YpProcessTraceVO ypProcessVO = result.getResult().getYpProcessVO();
                    if (!oConvertUtils.isEmpty( ypProcessVO)){
                        WptpYpSaleVO wptpYpSaleVO = ypProcessVO.getWptpYpSaleVO();
                        if (!oConvertUtils.isEmpty(wptpYpSaleVO)){
                            String medicinalCode = wptpYpSaleVO.getMedicinalCode();
                            MedicinalVO medicinalVO = listMedicineImageByCode(medicinalCode);
                            TraceVO traceVO = result.getResult();
                            traceVO.setMedicinalImageVO(medicinalVO);
                            result.setResult(traceVO);
                        }
                    }
                }

                break;
            case "31":
                result=traceBackService.ypBusinessTrace(traceCode);
                if (!oConvertUtils.isEmpty( result.getResult())){
                    List<YpBusinessTraceVO> ypBusinessTraceVOList = result.getResult().getYpBusinessTraceVOList();
                    if (!oConvertUtils.isEmpty( ypBusinessTraceVOList)){
                        YpBusinessTraceVO ypBusinessTraceVO = ypBusinessTraceVOList.get(0);
                        if (!oConvertUtils.isEmpty(ypBusinessTraceVO)){
                            WptpYpbSaleVO wptpYpbSaleVO = ypBusinessTraceVO.getWptpYpbSaleVO();
                            if (!oConvertUtils.isEmpty(wptpYpbSaleVO)){
                                String medicinalCode = wptpYpbSaleVO.getCategoryCode();
                                MedicinalVO medicinalVO = listMedicineImageByCode(medicinalCode);
                                TraceVO traceVO = result.getResult();
                                traceVO.setMedicinalImageVO(medicinalVO);
                                result.setResult(traceVO);
                            }
                        }
                    }
                }

        }
        return result;
    }

    /**
     * 追溯种植环节（档案-基地-作业）
     * @param traceCode
     * @return
     */
    @GetMapping(value = "/tracePlantLink")
    @ApiOperation(value = "追溯种植环节（档案-基地-作业）", notes = "追溯种植环节（档案-基地-作业）")
    public Result<TraceVO> tracePlantLink(@RequestParam(name="blockMedicinalId",required=true) @NotNull String blockMedicinalId) {
        return traceBackService.plantTraceLink02(blockMedicinalId);
    }

    private MedicinalVO listMedicineImageByCode(String medicinalCode){
        QueryWrapper<WptpMedicinal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("medicinal_code",medicinalCode);
        queryWrapper.eq("deleted","0");
        queryWrapper.eq("audit_status","2");
        WptpMedicinal wptpMedicinal = wptpMedicinalService.getBaseMapper().selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpMedicinal))return null;
        MedicinalVO medicinalVO = new MedicinalVO();
        BeanUtils.copyProperties(wptpMedicinal,medicinalVO);
        HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("main_id",wptpMedicinal.getMedicinalCode());
        List<WptpMedicineFile> wptpMedicineFiles = wptpMedicineFileService.getBaseMapper().selectByMap(queryMap);
        if (!wptpMedicineFiles.isEmpty()) {
            medicinalVO.setMedicinalImages(wptpMedicineFiles.get(0).getPath());
        }
        queryMap.clear();
        return medicinalVO;
    }
    @PostMapping(value = "/uploadXp")
    @ApiOperation(value = "上传成品", notes = "上传成品")
    public Result uploadXp(@RequestBody String id) throws Exception {
        QueryWrapper<WptpMedicinal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        WptpMedicinal wptpMedicinal = wptpMedicinalService.getBaseMapper().selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpMedicinal))
        {
            String result = wptpMedicinalService.uploadXh(wptpMedicinal);
            System.out.println(result);
            return new  Result<>().success("上传成功");
        }
        return new  Result<>().error500("上传失败");
    }
    @GetMapping(value = "/uploadXl")
    @ApiOperation(value = "上传环节", notes = "上传环节")
    public Result uploadXl(String traceCode) throws Exception {
        boolean upload = guildUpload.upload(traceCode, "1");
        if (upload)return  new  Result<>().success("上传成功");
        return new  Result<>().error500("上传失败");
    }


}
