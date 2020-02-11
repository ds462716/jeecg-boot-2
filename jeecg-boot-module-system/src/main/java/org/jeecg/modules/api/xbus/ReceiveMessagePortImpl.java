
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package org.jeecg.modules.api.xbus;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.api.webservice.*;
import org.jeecg.modules.demo.trace.service.AskUpperLinkService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import java.util.logging.Logger;

/**
 * This class was generated by Apache CXF 3.2.11
 * 2019-11-20T10:20:36.226+08:00
 * Generated source version: 3.2.11
 *
 */

@WebService(
                      serviceName = "ISoapWebServiceService",
                      portName = "receiveMessagePort",
                      targetNamespace = "http://webservice.esb.sphchina.com",
                    /*  wsdlLocation = "file:/C:/Users/laowang/AppData/Local/Temp/tempdir4541953000469166280.tmp/soap_1.wsdl",*/
                      endpointInterface = "org.jeecg.modules.api.xbus.ReceiveMessage")
@Slf4j
public class ReceiveMessagePortImpl implements ReceiveMessage {
    @Autowired
    private YpService ypService;
    @Autowired
    private DataDisseminationService dataDisseminationService;
    @Autowired
    private FileAndReportService fileAndReportService;
    @Autowired
    private ToVoidService toVoidService;
    @Autowired
    private PlantService plantService;
    @Autowired
    private MedicinalService medicinalService;
    @Autowired
    private AskUpperLinkService askUpperLinkService;

    private static final Logger LOG = Logger.getLogger(ReceiveMessagePortImpl.class.getName());

    /* (non-Javadoc)HttpUtils
     * @see com.cxf.ReceiveMessage#receiveMessage(com.cxf.DefaultWebServiceRequest defaultWebServiceRequest)*
     */
    public DefaultWebServiceResponse receiveMessage(DefaultWebServiceRequest defaultWebServiceRequest) {
        LOG.info("Executing operation receiveMessage");
        String msgBody = defaultWebServiceRequest.getMsgBody();
        LOG.info("通过Xbus调用的参数:"+msgBody);
        String serviceId = defaultWebServiceRequest.getServiceId();
        String result = handleData(msgBody, serviceId);
        LOG.info("返回值:"+result);
        try {
            DefaultWebServiceResponse DefaultWebServiceResponse = new DefaultWebServiceResponse("0",result);
            return DefaultWebServiceResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    private  String handleData(String msgBody,String serviceId ){
        String result="";
        switch (serviceId){
            /**
             * 种植-初加工信息
             */
            case "A0C101":
                result= plantService.addProcessInfo(msgBody);
                break;
            /**
             * 种植-出库销售
             */
            case "A0C102":
                result= plantService.addSaleInfo(msgBody);
                break;
            /**
             * 统一作废接口
             */
            case "A0C111":
                result= toVoidService.toviod(msgBody);
                break;
            /**
             * 饮片入库
             */
            case "A0C105":
                result= ypService.ypInstock(msgBody);
                break;
            /**
             * 饮片加工
             */
            case "A0C106":
                result= ypService.ypProcess(msgBody);
                break;
            /**
             * 饮片包装
             */
            case "A0C107":
                result= ypService.ypPack(msgBody);
                break;
            /**
             * 饮片销售
             */
            case "A0C108":
                result= ypService.ypSale(msgBody);
                break;
            /**
             * 饮片经营-饮片入库
             */
            case "A0C109":
                result= ypService.ypbInstock(msgBody);
                break;
            /**
             * 饮片经营-饮片销售
             */
            case "A0C110":
                result= ypService.ypbSale(msgBody);
                break;
            /**
             * 药材入库
             */
            case "A0C103":
                result= medicinalService.medicineInstock(msgBody);
                break;
            /**
             * 药材销售
             */
            case "A0C104":
                result= medicinalService.medicineSale(msgBody);
                break;
            /**
             * 文件上传
             */
            case "A0C113":
                try {
                    result= fileAndReportService.upload(msgBody);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            /**
             * 为XBUS，根据追数码查询
             */
            case "A0C114":
          /*      Map<String, String> paramMap = new HashMap<>();
                JSONObject jsonObj = new JSONObject(msgBody);
                paramMap.put("traceCode",jsonObj.get("traceCode").toString());
                result= HttpUtils.sendGet("http://localhost:8080/jeecg-boot/trace/traceByCode", paramMap);*/
                JSONObject jsonObj = new JSONObject(msgBody);
                String traceCode = jsonObj.get("traceCode").toString();
                log.info("追溯码是"+traceCode);
                if (traceCode.length()<5)return JSONArray.toJSON(new Result().error500("接收到追溯码是:"+traceCode+"  ;  追溯码的长度不符合规则")).toString();
                result = askUpperLinkService.askUpperLinkForOneDate(traceCode);
                break;
            /**
             * 数据下发
             */
            case "A0C112":
                result= dataDisseminationService.dataDissemination(msgBody);
        }
        return result;
    }

}
