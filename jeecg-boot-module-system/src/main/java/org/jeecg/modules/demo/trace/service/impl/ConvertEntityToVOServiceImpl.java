package org.jeecg.modules.demo.trace.service.impl;

import org.jeecg.modules.demo.baseinfo.entity.WptpBase;
import org.jeecg.modules.demo.baseinfo.mapper.WptpBaseMapper;
import org.jeecg.modules.demo.baseinfo.vo.WptpBaseVO;
import org.jeecg.modules.demo.blockinfo.entity.WptpBlockInfo;
import org.jeecg.modules.demo.blockinfo.mapper.WptpBlockInfoMapper;
import org.jeecg.modules.demo.blockmedicinal.entity.WptpBlockMeidicinal;
import org.jeecg.modules.demo.blockmedicinal.mapper.WptpBlockMeidicinalMapper;
import org.jeecg.modules.demo.blockmedicinal.vo.WptpBlockMeidicinalVO;
import org.jeecg.modules.demo.csinfo.entity.WptpCsInfo;
import org.jeecg.modules.demo.csinfo.mapper.WptpCsInfoMapper;
import org.jeecg.modules.demo.csinfo.vo.WptpCsInfoVO;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.mapper.WptpEntInfoMapper;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.mapper.WptpMedicinalMapper;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstock;
import org.jeecg.modules.demo.medicinebinstock.vo.WptpMedicineInstockVO;
import org.jeecg.modules.demo.medicinebsale.entity.WptpMedicineSale;
import org.jeecg.modules.demo.medicinebsale.vo.WptpMedicineSaleVO;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantInfo;
import org.jeecg.modules.demo.plantinfo.mapper.WptpPlantInfoMapper;
import org.jeecg.modules.demo.plantinfo.vo.WptpPlantInfoVO;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessInfo;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.mapper.WptpProcessInfoMapper;
import org.jeecg.modules.demo.processinfo.mapper.WptpProcessMaterialMapper;
import org.jeecg.modules.demo.processinfo.vo.WptpProcessInfoVO;
import org.jeecg.modules.demo.province.entity.WptpCity;
import org.jeecg.modules.demo.province.entity.WptpDistrict;
import org.jeecg.modules.demo.province.entity.WptpProvince;
import org.jeecg.modules.demo.province.mapper.WptpCityMapper;
import org.jeecg.modules.demo.province.mapper.WptpDistrictMapper;
import org.jeecg.modules.demo.province.mapper.WptpProvinceMapper;
import org.jeecg.modules.demo.sale.entity.WptpSale;
import org.jeecg.modules.demo.sale.mapper.WptpSaleMapper;
import org.jeecg.modules.demo.sale.vo.WptpSaleVO;
import org.jeecg.modules.demo.trace.service.ConvertEntityToVOService;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstock;
import org.jeecg.modules.demo.ypbinstock.vo.WptpYpbInstockVO;
import org.jeecg.modules.demo.ypbsale.entity.WptpYpbSale;
import org.jeecg.modules.demo.ypbsale.vo.WptpYpbSaleVO;
import org.jeecg.modules.demo.yppack.entity.WptpYpPack;
import org.jeecg.modules.demo.yppack.vo.WptpYpPackVO;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcess;
import org.jeecg.modules.demo.ypprocess.vo.WptpYpProcessVO;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstock;
import org.jeecg.modules.demo.ypprocessinstock.vo.WptpYpInstockVO;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSale;
import org.jeecg.modules.demo.ypprocesssale.vo.WptpYpSaleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
public class ConvertEntityToVOServiceImpl implements ConvertEntityToVOService {
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
    private WptpBlockInfoMapper wptpBlockInfoMapper;
    @Autowired
    private WptpCityMapper wptpCityMapper;
    @Autowired
    private WptpProvinceMapper wptpProvinceMapper;
    @Autowired
    private WptpDistrictMapper wptpDistrictMapper;

