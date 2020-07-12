package com.wangcaitao.admin.system.controller;

import com.wangcaitao.admin.system.entity.RoleBO;
import com.wangcaitao.admin.system.entity.RoleQuery;
import com.wangcaitao.admin.system.entity.RoleSaveDTO;
import com.wangcaitao.admin.system.entity.RoleUpdateDTO;
import com.wangcaitao.admin.system.service.RoleService;
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
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping(value = "/role/{id}")
    public Result<RoleBO> getById(@PathVariable(value = "id") Long id) {
        return roleService.getById(id);
    }

    @GetMapping(value = "/role")
    public Result<List<RoleBO>> list(RoleQuery query) {
        return roleService.list(query);
    }

    @PostMapping(value = "/role")
    public Result<Serializable> save(@Validated @RequestBody RoleSaveDTO entity) {
        return roleService.save(entity);
    }

    @PutMapping(value = "/role")
    public Result<Serializable> update(@Validated @RequestBody RoleUpdateDTO entity) {
        return roleService.update(entity);
    }

    @DeleteMapping(value = "/role/{id}")
    public Result<Serializable> delete(@PathVariable(value = "id") Long id) {
        return roleService.delete(id);
    }

    @PutMapping(value = "/role-sort")
    public Result<Serializable> updateSort(@Validated @RequestBody UpdateSortDTO entity) {
        return roleService.updateSort(entity);
    }
}
