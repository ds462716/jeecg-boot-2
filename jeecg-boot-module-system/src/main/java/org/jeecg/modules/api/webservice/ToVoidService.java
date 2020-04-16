package org.jeecg.modules.api.webservice;

import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.constraints.NotBlank;

/**
 * 逆向作废
 *
 * @author laowang
 */
@WebService(name = "toVoidService", targetNamespace = "http://webservice.api.modules.jeecg.org/")
@Component
public interface ToVoidService {
    /**
     * 统一作废接口
     *
     * @param jsonStr
     * @return
     */
    @WebMethod
    String toviod(@WebParam(name = "jsonStr") @NotBlank String jsonStr);
}
