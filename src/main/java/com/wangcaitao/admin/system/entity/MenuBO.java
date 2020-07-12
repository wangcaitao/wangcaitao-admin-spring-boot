package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@Data
public class MenuBO implements Serializable {
    private static final long serialVersionUID = -3766005955021067380L;

    /**
     * id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 地址
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 子集数据
     */
    private List<MenuBO> children;
}
