package com.wangcaitao.admin.auth.controller;

import com.wangcaitao.admin.auth.entity.AuthBO;
import com.wangcaitao.admin.auth.entity.AuthDTO;
import com.wangcaitao.admin.auth.service.AuthService;
import com.wangcaitao.common.entity.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author wangcaitao
 */
@RestController
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping(value = "/login")
    public Result<AuthBO> login(@Validated @RequestBody AuthDTO entity) {
        return authService.login(entity);
    }

    @PutMapping(value = "/logout")
    public Result<Serializable> logout() {
        return authService.logout();
    }
}
