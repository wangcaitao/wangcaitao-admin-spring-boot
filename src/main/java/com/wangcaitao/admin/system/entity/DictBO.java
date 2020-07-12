package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wangcaitao
 */
@Data
public class DictBO implements Serializable {
    private static final long serialVersionUID = -5268587466553893192L;

    /**
     * id
     */
    private Long id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    private LocalDateTime createGmt;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedGmt;
}
