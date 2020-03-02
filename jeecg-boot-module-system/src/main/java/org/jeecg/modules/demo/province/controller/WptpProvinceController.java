package org.jeecg.modules.demo.province.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.province.entity.WptpProvince;
import org.jeecg.modules.demo.province.service.IWptpProvinceService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: wptp_province
 * @Author: jeecg-boot
 * @Date: 2019-10-09
 * @Version: V1.0
 */
@Api(tags = "省")
@RestController
@RequestMapping("/province/wptpProvince")
@Slf4j
public class WptpProvinceController {
    @Autowired
    private IWptpProvinceService wptpProvinceService;

    /**
     * 分页列表查询
     *
     * @param wptpProvince
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<WptpProvince>> queryPageList(WptpProvince wptpProvince,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        Result<IPage<WptpProvince>> result = new Result<IPage<WptpProvince>>();
        QueryWrapper<WptpProvince> queryWrapper = QueryGenerator.initQueryWrapper(wptpProvince, req.getParameterMap());
        Page<WptpProvince> page = new Page<WptpProvince>(pageNo, pageSize);
        queryWrapper.eq("deleted", "0");
        IPage<WptpProvince> pageList = wptpProvinceService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 获取所有省份（不分页）
     *
     * @return
     */
    @ApiOperation(value = "查询所有省份", notes = "查询所有省份")
    @GetMapping(value = "/listProvinces")
    public Result<WptpProvince> listProvinces() {
        QueryWrapper<WptpProvince> queryWrapper = new QueryWrapper<>();
        List<WptpProvince> wptpProvinces = wptpProvinceService.getBaseMapper().selectList(queryWrapper);
        return new Result<WptpProvince>(true, "操作成功", 200, wptpProvinces, new Date().getTime());
    }

    /**
     * 添加
     *
     * @param wptpProvince
     * @return
     */
    @PostMapping(value = "/add")
    public Result<WptpProvince> add(@RequestBody WptpProvince wptpProvince) {
        Result<WptpProvince> result = new Result<WptpProvince>();
        try {
            wptpProvinceService.save(wptpProvince);
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
     * @param wptpProvince
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<WptpProvince> edit(@RequestBody WptpProvince wptpProvince) {
        Result<WptpProvince> result = new Result<WptpProvince>();
        WptpProvince wptpProvinceEntity = wptpProvinceService.getById(wptpProvince.getId());
        if (wptpProvinceEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = wptpProvinceService.updateById(wptpProvince);
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
            wptpProvinceService.removeById(id);
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
    public Result<WptpProvince> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<WptpProvince> result = new Result<WptpProvince>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.wptpProvinceService.removeByIds(Arrays.asList(ids.split(",")));
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
    public Result<WptpProvince> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<WptpProvince> result = new Result<WptpProvince>();
        WptpProvince wptpProvince = wptpProvinceService.getById(id);
        if (wptpProvince == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(wptpProvince);
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
    public ModelAndView exportXls(HttpServletRequest request, WptpProvince wptpProvince) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WptpProvince> queryWrapper = QueryGenerator.initQueryWrapper(wptpProvince, request.getParameterMap());
        List<WptpProvince> pageList = wptpProvinceService.list(queryWrapper);
        // Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isEmpty(selections)) {
            mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            List<WptpProvince> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
            mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        }
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "wptp_province列表");
        mv.addObject(NormalExcelConstants.CLASS, WptpProvince.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("wptp_province列表数据", "导出人:Jeecg", "导出信息"));
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
                List<WptpProvince> listWptpProvinces = ExcelImportUtil.importExcel(file.getInputStream(), WptpProvince.class, params);
                wptpProvinceService.saveBatch(listWptpProvinces);
                return Result.ok("文件导入成功！数据行数:" + listWptpProvinces.size());
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

}
