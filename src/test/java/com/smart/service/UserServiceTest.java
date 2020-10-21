package com.smart.service;

import com.smart.dao.UserDao;
import com.smart.domain.User;
import com.smart.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

@ContextConfiguration(locations="classpath*:smart-context.xml")
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    private UserService userService = new UserServiceImpl();

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @org.testng.annotations.Test
    public void testHasMatchUser(){
        boolean b1 = userService.hasMatchUser("admin","123456");
        boolean b2 = userService.hasMatchUser("admin", "1111");
        Assert.assertTrue(b1);
        Assert.assertTrue(!b2);

    }

    @Test
    public void testFindUserByUserName(){
        User user = userService.findUserByUserName("admin");
        Assert.assertEquals(user.getUserName(),"admin");
    }


    @Test
    @Rollback(false)
    public void testLoginSuccess(){
        User user = new User();
        user.setUserId(1);
        user.setCredits(0);
        user.setUserName("admin");
        user.setLastIp("192.168.1.1");
        user.setPassword("123456");
        Date now = new Date();
        user.setLastVisit(now);

        userService.loginSuccess(user);
    }

}
