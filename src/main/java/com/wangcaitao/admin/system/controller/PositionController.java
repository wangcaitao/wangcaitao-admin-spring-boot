package com.wangcaitao.admin.system.controller;

import com.wangcaitao.admin.system.entity.PositionBO;
import com.wangcaitao.admin.system.entity.PositionQuery;
import com.wangcaitao.admin.system.entity.PositionSaveDTO;
import com.wangcaitao.admin.system.entity.PositionUpdateDTO;
import com.wangcaitao.admin.system.service.PositionService;
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
public class PositionController {

    @Resource
    private PositionService positionService;

    @GetMapping(value = "/position/{id}")
    public Result<PositionBO> getById(@PathVariable(value = "id") Long id) {
        return positionService.getById(id);
    }

    @GetMapping(value = "/position")
    public Result<List<PositionBO>> list(PositionQuery query) {
        return positionService.list(query);
    }

    @PostMapping(value = "/position")
    public Result<Serializable> save(@Validated @RequestBody PositionSaveDTO entity) {
        return positionService.save(entity);
    }

    @PutMapping(value = "/position")
    public Result<Serializable> update(@Validated @RequestBody PositionUpdateDTO entity) {
        return positionService.update(entity);
    }

    @DeleteMapping(value = "/position/{id}")
    public Result<Serializable> delete(@PathVariable(value = "id") Long id) {
        return positionService.delete(id);
    }

    @PutMapping(value = "/position-sort")
    public Result<Serializable> updateSort(@Validated @RequestBody UpdateSortDTO entity) {
        return positionService.updateSort(entity);
    }
}
