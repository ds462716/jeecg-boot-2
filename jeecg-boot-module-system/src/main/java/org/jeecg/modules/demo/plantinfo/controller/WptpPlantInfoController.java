package org.jeecg.modules.demo.plantinfo.controller;

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
import org.jeecg.modules.demo.blockmedicinal.entity.WptpBlockMeidicinal;
import org.jeecg.modules.demo.blockmedicinal.service.IWptpBlockMeidicinalService;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantFile;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantInfo;
import org.jeecg.modules.demo.plantinfo.service.IWptpPlantFileService;
import org.jeecg.modules.demo.plantinfo.service.IWptpPlantInfoService;
import org.jeecg.modules.demo.plantinfo.vo.WptpPlantInfoPage;
import org.jeecg.modules.demo.plantinfo.vo.WptpPlantInfoVO;
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
 * @Description: 田间作业表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@Api(tags="种植-田间作业")
@RestController
@RequestMapping("/plantinfo/wptpPlantInfo")
@Slf4j
public class WptpPlantInfoController {
	@Autowired
	private IWptpPlantInfoService wptpPlantInfoService;
	@Autowired
	private IWptpPlantFileService wptpPlantFileService;
	@Autowired
	private IWptpBlockMeidicinalService iWptpBlockMeidicinalService;
	
