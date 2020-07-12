package com.wangcaitao.admin.system.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class EmployeeAccountStatusUpdateDTO implements Serializable {
    private static final long serialVersionUID = -3186715893565335201L;

    /**
     * 员工 id
     */
    @NotNull(message = "id 不能为空")
    private Long id;

    /**
     * 帐号状态
     */
    @NotNull(message = "accountStatusCode 不能为空")
    private Integer accountStatusCode;
}
