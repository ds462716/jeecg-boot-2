package org.jeecg.modules.demo.trace.service;

import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

/**
 * 请求上游环节
 *
 * @author laowang
 */
@Service
public interface AskUpperLinkService {
    String askUpperLinkForOneDate(@NotBlank String traceCode);
}
