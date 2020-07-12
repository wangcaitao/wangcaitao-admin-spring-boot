package com.wangcaitao.admin.auth.constant;

/**
 * @author wangcaitao
 */
public class AuthPropertyNameConstant {

    /**
     * token 失效时间. 单位: 小时
     */
    public static final String TOKEN_TIMEOUT = "token.timeout";

    /**
     * 一个帐号是否只能一个会话在线. 0: 可以多个, 1: 只能一个
     */
    public static final String TOKEN_SINGLE = "token.single";

    /**
     * 是否只能查看. false: 不是, true: 是
     */
    public static final String ONLY_VIEW = "only-view";
}
