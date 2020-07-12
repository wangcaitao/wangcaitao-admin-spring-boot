package com.wangcaitao.admin.auth.service.impl;

import com.wangcaitao.admin.auth.constant.AuthCacheEnum;
import com.wangcaitao.admin.auth.constant.AuthConstant;
import com.wangcaitao.admin.auth.constant.AuthPropertyNameConstant;
import com.wangcaitao.admin.auth.entity.AuthBO;
import com.wangcaitao.admin.auth.entity.AuthDTO;
import com.wangcaitao.admin.auth.service.AuthService;
import com.wangcaitao.admin.common.util.EmployeeUtils;
import com.wangcaitao.admin.common.util.HttpRequestUtils;
import com.wangcaitao.admin.common.util.PropertyUtils;
import com.wangcaitao.admin.employee.entity.EmployeeAccountBO;
import com.wangcaitao.admin.employee.service.EmployeeAccountService;
import com.wangcaitao.admin.system.constant.SystemDictCodeConstant;
import com.wangcaitao.admin.system.entity.LoginLogSaveDTO;
import com.wangcaitao.admin.system.entity.MenuBO;
import com.wangcaitao.admin.system.service.LoginLogService;
import com.wangcaitao.admin.system.service.RoleResourceService;
import com.wangcaitao.common.entity.Result;
import com.wangcaitao.common.util.JacksonUtils;
import com.wangcaitao.common.util.ResultUtils;
import com.wangcaitao.starter.redis.StringRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author wangcaitao
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Resource
    private EmployeeAccountService employeeAccountService;

    @Resource
    private LoginLogService loginLogService;

    @Resource
    private RoleResourceService roleResourceService;

    @Override
    public Result<AuthBO> login(AuthDTO entity) {
        String phoneNumber = entity.getPhoneNumber();
        EmployeeAccountBO employeeAccountBo = employeeAccountService.getByPhoneNumber(phoneNumber);
        if (null == employeeAccountBo) {
            saveErrorLoginLog(phoneNumber, "帐号不存在");

            return ResultUtils.error("手机号码或密码错误");
        }

        if (!Objects.equals(employeeAccountBo.getPassword(), DigestUtils.md5DigestAsHex(entity.getPassword().getBytes()))) {
            saveErrorLoginLog(phoneNumber, "密码错误");

            return ResultUtils.error("手机号码或密码错误");
        }

        if (Objects.equals(employeeAccountBo.getStatusCode(), SystemDictCodeConstant.EMPLOYEE_STATUS_LEFT)) {
            saveErrorLoginLog(phoneNumber, "离职人员登陆");

            return ResultUtils.error("您已离职, 谢谢!");
        }

        if (Objects.equals(employeeAccountBo.getAccountStatusCode(), SystemDictCodeConstant.ACCOUNT_STATUS_DISABLE)) {
            saveErrorLoginLog(phoneNumber, "帐号已禁用");

            return ResultUtils.error("请联系管理员开通帐号!");
        }

        Long roleId = employeeAccountBo.getRoleId();
        List<MenuBO> menus = roleResourceService.listMenu(roleId);
        List<String> authorizedResources = roleResourceService.listAuthorizedResources(roleId);

        String token = DigestUtils.md5DigestAsHex((UUID.randomUUID().toString().replace("-", "") + phoneNumber).getBytes());
        long timeout = Long.parseLong(PropertyUtils.getValueByName(AuthPropertyNameConstant.TOKEN_TIMEOUT));
        StringRedisUtils.set(String.format(AuthCacheEnum.AUTHORIZED_TOKEN.getKey(), token), JacksonUtils.toJsonString(authorizedResources), timeout, TimeUnit.HOURS);
        StringRedisUtils.set(String.format(AuthCacheEnum.EMPLOYEE_TOKEN.getKey(), token), JacksonUtils.toJsonString(employeeAccountBo), timeout, TimeUnit.HOURS);

        String tokenSingleValue = PropertyUtils.getValueByName(AuthPropertyNameConstant.TOKEN_SINGLE);
        if (Objects.equals(tokenSingleValue, AuthConstant.TOKEN_SINGLE_VALUE)) {
            String tokenKey = String.format(AuthCacheEnum.TOKEN.getKey(), phoneNumber);
            String tokenValue = StringRedisUtils.get(tokenKey);
            if (StringUtils.isNotEmpty(tokenValue)) {
                StringRedisUtils.delete(String.format(AuthCacheEnum.AUTHORIZED_TOKEN.getKey(), tokenValue));
                StringRedisUtils.delete(String.format(AuthCacheEnum.EMPLOYEE_TOKEN.getKey(), tokenValue));
            }

            StringRedisUtils.set(tokenKey, token, timeout, TimeUnit.HOURS);
        }

        AuthBO authBo = new AuthBO();
        authBo.setToken(token);
        authBo.setName(employeeAccountBo.getName());
        authBo.setAvatarImgUrl(employeeAccountBo.getAvatarImgUrl());
        authBo.setMenus(menus);
        authBo.setAuthorizedResources(authorizedResources);

        saveSuccessLoginLog(employeeAccountBo);

        return ResultUtils.success(authBo);
    }

    @Override
    public Result<Serializable> logout() {
        String token = HttpRequestUtils.getToken();
        String phoneNumber = EmployeeUtils.getEmployeePhoneNumber();

        StringRedisUtils.delete(String.format(AuthCacheEnum.TOKEN.getKey(), phoneNumber));
        StringRedisUtils.delete(String.format(AuthCacheEnum.AUTHORIZED_TOKEN.getKey(), token));
        StringRedisUtils.delete(String.format(AuthCacheEnum.EMPLOYEE_TOKEN.getKey(), token));

        return ResultUtils.success();
    }

    /**
     * 保存错误登陆日志
     *
     * @param errorMsg errorMsg
     */
    private void saveErrorLoginLog(String phoneNumber, String errorMsg) {
        LoginLogSaveDTO loginLogSaveDto = new LoginLogSaveDTO();
        loginLogSaveDto.setStatusCode(SystemDictCodeConstant.LOGIN_STATUS_ERROR);
        loginLogSaveDto.setLoginUserPhoneNumber(phoneNumber);
        loginLogSaveDto.setErrorMsg(errorMsg);

        loginLogService.save(loginLogSaveDto);
    }

    /**
     * 保存成功登陆日志
     *
     * @param entity entity
     */
    private void saveSuccessLoginLog(EmployeeAccountBO entity) {
        LoginLogSaveDTO loginLogSaveDto = new LoginLogSaveDTO();
        loginLogSaveDto.setStatusCode(SystemDictCodeConstant.LOGIN_STATUS_SUCCESS);
        loginLogSaveDto.setLoginUserId(entity.getId());
        loginLogSaveDto.setLoginUserName(entity.getName());
        loginLogSaveDto.setLoginUserPhoneNumber(entity.getPhoneNumber());

        loginLogService.save(loginLogSaveDto);
    }
}
