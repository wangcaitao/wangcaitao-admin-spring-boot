package com.wangcaitao.admin.system.controller;

import com.wangcaitao.admin.system.entity.PropertyBO;
import com.wangcaitao.admin.system.entity.PropertyQuery;
import com.wangcaitao.admin.system.entity.PropertySaveDTO;
import com.wangcaitao.admin.system.entity.PropertyUpdateDTO;
import com.wangcaitao.admin.system.service.PropertyService;
import com.wangcaitao.common.entity.Result;
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
public class PropertyController {

    @Resource
    private PropertyService propertyService;

    @GetMapping(value = "/property/{id}")
    public Result<PropertyBO> getById(@PathVariable(value = "id") Long id) {
        return propertyService.getById(id);
    }

    @GetMapping(value = "/property")
    public Result<List<PropertyBO>> list(PropertyQuery query) {
        return propertyService.list(query);
    }

    @PostMapping(value = "/property")
    public Result<Serializable> save(@Validated @RequestBody PropertySaveDTO entity) {
        return propertyService.save(entity);
    }

    @PutMapping(value = "/property")
    public Result<Serializable> update(@Validated @RequestBody PropertyUpdateDTO entity) {
        return propertyService.update(entity);
    }

    @DeleteMapping(value = "/property/{id}")
    public Result<Serializable> delete(@PathVariable(value = "id") Long id) {
        return propertyService.delete(id);
    }
}
