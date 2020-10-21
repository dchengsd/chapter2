package com.smart.web;

import com.smart.domain.LoginLog;
import com.smart.domain.User;
import com.smart.service.UserService;
import com.smart.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class LoginController {

    private UserService userService = new UserServiceImpl();

   //处理index.xml页面请求
    @RequestMapping(value = "/index.html")
    public String loginPage(){
        return "login";
    }

    //处理loginCheck.xml页面请求
    @RequestMapping(value = "/loginCheck.html")
    public ModelAndView loginCheck(HttpServletRequest request, LoginCommand loginCommand){

        boolean isValidUser = userService.hasMatchUser(loginCommand.getUserName(),loginCommand.getPassword());
        if(!isValidUser){
            return new ModelAndView("login","error","用户名密码错误！");
        }else{
            User user = userService.findUserByUserName(loginCommand.getUserName());
            user.setLastIp(request.getLocalAddr());
            user.setLastVisit(new Date());
            userService.loginSuccess(user);
            request.getSession().setAttribute("user",user);
            return new ModelAndView("main");
        }
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
