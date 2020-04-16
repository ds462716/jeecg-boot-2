package org.jeecg.modules.demo.ypbsale.controller;

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
import org.jeecg.modules.demo.ypbsale.entity.WptpYpbSale;
import org.jeecg.modules.demo.ypbsale.service.IWptpYpbSaleService;
import org.jeecg.modules.demo.ypbsale.vo.WptpYpbSaleVO;
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
 * @Description: 饮片经营销售
 * @Author: jeecg-boot
 * @Date: 2019-10-09
 * @Version: V1.0
 */
@RestController
@RequestMapping("/ypbsale/wptpYpbSale")
@Slf4j
public class WptpYpbSaleController {
    @Autowired
    private IWptpYpbSaleService wptpYpbSaleService;
    @Autowired
    private IWptpEntInfoService iWptpEntInfoService;

    /**
     * 分页列表查询
     *
     * @param wptpYpbSale
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<WptpYpbSaleVO>> queryPageList(WptpYpbSale wptpYpbSale,
                                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                      HttpServletRequest req) {
        QueryWrapper<WptpYpbSale> queryWrapper = QueryGenerator.initQueryWrapper(wptpYpbSale, req.getParameterMap());
        Page<WptpYpbSale> page = new Page<WptpYpbSale>(pageNo, pageSize);
        queryWrapper.eq("deleted", "0");
        IPage<WptpYpbSale> pageList = wptpYpbSaleService.page(page, queryWrapper);
        return handlePageData(pageList);
    }

    /**
     * 添加
     *
     * @param wptpYpbSale
     * @return
     */
    @PostMapping(value = "/add")
    public Result<WptpYpbSale> add(@RequestBody WptpYpbSale wptpYpbSale, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpYpbSale> result = new Result<WptpYpbSale>();
        try {
            wptpYpbSale.setDeleted("0");
            wptpYpbSale.setCreateBy(username);
            wptpYpbSaleService.save(wptpYpbSale);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     *
     * @param wptpYpbSale
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<WptpYpbSale> edit(@RequestBody WptpYpbSale wptpYpbSale, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpYpbSale> result = new Result<WptpYpbSale>();
        WptpYpbSale wptpYpbSaleEntity = wptpYpbSaleService.getById(wptpYpbSale.getId());
        if (wptpYpbSaleEntity == null) {
            result.error500("未找到对应实体");
        } else {
            wptpYpbSale.setUpdateBy(username);
            boolean ok = wptpYpbSaleService.updateById(wptpYpbSale);
            //TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        try {
            wptpYpbSaleService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e.getMessage());
            return Result.error("删除失败!");
        }
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/deleteBatch")
    public Result<WptpYpbSale> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<WptpYpbSale> result = new Result<WptpYpbSale>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.wptpYpbSaleService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/queryById")
    public Result<WptpYpbSale> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<WptpYpbSale> result = new Result<WptpYpbSale>();
        WptpYpbSale wptpYpbSale = wptpYpbSaleService.getById(id);
        if (wptpYpbSale == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(wptpYpbSale);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wptpYpbSale
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WptpYpbSale wptpYpbSale) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WptpYpbSale> queryWrapper = QueryGenerator.initQueryWrapper(wptpYpbSale, request.getParameterMap());
        List<WptpYpbSale> pageList = wptpYpbSaleService.list(queryWrapper);
        // Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isEmpty(selections)) {
            mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            List<WptpYpbSale> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
            mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        }
        //导出文件名称
        String username = JwtUtil.getUserNameByToken(request);
        mv.addObject(NormalExcelConstants.FILE_NAME, "饮片经营销售列表");
        mv.addObject(NormalExcelConstants.CLASS, WptpYpbSale.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("饮片经营销售列表数据", "导出人:" + username, "导出信息"));
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
                List<WptpYpbSale> listWptpYpbSales = ExcelImportUtil.importExcel(file.getInputStream(), WptpYpbSale.class, params);
                wptpYpbSaleService.saveBatch(listWptpYpbSales);
                return Result.ok("文件导入成功！数据行数:" + listWptpYpbSales.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
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
     *
     * @param pageList
     * @return
     */
    private Result<IPage<WptpYpbSaleVO>> handlePageData(IPage<WptpYpbSale> pageList) {

        Result<IPage<WptpYpbSaleVO>> result = new Result<IPage<WptpYpbSaleVO>>();
        Map<String, Object> map = new HashMap<>();//保存查询条件
        IPage<WptpYpbSaleVO> wptpYpbSaleVOIPage = new Page<>();//需要展示的分页数据

        wptpYpbSaleVOIPage.setPages(pageList.getPages());
        wptpYpbSaleVOIPage.setCurrent(pageList.getCurrent());
        wptpYpbSaleVOIPage.setSize(pageList.getSize());
        wptpYpbSaleVOIPage.setTotal(pageList.getTotal());
        List<WptpYpbSale> wptpYpbSaleList = pageList.getRecords();
        List<WptpYpbSaleVO> wptpYpbSaleVOList = new ArrayList<WptpYpbSaleVO>();


        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        for (int i = 0; i < wptpYpbSaleList.size(); i++) {
            WptpYpbSale wptpYpbSale = wptpYpbSaleList.get(i);
            WptpYpbSaleVO wptpYpbSaleVO = new WptpYpbSaleVO();
            BeanUtils.copyProperties(wptpYpbSale, wptpYpbSaleVO);


            map.put("ent_id", wptpYpbSale.getEntId());
            List<WptpEntInfo> wptpEntInfos = iWptpEntInfoService.getBaseMapper().selectByMap(map);
            if (!wptpEntInfos.isEmpty()) wptpYpbSaleVO.setEntName(wptpEntInfos.get(0).getEntName());
            map.clear();
            wptpYpbSaleVOList.add(wptpYpbSaleVO);
        }
        wptpYpbSaleVOIPage.setRecords(wptpYpbSaleVOList);
        result.setSuccess(true);
        result.setResult(wptpYpbSaleVOIPage);
        return result;
    }
}
