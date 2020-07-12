package com.wangcaitao.admin.system.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class DepartmentSaveDTO implements Serializable {
    private static final long serialVersionUID = -6864813605992710292L;
    /**
     * 父级 id
     */
    private Long parentId;

    /**
     * 名称
     */
    @NotBlank(message = "name 不能为空")
    @Size(max = 30, message = "name 最大 30 个字符")
    private String name;

    public void setParentId(Long parentId) {
        if (null != parentId && parentId < 1) {
            parentId = null;
        }

        this.parentId = parentId;
    }
}
