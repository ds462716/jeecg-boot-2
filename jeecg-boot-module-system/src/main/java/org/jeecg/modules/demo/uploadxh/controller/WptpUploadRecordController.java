package org.jeecg.modules.demo.uploadxh.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.uploadxh.entity.WptpUploadRecord;
import org.jeecg.modules.demo.uploadxh.service.IWptpUploadRecordService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

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
import com.alibaba.fastjson.JSON;

/**
 * @Description: 上传日志表
 * @Author: jeecg-boot
 * @Date: 2020-02-15
 * @Version: V1.0
 */
@RestController
@RequestMapping("/uploadxh/wptpUploadRecord")
@Slf4j
@Api(tags = "上传记录")
public class WptpUploadRecordController {
    @Autowired
    private IWptpUploadRecordService wptpUploadRecordService;

    /**
     * 分页列表查询
     *
     * @param wptpUploadRecord
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "上传记录", notes = "上传记录")
    public Result<IPage<WptpUploadRecord>> queryPageList(WptpUploadRecord wptpUploadRecord,
                                                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                         HttpServletRequest req) {
        Result<IPage<WptpUploadRecord>> result = new Result<IPage<WptpUploadRecord>>();
        QueryWrapper<WptpUploadRecord> queryWrapper = QueryGenerator.initQueryWrapper(wptpUploadRecord, req.getParameterMap());
        Page<WptpUploadRecord> page = new Page<WptpUploadRecord>(pageNo, pageSize);
        IPage<WptpUploadRecord> pageList = wptpUploadRecordService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
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
            wptpUploadRecordService.removeById(id);
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
    public Result<WptpUploadRecord> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<WptpUploadRecord> result = new Result<WptpUploadRecord>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.wptpUploadRecordService.removeByIds(Arrays.asList(ids.split(",")));
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
    public Result<WptpUploadRecord> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<WptpUploadRecord> result = new Result<WptpUploadRecord>();
        WptpUploadRecord wptpUploadRecord = wptpUploadRecordService.getById(id);
        if (wptpUploadRecord == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(wptpUploadRecord);
            result.setSuccess(true);
        }
        return result;
    }


}
