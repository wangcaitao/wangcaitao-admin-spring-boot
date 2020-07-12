package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wangcaitao
 */
@Data
public class PositionBO implements Serializable {
    private static final long serialVersionUID = 692876256331904437L;

    /**
     * id
     */
    private Long id;

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
