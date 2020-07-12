package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangcaitao.admin.system.entity.EmployeeDepartmentDO;
import com.wangcaitao.admin.system.mapper.EmployeeDepartmentMapper;
import com.wangcaitao.admin.system.service.DepartmentRelationService;
import com.wangcaitao.admin.system.service.DepartmentService;
import com.wangcaitao.admin.system.service.EmployeeDepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wangcaitao
 */
@Service
@Slf4j
public class EmployeeDepartmentServiceImpl extends ServiceImpl<EmployeeDepartmentMapper, EmployeeDepartmentDO> implements EmployeeDepartmentService {

    @Resource
    private DepartmentService departmentService;

    @Resource
    private DepartmentRelationService departmentRelationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Long employeeId, List<Long> departmentIds) {
        deleteByEmployeeId(employeeId);

        Set<EmployeeDepartmentDO> employeeDepartmentDos = new HashSet<>(16);
        for (Long departmentId : departmentIds) {
            departmentService.validateIdInvalid(departmentId);

            EmployeeDepartmentDO employeeDepartmentDo = new EmployeeDepartmentDO();
            employeeDepartmentDo.setEmployeeId(employeeId);
            employeeDepartmentDo.setDepartmentId(departmentId);

            employeeDepartmentDos.add(employeeDepartmentDo);
        }

        saveBatch(employeeDepartmentDos, departmentIds.size());
    }

    @Override
    public void deleteByEmployeeId(Long employeeId) {
        Wrapper<EmployeeDepartmentDO> queryWrapper = new QueryWrapper<>(new EmployeeDepartmentDO())
                .eq("employee_id", employeeId);

        remove(queryWrapper);
    }
}
