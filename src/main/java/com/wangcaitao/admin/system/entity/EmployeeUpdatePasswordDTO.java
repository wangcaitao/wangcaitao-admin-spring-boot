package com.wangcaitao.admin.system.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class EmployeeUpdatePasswordDTO implements Serializable {
    private static final long serialVersionUID = 2452726881872186331L;

    @NotNull(message = "id 不能为空")
    private Long id;
}
