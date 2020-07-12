package com.wangcaitao.admin.system.controller;

import com.wangcaitao.admin.system.entity.LoginLogBO;
import com.wangcaitao.admin.system.entity.LoginLogQuery;
import com.wangcaitao.admin.system.service.LoginLogService;
import com.wangcaitao.common.entity.Result;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@RestController
public class LoginLogController {

    @Resource
    private LoginLogService loginLogService;

    @GetMapping(value = "/login-log")
    public Result<List<LoginLogBO>> list(LoginLogQuery query) {
        return loginLogService.list(query);
    }

    @DeleteMapping(value = "/login-log")
    public Result<Serializable> delete(@RequestBody List<Long> ids) {
        return loginLogService.delete(ids);
    }

    @DeleteMapping(value = "login-log-empty")
    public Result<Serializable> empty() {
        return loginLogService.empty();
    }
}
