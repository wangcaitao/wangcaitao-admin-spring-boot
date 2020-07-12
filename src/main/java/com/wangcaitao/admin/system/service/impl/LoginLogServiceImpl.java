package com.wangcaitao.admin.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wangcaitao.admin.common.util.DictUtils;
import com.wangcaitao.admin.system.constant.SystemDictCodeConstant;
import com.wangcaitao.admin.system.entity.LoginLogBO;
import com.wangcaitao.admin.system.entity.LoginLogDO;
import com.wangcaitao.admin.system.entity.LoginLogQuery;
import com.wangcaitao.admin.system.entity.LoginLogSaveDTO;
import com.wangcaitao.admin.system.mapper.LoginLogMapper;
import com.wangcaitao.admin.system.service.LoginLogService;
import com.wangcaitao.common.constant.CommonErrorEnum;
import com.wangcaitao.common.constant.DateTimeFormatterConstant;
import com.wangcaitao.common.constant.HttpStatusEnum;
import com.wangcaitao.common.constant.LongConstant;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.util.HttpServletRequestUtils;
import com.wangcaitao.common.util.JacksonUtils;
import com.wangcaitao.common.util.LocationUtils;
import com.wangcaitao.common.util.ResultUtils;
import com.wangcaitao.starter.http.HttpServletUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLogDO> implements LoginLogService {

    @Override
    public Result<List<LoginLogBO>> list(LoginLogQuery query) {
        if (query.getPagination()) {
            Wrapper<LoginLogDO> queryWrapper = new QueryWrapper<LoginLogDO>()
                    .eq(null != query.getStatusCode(), "status_code", query.getStatusCode())
                    .eq(StringUtils.isNotEmpty(query.getLoginUserPhoneNumber()), "login_user_phone_number", query.getLoginUserPhoneNumber())
                    .likeRight(StringUtils.isNotEmpty(query.getLoginUserName()), "login_user_name", query.getLoginUserName())
                    .ge(null != query.getStartCreateGmt(), "create_gmt", query.getStartCreateGmt())
                    .le(null != query.getEndCreateGmt(), "create_gmt", query.getEndCreateGmt())
                    .orderByDesc("id");

            int pageNum = query.getPageNum();
            int pageSize = query.getPageSize();

            Page<Object> page = PageHelper.startPage(pageNum, pageSize);
            List<LoginLogDO> loginLogDos = list(queryWrapper);

            return ResultUtils.pagination(pageNum, pageSize, page.getPages(), page.getTotal(), JacksonUtils.convertArray(loginLogDos, LoginLogBO.class));
        } else {
            return ResultUtils.error(CommonErrorEnum.NOT_SUPPORT_LIST_QUERY);
        }
    }

    @Override
    public void save(LoginLogSaveDTO entity) {
        try {
            LoginLogDO loginLogDo = JacksonUtils.convertObject(entity, LoginLogDO.class);

            HttpServletRequest request = HttpServletUtils.getRequest();
            String ip = HttpServletRequestUtils.getRemoteAddr(request);
            loginLogDo.setIp(ip);
            loginLogDo.setLocation(LocationUtils.getLocation(ip));

            String userAgentStr = HttpServletRequestUtils.getUserAgent(request);
            UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
            loginLogDo.setUserAgent(userAgentStr);
            loginLogDo.setOs(userAgent.getOperatingSystem().getName());
            loginLogDo.setBrowser(userAgent.getBrowser().getName());

            loginLogDo.setStatus(DictUtils.getNameByCode(SystemDictCodeConstant.LOGIN_STATUS, String.valueOf(entity.getStatusCode())));

            save(loginLogDo);
        } catch (Exception e) {
            log.error("保存登陆日志失败. param: {}", JacksonUtils.toJsonString(entity), e);
        }
    }

    @Override
    public Result<Serializable> delete(List<Long> ids) {
        return removeByIds(ids) ? ResultUtils.success() : ResultUtils.error(HttpStatusEnum.NOT_FOUND);
    }

    @Override
    public Result<Serializable> empty() {
        Wrapper<LoginLogDO> queryWrapper = new QueryWrapper<LoginLogDO>()
                .lt("create_gmt", LocalDate.now().minusDays(LongConstant.THIRTY).format(DateTimeFormatter.ofPattern(DateTimeFormatterConstant.DATE_PATTERN_01)) + " 00:00:00");

        remove(queryWrapper);

        return ResultUtils.success();
    }
}
