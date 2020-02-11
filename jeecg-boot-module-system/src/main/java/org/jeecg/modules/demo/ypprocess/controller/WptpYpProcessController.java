package org.jeecg.modules.demo.ypprocess.controller;

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
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcess;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcessFile;
import org.jeecg.modules.demo.ypprocess.service.IWptpYpProcessFileService;
import org.jeecg.modules.demo.ypprocess.service.IWptpYpProcessService;
import org.jeecg.modules.demo.ypprocess.vo.WptpYpProcessPage;
import org.jeecg.modules.demo.ypprocess.vo.WptpYpProcessVO;
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
 * @Description: 饮片加工表
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/ypprocess/wptpYpProcess")
@Slf4j
@Api(tags="饮片加工-饮片加工")
public class WptpYpProcessController {
	@Autowired
	private IWptpYpProcessService wptpYpProcessService;
	@Autowired
	private IWptpYpProcessFileService wptpYpProcessFileService;
	 @Autowired
	 private IWptpMedicinalService iWptpMedicinalService;
	/**
	  * 分页列表查询
	 * @param wptpYpProcess
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<WptpYpProcessVO>> queryPageList(WptpYpProcess wptpYpProcess,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		QueryWrapper<WptpYpProcess> queryWrapper = QueryGenerator.initQueryWrapper(wptpYpProcess, req.getParameterMap());
		Page<WptpYpProcess> page = new Page<WptpYpProcess>(pageNo, pageSize);
		queryWrapper.eq("deleted","0");
		IPage<WptpYpProcess> pageList = wptpYpProcessService.page(page, queryWrapper);
		return handlePageData(pageList);
	}
	
	/**
	  *   添加
	 * @param wptpYpProcessPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<WptpYpProcess> add(@RequestBody WptpYpProcessPage wptpYpProcessPage,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpYpProcess> result = new Result<WptpYpProcess>();
		try {
			WptpYpProcess wptpYpProcess = new WptpYpProcess();
			BeanUtils.copyProperties(wptpYpProcessPage, wptpYpProcess);
			wptpYpProcess.setDeleted("0");
			wptpYpProcess.setCreateBy(username);
			wptpYpProcessService.saveMain(wptpYpProcess, wptpYpProcessPage.getWptpYpProcessFileList());
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param wptpYpProcessPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<WptpYpProcess> edit(@RequestBody WptpYpProcessPage wptpYpProcessPage,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpYpProcess> result = new Result<WptpYpProcess>();
		WptpYpProcess wptpYpProcess = new WptpYpProcess();
		BeanUtils.copyProperties(wptpYpProcessPage, wptpYpProcess);
		WptpYpProcess wptpYpProcessEntity = wptpYpProcessService.getById(wptpYpProcess.getId());
		if(wptpYpProcessEntity==null) {
			result.error500("未找到对应实体");
		}else {
			wptpYpProcess.setUpdateBy(username);
			boolean ok = wptpYpProcessService.updateById(wptpYpProcess);
			wptpYpProcessService.updateMain(wptpYpProcess, wptpYpProcessPage.getWptpYpProcessFileList());
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
			wptpYpProcessService.delMain(id);
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
	public Result<WptpYpProcess> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<WptpYpProcess> result = new Result<WptpYpProcess>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.wptpYpProcessService.delBatchMain(Arrays.asList(ids.split(",")));
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
	public Result<WptpYpProcess> queryById(@RequestParam(name="id",required=true) String id) {
		Result<WptpYpProcess> result = new Result<WptpYpProcess>();
		WptpYpProcess wptpYpProcess = wptpYpProcessService.getById(id);
		if(wptpYpProcess==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(wptpYpProcess);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "通过id查询饮片加工-饮片加工文件", notes = "通过id查询饮片加工-饮片加工文件")
	@GetMapping(value = "/queryWptpYpProcessFileByMainId")
	public Result<List<WptpYpProcessFile>> queryWptpYpProcessFileListByMainId(@RequestParam(name="id",required=true) String id) {
		Result<List<WptpYpProcessFile>> result = new Result();
		WptpYpProcess wptpYpProcess = wptpYpProcessService.getBaseMapper().selectById(id);
		if (oConvertUtils.isEmpty(wptpYpProcess))return new Result().error500("未找到饮片加工信息");
		QueryWrapper<WptpYpProcessFile> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("main_id",wptpYpProcess.getProcessNo());
		queryWrapper.eq("deleted","0");
		List<WptpYpProcessFile> wptpYpProcessFileList = wptpYpProcessFileService.getBaseMapper().selectList(queryWrapper);
		result.setResult(wptpYpProcessFileList);
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
  public ModelAndView exportXls(HttpServletRequest request, WptpYpProcess wptpYpProcess) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<WptpYpProcess> queryWrapper = QueryGenerator.initQueryWrapper(wptpYpProcess, request.getParameterMap());
      List<WptpYpProcess> queryList = wptpYpProcessService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<WptpYpProcess> wptpYpProcessList = new ArrayList<WptpYpProcess>();
      if(oConvertUtils.isEmpty(selections)) {
    	  wptpYpProcessList = queryList;
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  wptpYpProcessList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }
	  // Step.2 组装pageList
      List<WptpYpProcessPage> pageList = new ArrayList<WptpYpProcessPage>();
      for (WptpYpProcess main : wptpYpProcessList) {
          WptpYpProcessPage vo = new WptpYpProcessPage();
          BeanUtils.copyProperties(main, vo);
          List<WptpYpProcessFile> wptpYpProcessFileList = wptpYpProcessFileService.selectByMainId(main.getId());
          vo.setWptpYpProcessFileList(wptpYpProcessFileList);
          pageList.add(vo);
      }
      // Step.3 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
	  String username = JwtUtil.getUserNameByToken(request);
      mv.addObject(NormalExcelConstants.FILE_NAME, "饮片加工表列表");
      mv.addObject(NormalExcelConstants.CLASS, WptpYpProcessPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("饮片加工表列表数据", "导出人:"+username, "导出信息"));
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
              List<WptpYpProcessPage> list = ExcelImportUtil.importExcel(file.getInputStream(), WptpYpProcessPage.class, params);
              for (WptpYpProcessPage page : list) {
                  WptpYpProcess po = new WptpYpProcess();
                  BeanUtils.copyProperties(page, po);
                  wptpYpProcessService.saveMain(po, page.getWptpYpProcessFileList());
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
	 private Result<IPage<WptpYpProcessVO>> handlePageData(IPage<WptpYpProcess> pageList){

		 Result<IPage<WptpYpProcessVO>> result = new Result<IPage<WptpYpProcessVO>>();
		 Map<String, Object> map = new HashMap<>();//保存查询条件
		 IPage<WptpYpProcessVO> wptpYpProcessVOIPage = new Page<>();//需要展示的分页数据

		 wptpYpProcessVOIPage.setPages(pageList.getPages());
		 wptpYpProcessVOIPage.setCurrent(pageList.getCurrent());
		 wptpYpProcessVOIPage.setSize(pageList.getSize());
		 wptpYpProcessVOIPage.setTotal(pageList.getTotal());
		 List<WptpYpProcess> wptpYpProcessList = pageList.getRecords();
		 List<WptpYpProcessVO> wptpYpProcessVOList = new ArrayList<WptpYpProcessVO>();


		 /**
		  * 把查出来的实体类，属性值copy到展示类VO里面
		  */
		 for (int i = 0; i < wptpYpProcessList.size(); i++) {
			 WptpYpProcess wptpYpProcess = wptpYpProcessList.get(i);
			 WptpYpProcessVO wptpYpProcessVO =new WptpYpProcessVO();
			 BeanUtils.copyProperties(wptpYpProcess,wptpYpProcessVO);

			 map.put("medicinal_code",wptpYpProcess.getCategoryCode());
			 List<WptpMedicinal> wptpMedicinals = iWptpMedicinalService.getBaseMapper().selectByMap(map);
			 if (!wptpMedicinals.isEmpty())wptpYpProcessVO.setMedicinalName(wptpMedicinals.get(0).getName());
			 wptpYpProcessVOList.add(wptpYpProcessVO);
			 map.clear();

		 }
		 wptpYpProcessVOIPage.setRecords(wptpYpProcessVOList);
		 result.setSuccess(true);
		 result.setResult(wptpYpProcessVOIPage);
		 return result;
	 }
}
