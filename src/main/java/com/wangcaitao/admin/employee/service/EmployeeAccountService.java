package com.wangcaitao.admin.employee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangcaitao.admin.employee.entity.EmployeeAccountAvatarUpdateDTO;
import com.wangcaitao.admin.employee.entity.EmployeeAccountBO;
import com.wangcaitao.admin.employee.entity.EmployeeAccountDO;
import com.wangcaitao.admin.employee.entity.EmployeeAccountPasswordUpdateDTO;
import com.wangcaitao.admin.employee.entity.EmployeeAccountUpdateDTO;
import com.wangcaitao.common.entity.Result;

import java.io.Serializable;

/**
 * @author wangcaitao
 */
public interface EmployeeAccountService extends IService<EmployeeAccountDO> {

    /**
     * 根据手机号码获取
     *
     * @param phoneNumber phoneNumber
     * @return entity
     */
    EmployeeAccountBO getByPhoneNumber(String phoneNumber);

    /**
     * profile
     *
     * @return Result
     */
    Result<EmployeeAccountBO> profile();

    /**
     * 修改
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> update(EmployeeAccountUpdateDTO entity);

    /**
     * 修改密码
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> updatePassword(EmployeeAccountPasswordUpdateDTO entity);

    /**
     * 修改头像
     *
     * @param entity entity
     * @return Result
     */
    Result<Serializable> updateAvatar(EmployeeAccountAvatarUpdateDTO entity);
}
