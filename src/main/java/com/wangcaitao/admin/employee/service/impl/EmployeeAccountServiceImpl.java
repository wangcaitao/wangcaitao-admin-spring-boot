package com.wangcaitao.admin.employee.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangcaitao.admin.common.util.EmployeeUtils;
import com.wangcaitao.admin.employee.entity.EmployeeAccountAvatarUpdateDTO;
import com.wangcaitao.admin.employee.entity.EmployeeAccountBO;
import com.wangcaitao.admin.employee.entity.EmployeeAccountDO;
import com.wangcaitao.admin.employee.entity.EmployeeAccountPasswordUpdateDTO;
import com.wangcaitao.admin.employee.entity.EmployeeAccountUpdateDTO;
import com.wangcaitao.admin.employee.mapper.EmployeeAccountMapper;
import com.wangcaitao.admin.employee.service.EmployeeAccountService;
import com.wangcaitao.common.constant.HttpStatusEnum;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.util.JacksonUtils;
import com.wangcaitao.common.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author wangcaitao
 */
@Service
@Slf4j
public class EmployeeAccountServiceImpl extends ServiceImpl<EmployeeAccountMapper, EmployeeAccountDO> implements EmployeeAccountService {

    @Override
    public EmployeeAccountBO getByPhoneNumber(String phoneNumber) {
        return baseMapper.getByPhoneNumber(phoneNumber);
    }

    @Override
    public Result<EmployeeAccountBO> profile() {
        EmployeeAccountBO employeeAccountBo = baseMapper.profile(EmployeeUtils.getEmployeeId());

        return null == employeeAccountBo ? ResultUtils.error(HttpStatusEnum.NOT_FOUND) : ResultUtils.success(employeeAccountBo);
    }

    @Override
    public Result<Serializable> update(EmployeeAccountUpdateDTO entity) {
        entity.setId(EmployeeUtils.getEmployeeId());

        return updateById(JacksonUtils.convertObject(entity, EmployeeAccountDO.class)) ? ResultUtils.success() : ResultUtils.error(HttpStatusEnum.NOT_FOUND);
    }

    @Override
    public Result<Serializable> updatePassword(EmployeeAccountPasswordUpdateDTO entity) {
        Wrapper<EmployeeAccountDO> queryWrapper = new QueryWrapper<EmployeeAccountDO>()
                .select("id", "password")
                .eq("id", EmployeeUtils.getEmployeeId());

        EmployeeAccountDO employeeAccountDo = getOne(queryWrapper);
        if (null == employeeAccountDo) {
            return ResultUtils.error(HttpStatusEnum.NOT_FOUND);
        }

        if (!Objects.equals(employeeAccountDo.getPassword(), DigestUtils.md5DigestAsHex(entity.getOriginalPassword().getBytes()))) {
            return ResultUtils.error("原密码错误");
        }

        String newPassword = entity.getNewPassword();
        if (!Objects.equals(newPassword, entity.getConfirmPassword())) {
            return ResultUtils.error("新密码与确认密码不一致");
        }

        employeeAccountDo.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));

        updateById(employeeAccountDo);

        return ResultUtils.success();
    }

    @Override
    public Result<Serializable> updateAvatar(EmployeeAccountAvatarUpdateDTO entity) {
        entity.setId(EmployeeUtils.getEmployeeId());

        return updateById(JacksonUtils.convertObject(entity, EmployeeAccountDO.class)) ? ResultUtils.success() : ResultUtils.error(HttpStatusEnum.NOT_FOUND);
    }
}
