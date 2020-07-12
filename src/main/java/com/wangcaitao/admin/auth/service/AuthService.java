package com.wangcaitao.admin.auth.service;

import com.wangcaitao.admin.auth.entity.AuthBO;
import com.wangcaitao.admin.auth.entity.AuthDTO;
import com.wangcaitao.common.entity.Result;

import java.io.Serializable;

/**
 * @author wangcaitao
 */
public interface AuthService {

    /**
     * 登陆
     *
     * @param entity entity
     * @return Result
     */
    Result<AuthBO> login(AuthDTO entity);

    /**
     * 退出
     *
     * @return Result
     */
    Result<Serializable> logout();
}
