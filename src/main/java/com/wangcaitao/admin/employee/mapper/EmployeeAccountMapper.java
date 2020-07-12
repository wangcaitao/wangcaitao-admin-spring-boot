package com.wangcaitao.admin.employee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangcaitao.admin.employee.entity.EmployeeAccountBO;
import com.wangcaitao.admin.employee.entity.EmployeeAccountDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangcaitao
 */
public interface EmployeeAccountMapper extends BaseMapper<EmployeeAccountDO> {

    /**
     * 根据手机号码获取
     *
     * @param phoneNumber phoneNumber
     * @return entity
     */
    EmployeeAccountBO getByPhoneNumber(@Param(value = "phoneNumber") String phoneNumber);

    /**
     * profile
     *
     * @param employeeId employeeId
     * @return entity
     */
    EmployeeAccountBO profile(@Param(value = "employeeId") Long employeeId);
}