    /**
     * 把作业信息实体类转为VO
     *
     * @param pageList
     * @return
     */
    public List<WptpPlantInfoVO> handlePlantInfoList(List<WptpPlantInfo> entityList) {
        ConcurrentHashMap<String, Object> paramMap = new ConcurrentHashMap();
        List<WptpPlantInfoVO> wptpPlantInfoVOList = new ArrayList<WptpPlantInfoVO>();

        for (int i = 0; i < entityList.size(); i++) {
            WptpPlantInfo wptpPlantInfo = entityList.get(i);
            WptpPlantInfoVO wptpPlantInfoVO = new WptpPlantInfoVO();
            BeanUtils.copyProperties(wptpPlantInfo, wptpPlantInfoVO);

            paramMap.put("block_medicinal_id", wptpPlantInfo.getBlockMedicinalId());
            List<WptpBlockMeidicinal> wptpBlockMeidicinals = wptpBlockMeidicinalMapper.selectByMap(paramMap);
            if (!wptpBlockMeidicinals.isEmpty()) {
                wptpPlantInfoVO.setMedicinalCode(wptpBlockMeidicinals.get(0).getMedicinalCode());
                wptpPlantInfoVO.setEntId(wptpBlockMeidicinals.get(0).getEntId());
                wptpPlantInfoVO.setBaseCode(wptpBlockMeidicinals.get(0).getBaseCode());
                wptpPlantInfoVO.setBlockCode(wptpBlockMeidicinals.get(0).getBlockCode());
                wptpPlantInfoVO.setBaseAdmin(wptpBlockMeidicinals.get(0).getBaseAdmin());
                wptpPlantInfoVO.setFileTime(wptpBlockMeidicinals.get(0).getFileTime());
            }
            paramMap.clear();
            paramMap.put("ent_id", wptpPlantInfoVO.getEntId());
            List<WptpEntInfo> wptpEntInfos = wptpEntInfoMapper.selectByMap(paramMap);
            if (!wptpEntInfos.isEmpty()) wptpPlantInfoVO.setEntName(wptpEntInfos.get(0).getEntName());
            wptpPlantInfoVOList.add(wptpPlantInfoVO);
        }
        return wptpPlantInfoVOList;
    }

    /**
     * 把加工实体类转为VO
     *
     * @param pageList
     * @return
     */
    public WptpProcessInfoVO handleProcessInfo(WptpProcessInfo wptpProcessInfo) {
        ConcurrentHashMap<String, Object> paramMap = new ConcurrentHashMap();
        paramMap.put("process_no", wptpProcessInfo.getProcessNo());
        WptpProcessInfoVO wptpProcessInfoVO = new WptpProcessInfoVO();
        List<WptpProcessMaterial> wptpProcessMaterials = wptpProcessMaterialMapper.selectByMap(paramMap);
        BeanUtils.copyProperties(wptpProcessInfo, wptpProcessInfoVO);
        wptpProcessInfoVO.setBatch(wptpProcessMaterials);
        return wptpProcessInfoVO;
    }
    /**
     * 把采收批次实体类转为VO
     * @param pageList
     * @return
     */
   /* public List<WptpCsInfoVO> handleCsInfoList(List<WptpCsInfo> entityList){
        ConcurrentHashMap<String,Object> paramMap =new ConcurrentHashMap();
        List<WptpCsInfoVO> wptpCsInfoVOList = new ArrayList<WptpCsInfoVO>();
        for (int i = 0; i < entityList.size(); i++) {
            WptpCsInfo wptpCsInfo = entityList.get(i);
            WptpCsInfoVO wptpCsInfoVO =new WptpCsInfoVO();
            BeanUtils.copyProperties(wptpCsInfo,wptpCsInfoVO);
            paramMap.put("medicinal_code",wptpCsInfo.getMedicinalCode());
            List<WptpMedicinal> wptpMedicinals = wptpMedicinalMapper.selectByMap(paramMap);
            if (!wptpMedicinals.isEmpty())wptpCsInfoVO.setMedicinalName(wptpMedicinals.get(0).getName());
            paramMap.clear();
            paramMap.put("ent_id",wptpCsInfo.getEntId());
            List<WptpEntInfo> wptpEntInfos = wptpEntInfoMapper.selectByMap(paramMap);
            if (!wptpEntInfos.isEmpty())wptpCsInfoVO.setEntName(wptpEntInfos.get(0).getEntName());
            paramMap.clear();
            paramMap.put("base_code",wptpCsInfo.getBaseCode());
            List<WptpBase> wptpBases = wptpBaseMapper.selectByMap(paramMap);
            if (!wptpBases.isEmpty())wptpCsInfoVO.setBaseName(wptpBases.get(0).getBaseName());
            paramMap.clear();
            paramMap.put("block_medicinal_id",wptpCsInfo.getBlockMedicinalId());
            List<WptpBlockMeidicinal> wptpBlockMeidicinals = wptpBlockMeidicinalMapper.selectByMap(paramMap);
            if (!wptpBlockMeidicinals.isEmpty())
            {
                wptpCsInfoVO.setMedicinalCode(wptpBlockMeidicinals.get(0).getMedicinalCode());
                wptpCsInfoVO.setEntId(wptpBlockMeidicinals.get(0).getEntId());
                wptpCsInfoVO.setBaseCode(wptpBlockMeidicinals.get(0).getBaseCode());
                wptpCsInfoVO.setBlockCode(wptpBlockMeidicinals.get(0).getBlockCode());
                wptpCsInfoVO.setBaseAdmin(wptpBlockMeidicinals.get(0).getBaseAdmin());
                wptpCsInfoVO.setFileTime(wptpBlockMeidicinals.get(0).getFileTime());
            }
            paramMap.clear();
            wptpCsInfoVOList.add(wptpCsInfoVO);
        }
        return wptpCsInfoVOList;
    }*/

