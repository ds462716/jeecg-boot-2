package org.jeecg.modules.demo.medicinebsale.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.service.IWptpEntInfoService;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
import org.jeecg.modules.demo.medicinebsale.entity.WptpMedicineSale;
import org.jeecg.modules.demo.medicinebsale.service.IWptpMedicineSaleService;
import org.jeecg.modules.demo.medicinebsale.vo.WptpMedicineSaleVO;
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
 * @Description: 饮片经营出库销售
 * @Author: jeecg-boot
 * @Date:   2019-10-09
 * @Version: V1.0
 */
@RestController
@RequestMapping("/medicinebsale/wptpMedicineSale")
@Slf4j
public class WptpMedicineSaleController {
	@Autowired
	private IWptpMedicineSaleService wptpMedicineSaleService;
	 @Autowired
	 private IWptpMedicinalService iWptpMedicinalService;
	 @Autowired
	 private IWptpEntInfoService iWptpEntInfoService;
	/**
	  * 分页列表查询
	 * @param wptpMedicineSale
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<WptpMedicineSaleVO>> queryPageList(WptpMedicineSale wptpMedicineSale,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		QueryWrapper<WptpMedicineSale> queryWrapper = QueryGenerator.initQueryWrapper(wptpMedicineSale, req.getParameterMap());
		Page<WptpMedicineSale> page = new Page<WptpMedicineSale>(pageNo, pageSize);
		queryWrapper.eq("deleted","0");
		IPage<WptpMedicineSale> pageList = wptpMedicineSaleService.page(page, queryWrapper);
		return handlePageData(pageList);
	}
	
	/**
	  *   添加
	 * @param wptpMedicineSale
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<WptpMedicineSale> add(@RequestBody WptpMedicineSale wptpMedicineSale,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpMedicineSale> result = new Result<WptpMedicineSale>();
		try {
			wptpMedicineSale.setDeleted("0");
            wptpMedicineSale.setCreateBy(username);
			wptpMedicineSaleService.save(wptpMedicineSale);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param wptpMedicineSale
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<WptpMedicineSale> edit(@RequestBody WptpMedicineSale wptpMedicineSale,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpMedicineSale> result = new Result<WptpMedicineSale>();
		WptpMedicineSale wptpMedicineSaleEntity = wptpMedicineSaleService.getById(wptpMedicineSale.getId());
		if(wptpMedicineSaleEntity==null) {
			result.error500("未找到对应实体");
		}else {
            wptpMedicineSale.setUpdateBy(username);
			boolean ok = wptpMedicineSaleService.updateById(wptpMedicineSale);
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
			wptpMedicineSaleService.removeById(id);
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
	public Result<WptpMedicineSale> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<WptpMedicineSale> result = new Result<WptpMedicineSale>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.wptpMedicineSaleService.removeByIds(Arrays.asList(ids.split(",")));
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
	public Result<WptpMedicineSale> queryById(@RequestParam(name="id",required=true) String id) {
		Result<WptpMedicineSale> result = new Result<WptpMedicineSale>();
		WptpMedicineSale wptpMedicineSale = wptpMedicineSaleService.getById(id);
		if(wptpMedicineSale==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(wptpMedicineSale);
			result.setSuccess(true);
		}
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param wptpMedicineSale
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, WptpMedicineSale wptpMedicineSale) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<WptpMedicineSale> queryWrapper = QueryGenerator.initQueryWrapper(wptpMedicineSale, request.getParameterMap());
      List<WptpMedicineSale> pageList = wptpMedicineSaleService.list(queryWrapper);
      // Step.2 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      // 过滤选中数据
      String selections = request.getParameter("selections");
      if(oConvertUtils.isEmpty(selections)) {
    	  mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  List<WptpMedicineSale> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
    	  mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
      }
      //导出文件名称
	  String username = JwtUtil.getUserNameByToken(request);
      mv.addObject(NormalExcelConstants.FILE_NAME, "饮片经营出库销售列表");
      mv.addObject(NormalExcelConstants.CLASS, WptpMedicineSale.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("饮片经营出库销售列表数据", "导出人:"+username, "导出信息"));
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
              List<WptpMedicineSale> listWptpMedicineSales = ExcelImportUtil.importExcel(file.getInputStream(), WptpMedicineSale.class, params);
              wptpMedicineSaleService.saveBatch(listWptpMedicineSales);
              return Result.ok("文件导入成功！数据行数:" + listWptpMedicineSales.size());
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
	 private Result<IPage<WptpMedicineSaleVO>> handlePageData(IPage<WptpMedicineSale> pageList){

		 Result<IPage<WptpMedicineSaleVO>> result = new Result<IPage<WptpMedicineSaleVO>>();
		 Map<String, Object> map = new HashMap<>();//保存查询条件
		 IPage<WptpMedicineSaleVO> wptpMedicineSaleVOIPage = new Page<>();//需要展示的分页数据

		 wptpMedicineSaleVOIPage.setPages(pageList.getPages());
		 wptpMedicineSaleVOIPage.setCurrent(pageList.getCurrent());
		 wptpMedicineSaleVOIPage.setSize(pageList.getSize());
		 wptpMedicineSaleVOIPage.setTotal(pageList.getTotal());
		 List<WptpMedicineSale> wptpMedicineSaleList = pageList.getRecords();
		 List<WptpMedicineSaleVO> wptpMedicineSaleVOList = new ArrayList<WptpMedicineSaleVO>();


		 /**
		  * 把查出来的实体类，属性值copy到展示类VO里面
		  */
		 for (int i = 0; i < wptpMedicineSaleList.size(); i++) {
			 WptpMedicineSale wptpMedicineSale = wptpMedicineSaleList.get(i);
			 WptpMedicineSaleVO wptpMedicineSaleVO =new WptpMedicineSaleVO();
			 BeanUtils.copyProperties(wptpMedicineSale,wptpMedicineSaleVO);

			 map.put("name",wptpMedicineSale.getMedicineName());
			 List<WptpMedicinal> wptpMedicinals = iWptpMedicinalService.getBaseMapper().selectByMap(map);
			 if (!wptpMedicinals.isEmpty())wptpMedicineSaleVO.setMedicinalCode(wptpMedicinals.get(0).getMedicinalCode());
			 wptpMedicineSaleVOList.add(wptpMedicineSaleVO);
			 map.clear();
			 map.put("ent_id",wptpMedicineSale.getEntId());
			 List<WptpEntInfo> wptpEntInfos = iWptpEntInfoService.getBaseMapper().selectByMap(map);
			 if (!wptpEntInfos.isEmpty())wptpMedicineSaleVO.setEntName(wptpEntInfos.get(0).getEntName());
			 map.clear();
		 }
		 wptpMedicineSaleVOIPage.setRecords(wptpMedicineSaleVOList);
		 result.setSuccess(true);
		 result.setResult(wptpMedicineSaleVOIPage);
		 return result;
	 }
}
