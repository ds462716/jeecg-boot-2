package org.jeecg.modules.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.ValidField;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.api.webservice.guild.upload.GuildUpload;
import org.jeecg.modules.demo.baseinfo.entity.WptpBase;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseService;
import org.jeecg.modules.demo.blockinfo.entity.WptpBlockInfo;
import org.jeecg.modules.demo.blockinfo.service.IWptpBlockInfoService;
import org.jeecg.modules.demo.blockmedicinal.entity.WptpBlockMeidicinal;
import org.jeecg.modules.demo.blockmedicinal.service.IWptpBlockMeidicinalService;
import org.jeecg.modules.demo.csinfo.entity.WptpCsInfo;
import org.jeecg.modules.demo.csinfo.service.IWptpCsInfoService;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.service.IWptpEntInfoService;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantInfo;
import org.jeecg.modules.demo.plantinfo.service.IWptpPlantInfoService;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessInfo;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessInfoService;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.vo.WptpProcessInfoVO;
import org.jeecg.modules.demo.province.service.IWptpProvinceService;
import org.jeecg.modules.demo.sale.entity.WptpSale;
import org.jeecg.modules.demo.sale.service.IWptpSaleService;
import org.jeecg.modules.demo.uploadxh.service.IWptpUploadRecordService;
import org.jeecg.modules.demo.uploadxh.entity.WptpUploadRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 种植端控制层
 *
 * @author laowang
 */
@RestController
@Slf4j
@RequestMapping(value = "/plant")
public class PlantController {
    @Autowired
    private IWptpEntInfoService iWptpEntInfoService;
    @Autowired
    private IWptpBaseService iWptpBaseService;
    @Autowired
    private IWptpBlockMeidicinalService iWptpBlockMeidicinalService;
    @Autowired
    private IWptpPlantInfoService iWptpPlantInfoService;
    @Autowired
    private IWptpProcessInfoService iWptpProcessInfoService;
    @Autowired
    private IWptpSaleService iWptpSaleService;
    @Autowired
    private IWptpCsInfoService iWptpCsInfoService;
    @Autowired
    private HostCodeCheck hostCodeCheck;
    @Autowired
    private IWptpProvinceService iWptpProvinceService;
    @Autowired
    private IWptpBlockInfoService iWptpBlockInfoService;
    @Autowired
    private IWptpProcessMaterial iWptpProcessMaterial;
    @Autowired
    private GuildUpload guildUpload;
    @Autowired
    private IWptpUploadRecordService xhUploadRecordService;

