package com.sicau.platform.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "admin")
public class Admin implements Serializable {
    private static final long serialVersionUID = 6L;

    @Id
    Long adminId;
    String job;
    String department;
    String account;
    String password;
}