    /**
     * 把档案实体类转为VO
     *
     * @param pageList
     * @return
     */
    public WptpBlockMeidicinalVO handleBlockMeidicinal(WptpBlockMeidicinal wptpBlockMeidicinal) {

        ConcurrentHashMap<String, Object> paramMap = new ConcurrentHashMap();//保存查询条件


        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        WptpBlockMeidicinalVO wptpBlockMeidicinalVO = new WptpBlockMeidicinalVO();
        BeanUtils.copyProperties(wptpBlockMeidicinal, wptpBlockMeidicinalVO);
        paramMap.put("medicinal_code", wptpBlockMeidicinal.getMedicinalCode());
        List<WptpMedicinal> wptpMedicinals = wptpMedicinalMapper.selectByMap(paramMap);
        if (!wptpMedicinals.isEmpty()) wptpBlockMeidicinalVO.setMedicinalName(wptpMedicinals.get(0).getName());
        paramMap.clear();
        paramMap.put("ent_id", wptpBlockMeidicinal.getEntId());
        List<WptpEntInfo> wptpEntInfos = wptpEntInfoMapper.selectByMap(paramMap);
        if (!wptpEntInfos.isEmpty()) wptpBlockMeidicinalVO.setEntName(wptpEntInfos.get(0).getEntName());
        paramMap.clear();
        paramMap.put("base_code", wptpBlockMeidicinal.getBaseCode());
        List<WptpBase> wptpBases = wptpBaseMapper.selectByMap(paramMap);
        if (!wptpBases.isEmpty()) wptpBlockMeidicinalVO.setBaseName(wptpBases.get(0).getBaseName());
        paramMap.clear();
        paramMap.put("block_code", wptpBlockMeidicinal.getBlockCode());
        List<WptpBlockInfo> wptpBlockInfos = wptpBlockInfoMapper.selectByMap(paramMap);
        if (!wptpBlockInfos.isEmpty()) {
            wptpBlockMeidicinalVO.setBaseArea(wptpBlockInfos.get(0).getBaseArea());
            wptpBlockMeidicinalVO.setGpsLatitude(wptpBlockInfos.get(0).getGpsLatitude());
            wptpBlockMeidicinalVO.setGpsLongitude(wptpBlockInfos.get(0).getGpsLongitude());
        }
        return wptpBlockMeidicinalVO;
    }

    /**
     * 基地信息
     *
     * @param pageList
     * @return
     */
    public WptpBaseVO handleBase(WptpBase wptpBase) {

        Map<String, Object> map = new HashMap<>();//保存查询条件


        /**
         * 把表中省市区的编码字段，替换成省市区的名字,企业和公司id替换成从企业表里查询的企业名字
         * 把查出来的实体类WptpBase，属性值copy到展示类VO里面
         */
        WptpBaseVO wptpBaseVO = new WptpBaseVO();
        BeanUtils.copyProperties(wptpBase, wptpBaseVO);
        map.put("admi_code", wptpBase.getProvince());
        List<WptpProvince> wptpProvinces = wptpProvinceMapper.selectByMap(map);
        if (!wptpProvinces.isEmpty()) wptpBaseVO.setProvinceName(wptpProvinces.get(0).getName());
        map.clear();
        map.put("admi_code", wptpBase.getCity());
        List<WptpCity> wptpCities = wptpCityMapper.selectByMap(map);
        if (!wptpCities.isEmpty()) wptpBaseVO.setCityName(wptpCities.get(0).getCityName());
        map.clear();
        map.put("admi_code", wptpBase.getArea());
        List<WptpDistrict> wptpDistricts = wptpDistrictMapper.selectByMap(map);
        if (!wptpDistricts.isEmpty()) wptpBaseVO.setDistrictName(wptpDistricts.get(0).getDisName());
        map.clear();
        map.put("ent_id", wptpBase.getEntId());
        List<WptpEntInfo> wptpEntInfos = wptpEntInfoMapper.selectByMap(map);
        if (!wptpEntInfos.isEmpty()) wptpBaseVO.setEntName(wptpEntInfos.get(0).getEntName());
        map.clear();
        map.put("ent_id", wptpBase.getBaseEnt());
        List<WptpEntInfo> wptpEntInfoBase = wptpEntInfoMapper.selectByMap(map);
        if (!wptpEntInfoBase.isEmpty()) wptpBaseVO.setUpperEntName(wptpEntInfoBase.get(0).getEntName());
        return wptpBaseVO;
    }

