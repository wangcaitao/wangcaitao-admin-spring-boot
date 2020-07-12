package com.wangcaitao.admin.system.controller;

import com.wangcaitao.admin.system.entity.DepartmentBO;
import com.wangcaitao.admin.system.entity.DepartmentQuery;
import com.wangcaitao.admin.system.entity.DepartmentSaveDTO;
import com.wangcaitao.admin.system.entity.DepartmentTreeBO;
import com.wangcaitao.admin.system.entity.DepartmentUpdateDTO;
import com.wangcaitao.admin.system.service.DepartmentService;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.entity.UpdateSortDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @GetMapping(value = "/department/{id}")
    public Result<DepartmentBO> getById(@PathVariable(value = "id") Long id) {
        return departmentService.getById(id);
    }

    @GetMapping(value = "/department")
    public Result<List<DepartmentBO>> list(DepartmentQuery query) {
        return departmentService.list(query);
    }

    @PostMapping(value = "/department")
    public Result<Serializable> save(@Validated @RequestBody DepartmentSaveDTO entity) {
        return departmentService.save(entity);
    }

    @PutMapping(value = "/department")
    public Result<Serializable> update(@Validated @RequestBody DepartmentUpdateDTO entity) {
        return departmentService.update(entity);
    }

    @DeleteMapping(value = "/department/{id}")
    public Result<Serializable> delete(@PathVariable(value = "id") Long id) {
        return departmentService.delete(id);
    }

    @PutMapping(value = "/department-sort")
    public Result<Serializable> updateSort(@Validated @RequestBody UpdateSortDTO entity) {
        return departmentService.updateSort(entity);
    }

    @GetMapping(value = "/department-tree")
    public Result<List<DepartmentTreeBO>> tree() {
        return departmentService.tree();
    }
}
