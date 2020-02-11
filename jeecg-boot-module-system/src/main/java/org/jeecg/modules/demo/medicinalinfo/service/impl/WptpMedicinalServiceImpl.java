package org.jeecg.modules.demo.medicinalinfo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.util.HttpUtils;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.api.webservice.guild.bean.MedicineXh;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicineFile;
import org.jeecg.modules.demo.medicinalinfo.mapper.WptpMedicinalMapper;
import org.jeecg.modules.demo.medicinalinfo.mapper.WptpMedicineFileMapper;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * @Description: 中药材品种
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Service
public class WptpMedicinalServiceImpl extends ServiceImpl<WptpMedicinalMapper, WptpMedicinal> implements IWptpMedicinalService {
	private static final String APP_KEY="be6gntvg";
	private static final String APP_SECRET="f6c7ef91417e8ac0";
	private static final String IMG_URL_PREIX="http://180.168.130.217:9010/img";//图片路径前缀
	private static final String TOKEN_URL="http://indu.sdrhny.com/mq_service/token/getTokenInfo";//token获取
	private static final String ACCESS_TOKE="0C2B1870091E1CFA58206D6DC90A6029";
	private static final String PRODUCT_URL="http://indu.sdrhny.com/mq_service/linkDataProducer/send/productTemplateInfo";//成品上传地址
	@Autowired
	private WptpMedicinalMapper wptpMedicinalMapper;
	@Autowired
	private WptpMedicineFileMapper wptpMedicineFileMapper;
	
	@Override
	@Transactional
	public void saveMain(WptpMedicinal wptpMedicinal, List<WptpMedicineFile> wptpMedicineFileList) {
		wptpMedicinalMapper.insert(wptpMedicinal);
		if(wptpMedicineFileList!=null && wptpMedicineFileList.size()>0) {
			for(WptpMedicineFile entity:wptpMedicineFileList) {
				//外键设置
				entity.setMainId(wptpMedicinal.getId());
				wptpMedicineFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(WptpMedicinal wptpMedicinal,List<WptpMedicineFile> wptpMedicineFileList) {
		wptpMedicinalMapper.updateById(wptpMedicinal);
		
		//1.先删除子表数据
		wptpMedicineFileMapper.deleteByMainId(wptpMedicinal.getId());
		
		//2.子表数据重新插入
		if(wptpMedicineFileList!=null && wptpMedicineFileList.size()>0) {
			for(WptpMedicineFile entity:wptpMedicineFileList) {
				//外键设置
				entity.setMainId(wptpMedicinal.getId());
				wptpMedicineFileMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		wptpMedicineFileMapper.deleteByMainId(id);
		wptpMedicinalMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			wptpMedicineFileMapper.deleteByMainId(id.toString());
			wptpMedicinalMapper.deleteById(id);
		}
	}

	@Override
	public String getDescMedicineCode() {
		return wptpMedicinalMapper.getDescMedicineCode();
	}

	@Override
	public String uploadXh(WptpMedicinal wptpMedicinal) throws Exception {

		MedicineXh medicineXh = new MedicineXh();
		medicineXh.setAppKey(APP_KEY);
		medicineXh.setAppToken(getToken());
		medicineXh.setProductId(wptpMedicinal.getMedicinalCode());
		if ("1".equals(wptpMedicinal.getFlag())){
			medicineXh.setProductType("2");
			medicineXh.setPlaceOrigin(wptpMedicinal.getFunc());
		}else {
			medicineXh.setProductType("1");
		}
		medicineXh.setProductName(wptpMedicinal.getName());
		medicineXh.setExhibitionName(wptpMedicinal.getName());
		medicineXh.setManufacturingEnterprise("上海医药");
		List<WptpMedicineFile> wptpMedicineFileList = wptpMedicineFileMapper.selectByMainId(wptpMedicinal.getMedicinalCode());
		StringBuilder sb = new StringBuilder();
		for (WptpMedicineFile w:
				wptpMedicineFileList ) {
			sb.append("|");
			sb.append(IMG_URL_PREIX+w.getPath());
		}
		String imgUrlArray = sb.toString();
		if (!oConvertUtils.isEmpty(imgUrlArray)) medicineXh.setImage(imgUrlArray.substring(1,imgUrlArray.length()));
		/**
		 * 上传
		 */
		String param = JSON.toJSONString(medicineXh);
		String result = HttpUtils.doPost(PRODUCT_URL, param);
		return result;
	}
	/**
	 * 获得token
	 * @return
	 */
	private String getToken(){
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("appKey",APP_KEY);
		paramMap.put("appSecret",APP_SECRET);
		paramMap.put("time",new Date().getTime()+"");
		String result = HttpUtils.sendGet(TOKEN_URL, paramMap);
		JSONObject jsonObject = JSONObject.parseObject(result);
		String dataStr = jsonObject.get("data").toString();
		JSONObject dataJson = JSONObject.parseObject(dataStr);
		if (dataJson.get("flag").toString().equals("fasle"))return "";
		String accessToken = dataJson.get("accessToken").toString();
		if (oConvertUtils.isEmpty(accessToken))return "";
		return accessToken;
	}

}
