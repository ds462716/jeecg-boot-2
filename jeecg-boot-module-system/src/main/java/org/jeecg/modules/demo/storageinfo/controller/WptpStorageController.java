package org.jeecg.modules.demo.storageinfo.controller;

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
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.service.IWptpEntInfoService;
import org.jeecg.modules.demo.province.entity.WptpCity;
import org.jeecg.modules.demo.province.entity.WptpDistrict;
import org.jeecg.modules.demo.province.entity.WptpProvince;
import org.jeecg.modules.demo.province.service.IWptpCityService;
import org.jeecg.modules.demo.province.service.IWptpDistrictService;
import org.jeecg.modules.demo.province.service.IWptpProvinceService;
import org.jeecg.modules.demo.storageinfo.entity.WptpStorage;
import org.jeecg.modules.demo.storageinfo.service.IWptpStorageService;
import org.jeecg.modules.demo.storageinfo.vo.WptpStorageVO;
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
 * @Description: 仓库信息表
 * @Author: jeecg-boot
 * @Date: 2019-10-09
 * @Version: V1.0
 */
@RestController
@RequestMapping("/storageinfo/wptpStorage")
@Slf4j
public class WptpStorageController {
    @Autowired
    private IWptpStorageService wptpStorageService;
    @Autowired
    private IWptpCityService iWptpCityService;
    @Autowired
    private IWptpDistrictService iWptpDistrictService;
    @Autowired
    private IWptpProvinceService iWptpProvinceService;
    @Autowired
    private IWptpEntInfoService iWptpEntInfoService;
    @Autowired
    private IWptpBaseService iWptpBaseService;

    /**
     * 分页列表查询
     *
     * @param wptpStorage
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<WptpStorageVO>> queryPageList(@RequestBody WptpStorage wptpStorage,
                                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                      HttpServletRequest req) {
        QueryWrapper<WptpStorage> queryWrapper = QueryGenerator.initQueryWrapper(wptpStorage, req.getParameterMap());
        Page<WptpStorage> page = new Page<WptpStorage>(pageNo, pageSize);
        queryWrapper.eq("deleted", "0");
        IPage<WptpStorage> pageList = wptpStorageService.page(page, queryWrapper);
        return handlePageData(pageList);
    }

    /**
     * 添加
     *
     * @param wptpStorage
     * @return
     */
    @PostMapping(value = "/add")
    public Result<WptpStorage> add(@RequestBody WptpStorage wptpStorage, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpStorage> result = new Result<WptpStorage>();
        try {
            wptpStorage.setDeleted("0");
            wptpStorage.setCreateBy(username);
            wptpStorageService.save(wptpStorage);
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
     * @param wptpStorage
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<WptpStorage> edit(@RequestBody WptpStorage wptpStorage, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpStorage> result = new Result<WptpStorage>();
        WptpStorage wptpStorageEntity = wptpStorageService.getById(wptpStorage.getId());
        if (wptpStorageEntity == null) {
            result.error500("未找到对应实体");
        } else {
            wptpStorage.setUpdateBy(username);
            boolean ok = wptpStorageService.updateById(wptpStorage);
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
            wptpStorageService.removeById(id);
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
    public Result<WptpStorage> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<WptpStorage> result = new Result<WptpStorage>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.wptpStorageService.removeByIds(Arrays.asList(ids.split(",")));
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
    public Result<WptpStorage> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<WptpStorage> result = new Result<WptpStorage>();
        WptpStorage wptpStorage = wptpStorageService.getById(id);
        if (wptpStorage == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(wptpStorage);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wptpStorage
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WptpStorage wptpStorage) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WptpStorage> queryWrapper = QueryGenerator.initQueryWrapper(wptpStorage, request.getParameterMap());
        List<WptpStorage> pageList = wptpStorageService.list(queryWrapper);
        // Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isEmpty(selections)) {
            mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            List<WptpStorage> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
            mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        }
        //导出文件名称
        String username = JwtUtil.getUserNameByToken(request);
        mv.addObject(NormalExcelConstants.FILE_NAME, "仓库信息表列表");
        mv.addObject(NormalExcelConstants.CLASS, WptpStorage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("仓库信息表列表数据", "导出人:" + username, "导出信息"));
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
                List<WptpStorage> listWptpStorages = ExcelImportUtil.importExcel(file.getInputStream(), WptpStorage.class, params);
                wptpStorageService.saveBatch(listWptpStorages);
                return Result.ok("文件导入成功！数据行数:" + listWptpStorages.size());
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
    private Result<IPage<WptpStorageVO>> handlePageData(IPage<WptpStorage> pageList) {

        Result<IPage<WptpStorageVO>> result = new Result<IPage<WptpStorageVO>>();
        Map<String, Object> map = new HashMap<>();//保存查询条件
        IPage<WptpStorageVO> wptpStorageVOIPage = new Page<>();//需要展示的分页数据

        wptpStorageVOIPage.setPages(pageList.getPages());
        wptpStorageVOIPage.setCurrent(pageList.getCurrent());
        wptpStorageVOIPage.setSize(pageList.getSize());
        wptpStorageVOIPage.setTotal(pageList.getTotal());
        List<WptpStorage> wptpStorageList = pageList.getRecords();
        List<WptpStorageVO> wptpStorageVOList = new ArrayList<WptpStorageVO>();


        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        for (int i = 0; i < wptpStorageList.size(); i++) {
            WptpStorage wptpStorage = wptpStorageList.get(i);
            WptpStorageVO wptpStorageVO = new WptpStorageVO();
            BeanUtils.copyProperties(wptpStorage, wptpStorageVO);
            map.put("admi_code", wptpStorage.getProvince());
            List<WptpProvince> wptpProvinces = iWptpProvinceService.getBaseMapper().selectByMap(map);
            if (!wptpProvinces.isEmpty()) wptpStorageVO.setProvinceName(wptpProvinces.get(0).getName());
            map.clear();
            map.put("admi_code", wptpStorage.getCity());
            List<WptpCity> wptpCities = iWptpCityService.getBaseMapper().selectByMap(map);
            if (!wptpCities.isEmpty()) wptpStorageVO.setCityName(wptpCities.get(0).getCityName());
            map.clear();
            map.put("admi_code", wptpStorage.getDistrict());
            List<WptpDistrict> wptpDistricts = iWptpDistrictService.getBaseMapper().selectByMap(map);
            if (!wptpDistricts.isEmpty()) wptpStorageVO.setDistrictName(wptpDistricts.get(0).getDisName());
            map.clear();
            map.put("ent_id", wptpStorage.getEntId());
            List<WptpEntInfo> wptpEntInfos = iWptpEntInfoService.getBaseMapper().selectByMap(map);
            if (!wptpEntInfos.isEmpty()) wptpStorageVO.setEntName(wptpEntInfos.get(0).getEntName());
            map.clear();
            map.put("base_code", wptpStorage.getBaseCode());
            List<WptpBase> wptpBases = iWptpBaseService.getBaseMapper().selectByMap(map);
            if (!wptpBases.isEmpty()) wptpStorageVO.setBaseName(wptpBases.get(0).getBaseName());
            map.clear();
            wptpStorageVOList.add(wptpStorageVO);
        }
        wptpStorageVOIPage.setRecords(wptpStorageVOList);
        result.setSuccess(true);
        result.setResult(wptpStorageVOIPage);
        return result;
    }
}
