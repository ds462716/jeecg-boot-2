package org.jeecg.modules.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.DateUtils;
import org.jeecg.common.util.ValidField;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.api.webservice.guild.upload.GuildUpload;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.service.IWptpEntInfoService;
import org.jeecg.modules.demo.hostcode.entity.WptpHostcode;
import org.jeecg.modules.demo.hostcode.service.IWptpHostcodeService;
import org.jeecg.modules.demo.uploadxh.service.IWptpUploadRecordService;
import org.jeecg.modules.demo.uploadxh.entity.WptpUploadRecord;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstock;
import org.jeecg.modules.demo.ypbinstock.service.IWptpYpbInstockService;
import org.jeecg.modules.demo.ypbsale.entity.WptpYpbSale;
import org.jeecg.modules.demo.ypbsale.service.IWptpYpbSaleService;
import org.jeecg.modules.demo.yppack.entity.WptpYpPack;
import org.jeecg.modules.demo.yppack.service.IWptpYpPackService;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcess;
import org.jeecg.modules.demo.ypprocess.service.IWptpYpProcessService;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstock;
import org.jeecg.modules.demo.ypprocessinstock.service.IWptpYpInstockFileService;
import org.jeecg.modules.demo.ypprocessinstock.service.IWptpYpInstockService;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSale;
import org.jeecg.modules.demo.ypprocesssale.service.IWptpYpSaleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 饮片控制层
 * @author laowang
 */
