package org.jeecg.modules.demo.trace.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.baseinfo.entity.WptpBase;
import org.jeecg.modules.demo.baseinfo.entity.WptpBaseFile;
import org.jeecg.modules.demo.baseinfo.mapper.WptpBaseFileMapper;
import org.jeecg.modules.demo.baseinfo.mapper.WptpBaseMapper;
import org.jeecg.modules.demo.baseinfo.vo.WptpBaseVO;
import org.jeecg.modules.demo.blockmedicinal.entity.WptpBlockMeidicinal;
import org.jeecg.modules.demo.blockmedicinal.mapper.WptpBlockMeidicinalMapper;
import org.jeecg.modules.demo.blockmedicinal.vo.WptpBlockMeidicinalVO;
import org.jeecg.modules.demo.csinfo.entity.WptpCsInfo;
import org.jeecg.modules.demo.csinfo.mapper.WptpCsInfoMapper;
import org.jeecg.modules.demo.csinfo.vo.WptpCsInfoVO;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.mapper.WptpEntInfoMapper;
import org.jeecg.modules.demo.hostcode.entity.WptpHostcode;
import org.jeecg.modules.demo.hostcode.mapper.WptpHostcodeMapper;
import org.jeecg.modules.demo.medicinalinfo.mapper.WptpMedicinalMapper;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstock;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstockFile;
import org.jeecg.modules.demo.medicinebinstock.mapper.WptpMedicineInstockFileMapper;
import org.jeecg.modules.demo.medicinebinstock.mapper.WptpMedicineInstockMapper;
import org.jeecg.modules.demo.medicinebinstock.vo.WptpMedicineInstockVO;
import org.jeecg.modules.demo.medicinebsale.entity.WptpMedicineSale;
import org.jeecg.modules.demo.medicinebsale.mapper.WptpMedicineSaleMapper;
import org.jeecg.modules.demo.medicinebsale.vo.WptpMedicineSaleVO;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantFile;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantInfo;
import org.jeecg.modules.demo.plantinfo.mapper.WptpPlantFileMapper;
import org.jeecg.modules.demo.plantinfo.mapper.WptpPlantInfoMapper;
import org.jeecg.modules.demo.plantinfo.vo.WptpPlantInfoVO;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessInfo;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.mapper.WptpProcessFileMapper;
import org.jeecg.modules.demo.processinfo.mapper.WptpProcessInfoMapper;
import org.jeecg.modules.demo.processinfo.mapper.WptpProcessMaterialMapper;
import org.jeecg.modules.demo.processinfo.vo.WptpProcessInfoVO;
import org.jeecg.modules.demo.sale.entity.WptpSale;
import org.jeecg.modules.demo.sale.mapper.WptpSaleMapper;
import org.jeecg.modules.demo.trace.service.ConvertEntityToVOService;
import org.jeecg.modules.demo.trace.service.TraceBaseDataService;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstock;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstockFile;
import org.jeecg.modules.demo.ypbinstock.mapper.WptpYpbInstockFileMapper;
import org.jeecg.modules.demo.ypbinstock.mapper.WptpYpbInstockMapper;
import org.jeecg.modules.demo.ypbinstock.vo.WptpYpbInstockVO;
import org.jeecg.modules.demo.ypbsale.entity.WptpYpbSale;
import org.jeecg.modules.demo.ypbsale.mapper.WptpYpbSaleMapper;
import org.jeecg.modules.demo.ypbsale.vo.WptpYpbSaleVO;
import org.jeecg.modules.demo.yppack.entity.WptpYpPack;
import org.jeecg.modules.demo.yppack.mapper.WptpYpPackMapper;
import org.jeecg.modules.demo.yppack.vo.WptpYpPackVO;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcess;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcessFile;
import org.jeecg.modules.demo.ypprocess.mapper.WptpYpProcessFileMapper;
import org.jeecg.modules.demo.ypprocess.mapper.WptpYpProcessMapper;
import org.jeecg.modules.demo.ypprocess.vo.WptpYpProcessVO;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstock;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstockFile;
import org.jeecg.modules.demo.ypprocessinstock.mapper.WptpYpInstockFileMapper;
import org.jeecg.modules.demo.ypprocessinstock.mapper.WptpYpInstockMapper;
import org.jeecg.modules.demo.ypprocessinstock.vo.WptpYpInstockVO;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSale;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSaleFile;
import org.jeecg.modules.demo.ypprocesssale.mapper.WptpYpSaleFileMapper;
import org.jeecg.modules.demo.ypprocesssale.mapper.WptpYpSaleMapper;
import org.jeecg.modules.demo.ypprocesssale.vo.WptpYpSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TraceBaseDataServiceImpl implements TraceBaseDataService {
    @Autowired
    private ConvertEntityToVOService convertEntityToVOService;
    @Autowired
    private WptpSaleMapper wptpSaleMapper;
    @Autowired
    private WptpCsInfoMapper wptpCsInfoMapper;
    @Autowired
    private WptpProcessInfoMapper wptpProcessInfoMapper;
    @Autowired
    private WptpMedicinalMapper wptpMedicinalMapper;
    @Autowired
    private WptpEntInfoMapper wptpEntInfoMapper;
    @Autowired
    private WptpBaseMapper wptpBaseMapper;
    @Autowired
    private WptpBlockMeidicinalMapper wptpBlockMeidicinalMapper;
    @Autowired
    private WptpProcessMaterialMapper wptpProcessMaterialMapper;
    @Autowired
    private WptpPlantInfoMapper wptpPlantInfoMapper;
    @Autowired
    private WptpMedicineSaleMapper wptpMedicineSaleMapper;
    @Autowired
    private WptpMedicineInstockMapper wptpMedicineInstockMapper;
    @Autowired
    private WptpYpSaleMapper wptpYpSaleMapper;
    @Autowired
    private WptpYpPackMapper wptpYpPackMapper;
    @Autowired
    private WptpYpProcessMapper wptpYpProcessMapper;
    @Autowired
    private WptpYpInstockMapper wptpYpInstockMapper;
    @Autowired
    private WptpYpbSaleMapper wptpYpbSaleMapper;
    @Autowired
    private WptpYpbInstockMapper wptpYpbInstockMapper;
    @Autowired
    private WptpProcessFileMapper wptpProcessFileMapper;
    @Autowired
    private WptpMedicineInstockFileMapper wptpMedicineInstockFileMapper;
    @Autowired
    private WptpYpProcessFileMapper wptpYpProcessFileMapper;
    @Autowired
    private WptpYpInstockFileMapper wptpYpInstockFileMapper;
    @Autowired
    private WptpYpbInstockFileMapper wptpYpbInstockFileMapper;
    @Autowired
    private WptpYpSaleFileMapper wptpYpSaleFileMapper;
    @Autowired
    private WptpPlantFileMapper wptpPlantFileMapper;
    @Autowired
    private WptpBaseFileMapper wptpBaseFileMapper;
    @Autowired
    private WptpHostcodeMapper hostcodeMapper;
    @Override
    public WptpCsInfoVO getCsInfoVO(String medicineBatch) {
        QueryWrapper<WptpCsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cs_no",medicineBatch.trim());
        queryWrapper.eq("deleted","0");
        WptpCsInfo wptpCsInfo = wptpCsInfoMapper.selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpCsInfo))return null;
        return convertEntityToVOService.handleCsInfo(wptpCsInfo);
    }

    @Override
    public List<WptpPlantInfoVO> listPlantInfoVO(String medicineBatch) {
        QueryWrapper<WptpPlantInfo> wptpPlantInfoQueryWrapper = new QueryWrapper<>();
        wptpPlantInfoQueryWrapper.eq("block_medicinal_id",medicineBatch.trim());
        wptpPlantInfoQueryWrapper.eq("deleted","0");
        wptpPlantInfoQueryWrapper.orderByAsc("operate_time");
        List<WptpPlantInfo> wptpPlantInfos = wptpPlantInfoMapper.selectList(wptpPlantInfoQueryWrapper);
        return convertEntityToVOService.handlePlantInfoList(wptpPlantInfos);
    }

    @Override
    public List<WptpPlantFile> listPlantFiles(String plantId) {
        QueryWrapper<WptpPlantFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_id",plantId.trim());
        queryWrapper.eq("deleted","0");
        List<WptpPlantFile> wptpPlantFiles = wptpPlantFileMapper.selectList(queryWrapper);
        if (wptpPlantFiles.isEmpty())return null;
        return wptpPlantFiles;
    }

    @Override
    public WptpSale getSale(String traceCode) {
        QueryWrapper<WptpSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trace_code",traceCode.trim());
        queryWrapper.eq("deleted","0");
        WptpSale wptpSale = wptpSaleMapper.selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpSale))return null;
        return wptpSale;
    }

    @Override
    public WptpProcessInfoVO getProcessInfoVO(String medicineBatch) {
        QueryWrapper<WptpProcessInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("process_no",medicineBatch.trim());
        queryWrapper.eq("deleted","0");
        WptpProcessInfo wptpProcessInfo = wptpProcessInfoMapper.selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpProcessInfo))return null;
        return convertEntityToVOService.handleProcessInfo(wptpProcessInfo);
    }

    @Override
    public List<WptpProcessFile> listProcessFiles(String processNo) {
        QueryWrapper<WptpProcessFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_id",processNo.trim());
        queryWrapper.eq("deleted","0");
        List<WptpProcessFile> wptpProcessFileList = wptpProcessFileMapper.selectList(queryWrapper);
        if (wptpProcessFileList.isEmpty())return null;
        return wptpProcessFileList;
    }

    @Override
    public List<WptpProcessMaterial> listProcessMaterials(String processNo) {
        QueryWrapper<WptpProcessMaterial> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("process_no",processNo.trim());
        queryWrapper.eq("deleted","0");
        List<WptpProcessMaterial> wptpProcessMaterialList = wptpProcessMaterialMapper.selectList(queryWrapper);
        if (wptpProcessMaterialList.isEmpty())return null;
        return wptpProcessMaterialList;
    }
    @Override
    public List<WptpProcessMaterial> listProcessMaterialsByCsNo(String csNo) {
        QueryWrapper<WptpProcessMaterial> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cs_batch",csNo.trim());
        queryWrapper.eq("deleted","0");
        List<WptpProcessMaterial> wptpProcessMaterialList = wptpProcessMaterialMapper.selectList(queryWrapper);
        if (wptpProcessMaterialList.isEmpty())return null;
        return wptpProcessMaterialList;
    }
    @Override
    public WptpBlockMeidicinalVO getBlockMeidicinalVO(String blockMedicinalId) {
        QueryWrapper<WptpBlockMeidicinal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("block_medicinal_id",blockMedicinalId.trim());
        queryWrapper.eq("deleted","0");
        WptpBlockMeidicinal wptpBlockMeidicinal = wptpBlockMeidicinalMapper.selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpBlockMeidicinal))return null;
        return convertEntityToVOService.handleBlockMeidicinal(wptpBlockMeidicinal);
    }

    @Override
    public WptpBaseVO getWptpBaseVO(String baseCode) {
        QueryWrapper<WptpBase> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("base_code",baseCode.trim());
        queryWrapper.eq("deleted","0");
        WptpBase wptpBase = wptpBaseMapper.selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpBase))return null;
        return convertEntityToVOService.handleBase(wptpBase);
    }
    @Override
    public List<WptpBaseFile> getBaseFile(String baseCode,String type) {
        QueryWrapper<WptpBaseFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_id",baseCode.trim());
        queryWrapper.eq("deleted","0");
        queryWrapper.eq("type",type);
        List<WptpBaseFile>  wptpBaseFiles = wptpBaseFileMapper.selectList(queryWrapper);
        return wptpBaseFiles;
    }
    @Override
    public WptpMedicineInstockVO getMedicineInstockVO(String instockNumber) {
        QueryWrapper<WptpMedicineInstock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instock_number",instockNumber.trim());
        queryWrapper.eq("deleted","0");
        WptpMedicineInstock wptpMedicineInstock = wptpMedicineInstockMapper.selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpMedicineInstock))return null;
        return convertEntityToVOService.handleMedicineInstock(wptpMedicineInstock);
    }

    @Override
    public List<WptpMedicineInstockFile> listWptpMedicineInstockFiles(String instockNumber) {
        QueryWrapper<WptpMedicineInstockFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_id",instockNumber.trim());
        queryWrapper.eq("deleted","0");
        List<WptpMedicineInstockFile> wptpMedicineInstockFileList = wptpMedicineInstockFileMapper.selectList(queryWrapper);
        return wptpMedicineInstockFileList;
    }

    @Override
    public WptpMedicineSaleVO getMedicineSaleVO(String traceCode) {
        QueryWrapper<WptpMedicineSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trace_code",traceCode.trim());
        queryWrapper.eq("deleted","0");
        WptpMedicineSale wptpMedicineSale = wptpMedicineSaleMapper.selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpMedicineSale))return null;
        return convertEntityToVOService.handleMedicineSale(wptpMedicineSale);
    }

    @Override
    public WptpYpInstockVO getYpInstockVO(String instockNumber) {
        QueryWrapper<WptpYpInstock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instock_number",instockNumber.trim());
        queryWrapper.eq("deleted","0");
        WptpYpInstock wptpYpInstock= wptpYpInstockMapper.selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpYpInstock))return null;
        return convertEntityToVOService.handleYpInstock(wptpYpInstock);
    }

    @Override
    public List<WptpYpInstockFile> listWptpYpInstockFiles(String instockNumber) {
        QueryWrapper<WptpYpInstockFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_id",instockNumber.trim());
        queryWrapper.eq("deleted","0");
        List<WptpYpInstockFile> wptpYpInstockFileList = wptpYpInstockFileMapper.selectList(queryWrapper);
        return wptpYpInstockFileList;
    }

    @Override
    public WptpYpProcessVO getYpProcessVO(String processNo) {
        QueryWrapper<WptpYpProcess> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("process_no",processNo.trim());
        queryWrapper.eq("deleted","0");
        WptpYpProcess wptpYpProcess= wptpYpProcessMapper.selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpYpProcess))return null;
        return convertEntityToVOService.handleYpProcess(wptpYpProcess);
    }

    @Override
    public List<WptpYpProcessFile> listWptpYpProcessFiles(String processNo) {
        QueryWrapper<WptpYpProcessFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_id",processNo.trim());
        queryWrapper.eq("deleted","0");
        List<WptpYpProcessFile> wptpYpProcessFileList = wptpYpProcessFileMapper.selectList(queryWrapper);
        return wptpYpProcessFileList;
    }

    @Override
    public WptpYpPackVO getYpPackVO(String packNo) {
        QueryWrapper<WptpYpPack> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pack_no",packNo.trim());
        queryWrapper.eq("deleted","0");
        WptpYpPack wptpYpPack = wptpYpPackMapper.selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpYpPack))return null;
        return convertEntityToVOService.handleYpPack(wptpYpPack);
    }

    @Override
    public WptpYpSaleVO getYpSaleVO(String traceCode) {
      /*  QueryWrapper<WptpYpSale> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trace_code",traceCode);
        queryWrapper.eq("deleted","0");
        WptpYpSale wptpYpSale = wptpYpSaleMapper.selectOne(queryWrapper);
        if (oConvertUtils.isEmpty(wptpYpSale))return null;
        return convertEntityToVOService.handleYpSale(wptpYpSale);*/

        ConcurrentHashMap<String,Object> paramMap =new ConcurrentHashMap();
        paramMap.put("trace_code",traceCode.trim());
        paramMap.put("deleted","0");
        List<WptpYpSale>   wptpYpSales = wptpYpSaleMapper.selectByMap(paramMap);
        if (!wptpYpSales.isEmpty())return convertEntityToVOService.handleYpSale(wptpYpSales.get(0));
        paramMap.clear();
        paramMap.put("xh_trace_code",traceCode.trim());
        paramMap.put("deleted","0");
        List<WptpYpSale>   wptpYpSalesXH = wptpYpSaleMapper.selectByMap(paramMap);
        if (!wptpYpSalesXH.isEmpty())return convertEntityToVOService.handleYpSale(wptpYpSalesXH.get(0));
        return null;
    }

    @Override
    public List<WptpYpSaleFile> listWptpYpSaleFiles(String saleNumber) {
        QueryWrapper<WptpYpSaleFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_id",saleNumber.trim());
        queryWrapper.eq("deleted","0");
        List<WptpYpSaleFile> wptpYpSaleFileList = wptpYpSaleFileMapper.selectList(queryWrapper);
        return wptpYpSaleFileList;
    }

    @Override
    public WptpYpbInstockVO getYpbInstockVO(String instockNumber) {
        QueryWrapper<WptpYpbInstock> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("instock_number",instockNumber.trim());
        queryWrapper.eq("deleted","0");
        List<WptpYpbInstock> wptpYpbInstockList = wptpYpbInstockMapper.selectList(queryWrapper);
        if (wptpYpbInstockList.isEmpty())return null;
        return convertEntityToVOService.handleYpbInstock(wptpYpbInstockList.get(0));
    }

    @Override
    public List<WptpYpbInstockFile> listWptpYpbInstockFiles(String instockNo) {
        QueryWrapper<WptpYpbInstockFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_id",instockNo.trim());
        queryWrapper.eq("deleted","0");
        List<WptpYpbInstockFile> wptpYpbInstockFileList = wptpYpbInstockFileMapper.selectList(queryWrapper);
        return wptpYpbInstockFileList;
    }

    @Override
    public WptpYpbSaleVO getYpbSaleVO(String traceCode) {
        ConcurrentHashMap<String,Object> paramMap =new ConcurrentHashMap();
        paramMap.put("trace_code",traceCode.trim());
        paramMap.put("deleted","0");
        List<WptpYpbSale>   wptpYpbSales = wptpYpbSaleMapper.selectByMap(paramMap);
        if (!wptpYpbSales.isEmpty())return convertEntityToVOService.handleYpbSale(wptpYpbSales.get(0));
        paramMap.clear();
        paramMap.put("xh_trace_code",traceCode.trim());
        paramMap.put("deleted","0");
        List<WptpYpbSale>  wptpYpbSalesXH = wptpYpbSaleMapper.selectByMap(paramMap);
        if (!wptpYpbSalesXH.isEmpty())return convertEntityToVOService.handleYpbSale(wptpYpbSalesXH.get(0));
        return null;
    }
@Override
    public String getEntNameByHostCode(String hostCode){
        QueryWrapper<WptpHostcode> queryWrapper = new QueryWrapper<>();
        String trim = hostCode.trim();
        queryWrapper.eq("host_code",trim);
        WptpHostcode wptpHostcode = hostcodeMapper.selectOne(queryWrapper);
        if (!oConvertUtils.isEmpty(wptpHostcode)){
            QueryWrapper<WptpEntInfo> entInfoQueryWrapper = new QueryWrapper<>();
            entInfoQueryWrapper.eq("ent_id",wptpHostcode.getEntId());
            entInfoQueryWrapper.eq("deleted","0");
            WptpEntInfo wptpEntInfo = wptpEntInfoMapper.selectOne(entInfoQueryWrapper);
            if (!oConvertUtils.isEmpty(wptpEntInfo))return wptpEntInfo.getEntName();
        }
        return null;
    }
}
