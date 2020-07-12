package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangcaitao.admin.common.starter.ExtendIgnoredRouterUtils;
import com.wangcaitao.admin.common.util.DictUtils;
import com.wangcaitao.admin.common.util.EmployeeUtils;
import com.wangcaitao.admin.employee.entity.EmployeeAccountBO;
import com.wangcaitao.admin.system.constant.SystemDictCodeConstant;
import com.wangcaitao.admin.system.entity.OperationLogBO;
import com.wangcaitao.admin.system.entity.OperationLogDO;
import com.wangcaitao.admin.system.entity.OperationLogQuery;
import com.wangcaitao.admin.system.entity.OperationLogSaveDTO;
import com.wangcaitao.admin.system.mapper.OperationLogMapper;
import com.wangcaitao.admin.system.service.OperationLogService;
import com.wangcaitao.admin.system.service.ResourceService;
import com.wangcaitao.common.constant.CommonErrorEnum;
import com.wangcaitao.common.constant.DateTimeFormatterConstant;
import com.wangcaitao.common.constant.HttpStatusEnum;
import com.wangcaitao.common.constant.LongConstant;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.exception.ResultException;
import com.wangcaitao.common.util.AntPathUtils;
import com.wangcaitao.common.util.HttpServletRequestUtils;
import com.wangcaitao.common.util.JacksonUtils;
import com.wangcaitao.common.util.LocationUtils;
import com.wangcaitao.common.util.ResultUtils;
import com.wangcaitao.starter.http.HttpServletUtils;
import com.wangcaitao.starter.router.RestfulRouter;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author wangcaitao
 */
@Service
@Slf4j
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLogDO> implements OperationLogService {

    @Resource
    private ResourceService resourceService;

    @Override
    public Result<OperationLogBO> getById(Long id) {
        OperationLogDO operationLogDo = super.getById(id);

        return null == operationLogDo ? ResultUtils.error(HttpStatusEnum.NOT_FOUND) : ResultUtils.success(JacksonUtils.convertObject(operationLogDo, OperationLogBO.class));
    }

    @Override
    public Result<List<OperationLogBO>> list(OperationLogQuery query) {
        if (query.getPagination()) {
            Wrapper<OperationLogDO> queryWrapper = new QueryWrapper<OperationLogDO>()
                    .select("id", "operator_name", "operator_phone_number", "module", "type", "status", "error_msg", "ip", "location", "os", "browser", "request_method", "request_url", "wait_time", "create_gmt")
                    .eq(null != query.getStatusCode(), "status_code", query.getStatusCode())
                    .eq(StringUtils.isNotEmpty(query.getRequestMethod()), "request_method", query.getRequestMethod())
                    .eq(StringUtils.isNotEmpty(query.getOperatorPhoneNumber()), "operator_phone_number", query.getOperatorPhoneNumber())
                    .likeRight(StringUtils.isNotEmpty(query.getOperatorName()), "operator_name", query.getOperatorName())
                    .likeRight(StringUtils.isNotEmpty(query.getRequestUrl()), "request_url", query.getRequestUrl())
                    .ge(null != query.getStartCreateGmt(), "create_gmt", query.getStartCreateGmt())
                    .le(null != query.getEndCreateGmt(), "create_gmt", query.getEndCreateGmt())
                    .orderByDesc("id");

            int pageNum = query.getPageNum();
            int pageSize = query.getPageSize();

            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<OperationLogDO> operationLogDos = list(queryWrapper);

            return ResultUtils.pagination(pageNum, pageSize, page.getPages(), page.getTotal(), JacksonUtils.convertArray(operationLogDos, OperationLogBO.class));
        } else {
            return ResultUtils.error(CommonErrorEnum.NOT_SUPPORT_LIST_QUERY);
        }
    }

    @Override
    public void save(OperationLogSaveDTO entity) {
        OperationLogDO operationLogDo = JacksonUtils.convertObject(entity, OperationLogDO.class);

        try {
            EmployeeAccountBO employeeAccountBo = EmployeeUtils.getEmployee();
            operationLogDo.setOperatorId(employeeAccountBo.getId());
            operationLogDo.setOperatorName(employeeAccountBo.getName());
            operationLogDo.setOperatorPhoneNumber(employeeAccountBo.getPhoneNumber());
        } catch (ResultException e) {
            operationLogDo.setOperatorId(0L);
            operationLogDo.setOperatorName("");
            operationLogDo.setOperatorPhoneNumber("");
        }

        HttpServletRequest request = HttpServletUtils.getRequest();
        String ip = HttpServletRequestUtils.getRemoteAddr(request);
        operationLogDo.setLocation(LocationUtils.getLocation(ip));
        operationLogDo.setIp(ip);

        String userAgentStr = HttpServletRequestUtils.getUserAgent(request);
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        operationLogDo.setOs(userAgent.getOperatingSystem().getName());
        operationLogDo.setBrowser(userAgent.getBrowser().getName());

        operationLogDo.setStatus(DictUtils.getNameByCode(SystemDictCodeConstant.OPERATION_STATUS, String.valueOf(entity.getStatusCode())));

        String requestMethod = request.getMethod();
        String requestUrl = request.getServletPath();
        operationLogDo.setRequestMethod(requestMethod);
        operationLogDo.setRequestUrl(requestUrl);
        operationLogDo.setRequestContentType(request.getContentType());
        operationLogDo.setRequestUserAgent(userAgentStr);

        boolean isSaveOperationLogParam = true;
        RestfulRouter[] saveOperationLogParams = ExtendIgnoredRouterUtils.SAVE_OPERATION_LOG_PARAMS;
        if (null != saveOperationLogParams) {
            for (RestfulRouter restfulRouter : saveOperationLogParams) {
                if (AntPathUtils.match(restfulRouter.getMethod() + restfulRouter.getUrl(), requestMethod + requestUrl)) {
                    isSaveOperationLogParam = false;

                    break;
                }
            }
        }
        operationLogDo.setRequestParam(isSaveOperationLogParam ? HttpServletRequestUtils.getParam(request) : "");

        StringBuilder module = new StringBuilder();
        String type;
        List<String> parentResourceNames = resourceService.listParentResourceNameByChildId(requestMethod, requestUrl);
        if (CollectionUtils.isEmpty(parentResourceNames)) {
            type = "";
        } else {
            int size = parentResourceNames.size();
            if (1 == size) {
                type = parentResourceNames.get(0);
            } else {
                size = size - 1;
                type = parentResourceNames.get(size);
                for (int i = 0; i < size; i++) {
                    module.append(parentResourceNames.get(i));
                    if (i < size - 1) {
                        module.append(" -> ");
                    }
                }
            }
        }
        operationLogDo.setModule(module.toString());
        operationLogDo.setType(type);

        save(operationLogDo);
    }

    @Override
    public Result<Serializable> delete(List<Long> ids) {
        return removeByIds(ids) ? ResultUtils.success() : ResultUtils.error(HttpStatusEnum.NOT_FOUND);
    }

    @Override
    public Result<Serializable> empty() {
        Wrapper<OperationLogDO> queryWrapper = new QueryWrapper<OperationLogDO>()
                .lt("create_gmt", LocalDate.now().minusDays(LongConstant.THIRTY).format(DateTimeFormatter.ofPattern(DateTimeFormatterConstant.DATE_PATTERN_01)) + " 00:00:00");

        remove(queryWrapper);

        return ResultUtils.success();
    }
}
