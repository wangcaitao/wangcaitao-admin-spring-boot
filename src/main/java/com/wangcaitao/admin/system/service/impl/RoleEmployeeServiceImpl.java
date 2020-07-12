package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangcaitao.admin.system.entity.RoleEmployeeBO;
import com.wangcaitao.admin.system.entity.RoleEmployeeDO;
import com.wangcaitao.admin.system.entity.RoleEmployeeQuery;
import com.wangcaitao.admin.system.entity.RoleEmployeeSaveDTO;
import com.wangcaitao.admin.system.mapper.RoleEmployeeMapper;
import com.wangcaitao.admin.system.service.EmployeeService;
import com.wangcaitao.admin.system.service.RoleEmployeeService;
import com.wangcaitao.admin.system.service.RoleService;
import com.wangcaitao.common.constant.CommonErrorEnum;
import com.wangcaitao.common.constant.HttpStatusEnum;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.util.ResultUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wangcaitao
 */
@Service
public class RoleEmployeeServiceImpl extends ServiceImpl<RoleEmployeeMapper, RoleEmployeeDO> implements RoleEmployeeService {

    @Resource
    private RoleService roleService;

    @Resource
    private EmployeeService employeeService;

    @Override
    public Result<List<RoleEmployeeBO>> list(RoleEmployeeQuery query) {
        if (query.getPagination()) {
            int pageNum = query.getPageNum();
            int pageSize = query.getPageSize();

            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<RoleEmployeeBO> roleEmployeeBos = baseMapper.list(query);

            return ResultUtils.pagination(pageNum, pageSize, page.getPages(), page.getTotal(), roleEmployeeBos);
        } else {
            return ResultUtils.error(CommonErrorEnum.NOT_SUPPORT_LIST_QUERY);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> save(RoleEmployeeSaveDTO entity) {
        Long roleId = entity.getRoleId();
        roleService.validateIdInvalid(roleId);

        List<Long> employeeIds = entity.getEmployeeIds();
        if (!CollectionUtils.isEmpty(employeeIds)) {
            Set<RoleEmployeeDO> roleEmployeeDos = new HashSet<>();
            for (Long employeeId : employeeIds) {
                employeeService.validateIdInvalid(employeeId);

                if (exist(roleId, employeeId)) {
                    continue;
                }

                RoleEmployeeDO roleEmployeeDo = new RoleEmployeeDO();
                roleEmployeeDo.setRoleId(roleId);
                roleEmployeeDo.setEmployeeId(employeeId);

                roleEmployeeDos.add(roleEmployeeDo);
            }

            saveBatch(roleEmployeeDos, employeeIds.size());
        }

        return ResultUtils.success();
    }

    @Override
    public Result<Serializable> delete(List<Long> ids) {
        return removeByIds(ids) ? ResultUtils.success() : ResultUtils.error(HttpStatusEnum.NOT_FOUND);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        Wrapper<RoleEmployeeDO> queryWrapper = new QueryWrapper<RoleEmployeeDO>()
                .eq("role_id", roleId);

        remove(queryWrapper);
    }

    /**
     * 是否存在
     *
     * @param roleId     roleId
     * @param employeeId employeeId
     * @return 是否存在. true: 存在
     */
    private boolean exist(Long roleId, Long employeeId) {
        Wrapper<RoleEmployeeDO> queryWrapper = new QueryWrapper<RoleEmployeeDO>()
                .eq("role_id", roleId)
                .eq("employee_id", employeeId);

        return count(queryWrapper) > 0;
    }
}
