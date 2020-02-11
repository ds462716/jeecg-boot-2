package org.jeecg.modules.demo.blockinfo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.baseinfo.entity.WptpBase;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseService;
import org.jeecg.modules.demo.blockinfo.entity.WptpBlockFile;
import org.jeecg.modules.demo.blockinfo.entity.WptpBlockInfo;
import org.jeecg.modules.demo.blockinfo.service.IWptpBlockFileService;
import org.jeecg.modules.demo.blockinfo.service.IWptpBlockInfoService;
import org.jeecg.modules.demo.blockinfo.vo.WptpBlockInfoPage;
import org.jeecg.modules.demo.blockinfo.vo.WptpBlockInfoVO;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.service.IWptpEntInfoService;
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
 * @Description: 地块表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/blockinfo/wptpBlockInfo")
@Slf4j
public class WptpBlockInfoController {
	@Autowired
	private IWptpBlockInfoService wptpBlockInfoService;
	@Autowired
	private IWptpBlockFileService wptpBlockFileService;
	 @Autowired
	 private IWptpEntInfoService iWptpEntInfoService;
	 @Autowired
	 private IWptpBaseService iWptpBaseService;
	/**
	  * 分页列表查询
	 * @param wptpBlockInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<WptpBlockInfoVO>> queryPageList(WptpBlockInfo wptpBlockInfo,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		QueryWrapper<WptpBlockInfo> queryWrapper = QueryGenerator.initQueryWrapper(wptpBlockInfo, req.getParameterMap());
		Page<WptpBlockInfo> page = new Page<WptpBlockInfo>(pageNo, pageSize);
		queryWrapper.eq("deleted","0");
		IPage<WptpBlockInfo> pageList = wptpBlockInfoService.page(page, queryWrapper);
		return handlePageData(pageList);
	}
	
	/**
	  *   添加
	 * @param wptpBlockInfoPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<WptpBlockInfo> add(@RequestBody WptpBlockInfoPage wptpBlockInfoPage,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpBlockInfo> result = new Result<WptpBlockInfo>();
		try {
			WptpBlockInfo wptpBlockInfo = new WptpBlockInfo();
			BeanUtils.copyProperties(wptpBlockInfoPage, wptpBlockInfo);
			wptpBlockInfo.setDeleted("0");
			wptpBlockInfo.setCreateBy(username);
			wptpBlockInfoService.saveMain(wptpBlockInfo, wptpBlockInfoPage.getWptpBlockFileList());
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param wptpBlockInfoPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<WptpBlockInfo> edit(@RequestBody WptpBlockInfoPage wptpBlockInfoPage,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpBlockInfo> result = new Result<WptpBlockInfo>();
		WptpBlockInfo wptpBlockInfo = new WptpBlockInfo();
		BeanUtils.copyProperties(wptpBlockInfoPage, wptpBlockInfo);
		WptpBlockInfo wptpBlockInfoEntity = wptpBlockInfoService.getById(wptpBlockInfo.getId());
		if(wptpBlockInfoEntity==null) {
			result.error500("未找到对应实体");
		}else {
			wptpBlockInfo.setUpdateBy(username);
			boolean ok = wptpBlockInfoService.updateById(wptpBlockInfo);
			wptpBlockInfoService.updateMain(wptpBlockInfo, wptpBlockInfoPage.getWptpBlockFileList());
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
			wptpBlockInfoService.delMain(id);
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
	public Result<WptpBlockInfo> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<WptpBlockInfo> result = new Result<WptpBlockInfo>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.wptpBlockInfoService.delBatchMain(Arrays.asList(ids.split(",")));
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
	public Result<WptpBlockInfo> queryById(@RequestParam(name="id",required=true) String id) {
		Result<WptpBlockInfo> result = new Result<WptpBlockInfo>();
		WptpBlockInfo wptpBlockInfo = wptpBlockInfoService.getById(id);
		if(wptpBlockInfo==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(wptpBlockInfo);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryWptpBlockFileByMainId")
	public Result<List<WptpBlockFile>> queryWptpBlockFileListByMainId(@RequestParam(name="id",required=true) String id) {
		Result<List<WptpBlockFile>> result = new Result<List<WptpBlockFile>>();
		List<WptpBlockFile> wptpBlockFileList = wptpBlockFileService.selectByMainId(id);
		result.setResult(wptpBlockFileList);
		result.setSuccess(true);
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param wptpBlockInfo
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, WptpBlockInfo wptpBlockInfo) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<WptpBlockInfo> queryWrapper = QueryGenerator.initQueryWrapper(wptpBlockInfo, request.getParameterMap());
      List<WptpBlockInfo> queryList = wptpBlockInfoService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<WptpBlockInfo> wptpBlockInfoList = new ArrayList<WptpBlockInfo>();
      if(oConvertUtils.isEmpty(selections)) {
    	  wptpBlockInfoList = queryList;
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  wptpBlockInfoList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }
	  // Step.2 组装pageList
      List<WptpBlockInfoPage> pageList = new ArrayList<WptpBlockInfoPage>();
      for (WptpBlockInfo main : wptpBlockInfoList) {
          WptpBlockInfoPage vo = new WptpBlockInfoPage();
          BeanUtils.copyProperties(main, vo);
          List<WptpBlockFile> wptpBlockFileList = wptpBlockFileService.selectByMainId(main.getId());
          vo.setWptpBlockFileList(wptpBlockFileList);
          pageList.add(vo);
      }
      // Step.3 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
	  String username = JwtUtil.getUserNameByToken(request);
      mv.addObject(NormalExcelConstants.FILE_NAME, "地块表列表");
      mv.addObject(NormalExcelConstants.CLASS, WptpBlockInfoPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("地块表列表数据", "导出人:"+username, "导出信息"));
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
              List<WptpBlockInfoPage> list = ExcelImportUtil.importExcel(file.getInputStream(), WptpBlockInfoPage.class, params);
              for (WptpBlockInfoPage page : list) {
                  WptpBlockInfo po = new WptpBlockInfo();
                  BeanUtils.copyProperties(page, po);
                  wptpBlockInfoService.saveMain(po, page.getWptpBlockFileList());
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
	 private Result<IPage<WptpBlockInfoVO>> handlePageData(IPage<WptpBlockInfo> pageList){

		 Result<IPage<WptpBlockInfoVO>> result = new Result<IPage<WptpBlockInfoVO>>();
		 Map<String, Object> map = new HashMap<>();//保存查询条件
		 IPage<WptpBlockInfoVO> wptpBlockInfoVOIPage = new Page<>();//需要展示的分页数据

		 wptpBlockInfoVOIPage.setPages(pageList.getPages());
		 wptpBlockInfoVOIPage.setCurrent(pageList.getCurrent());
		 wptpBlockInfoVOIPage.setSize(pageList.getSize());
		 wptpBlockInfoVOIPage.setTotal(pageList.getTotal());
		 List<WptpBlockInfo> wptpBlockInfoList = pageList.getRecords();
		 List<WptpBlockInfoVO> wptpBlockInfoVOList = new ArrayList<WptpBlockInfoVO>();


		 /**
		  * 把查出来的实体类，属性值copy到展示类VO里面
		  */
		 for (int i = 0; i < wptpBlockInfoList.size(); i++) {
			 WptpBlockInfo wptpBlockInfo = wptpBlockInfoList.get(i);
			 WptpBlockInfoVO wptpBlockInfoVO =new WptpBlockInfoVO();
			 BeanUtils.copyProperties(wptpBlockInfo,wptpBlockInfoVO);
			 map.put("ent_id",wptpBlockInfo.getEntId());
			 List<WptpEntInfo> wptpEntInfos = iWptpEntInfoService.getBaseMapper().selectByMap(map);
			 if (!wptpEntInfos.isEmpty())wptpBlockInfoVO.setEntName(wptpEntInfos.get(0).getEntName());
			 map.clear();
			 map.put("base_code",wptpBlockInfo.getBaseCode());
			 List<WptpBase> wptpBases = iWptpBaseService.getBaseMapper().selectByMap(map);
			 if (!wptpBases.isEmpty())wptpBlockInfoVO.setBaseName(wptpBases.get(0).getBaseName());
			 map.clear();
			 wptpBlockInfoVOList.add(wptpBlockInfoVO);
		 }
		 wptpBlockInfoVOIPage.setRecords(wptpBlockInfoVOList);
		 result.setSuccess(true);
		 result.setResult(wptpBlockInfoVOIPage);
		 return result;
	 }
}
