package org.jeecg.modules.demo.entinfo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.entinfo.entity.WptpEntFile;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.service.IWptpEntFileService;
import org.jeecg.modules.demo.entinfo.service.IWptpEntInfoService;
import org.jeecg.modules.demo.entinfo.vo.WptpEntInfoPage;
import org.jeecg.modules.demo.entinfo.vo.WptpEntInfoVO;
import org.jeecg.modules.demo.province.entity.WptpCity;
import org.jeecg.modules.demo.province.entity.WptpDistrict;
import org.jeecg.modules.demo.province.entity.WptpProvince;
import org.jeecg.modules.demo.province.service.IWptpCityService;
import org.jeecg.modules.demo.province.service.IWptpDistrictService;
import org.jeecg.modules.demo.province.service.IWptpProvinceService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

 /**
 * @Description: 企业基本信息
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@RestController
@Api(tags="企业信息")
@RequestMapping("/entinfo/wptpEntInfo")
@Slf4j
public class WptpEntInfoController {
	@Autowired
	private IWptpEntInfoService wptpEntInfoService;
	@Autowired
	private IWptpEntFileService wptpEntFileService;
	@Autowired
	private IWptpProvinceService iWptpProvinceService;
	@Autowired
	private IWptpCityService iWptpCityService;
	@Autowired
	private IWptpDistrictService iWptpDistrictService;
	
	/**
	  * 分页列表查询
	 * @param wptpEntInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<WptpEntInfoVO>> queryPageList( WptpEntInfo wptpEntInfo,
									    @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
										@RequestParam(name="pageSize", defaultValue="10")	Integer pageSize,
									  HttpServletRequest req) {
		/*if (wptpEntInfo.getOrgCode()!=null){
			wptpEntInfo.setOrgCode(">= "+wptpEntInfo.getOrgCode());
		}*/
		QueryWrapper<WptpEntInfo> queryWrapper = QueryGenerator.initQueryWrapper(wptpEntInfo, req.getParameterMap());
		/*QueryWrapper<WptpEntInfo> queryWrapper=new QueryWrapper();*/
		Page<WptpEntInfo> page = new Page<WptpEntInfo>(pageNo, pageSize);
		queryWrapper.eq("deleted","0");

