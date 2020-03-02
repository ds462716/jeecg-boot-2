package org.jeecg.modules.demo.medicinebinstock.controller;

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
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstock;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstockFile;
import org.jeecg.modules.demo.medicinebinstock.service.IWptpMedicineInstockFileService;
import org.jeecg.modules.demo.medicinebinstock.service.IWptpMedicineInstockService;
import org.jeecg.modules.demo.medicinebinstock.vo.WptpMedicineInstockPage;
import org.jeecg.modules.demo.medicinebinstock.vo.WptpMedicineInstockVO;
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
 * @Description: 药材经营药材入库表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/medicinebinstock/wptpMedicineInstock")
@Slf4j
@Api(tags = "药材经营-药材入库")
public class WptpMedicineInstockController {
    @Autowired
    private IWptpMedicineInstockService wptpMedicineInstockService;
    @Autowired
    private IWptpMedicineInstockFileService wptpMedicineInstockFileService;
    @Autowired
    private IWptpMedicinalService iWptpMedicinalService;

    /**
     * 分页列表查询
     *
     * @param wptpMedicineInstock
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<WptpMedicineInstockVO>> queryPageList(WptpMedicineInstock wptpMedicineInstock,
                                                              @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                              @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                              HttpServletRequest req) {
        QueryWrapper<WptpMedicineInstock> queryWrapper = QueryGenerator.initQueryWrapper(wptpMedicineInstock, req.getParameterMap());
        Page<WptpMedicineInstock> page = new Page<WptpMedicineInstock>(pageNo, pageSize);
        queryWrapper.eq("deleted", "0");
        IPage<WptpMedicineInstock> pageList = wptpMedicineInstockService.page(page, queryWrapper);
        return handlePageData(pageList);
    }

    /**
     * 添加
     *
     * @param wptpMedicineInstockPage
     * @return
     */
    @PostMapping(value = "/add")
    public Result<WptpMedicineInstock> add(@RequestBody WptpMedicineInstockPage wptpMedicineInstockPage, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpMedicineInstock> result = new Result<WptpMedicineInstock>();
        try {
            WptpMedicineInstock wptpMedicineInstock = new WptpMedicineInstock();
            BeanUtils.copyProperties(wptpMedicineInstockPage, wptpMedicineInstock);
            wptpMedicineInstock.setDeleted("0");
            wptpMedicineInstock.setCreateBy(username);
            wptpMedicineInstockService.saveMain(wptpMedicineInstock, wptpMedicineInstockPage.getWptpMedicineInstockFileList());
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
     * @param wptpMedicineInstockPage
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<WptpMedicineInstock> edit(@RequestBody WptpMedicineInstockPage wptpMedicineInstockPage, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpMedicineInstock> result = new Result<WptpMedicineInstock>();
        WptpMedicineInstock wptpMedicineInstock = new WptpMedicineInstock();
        BeanUtils.copyProperties(wptpMedicineInstockPage, wptpMedicineInstock);
        WptpMedicineInstock wptpMedicineInstockEntity = wptpMedicineInstockService.getById(wptpMedicineInstock.getId());
        if (wptpMedicineInstockEntity == null) {
            result.error500("未找到对应实体");
        } else {
            wptpMedicineInstock.setUpdateBy(username);
            boolean ok = wptpMedicineInstockService.updateById(wptpMedicineInstock);
            wptpMedicineInstockService.updateMain(wptpMedicineInstock, wptpMedicineInstockPage.getWptpMedicineInstockFileList());
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
            wptpMedicineInstockService.delMain(id);
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
    public Result<WptpMedicineInstock> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<WptpMedicineInstock> result = new Result<WptpMedicineInstock>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.wptpMedicineInstockService.delBatchMain(Arrays.asList(ids.split(",")));
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
    public Result<WptpMedicineInstock> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<WptpMedicineInstock> result = new Result<WptpMedicineInstock>();
        WptpMedicineInstock wptpMedicineInstock = wptpMedicineInstockService.getById(id);
        if (wptpMedicineInstock == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(wptpMedicineInstock);
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
    @ApiOperation(value = "通过id查询药材经营-药材入库文件", notes = "通过id查询药材经营-药材入库文件")
    @GetMapping(value = "/queryWptpMedicineInstockFileByMainId")
    public Result<List<WptpMedicineInstockFile>> queryWptpMedicineInstockFileListByMainId(@RequestParam(name = "id", required = true) String id) {
        Result<List<WptpMedicineInstockFile>> result = new Result<List<WptpMedicineInstockFile>>();
        WptpMedicineInstock wptpMedicineInstock = wptpMedicineInstockService.getBaseMapper().selectById(id);
        if (oConvertUtils.isEmpty(wptpMedicineInstock)) return new Result().error500("未找到药材入库信息");
        QueryWrapper<WptpMedicineInstockFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_id", wptpMedicineInstock.getInstockNumber());
        queryWrapper.eq("deleted", "0");
        List<WptpMedicineInstockFile> wptpMedicineInstockFileList = wptpMedicineInstockFileService.getBaseMapper().selectList(queryWrapper);
        result.setResult(wptpMedicineInstockFileList);
        result.setSuccess(true);
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wptpMedicineInstock
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WptpMedicineInstock wptpMedicineInstock) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WptpMedicineInstock> queryWrapper = QueryGenerator.initQueryWrapper(wptpMedicineInstock, request.getParameterMap());
        List<WptpMedicineInstock> queryList = wptpMedicineInstockService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<WptpMedicineInstock> wptpMedicineInstockList = new ArrayList<WptpMedicineInstock>();
        if (oConvertUtils.isEmpty(selections)) {
            wptpMedicineInstockList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            wptpMedicineInstockList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }
        // Step.2 组装pageList
        List<WptpMedicineInstockPage> pageList = new ArrayList<WptpMedicineInstockPage>();
        for (WptpMedicineInstock main : wptpMedicineInstockList) {
            WptpMedicineInstockPage vo = new WptpMedicineInstockPage();
            BeanUtils.copyProperties(main, vo);
            List<WptpMedicineInstockFile> wptpMedicineInstockFileList = wptpMedicineInstockFileService.selectByMainId(main.getId());
            vo.setWptpMedicineInstockFileList(wptpMedicineInstockFileList);
            pageList.add(vo);
        }
        // Step.3 AutoPoi 导出Excel
        String username = JwtUtil.getUserNameByToken(request);
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "药材经营药材入库表列表");
        mv.addObject(NormalExcelConstants.CLASS, WptpMedicineInstockPage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("药材经营药材入库表列表数据", "导出人:" + username, "导出信息"));
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
                List<WptpMedicineInstockPage> list = ExcelImportUtil.importExcel(file.getInputStream(), WptpMedicineInstockPage.class, params);
                for (WptpMedicineInstockPage page : list) {
                    WptpMedicineInstock po = new WptpMedicineInstock();
                    BeanUtils.copyProperties(page, po);
                    wptpMedicineInstockService.saveMain(po, page.getWptpMedicineInstockFileList());
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
    private Result<IPage<WptpMedicineInstockVO>> handlePageData(IPage<WptpMedicineInstock> pageList) {

        Result<IPage<WptpMedicineInstockVO>> result = new Result<IPage<WptpMedicineInstockVO>>();
        Map<String, Object> map = new HashMap<>();//保存查询条件
        IPage<WptpMedicineInstockVO> wptpMedicineInstockVOIPage = new Page<>();//需要展示的分页数据

        wptpMedicineInstockVOIPage.setPages(pageList.getPages());
        wptpMedicineInstockVOIPage.setCurrent(pageList.getCurrent());
        wptpMedicineInstockVOIPage.setSize(pageList.getSize());
        wptpMedicineInstockVOIPage.setTotal(pageList.getTotal());
        List<WptpMedicineInstock> wptpMedicineInstockList = pageList.getRecords();
        List<WptpMedicineInstockVO> wptpMedicineInstockVOList = new ArrayList<WptpMedicineInstockVO>();


        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        for (int i = 0; i < wptpMedicineInstockList.size(); i++) {
            WptpMedicineInstock wptpMedicineInstock = wptpMedicineInstockList.get(i);
            WptpMedicineInstockVO wptpMedicineInstockVO = new WptpMedicineInstockVO();
            BeanUtils.copyProperties(wptpMedicineInstock, wptpMedicineInstockVO);

            map.put("name", wptpMedicineInstock.getMedicineName());
            List<WptpMedicinal> wptpMedicinals = iWptpMedicinalService.getBaseMapper().selectByMap(map);
            if (!wptpMedicinals.isEmpty())
                wptpMedicineInstockVO.setMedicinalCode(wptpMedicinals.get(0).getMedicinalCode());
            wptpMedicineInstockVOList.add(wptpMedicineInstockVO);
            map.clear();
        }
        wptpMedicineInstockVOIPage.setRecords(wptpMedicineInstockVOList);
        result.setSuccess(true);
        result.setResult(wptpMedicineInstockVOIPage);
        return result;
    }
}
