package com.wangcaitao.admin.system.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class EmployeeStatusUpdateDTO implements Serializable {
    private static final long serialVersionUID = 46338701308951860L;

    /**
     * 员工 id
     */
    @NotNull(message = "id 不能为空")
    private Long id;

    /**
     * 状态编码
     */
    @NotNull(message = "statusCode 不能为空")
    private Integer statusCode;
}