    /**
     * 把药材经营-药材销售实体转为VO
     *
     * @param pageList
     * @return
     */
    public WptpMedicineSaleVO handleMedicineSale(WptpMedicineSale wptpMedicineSale) {

        Map<String, Object> map = new HashMap<>();//保存查询条件
        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        WptpMedicineSaleVO wptpMedicineSaleVO = new WptpMedicineSaleVO();
        BeanUtils.copyProperties(wptpMedicineSale, wptpMedicineSaleVO);

        map.put("name", wptpMedicineSale.getMedicineName());
        List<WptpMedicinal> wptpMedicinals = wptpMedicinalMapper.selectByMap(map);
        if (!wptpMedicinals.isEmpty()) wptpMedicineSaleVO.setMedicinalCode(wptpMedicinals.get(0).getMedicinalCode());
        map.clear();
        map.put("ent_id", wptpMedicineSale.getEntId());
        List<WptpEntInfo> wptpEntInfos = wptpEntInfoMapper.selectByMap(map);
        if (!wptpEntInfos.isEmpty()) wptpMedicineSaleVO.setEntName(wptpEntInfos.get(0).getEntName());
        return wptpMedicineSaleVO;
    }

    /**
     * 把药材经营-药材入库实体类转为VO
     *
     * @param pageList
     * @return
     */
    public WptpMedicineInstockVO handleMedicineInstock(WptpMedicineInstock wptpMedicineInstock) {

        Map<String, Object> map = new HashMap<>();//保存查询条件
        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        WptpMedicineInstockVO wptpMedicineInstockVO = new WptpMedicineInstockVO();
        BeanUtils.copyProperties(wptpMedicineInstock, wptpMedicineInstockVO);

        map.put("name", wptpMedicineInstock.getMedicineName());
        List<WptpMedicinal> wptpMedicinals = wptpMedicinalMapper.selectByMap(map);
        if (!wptpMedicinals.isEmpty()) wptpMedicineInstockVO.setMedicinalCode(wptpMedicinals.get(0).getMedicinalCode());
        map.clear();
        map.put("ent_id", wptpMedicineInstock.getEntId());
        List<WptpEntInfo> wptpEntInfos = wptpEntInfoMapper.selectByMap(map);
        if (!wptpEntInfos.isEmpty()) wptpMedicineInstockVO.setEntName(wptpEntInfos.get(0).getEntName());
        return wptpMedicineInstockVO;
    }

    /**
     * 把饮片加工-饮片销售实体类转为VO
     *
     * @param pageList
     * @return
     */
    public WptpYpSaleVO handleYpSale(WptpYpSale wptpYpSale) {

        Map<String, Object> map = new HashMap<>();//保存查询条件


        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        WptpYpSaleVO wptpYpSaleVO = new WptpYpSaleVO();
        BeanUtils.copyProperties(wptpYpSale, wptpYpSaleVO);

        map.put("medicinal_code", wptpYpSale.getCategoryCode());
        List<WptpMedicinal> wptpMedicinals = wptpMedicinalMapper.selectByMap(map);
        if (!wptpMedicinals.isEmpty()) wptpYpSaleVO.setMedicinalName(wptpMedicinals.get(0).getName());
        wptpYpSaleVO.setMedicinalCode(wptpYpSale.getCategoryCode());
        map.clear();
        map.put("ent_id", wptpYpSale.getEntId());
        List<WptpEntInfo> wptpEntInfos = wptpEntInfoMapper.selectByMap(map);
        if (!wptpEntInfos.isEmpty()) wptpYpSaleVO.setEntName(wptpEntInfos.get(0).getEntName());
        map.clear();

        return wptpYpSaleVO;
    }

