package com.wangcaitao.admin.common.starter;

import com.wangcaitao.starter.router.RestfulRouter;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 忽略路由配置
 *
 * @author wangcaitao
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "router.ignored.extend")
public class ExtendIgnoredRouterProperty {

    /**
     * 保存操作日志参数
     */
    private RestfulRouter[] saveOperationLogParams;

    @PostConstruct
    public void init() {
        ExtendIgnoredRouterUtils.SAVE_OPERATION_LOG_PARAMS = saveOperationLogParams;
    }
}
