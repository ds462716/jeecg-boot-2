package org.jeecg.modules.api.webservice;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.ValidField;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.api.HostCodeCheck;
import org.jeecg.modules.api.webservice.guild.upload.GuildUpload;
import org.jeecg.modules.demo.entinfo.service.IWptpEntInfoService;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstock;
import org.jeecg.modules.demo.medicinebinstock.service.IWptpMedicineInstockService;
import org.jeecg.modules.demo.medicinebsale.entity.WptpMedicineSale;
import org.jeecg.modules.demo.medicinebsale.service.IWptpMedicineSaleService;
import org.jeecg.modules.demo.xhUploadRecord.XhUploadRecord;
import org.jeecg.modules.demo.xhUploadRecord.XhUploadRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 药材相关webservice接口
 * @author laowang
 */
@Component
@Slf4j
@WebService(name = "medicinalService",targetNamespace="http://webservice.api.modules.jeecg.org/",endpointInterface = "org.jeecg.modules.api.webservice.MedicinalService")
public class MedicinalServiceImpl implements MedicinalService {
    @Autowired
    private IWptpMedicineInstockService iWptpMedicineInstockService;
    @Autowired
    private IWptpMedicineSaleService iWptpMedicineSaleService;
    @Autowired
    private HostCodeCheck hostCodeCheck;
    @Autowired
    private IWptpEntInfoService iWptpEntInfoService;
    @Autowired
    private GuildUpload guildUpload;
    @Autowired
    private XhUploadRecordService xhUploadRecordService;
    @Override
    public synchronized String medicineInstock(@NotBlank String jsonStr) {
        String trim = jsonStr.trim();
        WptpMedicineInstock wptpMedicineInstock = new WptpMedicineInstock();
        BeanUtils.copyProperties(com.alibaba.fastjson.JSONObject.parseObject(trim, WptpMedicineInstock.class),wptpMedicineInstock);
        Result result = ValidField.checkField(wptpMedicineInstock);
        if (!result.isSuccess()) return JSONArray.toJSON(result).toString();
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(wptpMedicineInstock);
        /**
         * 校验主机代码
         */
        Result hostCodeResult = hostCodeCheck.checkHostCode(wptpMedicineInstock.getHostCode());

        if (!hostCodeResult.isSuccess()) return JSONArray.toJSON(hostCodeResult).toString();
        /**
         * 校验企业id
         */
        if (iWptpEntInfoService.getEntByEntId(wptpMedicineInstock.getEntId()))return new Result().error500("根据企业id查不到企业信息").toString();
        QueryWrapper<WptpMedicineInstock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instock_number",wptpMedicineInstock.getInstockNumber());//判断是否有该流水号的记录
        queryWrapper.eq("deleted","0");
        WptpMedicineInstock wptpMedicineInstockInDB = iWptpMedicineInstockService.getBaseMapper().selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpMedicineInstockInDB)) {
            wptpMedicineInstockInDB.setDeleted("1");
            wptpMedicineInstockInDB.setUpdateTime(new Date());
            wptpMedicineInstockInDB.setUpdateBy(wptpMedicineInstock.getHostCode());
            iWptpMedicineInstockService.getBaseMapper().updateById(wptpMedicineInstockInDB);
        }
        wptpMedicineInstock.setDeleted("0");
        wptpMedicineInstock.setCreateTime(new Date());
        wptpMedicineInstock.setCreateBy(wptpMedicineInstock.getHostCode());
        iWptpMedicineInstockService.saveMain(wptpMedicineInstock, null);
        return JSONArray.toJSON(new Result(true, "操作成功", 200, new Date().getTime())).toString();
    }

    @Override
    public synchronized String medicineSale(@NotBlank String jsonStr) {
        String replace = jsonStr.trim();
        WptpMedicineSale wptpMedicineSale = new WptpMedicineSale();
        BeanUtils.copyProperties(com.alibaba.fastjson.JSONObject.parseObject(replace, WptpMedicineSale.class),wptpMedicineSale);
        Result result = ValidField.checkField(wptpMedicineSale);
        if (!result.isSuccess()) return JSONArray.toJSON(result).toString();
        /**
         * 去除属性首尾空格
         */
        ValidField.trimSpace(wptpMedicineSale);
        /**
         * 校验主机代码
         */
        Result hostCodeResult = hostCodeCheck.checkHostCode(wptpMedicineSale.getHostCode());

        if (!hostCodeResult.isSuccess()) return JSONArray.toJSON(hostCodeResult).toString();
        /**
         * 校验追溯码
         */
        Result traceCodeResult = ValidField.checkTraceCode(wptpMedicineSale.getTraceCode(), "11");
        if (!traceCodeResult.isSuccess()) return JSONArray.toJSON(traceCodeResult).toString();
        /**
         * 校验企业id
         */
        if (iWptpEntInfoService.getEntByEntId(wptpMedicineSale.getEntId()))return new Result().error500("根据企业id查不到企业信息").toString();
        QueryWrapper<WptpMedicineSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sale_number",wptpMedicineSale.getSaleNumber());//判断是否有该流水号的记录
        queryWrapper.eq("deleted","0");
        WptpMedicineSale wptpMedicineSaleInDB = iWptpMedicineSaleService.getBaseMapper().selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpMedicineSaleInDB)) {
            wptpMedicineSaleInDB.setDeleted("1");
            wptpMedicineSaleInDB.setUpdateTime(new Date());
            wptpMedicineSaleInDB.setUpdateBy(wptpMedicineSale.getHostCode());
            iWptpMedicineSaleService.getBaseMapper().updateById(wptpMedicineSaleInDB);
        }
        wptpMedicineSale.setDeleted("0");
        wptpMedicineSale.setCreateTime(new Date());
        wptpMedicineSale.setCreateBy(wptpMedicineSale.getHostCode());
        iWptpMedicineSaleService.save(wptpMedicineSale);
        /**
         * 接收到销售数据就要上传至行业协会
         */
        try {
            guildUpload.upload(wptpMedicineSale.getTraceCode(),"0");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            XhUploadRecord xhUploadRecord = new XhUploadRecord(new Date(),"失败",e.getMessage(),wptpMedicineSale.getTraceCode(),"");
            xhUploadRecordService.getBaseMapper().insert(xhUploadRecord);
        }
        return JSONArray.toJSON(new Result(true, "操作成功", 200, new Date().getTime())).toString();
    }
}
