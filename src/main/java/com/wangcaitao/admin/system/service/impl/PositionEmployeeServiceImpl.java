package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangcaitao.admin.system.entity.PositionEmployeeBO;
import com.wangcaitao.admin.system.entity.PositionEmployeeDO;
import com.wangcaitao.admin.system.entity.PositionEmployeeQuery;
import com.wangcaitao.admin.system.entity.PositionEmployeeSaveDTO;
import com.wangcaitao.admin.system.mapper.PositionEmployeeMapper;
import com.wangcaitao.admin.system.service.EmployeeService;
import com.wangcaitao.admin.system.service.PositionEmployeeService;
import com.wangcaitao.admin.system.service.PositionService;
import com.wangcaitao.common.constant.CommonErrorEnum;
import com.wangcaitao.common.constant.HttpStatusEnum;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.util.ResultUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wangcaitao
 */
@Service
public class PositionEmployeeServiceImpl extends ServiceImpl<PositionEmployeeMapper, PositionEmployeeDO> implements PositionEmployeeService {

    @Resource
    private PositionService positionService;

    @Resource
    private EmployeeService employeeService;

    @Override
    public Result<List<PositionEmployeeBO>> list(PositionEmployeeQuery query) {
        if (query.getPagination()) {
            int pageNum = query.getPageNum();
            int pageSize = query.getPageSize();

            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<PositionEmployeeBO> positionEmployeeBos = baseMapper.list(query);

            return ResultUtils.pagination(pageNum, pageSize, page.getPages(), page.getTotal(), positionEmployeeBos);
        } else {
            return ResultUtils.error(CommonErrorEnum.NOT_SUPPORT_LIST_QUERY);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Serializable> save(PositionEmployeeSaveDTO entity) {
        Long positionId = entity.getPositionId();
        positionService.validateIdInvalid(positionId);

        List<Long> employeeIds = entity.getEmployeeIds();
        if (!CollectionUtils.isEmpty(employeeIds)) {
            Set<PositionEmployeeDO> positionEmployeeDos = new HashSet<>();
            for (Long employeeId : employeeIds) {
                employeeService.validateIdInvalid(employeeId);

                if (exist(positionId, employeeId)) {
                    continue;
                }

                PositionEmployeeDO positionEmployeeDo = new PositionEmployeeDO();
                positionEmployeeDo.setPositionId(positionId);
                positionEmployeeDo.setEmployeeId(employeeId);

                positionEmployeeDos.add(positionEmployeeDo);
            }

            saveBatch(positionEmployeeDos, employeeIds.size());
        }

        return ResultUtils.success();
    }

    @Override
    public Result<Serializable> delete(List<Long> ids) {
        return removeByIds(ids) ? ResultUtils.success() : ResultUtils.error(HttpStatusEnum.NOT_FOUND);
    }

    @Override
    public void deleteByPositionId(Long positionId) {
        Wrapper<PositionEmployeeDO> queryWrapper = new QueryWrapper<PositionEmployeeDO>()
                .eq("position_id", positionId);

        remove(queryWrapper);
    }

    /**
     * 是否存在
     *
     * @param positionId     positionId
     * @param employeeId employeeId
     * @return 是否存在. true: 存在
     */
    private boolean exist(Long positionId, Long employeeId) {
        Wrapper<PositionEmployeeDO> queryWrapper = new QueryWrapper<PositionEmployeeDO>()
                .eq("position_id", positionId)
                .eq("employee_id", employeeId);

        return count(queryWrapper) > 0;
    }
}
