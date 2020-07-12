package com.wangcaitao.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.wangcaitao.common.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wangcaitao
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "position")
public class PositionDO extends BaseDO {
    private static final long serialVersionUID = 4964960213897852521L;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;
}
