package org.jeecg.modules.demo.ypprocesssale.controller;

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
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.service.IWptpEntInfoService;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSale;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSaleFile;
import org.jeecg.modules.demo.ypprocesssale.service.IWptpYpSaleFileService;
import org.jeecg.modules.demo.ypprocesssale.service.IWptpYpSaleService;
import org.jeecg.modules.demo.ypprocesssale.vo.WptpYpSalePage;
import org.jeecg.modules.demo.ypprocesssale.vo.WptpYpSaleVO;
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
 * @Description: 饮片销售
 * @Author: jeecg-boot
 * @Date:   2019-10-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/ypprocesssale/wptpYpSale")
@Slf4j
@Api(tags="饮片加工-饮片销售")
public class WptpYpSaleController {
	@Autowired
	private IWptpYpSaleService wptpYpSaleService;
	@Autowired
	private IWptpYpSaleFileService wptpYpSaleFileService;
	 @Autowired
	 private IWptpMedicinalService iWptpMedicinalService;
	 @Autowired
	 private IWptpEntInfoService iWptpEntInfoService;
	
	/**
	  * 分页列表查询
	 * @param wptpYpSale
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<WptpYpSaleVO>> queryPageList(WptpYpSale wptpYpSale,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		QueryWrapper<WptpYpSale> queryWrapper = QueryGenerator.initQueryWrapper(wptpYpSale, req.getParameterMap());
		Page<WptpYpSale> page = new Page<WptpYpSale>(pageNo, pageSize);
		queryWrapper.eq("deleted","0");
		IPage<WptpYpSale> pageList = wptpYpSaleService.page(page, queryWrapper);
		return handlePageData(pageList);
	}
	
	/**
	  *   添加
	 * @param wptpYpSalePage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<WptpYpSale> add(@RequestBody WptpYpSalePage wptpYpSalePage,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpYpSale> result = new Result<WptpYpSale>();
		try {
			WptpYpSale wptpYpSale = new WptpYpSale();
			BeanUtils.copyProperties(wptpYpSalePage, wptpYpSale);
			wptpYpSale.setDeleted("0");
			wptpYpSale.setCreateBy(username);
			wptpYpSaleService.saveMain(wptpYpSale, wptpYpSalePage.getWptpYpSaleFileList());
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param wptpYpSalePage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<WptpYpSale> edit(@RequestBody WptpYpSalePage wptpYpSalePage,HttpServletRequest request) {
		String username = JwtUtil.getUserNameByToken(request);
		Result<WptpYpSale> result = new Result<WptpYpSale>();
		WptpYpSale wptpYpSale = new WptpYpSale();
		BeanUtils.copyProperties(wptpYpSalePage, wptpYpSale);
		WptpYpSale wptpYpSaleEntity = wptpYpSaleService.getById(wptpYpSale.getId());
		if(wptpYpSaleEntity==null) {
			result.error500("未找到对应实体");
		}else {
			wptpYpSale.setUpdateBy(username);
			boolean ok = wptpYpSaleService.updateById(wptpYpSale);
			wptpYpSaleService.updateMain(wptpYpSale, wptpYpSalePage.getWptpYpSaleFileList());
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
			wptpYpSaleService.delMain(id);
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
	public Result<WptpYpSale> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<WptpYpSale> result = new Result<WptpYpSale>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.wptpYpSaleService.delBatchMain(Arrays.asList(ids.split(",")));
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
	public Result<WptpYpSale> queryById(@RequestParam(name="id",required=true) String id) {
		Result<WptpYpSale> result = new Result<WptpYpSale>();
		WptpYpSale wptpYpSale = wptpYpSaleService.getById(id);
		if(wptpYpSale==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(wptpYpSale);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "通过id查询饮片加工-饮片销售文件", notes = "通过id查询饮片加工-饮片销售文件")
	@GetMapping(value = "/queryWptpYpSaleFileByMainId")
	public Result<List<WptpYpSaleFile>> queryWptpYpSaleFileListByMainId(@RequestParam(name="id",required=true) String id) {
		Result<List<WptpYpSaleFile>> result = new Result();
		WptpYpSale wptpYpSale = wptpYpSaleService.getBaseMapper().selectById(id);
		if (oConvertUtils.isEmpty(wptpYpSale))return new Result().error500("未找到饮片销售信息");
		QueryWrapper<WptpYpSaleFile> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("main_id",wptpYpSale.getSaleNumber());
		queryWrapper.eq("deleted","0");
		List<WptpYpSaleFile> wptpYpSaleFileList = wptpYpSaleFileService.getBaseMapper().selectList(queryWrapper);
		result.setResult(wptpYpSaleFileList);
		result.setSuccess(true);
		return result;
	}

  /**
      * 导出excel
   *
   * @param request
   * @param wptpYpSale
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, WptpYpSale wptpYpSale) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<WptpYpSale> queryWrapper = QueryGenerator.initQueryWrapper(wptpYpSale, request.getParameterMap());
      List<WptpYpSale> queryList = wptpYpSaleService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<WptpYpSale> wptpYpSaleList = new ArrayList<WptpYpSale>();
      if(oConvertUtils.isEmpty(selections)) {
    	  wptpYpSaleList = queryList;
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  wptpYpSaleList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }
	  // Step.2 组装pageList
      List<WptpYpSalePage> pageList = new ArrayList<WptpYpSalePage>();
      for (WptpYpSale main : wptpYpSaleList) {
          WptpYpSalePage vo = new WptpYpSalePage();
          BeanUtils.copyProperties(main, vo);
          List<WptpYpSaleFile> wptpYpSaleFileList = wptpYpSaleFileService.selectByMainId(main.getId());
          vo.setWptpYpSaleFileList(wptpYpSaleFileList);
          pageList.add(vo);
      }
      // Step.3 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
	  String username = JwtUtil.getUserNameByToken(request);
      mv.addObject(NormalExcelConstants.FILE_NAME, "饮片销售列表");
      mv.addObject(NormalExcelConstants.CLASS, WptpYpSalePage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("饮片销售列表数据", "导出人:"+username, "导出信息"));
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
              List<WptpYpSalePage> list = ExcelImportUtil.importExcel(file.getInputStream(), WptpYpSalePage.class, params);
              for (WptpYpSalePage page : list) {
                  WptpYpSale po = new WptpYpSale();
                  BeanUtils.copyProperties(page, po);
                  wptpYpSaleService.saveMain(po, page.getWptpYpSaleFileList());
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
	 private Result<IPage<WptpYpSaleVO>> handlePageData(IPage<WptpYpSale> pageList){

		 Result<IPage<WptpYpSaleVO>> result = new Result<IPage<WptpYpSaleVO>>();
		 Map<String, Object> map = new HashMap<>();//保存查询条件
		 IPage<WptpYpSaleVO> wptpYpSaleVOIPage = new Page<>();//需要展示的分页数据

		 wptpYpSaleVOIPage.setPages(pageList.getPages());
		 wptpYpSaleVOIPage.setCurrent(pageList.getCurrent());
		 wptpYpSaleVOIPage.setSize(pageList.getSize());
		 wptpYpSaleVOIPage.setTotal(pageList.getTotal());
		 List<WptpYpSale> wptpYpSaleList = pageList.getRecords();
		 List<WptpYpSaleVO> wptpYpSaleVOList = new ArrayList<WptpYpSaleVO>();


		 /**
		  * 把查出来的实体类，属性值copy到展示类VO里面
		  */
		 for (int i = 0; i < wptpYpSaleList.size(); i++) {
			 WptpYpSale wptpYpSale = wptpYpSaleList.get(i);
			 WptpYpSaleVO wptpYpSaleVO =new WptpYpSaleVO();
			 BeanUtils.copyProperties(wptpYpSale,wptpYpSaleVO);

			 map.put("medicinal_code",wptpYpSale.getCategoryCode());
			 List<WptpMedicinal> wptpMedicinals = iWptpMedicinalService.getBaseMapper().selectByMap(map);
			 if (!wptpMedicinals.isEmpty())wptpYpSaleVO.setMedicinalName(wptpMedicinals.get(0).getName());
			 map.clear();
			 map.put("ent_id",wptpYpSale.getEntId());
			 List<WptpEntInfo> wptpEntInfos = iWptpEntInfoService.getBaseMapper().selectByMap(map);
			 if (!wptpEntInfos.isEmpty())wptpYpSaleVO.setEntName(wptpEntInfos.get(0).getEntName());
			 map.clear();
			 wptpYpSaleVOList.add(wptpYpSaleVO);
		 }
		 wptpYpSaleVOIPage.setRecords(wptpYpSaleVOList);
		 result.setSuccess(true);
		 result.setResult(wptpYpSaleVOIPage);
		 return result;
	 }
}
