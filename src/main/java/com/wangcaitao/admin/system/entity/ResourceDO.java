package com.wangcaitao.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wangcaitao.common.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangcaitao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "resource")
public class ResourceDO extends BaseDO {
    private static final long serialVersionUID = 4343196200638737654L;

    /**
     * 状态编码. @see SystemDictCodeConstant.RESOURCE_STATUS
     */
    private Integer statusCode;

    /**
     * 名称
     */
    private String name;

    /**
     * 地址
     */
    private String url;

    /**
     * 请求方式. @see SystemDictCodeConstant.REQUEST_METHOD
     */
    private String requestMethod;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 类型编码. @see SystemDictCodeConstant.RESOURCE_TYPE
     */
    private Integer typeCode;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否顶级. 0: 不是, 1: 是
     */
    private Integer topStatusCode;
}
