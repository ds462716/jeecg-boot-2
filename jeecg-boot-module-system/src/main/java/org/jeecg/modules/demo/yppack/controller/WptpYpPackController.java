package org.jeecg.modules.demo.yppack.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
import org.jeecg.modules.demo.yppack.entity.WptpYpPack;
import org.jeecg.modules.demo.yppack.service.IWptpYpPackService;
import org.jeecg.modules.demo.yppack.vo.WptpYpPackVO;
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
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

 /**
 * @Description: 饮片包装
 * @Author: jeecg-boot
 * @Date:   2019-10-08
 * @Version: V1.0
 */
@RestController
@RequestMapping("/yppack/wptpYpPack")
@Slf4j
public class WptpYpPackController {
	@Autowired
	private IWptpYpPackService wptpYpPackService;
	 @Autowired
	 private IWptpMedicinalService iWptpMedicinalService;
	
	/**
	  * 分页列表查询
	 * @param wptpYpPack
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<WptpYpPackVO>> queryPageList(WptpYpPack wptpYpPack,
													 @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													 @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													 HttpServletRequest req) {
		QueryWrapper<WptpYpPack> queryWrapper = QueryGenerator.initQueryWrapper(wptpYpPack, req.getParameterMap());
		Page<WptpYpPack> page = new Page<WptpYpPack>(pageNo, pageSize);
		queryWrapper.eq("deleted","0");
		IPage<WptpYpPack> pageList = wptpYpPackService.page(page, queryWrapper);
		return handlePageData(pageList);
	}
	
	/**
	  *   添加
	 * @param wptpYpPack
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<WptpYpPack> add(@RequestBody WptpYpPack wptpYpPack,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpYpPack> result = new Result<WptpYpPack>();
		try {
			wptpYpPack.setDeleted("0");
			wptpYpPack.setCreateBy(username);
			wptpYpPackService.save(wptpYpPack);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param wptpYpPack
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<WptpYpPack> edit(@RequestBody WptpYpPack wptpYpPack,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpYpPack> result = new Result<WptpYpPack>();
		WptpYpPack wptpYpPackEntity = wptpYpPackService.getById(wptpYpPack.getId());
		if(wptpYpPackEntity==null) {
			result.error500("未找到对应实体");
		}else {
			wptpYpPack.setUpdateBy(username);
			boolean ok = wptpYpPackService.updateById(wptpYpPack);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
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
			wptpYpPackService.removeById(id);
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
	public Result<WptpYpPack> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<WptpYpPack> result = new Result<WptpYpPack>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.wptpYpPackService.removeByIds(Arrays.asList(ids.split(",")));
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
	public Result<WptpYpPack> queryById(@RequestParam(name="id",required=true) String id) {
		Result<WptpYpPack> result = new Result<WptpYpPack>();
		WptpYpPack wptpYpPack = wptpYpPackService.getById(id);
		if(wptpYpPack==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(wptpYpPack);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param wptpYpPack
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, WptpYpPack wptpYpPack) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<WptpYpPack> queryWrapper = QueryGenerator.initQueryWrapper(wptpYpPack, request.getParameterMap());
      List<WptpYpPack> pageList = wptpYpPackService.list(queryWrapper);
      // Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      // 过滤选中数据
      String selections = request.getParameter("selections");
      if(oConvertUtils.isEmpty(selections)) {
    	  mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  List<WptpYpPack> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
    	  mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
      }
      //导出文件名称
	  String username = JwtUtil.getUserNameByToken(request);
      mv.addObject(NormalExcelConstants.FILE_NAME, "饮片包装列表");
      mv.addObject(NormalExcelConstants.CLASS, WptpYpPack.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("饮片包装列表数据", "导出人:"+username, "导出信息"));
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
              List<WptpYpPack> listWptpYpPacks = ExcelImportUtil.importExcel(file.getInputStream(), WptpYpPack.class, params);
              wptpYpPackService.saveBatch(listWptpYpPacks);
              return Result.ok("文件导入成功！数据行数:" + listWptpYpPacks.size());
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
	 private Result<IPage<WptpYpPackVO>> handlePageData(IPage<WptpYpPack> pageList){

		 Result<IPage<WptpYpPackVO>> result = new Result<IPage<WptpYpPackVO>>();
		 Map<String, Object> map = new HashMap<>();//保存查询条件
		 IPage<WptpYpPackVO> wptpYpPackVOIPage = new Page<>();//需要展示的分页数据

		 wptpYpPackVOIPage.setPages(pageList.getPages());
		 wptpYpPackVOIPage.setCurrent(pageList.getCurrent());
		 wptpYpPackVOIPage.setSize(pageList.getSize());
		 wptpYpPackVOIPage.setTotal(pageList.getTotal());
		 List<WptpYpPack> wptpYpPackList = pageList.getRecords();
		 List<WptpYpPackVO> wptpYpPackVOList = new ArrayList<WptpYpPackVO>();


		 /**
		  * 把查出来的实体类，属性值copy到展示类VO里面
		  */
		 for (int i = 0; i < wptpYpPackList.size(); i++) {
			 WptpYpPack wptpYpPack = wptpYpPackList.get(i);
			 WptpYpPackVO wptpYpPackVO =new WptpYpPackVO();
			 BeanUtils.copyProperties(wptpYpPack,wptpYpPackVO);

			 map.put("medicinal_code",wptpYpPack.getCategoryCode());
			 List<WptpMedicinal> wptpMedicinals = iWptpMedicinalService.getBaseMapper().selectByMap(map);
			 if (!wptpMedicinals.isEmpty())wptpYpPackVO.setMedicinalName(wptpMedicinals.get(0).getName());
			 wptpYpPackVOList.add(wptpYpPackVO);
			 map.clear();

		 }
		 wptpYpPackVOIPage.setRecords(wptpYpPackVOList);
		 result.setSuccess(true);
		 result.setResult(wptpYpPackVOIPage);
		 return result;
	 }
@PostMapping("/laowang")
	 public Result getLaowang(@NotNull String key){
  	 return new Result(true, "操作成功", 200, new Date().getTime());
}
}
