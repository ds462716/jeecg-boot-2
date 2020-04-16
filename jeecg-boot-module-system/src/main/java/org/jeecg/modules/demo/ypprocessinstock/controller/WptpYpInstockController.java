package org.jeecg.modules.demo.ypprocessinstock.controller;

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
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstock;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstockFile;
import org.jeecg.modules.demo.ypprocessinstock.service.IWptpYpInstockFileService;
import org.jeecg.modules.demo.ypprocessinstock.service.IWptpYpInstockService;
import org.jeecg.modules.demo.ypprocessinstock.vo.WptpYpInstockPage;
import org.jeecg.modules.demo.ypprocessinstock.vo.WptpYpInstockVO;
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
 * @Description: 饮片加工-药材入库表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/ypprocessinstock/wptpYpInstock")
@Slf4j
@Api(tags = "饮片加工-饮片入库")
public class WptpYpInstockController {
    @Autowired
    private IWptpYpInstockService wptpYpInstockService;
    @Autowired
    private IWptpYpInstockFileService wptpYpInstockFileService;
    @Autowired
    private IWptpMedicinalService iWptpMedicinalService;
    @Autowired
    private IWptpEntInfoService iWptpEntInfoService;

    /**
     * 分页列表查询
     *
     * @param wptpYpInstock
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<WptpYpInstockVO>> queryPageList(WptpYpInstock wptpYpInstock,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest req) {
        QueryWrapper<WptpYpInstock> queryWrapper = QueryGenerator.initQueryWrapper(wptpYpInstock, req.getParameterMap());
        Page<WptpYpInstock> page = new Page<WptpYpInstock>(pageNo, pageSize);
        queryWrapper.eq("deleted", "0");
        IPage<WptpYpInstock> pageList = wptpYpInstockService.page(page, queryWrapper);
        return handlePageData(pageList);
    }

    /**
     * 添加
     *
     * @param wptpYpInstockPage
     * @return
     */
    @PostMapping(value = "/add")
    public Result<WptpYpInstock> add(@RequestBody WptpYpInstockPage wptpYpInstockPage, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpYpInstock> result = new Result<WptpYpInstock>();
        try {
            WptpYpInstock wptpYpInstock = new WptpYpInstock();
            BeanUtils.copyProperties(wptpYpInstockPage, wptpYpInstock);
            wptpYpInstock.setDeleted("0");
            wptpYpInstock.setCreateBy(username);
            wptpYpInstockService.saveMain(wptpYpInstock, wptpYpInstockPage.getWptpYpInstockFileList());
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
     * @param wptpYpInstockPage
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<WptpYpInstock> edit(@RequestBody WptpYpInstockPage wptpYpInstockPage, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpYpInstock> result = new Result<WptpYpInstock>();
        WptpYpInstock wptpYpInstock = new WptpYpInstock();
        BeanUtils.copyProperties(wptpYpInstockPage, wptpYpInstock);
        WptpYpInstock wptpYpInstockEntity = wptpYpInstockService.getById(wptpYpInstock.getId());
        if (wptpYpInstockEntity == null) {
            result.error500("未找到对应实体");
        } else {
            wptpYpInstock.setUpdateBy(username);
            boolean ok = wptpYpInstockService.updateById(wptpYpInstock);
            wptpYpInstockService.updateMain(wptpYpInstock, wptpYpInstockPage.getWptpYpInstockFileList());
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
            wptpYpInstockService.delMain(id);
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
    public Result<WptpYpInstock> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<WptpYpInstock> result = new Result<WptpYpInstock>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.wptpYpInstockService.delBatchMain(Arrays.asList(ids.split(",")));
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
    public Result<WptpYpInstock> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<WptpYpInstock> result = new Result<WptpYpInstock>();
        WptpYpInstock wptpYpInstock = wptpYpInstockService.getById(id);
        if (wptpYpInstock == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(wptpYpInstock);
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
    @ApiOperation(value = "通过id查询饮片加工-饮片入库文件", notes = "通过id查询饮片加工-饮片入库文件")
    @GetMapping(value = "/queryWptpYpInstockFileByMainId")
    public Result<List<WptpYpInstockFile>> queryWptpYpInstockFileListByMainId(@RequestParam(name = "id", required = true) String id) {
        Result<List<WptpYpInstockFile>> result = new Result();
        WptpYpInstock wptpYpInstock = wptpYpInstockService.getBaseMapper().selectById(id);
        if (oConvertUtils.isEmpty(wptpYpInstock)) return new Result().error500("未找到饮片入库信息");
        QueryWrapper<WptpYpInstockFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_id", wptpYpInstock.getInstockNumber());
        queryWrapper.eq("deleted", "0");
        List<WptpYpInstockFile> wptpYpInstockFileList = wptpYpInstockFileService.getBaseMapper().selectList(queryWrapper);
        result.setResult(wptpYpInstockFileList);
        result.setSuccess(true);
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wptpYpInstock
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WptpYpInstock wptpYpInstock) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WptpYpInstock> queryWrapper = QueryGenerator.initQueryWrapper(wptpYpInstock, request.getParameterMap());
        List<WptpYpInstock> queryList = wptpYpInstockService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<WptpYpInstock> wptpYpInstockList = new ArrayList<WptpYpInstock>();
        if (oConvertUtils.isEmpty(selections)) {
            wptpYpInstockList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            wptpYpInstockList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }
        // Step.2 组装pageList
        List<WptpYpInstockPage> pageList = new ArrayList<WptpYpInstockPage>();
        for (WptpYpInstock main : wptpYpInstockList) {
            WptpYpInstockPage vo = new WptpYpInstockPage();
            BeanUtils.copyProperties(main, vo);
            List<WptpYpInstockFile> wptpYpInstockFileList = wptpYpInstockFileService.selectByMainId(main.getId());
            vo.setWptpYpInstockFileList(wptpYpInstockFileList);
            pageList.add(vo);
        }
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        String username = JwtUtil.getUserNameByToken(request);
        mv.addObject(NormalExcelConstants.FILE_NAME, "饮片加工-药材入库表列表");
        mv.addObject(NormalExcelConstants.CLASS, WptpYpInstockPage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("饮片加工-药材入库表列表数据", "导出人:" + username, "导出信息"));
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
                List<WptpYpInstockPage> list = ExcelImportUtil.importExcel(file.getInputStream(), WptpYpInstockPage.class, params);
                for (WptpYpInstockPage page : list) {
                    WptpYpInstock po = new WptpYpInstock();
                    BeanUtils.copyProperties(page, po);
                    wptpYpInstockService.saveMain(po, page.getWptpYpInstockFileList());
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
    private Result<IPage<WptpYpInstockVO>> handlePageData(IPage<WptpYpInstock> pageList) {

        Result<IPage<WptpYpInstockVO>> result = new Result<IPage<WptpYpInstockVO>>();
        Map<String, Object> map = new HashMap<>();//保存查询条件
        IPage<WptpYpInstockVO> wptpYpInstockVOIPage = new Page<>();//需要展示的分页数据

        wptpYpInstockVOIPage.setPages(pageList.getPages());
        wptpYpInstockVOIPage.setCurrent(pageList.getCurrent());
        wptpYpInstockVOIPage.setSize(pageList.getSize());
        wptpYpInstockVOIPage.setTotal(pageList.getTotal());
        List<WptpYpInstock> wptpYpInstockList = pageList.getRecords();
        List<WptpYpInstockVO> wptpYpInstockVOList = new ArrayList<WptpYpInstockVO>();


        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        for (int i = 0; i < wptpYpInstockList.size(); i++) {
            WptpYpInstock wptpYpInstock = wptpYpInstockList.get(i);
            WptpYpInstockVO wptpYpInstockVO = new WptpYpInstockVO();
            BeanUtils.copyProperties(wptpYpInstock, wptpYpInstockVO);

            map.put("name", wptpYpInstock.getMaterialName());
            List<WptpMedicinal> wptpMedicinals = iWptpMedicinalService.getBaseMapper().selectByMap(map);
            if (!wptpMedicinals.isEmpty()) wptpYpInstockVO.setMedicinalCode(wptpMedicinals.get(0).getMedicinalCode());
            map.clear();
            map.put("ent_id", wptpYpInstock.getEntId());
            List<WptpEntInfo> wptpEntInfos = iWptpEntInfoService.getBaseMapper().selectByMap(map);
            if (!wptpEntInfos.isEmpty()) wptpYpInstockVO.setEntName(wptpEntInfos.get(0).getEntName());
            map.clear();
            wptpYpInstockVOList.add(wptpYpInstockVO);
        }
        wptpYpInstockVOIPage.setRecords(wptpYpInstockVOList);
        result.setSuccess(true);
        result.setResult(wptpYpInstockVOIPage);
        return result;
    }
}
