package com.wangcaitao.admin.system.controller;

import com.wangcaitao.admin.system.entity.EmployeeAccountStatusUpdateDTO;
import com.wangcaitao.admin.system.entity.EmployeeBO;
import com.wangcaitao.admin.system.entity.EmployeeQuery;
import com.wangcaitao.admin.system.entity.EmployeeSaveDTO;
import com.wangcaitao.admin.system.entity.EmployeeStatusUpdateDTO;
import com.wangcaitao.admin.system.entity.EmployeeUpdateDTO;
import com.wangcaitao.admin.system.entity.EmployeeUpdatePasswordDTO;
import com.wangcaitao.admin.system.service.EmployeeService;
import com.wangcaitao.common.entity.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@RestController
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @GetMapping(value = "/employee/{id}")
    public Result<EmployeeBO> getById(@PathVariable(value = "id") Long id) {
        return employeeService.getById(id);
    }

    @GetMapping(value = "/employee")
    public Result<List<EmployeeBO>> list(EmployeeQuery query) {
        return employeeService.list(query);
    }

    @PostMapping(value = "/employee")
    public Result<Serializable> save(@Validated @RequestBody EmployeeSaveDTO entity) {
        return employeeService.save(entity);
    }

    @PutMapping(value = "/employee")
    public Result<Serializable> update(@Validated @RequestBody EmployeeUpdateDTO entity) {
        return employeeService.update(entity);
    }

    @PutMapping(value = "/employee-password")
    public Result<Serializable> updatePassword(@Validated @RequestBody EmployeeUpdatePasswordDTO entity) {
        return employeeService.updatePassword(entity);
    }

    @PutMapping(value = "/employee-account-status")
    public Result<Serializable> update(@Validated @RequestBody EmployeeAccountStatusUpdateDTO entity) {
        return employeeService.updateAccountStatus(entity);
    }

    @PutMapping(value = "/employee-status")
    public Result<Serializable> update(@Validated @RequestBody EmployeeStatusUpdateDTO entity) {
        return employeeService.updateStatus(entity);
    }
}
