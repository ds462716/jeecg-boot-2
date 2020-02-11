package org.jeecg.modules.api.webservice;

import org.springframework.stereotype.Component;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.constraints.NotBlank;

/**
 * 种植相关webservice接口
 * @author laowang
 */
@WebService(name = "plantService",targetNamespace = "http://webservice.esb.sphchina.com")
@Component
public interface PlantService {
    /**
     * 种植-企业/供应商信息
     */
    @WebMethod
    String addEntInfo(@WebParam(name = "jsonStr")@NotBlank String jsonStr);
    /**
     * 种植-基地信息
     */
    @WebMethod
    String addBaseInfo(@WebParam(name = "jsonStr")@NotBlank String jsonStr);
    /**
     * 种植-档案信息
     */
    @WebMethod
    String addblockMeidicinal(@WebParam(name = "jsonStr")@NotBlank String jsonStr);
    /**
     * 种植-作业信息
     */
    @WebMethod
    String addPlantInfo(@WebParam(name = "jsonStr")@NotBlank String jsonStr);
    /**
     * 种植-初加工信息
     */
    @WebMethod
    String addProcessInfo(@WebParam(name = "jsonStr")@NotBlank String jsonStr);
    /**
     * 种植-出库销售
     */
    @WebMethod
    String addSaleInfo(@WebParam(name = "jsonStr")@NotBlank String jsonStr);
    /**
     * 种植-采收批次
     */
    @WebMethod
    String addCsInfo(@WebParam(name = "jsonStr")@NotBlank String jsonStr);
}
