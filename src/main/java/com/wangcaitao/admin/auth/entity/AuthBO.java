package com.wangcaitao.admin.auth.entity;

import com.wangcaitao.admin.system.entity.MenuBO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@Data
public class AuthBO implements Serializable {
    private static final long serialVersionUID = 7114142240193286456L;

    /**
     * token
     */
    private String token;

    /**
     * 姓名
     */
    private String name;

    /**
     * 头像
     */
    private String avatarImgUrl;

    /**
     * 菜单
     */
    private List<MenuBO> menus;

    /**
     * 已授权资源
     */
    private List<String> authorizedResources;
}
