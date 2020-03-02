package org.jeecg.modules.demo.csinfo.controller;

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
import org.jeecg.modules.demo.blockmedicinal.entity.WptpBlockMeidicinal;
import org.jeecg.modules.demo.blockmedicinal.service.IWptpBlockMeidicinalService;
import org.jeecg.modules.demo.csinfo.entity.WptpCsInfo;
import org.jeecg.modules.demo.csinfo.service.IWptpCsInfoService;
import org.jeecg.modules.demo.csinfo.vo.WptpCsInfoVO;
import org.jeecg.modules.demo.entinfo.entity.WptpEntInfo;
import org.jeecg.modules.demo.entinfo.service.IWptpEntInfoService;
import org.jeecg.modules.demo.medicinalinfo.entity.WptpMedicinal;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
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
 * @Description: 采收批次信息
 * @Author: jeecg-boot
 * @Date: 2019-10-16
 * @Version: V1.0
 */
@RestController
@RequestMapping("/csinfo/wptpCsInfo")
@Slf4j
public class WptpCsInfoController {
    @Autowired
    private IWptpCsInfoService wptpCsInfoService;
    @Autowired
    private IWptpBlockMeidicinalService iWptpBlockMeidicinalService;
    @Autowired
    private IWptpMedicinalService iWptpMedicinalService;
    @Autowired
    private IWptpEntInfoService iWptpEntInfoService;
    @Autowired
    private IWptpBaseService iWptpBaseService;

