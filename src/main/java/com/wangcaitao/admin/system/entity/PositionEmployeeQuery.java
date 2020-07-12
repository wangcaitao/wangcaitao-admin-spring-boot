package com.wangcaitao.admin.system.entity;

import com.wangcaitao.common.entity.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author wangcaitao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PositionEmployeeQuery extends BaseQuery {
    private static final long serialVersionUID = -1585589748690184507L;

    /**
     * position.id
     */
    @NotNull(message = "positionId 不能为空")
    private Long positionId;

    /**
     * 关联状态. 0: 未关联, 1: 已关联
     */
    private Integer relationStatusCode;

    /**
     * 姓名
     */
    private String employeeName;

    /**
     * 手机号码
     */
    private String phoneNumber;
}
