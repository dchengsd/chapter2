package com.smart.service;

import com.smart.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    //检查用户名密码正确性
    public boolean hasMatchUser(String userName, String password);

    //根据用户名加载User对象
    public User findUserByUserName(String userName);

    //登录成功，更新日志
    public void loginSuccess(User user);
}
