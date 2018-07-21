package com.imooc.test;/**
 * @Description: //TODO
 * @Author: cxx
 * @Date: Created in 2018/7/2019:26
 * @Modfied By:
 */

import com.imooc.shiro.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
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


        // HashedCredentialsMatcher工具的使用
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5"); // md5加密
        matcher.setHashIterations(1); // 加密次数
        // 在自定义realm中设置HashedCredentialsMatcher工具，为的就是说，这个工具会将传输过来的用户身份信息中的密码进行加密，用md5的方式，然后进行对比
        customRealm.setCredentialsMatcher(matcher);


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
