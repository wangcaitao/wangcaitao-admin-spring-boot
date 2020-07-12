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
public class PropertyUpdateDTO implements Serializable {
    private static final long serialVersionUID = 3158350630745076153L;

    /**
     * id
     */
    @NotNull(message = "id 不能为空")
    private Long id;

    /**
     * 属性名
     */
    @NotBlank(message = "name 不能为空")
    @Size(max = 200, message = "name 最大 200 个字符")
    private String name;

    /**
     * 属性值
     */
    @NotBlank(message = "value 不能为空")
    @Size(max = 200, message = "value 最大 200 个字符")
    private String value;

    /**
     * 说明
     */
    @Size(max = 200, message = "description 最大 200 个字符")
    private String description;
}
