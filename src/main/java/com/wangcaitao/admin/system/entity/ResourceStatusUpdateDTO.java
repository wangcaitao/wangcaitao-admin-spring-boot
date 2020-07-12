package com.wangcaitao.admin.system.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangcaitao
 */
@Data
public class ResourceStatusUpdateDTO implements Serializable {
    private static final long serialVersionUID = -3979348735381047575L;

    /**
     * id
     */
    @NotNull(message = "id 不能为空")
    private Long id;

    /**
     * 状态编码
     */
    @NotNull(message = "statusCode 不能为空")
    private Integer statusCode;
}
