package com.wangcaitao.admin.system.controller;

import com.wangcaitao.admin.system.entity.OperationLogBO;
import com.wangcaitao.admin.system.entity.OperationLogQuery;
import com.wangcaitao.admin.system.service.OperationLogService;
import com.wangcaitao.common.entity.Result;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@RestController
public class OperationLogController {

    @Resource
    private OperationLogService operationLogService;

    @GetMapping(value = "/operation-log/{id}")
    public Result<OperationLogBO> getById(@PathVariable(value = "id") Long id) {
        return operationLogService.getById(id);
    }

    @GetMapping(value = "/operation-log")
    public Result<List<OperationLogBO>> list(OperationLogQuery query) {
        return operationLogService.list(query);
    }

    @DeleteMapping(value = "/operation-log")
    public Result<Serializable> delete(@RequestBody List<Long> ids) {
        return operationLogService.delete(ids);
    }

    @DeleteMapping(value = "operation-log-empty")
    public Result<Serializable> empty() {
        return operationLogService.empty();
    }
}
