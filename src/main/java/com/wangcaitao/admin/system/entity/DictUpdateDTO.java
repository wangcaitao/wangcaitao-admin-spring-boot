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
public class DictUpdateDTO implements Serializable {
    private static final long serialVersionUID = -2894415708611947524L;

    /**
     * id
     */
    @NotNull(message = "id 不能为空")
    private Long id;

    /**
     * 编码
     */
    @NotBlank(message = "code 不能为空")
    @Size(max = 30, message = "code 最大 30 个字符")
    private String code;

    /**
     * 名称
     */
    @NotBlank(message = "name 不能为空")
    @Size(max = 30, message = "name 最大 30 个字符")
    private String name;
}