    /**
     * 种植-企业/供应商信息
     *
     * @param jsonStr
     * @return
     */
    @RequestMapping("/addCompanyInfo")
    public synchronized Result addCompanyInfo(@NotNull String jsonStr) {
        if (oConvertUtils.isEmpty(jsonStr)) return new Result().error500("参数不能为空");
        String trim = jsonStr.trim();
        WptpEntInfo wptpEntInfo = new WptpEntInfo();
        BeanUtils.copyProperties(JSONObject.parseObject(trim, WptpEntInfo.class), wptpEntInfo);
        Result result = ValidField.checkField(wptpEntInfo);
        if (!result.isSuccess()) return result;
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(wptpEntInfo);
        Result districtCode = iWptpProvinceService.getDistrictCode(wptpEntInfo.getProvince(), wptpEntInfo.getCity(), wptpEntInfo.getArea());

        if (!districtCode.isSuccess()) return districtCode;
        //校验主机代码
        String hostCode = wptpEntInfo.getHostCode();
        Result hostCodeResult = hostCodeCheck.checkHostCode(hostCode);

        if (!hostCodeResult.isSuccess()) return hostCodeResult;
        String entId = iWptpEntInfoService.getEntId((String) districtCode.getResult());
        if (oConvertUtils.isEmpty(entId)) entId = (String) districtCode.getResult() + "000";
        int anInt = oConvertUtils.getInt(entId);
        wptpEntInfo.setCreateTime(new Date());
        wptpEntInfo.setEntId(oConvertUtils.getString(anInt + 1));
        wptpEntInfo.setProvince((String) districtCode.getResultList().get(0));//保存省份编码
        wptpEntInfo.setCity((String) districtCode.getResultList().get(1));//保存市编码
        wptpEntInfo.setArea((String) districtCode.getResultList().get(2));//保存县地区编码
        QueryWrapper<WptpEntInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ent_id", wptpEntInfo.getEntId());//判断是否有该流水号的记录
        queryWrapper.eq("deleted", "0");
        WptpEntInfo wptpEntInfoInDB = iWptpEntInfoService.getBaseMapper().selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpEntInfoInDB)) {
            wptpEntInfoInDB.setDeleted("1");
            wptpEntInfoInDB.setUpdateTime(new Date());
            wptpEntInfoInDB.setUpdateBy(hostCode);
            iWptpEntInfoService.getBaseMapper().updateById(wptpEntInfoInDB);
        }
        wptpEntInfo.setDeleted("0");
        wptpEntInfo.setCreateTime(new Date());
        wptpEntInfo.setCreateBy(hostCode);
        iWptpEntInfoService.saveMain(wptpEntInfo, null);
        return new Result(true, "操作成功", 200, anInt + 1, new Date().getTime());
        /*return new Result(true, "操作成功", 200, new Date().getTime());*/
    }

    /**
     * 种植-基地信息
     *
     * @param jsonStr
     * @return
     */
    @RequestMapping("/addBaseInfo")
    public synchronized Result addBaseInfo(@NotNull String jsonStr) {
        if (oConvertUtils.isEmpty(jsonStr)) return new Result().error500("参数不能为空");
        /*  try{*/
        String trim = jsonStr.trim();
        WptpBase wptpBase = new WptpBase();
        BeanUtils.copyProperties(JSONObject.parseObject(trim, WptpBase.class), wptpBase);
        Result result = ValidField.checkField(wptpBase);
        if (!result.isSuccess()) return result;
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(wptpBase);
        /*        String baseCode = iWptpBaseService.getBaseCode(wptpBase.getEntId());
        if (oConvertUtils.isEmpty(baseCode))baseCode=wptpBase.getEntId()+"001";
        int anInt = oConvertUtils.getInt(baseCode);*/
        /*wptpBase.setBaseCode(oConvertUtils.getString(anInt+1));*/
        /**
         * 把省市区名替换成id
         */
        Result districtCode = iWptpProvinceService.getDistrictCode(wptpBase.getProvince(), wptpBase.getCity(), wptpBase.getArea());

        if (!districtCode.isSuccess()) return districtCode;
        //校验主机代码
        String hostCode = wptpBase.getHostCode();
        Result hostCodeResult = hostCodeCheck.checkHostCode(hostCode);

        if (!hostCodeResult.isSuccess()) return hostCodeResult;
        /**
         * 校验企业id
         */
        if (iWptpEntInfoService.getEntByEntId(wptpBase.getEntId())) return new Result().error500("根据企业id查不到企业信息");
        wptpBase.setProvince((String) districtCode.getResultList().get(0));//保存省份编码
        wptpBase.setCity((String) districtCode.getResultList().get(1));//保存市编码
        wptpBase.setArea((String) districtCode.getResultList().get(2));//保存县地区编码
        QueryWrapper<WptpBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("base_code", wptpBase.getBaseCode());//判断是否有该流水号的记录
        queryWrapper.eq("deleted", "0");
        WptpBase wptpBaseInDB = iWptpBaseService.getBaseMapper().selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpBaseInDB)) {
            wptpBaseInDB.setDeleted("1");
            wptpBaseInDB.setUpdateTime(new Date());
            wptpBaseInDB.setUpdateBy(hostCode);
            iWptpBaseService.getBaseMapper().updateById(wptpBaseInDB);
        }
        wptpBase.setDeleted("0");
        wptpBase.setCreateTime(new Date());
        wptpBase.setCreateBy(hostCode);
        iWptpBaseService.saveMain(wptpBase, null);
        return new Result(true, "操作成功", 200, new Date().getTime());
    }

    /**
     * 种植-档案信息
     *
     * @param jsonStr
     * @return
     */
    @RequestMapping("/addBlockMeidicinalInfo")
    public synchronized Result addBlockMeidicinalInfo(@NotNull String jsonStr) {
        if (oConvertUtils.isEmpty(jsonStr)) return new Result().error500("参数不能为空");
        String trim = jsonStr.trim();
        WptpBlockMeidicinal wptpBlockMeidicinal = new WptpBlockMeidicinal();//档案
        WptpBlockInfo wptpBlockInfo = new WptpBlockInfo();//地块
        BeanUtils.copyProperties(JSONObject.parseObject(trim, WptpBlockMeidicinal.class), wptpBlockMeidicinal);
        BeanUtils.copyProperties(JSONObject.parseObject(trim, WptpBlockInfo.class), wptpBlockInfo);
        Result result = ValidField.checkField(wptpBlockMeidicinal);
        if (!result.isSuccess()) return result;
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(wptpBlockMeidicinal);
        //校验主机代码
        String hostCode = wptpBlockMeidicinal.getHostCode();
        Result hostCodeResult = hostCodeCheck.checkHostCode(hostCode);

        if (!hostCodeResult.isSuccess()) return hostCodeResult;
        /**
         * 校验企业id
         */
        if (iWptpEntInfoService.getEntByEntId(wptpBlockMeidicinal.getEntId()))
            return new Result().error500("根据企业id查不到企业信息");
        QueryWrapper<WptpBlockMeidicinal> queryWrapperM = new QueryWrapper<>();
        QueryWrapper<WptpBlockInfo> queryWrapperI = new QueryWrapper<>();
        queryWrapperM.eq("block_medicinal_id", wptpBlockMeidicinal.getBlockMedicinalId());//判断是否有该流水号的记录
        queryWrapperM.eq("deleted", "0");
        queryWrapperI.eq("block_code", wptpBlockMeidicinal.getBlockCode());//判断是否有该流水号的记录
        queryWrapperI.eq("deleted", "0");
        WptpBlockMeidicinal wptpBlockMeidicinalInDB = iWptpBlockMeidicinalService.getBaseMapper().selectOne(queryWrapperM);
        WptpBlockInfo wptpBlockInfoInDB = iWptpBlockInfoService.getBaseMapper().selectOne(queryWrapperI);
        if (!oConvertUtils.isEmpty(wptpBlockMeidicinalInDB)) {
            wptpBlockMeidicinalInDB.setDeleted("1");
            wptpBlockMeidicinalInDB.setUpdateTime(new Date());
            wptpBlockMeidicinalInDB.setUpdateBy(hostCode);
            iWptpBlockMeidicinalService.getBaseMapper().updateById(wptpBlockMeidicinalInDB);
        }
        if (!oConvertUtils.isEmpty(wptpBlockInfoInDB)) {
            wptpBlockInfoInDB.setDeleted("1");
            wptpBlockInfoInDB.setUpdateBy(hostCode);
            wptpBlockInfoInDB.setUpdateTime(new Date());
            iWptpBlockInfoService.getBaseMapper().updateById(wptpBlockInfoInDB);
        }
        wptpBlockMeidicinal.setDeleted("0");
        wptpBlockMeidicinal.setCreateTime(new Date());
        wptpBlockMeidicinal.setCreateBy(hostCode);
        wptpBlockInfo.setDeleted("0");
        wptpBlockInfo.setCreateTime(new Date());
        wptpBlockInfo.setCreateBy(hostCode);
        iWptpBlockMeidicinalService.saveMain(wptpBlockMeidicinal);
        iWptpBlockInfoService.saveMain(wptpBlockInfo, null);
        return new Result(true, "操作成功", 200, new Date().getTime());
    }

    /**
     * 种植-作业信息
     *
     * @param jsonStr
     * @return
     */
    @RequestMapping("/addPlantInfo")
    public synchronized Result addPlantInfo(@NotNull String jsonStr) {
        if (oConvertUtils.isEmpty(jsonStr)) return new Result().error500("参数不能为空");
        String trim = jsonStr.trim();
        JSONObject jsonObject = JSONObject.parseObject(trim);
        String r = jsonObject.getString("jsonStr");
        List<WptpPlantInfo> wptpPlantInfos = (List<WptpPlantInfo>) JSONArray.parseArray(r, WptpPlantInfo.class);
        /**
         * 去除字段首尾空格
         */
        for (WptpPlantInfo w :
                wptpPlantInfos) {
            ValidField.trimSpace(w);
        }
        //校验主机代码
        String hostCode = wptpPlantInfos.get(0).getHostCode();

        Result hostCodeResult = hostCodeCheck.checkHostCode(hostCode);
        if (!hostCodeResult.isSuccess()) return hostCodeResult;
        for (WptpPlantInfo plantInfo :
                wptpPlantInfos) {
            Result result = ValidField.checkField(plantInfo);
            if (!result.isSuccess()) return result;
            QueryWrapper<WptpPlantInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("plant_id", plantInfo.getPlantId());//判断是否有该流水号的记录
            queryWrapper.eq("deleted", "0");
            List<WptpPlantInfo> wptpPlantInfoList = iWptpPlantInfoService.getBaseMapper().selectList(queryWrapper);
            if (!oConvertUtils.isEmpty(wptpPlantInfoList)) {
                for (WptpPlantInfo wptpPlantInfoInDB : wptpPlantInfoList
                ) {
                    wptpPlantInfoInDB.setDeleted("1");
                    wptpPlantInfoInDB.setUpdateTime(new Date());
                    wptpPlantInfoInDB.setUpdateBy(hostCode);
                    iWptpPlantInfoService.getBaseMapper().updateById(wptpPlantInfoInDB);
                }
            }
            plantInfo.setDeleted("0");
            plantInfo.setCreateTime(new Date());
            plantInfo.setCreateBy(hostCode);
            iWptpPlantInfoService.saveMain(plantInfo, null);
        }

        return new Result(true, "操作成功", 200, new Date().getTime());
    }

    /**
     * 种植-初加工信息
     *
     * @param jsonStr
     * @return
     */
    @RequestMapping("/addProcessInfo")
    public synchronized Result addProcessInfo(@NotNull String jsonStr) {
        if (oConvertUtils.isEmpty(jsonStr)) return new Result().error500("参数不能为空");

        String trim = jsonStr.trim();
        WptpProcessInfo wptpProcessInfo = new WptpProcessInfo();
        BeanUtils.copyProperties(JSONObject.parseObject(trim, WptpProcessInfo.class), wptpProcessInfo);
        WptpProcessInfoVO wptpProcessMaterialVO = new WptpProcessInfoVO();
        BeanUtils.copyProperties(JSONObject.parseObject(trim, WptpProcessInfoVO.class), wptpProcessMaterialVO);
        Result result = ValidField.checkField(wptpProcessInfo);
        if (!result.isSuccess()) return result;
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(wptpProcessInfo);
        //校验主机代码
        String hostCode = wptpProcessInfo.getHostCode();
        Result hostCodeResult = hostCodeCheck.checkHostCode(hostCode);

        if (!hostCodeResult.isSuccess()) return hostCodeResult;
        /**
         * 校验企业id
         */
        if (iWptpEntInfoService.getEntByEntId(wptpProcessInfo.getEntId()))
            return new Result().error500("根据企业id查不到企业信息");


        QueryWrapper<WptpProcessInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("process_no", wptpProcessInfo.getProcessNo());//判断是否有该流水号的记录
        queryWrapper.eq("deleted", "0");
        WptpProcessInfo wptpProcessInfoInDB = iWptpProcessInfoService.getBaseMapper().selectOne(queryWrapper);
        /**
         * 加工原料
         */
        QueryWrapper<WptpProcessMaterial> wptpProcessMaterialQueryWrapper = new QueryWrapper<>();
        wptpProcessMaterialQueryWrapper.eq("process_no", wptpProcessInfo.getProcessNo());//判断是否有该流水号的记录
        wptpProcessMaterialQueryWrapper.eq("deleted", "0");
        List<WptpProcessMaterial> wptpProcessMaterialList = iWptpProcessMaterial.getBaseMapper().selectList(wptpProcessMaterialQueryWrapper);
        if (!oConvertUtils.isEmpty(wptpProcessInfoInDB)) {
            wptpProcessInfoInDB.setDeleted("1");
            wptpProcessInfoInDB.setUpdateTime(new Date());
            wptpProcessInfoInDB.setUpdateBy(hostCode);
            iWptpProcessInfoService.getBaseMapper().updateById(wptpProcessInfoInDB);

            for (WptpProcessMaterial w :
                    wptpProcessMaterialList) {
                w.setDeleted("1");
                w.setUpdateTime(new Date());
                w.setUpdateBy(hostCode);
                iWptpProcessMaterial.getBaseMapper().updateById(w);
            }
        }

        wptpProcessInfo.setDeleted("0");
        wptpProcessInfo.setCreateTime(new Date());
        wptpProcessInfo.setCreateBy(hostCode);
        iWptpProcessInfoService.saveMain(wptpProcessInfo, null);

        List<WptpProcessMaterial> batch = wptpProcessMaterialVO.getBatch();
        if (batch != null) {
            for (WptpProcessMaterial wpm :
                    batch) {
                BeanUtils.copyProperties(wptpProcessMaterialVO, wpm);
                wpm.setCreateTime(new Date());
                wpm.setDeleted("0");
                iWptpProcessMaterial.getBaseMapper().insert(wpm);
            }
        }
        return new Result(true, "操作成功", 200, new Date().getTime());

    }

    /**
     * 种植-出库销售信息
     *
     * @param jsonStr
     * @return
     */
    @RequestMapping("/addSaleInfo")
    public synchronized Result addSaleInfo(@NotNull String jsonStr) {
        if (oConvertUtils.isEmpty(jsonStr)) return new Result().error500("参数不能为空");
        String trim = jsonStr.trim();
        WptpSale wptpSale = new WptpSale();
        BeanUtils.copyProperties(JSONObject.parseObject(trim, WptpSale.class), wptpSale);
        Result result = ValidField.checkField(wptpSale);
        if (!result.isSuccess()) return result;
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(wptpSale);
        /**
         * 校验主机代码
         */
        String hostCode = wptpSale.getHostCode();
        Result hostCodeResult = hostCodeCheck.checkHostCode(hostCode);
        if (!hostCodeResult.isSuccess()) return hostCodeResult;
        /**
         * 校验追溯码
         */
        Result traceCodeResult = ValidField.checkTraceCode(wptpSale.getTraceCode(), "04");
        if (!traceCodeResult.isSuccess()) return traceCodeResult;
        /**
         * 校验来源是否是0或者1
         */
        String source = wptpSale.getSource();
        if (!source.contains("1") && !source.contains("0")) return new Result().error500("来源必须是0或者1");
        /**
         * 校验企业ID
         */
        if (iWptpEntInfoService.getEntByEntId(wptpSale.getEntId())) return new Result().error500("根据企业id查不到企业信息");
        QueryWrapper<WptpSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sale_number", wptpSale.getSaleNumber());//判断是否有该流水号的记录
        queryWrapper.eq("deleted", "0");
        WptpSale wptpSaleInDB = iWptpSaleService.getBaseMapper().selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpSaleInDB)) {
            wptpSaleInDB.setDeleted("1");
            wptpSaleInDB.setUpdateTime(new Date());
            wptpSaleInDB.setUpdateBy(hostCode);
            iWptpSaleService.getBaseMapper().updateById(wptpSaleInDB);
        }
        wptpSale.setDeleted("0");
        wptpSale.setCreateTime(new Date());
        wptpSale.setCreateBy(hostCode);
        iWptpSaleService.save(wptpSale);
        /**
         * 接收到销售数据就要上传至行业协会
         */
        try {
            boolean upload = guildUpload.upload(wptpSale.getTraceCode(), "0");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            WptpUploadRecord xhUploadRecord = new WptpUploadRecord(new Date(), "失败", e.getMessage(), wptpSale.getTraceCode(), "", "种植");
            xhUploadRecordService.addWptpUploadRecord(xhUploadRecord);
        }
        return new Result(true, "操作成功", 200, new Date().getTime());


    }

    /**
     * 种植-采收批次
     *
     * @param jsonStr
     * @return
     */
    @RequestMapping("/addCsInfo")
    public Result addCsInfo(@NotNull String jsonStr) {
        if (oConvertUtils.isEmpty(jsonStr)) return new Result().error500("参数不能为空");
        String trim = jsonStr.trim();
        WptpCsInfo wptpCsInfo = new WptpCsInfo();
        BeanUtils.copyProperties(JSONObject.parseObject(trim, WptpCsInfo.class), wptpCsInfo);
        Result result = ValidField.checkField(wptpCsInfo);
        if (!result.isSuccess()) return result;
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(wptpCsInfo);
        //校验主机代码
        String hostCode = wptpCsInfo.getHostCode();
        Result hostCodeResult = hostCodeCheck.checkHostCode(hostCode);

        if (!hostCodeResult.isSuccess()) return hostCodeResult;
        /**
         * 校验企业id
         */
        if (iWptpEntInfoService.getEntByEntId(wptpCsInfo.getEntId())) return new Result().error500("根据企业id查不到企业信息");

        QueryWrapper<WptpCsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cs_no", wptpCsInfo.getCsNo());//判断是否有该流水号的记录
        queryWrapper.eq("deleted", "0");
        WptpCsInfo wptpCsInfoInDB = iWptpCsInfoService.getBaseMapper().selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpCsInfoInDB)) {
            wptpCsInfoInDB.setDeleted("1");
            wptpCsInfoInDB.setUpdateBy(hostCode);
            wptpCsInfoInDB.setUpdateTime(new Date());
            iWptpCsInfoService.getBaseMapper().updateById(wptpCsInfoInDB);
        }
        wptpCsInfo.setDeleted("0");
        wptpCsInfo.setCreateTime(new Date());
        wptpCsInfo.setCreateBy(hostCode);
        iWptpCsInfoService.save(wptpCsInfo);
        return new Result(true, "操作成功", 200, new Date().getTime());
    }
}
