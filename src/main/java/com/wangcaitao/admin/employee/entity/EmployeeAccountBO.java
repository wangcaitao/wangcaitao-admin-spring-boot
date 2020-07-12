package com.wangcaitao.admin.employee.entity;

import com.wangcaitao.admin.common.constant.CommonConstant;
import com.wangcaitao.admin.common.util.DictUtils;
import com.wangcaitao.common.constant.CommonDictCodeConstant;
import com.wangcaitao.starter.oss.QiniuOssUtils;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wangcaitao
 */
@Data
public class EmployeeAccountBO implements Serializable {
    private static final long serialVersionUID = -580168905374467396L;

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
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatarImgUrl;

    /**
     * 帐号状态编码. @see SystemDictCodeConstant.ACCOUNT_STATUS
     */
    private Integer accountStatusCode;

    /**
     * 状态编码. @see SystemDictCodeConstant.EMPLOYEE_STATUS
     */
    private Integer statusCode;

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
     * roleId
     */
    private Long roleId;

    /**
     * 部门名称
     */
    private List<String> departmentNames;

    /**
     * 职位名称
     */
    private List<String> positionNames;

    /**
     * 创建时间
     */
    private LocalDateTime createGmt;

    public String getGender() {
        return genderCode == null ? null : DictUtils.getNameByCode(CommonDictCodeConstant.GENDER, String.valueOf(genderCode));
    }

    public String getAvatarImgUrl() {
        return avatarImgUrl.replace(CommonConstant.STATIC_BASE_URL, QiniuOssUtils.BASE_URL);
    }
}
