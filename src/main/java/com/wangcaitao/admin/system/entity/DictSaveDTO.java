package com.wangcaitao.admin.system.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.AssertFalse;
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
    @Size(max = 30, message = "code 最大 30 个字符")
    private String code;

    /**
     * 名称
     */
    @NotBlank(message = "name 不能为空")
    @Size(max = 30, message = "name 最大 30 个字符")
    private String name;

    @AssertFalse(message = "code 不能为空")
    public boolean isEmptyCodeWithParentCodeIsEmpty() {
        if (StringUtils.isBlank(parentCode)) {
            return StringUtils.isBlank(code);
        }

        return false;
    }

    public String getCode() {
        return StringUtils.isBlank(code) ? "" : code;
    }
}
