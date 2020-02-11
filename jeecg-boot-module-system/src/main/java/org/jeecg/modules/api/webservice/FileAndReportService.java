package org.jeecg.modules.api.webservice;

import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.constraints.NotBlank;

/**
 * 文件上传webservice接口
 * @author laowang
 */
@WebService(name = "fileAndReportService",targetNamespace = "http://webservice.api.modules.jeecg.org/")
@Component
public interface FileAndReportService {
    @WebMethod
    String upload(@WebParam(name = "jsonStr")@NotBlank String jsonStr) throws Exception;

}
