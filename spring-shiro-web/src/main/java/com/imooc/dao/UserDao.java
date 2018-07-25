package com.imooc.dao;

import com.imooc.vo.User;

import java.util.List;

public interface UserDao {
    User getUserByUserName(String userName);

    List<String> queryRolesByUsername(String userName);

    List<String> queryPermissionsByUserName(String userName);
}