@RestController
@RequestMapping(value = "/yp")
@Slf4j
public class YpController {
    @Autowired
    private IWptpYpSaleService wptpYpSaleService;
    @Autowired
    private IWptpYpPackService wptpYpPackService;
    @Autowired
    private IWptpYpInstockService wptpYpInstockService;
    @Autowired
    private IWptpYpProcessService wptpYpProcessService;
    @Autowired
    private IWptpYpbInstockService iWptpYpbInstockService;
    @Autowired
    private IWptpYpbSaleService iWptpYpbSaleService;
    @Autowired
    private HostCodeCheck hostCodeCheck;
    @Autowired
    private IWptpYpInstockFileService iWptpYpInstockFileService;
    @Autowired
    private IWptpEntInfoService iWptpEntInfoService;
    @Autowired
    private GuildUpload guildUpload;
    @Autowired
    private IWptpUploadRecordService xhUploadRecordService;
    @Autowired
    private IWptpHostcodeService iWptpHostcodeService;
    /**
     * 饮片入库
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "/ypInstock")
    public synchronized
    String ypInstock(@NotBlank String jsonStr){
        String trim = jsonStr.trim();
        WptpYpInstock wptpYpInstock = new WptpYpInstock();
        BeanUtils.copyProperties(JSONObject.parseObject(trim, WptpYpInstock.class),wptpYpInstock);
        Result result = ValidField.checkField(wptpYpInstock);
        if (!result.isSuccess()) return JSONArray.toJSON(result).toString();
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(wptpYpInstock);
        //校验主机代码
        Result hostCodeResult = hostCodeCheck.checkHostCode(wptpYpInstock.getHostCode());

        if (!hostCodeResult.isSuccess()) return JSONArray.toJSON(hostCodeResult).toString();
        /**
         * 时间相关的字段进行转换
         */
        String arrivalTime = wptpYpInstock.getArrivalTime();
        String checkTime = wptpYpInstock.getCheckTime();
        if (arrivalTime.contains("/")) {
            String sqlDateArrivalTime = DateUtils.StringToSQLDateStr(arrivalTime);
            wptpYpInstock.setArrivalTime(sqlDateArrivalTime);
        }
        if (checkTime.contains("/")){
            String sqlDateCheckTime = DateUtils.StringToSQLDateStr(checkTime);
            wptpYpInstock.setCheckTime(sqlDateCheckTime);
        }
        /**
         * 校验企业id
         */
        if (iWptpEntInfoService.getEntByEntId(wptpYpInstock.getEntId()))return new Result().error500("根据企业id查不到企业信息").toString();
        QueryWrapper<WptpYpInstock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instock_number",wptpYpInstock.getInstockNumber());//判断是否有该流水号的记录
        queryWrapper.eq("deleted","0");
        WptpYpInstock wptpYpInstockInDB = wptpYpInstockService.getBaseMapper().selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpYpInstockInDB)) {
            wptpYpInstockInDB.setDeleted("1");
            wptpYpInstockInDB.setUpdateDate(new Date());
            wptpYpInstockInDB.setUpdateBy(wptpYpInstock.getHostCode());
            wptpYpInstockService.getBaseMapper().updateById(wptpYpInstockInDB);
        }
        wptpYpInstock.setCreateDate(new Date());
        wptpYpInstock.setDeleted("0");
        wptpYpInstock.setCreateBy(wptpYpInstock.getHostCode());
        wptpYpInstockService.saveMain(wptpYpInstock, null);
        return JSONArray.toJSON(new Result(true, "操作成功", 200, new Date().getTime())).toString();
    }



    /**
     * 饮片加工
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "/ypProcess")
    public synchronized String ypProcess(@NotBlank String jsonStr){
        String trim = jsonStr.trim();
        WptpYpProcess wptpYpProcess = new WptpYpProcess();
        BeanUtils.copyProperties(JSONObject.parseObject(trim, WptpYpProcess.class),wptpYpProcess);
        Result result = ValidField.checkField(wptpYpProcess);
        if (!result.isSuccess()) return JSONArray.toJSON(result).toString();
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(wptpYpProcess);
        //校验主机代码
        Result hostCodeResult = hostCodeCheck.checkHostCode(wptpYpProcess.getHostCode());

        if (!hostCodeResult.isSuccess()) return JSONArray.toJSON(hostCodeResult).toString();
        /**
         * 对时间相关的字段进行格式转化
         */
        String producedDate = wptpYpProcess.getProducedDate();
        String expiredDate = wptpYpProcess.getExpiredDate();
        String checkTime = wptpYpProcess.getCheckTime();
        String inTime = wptpYpProcess.getInTime();
        if (checkTime.contains("/")){
            String sqlDateCheckTime = DateUtils.StringToSQLDateStr(checkTime);
            wptpYpProcess.setCheckTime(sqlDateCheckTime);
        }
        if (expiredDate.contains("/")){
            String sqlDateExpiredDate = DateUtils.StringToSQLDateStr(expiredDate);
            wptpYpProcess.setExpiredDate(sqlDateExpiredDate);
        }
        if (producedDate.contains("/")){
            String sqlDateProducedDate = DateUtils.StringToSQLDateStr(producedDate);
            wptpYpProcess.setProducedDate(sqlDateProducedDate);
        }
        if (inTime.contains("/")){
            String sqlDateInTime = DateUtils.StringToSQLDateStr(inTime);
            wptpYpProcess.setInTime(sqlDateInTime);
        }

        QueryWrapper<WptpYpProcess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("process_no",wptpYpProcess.getProcessNo());//判断是否有该流水号的记录
        queryWrapper.eq("deleted","0");
        WptpYpProcess wptpYpProcessInDB = wptpYpProcessService.getBaseMapper().selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpYpProcessInDB)) {
            wptpYpProcessInDB.setDeleted("1");
            wptpYpProcessInDB.setUpdateDate(new Date());
            wptpYpProcessInDB.setUpdateBy(wptpYpProcess.getHostCode());
            wptpYpProcessService.getBaseMapper().updateById(wptpYpProcessInDB);
        }
        wptpYpProcess.setDeleted("0");
        wptpYpProcess.setCreateDate(new Date());
        wptpYpProcess.setCreateBy(wptpYpProcess.getHostCode());
        wptpYpProcessService.saveMain(wptpYpProcess, null);
        return JSONArray.toJSON(new Result(true, "操作成功", 200, new Date().getTime())).toString();
    }

    /**
     * 饮片包装
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "/ypPack")
    public synchronized String ypPack(@NotBlank String jsonStr){
        String trim = jsonStr.trim();
        WptpYpPack wptpYpPack = new WptpYpPack();
        BeanUtils.copyProperties(JSONObject.parseObject(trim, WptpYpPack.class),wptpYpPack);
        Result result = ValidField.checkField(wptpYpPack);
        if (!result.isSuccess()) return JSONArray.toJSON(result).toString();
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(wptpYpPack);
        //校验主机代码
        Result hostCodeResult = hostCodeCheck.checkHostCode(wptpYpPack.getHostCode());

        if (!hostCodeResult.isSuccess()) return JSONArray.toJSON(hostCodeResult).toString();
        QueryWrapper<WptpYpPack> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pack_no",wptpYpPack.getPackNo());//判断是否有该流水号的记录
        queryWrapper.eq("deleted","0");
        WptpYpPack wptpYpPackInDB = wptpYpPackService.getBaseMapper().selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpYpPackInDB)) {
            wptpYpPackInDB.setDeleted("1");
            wptpYpPackInDB.setUpdateDate(new Date());
            wptpYpPackInDB.setUpdateBy(wptpYpPack.getHostCode());
            wptpYpPackService.getBaseMapper().updateById(wptpYpPackInDB);
        }
        wptpYpPack.setDeleted("0");
        wptpYpPack.setCreateDate(new Date());
        wptpYpPack.setCreateBy(wptpYpPack.getHostCode());
        wptpYpPackService.save(wptpYpPack);
        return JSONArray.toJSON(new Result(true, "操作成功", 200, new Date().getTime())).toString();
    }

    /**
     * 饮片销售
     * @param jsonStr
     * @return
     */
    @RequestMapping(value = "/ypSale")
    public synchronized String ypSale(@NotBlank String jsonStr){
        String trim = jsonStr.trim();
        WptpYpSale wptpYpSale = new WptpYpSale();
        BeanUtils.copyProperties(JSONObject.parseObject(trim, WptpYpSale.class),wptpYpSale);
        Result result = ValidField.checkField(wptpYpSale);
        if (!result.isSuccess()) return JSONArray.toJSON(result).toString();
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(wptpYpSale);
        /**
         * 校验主机代码
         */
        Result hostCodeResult = hostCodeCheck.checkHostCode(wptpYpSale.getHostCode());

        if (!hostCodeResult.isSuccess()) return JSONArray.toJSON(hostCodeResult).toString();
        /**
         * 校验追溯码
         */
        Result traceCodeResult = ValidField.checkTraceCode(wptpYpSale.getTraceCode(), "23");
        if (!traceCodeResult.isSuccess()) return JSONArray.toJSON(traceCodeResult).toString();
        /**
         * 校验企业id
         */
        if (iWptpEntInfoService.getEntByEntId(wptpYpSale.getEntId()))return new Result().error500("根据企业id查不到企业信息").toString();
        String saleTime = wptpYpSale.getSaleTime();
        if (saleTime.contains("/")){
            String sqlDateSaleTime = DateUtils.StringToSQLDateStr(saleTime);
            wptpYpSale.setSaleTime(sqlDateSaleTime);
        }
        QueryWrapper<WptpYpSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sale_number",wptpYpSale.getSaleNumber());//判断是否有该流水号的记录
        queryWrapper.eq("deleted","0");
        WptpYpSale wptpYpSaleInDB = wptpYpSaleService.getBaseMapper().selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpYpSaleInDB)) {
            wptpYpSaleInDB.setDeleted("1");
            wptpYpSaleInDB.setUpdateDate(new Date());
            wptpYpSaleInDB.setUpdateBy(wptpYpSale.getHostCode());
            wptpYpSaleService.getBaseMapper().updateById(wptpYpSaleInDB);
        }
        wptpYpSale.setDeleted("0");
        wptpYpSale.setCreateDate(new Date());
        wptpYpSale.setCreateBy(wptpYpSale.getHostCode());
        String entIdByHostCode = getEntIdByHostCode(wptpYpSale.getHostCode());
        String entXhCode = getEntXhCode(entIdByHostCode);
        wptpYpSale.setXhTraceCode(entXhCode+"-"+wptpYpSale.getTraceCode());
        wptpYpSaleService.saveMain(wptpYpSale, null);
        /**
         * 接收到销售数据就要上传至行业协会
         */
        try {
            guildUpload.upload(wptpYpSale.getTraceCode(),"0");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            WptpUploadRecord xhUploadRecord = new WptpUploadRecord(new Date(),"失败",e.getMessage(),wptpYpSale.getTraceCode(),"","饮片加工");
            xhUploadRecordService.getBaseMapper().insert(xhUploadRecord);
        }
        return JSONArray.toJSON(new Result(true, "操作成功", 200, new Date().getTime())).toString();
    }
    /**
     * 饮片经营-饮片入库
     */
    @RequestMapping(value = "/ypbInstock")
    public synchronized String ypbInstock(@NotBlank String jsonStr) {
        String trim = jsonStr.trim();
        WptpYpbInstock wptpYpbInstock = new WptpYpbInstock();
        BeanUtils.copyProperties(JSONObject.parseObject(trim, WptpYpbInstock.class),wptpYpbInstock);
        Result result = ValidField.checkField(wptpYpbInstock);
        if (!result.isSuccess()) return JSONArray.toJSON(result).toString();
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(wptpYpbInstock);
        //校验主机代码
        Result hostCodeResult = hostCodeCheck.checkHostCode(wptpYpbInstock.getHostCode());

        if (!hostCodeResult.isSuccess()) return JSONArray.toJSON(hostCodeResult).toString();
        /**
         * 校验企业id
         */
        if (iWptpEntInfoService.getEntByEntId(wptpYpbInstock.getEntId()))return new Result().error500("根据企业id查不到企业信息").toString();
        QueryWrapper<WptpYpbInstock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instock_number",wptpYpbInstock.getInstockNumber());//判断是否有该流水号的记录
        queryWrapper.eq("deleted","0");
        WptpYpbInstock wptpYpbInstockInDB = iWptpYpbInstockService.getBaseMapper().selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpYpbInstockInDB)) {
            wptpYpbInstockInDB.setDeleted("1");
            wptpYpbInstockInDB.setUpdateDate(new Date());
            wptpYpbInstockInDB.setUpdateBy(wptpYpbInstock.getHostCode());
            iWptpYpbInstockService.getBaseMapper().updateById(wptpYpbInstockInDB);
        }
        wptpYpbInstock.setDeleted("0");
        wptpYpbInstock.setCreateDate(new Date());
        wptpYpbInstock.setCreateBy(wptpYpbInstock.getHostCode());
        iWptpYpbInstockService.saveMain(wptpYpbInstock, null);
        return JSONArray.toJSON(new Result(true, "操作成功", 200,new Date().getTime())).toString();
    }
    /**
     * 饮片经营-饮片销售
     */
    @RequestMapping(value = "/ypbSale")
    public synchronized String ypbSale(@NotBlank String jsonStr) {
        String trim = jsonStr.trim();
        WptpYpbSale wptpYpbSale = new WptpYpbSale();
        BeanUtils.copyProperties(JSONObject.parseObject(trim, WptpYpbSale.class),wptpYpbSale);
        Result result = ValidField.checkField(wptpYpbSale);
        if (!result.isSuccess()) return JSONArray.toJSON(result).toString();
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(wptpYpbSale);
        /**
         * 校验主机代码
         */
        Result hostCodeResult = hostCodeCheck.checkHostCode(wptpYpbSale.getHostCode());

        if (!hostCodeResult.isSuccess()) return JSONArray.toJSON(hostCodeResult).toString();
        /**
         * 校验追溯码
         */
        Result traceCodeResult = ValidField.checkTraceCode(wptpYpbSale.getTraceCode(), "31");
        if (!traceCodeResult.isSuccess()) return JSONArray.toJSON(traceCodeResult).toString();
        /**
         * 校验企业id
         */
        if (iWptpEntInfoService.getEntByEntId(wptpYpbSale.getEntId()))return new Result().error500("根据企业id查不到企业信息").toString();
        QueryWrapper<WptpYpbSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instock_no",wptpYpbSale.getInstockNo());//判断是否有该流水号的记录
        queryWrapper.eq("deleted","0");
        WptpYpbSale wptpYpbSaleInDB = iWptpYpbSaleService.getBaseMapper().selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpYpbSaleInDB)) {
            wptpYpbSaleInDB.setDeleted("1");
            wptpYpbSaleInDB.setUpdateDate(new Date());
            wptpYpbSaleInDB.setUpdateBy(wptpYpbSale.getHostCode());
            iWptpYpbSaleService.getBaseMapper().updateById(wptpYpbSaleInDB);
        }
        wptpYpbSale.setDeleted("0");
        wptpYpbSale.setCreateDate(new Date());
        wptpYpbSale.setCreateBy(wptpYpbSale.getHostCode());
        String entIdByHostCode = getEntIdByHostCode(wptpYpbSale.getHostCode());
        String entXhCode = getEntXhCode(entIdByHostCode);
        wptpYpbSale.setXhTraceCode(entXhCode+"-"+wptpYpbSale.getTraceCode());
        iWptpYpbSaleService.save(wptpYpbSale);
        /**
         * 接收到销售数据就要上传至行业协会
         */
        try {
            guildUpload.upload(wptpYpbSale.getTraceCode(),"0");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            WptpUploadRecord xhUploadRecord = new WptpUploadRecord(new Date(),"失败",e.getMessage(),wptpYpbSale.getTraceCode(),"","饮片经营");
            xhUploadRecordService.addWptpUploadRecord(xhUploadRecord);
        }
        return JSONArray.toJSON(new Result(true, "操作成功", 200,new Date().getTime())).toString();
    }
    /**
     * 根据主机代码查询企业id
     * @return
     */
    private String getEntIdByHostCode(String hostCode){
        QueryWrapper<WptpHostcode> entInfoQueryWrapper = new QueryWrapper<>();
        if (oConvertUtils.isEmpty(hostCode))return null;
        entInfoQueryWrapper.eq("host_code",hostCode.trim());
        WptpHostcode wptpHostcode = iWptpHostcodeService.getBaseMapper().selectOne(entInfoQueryWrapper);
        if (!oConvertUtils.isEmpty(wptpHostcode))return wptpHostcode.getEntId();
        return null;
    }
    /**
     * 根据企业id查询xhCode
     * @return
     */
    private String getEntXhCode(String entId){
        QueryWrapper<WptpEntInfo> entInfoQueryWrapper = new QueryWrapper<>();
        if (oConvertUtils.isEmpty(entId))return null;
        entInfoQueryWrapper.eq("ent_id",entId.trim());
        entInfoQueryWrapper.eq("deleted","0");
        WptpEntInfo wptpEntInfo = iWptpEntInfoService.getBaseMapper().selectOne(entInfoQueryWrapper);
        if (!oConvertUtils.isEmpty(wptpEntInfo))return wptpEntInfo.getXhCode();
        return null;
    }

}
