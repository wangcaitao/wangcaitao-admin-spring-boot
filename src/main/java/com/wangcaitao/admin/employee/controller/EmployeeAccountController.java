package com.wangcaitao.admin.employee.controller;

import com.wangcaitao.admin.employee.entity.EmployeeAccountAvatarUpdateDTO;
import com.wangcaitao.admin.employee.entity.EmployeeAccountBO;
import com.wangcaitao.admin.employee.entity.EmployeeAccountPasswordUpdateDTO;
import com.wangcaitao.admin.employee.entity.EmployeeAccountUpdateDTO;
import com.wangcaitao.admin.employee.service.EmployeeAccountService;
import com.wangcaitao.common.entity.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author wangcaitao
 */
@RestController
public class EmployeeAccountController {

    @Resource
    private EmployeeAccountService employeeAccountService;

    @GetMapping(value = "/account-profile")
    public Result<EmployeeAccountBO> profile() {
        return employeeAccountService.profile();
    }

    @PutMapping(value = "/account")
    public Result<Serializable> update(@Validated @RequestBody EmployeeAccountUpdateDTO entity) {
        return employeeAccountService.update(entity);
    }

    @PutMapping(value = "/account-password")
    public Result<Serializable> updatePassword(@Validated @RequestBody EmployeeAccountPasswordUpdateDTO entity) {
        return employeeAccountService.updatePassword(entity);
    }

    @PutMapping(value = "/account-avatar")
    public Result<Serializable> updateAvatar(@Validated @RequestBody EmployeeAccountAvatarUpdateDTO entity) {
        return employeeAccountService.updateAvatar(entity);
    }
}
