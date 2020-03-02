package org.jeecg.modules.demo.fileUpload;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.FTPUtil;
import org.jeecg.common.util.HttpUtils;
import org.jeecg.common.util.UUIDGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.baseinfo.service.IWptpBaseFileService;
import org.jeecg.modules.demo.entinfo.service.IWptpEntFileService;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicinalService;
import org.jeecg.modules.demo.medicinalinfo.service.IWptpMedicineFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

/**
 * 文件上传
 *
 * @author laowang
 */
@RestController
@RequestMapping("/file")
@Api(tags = "上传文件")
@Slf4j
public class UploadController {
    private static final String PREFIX_URL = "/app/files";
    @Autowired
    private IWptpBaseFileService iWptpBaseFileService;
    @Autowired
    private IWptpMedicineFileService iWptpMedicineFileService;
    @Autowired
    private IWptpEntFileService iWptpEntFileService;

    /**
     * 上传
     *
     * @return
     */

    @PostMapping("/upload/{suffixUrl1}/{suffixUrl2}")
    @ApiOperation(value = "上传", notes = "suffixUrl为上传路径后缀，药材:medicine,饮片:yp,图片:image,视频:video,文档:pdf.例如饮片图片则suffixUrl为/yp/image")
    public Result upload(@RequestParam("sourceFile") MultipartFile sourceFile, HttpServletRequest request, @PathVariable("suffixUrl1") String suffixUrl1, @PathVariable("suffixUrl2") String suffixUrl2) throws IOException {
        log.info("进来传文件请求");
        if (sourceFile.isEmpty()) return new Result().error500("文件不能为空");
        /*  String suffixUrl = request.getHeader("suffixUrl");*/
        if ("".equals(suffixUrl1) || "".equals(suffixUrl2)) return new Result().error500("后缀路径不能为空");
        File file = File.createTempFile("temp", null);
        sourceFile.transferTo(file);   //sourceFile为传入的MultipartFile
        InputStream inputStream = new FileInputStream(file);
        file.deleteOnExit();
        String generate = UUIDGenerator.generate();//UUID
        String newName = generate + sourceFile.getOriginalFilename();
        String dateUrl = getDateUrl();
        boolean result = FTPUtil.uploadFile(FTPUtil.HOST_NAME, FTPUtil.PORT, FTPUtil.USERNAME, FTPUtil.PASSWORD, PREFIX_URL + "/" + suffixUrl1 + "/" + suffixUrl2 + "/" + dateUrl, newName, inputStream);
        if (!result) {
            return new Result().error500("上传失败");
        }
        Result<Object> objectResult = new Result<>();
        objectResult.setResult("/" + suffixUrl1 + "/" + suffixUrl2 + "/" + dateUrl + "/" + newName);
        objectResult.setMessage("上传成功");
        objectResult.setCode(200);
        return objectResult;
    }

    /**
     * 获得yyyy-mm格式的字符串
     *
     * @return
     */
    private String getDateUrl() {
        Calendar now = Calendar.getInstance();
        String year = now.get(Calendar.YEAR) + "";
        String month = (now.get(Calendar.MONTH) + 1) + "";

        if (month.length() < 2) {
            month = "0" + month;
        }
        return year + month;
    }

    /**
     * 上传
     *
     * @return
     */

    @GetMapping("/upload/deleteByUrlAndLink")
    @ApiOperation(value = "刪除文件", notes = "刪除文件")
    public Result upload(String url, String link) {

        switch (link) {
            case "base":
                iWptpBaseFileService.deleteByPath(url);
                break;
            case "medicine":
                iWptpMedicineFileService.deleteByPath(url);
                break;
            case "ent":
                iWptpEntFileService.deleteByPath(url);
        }
        boolean b = delFileOne("/app/files" + url);
        return new Result(b, "", 0, 0);
    }

    /**
     * 刪除文件
     *
     * @param fileUrl
     * @return
     */
    @GetMapping("/upload/deleteFileTest")
    public boolean delFileOne(String fileUrl) {
       /* HttpUtils.sendGet("http://180.168.130.217:9010")*/
        boolean deleteFlag = false;
        if (!oConvertUtils.isEmpty(fileUrl)) {
            File file = new File(fileUrl);
            if (file.exists()) {
                deleteFlag = file.delete();
            } else {
                deleteFlag = false;
            }
        }
        return deleteFlag;
    }
}

