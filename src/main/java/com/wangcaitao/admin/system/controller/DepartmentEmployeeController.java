package com.wangcaitao.admin.system.controller;

import com.wangcaitao.admin.system.entity.DepartmentEmployeeBO;
import com.wangcaitao.admin.system.entity.DepartmentEmployeeQuery;
import com.wangcaitao.admin.system.entity.DepartmentEmployeeSaveDTO;
import com.wangcaitao.admin.system.service.DepartmentEmployeeService;
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
public class DepartmentEmployeeController {

    @Resource
    private DepartmentEmployeeService departmentEmployeeService;

    @GetMapping(value = "/department-employee")
    public Result<List<DepartmentEmployeeBO>> list(@Validated DepartmentEmployeeQuery query) {
        return departmentEmployeeService.list(query);
    }

    @PostMapping(value = "/department-employee")
    public Result<Serializable> save(@Validated @RequestBody DepartmentEmployeeSaveDTO entity) {
        return departmentEmployeeService.save(entity);
    }

    @DeleteMapping(value = "/department-employee")
    public Result<Serializable> delete(@RequestBody List<Long> ids) {
        return departmentEmployeeService.delete(ids);
    }
}
