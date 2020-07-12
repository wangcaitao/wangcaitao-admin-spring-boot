package com.wangcaitao.admin.common.util;

import com.wangcaitao.admin.auth.constant.AuthConstant;
import com.wangcaitao.common.constant.HttpStatusEnum;
import com.wangcaitao.common.exception.ResultException;
import com.wangcaitao.starter.http.HttpServletUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangcaitao
 */
public class HttpRequestUtils {

    /**
     * 获取 token
     *
     * @return token
     */
    public static String getToken() {
        HttpServletRequest request = HttpServletUtils.getRequest();

        String token = request.getHeader(AuthConstant.TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new ResultException(HttpStatusEnum.UNAUTHORIZED);
        }

        return token;
    }
}