    /**
     * 把饮片加工-饮片包装实体类转为VO
     *
     * @param pageList
     * @return
     */
    public WptpYpPackVO handleYpPack(WptpYpPack wptpYpPack) {

        Map<String, Object> map = new HashMap<>();//保存查询条件


        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        WptpYpPackVO wptpYpPackVO = new WptpYpPackVO();
        BeanUtils.copyProperties(wptpYpPack, wptpYpPackVO);

        map.put("medicinal_code", wptpYpPack.getCategoryCode());
        List<WptpMedicinal> wptpMedicinals = wptpMedicinalMapper.selectByMap(map);
        if (!wptpMedicinals.isEmpty()) wptpYpPackVO.setMedicinalName(wptpMedicinals.get(0).getName());
        return wptpYpPackVO;
    }

    /**
     * 把饮片加工实体类转为VO
     *
     * @param wptpYpProcess
     * @return
     */
    public WptpYpProcessVO handleYpProcess(WptpYpProcess wptpYpProcess) {

        Map<String, Object> map = new HashMap<>();//保存查询条件


        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        WptpYpProcessVO wptpYpProcessVO = new WptpYpProcessVO();
        BeanUtils.copyProperties(wptpYpProcess, wptpYpProcessVO);

        map.put("medicinal_code", wptpYpProcess.getCategoryCode());
        List<WptpMedicinal> wptpMedicinals = wptpMedicinalMapper.selectByMap(map);
        if (!wptpMedicinals.isEmpty()) wptpYpProcessVO.setMedicinalName(wptpMedicinals.get(0).getName());

        return wptpYpProcessVO;
    }

    /**
     * 把饮片加工-饮片入库实体类转为VO
     *
     * @param pageList
     * @return
     */
    public WptpYpInstockVO handleYpInstock(WptpYpInstock wptpYpInstock) {

        Map<String, Object> map = new HashMap<>();//保存查询条件


        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        WptpYpInstockVO wptpYpInstockVO = new WptpYpInstockVO();
        BeanUtils.copyProperties(wptpYpInstock, wptpYpInstockVO);

        map.put("name", wptpYpInstock.getMaterialName());
        List<WptpMedicinal> wptpMedicinals = wptpMedicinalMapper.selectByMap(map);
        if (!wptpMedicinals.isEmpty()) wptpYpInstockVO.setMedicinalCode(wptpMedicinals.get(0).getMedicinalCode());
        map.clear();
        map.put("ent_id", wptpYpInstock.getEntId());
        List<WptpEntInfo> wptpEntInfos = wptpEntInfoMapper.selectByMap(map);
        if (!wptpEntInfos.isEmpty()) wptpYpInstockVO.setEntName(wptpEntInfos.get(0).getEntName());

        return wptpYpInstockVO;
    }

    /**
     * 把饮片经营-饮片销售实体类转为VO
     *
     * @param pageList
     * @return
     */
    public WptpYpbSaleVO handleYpbSale(WptpYpbSale wptpYpbSale) {

        Map<String, Object> map = new HashMap<>();//保存查询条件


        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        WptpYpbSaleVO wptpYpbSaleVO = new WptpYpbSaleVO();
        BeanUtils.copyProperties(wptpYpbSale, wptpYpbSaleVO);


        map.put("ent_id", wptpYpbSale.getEntId());
        List<WptpEntInfo> wptpEntInfos = wptpEntInfoMapper.selectByMap(map);
        if (!wptpEntInfos.isEmpty()) wptpYpbSaleVO.setEntName(wptpEntInfos.get(0).getEntName());
        return wptpYpbSaleVO;
    }

    /**
     * 把饮片经营-饮片入库实体类转为VO
     *
     * @param pageList
     * @return
     */
    public WptpYpbInstockVO handleYpbInstock(WptpYpbInstock wptpYpbInstock) {

        Map<String, Object> map = new HashMap<>();//保存查询条件
        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */

        WptpYpbInstockVO wptpYpbInstockVO = new WptpYpbInstockVO();
        BeanUtils.copyProperties(wptpYpbInstock, wptpYpbInstockVO);

        map.put("medicinal_code", wptpYpbInstock.getCategoryCode());
        List<WptpMedicinal> wptpMedicinals = wptpMedicinalMapper.selectByMap(map);
        if (!wptpMedicinals.isEmpty()) wptpYpbInstockVO.setMedicinalName(wptpMedicinals.get(0).getName());
        map.clear();
        map.put("ent_id", wptpYpbInstock.getEntId());
        List<WptpEntInfo> wptpEntInfos = wptpEntInfoMapper.selectByMap(map);
        if (!wptpEntInfos.isEmpty()) wptpYpbInstockVO.setEntName(wptpEntInfos.get(0).getEntName());
        return wptpYpbInstockVO;
    }

