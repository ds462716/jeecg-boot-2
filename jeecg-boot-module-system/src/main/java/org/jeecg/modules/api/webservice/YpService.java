package org.jeecg.modules.api.webservice;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.constraints.NotBlank;

/**
 * 饮片相关webservice接口
 * @author laowang
 */
@Service
 public interface YpService {
    /**
     * 饮片入库
     * @param jsonStr
     * @return
     */
    String ypInstock(@NotBlank String jsonStr);
    /**
     * 饮片加工
     * @param jsonStr
     * @return
     */
    String ypProcess(@NotBlank String jsonStr);
    /**
     * 饮片包装
     * @param jsonStr
     * @return
     */
    String ypPack(@NotBlank String jsonStr);
    /**
     * 饮片销售
     * @param jsonStr
     * @return
     */
    String ypSale(@NotBlank String jsonStr);
    /**
     * 饮片经营-饮片入库
     * @param jsonStr
     * @return
     */
    String ypbInstock(@NotBlank String jsonStr);
    /**
     * 饮片经营-饮片销售
     * @param jsonStr
     * @return
     */
    String ypbSale(@NotBlank String jsonStr);

}
