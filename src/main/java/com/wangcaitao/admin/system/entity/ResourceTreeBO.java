package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@Data
public class ResourceTreeBO implements Serializable {
    private static final long serialVersionUID = -2362831692572454901L;

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
     * 请求方式
     */
    private String requestMethod;

    /**
     * 类型编码
     */
    private Integer typeCode;

    /**
     * 图标
     */
    private String icon;

    /**
     * 子集
     */
    private List<ResourceTreeBO> children;
}
