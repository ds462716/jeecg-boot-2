package org.jeecg.modules.demo.baseinfo.controller;

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
import org.jeecg.modules.demo.baseinfo.entity.WptpBase;
import org.jeecg.modules.demo.baseinfo.entity.WptpBaseFile;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseFileService;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseService;
import org.jeecg.modules.demo.baseinfo.vo.WptpBasePage;
import org.jeecg.modules.demo.baseinfo.vo.WptpBaseVO;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.service.IWptpEntInfoService;
import org.jeecg.modules.demo.province.entity.WptpCity;
import org.jeecg.modules.demo.province.entity.WptpDistrict;
import org.jeecg.modules.demo.province.entity.WptpProvince;
import org.jeecg.modules.demo.province.service.IWptpCityService;
import org.jeecg.modules.demo.province.service.IWptpDistrictService;
import org.jeecg.modules.demo.province.service.IWptpProvinceService;
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
 * @Description: 基地信息管理表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@RestController
@Api(tags = "基地信息")
@RequestMapping("/baseinfo/wptpBase")
@Slf4j
public class WptpBaseController {
    @Autowired
    private IWptpBaseService wptpBaseService;
    @Autowired
    private IWptpBaseFileService wptpBaseFileService;
    @Autowired
    private IWptpProvinceService iWptpProvinceService;
    @Autowired
    private IWptpCityService iWptpCityService;
    @Autowired
    private IWptpDistrictService iWptpDistrictService;
    @Autowired
    private IWptpEntInfoService iWptpEntInfoService;

