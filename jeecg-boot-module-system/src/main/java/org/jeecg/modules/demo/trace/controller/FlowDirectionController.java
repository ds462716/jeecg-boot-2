package org.jeecg.modules.demo.trace.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.modules.demo.blockmedicinal.entity.WptpBlockMeidicinal;
import org.jeecg.modules.demo.blockmedicinal.service.IWptpBlockMeidicinalService;
import org.jeecg.modules.demo.csinfo.entity.WptpCsInfo;
import org.jeecg.modules.demo.csinfo.service.IWptpCsInfoService;
import org.jeecg.modules.demo.medicinebinstock.entity.WptpMedicineInstock;
import org.jeecg.modules.demo.medicinebinstock.service.IWptpMedicineInstockService;
import org.jeecg.modules.demo.medicinebsale.entity.WptpMedicineSale;
import org.jeecg.modules.demo.medicinebsale.service.IWptpMedicineSaleService;
import org.jeecg.modules.demo.plantinfo.entity.WptpPlantInfo;
import org.jeecg.modules.demo.plantinfo.service.IWptpPlantInfoService;
import org.jeecg.modules.demo.processinfo.entity.WptpProcessInfo;
import org.jeecg.modules.demo.processinfo.service.IWptpProcessInfoService;
import org.jeecg.modules.demo.sale.entity.WptpSale;
import org.jeecg.modules.demo.sale.service.IWptpSaleService;
import org.jeecg.modules.demo.ypbinstock.entity.WptpYpbInstock;
import org.jeecg.modules.demo.ypbinstock.service.IWptpYpbInstockService;
import org.jeecg.modules.demo.ypbsale.entity.WptpYpbSale;
import org.jeecg.modules.demo.ypbsale.service.IWptpYpbSaleService;
import org.jeecg.modules.demo.yppack.entity.WptpYpPack;
import org.jeecg.modules.demo.yppack.service.IWptpYpPackService;
import org.jeecg.modules.demo.ypprocess.entity.WptpYpProcess;
import org.jeecg.modules.demo.ypprocess.service.IWptpYpProcessService;
import org.jeecg.modules.demo.ypprocessinstock.entity.WptpYpInstock;
import org.jeecg.modules.demo.ypprocessinstock.service.IWptpYpInstockService;
import org.jeecg.modules.demo.ypprocesssale.entity.WptpYpSale;
import org.jeecg.modules.demo.ypprocesssale.service.IWptpYpSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Api(tags = "流向")
@RestController
@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RequestMapping("/flow")
public class FlowDirectionController {
    @Autowired
    private IWptpBlockMeidicinalService iWptpBlockMeidicinalService;
    @Autowired
    private IWptpPlantInfoService iWptpPlantInfoService;
    @Autowired
    private IWptpCsInfoService iWptpCsInfoService;
    @Autowired
    private IWptpProcessInfoService iWptpProcessInfoService;
    @Autowired
    private IWptpSaleService iWptpSaleService;
    @Autowired
    private IWptpMedicineInstockService iWptpMedicineInstockService;
    @Autowired
    private IWptpMedicineSaleService iWptpMedicineSaleService;
    @Autowired
    private IWptpYpProcessService iWptpYpProcessService;
    @Autowired
    private IWptpYpInstockService iWptpYpInstockService;
    @Autowired
    private IWptpYpPackService iWptpYpPackService;
    @Autowired
    private IWptpYpSaleService iWptpYpSaleService;
    @Autowired
    private IWptpYpbInstockService iWptpYpbInstockService;
    @Autowired
    private IWptpYpbSaleService iWptpYpbSaleService;
    @GetMapping(value = "/flowDirection")
    @ApiOperation(value = "查看流向", notes = "参数link(环节)说明:{" +
            "'种植-档案':'archives'," +
            "'种植-作业':'work'," +
            "'种植-采收批次':'gather'," +
            "'种植-初加工':'plantProcess'," +
            "'种植-销售':'plantSale'," +
            "'药材经营-入库':'medicineInstock'," +
            "'药材经营-销售':'medicineSale'," +
            "'饮片加工-入库':'ypProcessInstock'," +
            "'饮片加工-加工':'ypProcessProcess'," +
            "'饮片加工-包装':'ypProcessPack'," +
            "'饮片加工-销售':'ypProcessSale'," +
            "'饮片经营-入库':'ypBusinessInstock'," +
            "'饮片经营-销售':'ypBusinessSale'}")
    public void flowDirection(@RequestParam(name = "link", required = true) @NotNull String link, @RequestParam(name = "id", required = true) @NotNull String id) {
        switch (link){
            case "archives":
                WptpBlockMeidicinal wptpBlockMeidicinal = iWptpBlockMeidicinalService.getBaseMapper().selectById(id);
                break;
            case "work":
                WptpPlantInfo wptpPlantInfo = iWptpPlantInfoService.getBaseMapper().selectById(id);
                break;
            case "gather":
                WptpCsInfo wptpCsInfo = iWptpCsInfoService.getBaseMapper().selectById(id);
                break;
            case "plantProcess":
                WptpProcessInfo wptpProcessInfo = iWptpProcessInfoService.getBaseMapper().selectById(id);
                break;
            case "plantSale":
                WptpSale wptpSale = iWptpSaleService.getBaseMapper().selectById(id);
                break;
            case "medicineInstock":
                WptpMedicineInstock wptpMedicineInstock = iWptpMedicineInstockService.getBaseMapper().selectById(id);
                break;
            case "medicineSale":
                WptpMedicineSale wptpMedicineSale = iWptpMedicineSaleService.getBaseMapper().selectById(id);
                break;
            case "ypProcessProcess":
                WptpYpProcess wptpYpProcess = iWptpYpProcessService.getBaseMapper().selectById(id);
                break;
            case "ypProcessInstock":
                WptpYpInstock wptpYpInstock = iWptpYpInstockService.getBaseMapper().selectById(id);
                break;
            case "ypProcessPack":
                WptpYpPack wptpYpPack = iWptpYpPackService.getBaseMapper().selectById(id);
                break;
            case "ypProcessSale":
                WptpYpSale wptpYpSale = iWptpYpSaleService.getBaseMapper().selectById(id);
                break;
            case "ypBusinessInstock":
                WptpYpbInstock wptpYpbInstock = iWptpYpbInstockService.getBaseMapper().selectById(id);
                break;
            case "ypBusinessSale":
                WptpYpbSale wptpYpbSale = iWptpYpbSaleService.getBaseMapper().selectById(id);

        }
    }
}
