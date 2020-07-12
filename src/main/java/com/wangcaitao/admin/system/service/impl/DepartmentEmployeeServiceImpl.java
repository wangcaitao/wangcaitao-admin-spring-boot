package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangcaitao.admin.system.entity.DepartmentEmployeeBO;
import com.wangcaitao.admin.system.entity.DepartmentEmployeeDO;
import com.wangcaitao.admin.system.entity.DepartmentEmployeeQuery;
import com.wangcaitao.admin.system.entity.DepartmentEmployeeSaveDTO;
import com.wangcaitao.admin.system.mapper.DepartmentEmployeeMapper;
import com.wangcaitao.admin.system.service.DepartmentEmployeeService;
import com.wangcaitao.admin.system.service.DepartmentService;
import com.wangcaitao.admin.system.service.EmployeeService;
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
public class DepartmentEmployeeServiceImpl extends ServiceImpl<DepartmentEmployeeMapper, DepartmentEmployeeDO> implements DepartmentEmployeeService {


    @Resource
    private DepartmentService departmentService;

    @Resource
    private EmployeeService employeeService;

    @Override
    public Result<List<DepartmentEmployeeBO>> list(DepartmentEmployeeQuery query) {
        if (query.getPagination()) {
            int pageNum = query.getPageNum();
            int pageSize = query.getPageSize();

            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<DepartmentEmployeeBO> departmentEmployeeBos = baseMapper.list(query);

            return ResultUtils.pagination(pageNum, pageSize, page.getPages(), page.getTotal(), departmentEmployeeBos);
        } else {
            return ResultUtils.error(CommonErrorEnum.NOT_SUPPORT_LIST_QUERY);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> save(DepartmentEmployeeSaveDTO entity) {
        Long departmentId = entity.getDepartmentId();
        departmentService.validateIdInvalid(departmentId);

        List<Long> employeeIds = entity.getEmployeeIds();
        if (!CollectionUtils.isEmpty(employeeIds)) {
            Set<DepartmentEmployeeDO> departmentEmployeeDos = new HashSet<>();
            for (Long employeeId : employeeIds) {
                employeeService.validateIdInvalid(employeeId);

                if (exist(departmentId, employeeId)) {
                    continue;
                }

                DepartmentEmployeeDO departmentEmployeeDo = new DepartmentEmployeeDO();
                departmentEmployeeDo.setDepartmentId(departmentId);
                departmentEmployeeDo.setEmployeeId(employeeId);

                departmentEmployeeDos.add(departmentEmployeeDo);
            }

            saveBatch(departmentEmployeeDos, employeeIds.size());
        }

        return ResultUtils.success();
    }

    @Override
    public Result<Serializable> delete(List<Long> ids) {
        return removeByIds(ids) ? ResultUtils.success() : ResultUtils.error(HttpStatusEnum.NOT_FOUND);
    }

    @Override
    public void deleteByDepartmentId(Long departmentId) {
        Wrapper<DepartmentEmployeeDO> queryWrapper = new QueryWrapper<>(new DepartmentEmployeeDO())
                .eq("department_id", departmentId);

        remove(queryWrapper);
    }

    /**
     * 是否存在
     *
     * @param departmentId departmentId
     * @param employeeId   employeeId
     * @return 是否存在. true: 存在
     */
    private boolean exist(Long departmentId, Long employeeId) {
        Wrapper<DepartmentEmployeeDO> queryWrapper = new QueryWrapper<DepartmentEmployeeDO>()
                .eq("department_id", departmentId)
                .eq("employee_id", employeeId);

        return count(queryWrapper) > 0;
    }
}
