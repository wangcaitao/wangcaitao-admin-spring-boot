package com.wangcaitao.admin.employee.entity;

import com.wangcaitao.admin.common.constant.CommonConstant;
import com.wangcaitao.starter.oss.QiniuOssUtils;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class EmployeeAccountAvatarUpdateDTO implements Serializable {
    private static final long serialVersionUID = -3183339679697599668L;

    /**
     * id
     */
    private Long id;

    /**
     * 头像地址
     */
    @NotBlank(message = "avatarImgUrl 不能为空")
    private String avatarImgUrl;

    public String getAvatarImgUrl() {
        return avatarImgUrl.replace(QiniuOssUtils.BASE_URL, CommonConstant.STATIC_BASE_URL);
    }
}
