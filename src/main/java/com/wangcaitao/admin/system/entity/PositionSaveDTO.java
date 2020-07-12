package com.wangcaitao.admin.system.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class PositionSaveDTO implements Serializable {
    private static final long serialVersionUID = 8787662648697832488L;

    /**
     * 名称
     */
    @NotBlank(message = "name 不能为空")
    @Size(max = 30, message = "name 最大 30 个字符")
    private String name;
}