/*		if (wptpEntInfo.getOrgCode()!=null){
			queryWrapper.like("org_code",wptpEntInfo.getOrgCode());
		}
		if (wptpEntInfo.getEntPrincipal()!=null){
			queryWrapper.like("ent_principal",wptpEntInfo.getEntPrincipal());
		}*/
		/*queryWrapper.between("create_time","2019-10-15 16:17:04","2019-10-17 16:17:04");*/

		IPage<WptpEntInfo> pageList = wptpEntInfoService.page(page, queryWrapper);
		return handlePageData(pageList);
	}
	
	/**
	  *   添加
	 * @param wptpEntInfoPage
	 * @return
	 */
	@PostMapping(value = "/add")
	@ApiOperation(value = "添加企业", notes = "添加企业")
	public Result<WptpEntInfo> add(@RequestBody WptpEntInfoPage wptpEntInfoPage,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpEntInfo> result = new Result<WptpEntInfo>();
		QueryWrapper<WptpEntInfo> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("ent_name", wptpEntInfoPage.getEntName());
		WptpEntInfo wptpEntInfoInDB = wptpEntInfoService.getBaseMapper().selectOne(queryWrapper);
		if (!oConvertUtils.isEmpty(wptpEntInfoInDB))return result.error500("该企业已经存在!");

		String area = wptpEntInfoPage.getArea();
		String entId = wptpEntInfoService.getEntId(area);//企业编码
		if (oConvertUtils.isEmpty(entId))entId=area+"000";
		int anInt = oConvertUtils.getInt(entId);

		try {
			WptpEntInfo wptpEntInfo = new WptpEntInfo();
			BeanUtils.copyProperties(wptpEntInfoPage, wptpEntInfo);
			wptpEntInfo.setEntId(oConvertUtils.getString(anInt+1));

			wptpEntInfo.setDeleted("0");
			wptpEntInfo.setCreateBy(username);
			wptpEntInfo.setCreateTime(new Date());
			List<WptpEntFile> wptpEntFileList = wptpEntInfoPage.getWptpEntFileList();

			for (WptpEntFile w:
				 wptpEntFileList) {
				if (oConvertUtils.isEmpty(w.getPath()))continue;
				w.setMainId(oConvertUtils.getString(anInt+1));
				w.setCreateBy(username);
				w.setCreateTime(new Date());
				w.setDeleted("0");

				wptpEntFileService.save(w);
			}
			wptpEntInfoService.saveMain(wptpEntInfo, null);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param wptpEntInfoPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<WptpEntInfo> edit(@RequestBody WptpEntInfoPage wptpEntInfoPage,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpEntInfo> result = new Result<WptpEntInfo>();
		WptpEntInfo wptpEntInfo = new WptpEntInfo();
		BeanUtils.copyProperties(wptpEntInfoPage, wptpEntInfo);
		WptpEntInfo wptpEntInfoEntity = wptpEntInfoService.getById(wptpEntInfo.getId());
		if(wptpEntInfoEntity==null) {
			result.error500("未找到对应实体");
		}else {
			wptpEntInfo.setUpdateBy(username);
			wptpEntInfoService.updateById(wptpEntInfo);
			QueryWrapper<WptpEntFile> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("main_id",wptpEntInfo.getEntId());
			queryWrapper.eq("deleted","0");
			List<WptpEntFile> wptpEntInfoList = wptpEntFileService.getBaseMapper().selectList(queryWrapper);
			for (WptpEntFile w:wptpEntInfoList){
				w.setDeleted("1");
				w.setUpdateBy(username);
				w.setUpdateTime(new Date());
				wptpEntFileService.getBaseMapper().updateById(w);
			}

			List<WptpEntFile> wptpEntFileList = wptpEntInfoPage.getWptpEntFileList();
			if (!wptpEntFileList.isEmpty()) {
				for (WptpEntFile wptpEntFile:
						wptpEntFileList) {
					if (oConvertUtils.isEmpty(wptpEntFile.getPath()))continue;
					wptpEntFile.setDeleted("0");
					wptpEntFile.setCreateBy(username);
					wptpEntFile.setCreateTime(new Date());
					wptpEntFileService.getBaseMapper().insert(wptpEntFile);
				}
			}
			/*wptpEntInfoService.updateMain(wptpEntInfo, wptpEntInfoPage.getWptpEntFileList());*/
			result.success("修改成功!");
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			wptpEntInfoService.delMain(id);
		} catch (Exception e) {
			log.error("删除失败",e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<WptpEntInfo> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<WptpEntInfo> result = new Result<WptpEntInfo>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.wptpEntInfoService.delBatchMain(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "通过id查询企业信息", notes = "通过id查询企业信息")
	@GetMapping(value = "/queryById")
	public Result<WptpEntInfoVO> queryById(@RequestParam(name="id",required=true) String id) {
		Result<WptpEntInfoVO> result = new Result<WptpEntInfoVO>();
		Map<String, Object> map = new HashMap<>();//保存查询条件
		WptpEntInfo wptpEntInfo = wptpEntInfoService.getById(id);
		if(wptpEntInfo==null) {
			result.error500("未找到对应实体");
		}else {
			WptpEntInfoVO wptpEntInfoVO =new WptpEntInfoVO();
			BeanUtils.copyProperties(wptpEntInfo,wptpEntInfoVO);
			map.put("admi_code",wptpEntInfo.getProvince());
			List<WptpProvince> wptpProvinces = iWptpProvinceService.getBaseMapper().selectByMap(map);
			if (!wptpProvinces.isEmpty())wptpEntInfoVO.setProvinceName(wptpProvinces.get(0).getName());
			map.clear();
			map.put("admi_code",wptpEntInfo.getCity());
			List<WptpCity> wptpCities = iWptpCityService.getBaseMapper().selectByMap(map);
			if (!wptpCities.isEmpty())wptpEntInfoVO.setCityName(wptpCities.get(0).getCityName());
			map.clear();
			map.put("admi_code",wptpEntInfo.getArea());
			List<WptpDistrict> wptpDistricts = iWptpDistrictService.getBaseMapper().selectByMap(map);
			if (!wptpDistricts.isEmpty())wptpEntInfoVO.setDistrictName(wptpDistricts.get(0).getDisName());
			map.clear();
			result.setResult(wptpEntInfoVO);
			result.setSuccess(true);
			result.setCode(200);
			result.setMessage("操作成功");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "通过id查询文件", notes = "通过id查询文件")
	@GetMapping(value = "/queryWptpEntFileByMainId")
	public Result<List<WptpEntFile>> queryWptpEntFileListByMainId(@RequestParam(name="id",required=true) String id) {
		Result<List<WptpEntFile>> result = new Result<List<WptpEntFile>>();
		QueryWrapper<WptpEntFile> queryWrapper = new QueryWrapper<>();
		WptpEntInfo wptpEntInfo = wptpEntInfoService.getBaseMapper().selectById(id);
		if (oConvertUtils.isEmpty(wptpEntInfo))return new Result().error500("未找到企业信息");
		queryWrapper.eq("main_id",wptpEntInfo.getEntId());
		queryWrapper.eq("deleted","0");
		List<WptpEntFile> wptpEntFileList = wptpEntFileService.getBaseMapper().selectList(queryWrapper);
		result.setResult(wptpEntFileList);
		result.setSuccess(true);
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param wptpEntInfo
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, WptpEntInfo wptpEntInfo) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<WptpEntInfo> queryWrapper = QueryGenerator.initQueryWrapper(wptpEntInfo, request.getParameterMap());
      List<WptpEntInfo> queryList = wptpEntInfoService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<WptpEntInfo> wptpEntInfoList = new ArrayList<WptpEntInfo>();
      if(oConvertUtils.isEmpty(selections)) {
    	  wptpEntInfoList = queryList;
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  wptpEntInfoList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }
	  // Step.2 组装pageList
      List<WptpEntInfoPage> pageList = new ArrayList<WptpEntInfoPage>();
      for (WptpEntInfo main : wptpEntInfoList) {
          WptpEntInfoPage vo = new WptpEntInfoPage();
          BeanUtils.copyProperties(main, vo);
          List<WptpEntFile> wptpEntFileList = wptpEntFileService.selectByMainId(main.getId());
          vo.setWptpEntFileList(wptpEntFileList);
          pageList.add(vo);
      }
      // Step.3 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
	  String username = JwtUtil.getUserNameByToken(request);
      mv.addObject(NormalExcelConstants.FILE_NAME, "企业基本信息列表");
      mv.addObject(NormalExcelConstants.CLASS, WptpEntInfoPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("企业基本信息列表数据", "导出人:"+username, "导出信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
  }

  /**
      * 通过excel导入数据
   *
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<WptpEntInfoPage> list = ExcelImportUtil.importExcel(file.getInputStream(), WptpEntInfoPage.class, params);
              for (WptpEntInfoPage page : list) {
                  WptpEntInfo po = new WptpEntInfo();
                  BeanUtils.copyProperties(page, po);
                  wptpEntInfoService.saveMain(po, page.getWptpEntFileList());
              }
              return Result.ok("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.ok("文件导入失败！");
  }
	 /**
	  * 处理查出来的分页数据，返回一个新的分页数据
	  * @param pageList
	  * @return
	  */
	 private Result<IPage<WptpEntInfoVO>> handlePageData(IPage<WptpEntInfo> pageList){

		 Result<IPage<WptpEntInfoVO>> result = new Result<IPage<WptpEntInfoVO>>();
		 Map<String, Object> map = new HashMap<>();//保存查询条件
		 IPage<WptpEntInfoVO> wptpEntInfoVOIPage = new Page<>();//需要展示的分页数据

		 wptpEntInfoVOIPage.setPages(pageList.getPages());
		 wptpEntInfoVOIPage.setCurrent(pageList.getCurrent());
		 wptpEntInfoVOIPage.setSize(pageList.getSize());
		 wptpEntInfoVOIPage.setTotal(pageList.getTotal());
		 List<WptpEntInfo> wptpEntInfoList = pageList.getRecords();
		 List<WptpEntInfoVO> wptpEntInfoVOList = new ArrayList<WptpEntInfoVO>();


		 /**
		  * 把查出来的实体类，属性值copy到展示类VO里面
		  */
		 for (int i = 0; i < wptpEntInfoList.size(); i++) {
			 WptpEntInfo wptpEntInfo = wptpEntInfoList.get(i);
			 WptpEntInfoVO wptpEntInfoVO =new WptpEntInfoVO();
			 BeanUtils.copyProperties(wptpEntInfo,wptpEntInfoVO);
			 map.put("admi_code",wptpEntInfo.getProvince());
			 List<WptpProvince> wptpProvinces = iWptpProvinceService.getBaseMapper().selectByMap(map);
			 if (!wptpProvinces.isEmpty())wptpEntInfoVO.setProvinceName(wptpProvinces.get(0).getName());
			 map.clear();
			 map.put("admi_code",wptpEntInfo.getCity());
			 List<WptpCity> wptpCities = iWptpCityService.getBaseMapper().selectByMap(map);
			 if (!wptpCities.isEmpty())wptpEntInfoVO.setCityName(wptpCities.get(0).getCityName());
			 map.clear();
			 map.put("admi_code",wptpEntInfo.getArea());
			 List<WptpDistrict> wptpDistricts = iWptpDistrictService.getBaseMapper().selectByMap(map);
			 if (!wptpDistricts.isEmpty())wptpEntInfoVO.setDistrictName(wptpDistricts.get(0).getDisName());
			 map.clear();
			 wptpEntInfoVOList.add(wptpEntInfoVO);
		 }
		 wptpEntInfoVOIPage.setRecords(wptpEntInfoVOList);
		 result.setSuccess(true);
		 result.setResult(wptpEntInfoVOIPage);
		 return result;
	 }
}
