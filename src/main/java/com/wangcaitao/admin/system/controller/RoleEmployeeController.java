package com.wangcaitao.admin.system.controller;

import com.wangcaitao.admin.system.entity.RoleEmployeeBO;
import com.wangcaitao.admin.system.entity.RoleEmployeeQuery;
import com.wangcaitao.admin.system.entity.RoleEmployeeSaveDTO;
import com.wangcaitao.admin.system.service.RoleEmployeeService;
import com.wangcaitao.common.entity.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@RestController
public class RoleEmployeeController {

    @Resource
    private RoleEmployeeService roleEmployeeService;

    @GetMapping(value = "/role-employee")
    public Result<List<RoleEmployeeBO>> list(@Validated RoleEmployeeQuery query) {
        return roleEmployeeService.list(query);
    }

    @PostMapping(value = "/role-employee")
    public Result<Serializable> save(@Validated @RequestBody RoleEmployeeSaveDTO entity) {
        return roleEmployeeService.save(entity);
    }

    @DeleteMapping(value = "/role-employee")
    public Result<Serializable> delete(@RequestBody List<Long> ids) {
        return roleEmployeeService.delete(ids);
    }
}
