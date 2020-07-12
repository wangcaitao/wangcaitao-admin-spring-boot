package com.wangcaitao.admin.auth.filter;

import com.wangcaitao.admin.auth.constant.AuthCacheEnum;
import com.wangcaitao.admin.auth.constant.AuthPropertyNameConstant;
import com.wangcaitao.admin.common.util.HttpRequestUtils;
import com.wangcaitao.admin.common.util.PropertyUtils;
import com.wangcaitao.admin.system.constant.SystemDictCodeConstant;
import com.wangcaitao.admin.system.entity.OperationLogSaveDTO;
import com.wangcaitao.admin.system.service.OperationLogService;
import com.wangcaitao.common.constant.ContentTypeConstant;
import com.wangcaitao.common.constant.HttpMethodConstant;
import com.wangcaitao.common.constant.HttpStatusConstant;
import com.wangcaitao.common.constant.HttpStatusEnum;
import com.wangcaitao.common.constant.IntegerConstant;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.exception.ResultException;
import com.wangcaitao.common.util.AntPathUtils;
import com.wangcaitao.common.util.HttpServletResponseUtils;
import com.wangcaitao.common.util.JacksonUtils;
import com.wangcaitao.common.util.ResultUtils;
import com.wangcaitao.starter.http.CachedBodyHttpServletResponse;
import com.wangcaitao.starter.http.HttpServletUtils;
import com.wangcaitao.starter.redis.StringRedisUtils;
import com.wangcaitao.starter.router.IgnoredRouterUtils;
import com.wangcaitao.starter.router.RestfulRouter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author wangcaitao
 */
@Order(value = IntegerConstant.ZERO)
@Configuration
@WebFilter(value = "authFilter", urlPatterns = "/*")
@Slf4j
public class AuthFilter implements Filter {

    @Resource
    private OperationLogService operationLogService;

    private static final ThreadLocal<LocalDateTime> WAIT_TIME = new ThreadLocal<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = HttpServletUtils.getRequest();

        // region 校验认证和授权
        String path = request.getMethod() + request.getServletPath();
        try {
            validateAuth(path);
        } catch (ResultException e) {
            HttpServletResponseUtils.output(HttpServletUtils.getResponse(), JacksonUtils.toJsonString(ResultUtils.error(e.getCode(), e.getMsg())));

            return;
        } catch (Exception e) {
            log.error("filter error. path: {}", path, e);

            HttpServletResponseUtils.output(HttpServletUtils.getResponse(), JacksonUtils.toJsonString(ResultUtils.error()));

            return;
        }
        // endregion

        // region 是否只能查询. 仅体验版限制, 防止数据错乱导致系统不能正常使用.
        String onlyView = PropertyUtils.getValueByName(AuthPropertyNameConstant.ONLY_VIEW);
        if (StringUtils.isNotBlank(onlyView) && Boolean.parseBoolean(onlyView)) {
            boolean flag = true;
            RestfulRouter[] authentications = IgnoredRouterUtils.AUTHENTICATIONS;
            if (null != authentications) {
                for (RestfulRouter restfulRouter : authentications) {
                    if (AntPathUtils.match(restfulRouter.getMethod() + restfulRouter.getUrl(), path)) {
                        flag = false;

                        break;
                    }
                }
            }

            if (flag && !Objects.equals(request.getMethod(), HttpMethodConstant.GET)) {
                HttpServletResponseUtils.output(HttpServletUtils.getResponse(), JacksonUtils.toJsonString(ResultUtils.error("体验版仅支持查询")));

                return;
            }
        }
        // endregion

        CachedBodyHttpServletResponse cachedBodyHttpServletResponse = new CachedBodyHttpServletResponse(HttpServletUtils.getResponse());
        WAIT_TIME.set(LocalDateTime.now());
        filterChain.doFilter(servletRequest, cachedBodyHttpServletResponse);
        long waitTime = Duration.between(WAIT_TIME.get(), LocalDateTime.now()).toMillis();
        WAIT_TIME.remove();

        // 记录操作日志
        saveOperationLog(cachedBodyHttpServletResponse.getContentType(), cachedBodyHttpServletResponse.getBody(), waitTime);
    }

    /**
     * 校验认证和授权
     *
     * @param path path
     */
    private void validateAuth(String path) {
        RestfulRouter[] authentications = IgnoredRouterUtils.AUTHENTICATIONS;
        if (null != authentications) {
            for (RestfulRouter restfulRouter : authentications) {
                if (AntPathUtils.match(restfulRouter.getMethod() + restfulRouter.getUrl(), path)) {
                    return;
                }
            }
        }

        String token = HttpRequestUtils.getToken();
        String authorizedResourcesValue = StringRedisUtils.get(String.format(AuthCacheEnum.AUTHORIZED_TOKEN.getKey(), token));
        if (null == authorizedResourcesValue) {
            throw new ResultException(HttpStatusEnum.UNAUTHORIZED);
        }

        RestfulRouter[] authorizations = IgnoredRouterUtils.AUTHORIZATIONS;
        if (null != authorizations) {
            for (RestfulRouter restfulRouter : authorizations) {
                if (AntPathUtils.match(restfulRouter.getMethod() + restfulRouter.getUrl(), path)) {
                    return;
                }
            }
        }

        List<String> authorizedResources = JacksonUtils.parseArray(authorizedResourcesValue, String.class);
        for (String authorizedResource : authorizedResources) {
            if (AntPathUtils.match(authorizedResource, path)) {
                return;
            }
        }

        throw new ResultException(HttpStatusEnum.FORBIDDEN);
    }

    /**
     * 保存操作日志
     *
     * @param responseContentType responseContentType
     * @param responseBody        responseBody
     * @param waitTime            waitTime
     */
    private void saveOperationLog(String responseContentType, byte[] responseBody, long waitTime) {
        OperationLogSaveDTO operationLogSaveDto = new OperationLogSaveDTO();
        operationLogSaveDto.setWaitTime(waitTime);

        int statusCode = SystemDictCodeConstant.LOGIN_STATUS_SUCCESS;
        String errorMsg = "";
        String responseResult = "";
        try {
            if (null == responseContentType) {
                responseContentType = "";
            }

            if (responseContentType.startsWith(ContentTypeConstant.APPLICATION_JSON_VALUE)) {
                Result<Object> result = JacksonUtils.OBJECT_MAPPER.readValue(responseBody, JacksonUtils.OBJECT_MAPPER.getTypeFactory().constructParametricType(Result.class, Object.class));

                responseResult = JacksonUtils.toJsonString(result);
                if (!Objects.equals(result.getCode(), HttpStatusConstant.OK_CODE)) {
                    statusCode = SystemDictCodeConstant.OPERATION_STATUS_ERROR;
                    errorMsg = result.getMsg();
                }
            } else if (responseContentType.startsWith(ContentTypeConstant.TEXT_PLAIN_VALUE) || responseContentType.startsWith(ContentTypeConstant.TEXT_XML_VALUE)) {
                responseResult = new String(responseBody, StandardCharsets.UTF_8);
            }

            operationLogSaveDto.setStatusCode(statusCode);
            operationLogSaveDto.setErrorMsg(errorMsg);
            operationLogSaveDto.setResponseResult(responseResult);

            operationLogService.save(operationLogSaveDto);
        } catch (Exception e) {
            log.error("保存操作日志失败. responseContentType: {}, param: {}", responseContentType, JacksonUtils.toJsonString(operationLogSaveDto), e);
        }
    }
}
