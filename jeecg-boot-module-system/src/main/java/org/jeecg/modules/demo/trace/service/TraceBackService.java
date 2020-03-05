package org.jeecg.modules.demo.trace.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.trace.vo.MedicineTraceVO;
import org.jeecg.modules.demo.trace.vo.TraceVO;
import org.jeecg.modules.demo.trace.vo.YpBusinessTraceVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 追溯
 */
@Service
public interface TraceBackService {
    /**
     * 种植端追溯(link01：采收批次-加工-销售的数据放在一起)
     *
     * @param traceCode
     * @return
     */
    Result<TraceVO> plantTraceLink01(String traceCode);

    /**
     * 种植端追溯(link02：基地-档案-作业的数据放在一起)
     *
     * @param traceCode
     * @return
     */
    Result<TraceVO> plantTraceLink02(String csNo);

    /**
     * 饮片加工追溯
     *
     * @param traceCode
     * @return
     */
    Result<TraceVO> ypProcessTrace(String traceCode);

    /**
     * 饮片经营追溯
     *
     * @param traceCode
     * @return
     */
    Result<TraceVO> ypBusinessTrace(String traceCode);

    /**
     * 药材经营追溯
     *
     * @param traceCode
     * @return
     */
    Result<TraceVO> medicinalTrace(String traceCode);

    /**
     * 由于饮片经营环节的上游可能是本身，所以经过此环节时需要递归调用，返回饮片经营环节list结果集
     */
    List<YpBusinessTraceVO> listYpBusinessTraceVO(String traceCode, List<YpBusinessTraceVO> sourceList);
    /**
     * 由于药材经营环节的上游可能是本身，所以经过此环节时需要递归调用，返回药材经营环节list结果集
     */
    List<MedicineTraceVO> listMedicineTraceVO(String traceCode, List<MedicineTraceVO> sourceList);

}
