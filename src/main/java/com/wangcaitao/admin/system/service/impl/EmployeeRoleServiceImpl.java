package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangcaitao.admin.system.entity.EmployeeRoleDO;
import com.wangcaitao.admin.system.mapper.EmployeeRoleMapper;
import com.wangcaitao.admin.system.service.EmployeeRoleService;
import com.wangcaitao.admin.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wangcaitao
 */
@Service
@Slf4j
public class EmployeeRoleServiceImpl extends ServiceImpl<EmployeeRoleMapper, EmployeeRoleDO> implements EmployeeRoleService {

    @Resource
    private RoleService roleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Long employeeId, Long roleId) {
        deleteByEmployeeId(employeeId);

        if (null == roleId) {
            return;
        }

        roleService.validateIdInvalid(roleId);

        EmployeeRoleDO employeeRoleDo = new EmployeeRoleDO();
        employeeRoleDo.setEmployeeId(employeeId);
        employeeRoleDo.setRoleId(roleId);

        save(employeeRoleDo);
    }

    @Override
    public void deleteByEmployeeId(Long employeeId) {
        Wrapper<EmployeeRoleDO> queryWrapper = new QueryWrapper<>(new EmployeeRoleDO())
                .eq("employee_id", employeeId);

        remove(queryWrapper);
    }
}
