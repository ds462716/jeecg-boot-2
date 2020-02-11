package org.jeecg.modules.api;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.hostcode.entity.WptpHostcode;
import org.jeecg.modules.demo.hostcode.service.IWptpHostcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 校验主机代码
 * @author laowang
 */
@Component
public class HostCodeCheck {
    @Autowired
    private IWptpHostcodeService iWptpHostcodeService;



    public Result checkHostCode(String hostCode){
        Map<String, Object> map = new HashMap<>();
        map.put("host_code",hostCode);
        List<WptpHostcode> wptpHostcodes = iWptpHostcodeService.getBaseMapper().selectByMap(map);
        if (wptpHostcodes.isEmpty()){
            return new Result(false, "主机代码不存在", 500, new Date().getTime());
        }
        return new Result(true, "操作成功", 200, new Date().getTime());
    }
}
