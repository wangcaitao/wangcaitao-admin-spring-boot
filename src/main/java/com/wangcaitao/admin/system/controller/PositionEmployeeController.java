package com.wangcaitao.admin.system.controller;

import com.wangcaitao.admin.system.entity.PositionEmployeeBO;
import com.wangcaitao.admin.system.entity.PositionEmployeeQuery;
import com.wangcaitao.admin.system.entity.PositionEmployeeSaveDTO;
import com.wangcaitao.admin.system.service.PositionEmployeeService;
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
public class PositionEmployeeController {

    @Resource
    private PositionEmployeeService positionEmployeeService;

    @GetMapping(value = "/position-employee")
    public Result<List<PositionEmployeeBO>> list(@Validated PositionEmployeeQuery query) {
        return positionEmployeeService.list(query);
    }

    @PostMapping(value = "/position-employee")
    public Result<Serializable> save(@Validated @RequestBody PositionEmployeeSaveDTO entity) {
        return positionEmployeeService.save(entity);
    }

    @DeleteMapping(value = "/position-employee")
    public Result<Serializable> delete(@RequestBody List<Long> ids) {
        return positionEmployeeService.delete(ids);
    }
}
