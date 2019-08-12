package com.sicau.platform.controller;

import com.sicau.platform.entity.*;
import com.sicau.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;

    @GetMapping("/index")
    public Result index() {
        return new Result(true, StatusCode.UPDATEERROR, hostHolder.getUser().getAccount().toString());
    }

    @PostMapping("/login")
    public Result doLogin(@RequestBody User user, HttpServletResponse response) {
        User findUser = userService.findByUserAccount(user.getAccount());
        if (findUser != null) {
            Result result = null;
            Boolean msg = user.getPassword().equals(findUser.getPassword());
            if (msg == false) {
                result = new Result(false, StatusCode.LOGINERROR, "密码错误");
            } else {
                String ticketName = userService.addTicket(findUser.getUserid());
                Cookie cookie = new Cookie("ticket", ticketName);
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 24 * 3);
                response.addCookie(cookie);
                result = new Result(true, StatusCode.OK, "登录成功");
            }
            return result;
        }
        return new Result(false, StatusCode.LOGINERROR, "登录失败，账户不存在");
    }

    @PostMapping("/logout")
    public Result doLogout(@CookieValue(name = "ticket") String ticket) {
        userService.changeStatus(ticket);
        return new Result(true, StatusCode.OK, "退出成功");
    }

    @PostMapping("/regist")
    public Result doRegist(@RequestBody User user) {
        if (userService.findByUserAccount(user.getAccount()) != null)
            return new Result(false, StatusCode.REGISTERROR, "当前用户已存在");
        if (userService.addUser(user)) {
            return new Result(true, StatusCode.OK, "用户注册成功");
        } else {
            return new Result(false, StatusCode.REGISTERROR, "插入用户数据失败");
        }
    }

/*    *//**
     * 判断手机号或者email是否正确，identity必须正确（这个id可以是学号，可以是身份证）
     *//*
    @PostMapping("/forget")
    public Result findPassword(@RequestBody UserDetail userDetail){
        userService.
    }*/

    @PostMapping("/updateDetail")
    /* todo 这个异常抛得太low了 */
    public Result updateDetail(@RequestBody UserDetail userDetail) throws Exception {
        boolean result = userService.updateUserDetail(userDetail);
        if (result) {
            return new Result(true, StatusCode.OK, "信息更新成功");
        } else {
            return new Result(false, StatusCode.UPDATEERROR, "信息更新失败");
        }
    }

    @GetMapping("/getDetail")
    public Result getDetail() {
        UserDetail userDetail = userService.getUserDetail(hostHolder.getUser().getUserid());
        if (userDetail != null){
            return new Result(true, StatusCode.OK, "详细信息获取成功", userDetail);
        }else {
            return new Result(false, StatusCode.GETINFOEERROR,"你还未设置详细信息");
        }
    }
}