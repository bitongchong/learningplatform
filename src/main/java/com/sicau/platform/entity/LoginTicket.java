package com.sicau.platform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "login_ticket")
public class LoginTicket implements Serializable {
    private static final long serialVersionUID = 5L;
    @Id
    private Long id;
    private Long userId;
    private String ticket;
    private Date expired;
    private int status;
}
