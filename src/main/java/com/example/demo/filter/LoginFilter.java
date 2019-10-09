package com.example.demo.filter;

import com.example.demo.shiro.ShiroConfig;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*自定义过滤器用来处理session过期后ajax请求自动跳转到登录页的问题
* onAccessDenied方法只有在未登录时，即session为空时，执行此方法。
* */
public class LoginFilter extends FormAuthenticationFilter{

    /**
     * 该方法在需要登录而未登录的时候，都会执行此方法，这里重写此方法，主要是处理ajax请求时
     * 没有登录时，跳转登录页重新登录。
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
//                if (log.isTraceEnabled()) {
//                    log.trace("Login submission detected.  Attempting to execute login.");
//                }
                return executeLogin(request, response);
            } else {
//                if (log.isTraceEnabled()) {
//                    log.trace("Login page view.");
//                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
//            if (log.isTraceEnabled()) {
//                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
//                        "Authentication url [" + getLoginUrl() + "]");
//            }
            //如果是Ajax请求，不跳转登录
            if (ShiroConfig.isAjax(httpServletRequest)) {
                System.out.println("ajax");
                httpServletResponse.setStatus(401);
            } else {
                saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
    }

}
