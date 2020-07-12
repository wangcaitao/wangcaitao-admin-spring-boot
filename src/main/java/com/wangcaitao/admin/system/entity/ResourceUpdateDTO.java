package com.wangcaitao.admin.system.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class ResourceUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1539694294237622666L;

    /**
     * id
     */
    @NotNull(message = "id 不能为空")
    private Long id;

    /**
     * 状态编码
     */
    @NotNull(message = "statusCode 不能为空")
    private Integer statusCode;

    /**
     * 名称
     */
    @NotBlank(message = "name 不能为空")
    @Size(max = 30, message = "name 最大 30 个字符")
    private String name;

    /**
     * 地址
     */
    @Size(max = 128, message = "url 最大 128 个字符")
    private String url;

    /**
     * 请求方式
     */
    @NotBlank(message = "requestMethod 不能为空")
    private String requestMethod;

    /**
     * 类型编码
     */
    @NotNull(message = "typeCode 不能为空")
    private Integer typeCode;

    /**
     * 图标
     */
    @Size(max = 255, message = "icon 最大 255 个字符")
    private String icon;
}
