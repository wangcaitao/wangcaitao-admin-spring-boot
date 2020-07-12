package com.wangcaitao.admin.system.entity;

import com.wangcaitao.admin.common.constant.CommonConstant;
import com.wangcaitao.admin.common.util.DictUtils;
import com.wangcaitao.admin.system.constant.SystemDictCodeConstant;
import com.wangcaitao.common.constant.CommonDictCodeConstant;
import com.wangcaitao.starter.oss.QiniuOssUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangcaitao
 */
@Data
public class EmployeeBO implements Serializable {
    private static final long serialVersionUID = 6521053472952007710L;

    /**
     * id
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 头像
     */
    private String avatarImgUrl;

    /**
     * 帐号状态编码. @see SystemDictCodeConstant.ACCOUNT_STATUS
     */
    private Integer accountStatusCode;

    /**
     * 帐号状态
     */
    private String accountStatus;

    /**
     * 状态编码. @see SystemDictCodeConstant.EMPLOYEE_STATUS
     */
    private Integer statusCode;

    /**
     * 状态
     */
    private String status;

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别编码
     */
    private Integer genderCode;

    /**
     * 性别
     */
    private String gender;

    /**
     * 已勾选部门
     */
    private List<EmployeeDepartmentBO> departments;

    /**
     * 已勾选部门 ids
     */
    private List<Long> departmentIds;

    /**
     * 已勾选部门名称, 英文逗号分开
     */
    private String departmentNames;

    /**
     * 已选择职位
     */
    private List<EmployeePositionBO> positions;

    /**
     * 已选择职位 ids
     */
    private List<Long> positionIds;

    /**
     * 已选择职位名称, 英文逗号分开
     */
    private String positionNames;

    /**
     * 已选择角色 id
     */
    private Long roleId;

    /**
     * 已选择角色名称
     */
    private String roleName;

    /**
     * 创建时间
     */
    private LocalDateTime createGmt;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedGmt;

    public String getAccountStatus() {
        return accountStatusCode == null ? null : DictUtils.getNameByCode(SystemDictCodeConstant.ACCOUNT_STATUS, String.valueOf(accountStatusCode));
    }

    public String getStatus() {
        return statusCode == null ? null : DictUtils.getNameByCode(SystemDictCodeConstant.EMPLOYEE_STATUS, String.valueOf(statusCode));
    }

    public String getAvatarImgUrl() {
        return avatarImgUrl.replace(CommonConstant.STATIC_BASE_URL, QiniuOssUtils.BASE_URL);
    }

    public String getGender() {
        return genderCode == null ? null : DictUtils.getNameByCode(CommonDictCodeConstant.GENDER, String.valueOf(genderCode));
    }

    public List<Long> getPositionIds() {
        positionIds = new ArrayList<>();
        if (CollectionUtils.isEmpty(positions)) {
            return positionIds;
        }

        for (EmployeePositionBO position : positions) {
            positionIds.add(position.getPositionId());
        }

        return positionIds;
    }

    public String getPositionNames() {
        if (CollectionUtils.isEmpty(positions)) {
            return "";
        }

        StringBuilder positionNames = new StringBuilder();
        for (EmployeePositionBO position : positions) {
            positionNames.append(position.getPositionName()).append(",");
        }

        if (StringUtils.isNotEmpty(positionNames)) {
            positionNames.deleteCharAt(positionNames.length() - 1);
        }

        return positionNames.toString();
    }

    public List<Long> getDepartmentIds() {
        departmentIds = new ArrayList<>();
        if (CollectionUtils.isEmpty(departments)) {
            return departmentIds;
        }

        for (EmployeeDepartmentBO department : departments) {
            departmentIds.add(department.getDepartmentId());
        }

        return departmentIds;
    }

    public String getDepartmentNames() {
        if (CollectionUtils.isEmpty(departments)) {
            return "";
        }

        StringBuilder departmentNames = new StringBuilder();
        for (EmployeeDepartmentBO department : departments) {
            departmentNames.append(department.getDepartmentName()).append(",");
        }

        if (StringUtils.isNotEmpty(departmentNames)) {
            departmentNames.deleteCharAt(departmentNames.length() - 1);
        }

        return departmentNames.toString();
    }
}
