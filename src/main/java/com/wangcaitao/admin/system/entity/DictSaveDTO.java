package com.wangcaitao.admin.system.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class DictSaveDTO implements Serializable {
    private static final long serialVersionUID = 1041435262740861604L;

    /**
     * 父级编码
     */
    @Size(max = 30, message = "parentCode 最大 30 个字符")
    private String parentCode;

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
