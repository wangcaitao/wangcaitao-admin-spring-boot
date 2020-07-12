package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wangcaitao
 */
@Data
public class PropertyBO implements Serializable {
    private static final long serialVersionUID = 3405009700960119656L;

    /**
     * id
     */
    private Long id;

    /**
     * 属性名
     */
    private String name;

    /**
     * 属性值
     */
    private String value;

    /**
     * 说明
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createGmt;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedGmt;
}
