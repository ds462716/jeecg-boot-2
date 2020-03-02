package org.jeecg.modules.api.webservice;

import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.constraints.NotBlank;

/**
 * 药材相关webservice接口
 *
 * @author laowang
 */
@WebService(name = "medicinalService", targetNamespace = "http://webservice.api.modules.jeecg.org/")
@Component
public interface MedicinalService {
    /**
     * 药材入库
     *
     * @param jsonStr
     * @return
     */
    @WebMethod
    String medicineInstock(@WebParam(name = "jsonStr") @NotBlank String jsonStr);

    /**
     * 药材销售
     *
     * @param jsonStr
     * @return
     */
    @WebMethod
    String medicineSale(@WebParam(name = "jsonStr") @NotBlank String jsonStr);


}
