package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangcaitao.admin.system.entity.EmployeePositionDO;
import com.wangcaitao.admin.system.mapper.EmployeePositionMapper;
import com.wangcaitao.admin.system.service.EmployeePositionService;
import com.wangcaitao.admin.system.service.PositionService;
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
public class EmployeePositionServiceImpl extends ServiceImpl<EmployeePositionMapper, EmployeePositionDO> implements EmployeePositionService {

    @Resource
    private PositionService positionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Long employeeId, List<Long> positionIds) {
        deleteByEmployeeId(employeeId);

        Set<EmployeePositionDO> employeePositionDos = new HashSet<>(16);
        for (Long positionId : positionIds) {
            positionService.validateIdInvalid(positionId);

            EmployeePositionDO employeePositionDo = new EmployeePositionDO();
            employeePositionDo.setEmployeeId(employeeId);
            employeePositionDo.setPositionId(positionId);

            employeePositionDos.add(employeePositionDo);
        }

        saveBatch(employeePositionDos, positionIds.size());
    }

    @Override
    public void deleteByEmployeeId(Long employeeId) {
        Wrapper<EmployeePositionDO> queryWrapper = new QueryWrapper<>(new EmployeePositionDO())
                .eq("employee_id", employeeId);

        remove(queryWrapper);
    }
}
