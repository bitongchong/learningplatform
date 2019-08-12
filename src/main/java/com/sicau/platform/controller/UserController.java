package com.sicau.platform.controller;

import com.sicau.platform.entity.*;
import com.sicau.platform.exception.EmailSendFailException;
import com.sicau.platform.service.UserService;
import com.sicau.platform.util.IdGenerator;
import com.sicau.platform.util.MailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;

/**
 * @author boot liu
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    MailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/index")
    public Result index() {
        return new Result(true, StatusCode.UPDATEERROR, hostHolder.getUser().getAccount().toString());
    }

    @PostMapping("/login")
    public Result doLogin(@RequestBody User user, HttpServletResponse response) {
        User findUser = userService.findByUserAccount(user.getAccount());
        if (findUser != null) {
            Result result = null;
            boolean msg = user.getPassword().equals(findUser.getPassword());
            if (!msg) {
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
        if (userService.findByUserAccount(user.getAccount()) != null) {
            return new Result(false, StatusCode.REGISTERROR, "当前用户已存在");
        }
        if (userService.addUser(user)) {
            return new Result(true, StatusCode.OK, "用户注册成功");
        } else {
            return new Result(false, StatusCode.REGISTERROR, "插入用户数据失败");
        }
    }

    @PostMapping("/forget")
    public Result findPassword(String account) throws EmailSendFailException {
        // todo controller层不能写大量逻辑代码，只留下主体流程
        Long userId = hostHolder.getUser().getUserid();
        String email = userService.findEmailByUserId(userId);
        if (Objects.isNull(email)) {
            return new Result(false, StatusCode.EMAILUNKNOWN, "未完善邮箱信息，请联系管理员进行密码找回");
        } else {
            long token = IdGenerator.nextId();
            Date now = new Date();
            now.setTime(10 * 60 * 1000 + now.getTime());
            PasswordToken passwordToken = PasswordToken.builder().token(token).account(hostHolder.getUser()
                    .getAccount()).status(0).expired(now).build();
            userService.addPasswordToken(passwordToken);
            boolean sendEmailResult = mailSender.sendEmail(email, "密码找回邮件", token);
            if (!sendEmailResult) {
                throw new EmailSendFailException("邮件发送失败");
            }
        }
        return new Result(true, 0, "密码找回邮件已发送");
    }

    @GetMapping("/findPassword/{token}")
    public Result findPassword(@PathVariable("token") Long token) {
        PasswordToken passwordToken = userService.findPasswordToken(token);
        if (passwordToken != null) {
            Date expired = passwordToken.getExpired();
            if (passwordToken.getStatus() != 0 || new Date().after(expired)) {
                return new Result(false, StatusCode.TOKENEXPIRED, "token无效");
            }
            boolean initPasswordResult = userService.initPassword(passwordToken.getAccount());
            return initPasswordResult ? new Result(true, StatusCode.OK, "密码已初始化为00000000，登录后请及时更新") :
                    new Result(false, StatusCode.INTERNALSERVERERROR, "服务端错误");
        }
        return new Result(false, StatusCode.TOKENEXPIRED, "token无效");
    }

    @PostMapping("/changePassword")
    public Result changePassword(String oldPassword, String newPassword) {
        if (Objects.equals(hostHolder.getUser().getPassword(), oldPassword)) {
            userService.changePassword(newPassword);
            return new Result(true, StatusCode.OK, "密码修改成功");
        } else {
            return new Result(false, StatusCode.PASSWORDERRO, "旧密码错误");
        }
    }

    @PostMapping("/updateDetail")
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
        if (userDetail != null) {
            return new Result(true, StatusCode.OK, "详细信息获取成功", userDetail);
        } else {
            return new Result(false, StatusCode.GETINFOEERROR, "你还未设置详细信息");
        }
    }
}