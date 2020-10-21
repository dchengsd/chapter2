package com.smart.service.impl;

import com.smart.dao.LoginLogDao;
import com.smart.dao.UserDao;
import com.smart.dao.impl.LoginLogDaoImpl;
import com.smart.dao.impl.UserDaoImpl;
import com.smart.domain.LoginLog;
import com.smart.domain.User;
import com.smart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    private LoginLogDao loginLogDao = new LoginLogDaoImpl();

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setLoginLogDao(LoginLogDao loginLogDao) {
        this.loginLogDao = loginLogDao;
    }

    public boolean hasMatchUser(String userName, String password) {

        int matchCount = userDao.getMatchCount(userName,password);
        return matchCount > 0;
    }

    public User findUserByUserName(String userName) {
        return userDao.findUserByUserName(userName);
    }

    @Transactional
    public void loginSuccess(User user) {
        user.setCredits(5+user.getCredits());
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getUserId());
        loginLog.setIp(user.getLastIp());
        loginLog.setLoginDate(user.getLastVisit());
        userDao.updateLoginInfo(user);
        loginLogDao.insertLogin(loginLog);

    }
}
