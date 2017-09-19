package com.wang.xyhua.common.global;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by dell on 2017/9/19.
 */
@Setter @Getter @ToString
public class GlobalConfig {
    //渠道号
    private String channelId = MConstant.DEFAULT_CHANNEL;
    //是否登录成功
    private boolean isLogined;

}
