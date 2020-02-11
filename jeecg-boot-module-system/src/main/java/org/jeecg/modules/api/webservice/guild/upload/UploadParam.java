package org.jeecg.modules.api.webservice.guild.upload;

import lombok.Data;
import org.jeecg.modules.api.webservice.guild.bean.*;

import java.io.Serializable;
import java.util.List;

/**
 * 上传至行业协会的数据
 * @author laowang
 */
@Data
public class UploadParam implements Serializable {

    private String appKey;//平台唯一编码
    private String appToken;//接口校验字段
    private String commodityImg;//货品图片,多张用竖线（|）隔开
    private String commodityName;//货品名称
    private String commoditySpec;//货品描述
    private String currentNodeId;//当前环节数据id
    private String detailUrl;//外部履历地址

    private EnterpriseInfo enterpriseInfo;//企业信息

    private String factoryCode;//厂家原编码

    private List<HarvestBatch> harvestBatch;//采收批次信息

    private String lastNodeId;//上个环节数据id

    private long nodeDate;//节点生成时间

    private String operaterFlag;//操作标识(必填)，0添加、1修改、2删除

    private List<OrderShipment> orderShipment;//订单发货

    private List<PlantingBatch> plantingBatch;//种植批次信息

    private List<ProcessingInfo> processingInfo;//加工信息

    private List<PurchaseOrderInfo> purchaseOrderInfo;//采收（购）订单

    private List<QualitySelfTest> qualitySelfTest;//检测记录

    private TraceCode traceCode;//追溯码

  /*  private long cropArea;
    private String custombatchCode;*/


    private String traceStage;//追溯环节(其他0、生产1、加工2、流通3、批发4、零售/医院5、种植6) ,
    private String wayBillCode;//发货单号

    private List<WarehouseInfo>  warehouseInfo;//仓库信息

    private List<PackingInfo> packingInfo;//包装信息



}