	/**
	  * 分页列表查询
	 * @param wptpPlantInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<WptpPlantInfoVO>> queryPageList(WptpPlantInfo wptpPlantInfo,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		QueryWrapper<WptpPlantInfo> queryWrapper = QueryGenerator.initQueryWrapper(wptpPlantInfo, req.getParameterMap());
		Page<WptpPlantInfo> page = new Page<WptpPlantInfo>(pageNo, pageSize);
		queryWrapper.eq("deleted","0");
		IPage<WptpPlantInfo> pageList = wptpPlantInfoService.page(page, queryWrapper);
		return handlePageData(pageList);
	}
	
	/**
	  *   添加
	 * @param wptpPlantInfoPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<WptpPlantInfo> add(@RequestBody WptpPlantInfoPage wptpPlantInfoPage,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpPlantInfo> result = new Result<WptpPlantInfo>();
		try {
			WptpPlantInfo wptpPlantInfo = new WptpPlantInfo();
			BeanUtils.copyProperties(wptpPlantInfoPage, wptpPlantInfo);
			wptpPlantInfo.setDeleted("0");
			wptpPlantInfo.setCreateBy(username);
			wptpPlantInfoService.saveMain(wptpPlantInfo, wptpPlantInfoPage.getWptpPlantFileList());
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param wptpPlantInfoPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<WptpPlantInfo> edit(@RequestBody WptpPlantInfoPage wptpPlantInfoPage,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpPlantInfo> result = new Result<WptpPlantInfo>();
		WptpPlantInfo wptpPlantInfo = new WptpPlantInfo();
		BeanUtils.copyProperties(wptpPlantInfoPage, wptpPlantInfo);
		WptpPlantInfo wptpPlantInfoEntity = wptpPlantInfoService.getById(wptpPlantInfo.getId());
		if(wptpPlantInfoEntity==null) {
			result.error500("未找到对应实体");
		}else {
			wptpPlantInfo.setUpdateBy(username);
			boolean ok = wptpPlantInfoService.updateById(wptpPlantInfo);
			wptpPlantInfoService.updateMain(wptpPlantInfo, wptpPlantInfoPage.getWptpPlantFileList());
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
			wptpPlantInfoService.delMain(id);
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
	public Result<WptpPlantInfo> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<WptpPlantInfo> result = new Result<WptpPlantInfo>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.wptpPlantInfoService.delBatchMain(Arrays.asList(ids.split(",")));
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
	public Result<WptpPlantInfo> queryById(@RequestParam(name="id",required=true) String id) {
		Result<WptpPlantInfo> result = new Result<WptpPlantInfo>();
		WptpPlantInfo wptpPlantInfo = wptpPlantInfoService.getById(id);
		if(wptpPlantInfo==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(wptpPlantInfo);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "根据mainId查询田间作业文件", notes = "根据mainId查询田间作业文件")
	@GetMapping(value = "/listPlantFileByPlantId")
	public Result<WptpPlantFile> listPlantFileByPlantId(@RequestParam(name="plantId",required=true) String plantId) {
		Result<WptpPlantFile> result = new Result<WptpPlantFile>();
		QueryWrapper<WptpPlantFile> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("main_id",plantId);
		queryWrapper.eq("deleted","0");
		/*queryWrapper.orderByAsc("create_time");*/
		List<WptpPlantFile> wptpPlantFileList = wptpPlantFileService.getBaseMapper().selectList(queryWrapper);
		Collections.reverse(wptpPlantFileList);
		if (wptpPlantFileList.isEmpty())return new Result().error500("无数据");
		return new Result<WptpPlantFile>(true,"操作成功",200,wptpPlantFileList,new Date().getTime());
	}

  /**
      * 导出excel
   *
   * @param request
   * @param wptpPlantInfo
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, WptpPlantInfo wptpPlantInfo) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<WptpPlantInfo> queryWrapper = QueryGenerator.initQueryWrapper(wptpPlantInfo, request.getParameterMap());
      List<WptpPlantInfo> queryList = wptpPlantInfoService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<WptpPlantInfo> wptpPlantInfoList = new ArrayList<WptpPlantInfo>();
      if(oConvertUtils.isEmpty(selections)) {
    	  wptpPlantInfoList = queryList;
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  wptpPlantInfoList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }
	  // Step.2 组装pageList
      List<WptpPlantInfoPage> pageList = new ArrayList<WptpPlantInfoPage>();
      for (WptpPlantInfo main : wptpPlantInfoList) {
          WptpPlantInfoPage vo = new WptpPlantInfoPage();
          BeanUtils.copyProperties(main, vo);
          List<WptpPlantFile> wptpPlantFileList = wptpPlantFileService.selectByMainId(main.getId());
          vo.setWptpPlantFileList(wptpPlantFileList);
          pageList.add(vo);
      }
      // Step.3 AutoPoi 导出Excel
	  String username = JwtUtil.getUserNameByToken(request);
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "田间作业表列表");
      mv.addObject(NormalExcelConstants.CLASS, WptpPlantInfoPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("田间作业表列表数据", "导出人:"+username, "导出信息"));
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
              List<WptpPlantInfoPage> list = ExcelImportUtil.importExcel(file.getInputStream(), WptpPlantInfoPage.class, params);
              for (WptpPlantInfoPage page : list) {
                  WptpPlantInfo po = new WptpPlantInfo();
                  BeanUtils.copyProperties(page, po);
                  wptpPlantInfoService.saveMain(po, page.getWptpPlantFileList());
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
	 private Result<IPage<WptpPlantInfoVO>> handlePageData(IPage<WptpPlantInfo> pageList){

		 Result<IPage<WptpPlantInfoVO>> result = new Result<IPage<WptpPlantInfoVO>>();
		 Map<String, Object> map = new HashMap<>();//保存查询条件
		 IPage<WptpPlantInfoVO> wptpPlantInfoVOIPage = new Page<>();//需要展示的分页数据

		 wptpPlantInfoVOIPage.setPages(pageList.getPages());
		 wptpPlantInfoVOIPage.setCurrent(pageList.getCurrent());
		 wptpPlantInfoVOIPage.setSize(pageList.getSize());
		 wptpPlantInfoVOIPage.setTotal(pageList.getTotal());
		 List<WptpPlantInfo> wptpPlantInfoList = pageList.getRecords();
		 List<WptpPlantInfoVO> wptpPlantInfoVOList = new ArrayList<WptpPlantInfoVO>();


		 /**
		  * 把查出来的实体类，属性值copy到展示类VO里面
		  */
		 for (int i = 0; i < wptpPlantInfoList.size(); i++) {
			 WptpPlantInfo wptpPlantInfo = wptpPlantInfoList.get(i);
			 WptpPlantInfoVO wptpPlantInfoVO =new WptpPlantInfoVO();
			 BeanUtils.copyProperties(wptpPlantInfo,wptpPlantInfoVO);

			 map.put("block_medicinal_id",wptpPlantInfo.getBlockMedicinalId());
			 List<WptpBlockMeidicinal> wptpBlockMeidicinals = iWptpBlockMeidicinalService.getBaseMapper().selectByMap(map);
			 if (!wptpBlockMeidicinals.isEmpty())
			 {
				 wptpPlantInfoVO.setMedicinalCode(wptpBlockMeidicinals.get(0).getMedicinalCode());
				 wptpPlantInfoVO.setEntId(wptpBlockMeidicinals.get(0).getEntId());
				 wptpPlantInfoVO.setBaseCode(wptpBlockMeidicinals.get(0).getBaseCode());
				 wptpPlantInfoVO.setBlockCode(wptpBlockMeidicinals.get(0).getBlockCode());
				 wptpPlantInfoVO.setBaseAdmin(wptpBlockMeidicinals.get(0).getBaseAdmin());
				 wptpPlantInfoVO.setFileTime(wptpBlockMeidicinals.get(0).getFileTime());
			 }
			 map.clear();
			 wptpPlantInfoVOList.add(wptpPlantInfoVO);
		 }
		 wptpPlantInfoVOIPage.setRecords(wptpPlantInfoVOList);
		 result.setSuccess(true);
		 result.setResult(wptpPlantInfoVOIPage);
		 return result;
	 }
}
