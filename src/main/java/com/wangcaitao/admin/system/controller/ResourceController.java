package com.wangcaitao.admin.system.controller;

import com.wangcaitao.admin.system.entity.ResourceBO;
import com.wangcaitao.admin.system.entity.ResourceQuery;
import com.wangcaitao.admin.system.entity.ResourceSaveDTO;
import com.wangcaitao.admin.system.entity.ResourceStatusUpdateDTO;
import com.wangcaitao.admin.system.entity.ResourceTreeBO;
import com.wangcaitao.admin.system.entity.ResourceUpdateDTO;
import com.wangcaitao.admin.system.service.ResourceService;
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
public class ResourceController {

    @Resource
    private ResourceService resourceService;

    @GetMapping(value = "/resource/{id}")
    public Result<ResourceBO> getById(@PathVariable(value = "id") Long id) {
        return resourceService.getById(id);
    }

    @GetMapping(value = "/resource")
    public Result<List<ResourceBO>> list(ResourceQuery query) {
        return resourceService.list(query);
    }

    @PostMapping(value = "/resource")
    public Result<Serializable> save(@Validated @RequestBody ResourceSaveDTO entity) {
        return resourceService.save(entity);
    }

    @PutMapping(value = "/resource")
    public Result<Serializable> update(@Validated @RequestBody ResourceUpdateDTO entity) {
        return resourceService.update(entity);
    }

    @DeleteMapping(value = "/resource/{id}")
    public Result<Serializable> delete(@PathVariable(value = "id") Long id) {
        return resourceService.delete(id);
    }

    @PutMapping(value = "/resource-sort")
    public Result<Serializable> updateSort(@Validated @RequestBody UpdateSortDTO entity) {
        return resourceService.updateSort(entity);
    }

    @PutMapping(value = "/resource-status")
    public Result<Serializable> updateStatus(@Validated @RequestBody ResourceStatusUpdateDTO entity) {
        return resourceService.updateStatus(entity);
    }

    @GetMapping(value = "/resource-tree")
    public Result<List<ResourceTreeBO>> tree() {
        return resourceService.tree();
    }
}
