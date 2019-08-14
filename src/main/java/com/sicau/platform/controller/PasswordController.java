package com.sicau.platform.controller;

import com.sicau.platform.entity.HostHolder;
import com.sicau.platform.entity.PasswordToken;
import com.sicau.platform.entity.Result;
import com.sicau.platform.entity.StatusCode;
import com.sicau.platform.exception.EmailSendFailException;
import com.sicau.platform.service.PasswordService;
import com.sicau.platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;

/**
 * @author liuyuehe
 * @description 密码相关
 * @date 2019/8/13
 */

@RestController
public class PasswordController {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @Autowired
    PasswordService passwordService;

    @PostMapping("/forget")
    public Result findPassword(String account) throws EmailSendFailException {
        Long userId = userService.findByUserAccount(account).getUserid();
        String email = passwordService.findEmailByUserId(userId);
        if (Objects.isNull(email)) {
            return new Result(false, StatusCode.EMAILUNKNOWN, "未完善邮箱信息，请联系管理员进行密码找回");
        }
        passwordService.sendTokenEmail(userId, email);
        return new Result(true, 0, "密码找回邮件已发送");
    }

    @GetMapping("/findPassword/{token}")
    public String findPassword(@PathVariable("token") Long token) {
        PasswordToken passwordToken = passwordService.findPasswordToken(token);
        if (passwordToken != null) {
            Date expired = passwordToken.getExpired();
            if (passwordToken.getStatus() != 0 || new Date().after(expired)) {
                return "token无效或已使用";
            }
            boolean initPasswordResult = passwordService.initPassword(passwordToken.getAccount(), passwordToken);
            return initPasswordResult ? "密码已初始化为00000000，登录后请及时更新" :
                    "服务端错误";
        }
        return "token无效";
    }

    @PostMapping("/changePassword")
    public Result changePassword(String oldPassword, String newPassword, String repeatPassword) {
        if (Objects.equals(newPassword, repeatPassword)) {
            if (Objects.equals(hostHolder.getUser().getPassword(), oldPassword)) {
                passwordService.changePassword(newPassword);
                return new Result(true, StatusCode.OK, "密码修改成功");
            } else {
                return new Result(false, StatusCode.PASSWORDERRO, "旧密码错误");
            }
        }
        return new Result(false, StatusCode.PASSWORDERRO, "两次输入密码不相同");
    }
}
