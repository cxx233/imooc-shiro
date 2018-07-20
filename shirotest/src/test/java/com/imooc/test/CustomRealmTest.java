package com.imooc.test;/**
 * @Description: //TODO
 * @Author: cxx
 * @Date: Created in 2018/7/2019:26
 * @Modfied By:
 */

import com.imooc.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * 自定义CustomRealm测试类
 * @author: xujiaxi
 * @date: 2018/7/20
 */
public class CustomRealmTest  {

    @Test
    public void testAuthentication() {

        CustomRealm customRealm = new CustomRealm();

        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        // 2. 主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Mark","123456");
        subject.login(token);

        System.out.println("isAuthenticated:" + subject.isAuthenticated());

        subject.checkRoles("admin");

        subject.checkPermissions("user:add","user:delete");

        subject.logout();

        System.out.println("isAuthenticated:" + subject.isAuthenticated());

    }
}
