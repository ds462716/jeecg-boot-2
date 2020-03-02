package org.jeecg.modules.demo.ypbinstock.controller;

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
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstock;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstockFile;
import org.jeecg.modules.demo.ypbinstock.service.IWptpYpbInstockFileService;
import org.jeecg.modules.demo.ypbinstock.service.IWptpYpbInstockService;
import org.jeecg.modules.demo.ypbinstock.vo.WptpYpbInstockPage;
import org.jeecg.modules.demo.ypbinstock.vo.WptpYpbInstockVO;
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
 * @Description: 饮片经营药材入库
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/ypbinstock/wptpYpbInstock")
@Slf4j
@Api(tags = "饮片经营-饮片入库")
public class WptpYpbInstockController {
    @Autowired
    private IWptpYpbInstockService wptpYpbInstockService;
    @Autowired
    private IWptpYpbInstockFileService wptpYpbInstockFileService;
    @Autowired
    private IWptpMedicinalService iWptpMedicinalService;

    /**
     * 分页列表查询
     *
     * @param wptpYpbInstock
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<WptpYpbInstockVO>> queryPageList(WptpYpbInstock wptpYpbInstock,
                                                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                         HttpServletRequest req) {
        QueryWrapper<WptpYpbInstock> queryWrapper = QueryGenerator.initQueryWrapper(wptpYpbInstock, req.getParameterMap());
        Page<WptpYpbInstock> page = new Page<WptpYpbInstock>(pageNo, pageSize);
        queryWrapper.eq("deleted", "0");
        IPage<WptpYpbInstock> pageList = wptpYpbInstockService.page(page, queryWrapper);
        return handlePageData(pageList);
    }

    /**
     * 添加
     *
     * @param wptpYpbInstockPage
     * @return
     */
    @PostMapping(value = "/add")
    public Result<WptpYpbInstock> add(@RequestBody WptpYpbInstockPage wptpYpbInstockPage, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpYpbInstock> result = new Result<WptpYpbInstock>();
        try {
            WptpYpbInstock wptpYpbInstock = new WptpYpbInstock();
            BeanUtils.copyProperties(wptpYpbInstockPage, wptpYpbInstock);
            wptpYpbInstock.setDeleted("0");
            wptpYpbInstock.setCreateBy(username);
            wptpYpbInstockService.saveMain(wptpYpbInstock, wptpYpbInstockPage.getWptpYpbInstockFileList());
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
     * @param wptpYpbInstockPage
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<WptpYpbInstock> edit(@RequestBody WptpYpbInstockPage wptpYpbInstockPage, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpYpbInstock> result = new Result<WptpYpbInstock>();
        WptpYpbInstock wptpYpbInstock = new WptpYpbInstock();
        BeanUtils.copyProperties(wptpYpbInstockPage, wptpYpbInstock);
        WptpYpbInstock wptpYpbInstockEntity = wptpYpbInstockService.getById(wptpYpbInstock.getId());
        if (wptpYpbInstockEntity == null) {
            result.error500("未找到对应实体");
        } else {
            wptpYpbInstock.setUpdateBy(username);
            boolean ok = wptpYpbInstockService.updateById(wptpYpbInstock);
            wptpYpbInstockService.updateMain(wptpYpbInstock, wptpYpbInstockPage.getWptpYpbInstockFileList());
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
            wptpYpbInstockService.delMain(id);
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
    public Result<WptpYpbInstock> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<WptpYpbInstock> result = new Result<WptpYpbInstock>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.wptpYpbInstockService.delBatchMain(Arrays.asList(ids.split(",")));
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
    public Result<WptpYpbInstock> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<WptpYpbInstock> result = new Result<WptpYpbInstock>();
        WptpYpbInstock wptpYpbInstock = wptpYpbInstockService.getById(id);
        if (wptpYpbInstock == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(wptpYpbInstock);
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
    @ApiOperation(value = "通过id查询饮片经营-饮片入库文件", notes = "通过id查询饮片经营-饮片入库文件")
    @GetMapping(value = "/queryWptpYpbInstockFileByMainId")
    public Result<List<WptpYpbInstockFile>> queryWptpYpbInstockFileListByMainId(@RequestParam(name = "id", required = true) String id) {
        Result<List<WptpYpbInstockFile>> result = new Result();
        WptpYpbInstock wptpYpbInstock = wptpYpbInstockService.getBaseMapper().selectById(id);
        if (oConvertUtils.isEmpty(wptpYpbInstock)) return new Result().error500("未找到饮片入库信息");
        QueryWrapper<WptpYpbInstockFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_id", wptpYpbInstock.getInstockNumber());
        queryWrapper.eq("deleted", "0");
        List<WptpYpbInstockFile> wptpYpbInstockFileList = wptpYpbInstockFileService.getBaseMapper().selectList(queryWrapper);
        result.setResult(wptpYpbInstockFileList);
        result.setSuccess(true);
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wptpYpbInstock
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WptpYpbInstock wptpYpbInstock) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WptpYpbInstock> queryWrapper = QueryGenerator.initQueryWrapper(wptpYpbInstock, request.getParameterMap());
        List<WptpYpbInstock> queryList = wptpYpbInstockService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<WptpYpbInstock> wptpYpbInstockList = new ArrayList<WptpYpbInstock>();
        if (oConvertUtils.isEmpty(selections)) {
            wptpYpbInstockList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            wptpYpbInstockList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }
        // Step.2 组装pageList
        List<WptpYpbInstockPage> pageList = new ArrayList<WptpYpbInstockPage>();
        for (WptpYpbInstock main : wptpYpbInstockList) {
            WptpYpbInstockPage vo = new WptpYpbInstockPage();
            BeanUtils.copyProperties(main, vo);
            List<WptpYpbInstockFile> wptpYpbInstockFileList = wptpYpbInstockFileService.selectByMainId(main.getId());
            vo.setWptpYpbInstockFileList(wptpYpbInstockFileList);
            pageList.add(vo);
        }
        // Step.3 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        String username = JwtUtil.getUserNameByToken(request);
        mv.addObject(NormalExcelConstants.FILE_NAME, "饮片经营药材入库列表");
        mv.addObject(NormalExcelConstants.CLASS, WptpYpbInstockPage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("饮片经营药材入库列表数据", "导出人:" + username, "导出信息"));
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
                List<WptpYpbInstockPage> list = ExcelImportUtil.importExcel(file.getInputStream(), WptpYpbInstockPage.class, params);
                for (WptpYpbInstockPage page : list) {
                    WptpYpbInstock po = new WptpYpbInstock();
                    BeanUtils.copyProperties(page, po);
                    wptpYpbInstockService.saveMain(po, page.getWptpYpbInstockFileList());
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
    private Result<IPage<WptpYpbInstockVO>> handlePageData(IPage<WptpYpbInstock> pageList) {

        Result<IPage<WptpYpbInstockVO>> result = new Result<IPage<WptpYpbInstockVO>>();
        Map<String, Object> map = new HashMap<>();//保存查询条件
        IPage<WptpYpbInstockVO> wptpYpbInstockVOIPage = new Page<>();//需要展示的分页数据

        wptpYpbInstockVOIPage.setPages(pageList.getPages());
        wptpYpbInstockVOIPage.setCurrent(pageList.getCurrent());
        wptpYpbInstockVOIPage.setSize(pageList.getSize());
        wptpYpbInstockVOIPage.setTotal(pageList.getTotal());
        List<WptpYpbInstock> wptpYpbInstockList = pageList.getRecords();
        List<WptpYpbInstockVO> wptpYpbInstockVOList = new ArrayList<WptpYpbInstockVO>();


        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        for (int i = 0; i < wptpYpbInstockList.size(); i++) {
            WptpYpbInstock wptpYpbInstock = wptpYpbInstockList.get(i);
            WptpYpbInstockVO wptpYpbInstockVO = new WptpYpbInstockVO();
            BeanUtils.copyProperties(wptpYpbInstock, wptpYpbInstockVO);

            map.put("medicinal_code", wptpYpbInstock.getCategoryCode());
            List<WptpMedicinal> wptpMedicinals = iWptpMedicinalService.getBaseMapper().selectByMap(map);
            if (!wptpMedicinals.isEmpty()) wptpYpbInstockVO.setMedicinalName(wptpMedicinals.get(0).getName());
            map.clear();

            wptpYpbInstockVOList.add(wptpYpbInstockVO);
        }
        wptpYpbInstockVOIPage.setRecords(wptpYpbInstockVOList);
        result.setSuccess(true);
        result.setResult(wptpYpbInstockVOIPage);
        return result;
    }
}