    public WptpCsInfoVO handleCsInfo(WptpCsInfo wptpCsInfo) {

        Map<String, Object> map = new HashMap<>();//保存查询条件


        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        WptpCsInfoVO wptpCsInfoVO = new WptpCsInfoVO();
        BeanUtils.copyProperties(wptpCsInfo, wptpCsInfoVO);
        map.put("medicinal_code", wptpCsInfo.getMedicinalCode());
        List<WptpMedicinal> wptpMedicinals = wptpMedicinalMapper.selectByMap(map);
        if (!wptpMedicinals.isEmpty()) wptpCsInfoVO.setMedicinalName(wptpMedicinals.get(0).getName());
        map.clear();
        map.put("ent_id", wptpCsInfo.getEntId());
        List<WptpEntInfo> wptpEntInfos = wptpEntInfoMapper.selectByMap(map);
        if (!wptpEntInfos.isEmpty()) wptpCsInfoVO.setEntName(wptpEntInfos.get(0).getEntName());
        map.clear();
        map.put("base_code", wptpCsInfo.getBaseCode());
        List<WptpBase> wptpBases = wptpBaseMapper.selectByMap(map);
        if (!wptpBases.isEmpty()) wptpCsInfoVO.setBaseName(wptpBases.get(0).getBaseName());
        map.clear();
        map.put("block_medicinal_id", wptpCsInfo.getBlockMedicinalId());
        List<WptpBlockMeidicinal> wptpBlockMeidicinals = wptpBlockMeidicinalMapper.selectByMap(map);
        if (!wptpBlockMeidicinals.isEmpty()) {
            wptpCsInfoVO.setMedicinalCode(wptpBlockMeidicinals.get(0).getMedicinalCode());
            /*  wptpCsInfoVO.setEntId(wptpBlockMeidicinals.get(0).getEntId());*/
            wptpCsInfoVO.setBaseCode(wptpBlockMeidicinals.get(0).getBaseCode());
            wptpCsInfoVO.setBlockCode(wptpBlockMeidicinals.get(0).getBlockCode());
            wptpCsInfoVO.setBaseAdmin(wptpBlockMeidicinals.get(0).getBaseAdmin());
            wptpCsInfoVO.setFileTime(wptpBlockMeidicinals.get(0).getFileTime());
        }
        return wptpCsInfoVO;
    }

    @Override
    public WptpSaleVO handleSale(WptpSale wptpSale) {
        Map<String, Object> map = new HashMap<>();//保存查询条件
        /*  Map<String, Object> map = new HashMap<>();//保存查询条件
         *//**
         * 把查出来的实体类，属性值copy到展示类VO里面
         *//*

        WptpSaleVO wptpSaleVO =new WptpSaleVO();
        BeanUtils.copyProperties(wptpSale,wptpSaleVO);

        map.put("medicinal_code",wptpSale.getMedicineName());
        List<WptpMedicinal> wptpMedicinals = wptpMedicinalMapper.selectByMap(map);
        if (!wptpMedicinals.isEmpty())wptpSaleVO.setMedicinalCode(wptpMedicinals.get(0).getMedicinalCode());
*/
        WptpSaleVO wptpSaleVO = new WptpSaleVO();
        BeanUtils.copyProperties(wptpSale, wptpSaleVO);
        map.put("medicinal_code", wptpSale.getMedicineName());
        List<WptpMedicinal> wptpMedicinals = wptpMedicinalMapper.selectByMap(map);
        if (!wptpMedicinals.isEmpty()) {
            wptpSaleVO.setMedicinalCode(wptpMedicinals.get(0).getMedicinalCode());
            wptpSaleVO.setMedicineName(wptpMedicinals.get(0).getName());
        }
        map.clear();
        map.put("ent_id", wptpSale.getEntId());
        List<WptpEntInfo> wptpEntInfos = wptpEntInfoMapper.selectByMap(map);
        if (!wptpEntInfos.isEmpty()) wptpSaleVO.setEntName(wptpEntInfos.get(0).getEntName());


        return wptpSaleVO;
    }


}