    /**
     * 分页列表查询
     *
     * @param wptpBase
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<WptpBaseVO>> queryPageList(WptpBase wptpBase,
                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest req) {
        QueryWrapper<WptpBase> queryWrapper = QueryGenerator.initQueryWrapper(wptpBase, req.getParameterMap());
        Page<WptpBase> page = new Page<WptpBase>(pageNo, pageSize);
        queryWrapper.eq("deleted", "0");
        IPage<WptpBase> pageList = wptpBaseService.page(page, queryWrapper);
        return handlePageData(pageList);
    }

    /**
     * 添加
     *
     * @param wptpBasePage
     * @return
     */
    @PostMapping(value = "/add")
    @ApiOperation(value = "添加基地", notes = "添加基地")
    public Result<WptpBase> add(@RequestBody WptpBasePage wptpBasePage, HttpServletRequest request) {
        Result<WptpBase> result = new Result<WptpBase>();
        try {

            String username = JwtUtil.getUserNameByToken(request);
            WptpBase wptpBase = new WptpBase();
            BeanUtils.copyProperties(wptpBasePage, wptpBase);
            QueryWrapper<WptpBase> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("base_name", wptpBase.getBaseName());
            WptpBase wptpBaseInDB = wptpBaseService.getBaseMapper().selectOne(queryWrapper);
            if (!oConvertUtils.isEmpty(wptpBaseInDB)) return result.error500("该基地已经存在!");
            wptpBase.setDeleted("0");
            wptpBase.setCreateBy(username);
            wptpBase.setCreateTime(new Date());
            List<WptpBaseFile> wptpBaseFileList = wptpBasePage.getWptpBaseFileList();
            for (WptpBaseFile w :
                    wptpBaseFileList) {
                if (oConvertUtils.isEmpty(w.getPath())) continue;
                w.setCreateBy(username);
                w.setCreateTime(new Date());
                w.setDeleted("0");
                wptpBaseFileService.save(w);
            }
            wptpBaseService.saveMain(wptpBase, null);
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
     * @param wptpBasePage
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<WptpBase> edit(@RequestBody WptpBasePage wptpBasePage, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpBase> result = new Result<WptpBase>();
        WptpBase wptpBase = new WptpBase();
        BeanUtils.copyProperties(wptpBasePage, wptpBase);
        WptpBase wptpBaseEntity = wptpBaseService.getById(wptpBase.getId());
        if (wptpBaseEntity == null) {
            result.error500("未找到对应实体");
        } else {
            wptpBase.setUpdateBy(username);
            wptpBase.setUpdateTime(new Date());
            wptpBaseService.updateById(wptpBase);
            QueryWrapper<WptpBaseFile> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("main_id", wptpBase.getBaseCode());
            queryWrapper.eq("deleted", "0");
            List<WptpBaseFile> wptpBaseFileListInDb = wptpBaseFileService.getBaseMapper().selectList(queryWrapper);
            for (WptpBaseFile w : wptpBaseFileListInDb) {
                w.setDeleted("1");
                w.setUpdateTime(new Date());
                w.setUpdateBy(username);
                wptpBaseFileService.getBaseMapper().updateById(w);
            }
            List<WptpBaseFile> wptpBaseFileList = wptpBasePage.getWptpBaseFileList();
            if (!wptpBaseFileList.isEmpty()) {
                for (WptpBaseFile wptpBaseFile :
                        wptpBaseFileList) {
                    if (oConvertUtils.isEmpty(wptpBaseFile.getPath())) continue;
                    wptpBaseFile.setDeleted("0");
                    wptpBaseFile.setCreateBy(username);
                    wptpBaseFile.setCreateTime(new Date());
                    wptpBaseFileService.getBaseMapper().insert(wptpBaseFile);
                }
            }
            result.success("修改成功!");
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
            wptpBaseService.delMain(id);
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
    public Result<WptpBase> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<WptpBase> result = new Result<WptpBase>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.wptpBaseService.delBatchMain(Arrays.asList(ids.split(",")));
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
    public Result<WptpBase> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<WptpBase> result = new Result<WptpBase>();
        WptpBase wptpBase = wptpBaseService.getById(id);
        if (wptpBase == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(wptpBase);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "通过id查询文件", notes = "通过id查询文件")
    @GetMapping(value = "/queryWptpBaseFileByMainId")
    public Result<List<WptpBaseFile>> queryWptpBaseFileListByMainId(@RequestParam(name = "id", required = true) String id) {
        Result<List<WptpBaseFile>> result = new Result<List<WptpBaseFile>>();
        WptpBase wptpBase = wptpBaseService.getBaseMapper().selectById(id);
        if (oConvertUtils.isEmpty(wptpBase)) return new Result().error500("未找到基地信息");
        QueryWrapper<WptpBaseFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_id", wptpBase.getBaseCode());
        queryWrapper.eq("deleted", "0");
        List<WptpBaseFile> wptpBaseFileList = wptpBaseFileService.getBaseMapper().selectList(queryWrapper);
        result.setResult(wptpBaseFileList);
        result.setSuccess(true);
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wptpBase
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WptpBase wptpBase) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WptpBase> queryWrapper = QueryGenerator.initQueryWrapper(wptpBase, request.getParameterMap());
        List<WptpBase> queryList = wptpBaseService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<WptpBase> wptpBaseList = new ArrayList<WptpBase>();
        if (oConvertUtils.isEmpty(selections)) {
            wptpBaseList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            wptpBaseList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }
        // Step.2 组装pageList
        List<WptpBasePage> pageList = new ArrayList<WptpBasePage>();
        for (WptpBase main : wptpBaseList) {
            WptpBasePage vo = new WptpBasePage();
            BeanUtils.copyProperties(main, vo);
            List<WptpBaseFile> wptpBaseFileList = wptpBaseFileService.selectByMainId(main.getId());
            vo.setWptpBaseFileList(wptpBaseFileList);
            pageList.add(vo);
        }
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        String username = JwtUtil.getUserNameByToken(request);
        mv.addObject(NormalExcelConstants.FILE_NAME, "基地信息管理表列表");
        mv.addObject(NormalExcelConstants.CLASS, WptpBasePage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("基地信息管理表列表数据", "导出人:" + username, "导出信息"));
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
                List<WptpBasePage> list = ExcelImportUtil.importExcel(file.getInputStream(), WptpBasePage.class, params);
                for (WptpBasePage page : list) {
                    WptpBase po = new WptpBase();
                    BeanUtils.copyProperties(page, po);
                    wptpBaseService.saveMain(po, page.getWptpBaseFileList());
                }
                return Result.ok("文件导入成功！数据行数:" + list.size());
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
    private Result<IPage<WptpBaseVO>> handlePageData(IPage<WptpBase> pageList) {

        Result<IPage<WptpBaseVO>> result = new Result<IPage<WptpBaseVO>>();
        Map<String, Object> map = new HashMap<>();//保存查询条件
        IPage<WptpBaseVO> wptpBaseVOIPage = new Page<>();//需要展示的分页数据

        wptpBaseVOIPage.setPages(pageList.getPages());
        wptpBaseVOIPage.setCurrent(pageList.getCurrent());
        wptpBaseVOIPage.setSize(pageList.getSize());
        wptpBaseVOIPage.setTotal(pageList.getTotal());
        List<WptpBase> wptpBaseList = pageList.getRecords();
        List<WptpBaseVO> wptpBaseVOList = new ArrayList<WptpBaseVO>();


        /**
         * 把表中省市区的编码字段，替换成省市区的名字,企业和公司id替换成从企业表里查询的企业名字
         * 把查出来的实体类WptpBase，属性值copy到展示类VO里面
         */
        for (int i = 0; i < wptpBaseList.size(); i++) {
            WptpBase wptpBase = wptpBaseList.get(i);
            WptpBaseVO wptpBaseVO = new WptpBaseVO();
            BeanUtils.copyProperties(wptpBase, wptpBaseVO);
            map.put("admi_code", wptpBase.getProvince());
            List<WptpProvince> wptpProvinces = iWptpProvinceService.getBaseMapper().selectByMap(map);
            if (!wptpProvinces.isEmpty()) wptpBaseVO.setProvinceName(wptpProvinces.get(0).getName());
            map.clear();
            map.put("admi_code", wptpBase.getCity());
            List<WptpCity> wptpCities = iWptpCityService.getBaseMapper().selectByMap(map);
            if (!wptpCities.isEmpty()) wptpBaseVO.setCityName(wptpCities.get(0).getCityName());
            map.clear();
            map.put("admi_code", wptpBase.getArea());
            List<WptpDistrict> wptpDistricts = iWptpDistrictService.getBaseMapper().selectByMap(map);
            if (!wptpDistricts.isEmpty()) wptpBaseVO.setDistrictName(wptpDistricts.get(0).getDisName());
            map.clear();
            map.put("ent_id", wptpBase.getEntId());
            List<WptpEntInfo> wptpEntInfos = iWptpEntInfoService.getBaseMapper().selectByMap(map);
            if (!wptpEntInfos.isEmpty()) wptpBaseVO.setEntName(wptpEntInfos.get(0).getEntName());
            map.clear();
            map.put("ent_id", wptpBase.getBaseCode());
            List<WptpEntInfo> wptpEntInfoBase = iWptpEntInfoService.getBaseMapper().selectByMap(map);
            if (!wptpEntInfoBase.isEmpty()) wptpBaseVO.setUpperEntName(wptpEntInfoBase.get(0).getEntName());
            map.clear();
            wptpBaseVOList.add(wptpBaseVO);
        }
        wptpBaseVOIPage.setRecords(wptpBaseVOList);
        result.setSuccess(true);
        result.setResult(wptpBaseVOIPage);
        return result;
    }
}
