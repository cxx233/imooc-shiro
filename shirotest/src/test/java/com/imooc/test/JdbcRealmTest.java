package com.imooc.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * jdbcReal测试
 */
public class JdbcRealmTest {

     private  DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    @Test
    public void testAuthentication() {


        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        jdbcRealm.setPermissionsLookupEnabled(true); // 需要设置权限的开关，默认为false，则表示不会自动去查询权限的控制

        String sql = "select password from my_users where username=?";
        jdbcRealm.setAuthenticationQuery(sql);

        String roleSql = "select role_name from  my_user_role where user_name=?"; // 角色查询语句
        jdbcRealm.setUserRolesQuery(roleSql); // 根据自己表查询用户的角色名称，自定义的sql，自定义的表


        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
//        defaultSecurityManager.setRealm(iniRealm);
        defaultSecurityManager.setRealm(jdbcRealm);

        // 2. 主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("zhang","456");
        subject.login(token);

        System.out.println("isAuthenticated:" + subject.isAuthenticated());
//
        subject.checkRoles("user");
//
//        subject.checkPermissions("user:update","user:delete");
//        subject.checkPermissions("user:select");

        subject.logout();

        System.out.println("isAuthenticated:" + subject.isAuthenticated());

    }
}
