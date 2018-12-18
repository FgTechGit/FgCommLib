package cn.sight.test.app.service;

/**
 * 页面跳转路由表
 */
public interface RoutingTable {

    /**
     * 账号密码登录页
     */
    String LOGIN_PAGE = "/outer/login";

    /**
     * 验证码登录页
     */
    String LOGIN_VIA_VERIFICATION_CODE = "/outer/loginViaVerificationCode";

}
