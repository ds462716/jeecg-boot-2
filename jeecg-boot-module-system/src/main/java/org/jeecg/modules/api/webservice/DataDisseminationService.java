package org.jeecg.modules.api.webservice;

import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.constraints.NotBlank;

/**
 * 数据下发
 * @author laowang
 */
@WebService(name = "dataDisseminationService",targetNamespace = "http://webservice.api.modules.jeecg.org/")
@Component
public interface DataDisseminationService {
    /**
     * 数据下发
     * @param jsonStr
     * @return
     */
    @WebMethod
    String dataDissemination(@WebParam(name = "jsonStr")@NotBlank String jsonStr);

}
