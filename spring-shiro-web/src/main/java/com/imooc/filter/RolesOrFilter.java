package com.imooc.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义的filter，主要作为一个拦截器的内容所使用。
 * 所实现的功能是：传多个的Roles过来，实现任何集合
 */
public class RolesOrFilter extends AuthorizationFilter{
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest,servletResponse);
        String[] roles = (String[]) o;

        // 说明是可以访问的，不需要认证
        if (roles == null || roles.length == 0) {
            return  true;
        }

        // 遍历角色
        for (String role:roles) {
             // 如果有该角色,返回正
            if (subject.hasRole(role)) {
                return true;
            }
        }



        return false;
    }
}
