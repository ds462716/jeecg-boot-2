package org.jeecg.modules.demo.processinfo.controller;

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
import org.jeecg.modules.demo.processinfo.entity.WptpProcessFile;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessInfo;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessFileService;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessInfoService;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessMaterial;
import org.jeecg.modules.demo.processinfo.vo.WptpProcessInfoPage;
import org.jeecg.modules.demo.processinfo.vo.WptpProcessInfoVO;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 产地初加工表
 * @Author: jeecg-boot
 * @Date: 2019-10-11
 * @Version: V1.0
 */
@RestController
@RequestMapping("/processinfo/wptpProcessInfo")
@Slf4j
@Api(tags = "种植-初加工")
public class WptpProcessInfoController {
    @Autowired
    private IWptpProcessInfoService wptpProcessInfoService;
    @Autowired
    private IWptpProcessFileService wptpProcessFileService;
    @Autowired
    private IWptpProcessMaterial iWptpProcessMaterial;

    /**
     * 分页列表查询
     *
     * @param wptpProcessInfo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<WptpProcessInfoVO>> queryPageList(WptpProcessInfo wptpProcessInfo,
                                                          @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                          HttpServletRequest req) {
        Result<IPage<WptpProcessInfoVO>> result = new Result<IPage<WptpProcessInfoVO>>();
        QueryWrapper<WptpProcessInfo> queryWrapper = QueryGenerator.initQueryWrapper(wptpProcessInfo, req.getParameterMap());
        Page<WptpProcessInfo> page = new Page<WptpProcessInfo>(pageNo, pageSize);
        queryWrapper.eq("deleted", "0");
        IPage<WptpProcessInfo> pageList = wptpProcessInfoService.page(page, queryWrapper);
        IPage<WptpProcessInfoVO> wptpProcessInfoVOIPage = hanldeData(pageList);
        result.setSuccess(true);
        result.setResult(wptpProcessInfoVOIPage);
        return result;
    }

    private IPage<WptpProcessInfoVO> hanldeData(IPage<WptpProcessInfo> pageList) {
        List<WptpProcessInfo> records = pageList.getRecords();

        List<WptpProcessInfoVO> wptpProcessInfoVOList = new ArrayList<>();
        for (WptpProcessInfo w :
                records) {
            WptpProcessInfoVO wptpProcessInfoVO = new WptpProcessInfoVO();
            QueryWrapper<WptpProcessMaterial> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("process_no", w.getProcessNo());
            queryWrapper.eq("deleted", "0");
            List<WptpProcessMaterial> wptpProcessMaterialList = iWptpProcessMaterial.getBaseMapper().selectList(queryWrapper);
            BeanUtils.copyProperties(w, wptpProcessInfoVO);
            wptpProcessInfoVO.setBatch(wptpProcessMaterialList);
            wptpProcessInfoVOList.add(wptpProcessInfoVO);
        }
        IPage<WptpProcessInfoVO> wptpProcessInfoVOIPage = new Page<WptpProcessInfoVO>();
        BeanUtils.copyProperties(pageList, wptpProcessInfoVOIPage);

        wptpProcessInfoVOIPage.setRecords(wptpProcessInfoVOList);
        return wptpProcessInfoVOIPage;


    }

    /**
     * 添加
     *
     * @param wptpProcessInfoPage
     * @return
     */
    @PostMapping(value = "/add")
    public Result<WptpProcessInfo> add(@RequestBody WptpProcessInfoPage wptpProcessInfoPage, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpProcessInfo> result = new Result<WptpProcessInfo>();
        try {
            WptpProcessInfo wptpProcessInfo = new WptpProcessInfo();
            BeanUtils.copyProperties(wptpProcessInfoPage, wptpProcessInfo);
            wptpProcessInfo.setDeleted("0");
            wptpProcessInfo.setCreateBy(username);
            wptpProcessInfoService.saveMain(wptpProcessInfo, wptpProcessInfoPage.getWptpProcessFileList());
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
     * @param wptpProcessInfoPage
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<WptpProcessInfo> edit(@RequestBody WptpProcessInfoPage wptpProcessInfoPage, HttpServletRequest request) {
        String username = JwtUtil.getUserNameByToken(request);
        Result<WptpProcessInfo> result = new Result<WptpProcessInfo>();
        WptpProcessInfo wptpProcessInfo = new WptpProcessInfo();
        BeanUtils.copyProperties(wptpProcessInfoPage, wptpProcessInfo);
        WptpProcessInfo wptpProcessInfoEntity = wptpProcessInfoService.getById(wptpProcessInfo.getId());
        if (wptpProcessInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            wptpProcessInfo.setUpdateBy(username);
            boolean ok = wptpProcessInfoService.updateById(wptpProcessInfo);
            wptpProcessInfoService.updateMain(wptpProcessInfo, wptpProcessInfoPage.getWptpProcessFileList());
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
            wptpProcessInfoService.delMain(id);
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
    public Result<WptpProcessInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<WptpProcessInfo> result = new Result<WptpProcessInfo>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.wptpProcessInfoService.delBatchMain(Arrays.asList(ids.split(",")));
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
    public Result<WptpProcessInfo> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<WptpProcessInfo> result = new Result<WptpProcessInfo>();
        WptpProcessInfo wptpProcessInfo = wptpProcessInfoService.getById(id);
        if (wptpProcessInfo == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(wptpProcessInfo);
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
    @ApiOperation(value = "通过id查询种植-初加工文件", notes = "通过id查询种植-初加工文件")
    @GetMapping(value = "/queryWptpProcessFileByMainId")
    public Result<List<WptpProcessFile>> queryWptpProcessFileListByMainId(@RequestParam(name = "id", required = true) String id) {
        Result<List<WptpProcessFile>> result = new Result<List<WptpProcessFile>>();
        WptpProcessInfo wptpProcessInfo = wptpProcessInfoService.getBaseMapper().selectById(id);
        if (oConvertUtils.isEmpty(wptpProcessInfo)) return new Result().error500("未找到初加工信息");
        QueryWrapper<WptpProcessFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_id", wptpProcessInfo.getProcessNo());
        queryWrapper.eq("deleted", "0");
        List<WptpProcessFile> wptpProcessFileList = wptpProcessFileService.getBaseMapper().selectList(queryWrapper);
        result.setResult(wptpProcessFileList);
        result.setSuccess(true);
        return result;
    }

    /**
     * @param id
     * @return
     */
    @GetMapping(value = "/queryWptpProcessMaterialByMainId")
    public Result<List<WptpProcessMaterial>> queryWptpProcessMaterialByMainId(@RequestParam(name = "processNo", required = true) String processNo) {
        Result<List<WptpProcessMaterial>> result = new Result<List<WptpProcessMaterial>>();
        QueryWrapper<WptpProcessMaterial> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("process_no", processNo);
        List<WptpProcessMaterial> wptpProcessMaterialList = iWptpProcessMaterial.getBaseMapper().selectList(queryWrapper);
        result.setResult(wptpProcessMaterialList);
        result.setSuccess(true);
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WptpProcessInfo wptpProcessInfo) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WptpProcessInfo> queryWrapper = QueryGenerator.initQueryWrapper(wptpProcessInfo, request.getParameterMap());
        List<WptpProcessInfo> queryList = wptpProcessInfoService.list(queryWrapper);
        // 过滤选中数据
        String selections = request.getParameter("selections");
        List<WptpProcessInfo> wptpProcessInfoList = new ArrayList<WptpProcessInfo>();
        if (oConvertUtils.isEmpty(selections)) {
            wptpProcessInfoList = queryList;
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            wptpProcessInfoList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
        }
        // Step.2 组装pageList
        List<WptpProcessInfoPage> pageList = new ArrayList<WptpProcessInfoPage>();
        for (WptpProcessInfo main : wptpProcessInfoList) {
            WptpProcessInfoPage vo = new WptpProcessInfoPage();
            BeanUtils.copyProperties(main, vo);
            List<WptpProcessFile> wptpProcessFileList = wptpProcessFileService.selectByMainId(main.getId());
            vo.setWptpProcessFileList(wptpProcessFileList);
            pageList.add(vo);
        }
        // Step.3 AutoPoi 导出Excel
        String username = JwtUtil.getUserNameByToken(request);
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "产地初加工表列表");
        mv.addObject(NormalExcelConstants.CLASS, WptpProcessInfoPage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("产地初加工表列表数据", "导出人:" + username, "导出信息"));
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
                List<WptpProcessInfoPage> list = ExcelImportUtil.importExcel(file.getInputStream(), WptpProcessInfoPage.class, params);
                for (WptpProcessInfoPage page : list) {
                    WptpProcessInfo po = new WptpProcessInfo();
                    BeanUtils.copyProperties(page, po);
                    wptpProcessInfoService.saveMain(po, page.getWptpProcessFileList());
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

}
