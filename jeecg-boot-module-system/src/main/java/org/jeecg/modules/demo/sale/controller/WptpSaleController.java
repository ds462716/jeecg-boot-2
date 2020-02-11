package org.jeecg.modules.demo.sale.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.sale.entity.WptpSale;
import org.jeecg.modules.demo.sale.service.IWptpSaleService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 出库销售表
 * @Author: jeecg-boot
 * @Date:   2019-10-09
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sale/wptpSale")
@Slf4j
public class WptpSaleController {
	@Autowired
	private IWptpSaleService wptpSaleService;
	
	/**
	  * 分页列表查询
	 * @param wptpSale
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<WptpSale>> queryPageList(WptpSale wptpSale,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		Result<IPage<WptpSale>> result = new Result<IPage<WptpSale>>();
		QueryWrapper<WptpSale> queryWrapper = QueryGenerator.initQueryWrapper(wptpSale, req.getParameterMap());
		Page<WptpSale> page = new Page<WptpSale>(pageNo, pageSize);
		queryWrapper.eq("deleted","0");
		IPage<WptpSale> pageList = wptpSaleService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param wptpSale
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<WptpSale> add(@RequestBody WptpSale wptpSale,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpSale> result = new Result<WptpSale>();
		try {
			wptpSale.setCreateBy(username);
			wptpSale.setDeleted("0");
			wptpSaleService.save(wptpSale);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param wptpSale
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<WptpSale> edit(@RequestBody WptpSale wptpSale,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpSale> result = new Result<WptpSale>();
		WptpSale wptpSaleEntity = wptpSaleService.getById(wptpSale.getId());
		if(wptpSaleEntity==null) {
			result.error500("未找到对应实体");
		}else {
			wptpSale.setUpdateBy(username);
			boolean ok = wptpSaleService.updateById(wptpSale);
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
			wptpSaleService.removeById(id);
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
	public Result<WptpSale> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<WptpSale> result = new Result<WptpSale>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.wptpSaleService.removeByIds(Arrays.asList(ids.split(",")));
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
	public Result<WptpSale> queryById(@RequestParam(name="id",required=true) String id) {
		Result<WptpSale> result = new Result<WptpSale>();
		WptpSale wptpSale = wptpSaleService.getById(id);
		if(wptpSale==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(wptpSale);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param response
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, WptpSale wptpSale) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<WptpSale> queryWrapper = QueryGenerator.initQueryWrapper(wptpSale, request.getParameterMap());
      List<WptpSale> pageList = wptpSaleService.list(queryWrapper);
      // Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      // 过滤选中数据
      String selections = request.getParameter("selections");
      if(oConvertUtils.isEmpty(selections)) {
    	  mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  List<WptpSale> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
    	  mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
      }
      //导出文件名称
	  String username = JwtUtil.getUserNameByToken(request);
      mv.addObject(NormalExcelConstants.FILE_NAME, "出库销售表列表");
      mv.addObject(NormalExcelConstants.CLASS, WptpSale.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("出库销售表列表数据", "导出人:"+username, "导出信息"));
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
              List<WptpSale> listWptpSales = ExcelImportUtil.importExcel(file.getInputStream(), WptpSale.class, params);
              wptpSaleService.saveBatch(listWptpSales);
              return Result.ok("文件导入成功！数据行数:" + listWptpSales.size());
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

}
