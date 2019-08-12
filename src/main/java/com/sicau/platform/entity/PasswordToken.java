package com.sicau.platform.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author liuyuehe
 * @description 密码找回时的token
 * @date 2019/8/12
 */
@Entity
@Builder
@Table(name = "find_password_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String account;
    Long token;
    private Date expired;
    Integer status;
}
