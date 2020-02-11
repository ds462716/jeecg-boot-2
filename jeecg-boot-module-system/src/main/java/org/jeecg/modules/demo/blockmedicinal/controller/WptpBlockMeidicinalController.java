package org.jeecg.modules.demo.blockmedicinal.controller;

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
import org.jeecg.modules.demo.blockinfo.entity.WptpBlockInfo;
import org.jeecg.modules.demo.blockinfo.service.IWptpBlockInfoService;
import org.jeecg.modules.demo.blockmedicinal.entity.WptpBlockMeidicinal;
import org.jeecg.modules.demo.blockmedicinal.service.IWptpBlockMeidicinalService;
import org.jeecg.modules.demo.blockmedicinal.vo.WptpBlockMeidicinalPage;
import org.jeecg.modules.demo.blockmedicinal.vo.WptpBlockMeidicinalVO;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.service.IWptpEntInfoService;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
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
 * @Description: 生产档案表
 * @Author: jeecg-boot
 * @Date:   2019-10-09
 * @Version: V1.0
 */
@RestController
@RequestMapping("/blockmedicinal/wptpBlockMeidicinal")
@Slf4j
public class WptpBlockMeidicinalController {
	@Autowired
	private IWptpBlockMeidicinalService wptpBlockMeidicinalService;
	@Autowired
	private IWptpMedicinalService iWptpMedicinalService;
	 @Autowired
	 private IWptpEntInfoService iWptpEntInfoService;
	 @Autowired
	 private IWptpBaseService iWptpBaseService;
	 @Autowired
	 private IWptpBlockInfoService iWptpBlockInfoService;
	
