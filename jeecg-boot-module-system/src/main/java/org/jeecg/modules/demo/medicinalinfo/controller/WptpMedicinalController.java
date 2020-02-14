package org.jeecg.modules.demo.medicinalinfo.controller;

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
import org.jeecg.modules.demo.index.vo.MedicinalVO;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicineFile;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicineFileService;
import org.jeecg.modules.demo.medicinalinfo.vo.WptpMedicinalPage;
import org.jeecg.modules.demo.xhUploadRecord.XhUploadRecord;
import org.jeecg.modules.demo.xhUploadRecord.XhUploadRecordService;
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
 * @Description: 中药材品种
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/medicinalinfo/wptpMedicinal")
@Slf4j
@Api(tags="药材信息")
public class WptpMedicinalController {
	@Autowired
	private IWptpMedicinalService wptpMedicinalService;
	@Autowired
	private IWptpMedicineFileService wptpMedicineFileService;
	@Autowired
	private XhUploadRecordService xhUploadRecordService;

	/**
	  * 分页列表查询
	 * @param wptpMedicinal
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<WptpMedicinal>> queryPageList(WptpMedicinal wptpMedicinal,
													  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													  HttpServletRequest req) {
		String flag=req.getParameter("flag");
		Result<IPage<WptpMedicinal>> result = new Result<IPage<WptpMedicinal>>();
		QueryWrapper<WptpMedicinal> queryWrapper = QueryGenerator.initQueryWrapper(wptpMedicinal, req.getParameterMap());
		Page<WptpMedicinal> page = new Page<WptpMedicinal>(pageNo, pageSize);
		queryWrapper.eq("deleted","0");
		queryWrapper.eq("flag",flag);
		IPage<WptpMedicinal> pageList = wptpMedicinalService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param wptpMedicinalPage
	 * @return
	 */
	@PostMapping(value = "/add")
	@ApiOperation(value = "添加药材", notes = "添加药材")
	public Result<WptpMedicinal> add(@RequestBody WptpMedicinalPage wptpMedicinalPage,HttpServletRequest request) {
		Result<WptpMedicinal> result = new Result<WptpMedicinal>();
		try {
			String username = JwtUtil.getUserNameByToken(request);
			WptpMedicinal wptpMedicinal = new WptpMedicinal();
			BeanUtils.copyProperties(wptpMedicinalPage, wptpMedicinal);
			QueryWrapper<WptpMedicinal> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("name", wptpMedicinal.getName());
			queryWrapper.eq("flag", wptpMedicinal.getFlag());
			WptpMedicinal wptpMedicinalInDB = wptpMedicinalService.getBaseMapper().selectOne(queryWrapper);
			if (!oConvertUtils.isEmpty(wptpMedicinalInDB))return result.error500("该品种已经存在!");
			wptpMedicinal.setDeleted("0");
			wptpMedicinal.setCreateBy(username);
			wptpMedicinal.setCreateTime(new Date());
			String medicineCode="";
			if (wptpMedicinalPage.getFlag().equals("0")) {
				String descMedicineCode = wptpMedicinalService.getDescMedicineCode();//数据库中最大的一个药材编码
				medicineCode = oConvertUtils.completionString(descMedicineCode);//药材编码加1
				wptpMedicinal.setMedicinalCode(medicineCode);
			}
			List<WptpMedicineFile> wptpMedicineFileList = wptpMedicinalPage.getWptpMedicineFileList();
			for (WptpMedicineFile w:
				 wptpMedicineFileList) {
				if (oConvertUtils.isEmpty(w.getPath()))continue;
				w.setDeleted("0");
				w.setCreateBy(username);
				w.setCreateTime(new Date());
				w.setMainId(medicineCode);
				wptpMedicineFileService.save(w);
			}
			wptpMedicinalService.saveMain(wptpMedicinal,null);
			/**
			 * 上传到行业协会
			 */
			XhUploadRecord xhUploadRecord = new XhUploadRecord(new Date(),"",wptpMedicinalService.uploadXh(wptpMedicinal),"成品或者原药","");
			xhUploadRecordService.getBaseMapper().insert(xhUploadRecord);

			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	/**
	 *  上传药材到行业协会
	 * @param wptpMedicinalPage
	 * @return
	 */
	@PostMapping(value = "/uploadXh")
	@ApiOperation(value = "上传药材到行业协会", notes = "上传药材到行业协会")
	public Result uploadXh(@RequestBody String id) throws Exception {
		QueryWrapper<WptpMedicinal> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id",id);
		WptpMedicinal wptpMedicinal = wptpMedicinalService.getBaseMapper().selectOne(queryWrapper);
		if (!oConvertUtils.isEmpty(wptpMedicinal))
		{
			String result = wptpMedicinalService.uploadXh(wptpMedicinal);
			XhUploadRecord xhUploadRecord = new XhUploadRecord(new Date(),"",result,"成品或者原药","");
			xhUploadRecordService.getBaseMapper().insert(xhUploadRecord);
			return new  Result<>().success("上传成功");
		}
		return new  Result<>().error500("上传失败");
	}
	/**
	  *  编辑
	 * @param wptpMedicinalPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<WptpMedicinal> edit(@RequestBody WptpMedicinalPage wptpMedicinalPage,HttpServletRequest request) {
		Result<WptpMedicinal> result = new Result<WptpMedicinal>();
		WptpMedicinal wptpMedicinal = new WptpMedicinal();
		BeanUtils.copyProperties(wptpMedicinalPage, wptpMedicinal);
		String username = JwtUtil.getUserNameByToken(request);
		WptpMedicinal wptpMedicinalEntity = wptpMedicinalService.getById(wptpMedicinal.getId());
		if(wptpMedicinalEntity==null) {
			result.error500("未找到对应实体");
		}else {
			wptpMedicinal.setUpdateBy(username);
			wptpMedicinalService.updateById(wptpMedicinal);
			QueryWrapper<WptpMedicineFile> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("main_id",wptpMedicinal.getMedicinalCode());
			queryWrapper.eq("deleted","0");
			List<WptpMedicineFile> wptpEntInfoList = wptpMedicineFileService.getBaseMapper().selectList(queryWrapper);
			for (WptpMedicineFile w:wptpEntInfoList){
				w.setDeleted("1");
				w.setUpdateBy(username);
				w.setUpdateTime(new Date());
				wptpMedicineFileService.getBaseMapper().updateById(w);
			}
			List<WptpMedicineFile> wptpMedicineFileList = wptpMedicinalPage.getWptpMedicineFileList();
			if (!wptpMedicineFileList.isEmpty()) {
				for (WptpMedicineFile wptpMedicineFile:
						wptpMedicineFileList) {
					if (oConvertUtils.isEmpty(wptpMedicineFile.getPath()))continue;
					wptpMedicineFile.setDeleted("0");
					wptpMedicineFile.setUpdateBy(username);
					wptpMedicineFile.setUpdateTime(new Date());
					wptpMedicineFileService.getBaseMapper().insert(wptpMedicineFile);
				}
			}
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
			wptpMedicinalService.delMain(id);
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
	public Result<WptpMedicinal> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<WptpMedicinal> result = new Result<WptpMedicinal>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.wptpMedicinalService.delBatchMain(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<WptpMedicinal> queryById(@RequestParam(name="id",required=true) String id) {
		Result<WptpMedicinal> result = new Result<WptpMedicinal>();
		WptpMedicinal wptpMedicinal = wptpMedicinalService.getById(id);
		if(wptpMedicinal==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(wptpMedicinal);
			result.setSuccess(true);
		}
		return result;
	}
	/**
	 * 通过mainid查询
	 * @param mainid
	 * @return
	 */
	@GetMapping(value = "/queryByMainid")
	public Result<WptpMedicinal> queryByMainid(@RequestParam(name="medicinalCode",required=true) String medicinalCode) {
		Result<WptpMedicinal> result = new Result<WptpMedicinal>();
		QueryWrapper<WptpMedicinal> wptpMedicinalQueryWrapper = new QueryWrapper<>();
		wptpMedicinalQueryWrapper.eq("medicinal_code",medicinalCode);
		WptpMedicinal wptpMedicinal = wptpMedicinalService.getBaseMapper().selectOne(wptpMedicinalQueryWrapper);
		if(wptpMedicinal==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(wptpMedicinal);
			result.setSuccess(true);
		}
		return result;
	}
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "通过id查询药材文件", notes = "通过id查询药材文件")
	@GetMapping(value = "/queryWptpMedicineFileByMainId")
	public Result<List<WptpMedicineFile>> queryWptpMedicineFileListByMainId(@RequestParam(name="id",required=true) String id) {
		Result<List<WptpMedicineFile>> result = new Result<List<WptpMedicineFile>>();
		WptpMedicinal wptpMedicinal = wptpMedicinalService.getBaseMapper().selectById(id);
		if (oConvertUtils.isEmpty(wptpMedicinal))return new Result().error500("未找到药材信息");
		QueryWrapper<WptpMedicineFile> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("main_id",wptpMedicinal.getMedicinalCode());
		queryWrapper.eq("deleted","0");
		List<WptpMedicineFile> wptpMedicineFileList = wptpMedicineFileService.getBaseMapper().selectList(queryWrapper);
		result.setResult(wptpMedicineFileList);
		result.setSuccess(true);
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, WptpMedicinal wptpMedicinal) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<WptpMedicinal> queryWrapper = QueryGenerator.initQueryWrapper(wptpMedicinal, request.getParameterMap());
      List<WptpMedicinal> queryList = wptpMedicinalService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<WptpMedicinal> wptpMedicinalList = new ArrayList<WptpMedicinal>();
      if(oConvertUtils.isEmpty(selections)) {
    	  wptpMedicinalList = queryList;
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  wptpMedicinalList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }
	  // Step.2 组装pageList
      List<WptpMedicinalPage> pageList = new ArrayList<WptpMedicinalPage>();
      for (WptpMedicinal main : wptpMedicinalList) {
          WptpMedicinalPage vo = new WptpMedicinalPage();
          BeanUtils.copyProperties(main, vo);
          List<WptpMedicineFile> wptpMedicineFileList = wptpMedicineFileService.selectByMainId(main.getId());
          vo.setWptpMedicineFileList(wptpMedicineFileList);
          pageList.add(vo);
      }
      // Step.3 AutoPoi 导出Excel
	  String username = JwtUtil.getUserNameByToken(request);
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "中药材品种列表");
      mv.addObject(NormalExcelConstants.CLASS, WptpMedicinalPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("中药材品种列表数据", "导出人:"+username, "导出信息"));
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
              List<WptpMedicinalPage> list = ExcelImportUtil.importExcel(file.getInputStream(), WptpMedicinalPage.class, params);
              for (WptpMedicinalPage page : list) {
                  WptpMedicinal po = new WptpMedicinal();
                  BeanUtils.copyProperties(page, po);
                  wptpMedicinalService.saveMain(po, page.getWptpMedicineFileList());
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
	 * 根据药材编码查找图片
	 * @return
	 */
	@ApiOperation(value = "根据药材编码查找图片", notes = "根据药材编码查找图片")
	@GetMapping(value = "/listMedicineImageByCode")
	public Result<MedicinalVO>  listMedicineImageByCode(String medicinalCode){
		QueryWrapper<WptpMedicinal> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("medicinal_code",medicinalCode);
		queryWrapper.eq("deleted","0");
		queryWrapper.eq("audit_status","2");
		WptpMedicinal wptpMedicinal = wptpMedicinalService.getBaseMapper().selectOne(queryWrapper);
		if (oConvertUtils.isEmpty(wptpMedicinal))return new Result().error500("未找到相关药材");
		MedicinalVO medicinalVO = new MedicinalVO();
		BeanUtils.copyProperties(wptpMedicinal,medicinalVO);
			HashMap<String, Object> queryMap = new HashMap<>();
			queryMap.put("main_id",wptpMedicinal.getMedicinalCode());
			List<WptpMedicineFile> wptpMedicineFiles = wptpMedicineFileService.getBaseMapper().selectByMap(queryMap);
			if (!wptpMedicineFiles.isEmpty()) {
				medicinalVO.setMedicinalImages(wptpMedicineFiles.get(0).getPath());
			}
			queryMap.clear();
		return new Result<>(true,"操作成功",200,medicinalVO,new Date().getTime());
	}
}
