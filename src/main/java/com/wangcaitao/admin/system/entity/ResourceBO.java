package com.wangcaitao.admin.system.entity;

import com.wangcaitao.admin.common.util.DictUtils;
import com.wangcaitao.admin.system.constant.SystemDictCodeConstant;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wangcaitao
 */
@Data
public class ResourceBO implements Serializable {
    private static final long serialVersionUID = 4524604858009221804L;

    /**
     * id
     */
    private Long id;

    /**
     * 状态编码
     */
    private Integer statusCode;

    /**
     * 状态
     */
    private String status;

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
     * 类型
     */
    private String type;

    /**
     * 图标
     */
    private String icon;

    /**
     * 创建时间
     */
    private LocalDateTime createGmt;

    /**
     * 修改时间
     */
    private LocalDateTime modifiedGmt;

    public String getStatus() {
        return DictUtils.getNameByCode(SystemDictCodeConstant.RESOURCE_STATUS, String.valueOf(statusCode));
    }

    public String getType() {
        return DictUtils.getNameByCode(SystemDictCodeConstant.RESOURCE_TYPE, String.valueOf(typeCode));
    }
}