	/**
	  * 分页列表查询
	 * @param wptpBlockMeidicinal
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<IPage<WptpBlockMeidicinalVO>> queryPageList(WptpBlockMeidicinal wptpBlockMeidicinal,
									  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
									  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
									  HttpServletRequest req) {
		QueryWrapper<WptpBlockMeidicinal> queryWrapper = QueryGenerator.initQueryWrapper(wptpBlockMeidicinal, req.getParameterMap());
		Page<WptpBlockMeidicinal> page = new Page<WptpBlockMeidicinal>(pageNo, pageSize);
		queryWrapper.eq("deleted","0");
		IPage<WptpBlockMeidicinal> pageList = wptpBlockMeidicinalService.page(page, queryWrapper);
		return handlePageData(pageList);
	}
	
	/**
	  *   添加
	 * @param wptpBlockMeidicinalPage
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<WptpBlockMeidicinal> add(@RequestBody WptpBlockMeidicinalPage wptpBlockMeidicinalPage,HttpServletRequest request) {
		Result<WptpBlockMeidicinal> result = new Result<WptpBlockMeidicinal>();
		String username = JwtUtil.getUserNameByToken(request);
		try {
			WptpBlockMeidicinal wptpBlockMeidicinal = new WptpBlockMeidicinal();
			BeanUtils.copyProperties(wptpBlockMeidicinalPage, wptpBlockMeidicinal);
			wptpBlockMeidicinal.setDeleted("0");
			wptpBlockMeidicinal.setCreateBy(username);
			wptpBlockMeidicinalService.saveMain(wptpBlockMeidicinal );
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param wptpBlockMeidicinalPage
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<WptpBlockMeidicinal> edit(@RequestBody WptpBlockMeidicinalPage wptpBlockMeidicinalPage,HttpServletRequest request) {
		Result<WptpBlockMeidicinal> result = new Result<WptpBlockMeidicinal>();
		String username = JwtUtil.getUserNameByToken(request);
		WptpBlockMeidicinal wptpBlockMeidicinal = new WptpBlockMeidicinal();
		BeanUtils.copyProperties(wptpBlockMeidicinalPage, wptpBlockMeidicinal);
		WptpBlockMeidicinal wptpBlockMeidicinalEntity = wptpBlockMeidicinalService.getById(wptpBlockMeidicinal.getId());
		if(wptpBlockMeidicinalEntity==null) {
			result.error500("未找到对应实体");
		}else {
			wptpBlockMeidicinal.setUpdateBy(username);
			boolean ok = wptpBlockMeidicinalService.updateById(wptpBlockMeidicinal);
			wptpBlockMeidicinalService.updateMain(wptpBlockMeidicinal );
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
			wptpBlockMeidicinalService.delMain(id);
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
	public Result<WptpBlockMeidicinal> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<WptpBlockMeidicinal> result = new Result<WptpBlockMeidicinal>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.wptpBlockMeidicinalService.delBatchMain(Arrays.asList(ids.split(",")));
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
	public Result<WptpBlockMeidicinal> queryById(@RequestParam(name="id",required=true) String id) {
		Result<WptpBlockMeidicinal> result = new Result<WptpBlockMeidicinal>();
		WptpBlockMeidicinal wptpBlockMeidicinal = wptpBlockMeidicinalService.getById(id);
		if(wptpBlockMeidicinal==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(wptpBlockMeidicinal);
			result.setSuccess(true);
		}
		return result;
	}
	

  /**
      * 导出excel
   *
   * @param request
   */
  @RequestMapping(value = "/exportXls")
  public ModelAndView exportXls(HttpServletRequest request, WptpBlockMeidicinal wptpBlockMeidicinal) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<WptpBlockMeidicinal> queryWrapper = QueryGenerator.initQueryWrapper(wptpBlockMeidicinal, request.getParameterMap());
      List<WptpBlockMeidicinal> queryList = wptpBlockMeidicinalService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<WptpBlockMeidicinal> wptpBlockMeidicinalList = new ArrayList<WptpBlockMeidicinal>();
      if(oConvertUtils.isEmpty(selections)) {
    	  wptpBlockMeidicinalList = queryList;
      }else {
    	  List<String> selectionList = Arrays.asList(selections.split(","));
    	  wptpBlockMeidicinalList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }
	  // Step.2 组装pageList
      List<WptpBlockMeidicinalPage> pageList = new ArrayList<WptpBlockMeidicinalPage>();
      for (WptpBlockMeidicinal main : wptpBlockMeidicinalList) {
          WptpBlockMeidicinalPage vo = new WptpBlockMeidicinalPage();
          BeanUtils.copyProperties(main, vo);
          pageList.add(vo);
      }
      // Step.3 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
	  String username = JwtUtil.getUserNameByToken(request);
      mv.addObject(NormalExcelConstants.FILE_NAME, "生产档案表列表");
      mv.addObject(NormalExcelConstants.CLASS, WptpBlockMeidicinalPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("生产档案表列表数据", "导出人:"+username, "导出信息"));
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
              List<WptpBlockMeidicinalPage> list = ExcelImportUtil.importExcel(file.getInputStream(), WptpBlockMeidicinalPage.class, params);
              for (WptpBlockMeidicinalPage page : list) {
                  WptpBlockMeidicinal po = new WptpBlockMeidicinal();
                  BeanUtils.copyProperties(page, po);
                  wptpBlockMeidicinalService.saveMain(po);
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
	 private Result<IPage<WptpBlockMeidicinalVO>> handlePageData(IPage<WptpBlockMeidicinal> pageList){

		 Result<IPage<WptpBlockMeidicinalVO>> result = new Result<IPage<WptpBlockMeidicinalVO>>();
		 Map<String, Object> map = new HashMap<>();//保存查询条件
		 IPage<WptpBlockMeidicinalVO> wptpBlockMeidicinalVOIPage = new Page<>();//需要展示的分页数据

		 wptpBlockMeidicinalVOIPage.setPages(pageList.getPages());
		 wptpBlockMeidicinalVOIPage.setCurrent(pageList.getCurrent());
		 wptpBlockMeidicinalVOIPage.setSize(pageList.getSize());
		 wptpBlockMeidicinalVOIPage.setTotal(pageList.getTotal());
		 List<WptpBlockMeidicinal> wptpBlockMeidicinalList = pageList.getRecords();
		 List<WptpBlockMeidicinalVO> wptpBlockMeidicinalVOList = new ArrayList<WptpBlockMeidicinalVO>();


		 /**
		  * 把查出来的实体类，属性值copy到展示类VO里面
		  */
		 for (int i = 0; i < wptpBlockMeidicinalList.size(); i++) {
			 WptpBlockMeidicinal wptpBlockMeidicinal = wptpBlockMeidicinalList.get(i);
			 WptpBlockMeidicinalVO wptpBlockMeidicinalVO =new WptpBlockMeidicinalVO();
			 BeanUtils.copyProperties(wptpBlockMeidicinal,wptpBlockMeidicinalVO);
			 map.put("medicinal_code",wptpBlockMeidicinal.getMedicinalCode());
			 List<WptpMedicinal> wptpMedicinals = iWptpMedicinalService.getBaseMapper().selectByMap(map);
			 if (!wptpMedicinals.isEmpty())wptpBlockMeidicinalVO.setMedicinalName(wptpMedicinals.get(0).getName());
			 map.clear();
			 map.put("ent_id",wptpBlockMeidicinal.getEntId());
			 List<WptpEntInfo> wptpEntInfos = iWptpEntInfoService.getBaseMapper().selectByMap(map);
			 if (!wptpEntInfos.isEmpty())wptpBlockMeidicinalVO.setEntName(wptpEntInfos.get(0).getEntName());
			 map.clear();
			 map.put("base_code",wptpBlockMeidicinal.getBaseCode());
			 List<WptpBase> wptpBases = iWptpBaseService.getBaseMapper().selectByMap(map);
			 if (!wptpBases.isEmpty())wptpBlockMeidicinalVO.setBaseName(wptpBases.get(0).getBaseName());
			 map.clear();
			 map.put("block_code",wptpBlockMeidicinal.getBlockCode());
			 List<WptpBlockInfo> wptpBlockInfos = iWptpBlockInfoService.getBaseMapper().selectByMap(map);
			 if (!wptpBlockInfos.isEmpty())
			 {
				 wptpBlockMeidicinalVO.setBaseArea(wptpBlockInfos.get(0).getBaseArea());
				 wptpBlockMeidicinalVO.setGpsLatitude(wptpBlockInfos.get(0).getGpsLatitude());
				 wptpBlockMeidicinalVO.setGpsLongitude(wptpBlockInfos.get(0).getGpsLongitude());
			 }
			 map.clear();
			 wptpBlockMeidicinalVOList.add(wptpBlockMeidicinalVO);
		 }
		 wptpBlockMeidicinalVOIPage.setRecords(wptpBlockMeidicinalVOList);
		 result.setSuccess(true);
		 result.setResult(wptpBlockMeidicinalVOIPage);
		 return result;
	 }
}
