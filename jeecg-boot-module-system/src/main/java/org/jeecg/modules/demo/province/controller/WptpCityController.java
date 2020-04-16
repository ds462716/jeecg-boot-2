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
import org.jeecg.modules.demo.province.entity.WptpCity;
import org.jeecg.modules.demo.province.entity.WptpProvince;
import org.jeecg.modules.demo.province.service.IWptpCityService;
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
 * @Description: wptp_city
 * @Author: jeecg-boot
 * @Date: 2019-10-09
 * @Version: V1.0
 */
@Api(tags = "市")
@RestController
@RequestMapping("/province/wptpCity")
@Slf4j
public class WptpCityController {
    @Autowired
    private IWptpCityService wptpCityService;
    @Autowired
    private IWptpProvinceService iWptpProvinceService;

    /**
     * 分页列表查询
     *
     * @param wptpCity
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<WptpCity>> queryPageList(WptpCity wptpCity,
                                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                 HttpServletRequest req) {
        Result<IPage<WptpCity>> result = new Result<IPage<WptpCity>>();
        QueryWrapper<WptpCity> queryWrapper = QueryGenerator.initQueryWrapper(wptpCity, req.getParameterMap());
        Page<WptpCity> page = new Page<WptpCity>(pageNo, pageSize);
        queryWrapper.eq("deleted", "0");
        IPage<WptpCity> pageList = wptpCityService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 获取某个省份下的市（不分页）
     *
     * @return
     */
    @ApiOperation(value = "获取某个省份下的市（不分页）", notes = "获取某个省份下的市（不分页）")
    @GetMapping(value = "/listCitys")
    public Result<WptpCity> listCitys(String provinceCode) {
        QueryWrapper<WptpProvince> provinceQueryWrapper = new QueryWrapper<>();
        provinceQueryWrapper.eq("admi_code", provinceCode);
        WptpProvince wptpProvince = iWptpProvinceService.getBaseMapper().selectOne(provinceQueryWrapper);
        if (oConvertUtils.isEmpty(wptpProvince)) return new Result().error500("未找到相关省");
        QueryWrapper<WptpCity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("province_id", wptpProvince.getId());
        List<WptpCity> wptpCities = wptpCityService.getBaseMapper().selectList(queryWrapper);
        return new Result<WptpCity>(true, "操作成功", 200, wptpCities, new Date().getTime());
    }

    /**
     * 添加
     *
     * @param wptpCity
     * @return
     */
    @PostMapping(value = "/add")
    public Result<WptpCity> add(@RequestBody WptpCity wptpCity) {
        Result<WptpCity> result = new Result<WptpCity>();
        try {
            wptpCityService.save(wptpCity);
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
     * @param wptpCity
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<WptpCity> edit(@RequestBody WptpCity wptpCity) {
        Result<WptpCity> result = new Result<WptpCity>();
        WptpCity wptpCityEntity = wptpCityService.getById(wptpCity.getId());
        if (wptpCityEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = wptpCityService.updateById(wptpCity);
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
            wptpCityService.removeById(id);
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
    public Result<WptpCity> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<WptpCity> result = new Result<WptpCity>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.wptpCityService.removeByIds(Arrays.asList(ids.split(",")));
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
    public Result<WptpCity> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<WptpCity> result = new Result<WptpCity>();
        WptpCity wptpCity = wptpCityService.getById(id);
        if (wptpCity == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(wptpCity);
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
    public ModelAndView exportXls(HttpServletRequest request, WptpCity wptpCity) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WptpCity> queryWrapper = QueryGenerator.initQueryWrapper(wptpCity, request.getParameterMap());
        List<WptpCity> pageList = wptpCityService.list(queryWrapper);
        // Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isEmpty(selections)) {
            mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            List<WptpCity> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
            mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        }
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "wptp_city列表");
        mv.addObject(NormalExcelConstants.CLASS, WptpCity.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("wptp_city列表数据", "导出人:Jeecg", "导出信息"));
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
                List<WptpCity> listWptpCitys = ExcelImportUtil.importExcel(file.getInputStream(), WptpCity.class, params);
                wptpCityService.saveBatch(listWptpCitys);
                return Result.ok("文件导入成功！数据行数:" + listWptpCitys.size());
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
