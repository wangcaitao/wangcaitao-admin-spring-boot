package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangcaitao.admin.common.util.PropertyUtils;
import com.wangcaitao.admin.system.constant.EmployeeConstant;
import com.wangcaitao.admin.system.constant.SystemPropertyNameConstant;
import com.wangcaitao.admin.system.entity.EmployeeAccountStatusUpdateDTO;
import com.wangcaitao.admin.system.entity.EmployeeBO;
import com.wangcaitao.admin.system.entity.EmployeeDO;
import com.wangcaitao.admin.system.entity.EmployeeQuery;
import com.wangcaitao.admin.system.entity.EmployeeSaveDTO;
import com.wangcaitao.admin.system.entity.EmployeeStatusUpdateDTO;
import com.wangcaitao.admin.system.entity.EmployeeUpdateDTO;
import com.wangcaitao.admin.system.entity.EmployeeUpdatePasswordDTO;
import com.wangcaitao.admin.system.mapper.EmployeeMapper;
import com.wangcaitao.admin.system.service.EmployeeDepartmentService;
import com.wangcaitao.admin.system.service.EmployeePositionService;
import com.wangcaitao.admin.system.service.EmployeeRoleService;
import com.wangcaitao.admin.system.service.EmployeeService;
import com.wangcaitao.common.constant.CommonErrorEnum;
import com.wangcaitao.common.constant.HttpStatusEnum;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.exception.ResultException;
import com.wangcaitao.common.util.JacksonUtils;
import com.wangcaitao.common.util.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcaitao
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, EmployeeDO> implements EmployeeService {

    @Resource
    private EmployeeDepartmentService employeeDepartmentService;

    @Resource
    private EmployeePositionService employeePositionService;

    @Resource
    private EmployeeRoleService employeeRoleService;

    @Override
    public Result<EmployeeBO> getById(Long id) {
        EmployeeBO employeeBo = baseMapper.getById(id);
        if (null == employeeBo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        return ResultUtils.success(employeeBo);
    }

    @Override
    public Result<List<EmployeeBO>> list(EmployeeQuery query) {
        if (query.getPagination()) {
            int pageNum = query.getPageNum();
            int pageSize = query.getPageSize();

            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<Long> ids = baseMapper.listIds(query);
            if (CollectionUtils.isEmpty(ids)) {
                return ResultUtils.pagination(pageNum, pageSize, 0, new ArrayList<>());
            }

            return ResultUtils.pagination(pageNum, pageSize, page.getPages(), page.getTotal(), baseMapper.listByIds(ids));
        } else {
            return ResultUtils.error(CommonErrorEnum.NOT_SUPPORT_LIST_QUERY);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> save(EmployeeSaveDTO entity) {
        validatePhoneNumberExist(null, entity.getPhoneNumber());
        validateJobNumberExist(null, entity.getJobNumber());

        EmployeeDO employeeDo = JacksonUtils.convertObject(entity, EmployeeDO.class);
        employeeDo.setPassword(DigestUtils.md5DigestAsHex(PropertyUtils.getValueByName(SystemPropertyNameConstant.DEFAULT_PASSWORD).getBytes()));

        if (save(employeeDo)) {
            Long employeeId = employeeDo.getId();

            employeeDepartmentService.save(employeeId, entity.getDepartmentIds());
            employeePositionService.save(employeeId, entity.getPositionIds());
            employeeRoleService.save(employeeId, entity.getRoleId());

            return ResultUtils.success();
        } else {
            return ResultUtils.error();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> update(EmployeeUpdateDTO entity) {
        Long id = entity.getId();
        validatePhoneNumberExist(id, entity.getPhoneNumber());
        validateJobNumberExist(id, entity.getJobNumber());

        EmployeeDO employeeDo = JacksonUtils.convertObject(entity, EmployeeDO.class);
        if (updateById(employeeDo)) {
            employeeDepartmentService.save(id, entity.getDepartmentIds());
            employeePositionService.save(id, entity.getPositionIds());
            employeeRoleService.save(id, entity.getRoleId());

            return ResultUtils.success();
        } else {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }
    }

    @Override
    public Result<Serializable> updatePassword(EmployeeUpdatePasswordDTO entity) {
        Long id = entity.getId();
        EmployeeDO employeeDo = super.getById(id);
        if (null == employeeDo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        EmployeeDO updateEmployeeDo = new EmployeeDO();
        updateEmployeeDo.setId(entity.getId());
        updateEmployeeDo.setPassword(DigestUtils.md5DigestAsHex((PropertyUtils.getValueByName(SystemPropertyNameConstant.DEFAULT_PASSWORD)).getBytes()));

        return updateById(updateEmployeeDo) ? ResultUtils.success() : ResultUtils.error(HttpStatusEnum.NOT_FOUND);
    }

    @Override
    public Result<Serializable> updateStatus(EmployeeStatusUpdateDTO entity) {
        Wrapper<EmployeeDO> queryWrapper = new QueryWrapper<>(new EmployeeDO())
                .select("id", "status_code")
                .eq("id", entity.getId())
                .ne("id", EmployeeConstant.SUPER_ADMIN_ID);
        EmployeeDO employeeDo = getOne(queryWrapper);
        if (null == employeeDo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        employeeDo.setStatusCode(entity.getStatusCode());

        updateById(employeeDo);

        return ResultUtils.success();
    }

    @Override
    public Result<Serializable> updateAccountStatus(EmployeeAccountStatusUpdateDTO entity) {
        Wrapper<EmployeeDO> queryWrapper = new QueryWrapper<>(new EmployeeDO())
                .select("id", "account_status_code")
                .eq("id", entity.getId())
                .ne("id", EmployeeConstant.SUPER_ADMIN_ID);
        EmployeeDO employeeDo = getOne(queryWrapper);
        if (null == employeeDo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        employeeDo.setAccountStatusCode(entity.getAccountStatusCode());

        updateById(employeeDo);

        return ResultUtils.success();
    }

    @Override
    public void validateIdInvalid(Long id) {
        Wrapper<EmployeeDO> queryWrapper = new QueryWrapper<EmployeeDO>()
                .eq("id", id);

        if (count(queryWrapper) == 0) {
            throw new ResultException("员工 id 无效");
        }
    }

    /**
     * 校验手机号码是否已存在
     *
     * @param id          id
     * @param phoneNumber 手机号码
     */
    private void validatePhoneNumberExist(Long id, String phoneNumber) {
        if (StringUtils.isEmpty(phoneNumber)) {
            return;
        }

        Wrapper<EmployeeDO> queryWrapper = new QueryWrapper<>(new EmployeeDO())
                .eq("phone_number", phoneNumber)
                .ne(null != id, "id", id);
        if (0 != count(queryWrapper)) {
            throw new ResultException("手机号码已存在");
        }
    }

    /**
     * 校验工号是否已存在
     *
     * @param id        id
     * @param jobNumber jobNumber
     */
    private void validateJobNumberExist(Long id, String jobNumber) {
        if (StringUtils.isEmpty(jobNumber)) {
            return;
        }

        Wrapper<EmployeeDO> queryWrapper = new QueryWrapper<>(new EmployeeDO())
                .eq("job_number", jobNumber)
                .ne(null != id, "id", id);

        if (count(queryWrapper) > 0) {
            throw new ResultException("工号已存在");
        }
    }
}
