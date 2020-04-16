package org.jeecg.modules.demo.hostcode.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.hostcode.entity.WptpHostcode;
import org.jeecg.modules.demo.hostcode.service.IWptpHostcodeService;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 主机代码表
 * @Author: jeecg-boot
 * @Date: 2019-10-09
 * @Version: V1.0
 */
@RestController
@RequestMapping("/hostcode/wptpHostcode")
@Slf4j
public class WptpHostcodeController {
    @Autowired
    private IWptpHostcodeService wptpHostcodeService;

    /**
     * 分页列表查询
     *
     * @param wptpHostcode
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<WptpHostcode>> queryPageList(WptpHostcode wptpHostcode,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        Result<IPage<WptpHostcode>> result = new Result<IPage<WptpHostcode>>();
        QueryWrapper<WptpHostcode> queryWrapper = QueryGenerator.initQueryWrapper(wptpHostcode, req.getParameterMap());
        Page<WptpHostcode> page = new Page<WptpHostcode>(pageNo, pageSize);
        queryWrapper.eq("deleted", "0");
        IPage<WptpHostcode> pageList = wptpHostcodeService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param wptpHostcode
     * @return
     */
    @PostMapping(value = "/add")
    public Result<WptpHostcode> add(@RequestBody WptpHostcode wptpHostcode, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpHostcode> result = new Result<WptpHostcode>();
        try {
            wptpHostcode.setCreateBy(username);
            wptpHostcodeService.save(wptpHostcode);
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
     * @param wptpHostcode
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<WptpHostcode> edit(@RequestBody WptpHostcode wptpHostcode, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpHostcode> result = new Result<WptpHostcode>();
        WptpHostcode wptpHostcodeEntity = wptpHostcodeService.getById(wptpHostcode.getId());
        if (wptpHostcodeEntity == null) {
            result.error500("未找到对应实体");
        } else {
            wptpHostcode.setUpdateBy(username);
            boolean ok = wptpHostcodeService.updateById(wptpHostcode);
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
            wptpHostcodeService.removeById(id);
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
    public Result<WptpHostcode> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<WptpHostcode> result = new Result<WptpHostcode>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.wptpHostcodeService.removeByIds(Arrays.asList(ids.split(",")));
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
    public Result<WptpHostcode> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<WptpHostcode> result = new Result<WptpHostcode>();
        WptpHostcode wptpHostcode = wptpHostcodeService.getById(id);
        if (wptpHostcode == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(wptpHostcode);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wptpHostcode
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WptpHostcode wptpHostcode) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WptpHostcode> queryWrapper = QueryGenerator.initQueryWrapper(wptpHostcode, request.getParameterMap());
        List<WptpHostcode> pageList = wptpHostcodeService.list(queryWrapper);
        // Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isEmpty(selections)) {
            mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            List<WptpHostcode> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
            mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        }
        //导出文件名称
        mv.addObject(NormalExcelConstants.FILE_NAME, "主机代码表列表");
        mv.addObject(NormalExcelConstants.CLASS, WptpHostcode.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("主机代码表列表数据", "导出人:Jeecg", "导出信息"));
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
                List<WptpHostcode> listWptpHostcodes = ExcelImportUtil.importExcel(file.getInputStream(), WptpHostcode.class, params);
                wptpHostcodeService.saveBatch(listWptpHostcodes);
                return Result.ok("文件导入成功！数据行数:" + listWptpHostcodes.size());
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