    /**
     * 分页列表查询
     *
     * @param wptpCsInfo
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @GetMapping(value = "/list")
    public Result<IPage<WptpCsInfoVO>> queryPageList(WptpCsInfo wptpCsInfo,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        QueryWrapper<WptpCsInfo> queryWrapper = QueryGenerator.initQueryWrapper(wptpCsInfo, req.getParameterMap());
        Page<WptpCsInfo> page = new Page<WptpCsInfo>(pageNo, pageSize);
        queryWrapper.eq("deleted", "0");
        IPage<WptpCsInfo> pageList = wptpCsInfoService.page(page, queryWrapper);
        return handlePageData(pageList);
    }

    /**
     * 添加
     *
     * @param wptpCsInfo
     * @return
     */
    @PostMapping(value = "/add")
    public Result<WptpCsInfo> add(@RequestBody WptpCsInfo wptpCsInfo, HttpServletRequest request) {
        Result<WptpCsInfo> result = new Result<WptpCsInfo>();
        String username = JwtUtil.getUserNameByToken(request);
        try {
            wptpCsInfo.setDeleted("0");
            wptpCsInfo.setCreateBy(username);
            wptpCsInfoService.save(wptpCsInfo);
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
     * @param wptpCsInfo
     * @return
     */
    @PutMapping(value = "/edit")
    public Result<WptpCsInfo> edit(@RequestBody WptpCsInfo wptpCsInfo, HttpServletRequest request) {
        Result<WptpCsInfo> result = new Result<WptpCsInfo>();
        String username = JwtUtil.getUserNameByToken(request);
        WptpCsInfo wptpCsInfoEntity = wptpCsInfoService.getById(wptpCsInfo.getId());
        if (wptpCsInfoEntity == null) {
            result.error500("未找到对应实体");
        } else {
            wptpCsInfo.setUpdateBy(username);
            boolean ok = wptpCsInfoService.updateById(wptpCsInfo);
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
            wptpCsInfoService.removeById(id);
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
    public Result<WptpCsInfo> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<WptpCsInfo> result = new Result<WptpCsInfo>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.wptpCsInfoService.removeByIds(Arrays.asList(ids.split(",")));
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
    public Result<WptpCsInfo> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<WptpCsInfo> result = new Result<WptpCsInfo>();
        WptpCsInfo wptpCsInfo = wptpCsInfoService.getById(id);
        if (wptpCsInfo == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(wptpCsInfo);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param wptpCsInfo
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WptpCsInfo wptpCsInfo) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<WptpCsInfo> queryWrapper = QueryGenerator.initQueryWrapper(wptpCsInfo, request.getParameterMap());
        List<WptpCsInfo> pageList = wptpCsInfoService.list(queryWrapper);
        // Step.2 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        // 过滤选中数据
        String selections = request.getParameter("selections");
        if (oConvertUtils.isEmpty(selections)) {
            mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        } else {
            List<String> selectionList = Arrays.asList(selections.split(","));
            List<WptpCsInfo> exportList = pageList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
            mv.addObject(NormalExcelConstants.DATA_LIST, exportList);
        }
        //导出文件名称+
        String username = JwtUtil.getUserNameByToken(request);
        mv.addObject(NormalExcelConstants.FILE_NAME, "采收批次信息列表");
        mv.addObject(NormalExcelConstants.CLASS, WptpCsInfo.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("采收批次信息列表数据", "导出人:" + username, "导出信息"));
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
                List<WptpCsInfo> listWptpCsInfos = ExcelImportUtil.importExcel(file.getInputStream(), WptpCsInfo.class, params);
                wptpCsInfoService.saveBatch(listWptpCsInfos);
                return Result.ok("文件导入成功！数据行数:" + listWptpCsInfos.size());
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
    private Result<IPage<WptpCsInfoVO>> handlePageData(IPage<WptpCsInfo> pageList) {

        Result<IPage<WptpCsInfoVO>> result = new Result<IPage<WptpCsInfoVO>>();
        Map<String, Object> map = new HashMap<>();//保存查询条件
        IPage<WptpCsInfoVO> wptpCsInfoVOIPage = new Page<>();//需要展示的分页数据

        wptpCsInfoVOIPage.setPages(pageList.getPages());
        wptpCsInfoVOIPage.setCurrent(pageList.getCurrent());
        wptpCsInfoVOIPage.setSize(pageList.getSize());
        wptpCsInfoVOIPage.setTotal(pageList.getTotal());
        List<WptpCsInfo> wptpCsInfoList = pageList.getRecords();
        List<WptpCsInfoVO> wptpCsInfoVOList = new ArrayList<WptpCsInfoVO>();


        /**
         * 把查出来的实体类，属性值copy到展示类VO里面
         */
        for (int i = 0; i < wptpCsInfoList.size(); i++) {
            WptpCsInfo wptpCsInfo = wptpCsInfoList.get(i);
            WptpCsInfoVO wptpCsInfoVO = new WptpCsInfoVO();
            BeanUtils.copyProperties(wptpCsInfo, wptpCsInfoVO);
            map.put("medicinal_code", wptpCsInfo.getMedicinalCode());
            List<WptpMedicinal> wptpMedicinals = iWptpMedicinalService.getBaseMapper().selectByMap(map);
            if (!wptpMedicinals.isEmpty()) wptpCsInfoVO.setMedicinalName(wptpMedicinals.get(0).getName());
            map.clear();
            map.put("ent_id", wptpCsInfo.getEntId());
            List<WptpEntInfo> wptpEntInfos = iWptpEntInfoService.getBaseMapper().selectByMap(map);
            if (!wptpEntInfos.isEmpty()) wptpCsInfoVO.setEntName(wptpEntInfos.get(0).getEntName());
            map.clear();
            map.put("base_code", wptpCsInfo.getBaseCode());
            List<WptpBase> wptpBases = iWptpBaseService.getBaseMapper().selectByMap(map);
            if (!wptpBases.isEmpty()) wptpCsInfoVO.setBaseName(wptpBases.get(0).getBaseName());
            map.clear();
            map.put("block_medicinal_id", wptpCsInfo.getBlockMedicinalId());
            List<WptpBlockMeidicinal> wptpBlockMeidicinals = iWptpBlockMeidicinalService.getBaseMapper().selectByMap(map);
            if (!wptpBlockMeidicinals.isEmpty()) {
                wptpCsInfoVO.setMedicinalCode(wptpBlockMeidicinals.get(0).getMedicinalCode());
                wptpCsInfoVO.setEntId(wptpBlockMeidicinals.get(0).getEntId());
                wptpCsInfoVO.setBaseCode(wptpBlockMeidicinals.get(0).getBaseCode());
                wptpCsInfoVO.setBlockCode(wptpBlockMeidicinals.get(0).getBlockCode());
                wptpCsInfoVO.setBaseAdmin(wptpBlockMeidicinals.get(0).getBaseAdmin());
                wptpCsInfoVO.setFileTime(wptpBlockMeidicinals.get(0).getFileTime());
            }
            map.clear();
            wptpCsInfoVOList.add(wptpCsInfoVO);
        }
        wptpCsInfoVOIPage.setRecords(wptpCsInfoVOList);
        result.setSuccess(true);
        result.setResult(wptpCsInfoVOIPage);
        return result;
    }
}
