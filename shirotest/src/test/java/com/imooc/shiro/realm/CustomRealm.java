package com.imooc.shiro.realm;/**
 * @Description: //TODO
 * @Author: cxx
 * @Date: Created in 2018/7/2019:13
 * @Modfied By:
 */

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 自定义Realm
 * @author: xujiaxi
 * @date: 2018/7/20
 */

/**
 * 自定义realm，继承AuthorizingRealm，则要实现自定义的授权方法和验证方法
 */
public class CustomRealm extends AuthorizingRealm  {

    // 仿造数据
    Map<String ,String > userMap = new HashMap<String, String>(16);

    {
        userMap.put("Mark","123456");
    }


    // 授权方法

    /**
     *
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        // 1. 获取用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();


        // 2. 根据用户进行获取角色数据(从数据库中，或者缓存数据中获取角色数据）
        Set<String> roles = getRolesByUsername(userName);

        // 3. 根据用户进行获取权限数据（应该是从数据库中，或者缓存数据中获取权限数据）
        Set<String> permissions = getPermissionsByUserName(userName);

        // 4. 将角色数据和权限数据进行返回
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);

        return authorizationInfo;
    }

    /**
     * 根据用户名获取该用户的权限，这里是测试。则会用测试数据展示，但实际情况应该是用数据库连接比较好
     * @param userName
     * @return
     */
    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> sets = new HashSet<String>();
        sets.add("user:delete");
        sets.add("user:add");
        return sets;
    }

    /**
     * 根据用户进行获取用户的角色，这里是测试。则会用测试数据展示，但实际情况应该是用数据库连接比较好
     * @param userName
     * @return
     */
    private Set<String> getRolesByUsername(String userName) {
        Set<String> sets = new HashSet<String>();
        sets.add("admin");
        sets.add("user");
        return sets;
    }

    @Override
    public String getName() {
        return "CustomRealm";
    }

    // 认证方法

    /**
     *
     * @param authenticationToken 主题传送过来的认证信息
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        // 1. 从主题传过来的认证信息中，获得用户名
        String userName = (String) authenticationToken.getPrincipal();


        // 2. 通过用户名到数据库中获取凭证
        String password = getPasswordByUserName(userName);
        // 如果密码不存在，则该用户信息是不存在的
        if (password == null) {
            return  null;
        }

        // 如果有该用户密码时，则表示用户是存在的，创建返回对象
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("Mark",password,"CustomRealm");
        return authenticationInfo;
    }

    /**
     * 通过用户名到数据库中获取凭证，这里应该从数据库中获取。但是测试环境下，直接以Map形式存储
     * 模拟数据库查询凭证
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        return userMap.get(userName);
    }
}
