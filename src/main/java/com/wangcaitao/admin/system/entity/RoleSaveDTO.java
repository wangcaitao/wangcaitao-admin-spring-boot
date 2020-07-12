package com.wangcaitao.admin.system.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangcaitao
 */
@Data
public class RoleSaveDTO implements Serializable {
    private static final long serialVersionUID = 5418655509043328167L;

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

    /**
     * 资源 ids
     */
    private List<Long> resourceIds;
}
