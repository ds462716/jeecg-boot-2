package org.jeecg.modules.api.webservice;

import com.alibaba.fastjson.JSONArray;
import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dispatchWebservice")
public class DispatchController {
    @Autowired
    private YpService ypService;
    @Autowired
    private DataDisseminationService dataDisseminationService;
    @Autowired
    private FileAndReportService fileAndReportService;
    @Autowired
    private ToVoidService toVoidService;
    @PostMapping("/whichService")
    public String whichService( String jsonStr,Integer condition){
        StringBuilder result = new StringBuilder();
        switch (condition){
             /**
              * 数据下发
              */
             case 1:
                 result.append(dataDisseminationService.dataDissemination(jsonStr));
                 break;
            /**
             * 逆向作废
             */
            case 2:
                result.append(toVoidService.toviod(jsonStr));
                break;
            /**
             * 文件上传
             */
            case 3:
                try {
                    result.append(fileAndReportService.upload(jsonStr));
                } catch (Exception e) {
                    System.out.println(e.getMessage()+e.getCause()+e.getLocalizedMessage()+e.getStackTrace());
                   return JSONArray.toJSON(new Result().error500(e.getMessage())).toString();
                }
                break;
            /**
             * 饮片入库
             */
            case 4:
                result.append(ypService.ypInstock(jsonStr));
                break;
            /**
             * 饮片加工
             */
            case 5:
                result.append(ypService.ypProcess(jsonStr));
                break;
            /**
             * 饮片销售
             */
            case 6:
                result.append(ypService.ypPack(jsonStr));
                break;
            /**
             * 饮片包装
             */
            case 7:
                result.append(ypService.ypSale(jsonStr));
                break;
            /**
             * 饮片经营-饮片入库
             */
            case 8:
                result.append(ypService.ypbInstock(jsonStr));
                break;
            /**
             * 饮片经营-饮片销售
             */
            case 9:
                result.append(ypService.ypbSale(jsonStr));
                break;

        }
        return result.toString();
    }
}
