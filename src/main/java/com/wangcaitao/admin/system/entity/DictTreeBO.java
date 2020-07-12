package com.wangcaitao.admin.system.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@Data
public class DictTreeBO implements Serializable {
    private static final long serialVersionUID = 4622896945662854702L;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 子集
     */
    private List<DictTreeBO> children;
}
